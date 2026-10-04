package net.mcreator.boh.compat.mc.world.entity.ai.goal;

import java.util.EnumSet;
import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.RandomPositionGenerator;

public class RandomStrollGoal extends Goal {
    protected final EntityCreature mob;
    protected double wantedX;
    protected double wantedY;
    protected double wantedZ;
    protected final double speedModifier;
    protected int interval;
    protected boolean forceTrigger;
    private final boolean checkNoActionTime;

    public RandomStrollGoal(EntityCreature mob, double speed) {
        this(mob, speed, 120);
    }

    public RandomStrollGoal(EntityCreature mob, double speed, int interval) {
        this(mob, speed, interval, true);
    }

    public RandomStrollGoal(EntityCreature mob, double speed, int interval, boolean checkNoActionTime) {
        this.mob = mob;
        this.speedModifier = speed;
        this.interval = interval;
        this.checkNoActionTime = checkNoActionTime;
        this.setFlags(EnumSet.of(Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        if (this.mob.riddenByEntity != null) {
            return false;
        } else {
            if (!this.forceTrigger) {
                if (this.checkNoActionTime && this.mob.getAge() >= 100) {
                    return false;
                }

                if (this.mob.getRNG().nextInt(reducedTickDelay(this.interval)) != 0) {
                    return false;
                }
            }

            Vec3 pos = this.getPosition();
            if (pos == null) {
                return false;
            } else {
                this.wantedX = pos.x;
                this.wantedY = pos.y;
                this.wantedZ = pos.z;
                this.forceTrigger = false;
                return true;
            }
        }
    }

    protected Vec3 getPosition() {
        return Vec3.of(RandomPositionGenerator.findRandomTarget(this.mob, 10, 7));
    }

    @Override
    public boolean canContinueToUse() {
        return !this.mob.getNavigator().noPath() && this.mob.riddenByEntity == null;
    }

    @Override
    public void start() {
        this.mob.getNavigator().tryMoveToXYZ(this.wantedX, this.wantedY, this.wantedZ, this.speedModifier);
    }

    @Override
    public void stop() {
        this.mob.getNavigator().clearPathEntity();
    }

    public void trigger() {
        this.forceTrigger = true;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }
}
