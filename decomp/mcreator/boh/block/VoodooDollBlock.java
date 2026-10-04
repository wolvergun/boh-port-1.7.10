package net.mcreator.boh.block;

import net.mcreator.boh.procedures.VoodooDollBlockAddedProcedure;
import net.mcreator.boh.procedures.VoodooDollBlockDestroyedByPlayerProcedure;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class VoodooDollBlock extends Block {
   public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

   public VoodooDollBlock() {
      super(Properties.of().ignitedByLava().sound(SoundType.VINE).strength(1.0F, 10.0F).noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
      this.registerDefaultState((BlockState)((BlockState)this.stateDefinition.any()).setValue(FACING, Direction.NORTH));
   }

   public boolean propagatesSkylightDown(BlockState state, BlockGetter reader, BlockPos pos) {
      return true;
   }

   public int getLightBlock(BlockState state, BlockGetter worldIn, BlockPos pos) {
      return 0;
   }

   public VoxelShape getVisualShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
      return Shapes.empty();
   }

   public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
      return switch ((Direction)state.getValue(FACING)) {
         case NORTH -> Shapes.or(
            box(7.0, 6.0, 14.0, 9.0, 9.0, 16.0),
            new VoxelShape[]{
               box(8.0, 4.0, 14.5, 9.0, 6.0, 15.5),
               box(9.0, 7.5, 14.75, 11.0, 8.5, 15.75),
               box(7.0, 4.0, 14.5, 8.0, 6.0, 15.5),
               box(5.0, 7.5, 14.75, 7.0, 8.5, 15.75),
               box(6.5, 9.0, 13.0, 9.5, 12.0, 16.0)
            }
         );
         case EAST -> Shapes.or(
            box(0.0, 6.0, 7.0, 2.0, 9.0, 9.0),
            new VoxelShape[]{
               box(0.5, 4.0, 8.0, 1.5, 6.0, 9.0),
               box(0.25, 7.5, 9.0, 1.25, 8.5, 11.0),
               box(0.5, 4.0, 7.0, 1.5, 6.0, 8.0),
               box(0.25, 7.5, 5.0, 1.25, 8.5, 7.0),
               box(0.0, 9.0, 6.5, 3.0, 12.0, 9.5)
            }
         );
         case WEST -> Shapes.or(
            box(14.0, 6.0, 7.0, 16.0, 9.0, 9.0),
            new VoxelShape[]{
               box(14.5, 4.0, 7.0, 15.5, 6.0, 8.0),
               box(14.75, 7.5, 5.0, 15.75, 8.5, 7.0),
               box(14.5, 4.0, 8.0, 15.5, 6.0, 9.0),
               box(14.75, 7.5, 9.0, 15.75, 8.5, 11.0),
               box(13.0, 9.0, 6.5, 16.0, 12.0, 9.5)
            }
         );
         default -> Shapes.or(
            box(7.0, 6.0, 0.0, 9.0, 9.0, 2.0),
            new VoxelShape[]{
               box(7.0, 4.0, 0.5, 8.0, 6.0, 1.5),
               box(5.0, 7.5, 0.25, 7.0, 8.5, 1.25),
               box(8.0, 4.0, 0.5, 9.0, 6.0, 1.5),
               box(9.0, 7.5, 0.25, 11.0, 8.5, 1.25),
               box(6.5, 9.0, 0.0, 9.5, 12.0, 3.0)
            }
         );
      };
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      super.createBlockStateDefinition(builder);
      builder.add(new Property[]{FACING});
   }

   public BlockState getStateForPlacement(BlockPlaceContext context) {
      return (BlockState)super.getStateForPlacement(context).setValue(FACING, context.getHorizontalDirection().getOpposite());
   }

   public BlockState rotate(BlockState state, Rotation rot) {
      return (BlockState)state.setValue(FACING, rot.rotate((Direction)state.getValue(FACING)));
   }

   public BlockState mirror(BlockState state, Mirror mirrorIn) {
      return state.rotate(mirrorIn.getRotation((Direction)state.getValue(FACING)));
   }

   public void onPlace(BlockState blockstate, Level world, BlockPos pos, BlockState oldState, boolean moving) {
      super.onPlace(blockstate, world, pos, oldState, moving);
      VoodooDollBlockAddedProcedure.execute(world, pos.getX(), pos.getY(), pos.getZ());
   }

   public boolean onDestroyedByPlayer(BlockState blockstate, Level world, BlockPos pos, Player entity, boolean willHarvest, FluidState fluid) {
      boolean retval = super.onDestroyedByPlayer(blockstate, world, pos, entity, willHarvest, fluid);
      VoodooDollBlockDestroyedByPlayerProcedure.execute(world, pos.getX(), pos.getY(), pos.getZ());
      return retval;
   }
}
