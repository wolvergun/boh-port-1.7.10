package net.mcreator.boh.procedures;

import javax.annotation.Nullable;
import net.mcreator.boh.entity.PatrickBatemanEntity;
import net.mcreator.boh.entity.PyramidHeadEntity;
import net.mcreator.boh.entity.SawRunnerEntity;
import net.mcreator.boh.entity.SeedEaterEntity;
import net.mcreator.boh.entity.SpringtrapEntity;
import net.mcreator.boh.entity.WhitefaceEntity;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber
public class DisableShieldProcedure {
   @SubscribeEvent
   public static void onEntityDeath(LivingDeathEvent event) {
      if (event != null && event.getEntity() != null) {
         execute(event, event.getEntity());
      }
   }

   public static void execute(Entity entity) {
      execute(null, entity);
   }

   private static void execute(@Nullable Event event, Entity entity) {
      if (entity != null) {
         if (entity instanceof SawRunnerEntity
            && entity instanceof PyramidHeadEntity
            && entity instanceof WhitefaceEntity
            && entity instanceof SpringtrapEntity
            && entity instanceof SeedEaterEntity
            && entity instanceof PatrickBatemanEntity) {
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
                     "/kill @e[type=item,nbt={Item:{id:\"minecraft:wooden_axe\"}}]"
                  );
            }
         }
      }
   }
}
