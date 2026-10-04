package net.mcreator.boh.compat.mc.world.entity.ai.navigation;

import net.minecraft.entity.EntityLiving;
import net.minecraft.world.World;

public class WaterBoundPathNavigation extends PathNavigation {
    public WaterBoundPathNavigation(EntityLiving mob, World world) {
        super(mob, new FlyingNavigator(mob, world));
        this.nav.setCanSwim(true);
    }
}
