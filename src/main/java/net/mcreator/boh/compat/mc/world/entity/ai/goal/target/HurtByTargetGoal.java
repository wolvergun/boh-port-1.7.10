package net.mcreator.boh.compat.mc.world.entity.ai.goal.target;

import net.mcreator.boh.compat.mc.world.entity.ai.goal.WrappedGoal;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIHurtByTarget;

public class HurtByTargetGoal extends WrappedGoal {
    protected final EntityCreature mob;

    public HurtByTargetGoal(EntityCreature mob, Class<?>... toIgnoreDamage) {
        super(new EntityAIHurtByTarget(mob, false));
        this.mob = mob;
    }

    public HurtByTargetGoal setAlertOthers(Class<?>... reinforcementTypes) {
        this.delegate = new EntityAIHurtByTarget(this.mob, true);
        this.setMutexBits(this.delegate.getMutexBits());
        return this;
    }
}
