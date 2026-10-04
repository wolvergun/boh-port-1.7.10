package net.mcreator.boh.procedures;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;

public class BaldiRulerToolInHandTickProcedure {
   public static void execute(Entity entity, ItemStack itemstack) {
      if (entity != null) {
         if (itemstack.getOrCreateTag().getBoolean("ruler_clap")) {
            if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide()) {
               _entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 20, 2, false, false));
            }

            if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide()) {
               _entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 20, 2, false, false));
            }

            if (entity instanceof LivingEntity _entity) {
               _entity.removeEffect(MobEffects.MOVEMENT_SLOWDOWN);
            }

            if (entity instanceof LivingEntity _entity) {
               _entity.removeEffect(MobEffects.WEAKNESS);
            }
         }

         if (!itemstack.getOrCreateTag().getBoolean("ruler_clap")) {
            entity.makeStuckInBlock(Blocks.AIR.defaultBlockState(), new Vec3(0.25, 0.05, 0.25));
            if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide()) {
               _entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20, 255, false, false));
            }

            if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide()) {
               _entity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 20, 255, false, false));
            }
         }

         if (!(entity instanceof Player _plrCldCheck12 && _plrCldCheck12.getCooldowns().isOnCooldown(itemstack.getItem()))) {
            itemstack.getOrCreateTag().putBoolean("ruler_clap", false);
         }
      }
   }
}
