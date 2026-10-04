package net.mcreator.boh.compat.mc.world.entity.ai.goal.target;

import java.util.EnumSet;

import net.mcreator.boh.compat.mc.world.entity.ai.goal.Flag;
import net.mcreator.boh.compat.mc.world.entity.ai.goal.Goal;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.MathHelper;

/**
 * Port of the 1.20.1 TargetGoal. Unlike 1.7.10's EntityAITarget, acquiring a target always needs line of sight
 * (see {@link Targeting}); {@code mustSee} only decides whether a target that drops out of view is forgotten.
 */
public abstract class TargetGoal extends Goal {

    protected final EntityCreature mob;
    protected final boolean mustSee;
    private final boolean mustReach;
    private int reachCache;
    private int reachCacheTime;
    private int unseenTicks;
    protected EntityLivingBase targetMob;
    protected int unseenMemoryTicks = 60;

    public TargetGoal(EntityCreature mob, boolean mustSee) {
        this(mob, mustSee, false);
    }

    public TargetGoal(EntityCreature mob, boolean mustSee, boolean mustReach) {
        this.mob = mob;
        this.mustSee = mustSee;
        this.mustReach = mustReach;
        setFlags(EnumSet.of(Flag.TARGET));
        // vanilla 1.7.10 target tasks (OwnerHurtByTarget etc., same targetTasks list) use bit 1; share it so they exclude each other
        setMutexBits(1);
    }

    @Override
    public boolean canContinueToUse() {
        EntityLivingBase target = mob.getAttackTarget();
        if (target == null) target = targetMob;
        if (target == null) return false;
        if (!Targeting.canAttack(mob, target)) return false;
        if (mob.isOnSameTeam(target)) return false;
        double range = getFollowDistance();
        if (mob.getDistanceSqToEntity(target) > range * range) return false;
        if (mustSee) {
            if (mob.getEntitySenses().canSee(target)) unseenTicks = 0;
            // 1.20 checks every other tick and halves the memory; 1.7.10 checks every tick, so count full ticks
            else if (++unseenTicks > unseenMemoryTicks) return false;
        }
        mob.setAttackTarget(target);
        return true;
    }

    protected double getFollowDistance() {
        IAttributeInstance range = mob.getEntityAttribute(SharedMonsterAttributes.followRange);
        return range == null ? 16.0 : range.getAttributeValue();
    }

    @Override
    public void start() {
        reachCache = 0;
        reachCacheTime = 0;
        unseenTicks = 0;
    }

    @Override
    public void stop() {
        mob.setAttackTarget(null);
        targetMob = null;
    }

    /** 1.20 TargetGoal.canAttack: the targeting conditions plus home restriction and, if asked, reachability. */
    protected boolean canAttack(EntityLivingBase target, Targeting.Conditions conditions) {
        if (target == null || !conditions.test(mob, target)) return false;
        if (!mob.isWithinHomeDistance(MathHelper.floor_double(target.posX), MathHelper.floor_double(target.posY),
            MathHelper.floor_double(target.posZ))) return false;
        if (mustReach) {
            if (--reachCacheTime <= 0) reachCache = 0;
            if (reachCache == 0) reachCache = canReach(target) ? 1 : 2;
            if (reachCache == 2) return false;
        }
        return true;
    }

    private boolean canReach(EntityLivingBase target) {
        reachCacheTime = reducedTickDelay(10 + mob.getRNG().nextInt(5));
        PathEntity path = mob.getNavigator().getPathToEntityLiving(target);
        if (path == null) return false;
        PathPoint end = path.getFinalPathPoint();
        if (end == null) return false;
        int dx = end.xCoord - MathHelper.floor_double(target.posX);
        int dz = end.zCoord - MathHelper.floor_double(target.posZ);
        return dx * dx + dz * dz <= 2.25;
    }

    public TargetGoal setUnseenMemoryTicks(int ticks) {
        unseenMemoryTicks = ticks;
        return this;
    }
}
