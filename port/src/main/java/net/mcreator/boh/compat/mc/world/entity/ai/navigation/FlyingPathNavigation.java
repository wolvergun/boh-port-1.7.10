package net.mcreator.boh.compat.mc.world.entity.ai.navigation;

import net.minecraft.entity.EntityLiving;
import net.minecraft.world.World;

/** 1.20 FlyingPathNavigation: direct 3D steering through {@link FlyingNavigator}. */
public class FlyingPathNavigation extends PathNavigation {

    public FlyingPathNavigation(EntityLiving mob, World world) {
        super(mob, new FlyingNavigator(mob, world));
    }
}
