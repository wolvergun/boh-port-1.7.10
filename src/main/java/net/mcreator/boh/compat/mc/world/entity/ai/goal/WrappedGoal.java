package net.mcreator.boh.compat.mc.world.entity.ai.goal;

import net.minecraft.entity.ai.EntityAIBase;

public class WrappedGoal extends Goal {
    protected EntityAIBase delegate;

    public WrappedGoal(EntityAIBase delegate) {
        this.delegate = delegate;
        this.setMutexBits(delegate.getMutexBits());
    }

    @Override
    public boolean canUse() {
        return this.delegate.shouldExecute();
    }

    @Override
    public boolean canContinueToUse() {
        return this.delegate.continueExecuting();
    }

    @Override
    public boolean isInterruptable() {
        return this.delegate.isInterruptible();
    }

    @Override
    public void start() {
        this.delegate.startExecuting();
    }

    @Override
    public void stop() {
        this.delegate.resetTask();
    }

    @Override
    public void tick() {
        this.delegate.updateTask();
    }

    public EntityAIBase getGoal() {
        return this.delegate;
    }
}
