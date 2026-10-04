package net.mcreator.boh.procedures;

import java.util.Comparator;
import net.mcreator.boh.BohMod;
import net.mcreator.boh.entity.SlenderManEntity;
import net.mcreator.boh.init.BohModEntities;
import net.mcreator.boh.init.BohModMobEffects;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.registries.ForgeRegistries;

public class DrawingSlenderOnBlockRightClickedProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
      if (entity != null) {
         if (world instanceof Level _level) {
            if (!_level.isClientSide()) {
               _level.playSound(
                  null,
                  BlockPos.containing(x, y, z),
                  (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:page_pickup")),
                  SoundSource.NEUTRAL,
                  1.0F,
                  1.0F
               );
            } else {
               _level.playLocalSound(
                  x, y, z, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:page_pickup")), SoundSource.NEUTRAL, 1.0F, 1.0F, false
               );
            }
         }

         if (entity instanceof ServerPlayer _player) {
            Advancement _adv = _player.server.getAdvancements().getAdvancement(new ResourceLocation("boh:grimm_start"));
            AdvancementProgress _ap = _player.getAdvancements().getOrStartProgress(_adv);
            if (!_ap.isDone()) {
               for (String criteria : _ap.getRemainingCriteria()) {
                  _player.getAdvancements().award(_adv, criteria);
               }
            }
         }

         world.destroyBlock(BlockPos.containing(x, y, z), false);
         if (entity instanceof Player _player) {
            ItemStack _setstack = new ItemStack(Items.PAPER).copy();
            _setstack.setCount(1);
            ItemHandlerHelper.giveItemToPlayer(_player, _setstack);
         }

         if (world.getEntitiesOfClass(SlenderManEntity.class, AABB.ofSize(new Vec3(x, y, z), 600.0, 600.0, 600.0), e -> true).isEmpty()) {
            if (world instanceof ServerLevel _level) {
               Entity entityToSpawn = ((EntityType)BohModEntities.SLENDER_MAN.get()).spawn(_level, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED);
               if (entityToSpawn != null) {
                  entityToSpawn.setDeltaMovement(0.0, 0.0, 0.0);
               }
            }

            BohMod.queueServerWork(
               10,
               () -> {
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
                           "/execute at @p rotated ~ 1 run spreadplayers ~ ~ 20 30 false @e[type=boh:slender_man,limit=1]"
                        );
                  }
               }
            );
         }

         if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide()) {
            _entity.addEffect(
               new MobEffectInstance(
                  (MobEffect)BohModMobEffects.ENGAGED.get(),
                  999999,
                  (
                        entity instanceof LivingEntity _livEnt && _livEnt.hasEffect((MobEffect)BohModMobEffects.ENGAGED.get())
                           ? _livEnt.getEffect((MobEffect)BohModMobEffects.ENGAGED.get()).getAmplifier()
                           : 0
                     )
                     + 1,
                  false,
                  false
               )
            );
         }

         if ((
               entity instanceof LivingEntity _livEnt && _livEnt.hasEffect((MobEffect)BohModMobEffects.ENGAGED.get())
                  ? _livEnt.getEffect((MobEffect)BohModMobEffects.ENGAGED.get()).getAmplifier()
                  : 0
            )
            > 7) {
            if (entity instanceof LivingEntity _entity) {
               _entity.removeEffect((MobEffect)BohModMobEffects.ENGAGED.get());
            }

            Vec3 _center = new Vec3(x, y, z);

            for (Entity entityiterator : world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(300.0), e -> true)
               .stream()
               .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center)))
               .toList()) {
               if (entityiterator instanceof SlenderManEntity) {
                  if (entity instanceof ServerPlayer _player) {
                     Advancement _adv = _player.server.getAdvancements().getAdvancement(new ResourceLocation("boh:slender_gift"));
                     AdvancementProgress _ap = _player.getAdvancements().getOrStartProgress(_adv);
                     if (!_ap.isDone()) {
                        for (String criteria : _ap.getRemainingCriteria()) {
                           _player.getAdvancements().award(_adv, criteria);
                        }
                     }
                  }

                  Entity _ent = entityiterator;
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
                           "/particle minecraft:squid_ink ~ ~ ~ 0.5 3 0.5 0 200"
                        );
                  }

                  _ent = entityiterator;
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
                           "/advancement grant @a[distance=0..600] only boh:slender_gift "
                        );
                  }

                  _ent = entityiterator;
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
                           "/playsound boh:slender_jumpscare hostile @a"
                        );
                  }

                  if (!entityiterator.level().isClientSide()) {
                     entityiterator.discard();
                  }
               }
            }
         }
      }
   }
}
