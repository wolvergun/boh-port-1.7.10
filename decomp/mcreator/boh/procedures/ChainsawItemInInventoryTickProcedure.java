package net.mcreator.boh.procedures;

import net.mcreator.boh.item.ChainsawItem;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.registries.ForgeRegistries;

public class ChainsawItemInInventoryTickProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, ItemStack itemstack) {
      if (entity != null) {
         if (itemstack.getOrCreateTag().getDouble("rev") == 3.0) {
            itemstack.getOrCreateTag().putDouble("rev_timer", itemstack.getOrCreateTag().getDouble("rev_timer") + 1.0);
            itemstack.getOrCreateTag().putDouble("timer_sound", itemstack.getOrCreateTag().getDouble("timer_sound") + 1.0);
         }

         if (itemstack.getOrCreateTag().getDouble("rev_timer") == 200.0) {
            if (entity instanceof Player _player) {
               _player.getCooldowns().addCooldown(itemstack.getItem(), 100);
            }

            itemstack.getOrCreateTag().putDouble("rev", 0.0);
            itemstack.getOrCreateTag().putDouble("rev_timer", 0.0);
            if (itemstack.getItem() instanceof ChainsawItem) {
               itemstack.getOrCreateTag().putString("geckoAnim", "idle");
            }

            if (world instanceof Level _level) {
               if (!_level.isClientSide()) {
                  _level.playSound(
                     null,
                     BlockPos.containing(x, y, z),
                     (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:chainsaw_stall")),
                     SoundSource.PLAYERS,
                     1.0F,
                     1.0F
                  );
               } else {
                  _level.playLocalSound(
                     x,
                     y,
                     z,
                     (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("boh:chainsaw_stall")),
                     SoundSource.PLAYERS,
                     1.0F,
                     1.0F,
                     false
                  );
               }
            }
         }
      }
   }
}
