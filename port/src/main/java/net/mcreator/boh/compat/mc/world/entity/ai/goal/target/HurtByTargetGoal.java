package net.mcreator.boh.compat.mc.world.entity.ai.goal.target;

import net.mcreator.boh.compat.mc.world.entity.ai.goal.WrappedGoal;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIHurtByTarget;

/** 1.20 HurtByTargetGoal; setAlertOthers() switches the vanilla task to call for help. */
public class HurtByTargetGoal extends WrappedGoal {

    protected final EntityCreature mob;

    public HurtByTargetGoal(EntityCreature mob, Class<?>... toIgnoreDamage) {
        super(new EntityAIHurtByTarget(mob, false));
        this.mob = mob;
    }

    public HurtByTargetGoal setAlertOthers(Class<?>... reinforcementTypes) {
        delegate = new EntityAIHurtByTarget(mob, true);
        setMutexBits(delegate.getMutexBits());
        return this;
    }
}
