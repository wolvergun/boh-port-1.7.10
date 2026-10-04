package net.mcreator.boh.compat.mc.world.entity.ai.goal;

import java.util.EnumSet;

import net.mcreator.boh.compat.entity.BohMob;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.pathfinding.PathEntity;

/** Port of the 1.20.1 MeleeAttackGoal on the 1.7.10 navigator. */
public class MeleeAttackGoal extends Goal {

    protected final EntityCreature mob;
    private final double speedModifier;
    private final boolean followingTargetEvenIfNotSeen;
    private PathEntity path;
    private double pathedTargetX, pathedTargetY, pathedTargetZ;
    private int ticksUntilNextPathRecalculation;
    private int ticksUntilNextAttack;
    private long lastCanUseCheck;
    private int failedPathFindingPenalty;

    public MeleeAttackGoal(EntityCreature mob, double speed, boolean followingTargetEvenIfNotSeen) {
        this.mob = mob;
        this.speedModifier = speed;
        this.followingTargetEvenIfNotSeen = followingTargetEvenIfNotSeen;
        setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        long time = mob.worldObj.getTotalWorldTime();
        if (time - lastCanUseCheck < 20L) return false;
        lastCanUseCheck = time;
        EntityLivingBase target = mob.getAttackTarget();
        if (target == null || !target.isEntityAlive()) return false;
        path = mob.getNavigator().getPathToEntityLiving(target);
        if (path != null) return true;
        return getAttackReachSqr(target) >= mob.getDistanceSq(target.posX, target.boundingBox.minY, target.posZ);
    }

    @Override
    public boolean canContinueToUse() {
        EntityLivingBase target = mob.getAttackTarget();
        if (target == null || !target.isEntityAlive()) return false;
        if (!followingTargetEvenIfNotSeen) return !mob.getNavigator().noPath();
        if (!mob.isWithinHomeDistance((int) Math.floor(target.posX), (int) Math.floor(target.posY), (int) Math.floor(target.posZ)))
            return false;
        return !(target instanceof EntityPlayer) || !((EntityPlayer) target).capabilities.isCreativeMode;
    }

    @Override
    public void start() {
        mob.getNavigator().setPath(path, speedModifier);
        BohMob.setAggressive(mob, true);
        ticksUntilNextPathRecalculation = 0;
        ticksUntilNextAttack = 0;
    }

    @Override
    public void stop() {
        EntityLivingBase target = mob.getAttackTarget();
        if (target instanceof EntityPlayer && ((EntityPlayer) target).capabilities.isCreativeMode) mob.setAttackTarget(null);
        BohMob.setAggressive(mob, false);
        mob.getNavigator().clearPathEntity();
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    @Override
    public void tick() {
        EntityLivingBase target = mob.getAttackTarget();
        if (target == null) return;
        mob.getLookHelper().setLookPositionWithEntity(target, 30.0F, 30.0F);
        double distSqr = mob.getDistanceSq(target.posX, target.boundingBox.minY, target.posZ);
        ticksUntilNextPathRecalculation = Math.max(ticksUntilNextPathRecalculation - 1, 0);
        if ((followingTargetEvenIfNotSeen || mob.getEntitySenses().canSee(target)) && ticksUntilNextPathRecalculation <= 0
            && (pathedTargetX == 0 && pathedTargetY == 0 && pathedTargetZ == 0
                || target.getDistanceSq(pathedTargetX, pathedTargetY, pathedTargetZ) >= 1.0
                || mob.getRNG().nextFloat() < 0.05F)) {
            pathedTargetX = target.posX;
            pathedTargetY = target.boundingBox.minY;
            pathedTargetZ = target.posZ;
            ticksUntilNextPathRecalculation = 4 + mob.getRNG().nextInt(7);
            if (distSqr > 1024.0) ticksUntilNextPathRecalculation += 10;
            else if (distSqr > 256.0) ticksUntilNextPathRecalculation += 5;
            if (!mob.getNavigator().tryMoveToEntityLiving(target, speedModifier)) ticksUntilNextPathRecalculation += 15;
            ticksUntilNextPathRecalculation = adjustedTickDelay(ticksUntilNextPathRecalculation);
        }
        ticksUntilNextAttack = Math.max(ticksUntilNextAttack - 1, 0);
        checkAndPerformAttack(target, distSqr);
    }

    protected void checkAndPerformAttack(EntityLivingBase target, double distSqr) {
        if (distSqr <= getAttackReachSqr(target) && ticksUntilNextAttack <= 0) {
            resetAttackCooldown();
            mob.swingItem();
            mob.attackEntityAsMob(target);
        }
    }

    protected void resetAttackCooldown() {
        ticksUntilNextAttack = adjustedTickDelay(20);
    }

    protected boolean isTimeToAttack() {
        return ticksUntilNextAttack <= 0;
    }

    protected int getTicksUntilNextAttack() {
        return ticksUntilNextAttack;
    }

    protected int getAttackInterval() {
        return adjustedTickDelay(20);
    }

    protected double getAttackReachSqr(EntityLivingBase target) {
        return mob.width * 2.0F * mob.width * 2.0F + target.width;
    }
}
