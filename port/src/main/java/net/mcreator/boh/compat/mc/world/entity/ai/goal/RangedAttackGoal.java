package net.mcreator.boh.compat.mc.world.entity.ai.goal;

import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.ai.EntityAIArrowAttack;

public class RangedAttackGoal extends WrappedGoal {

    public RangedAttackGoal(IRangedAttackMob mob, double speed, int interval, float radius) {
        super(new EntityAIArrowAttack(mob, speed, interval, interval, radius));
    }

    public RangedAttackGoal(IRangedAttackMob mob, double speed, int minInterval, int maxInterval, float radius) {
        super(new EntityAIArrowAttack(mob, speed, minInterval, maxInterval, radius));
    }
}
