package net.mcreator.boh.procedures;

import net.mcreator.boh.init.BohModItems;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class TamedRatRightClickedOnEntityProcedure {
   public static void execute(Entity entity, Entity sourceentity) {
      if (entity != null && sourceentity != null) {
         if (!(entity instanceof TamableAnimal _tamIsTamedBy && sourceentity instanceof LivingEntity _livEnt && _tamIsTamedBy.isOwnedBy(_livEnt))) {
            if (entity instanceof TamableAnimal _toTame && sourceentity instanceof Player _owner) {
               _toTame.tame(_owner);
            }

            if (sourceentity instanceof Player _player && !_player.level().isClientSide()) {
               _player.displayClientMessage(Component.literal("Will now follow you"), true);
            }
         }

         if (((entity instanceof TamableAnimal _tamEnt ? _tamEnt.getOwner() : null) instanceof LivingEntity _livEntx
                     ? _livEntx.getMainHandItem()
                     : ItemStack.EMPTY)
                  .getItem()
               == ItemStack.EMPTY.getItem()
            && entity instanceof TamableAnimal _tamIsTamedByx
            && sourceentity instanceof LivingEntity _livEntx
            && _tamIsTamedByx.isOwnedBy(_livEntx)) {
            if (entity.getPersistentData().getDouble("state_ai") == 0.0) {
               if (sourceentity instanceof Player _player && !_player.level().isClientSide()) {
                  _player.displayClientMessage(Component.literal("Is Staying"), true);
               }

               entity.setShiftKeyDown(true);
               if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide()) {
                  _entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 999999, 255, false, false));
               }
            } else if (entity.getPersistentData().getDouble("state_ai") == 1.0) {
               if (sourceentity instanceof Player _player && !_player.level().isClientSide()) {
                  _player.displayClientMessage(Component.literal("Is Following"), true);
               }

               entity.setShiftKeyDown(false);
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

         if ((entity instanceof LivingEntity _livEntx ? _livEntx.getHealth() : -1.0F) < (entity instanceof LivingEntity _livEntx ? _livEntx.getMaxHealth() : -1.0F)
            && entity instanceof TamableAnimal _tamEnt
            && _tamEnt.isTame()
            && (sourceentity instanceof LivingEntity _livEntx ? _livEntx.getMainHandItem() : ItemStack.EMPTY).getItem() == BohModItems.PRETZEL.get()
            && entity instanceof TamableAnimal _tamIsTamedByx
            && sourceentity instanceof LivingEntity _livEntx
            && _tamIsTamedByx.isOwnedBy(_livEntx)) {
            if (sourceentity instanceof Player _player) {
               ItemStack _stktoremove;
               ItemStack var32 = _stktoremove = sourceentity instanceof LivingEntity _livEntxx ? _livEntxx.getMainHandItem() : ItemStack.EMPTY;
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
      }
   }
}
