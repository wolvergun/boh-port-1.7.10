package net.mcreator.boh.procedures;

import net.mcreator.boh.BohMod;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.forge.registries.ForgeRegistries;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.sounds.SoundSource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class Specimen9BossOnInitialEntitySpawnProcedure {
    public static void execute(World world, double x, double y, double z, Entity entity) {
        if (entity != null) {
            if (world instanceof World) {
                if (!M.isClientSide(world)) {
                    M.playSound(
                        world,
                        null,
                        BlockPos.containing(x, y, z),
                        ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:specimen_9_boss")),
                        SoundSource.MUSIC,
                        1.0F,
                        1.0F
                    );
                } else {
                    M.playLocalSound(
                        world,
                        x,
                        y,
                        z,
                        ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:specimen_9_boss")),
                        SoundSource.MUSIC,
                        1.0F,
                        1.0F,
                        false
                    );
                }
            }

            BohMod.queueServerWork(2, () -> {
                M.teleportTo(entity, x, y + 6.0, z);
                if (entity instanceof EntityPlayerMP _serverPlayer) {
                    M.teleport(M.connection(_serverPlayer), x, y + 6.0, z, M.getYRot(entity), M.getXRot(entity));
                }
            });
        }
    }
}
