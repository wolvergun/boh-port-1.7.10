package net.mcreator.boh.compat.mc.world.entity.ai.goal;

import net.minecraft.entity.ai.EntityAIBase;

/** Goal that forwards to a vanilla 1.7.10 AI task. */
public class WrappedGoal extends Goal {

    protected EntityAIBase delegate;

    public WrappedGoal(EntityAIBase delegate) {
        this.delegate = delegate;
        setMutexBits(delegate.getMutexBits());
    }

    @Override
    public boolean canUse() {
        return delegate.shouldExecute();
    }

    @Override
    public boolean canContinueToUse() {
        return delegate.continueExecuting();
    }

    @Override
    public boolean isInterruptable() {
        return delegate.isInterruptible();
    }

    @Override
    public void start() {
        delegate.startExecuting();
    }

    @Override
    public void stop() {
        delegate.resetTask();
    }

    @Override
    public void tick() {
        delegate.updateTask();
    }

    public EntityAIBase getGoal() {
        return delegate;
    }
}
