package net.mcreator.boh.procedures;

import java.util.Comparator;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;

public class GasterOnEntityTickUpdateProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
      if (entity != null) {
         if (Math.random() < 0.005 && world instanceof Level _level) {
            if (!_level.isClientSide()) {
               _level.playSound(
                  null,
                  BlockPos.containing(x, y, z),
                  (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:gaster_laugh")),
                  SoundSource.PLAYERS,
                  1.0F,
                  1.0F
               );
            } else {
               _level.playLocalSound(
                  x, y, z, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:gaster_laugh")), SoundSource.PLAYERS, 1.0F, 1.0F, false
               );
            }
         }

         Vec3 _center = new Vec3(x, y, z);

         for (Entity entityiterator : world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(10.0), e -> true)
            .stream()
            .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center)))
            .toList()) {
            if (Math.random() < 0.1) {
               if (entityiterator instanceof Player _player && !_player.level().isClientSide()) {
                  _player.displayClientMessage(
                     Component.literal(
                        "\ud83d\udc4e︎✌︎☼︎\ud83d\ude10︎\ud83d\udcea︎ \ud83d\udc4e︎✌︎☼︎\ud83d\ude10︎\ud83d\udcea︎ ✡︎☜︎❄︎ \ud83d\udc4e︎✌︎☼︎\ud83d\ude10︎☜︎☼︎"
                     ),
                     true
                  );
               }
            } else if (Math.random() < 0.1) {
               if (entityiterator instanceof Player _player && !_player.level().isClientSide()) {
                  _player.displayClientMessage(
                     Component.literal("\ud83d\udc4d︎\ud83d\udd46︎\ud83d\udd46︎\ud83d\udd46︎\ud83d\udd46︎\ud83d\udd46︎\ud83d\udd46︎\ud83d\udc4c︎☜︎"), true
                  );
               }
            } else if (Math.random() < 0.1) {
               if (entityiterator instanceof Player _player && !_player.level().isClientSide()) {
                  _player.displayClientMessage(Component.literal("✋︎ \ud83d\udca7︎☜︎☠︎\ud83d\udca7︎☜︎ ☠︎⚐︎ \ud83d\udc4e︎☜︎❄︎☜︎☼︎\ud83d\udca3︎✋︎☠︎✌︎❄︎✋︎⚐︎☠︎"), true);
               }
            } else if (Math.random() < 0.1) {
               if (entityiterator instanceof Player _player && !_player.level().isClientSide()) {
                  _player.displayClientMessage(Component.literal("\ud83d\udd48︎☟︎☜︎☼︎☜︎ ✌︎\ud83d\udca3︎ ✋︎"), true);
               }
            } else if (Math.random() < 0.1) {
               if (entityiterator instanceof Player _player && !_player.level().isClientSide()) {
                  _player.displayClientMessage(
                     Component.literal(
                        "❄︎☟︎✋︎\ud83d\udca7︎ ✋︎\ud83d\udca7︎ ☹︎✋︎\ud83d\ude10︎☜︎ ❄︎☟︎✌︎❄︎ ❄︎✋︎\ud83d\udca3︎☜︎ ✋︎ \ud83d\udd48︎✌︎\ud83d\udca7︎ ✋︎☠︎ \ud83d\udc4c︎✋︎☠︎\ud83d\udc4e︎✋︎☠︎☝︎ ⚐︎☞︎ ✋︎\ud83d\udca7︎✌︎✌︎\ud83d\udc4d︎"
                     ),
                     true
                  );
               }
            } else if (Math.random() < 0.1) {
               if (entityiterator instanceof Player _player && !_player.level().isClientSide()) {
                  _player.displayClientMessage(Component.literal("✋︎ ✌︎\ud83d\udca3︎ ✌︎ ☝︎✌︎☝︎ \ud83d\udc4d︎☟︎✌︎☼︎✌︎\ud83d\udc4d︎❄︎☜︎☼︎"), true);
               }
            } else if (Math.random() < 0.1) {
               if (entityiterator instanceof Player _player && !_player.level().isClientSide()) {
                  _player.displayClientMessage(Component.literal("☞︎\ud83d\udd46︎\ud83d\udc4d︎\ud83d\ude10︎ ⚐︎☞︎☞︎"), true);
               }
            } else if (Math.random() < 0.1) {
               if (entityiterator instanceof Player _player && !_player.level().isClientSide()) {
                  _player.displayClientMessage(Component.literal("\ud83d\udc4d︎☼︎☜︎☜︎\ud83c\udff1︎☜︎☼︎ ✌︎\ud83d\udd48︎\ud83d\udd48︎ \ud83d\udca3︎✌︎☠︎"), true);
               }
            } else if (Math.random() < 0.1) {
               if (entityiterator instanceof Player _player && !_player.level().isClientSide()) {
                  _player.displayClientMessage(Component.literal("\ud83d\udd48︎☟︎☜︎☼︎☜︎ ✋︎\ud83d\udca7︎ \ud83d\udca7︎✌︎☠︎\ud83d\udca7︎"), true);
               }
            } else if (Math.random() < 0.1) {
               if (entityiterator instanceof Player _player && !_player.level().isClientSide()) {
                  _player.displayClientMessage(
                     Component.literal("✋︎ \ud83d\udca3︎✋︎\ud83d\udca7︎\ud83d\udca7︎ \ud83d\udca3︎✡︎ \ud83d\udd48︎✋︎☞︎☜︎ ❄︎✌︎✋︎☹︎\ud83d\udca7︎"), true
                  );
               }
            } else if (Math.random() < 0.1) {
               if (entityiterator instanceof Player _player && !_player.level().isClientSide()) {
                  _player.displayClientMessage(
                     Component.literal(
                        "☝︎\ud83d\udd46︎\ud83d\udca7︎\ud83d\udcea︎ ❄︎✌︎\ud83d\udc4d︎\ud83d\udcc1︎\ud83d\udc4e︎✋︎☹︎☜︎\ud83d\udcea︎ \ud83d\udca7︎✋︎☼︎\ud83c\udff1︎✌︎☠︎\ud83d\udc4d︎✌︎\ud83d\ude10︎☜︎\ud83d\udca7︎"
                     ),
                     true
                  );
               }
            } else if (Math.random() < 0.1) {
               if (entityiterator instanceof Player _player && !_player.level().isClientSide()) {
                  _player.displayClientMessage(
                     Component.literal(
                        "\ud83d\uddcf︎\ud83d\uddb2︎ \ud83d\udc4c︎\ud83d\udd46︎☼︎✋︎☜︎\ud83d\udc4e︎ \ud83d\udcc1︎ ☞︎⚐︎\ud83d\udd46︎☠︎\ud83d\udc4e︎"
                     ),
                     true
                  );
               }
            } else if (Math.random() < 0.1 && entityiterator instanceof Player _player && !_player.level().isClientSide()) {
               _player.displayClientMessage(Component.literal("☠︎⚐︎✋︎\ud83d\udca7︎☜︎✡︎ \ud83d\udc4c︎✋︎❄︎\ud83d\udc4d︎☟︎"), true);
            }
         }

         entity.getPersistentData().putDouble("ambience", entity.getPersistentData().getDouble("ambience") + 1.0);
         if (entity.getPersistentData().getDouble("ambience") >= 1580.0) {
            if (world instanceof Level _level) {
               if (!_level.isClientSide()) {
                  _level.playSound(
                     null,
                     BlockPos.containing(x, y, z),
                     (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:gaster_ambience")),
                     SoundSource.AMBIENT,
                     0.5F,
                     1.0F
                  );
               } else {
                  _level.playLocalSound(
                     x,
                     y,
                     z,
                     (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:gaster_ambience")),
                     SoundSource.AMBIENT,
                     0.5F,
                     1.0F,
                     false
                  );
               }
            }

            entity.getPersistentData().putDouble("ambience", 0.0);
         }
      }
   }
}
