package net.mcreator.boh.procedures;

import net.mcreator.boh.compat.mc.core.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.mcreator.boh.compat.mc.sounds.SoundEvent;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.M;

public class ShapesGazeEffectStartedappliedProcedure {

    public static void execute(World world, double x, double y, double z, Entity entity) {
        if (entity != null) {
            M.putBoolean(M.getPersistentData(entity), "stinger_myers", true);
            if (!M.getBoolean(M.getPersistentData(entity), "stinger_myers_lock") && M.getBoolean(M.getPersistentData(entity), "stinger_myers")) {
                if (world instanceof World _level) {
                    if (!M.isClientSide(_level)) {
                        M.playSound(_level, null, BlockPos.containing(x, y, z), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:myers_charge")), SoundSource.MUSIC, 1.0F, 1.0F);
                    } else {
                        M.playLocalSound(_level, x, y, z, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:myers_charge")), SoundSource.MUSIC, 1.0F, 1.0F, false);
                    }
                }
                M.putBoolean(M.getPersistentData(entity), "stinger_myers_lock", true);
            }
        }
    }
}
