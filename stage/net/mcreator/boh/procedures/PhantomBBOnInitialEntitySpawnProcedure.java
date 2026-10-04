package net.mcreator.boh.procedures;

import net.mcreator.boh.BohMod;
import net.minecraft.util.ResourceLocation;
import net.mcreator.boh.compat.mc.sounds.SoundEvent;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.M;

public class PhantomBBOnInitialEntitySpawnProcedure {

    public static void execute(World world, double x, double y, double z, Entity entity) {
        if (entity != null) {
            BohMod.queueServerWork(200, () -> {
                if (!M.isClientSide(M.level(entity))) {
                    M.discard(entity);
                }
                if (world instanceof World _level && M.isClientSide(_level)) {
                    M.playLocalSound(_level, x, y, z, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:phantom_bb_spawn")), SoundSource.HOSTILE, 1.0F, 1.0F, false);
                }
            });
        }
    }
}
