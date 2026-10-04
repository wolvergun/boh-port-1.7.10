package net.mcreator.boh.procedures;

import net.mcreator.boh.BohMod;
import net.mcreator.boh.item.BigDaddyDrillItem;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;

public class BigDaddyDrillRightclickedProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, ItemStack itemstack) {
      if (entity != null) {
         if (entity.onGround()) {
            ItemStack _ist = itemstack;
            if (_ist.hurt(2, RandomSource.create(), null)) {
               _ist.shrink(1);
               _ist.setDamageValue(0);
            }

            itemstack.getOrCreateTag().putBoolean("dash", true);
            if (world instanceof Level _level) {
               if (!_level.isClientSide()) {
                  _level.playSound(
                     null,
                     BlockPos.containing(x, y, z),
                     (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:drill_dash")),
                     SoundSource.PLAYERS,
                     1.0F,
                     1.0F
                  );
               } else {
                  _level.playLocalSound(
                     x, y, z, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:drill_dash")), SoundSource.PLAYERS, 1.0F, 1.0F, false
                  );
               }
            }

            BohMod.queueServerWork(20, () -> itemstack.getOrCreateTag().putBoolean("dash", false));
            if (itemstack.getItem() instanceof BigDaddyDrillItem) {
               itemstack.getOrCreateTag().putString("geckoAnim", "use");
            }

            if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide()) {
               _entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 20, 2, false, false));
            }

            entity.setDeltaMovement(
               new Vec3(
                  entity.getDeltaMovement().x() + entity.getLookAngle().x * 6.0,
                  entity.getDeltaMovement().y(),
                  entity.getDeltaMovement().z() + entity.getLookAngle().z * 6.0
               )
            );
            if (entity instanceof Player _player) {
               _player.getCooldowns().addCooldown(itemstack.getItem(), 60);
            }
         }
      }
   }
}
