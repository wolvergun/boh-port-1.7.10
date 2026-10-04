package net.mcreator.boh.procedures;

import net.mcreator.boh.BohMod;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.registries.ForgeRegistries;

public class Specimen9BossOnInitialEntitySpawnProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
      if (entity != null) {
         if (world instanceof Level _level) {
            if (!_level.isClientSide()) {
               _level.playSound(
                  null,
                  BlockPos.containing(x, y, z),
                  (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:specimen_9_boss")),
                  SoundSource.MUSIC,
                  1.0F,
                  1.0F
               );
            } else {
               _level.playLocalSound(
                  x, y, z, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:specimen_9_boss")), SoundSource.MUSIC, 1.0F, 1.0F, false
               );
            }
         }

         BohMod.queueServerWork(2, () -> {
            Entity _ent = entity;
            _ent.teleportTo(x, y + 6.0, z);
            if (_ent instanceof ServerPlayer _serverPlayer) {
               _serverPlayer.connection.teleport(x, y + 6.0, z, _ent.getYRot(), _ent.getXRot());
            }
         });
      }
   }
}
