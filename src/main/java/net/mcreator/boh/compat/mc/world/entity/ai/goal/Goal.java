package net.mcreator.boh.compat.mc.world.entity.ai.goal;

import java.util.EnumSet;
import net.minecraft.entity.ai.EntityAIBase;

public abstract class Goal extends EntityAIBase {
    private final EnumSet<Flag> flags = EnumSet.noneOf(Flag.class);

    public abstract boolean canUse();

    public boolean canContinueToUse() {
        return this.canUse();
    }

    public boolean isInterruptable() {
        return true;
    }

    public void start() {
    }

    public void stop() {
    }

    public boolean requiresUpdateEveryTick() {
        return false;
    }

    public void tick() {
    }

    public void setFlags(EnumSet<Flag> newFlags) {
        this.flags.clear();
        this.flags.addAll(newFlags);
        int bits = 0;

        for (Flag f : this.flags) {
            bits |= f.bit;
        }

        this.setMutexBits(bits);
    }

    public EnumSet<Flag> getFlags() {
        return this.flags;
    }

    protected int adjustedTickDelay(int ticks) {
        return this.requiresUpdateEveryTick() ? ticks : (ticks + 1) / 2;
    }

    protected static int reducedTickDelay(int ticks) {
        return (ticks + 1) / 2;
    }

    public final boolean shouldExecute() {
        return this.canUse();
    }

    public final boolean continueExecuting() {
        return this.canContinueToUse();
    }

    public final boolean isInterruptible() {
        return this.isInterruptable();
    }

    public final void startExecuting() {
        this.start();
    }

    public final void resetTask() {
        this.stop();
    }

    public final void updateTask() {
        this.tick();
    }

    public String toString() {
        return this.getClass().getSimpleName();
    }
}
