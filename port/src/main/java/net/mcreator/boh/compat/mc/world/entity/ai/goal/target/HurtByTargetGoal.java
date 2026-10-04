package net.mcreator.boh.compat.mc.world.entity.ai.goal.target;

import java.util.List;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.util.AxisAlignedBB;

/**
 * Port of the 1.20.1 HurtByTargetGoal: targets whoever last hurt the mob (unless it is one of the ignored types),
 * remembers it for 15 s out of sight, and with setAlertOthers() pulls nearby mobs of the same class in.
 */
public class HurtByTargetGoal extends TargetGoal {

    private static final Targeting.Conditions HURT_BY_TARGETING = new Targeting.Conditions().ignoreLineOfSight().ignoreInvisibilityTesting();

    private final Class<?>[] toIgnoreDamage;
    private boolean alertSameType;
    private int timestamp;
    private Class<?>[] toIgnoreAlert;

    public HurtByTargetGoal(EntityCreature mob, Class<?>... toIgnoreDamage) {
        super(mob, true);
        this.toIgnoreDamage = toIgnoreDamage;
    }

    @Override
    public boolean canUse() {
        int stamp = mob.func_142015_aE();
        EntityLivingBase attacker = mob.getAITarget();
        if (stamp == timestamp || attacker == null) return false;
        for (Class<?> c : toIgnoreDamage) if (c.isInstance(attacker)) return false;
        return canAttack(attacker, HURT_BY_TARGETING);
    }

    public HurtByTargetGoal setAlertOthers(Class<?>... reinforcementTypes) {
        alertSameType = true;
        toIgnoreAlert = reinforcementTypes;
        return this;
    }

    @Override
    public void start() {
        mob.setAttackTarget(mob.getAITarget());
        targetMob = mob.getAttackTarget();
        timestamp = mob.func_142015_aE();
        unseenMemoryTicks = 300;
        if (alertSameType) alertOthers();
        super.start();
    }

    protected void alertOthers() {
        double range = getFollowDistance();
        AxisAlignedBB box = AxisAlignedBB.getBoundingBox(mob.posX, mob.posY, mob.posZ, mob.posX + 1, mob.posY + 1, mob.posZ + 1)
            .expand(range, 10.0, range);
        EntityLivingBase attacker = mob.getAITarget();
        if (attacker == null) return;
        List<?> others = mob.worldObj.getEntitiesWithinAABB(mob.getClass(), box);
        for (Object o : others) {
            EntityLiving other = (EntityLiving) o;
            if (other == mob || other.getAttackTarget() != null || other.isOnSameTeam(attacker)) continue;
            if (mob instanceof EntityTameable && ((EntityTameable) mob).getOwner() != ((EntityTameable) other).getOwner()) continue;
            boolean ignored = false;
            if (toIgnoreAlert != null) for (Class<?> c : toIgnoreAlert) if (c == other.getClass()) ignored = true;
            if (!ignored) alertOther(other, attacker);
        }
    }

    protected void alertOther(EntityLiving other, EntityLivingBase target) {
        other.setAttackTarget(target);
    }
}
