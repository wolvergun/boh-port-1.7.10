package net.mcreator.boh.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.registries.ForgeRegistries;

public class TubbyCustardOnBlockRightClickedProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z) {
      world.destroyBlock(BlockPos.containing(x, y, z), false);
      if (world instanceof Level _level) {
         if (!_level.isClientSide()) {
            _level.playSound(
               null,
               BlockPos.containing(x, y, z),
               (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.wandering_trader.drink_milk")),
               SoundSource.BLOCKS,
               1.0F,
               1.0F
            );
         } else {
            _level.playLocalSound(
               x,
               y,
               z,
               (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.wandering_trader.drink_milk")),
               SoundSource.BLOCKS,
               1.0F,
               1.0F,
               false
            );
         }
      }
   }
}
