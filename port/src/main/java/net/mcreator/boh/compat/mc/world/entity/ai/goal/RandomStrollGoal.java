package net.mcreator.boh.compat.mc.world.entity.ai.goal;

import java.util.EnumSet;

import net.mcreator.boh.compat.mc.world.phys.Vec3;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.RandomPositionGenerator;

/** Port of the 1.20.1 RandomStrollGoal; subclasses may override {@link #getPosition()}. */
public class RandomStrollGoal extends Goal {

    protected final EntityCreature mob;
    protected double wantedX, wantedY, wantedZ;
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
        setFlags(EnumSet.of(Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        if (mob.riddenByEntity != null) return false;
        if (!forceTrigger) {
            if (checkNoActionTime && mob.getAge() >= 100) return false;
            if (mob.getRNG().nextInt(reducedTickDelay(interval)) != 0) return false;
        }
        Vec3 pos = getPosition();
        if (pos == null) return false;
        wantedX = pos.x;
        wantedY = pos.y;
        wantedZ = pos.z;
        forceTrigger = false;
        return true;
    }

    protected Vec3 getPosition() {
        return Vec3.of(RandomPositionGenerator.findRandomTarget(mob, 10, 7));
    }

    @Override
    public boolean canContinueToUse() {
        return !mob.getNavigator().noPath() && mob.riddenByEntity == null;
    }

    @Override
    public void start() {
        mob.getNavigator().tryMoveToXYZ(wantedX, wantedY, wantedZ, speedModifier);
    }

    @Override
    public void stop() {
        mob.getNavigator().clearPathEntity();
    }

    public void trigger() {
        forceTrigger = true;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }
}
