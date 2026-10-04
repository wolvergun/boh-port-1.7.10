package net.mcreator.boh.procedures;

import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.registries.ForgeRegistries;

public class TarBallProjectileHitsBlockProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity immediatesourceentity) {
      if (immediatesourceentity != null) {
         if (world instanceof Level _level) {
            if (!_level.isClientSide()) {
               _level.playSound(
                  null,
                  BlockPos.containing(x, y, z),
                  (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.slime_block.break")),
                  SoundSource.BLOCKS,
                  1.0F,
                  1.0F
               );
            } else {
               _level.playLocalSound(
                  x,
                  y,
                  z,
                  (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.slime_block.break")),
                  SoundSource.BLOCKS,
                  1.0F,
                  1.0F,
                  false
               );
            }
         }

         Entity _ent = immediatesourceentity;
         if (!_ent.level().isClientSide() && _ent.getServer() != null) {
            _ent.getServer()
               .getCommands()
               .performPrefixedCommand(
                  new CommandSourceStack(
                     CommandSource.NULL,
                     _ent.position(),
                     _ent.getRotationVector(),
                     _ent.level() instanceof ServerLevel ? (ServerLevel)_ent.level() : null,
                     4,
                     _ent.getName().getString(),
                     _ent.getDisplayName(),
                     _ent.level().getServer(),
                     _ent
                  ),
                  "/particle squid_ink ~ ~ ~ 0.2 0.2 0.2 .1 50"
               );
         }

         _ent = immediatesourceentity;
         if (!_ent.level().isClientSide() && _ent.getServer() != null) {
            _ent.getServer()
               .getCommands()
               .performPrefixedCommand(
                  new CommandSourceStack(
                     CommandSource.NULL,
                     _ent.position(),
                     _ent.getRotationVector(),
                     _ent.level() instanceof ServerLevel ? (ServerLevel)_ent.level() : null,
                     4,
                     _ent.getName().getString(),
                     _ent.getDisplayName(),
                     _ent.level().getServer(),
                     _ent
                  ),
                  "/summon area_effect_cloud ~ ~ ~ {Particle:squid_ink,Potion:2,Radius:3,Duration:200,Effects:[{Id:2,Duration:20,Amplifier:1,Ambient:1b,ShowParticles:1b,ShowIcon:1}]}"
               );
         }
      }
   }
}
