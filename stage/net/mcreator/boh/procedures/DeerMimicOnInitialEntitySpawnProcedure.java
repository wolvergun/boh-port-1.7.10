package net.mcreator.boh.procedures;

import net.mcreator.boh.BohMod;
import net.mcreator.boh.entity.DeerMimicEntity;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class DeerMimicOnInitialEntitySpawnProcedure {

    public static void execute(World world, Entity entity) {
        if (entity != null) {
            if (Math.random() < 0.5) {
                BohMod.queueServerWork(2, () -> {
                    if (entity instanceof DeerMimicEntity animatable) {
                        animatable.setTexture("doe");
                    }
                });
            }
        }
    }
}
