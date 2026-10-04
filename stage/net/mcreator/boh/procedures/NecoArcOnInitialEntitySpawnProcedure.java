package net.mcreator.boh.procedures;

import net.mcreator.boh.compat.mc.core.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.mcreator.boh.compat.mc.sounds.SoundEvent;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.minecraft.world.World;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.M;

public class NecoArcOnInitialEntitySpawnProcedure {

    public static void execute(World world, double x, double y, double z) {
        if (world instanceof World _level) {
            if (!M.isClientSide(_level)) {
                M.playSound(_level, null, BlockPos.containing(x, y, z), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:necoarc_spawn")), SoundSource.HOSTILE, 100.0F, 1.0F);
            } else {
                M.playLocalSound(_level, x, y, z, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:necoarc_spawn")), SoundSource.HOSTILE, 100.0F, 1.0F, false);
            }
        }
    }
}
