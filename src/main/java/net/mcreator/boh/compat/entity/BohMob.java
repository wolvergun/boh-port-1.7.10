package net.mcreator.boh.compat.entity;

import net.mcreator.boh.compat.mc.network.syncher.SynchedEntityData;
import net.mcreator.boh.compat.mc.world.entity.EntityType;
import net.mcreator.boh.compat.mc.world.entity.ai.control.LookControl;
import net.mcreator.boh.compat.mc.world.entity.ai.control.MoveControl;
import net.mcreator.boh.compat.mc.world.entity.ai.goal.GoalSelector;
import net.mcreator.boh.compat.mc.world.entity.ai.navigation.PathNavigation;
import net.minecraft.entity.Entity;

public interface BohMob {
    EntityType<?> bohType();

    SynchedEntityData bohEntityData();

    GoalSelector bohGoals();

    GoalSelector bohTargets();

    MoveControl bohMoveControl();

    LookControl bohLookControl();

    PathNavigation bohNavigation();

    boolean isAggressive();

    void setAggressive(boolean var1);

    boolean isNoAi();

    void setNoAi(boolean var1);

    boolean isNoGravity();

    void setNoGravity(boolean var1);

    void refreshDimensions();

    void dropExperience();

    void setSizeCompat(float var1, float var2);

    static void setAggressive(Entity e, boolean b) {
        if (e instanceof BohMob) {
            ((BohMob)e).setAggressive(b);
        }
    }

    static void setNoGravity(Entity e, boolean b) {
        if (e instanceof BohMob) {
            ((BohMob)e).setNoGravity(b);
        }
    }

    static boolean isNoGravity(Entity e) {
        return e instanceof BohMob && ((BohMob)e).isNoGravity();
    }
}
