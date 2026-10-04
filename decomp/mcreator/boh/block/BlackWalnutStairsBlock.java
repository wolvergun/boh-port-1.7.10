package net.mcreator.boh.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;

public class BlackWalnutStairsBlock extends StairBlock {
   public BlackWalnutStairsBlock() {
      super(
         () -> Blocks.AIR.defaultBlockState(),
         Properties.of().ignitedByLava().instrument(NoteBlockInstrument.BASS).sound(SoundType.WOOD).strength(4.5F, 3.0F).dynamicShape()
      );
   }

   public float getExplosionResistance() {
      return 3.0F;
   }

   public boolean isRandomlyTicking(BlockState state) {
      return false;
   }

   public int getLightBlock(BlockState state, BlockGetter worldIn, BlockPos pos) {
      return 0;
   }

   public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
      return 8;
   }
}
