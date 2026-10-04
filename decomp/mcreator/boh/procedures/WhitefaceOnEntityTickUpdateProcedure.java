package net.mcreator.boh.procedures;

import net.mcreator.boh.network.BohModVariables;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;

public class WhitefaceOnEntityTickUpdateProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
      if (entity != null) {
         if (entity instanceof Mob _mob && _mob.isAggressive()) {
            entity.setDeltaMovement(new Vec3(entity.getLookAngle().x * 0.5, entity.getLookAngle().y * 0.9, entity.getLookAngle().z * 0.5));
            Entity _ent = entity;
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
                     "/execute as @e[type=boh:whiteface,distance=0..1] at @s facing entity @e[type=minecraft:player,tag=whiteface,sort=nearest,limit=1] feet run tp @e[type=boh:whiteface] ^ ^ ^0.1 ~ ~"
                  );
            }
         }

         entity.setDeltaMovement(new Vec3(entity.getLookAngle().x * 0.2, entity.getLookAngle().y * 0.9, entity.getLookAngle().z * 0.2));
         entity.getPersistentData().putDouble("loop", entity.getPersistentData().getDouble("loop") + 1.0);
         if (entity.getPersistentData().getDouble("loop") == 12.0) {
            if ((entity instanceof Mob _mobEnt ? _mobEnt.getTarget() : null) instanceof Player && world instanceof Level _level) {
               if (!_level.isClientSide()) {
                  _level.playSound(
                     null,
                     BlockPos.containing(x, y, z),
                     (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:whiteface_ambience")),
                     SoundSource.HOSTILE,
                     1.0F,
                     1.0F
                  );
               } else {
                  _level.playLocalSound(
                     x,
                     y,
                     z,
                     (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:whiteface_ambience")),
                     SoundSource.HOSTILE,
                     1.0F,
                     1.0F,
                     false
                  );
               }
            }

            entity.getPersistentData().putDouble("loop", 0.0);
         }

         if (BohModVariables.MapVariables.get(world).Kill_WF == 1.0 && !entity.level().isClientSide()) {
            entity.discard();
         }
      }
   }
}
