package net.mcreator.boh.procedures;

import javax.annotation.Nullable;
import net.mcreator.boh.entity.PatrickBatemanEntity;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber
public class AttackBatemanProcedure {
   @SubscribeEvent
   public static void onEntityAttacked(LivingAttackEvent event) {
      if (event != null && event.getEntity() != null) {
         execute(event, event.getEntity(), event.getSource().getEntity());
      }
   }

   public static void execute(Entity entity, Entity sourceentity) {
      execute(null, entity, sourceentity);
   }

   private static void execute(@Nullable Event event, Entity entity, Entity sourceentity) {
      if (entity != null && sourceentity != null) {
         if (sourceentity instanceof PatrickBatemanEntity) {
            Entity _ent = sourceentity;
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
                     "/particle minecraft:sweep_attack ~ ~1.2 ~"
                  );
            }

            _ent = sourceentity;
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
                     "/playsound minecraft:entity.player.attack.strong hostile @a"
                  );
            }

            if (!(entity instanceof LivingEntity _livEnt3 && _livEnt3.isBlocking()) && Math.random() < 0.45) {
               Entity _entx = entity;
               if (!_entx.level().isClientSide() && _entx.getServer() != null) {
                  _entx.getServer()
                     .getCommands()
                     .performPrefixedCommand(
                        new CommandSourceStack(
                           CommandSource.NULL,
                           _entx.position(),
                           _entx.getRotationVector(),
                           _entx.level() instanceof ServerLevel ? (ServerLevel)_entx.level() : null,
                           4,
                           _entx.getName().getString(),
                           _entx.getDisplayName(),
                           _entx.level().getServer(),
                           _entx
                        ),
                        "effect give @s kurolib:bleeding 2 10"
                     );
               }
            }
         }
      }
   }
}
