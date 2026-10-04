package net.mcreator.boh.procedures;

import net.mcreator.boh.entity.LaughingJackEntity;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.mcreator.boh.compat.mc.sounds.SoundEvent;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.M;

public class LaughingJackOnEntityTickUpdateProcedure {

    public static void execute(World world, double x, double y, double z, Entity entity) {
        if (entity != null) {
            if (entity instanceof LaughingJackEntity _datEntSetI) {
                M.set(M.getEntityData(_datEntSetI), LaughingJackEntity.DATA_laughingjack_cooldown, (entity instanceof LaughingJackEntity _datEntI ? (Integer) M.getEntityData(_datEntI).get(LaughingJackEntity.DATA_laughingjack_cooldown) : 0) + 1);
            }
            if ((entity instanceof LaughingJackEntity _datEntI ? (Integer) M.getEntityData(_datEntI).get(LaughingJackEntity.DATA_laughingjack_cooldown) : 0) == 1 && world instanceof World _level) {
                if (!M.isClientSide(_level)) {
                    M.playSound(_level, null, BlockPos.containing(x, y, z), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:laghingjack_ambience")), SoundSource.HOSTILE, 1.0F, 1.0F);
                } else {
                    M.playLocalSound(_level, x, y, z, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:laghingjack_ambience")), SoundSource.HOSTILE, 1.0F, 1.0F, false);
                }
            }
            if ((entity instanceof LaughingJackEntity _datEntI ? (Integer) M.getEntityData(_datEntI).get(LaughingJackEntity.DATA_laughingjack_cooldown) : 0) == 709 && entity instanceof LaughingJackEntity _datEntSetI) {
                M.set(M.getEntityData(_datEntSetI), LaughingJackEntity.DATA_laughingjack_cooldown, 0);
            }
        }
    }
}
