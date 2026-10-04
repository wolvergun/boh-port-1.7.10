package net.mcreator.boh.compat.mc.world.entity.ai.goal.target;

import java.util.function.Predicate;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;

/** Port of the 1.20.1 NearestAttackableTargetGoal: nearest visible target of the type within follow range. */
public class NearestAttackableTargetGoal<T> extends TargetGoal {

    protected final Class<T> targetType;
    protected final int randomInterval;
    protected EntityLivingBase target;
    protected Targeting.Conditions targetConditions;

    public NearestAttackableTargetGoal(EntityCreature mob, Class<T> targetType, boolean mustSee) {
        this(mob, targetType, 10, mustSee, false, null);
    }

    public NearestAttackableTargetGoal(EntityCreature mob, Class<T> targetType, boolean mustSee, boolean mustReach) {
        this(mob, targetType, 10, mustSee, mustReach, null);
    }

    public NearestAttackableTargetGoal(EntityCreature mob, Class<T> targetType, boolean mustSee, Predicate<EntityLivingBase> predicate) {
        this(mob, targetType, 10, mustSee, false, predicate);
    }

    public NearestAttackableTargetGoal(EntityCreature mob, Class<T> targetType, int randomInterval, boolean mustSee, boolean mustReach,
        Predicate<EntityLivingBase> predicate) {
        super(mob, mustSee, mustReach);
        this.targetType = targetType;
        // 1.20 rolls 1-in-(interval/2) every 2 ticks (~interval ticks on average); 1.7.10 asks every 3 ticks
        this.randomInterval = randomInterval <= 0 ? 0 : Math.max(1, (randomInterval + 2) / 3);
        this.targetConditions = new Targeting.Conditions().range(getFollowDistance()).selector(predicate);
    }

    @Override
    public boolean canUse() {
        if (randomInterval > 0 && mob.getRNG().nextInt(randomInterval) != 0) return false;
        findTarget();
        return target != null;
    }

    protected AxisAlignedBB getTargetSearchArea(double range) {
        return mob.boundingBox.expand(range, 4.0, range);
    }

    protected void findTarget() {
        double range = getFollowDistance();
        targetConditions.range(range);
        Iterable<?> candidates = EntityPlayer.class.isAssignableFrom(targetType) ? mob.worldObj.playerEntities
            : mob.worldObj.getEntitiesWithinAABB(targetType, getTargetSearchArea(range));
        target = null;
        double best = Double.MAX_VALUE;
        double eyeY = mob.posY + mob.getEyeHeight();
        for (Object o : candidates) {
            if (!targetType.isInstance(o) || !(o instanceof EntityLivingBase)) continue;
            EntityLivingBase e = (EntityLivingBase) o;
            if (!canAttack(e, targetConditions)) continue;
            double d = e.getDistanceSq(mob.posX, eyeY, mob.posZ);
            if (d < best) {
                best = d;
                target = e;
            }
        }
    }

    @Override
    public void start() {
        mob.setAttackTarget(target);
        super.start();
    }

    public void setTarget(EntityLivingBase target) {
        this.target = target;
    }
}
