package net.mcreator.boh.procedures;

import net.mcreator.boh.BohMod;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.mcreator.boh.entity.PatrickBatemanEntity;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class PatrickBatemanOnInitialEntitySpawnProcedure {
    public static void execute(World world, double x, double y, double z, Entity entity) {
        if (entity != null) {
            BohMod.queueServerWork(1, () -> {
                if (Math.random() < 0.55) {
                    if (entity instanceof PatrickBatemanEntity animatable) {
                        animatable.setTexture("bateman");
                    }
                } else if (entity instanceof PatrickBatemanEntity animatable) {
                    animatable.setTexture("bateman_coat");
                }
            });
            if (Math.random() < 0.5 && world instanceof World && M.isClientSide(world)) {
                M.playLocalSound(
                    world, x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:bateman_spawn")), SoundSource.HOSTILE, 1.0F, 1.0F, false
                );
            }
        }
    }
}
