package net.mcreator.boh.procedures;

import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class SawrunnerOnEntityTickUpdateProcedure {
    public static void execute(World world, double x, double y, double z, Entity entity) {
        if (entity != null) {
            M.putDouble(M.getPersistentData(entity), "ambincesaw", M.getDouble(M.getPersistentData(entity), "ambincesaw") + 1.0);
            if (M.getDouble(M.getPersistentData(entity), "ambincesaw") == 160.0) {
                if (world instanceof World) {
                    if (!M.isClientSide(world)) {
                        M.playSound(
                            world,
                            null,
                            BlockPos.containing(x, y, z),
                            ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:sawrunner_ambient")),
                            SoundSource.HOSTILE,
                            1.0F,
                            1.0F
                        );
                    } else {
                        M.playLocalSound(
                            world,
                            x,
                            y,
                            z,
                            ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:sawrunner_ambient")),
                            SoundSource.HOSTILE,
                            1.0F,
                            1.0F,
                            false
                        );
                    }
                }

                M.putDouble(M.getPersistentData(entity), "ambincesaw", 0.0);
            }
        }
    }
}
