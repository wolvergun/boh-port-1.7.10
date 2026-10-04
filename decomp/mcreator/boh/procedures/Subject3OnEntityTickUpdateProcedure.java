package net.mcreator.boh.procedures;

import net.mcreator.boh.entity.Subject3Entity;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.registries.ForgeRegistries;

public class Subject3OnEntityTickUpdateProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
      if (entity != null) {
         if (entity instanceof Subject3Entity _datEntSetI) {
            _datEntSetI.getEntityData()
               .set(
                  Subject3Entity.DATA_subject_3_cooldown,
                  (entity instanceof Subject3Entity _datEntI ? (Integer)_datEntI.getEntityData().get(Subject3Entity.DATA_subject_3_cooldown) : 0) + 1
               );
         }

         if ((entity instanceof Subject3Entity _datEntI ? (Integer)_datEntI.getEntityData().get(Subject3Entity.DATA_subject_3_cooldown) : 0) == 1
            && world instanceof Level _level) {
            if (!_level.isClientSide()) {
               _level.playSound(
                  null,
                  BlockPos.containing(x, y, z),
                  (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:subject3_chase")),
                  SoundSource.HOSTILE,
                  1.0F,
                  1.0F
               );
            } else {
               _level.playLocalSound(
                  x,
                  y,
                  z,
                  (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:subject3_chase")),
                  SoundSource.HOSTILE,
                  1.0F,
                  1.0F,
                  false
               );
            }
         }

         if ((entity instanceof Subject3Entity _datEntI ? (Integer)_datEntI.getEntityData().get(Subject3Entity.DATA_subject_3_cooldown) : 0) == 782
            && entity instanceof Subject3Entity _datEntSetI) {
            _datEntSetI.getEntityData().set(Subject3Entity.DATA_subject_3_cooldown, 0);
         }
      }
   }
}
