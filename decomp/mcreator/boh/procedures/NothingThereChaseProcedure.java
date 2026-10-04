package net.mcreator.boh.procedures;

import javax.annotation.Nullable;
import net.mcreator.boh.entity.NothingThereEntity;
import net.mcreator.boh.network.BohModVariables;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.ForgeRegistries;

@EventBusSubscriber
public class NothingThereChaseProcedure {
   @SubscribeEvent
   public static void onPlayerTick(PlayerTickEvent event) {
      if (event.phase == Phase.END) {
         execute(event, event.player.level(), event.player.getX(), event.player.getY(), event.player.getZ(), event.player);
      }
   }

   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
      execute(null, world, x, y, z, entity);
   }

   private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z, Entity entity) {
      if (entity != null) {
         if (((BohModVariables.PlayerVariables)entity.getCapability(BohModVariables.PLAYER_VARIABLES_CAPABILITY, null)
               .orElse(new BohModVariables.PlayerVariables()))
            .chase_nothingthere) {
            entity.getPersistentData().putDouble("timer_chase_nt", entity.getPersistentData().getDouble("timer_chase_nt") + 1.0);
            if (!entity.getPersistentData().getBoolean("loop")
               && !world.getEntitiesOfClass(NothingThereEntity.class, AABB.ofSize(new Vec3(x, y, z), 15.0, 15.0, 15.0), e -> true).isEmpty()) {
               entity.getPersistentData().putBoolean("loop", true);
               if (world.isClientSide() && world instanceof Level _level) {
                  if (!_level.isClientSide()) {
                     _level.playSound(
                        null,
                        BlockPos.containing(x, y, z),
                        (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:chase_nothing_there")),
                        SoundSource.MUSIC,
                        0.5F,
                        1.0F
                     );
                  } else {
                     _level.playLocalSound(
                        x,
                        y,
                        z,
                        (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:chase_nothing_there")),
                        SoundSource.MUSIC,
                        0.5F,
                        1.0F,
                        false
                     );
                  }
               }
            }

            if (entity.getPersistentData().getBoolean("loop") && entity.getPersistentData().getDouble("timer_chase_nt") == 3520.0) {
               entity.getPersistentData().putDouble("timer_chase_nt", 0.0);
               entity.getPersistentData().putBoolean("loop", false);
            }
         }

         if (world.getEntitiesOfClass(NothingThereEntity.class, AABB.ofSize(new Vec3(x, y, z), 55.0, 55.0, 55.0), e -> true).isEmpty()) {
            boolean _setval = false;
            entity.getCapability(BohModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
               capability.chase_nothingthere = _setval;
               capability.syncPlayerVariables(entity);
            });
            if (world instanceof ServerLevel _level) {
               _level.getServer()
                  .getCommands()
                  .performPrefixedCommand(
                     new CommandSourceStack(
                           CommandSource.NULL, new Vec3(x, y, z), Vec2.ZERO, _level, 4, "", Component.literal(""), _level.getServer(), null
                        )
                        .withSuppressedOutput(),
                     "/stopsound @a music boh:chase_nothing_there"
                  );
            }
         }
      }
   }
}
