package net.mcreator.boh.compat.mc.world.entity.ai.navigation;

import net.minecraft.entity.EntityLiving;
import net.minecraft.world.World;

public class FlyingPathNavigation extends PathNavigation {
    public FlyingPathNavigation(EntityLiving mob, World world) {
        super(mob, new FlyingNavigator(mob, world));
    }
}
