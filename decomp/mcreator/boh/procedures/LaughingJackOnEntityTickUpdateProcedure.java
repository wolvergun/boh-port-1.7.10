package net.mcreator.boh.procedures;

import net.mcreator.boh.entity.LaughingJackEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.registries.ForgeRegistries;

public class LaughingJackOnEntityTickUpdateProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
      if (entity != null) {
         if (entity instanceof LaughingJackEntity _datEntSetI) {
            _datEntSetI.getEntityData()
               .set(
                  LaughingJackEntity.DATA_laughingjack_cooldown,
                  (entity instanceof LaughingJackEntity _datEntI ? (Integer)_datEntI.getEntityData().get(LaughingJackEntity.DATA_laughingjack_cooldown) : 0)
                     + 1
               );
         }

         if ((entity instanceof LaughingJackEntity _datEntI ? (Integer)_datEntI.getEntityData().get(LaughingJackEntity.DATA_laughingjack_cooldown) : 0) == 1
            && world instanceof Level _level) {
            if (!_level.isClientSide()) {
               _level.playSound(
                  null,
                  BlockPos.containing(x, y, z),
                  (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:laghingjack_ambience")),
                  SoundSource.HOSTILE,
                  1.0F,
                  1.0F
               );
            } else {
               _level.playLocalSound(
                  x,
                  y,
                  z,
                  (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:laghingjack_ambience")),
                  SoundSource.HOSTILE,
                  1.0F,
                  1.0F,
                  false
               );
            }
         }

         if ((entity instanceof LaughingJackEntity _datEntI ? (Integer)_datEntI.getEntityData().get(LaughingJackEntity.DATA_laughingjack_cooldown) : 0) == 709
            && entity instanceof LaughingJackEntity _datEntSetI) {
            _datEntSetI.getEntityData().set(LaughingJackEntity.DATA_laughingjack_cooldown, 0);
         }
      }
   }
}
