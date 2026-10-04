package net.mcreator.boh.procedures;

import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.ForgeRegistries;

public class MothlingRightClickedOnEntityProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, Entity sourceentity) {
      if (entity != null && sourceentity != null) {
         if (!sourceentity.isShiftKeyDown()) {
            if (!(entity instanceof TamableAnimal _tamEnt && _tamEnt.isTame())
               && (sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Blocks.WHITE_WOOL.asItem()
               && !(entity instanceof TamableAnimal _tamIsTamedBy && sourceentity instanceof LivingEntity _livEnt && _tamIsTamedBy.isOwnedBy(_livEnt))) {
               if (!(entity instanceof Player _plr && _plr.getAbilities().instabuild) && sourceentity instanceof Player _player) {
                  ItemStack _stktoremove;
                  ItemStack var44 = _stktoremove = sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY;
                  _player.getInventory().clearOrCountMatchingItems(p -> _stktoremove.getItem() == p.getItem(), 1, _player.inventoryMenu.getCraftSlots());
               }

               if (Math.random() < 0.02) {
                  if (entity instanceof TamableAnimal _toTame && sourceentity instanceof Player _owner) {
                     _toTame.tame(_owner);
                  }

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
                           "/particle minecraft:happy_villager ~ ~1.8 ~ 0.3 0.2 0.3 0 5"
                        );
                  }

                  if (world instanceof Level _level) {
                     if (!_level.isClientSide()) {
                        _level.playSound(
                           null,
                           BlockPos.containing(x, y, z),
                           (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.arrow.hit_player")),
                           SoundSource.AMBIENT,
                           1.0F,
                           1.0F
                        );
                     } else {
                        _level.playLocalSound(
                           x,
                           y,
                           z,
                           (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.arrow.hit_player")),
                           SoundSource.AMBIENT,
                           1.0F,
                           1.0F,
                           false
                        );
                     }
                  }
               }
            }

            if ((entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1.0F) < (entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1.0F)
               && entity instanceof TamableAnimal _tamEnt
               && _tamEnt.isTame()
               && (sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Blocks.WHITE_WOOL.asItem()
               && entity instanceof TamableAnimal _tamIsTamedBy
               && sourceentity instanceof LivingEntity _livEnt
               && _tamIsTamedBy.isOwnedBy(_livEnt)) {
               if (sourceentity instanceof Player _player) {
                  ItemStack _stktoremove;
                  ItemStack var47 = _stktoremove = sourceentity instanceof LivingEntity _livEntx ? _livEntx.getMainHandItem() : ItemStack.EMPTY;
                  _player.getInventory().clearOrCountMatchingItems(p -> _stktoremove.getItem() == p.getItem(), 1, _player.inventoryMenu.getCraftSlots());
               }

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
                        "/particle minecraft:heart ~ ~.5 ~ 0.3 0.2 0.3 0 5"
                     );
               }

               if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide()) {
                  _entity.addEffect(new MobEffectInstance(MobEffects.HEAL, 10, 0, false, false));
               }
            }

            if ((sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == ItemStack.EMPTY.getItem()
               && entity instanceof TamableAnimal _tamIsTamedBy
               && sourceentity instanceof LivingEntity _livEnt
               && _tamIsTamedBy.isOwnedBy(_livEnt)
               && entity instanceof TamableAnimal _tamIsTamedByx
               && sourceentity instanceof LivingEntity _livEntx
               && _tamIsTamedByx.isOwnedBy(_livEntx)) {
               if (world instanceof Level _level) {
                  if (!_level.isClientSide()) {
                     _level.playSound(
                        null,
                        BlockPos.containing(x, y, z),
                        (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.arrow.hit_player")),
                        SoundSource.PLAYERS,
                        1.0F,
                        1.0F
                     );
                  } else {
                     _level.playLocalSound(
                        x,
                        y,
                        z,
                        (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.arrow.hit_player")),
                        SoundSource.PLAYERS,
                        1.0F,
                        1.0F,
                        false
                     );
                  }
               }

               if (entity.getPersistentData().getDouble("state_ai") == 0.0) {
                  if (sourceentity instanceof Player _player && !_player.level().isClientSide()) {
                     _player.displayClientMessage(Component.literal("Is Staying"), true);
                  }

                  if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide()) {
                     _entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 9999999, 255, false, false));
                  }
               } else if (entity.getPersistentData().getDouble("state_ai") == 1.0) {
                  if (sourceentity instanceof Player _player && !_player.level().isClientSide()) {
                     _player.displayClientMessage(Component.literal("Is Following"), true);
                  }

                  if (entity instanceof LivingEntity _entity) {
                     _entity.removeEffect(MobEffects.MOVEMENT_SLOWDOWN);
                  }
               }

               if (entity.getPersistentData().getDouble("state_ai") <= 2.0) {
                  entity.getPersistentData().putDouble("state_ai", entity.getPersistentData().getDouble("state_ai") + 1.0);
               } else if (entity.getPersistentData().getDouble("state_ai") >= 2.0) {
                  if (sourceentity instanceof Player _player && !_player.level().isClientSide()) {
                     _player.displayClientMessage(Component.literal("Is Wandering"), true);
                  }

                  entity.setShiftKeyDown(false);
                  if (entity instanceof LivingEntity _entity) {
                     _entity.removeEffect(MobEffects.MOVEMENT_SLOWDOWN);
                  }

                  entity.getPersistentData().putDouble("state_ai", 0.0);
               }
            }
         }
      }
   }
}
