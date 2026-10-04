package net.mcreator.boh.procedures;

import net.mcreator.boh.entity.VitaMimicEntity;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.mcreator.boh.compat.mc.world.level.LightLayer;
import net.mcreator.boh.compat.M;

public class VitaMimicOnEntityTickUpdateProcedure {

    public static void execute(World world, double x, double y, double z, Entity entity) {
        if (entity != null) {
            if (M.getBrightness(world, LightLayer.BLOCK, BlockPos.containing(x, y, z)) <= 8 ^ (!(world instanceof World _lvl1 && M.isDay(_lvl1)) && M.canSeeSkyFromBelowWater(world, BlockPos.containing(x, y, z)))) {
                if (entity instanceof VitaMimicEntity animatable) {
                    animatable.setTexture("vita_mimic_translucent");
                }
            } else if (entity instanceof VitaMimicEntity animatable) {
                animatable.setTexture("vita_mimic");
            }
        }
    }
}
