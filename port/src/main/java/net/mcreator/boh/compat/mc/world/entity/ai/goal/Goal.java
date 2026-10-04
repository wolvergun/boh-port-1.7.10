package net.mcreator.boh.compat.mc.world.entity.ai.goal;

import java.util.EnumSet;

import net.minecraft.entity.ai.EntityAIBase;

/** 1.20 Goal on top of {@link EntityAIBase}, keeping the modern method names so subclasses override them. */
public abstract class Goal extends EntityAIBase {

    private final EnumSet<Flag> flags = EnumSet.noneOf(Flag.class);

    public abstract boolean canUse();

    public boolean canContinueToUse() {
        return canUse();
    }

    public boolean isInterruptable() {
        return true;
    }

    public void start() {}

    public void stop() {}

    public boolean requiresUpdateEveryTick() {
        return false;
    }

    public void tick() {}

    public void setFlags(EnumSet<Flag> newFlags) {
        flags.clear();
        flags.addAll(newFlags);
        int bits = 0;
        for (Flag f : flags) bits |= f.bit;
        setMutexBits(bits);
    }

    public EnumSet<Flag> getFlags() {
        return flags;
    }

    protected int adjustedTickDelay(int ticks) {
        return requiresUpdateEveryTick() ? ticks : (ticks + 1) / 2;
    }

    protected static int reducedTickDelay(int ticks) {
        return (ticks + 1) / 2;
    }

    // ---- 1.7.10 bridge

    @Override
    public final boolean shouldExecute() {
        return canUse();
    }

    @Override
    public final boolean continueExecuting() {
        return canContinueToUse();
    }

    @Override
    public final boolean isInterruptible() {
        return isInterruptable();
    }

    @Override
    public final void startExecuting() {
        start();
    }

    @Override
    public final void resetTask() {
        stop();
    }

    @Override
    public final void updateTask() {
        tick();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
