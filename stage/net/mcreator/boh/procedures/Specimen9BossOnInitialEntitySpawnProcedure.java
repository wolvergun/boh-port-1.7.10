package net.mcreator.boh.procedures;

import net.mcreator.boh.BohMod;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.entity.player.EntityPlayerMP;
import net.mcreator.boh.compat.mc.sounds.SoundEvent;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.M;

public class Specimen9BossOnInitialEntitySpawnProcedure {

    public static void execute(World world, double x, double y, double z, Entity entity) {
        if (entity != null) {
            if (world instanceof World _level) {
                if (!M.isClientSide(_level)) {
                    M.playSound(_level, null, BlockPos.containing(x, y, z), (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:specimen_9_boss")), SoundSource.MUSIC, 1.0F, 1.0F);
                } else {
                    M.playLocalSound(_level, x, y, z, (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:specimen_9_boss")), SoundSource.MUSIC, 1.0F, 1.0F, false);
                }
            }
            BohMod.queueServerWork(2, () -> {
                Entity _ent = entity;
                M.teleportTo(_ent, x, y + 6.0, z);
                if (_ent instanceof EntityPlayerMP _serverPlayer) {
                    M.teleport(M.connection(_serverPlayer), x, y + 6.0, z, M.getYRot(_ent), M.getXRot(_ent));
                }
            });
        }
    }
}
