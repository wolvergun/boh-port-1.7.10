package net.mcreator.boh.compat.mc.world.entity.ai.goal;

import java.util.function.Predicate;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIAvoidEntity;

public class AvoidEntityGoal<T> extends WrappedGoal {
    protected final EntityCreature mob;

    public AvoidEntityGoal(EntityCreature mob, Class<T> avoidClass, float maxDist, double walkSpeed, double sprintSpeed) {
        super(new EntityAIAvoidEntity(mob, avoidClass, maxDist, walkSpeed, sprintSpeed));
        this.mob = mob;
    }

    public AvoidEntityGoal(EntityCreature mob, Class<T> avoidClass, float maxDist, double walkSpeed, double sprintSpeed, Predicate<?> predicate) {
        this(mob, avoidClass, maxDist, walkSpeed, sprintSpeed);
    }
}
