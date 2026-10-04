package net.mcreator.boh.compat.entity;

import net.mcreator.boh.compat.mc.network.syncher.SynchedEntityData;
import net.mcreator.boh.compat.mc.world.entity.EntityType;
import net.mcreator.boh.compat.mc.world.entity.ai.control.LookControl;
import net.mcreator.boh.compat.mc.world.entity.ai.control.MoveControl;
import net.mcreator.boh.compat.mc.world.entity.ai.goal.GoalSelector;
import net.mcreator.boh.compat.mc.world.entity.ai.navigation.PathNavigation;
import net.minecraft.entity.Entity;

/** Shared surface of the compat base mobs, used by the static helpers in M. */
public interface BohMob {

    EntityType<?> bohType();

    SynchedEntityData bohEntityData();

    GoalSelector bohGoals();

    GoalSelector bohTargets();

    MoveControl bohMoveControl();

    LookControl bohLookControl();

    PathNavigation bohNavigation();

    boolean isAggressive();

    void setAggressive(boolean aggressive);

    boolean isNoAi();

    void setNoAi(boolean noAi);

    boolean isNoGravity();

    void setNoGravity(boolean noGravity);

    void refreshDimensions();

    void dropExperience();

    /** Public access to the protected Entity.setSize for M.setSize. */
    void setSizeCompat(float width, float height);

    static void setAggressive(Entity e, boolean b) {
        if (e instanceof BohMob) ((BohMob) e).setAggressive(b);
    }

    static void setNoGravity(Entity e, boolean b) {
        if (e instanceof BohMob) ((BohMob) e).setNoGravity(b);
    }

    static boolean isNoGravity(Entity e) {
        return e instanceof BohMob && ((BohMob) e).isNoGravity();
    }
}
