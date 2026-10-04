package net.mcreator.boh.compat.mc.world.entity.ai.goal;

import java.util.EnumSet;
import net.mcreator.boh.compat.entity.BohMob;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.pathfinding.PathEntity;

public class MeleeAttackGoal extends Goal {
    protected final EntityCreature mob;
    private final double speedModifier;
    private final boolean followingTargetEvenIfNotSeen;
    private PathEntity path;
    private double pathedTargetX;
    private double pathedTargetY;
    private double pathedTargetZ;
    private int ticksUntilNextPathRecalculation;
    private int ticksUntilNextAttack;
    private long lastCanUseCheck;
    private int failedPathFindingPenalty;

    public MeleeAttackGoal(EntityCreature mob, double speed, boolean followingTargetEvenIfNotSeen) {
        this.mob = mob;
        this.speedModifier = speed;
        this.followingTargetEvenIfNotSeen = followingTargetEvenIfNotSeen;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        long time = this.mob.worldObj.getTotalWorldTime();
        if (time - this.lastCanUseCheck < 20L) {
            return false;
        } else {
            this.lastCanUseCheck = time;
            EntityLivingBase target = this.mob.getAttackTarget();
            if (target != null && target.isEntityAlive()) {
                this.path = this.mob.getNavigator().getPathToEntityLiving(target);
                return this.path != null
                    ? true
                    : this.getAttackReachSqr(target) >= this.mob.getDistanceSq(target.posX, target.boundingBox.minY, target.posZ);
            } else {
                return false;
            }
        }
    }

    @Override
    public boolean canContinueToUse() {
        EntityLivingBase target = this.mob.getAttackTarget();
        if (target == null || !target.isEntityAlive()) {
            return false;
        } else if (!this.followingTargetEvenIfNotSeen) {
            return !this.mob.getNavigator().noPath();
        } else {
            return !this.mob.isWithinHomeDistance((int)Math.floor(target.posX), (int)Math.floor(target.posY), (int)Math.floor(target.posZ))
                ? false
                : !(target instanceof EntityPlayer) || !((EntityPlayer)target).capabilities.isCreativeMode;
        }
    }

    @Override
    public void start() {
        this.mob.getNavigator().setPath(this.path, this.speedModifier);
        BohMob.setAggressive(this.mob, true);
        this.ticksUntilNextPathRecalculation = 0;
        this.ticksUntilNextAttack = 0;
    }

    @Override
    public void stop() {
        EntityLivingBase target = this.mob.getAttackTarget();
        if (target instanceof EntityPlayer && ((EntityPlayer)target).capabilities.isCreativeMode) {
            this.mob.setAttackTarget(null);
        }

        BohMob.setAggressive(this.mob, false);
        this.mob.getNavigator().clearPathEntity();
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    @Override
    public void tick() {
        EntityLivingBase target = this.mob.getAttackTarget();
        if (target != null) {
            this.mob.getLookHelper().setLookPositionWithEntity(target, 30.0F, 30.0F);
            double distSqr = this.mob.getDistanceSq(target.posX, target.boundingBox.minY, target.posZ);
            this.ticksUntilNextPathRecalculation = Math.max(this.ticksUntilNextPathRecalculation - 1, 0);
            if ((this.followingTargetEvenIfNotSeen || this.mob.getEntitySenses().canSee(target))
                && this.ticksUntilNextPathRecalculation <= 0
                && (
                    this.pathedTargetX == 0.0 && this.pathedTargetY == 0.0 && this.pathedTargetZ == 0.0
                        || target.getDistanceSq(this.pathedTargetX, this.pathedTargetY, this.pathedTargetZ) >= 1.0
                        || this.mob.getRNG().nextFloat() < 0.05F
                )) {
                this.pathedTargetX = target.posX;
                this.pathedTargetY = target.boundingBox.minY;
                this.pathedTargetZ = target.posZ;
                this.ticksUntilNextPathRecalculation = 4 + this.mob.getRNG().nextInt(7);
                if (distSqr > 1024.0) {
                    this.ticksUntilNextPathRecalculation += 10;
                } else if (distSqr > 256.0) {
                    this.ticksUntilNextPathRecalculation += 5;
                }

                if (!this.mob.getNavigator().tryMoveToEntityLiving(target, this.speedModifier)) {
                    this.ticksUntilNextPathRecalculation += 15;
                }

                this.ticksUntilNextPathRecalculation = this.adjustedTickDelay(this.ticksUntilNextPathRecalculation);
            }

            this.ticksUntilNextAttack = Math.max(this.ticksUntilNextAttack - 1, 0);
            this.checkAndPerformAttack(target, distSqr);
        }
    }

    protected void checkAndPerformAttack(EntityLivingBase target, double distSqr) {
        if (distSqr <= this.getAttackReachSqr(target) && this.ticksUntilNextAttack <= 0) {
            this.resetAttackCooldown();
            this.mob.swingItem();
            this.mob.attackEntityAsMob(target);
        }
    }

    protected void resetAttackCooldown() {
        this.ticksUntilNextAttack = this.adjustedTickDelay(20);
    }

    protected boolean isTimeToAttack() {
        return this.ticksUntilNextAttack <= 0;
    }

    protected int getTicksUntilNextAttack() {
        return this.ticksUntilNextAttack;
    }

    protected int getAttackInterval() {
        return this.adjustedTickDelay(20);
    }

    protected double getAttackReachSqr(EntityLivingBase target) {
        return this.mob.width * 2.0F * this.mob.width * 2.0F + target.width;
    }
}
