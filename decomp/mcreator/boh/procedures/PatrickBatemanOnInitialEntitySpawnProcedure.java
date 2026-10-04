package net.mcreator.boh.procedures;

import net.mcreator.boh.BohMod;
import net.mcreator.boh.entity.PatrickBatemanEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.registries.ForgeRegistries;

public class PatrickBatemanOnInitialEntitySpawnProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
      if (entity != null) {
         BohMod.queueServerWork(1, () -> {
            if (Math.random() < 0.55) {
               if (entity instanceof PatrickBatemanEntity animatable) {
                  animatable.setTexture("bateman");
               }
            } else if (entity instanceof PatrickBatemanEntity animatable) {
               animatable.setTexture("bateman_coat");
            }
         });
         if (Math.random() < 0.5 && world instanceof Level _level && _level.isClientSide()) {
            _level.playLocalSound(
               x, y, z, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:bateman_spawn")), SoundSource.HOSTILE, 1.0F, 1.0F, false
            );
         }
      }
   }
}
