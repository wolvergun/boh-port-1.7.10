package net.mcreator.boh.block;

import net.mcreator.boh.block.grower.SinistreeSaplingTreeGrower;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.OffsetType;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

public class SinistreeSaplingBlock extends SaplingBlock {
   public SinistreeSaplingBlock() {
      super(
         new SinistreeSaplingTreeGrower(),
         Properties.of()
            .mapColor(MapColor.PLANT)
            .randomTicks()
            .sound(SoundType.GRASS)
            .instabreak()
            .noCollission()
            .offsetType(OffsetType.NONE)
            .pushReaction(PushReaction.DESTROY)
      );
   }

   public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
      return 100;
   }

   public int getFireSpreadSpeed(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
      return 60;
   }
}
