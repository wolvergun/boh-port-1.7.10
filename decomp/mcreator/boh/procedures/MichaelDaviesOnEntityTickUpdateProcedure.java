package net.mcreator.boh.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.registries.ForgeRegistries;

public class MichaelDaviesOnEntityTickUpdateProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
      if (entity != null) {
         if (!entity.getPersistentData().getBoolean("lines_michael") && (entity instanceof Mob _mobEnt ? _mobEnt.getTarget() : null) instanceof Player) {
            entity.getPersistentData().putBoolean("lines_michael", true);
         }

         if (!entity.getPersistentData().getBoolean("throlgular") && entity.getPersistentData().getBoolean("lines_michael")) {
            if (world instanceof Level _level) {
               if (!_level.isClientSide()) {
                  _level.playSound(
                     null,
                     BlockPos.containing(x, y, z),
                     (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:michael_spotted")),
                     SoundSource.HOSTILE,
                     2.0F,
                     1.0F
                  );
               } else {
                  _level.playLocalSound(
                     x,
                     y,
                     z,
                     (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:michael_spotted")),
                     SoundSource.HOSTILE,
                     2.0F,
                     1.0F,
                     false
                  );
               }
            }

            entity.getPersistentData().putBoolean("throlgular", true);
         }

         if (!((entity instanceof Mob _mobEnt ? _mobEnt.getTarget() : null) instanceof Player)) {
            entity.getPersistentData().putBoolean("lines_michael", false);
            entity.getPersistentData().putBoolean("throlgular", false);
         }
      }
   }
}
