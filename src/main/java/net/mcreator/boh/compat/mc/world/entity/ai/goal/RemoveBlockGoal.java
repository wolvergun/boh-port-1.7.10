package net.mcreator.boh.compat.mc.world.entity.ai.goal;

import java.util.EnumSet;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityCreature;

public class RemoveBlockGoal extends Goal {
    private final Block blockToRemove;
    protected final EntityCreature mob;
    private final double speed;
    private final int verticalRange;
    private int tx;
    private int ty;
    private int tz;
    private boolean hasTarget;
    private int ticksSinceReached;

    public RemoveBlockGoal(Block block, EntityCreature mob, double speed, int verticalRange) {
        this.blockToRemove = block;
        this.mob = mob;
        this.speed = speed;
        this.verticalRange = verticalRange;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.JUMP));
    }

    @Override
    public boolean canUse() {
        if (this.mob.getRNG().nextInt(reducedTickDelay(20)) != 0) {
            return false;
        } else {
            int bx = (int)Math.floor(this.mob.posX);
            int by = (int)Math.floor(this.mob.posY);
            int bz = (int)Math.floor(this.mob.posZ);

            for (int dy = -this.verticalRange; dy <= this.verticalRange; dy++) {
                for (int dx = -8; dx <= 8; dx++) {
                    for (int dz = -8; dz <= 8; dz++) {
                        if (this.mob.worldObj.getBlock(bx + dx, by + dy, bz + dz) == this.blockToRemove) {
                            this.tx = bx + dx;
                            this.ty = by + dy;
                            this.tz = bz + dz;
                            this.hasTarget = true;
                            return true;
                        }
                    }
                }
            }

            return false;
        }
    }

    @Override
    public boolean canContinueToUse() {
        return this.hasTarget && this.mob.worldObj.getBlock(this.tx, this.ty, this.tz) == this.blockToRemove && this.ticksSinceReached < 60;
    }

    @Override
    public void start() {
        this.ticksSinceReached = 0;
        this.mob.getNavigator().tryMoveToXYZ(this.tx + 0.5, this.ty, this.tz + 0.5, this.speed);
    }

    @Override
    public void stop() {
        this.hasTarget = false;
    }

    @Override
    public void tick() {
        if (this.mob.getDistanceSq(this.tx + 0.5, this.ty, this.tz + 0.5) < 4.0) {
            this.ticksSinceReached++;
            if (this.ticksSinceReached > 20) {
                this.mob.worldObj.func_147480_a(this.tx, this.ty, this.tz, false);
                this.hasTarget = false;
            }
        } else if (this.mob.getNavigator().noPath()) {
            this.mob.getNavigator().tryMoveToXYZ(this.tx + 0.5, this.ty, this.tz + 0.5, this.speed);
        }
    }
}
