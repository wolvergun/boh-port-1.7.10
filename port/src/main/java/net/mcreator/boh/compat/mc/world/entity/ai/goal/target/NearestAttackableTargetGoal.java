package net.mcreator.boh.compat.mc.world.entity.ai.goal.target;

import java.util.function.Predicate;

import net.mcreator.boh.compat.mc.world.entity.ai.goal.WrappedGoal;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;

public class NearestAttackableTargetGoal<T> extends WrappedGoal {

    protected final EntityCreature mob;
    protected final Class<T> targetType;

    public NearestAttackableTargetGoal(EntityCreature mob, Class<T> targetType, boolean mustSee) {
        this(mob, targetType, 10, mustSee, false, null);
    }

    public NearestAttackableTargetGoal(EntityCreature mob, Class<T> targetType, boolean mustSee, boolean mustReach) {
        this(mob, targetType, 10, mustSee, mustReach, null);
    }

    public NearestAttackableTargetGoal(EntityCreature mob, Class<T> targetType, boolean mustSee,
        Predicate<EntityLivingBase> predicate) {
        this(mob, targetType, 10, mustSee, false, predicate);
    }

    public NearestAttackableTargetGoal(EntityCreature mob, Class<T> targetType, int randomInterval, boolean mustSee,
        boolean mustReach, Predicate<EntityLivingBase> predicate) {
        super(new EntityAINearestAttackableTarget(mob, (Class) targetType, randomInterval, mustSee, mustReach,
            predicate == null ? null : (Entity e) -> e instanceof EntityLivingBase && predicate.test((EntityLivingBase) e)));
        this.mob = mob;
        this.targetType = targetType;
    }
}
