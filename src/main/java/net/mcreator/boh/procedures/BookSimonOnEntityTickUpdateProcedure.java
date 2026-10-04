package net.mcreator.boh.procedures;

import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class BookSimonOnEntityTickUpdateProcedure {
    public static void execute(World world, double x, double y, double z, Entity entity) {
        if (entity != null) {
            M.putDouble(M.getPersistentData(entity), "tick_music", M.getDouble(M.getPersistentData(entity), "tick_music") + 1.0);
            if (M.getDouble(M.getPersistentData(entity), "tick_music") == 2160.0) {
                if (world instanceof World) {
                    if (!M.isClientSide(world)) {
                        M.playSound(
                            world,
                            null,
                            BlockPos.containing(x, y, z),
                            ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:simon_ost")),
                            SoundSource.MUSIC,
                            1.0F,
                            1.0F
                        );
                    } else {
                        M.playLocalSound(
                            world, x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:simon_ost")), SoundSource.MUSIC, 1.0F, 1.0F, false
                        );
                    }
                }

                M.putDouble(M.getPersistentData(entity), "tick_music", 0.0);
            }
        }
    }
}
