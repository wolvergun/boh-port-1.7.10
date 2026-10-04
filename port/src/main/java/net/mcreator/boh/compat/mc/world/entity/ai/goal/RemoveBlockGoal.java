package net.mcreator.boh.compat.mc.world.entity.ai.goal;

import java.util.EnumSet;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityCreature;

/** Simplified 1.20 RemoveBlockGoal: walk to a nearby block of the given type and break it. */
public class RemoveBlockGoal extends Goal {

    private final Block blockToRemove;
    protected final EntityCreature mob;
    private final double speed;
    private final int verticalRange;
    private int tx, ty, tz;
    private boolean hasTarget;
    private int ticksSinceReached;

    public RemoveBlockGoal(Block block, EntityCreature mob, double speed, int verticalRange) {
        this.blockToRemove = block;
        this.mob = mob;
        this.speed = speed;
        this.verticalRange = verticalRange;
        setFlags(EnumSet.of(Flag.MOVE, Flag.JUMP));
    }

    @Override
    public boolean canUse() {
        if (mob.getRNG().nextInt(reducedTickDelay(20)) != 0) return false;
        int bx = (int) Math.floor(mob.posX), by = (int) Math.floor(mob.posY), bz = (int) Math.floor(mob.posZ);
        for (int dy = -verticalRange; dy <= verticalRange; dy++) for (int dx = -8; dx <= 8; dx++) for (int dz = -8; dz <= 8; dz++) {
            if (mob.worldObj.getBlock(bx + dx, by + dy, bz + dz) == blockToRemove) {
                tx = bx + dx;
                ty = by + dy;
                tz = bz + dz;
                hasTarget = true;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean canContinueToUse() {
        return hasTarget && mob.worldObj.getBlock(tx, ty, tz) == blockToRemove && ticksSinceReached < 60;
    }

    @Override
    public void start() {
        ticksSinceReached = 0;
        mob.getNavigator().tryMoveToXYZ(tx + 0.5, ty, tz + 0.5, speed);
    }

    @Override
    public void stop() {
        hasTarget = false;
    }

    @Override
    public void tick() {
        if (mob.getDistanceSq(tx + 0.5, ty, tz + 0.5) < 4.0) {
            ticksSinceReached++;
            if (ticksSinceReached > 20) {
                mob.worldObj.func_147480_a(tx, ty, tz, false);
                hasTarget = false;
            }
        } else if (mob.getNavigator().noPath()) {
            mob.getNavigator().tryMoveToXYZ(tx + 0.5, ty, tz + 0.5, speed);
        }
    }
}
