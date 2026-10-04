package net.mcreator.boh.compat.mc.world.entity.ai.goal;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIPanic;

public class PanicGoal extends WrappedGoal {
    protected final EntityCreature mob;
    protected final double speedModifier;

    public PanicGoal(EntityCreature mob, double speed) {
        super(new EntityAIPanic(mob, speed));
        this.mob = mob;
        this.speedModifier = speed;
    }
}
