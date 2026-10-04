package net.mcreator.boh.block;

import java.util.List;
import net.mcreator.boh.procedures.DreamCatcherOnTickUpdateProcedure;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class DreamCatcherBlock extends Block implements SimpleWaterloggedBlock {
   public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
   public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

   public DreamCatcherBlock() {
      super(Properties.of().sound(SoundType.WOOD).strength(0.3F, 2.0F).noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
      this.registerDefaultState((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue(FACING, Direction.NORTH)).setValue(WATERLOGGED, false));
   }

   public void appendHoverText(ItemStack itemstack, BlockGetter level, List<Component> list, TooltipFlag flag) {
      super.appendHoverText(itemstack, level, list, flag);
      list.add(Component.translatable("block.boh.dream_catcher.description_0"));
   }

   public boolean propagatesSkylightDown(BlockState state, BlockGetter reader, BlockPos pos) {
      return state.getFluidState().isEmpty();
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
            box(6.0, 10.0, 15.0, 10.0, 13.0, 16.0),
            new VoxelShape[]{
               box(5.0, 1.0, 15.0, 6.0, 12.0, 16.0),
               box(10.0, 2.0, 15.0, 11.0, 12.0, 16.0),
               box(4.0, 0.0, 15.0, 5.0, 11.0, 16.0),
               box(11.0, 1.0, 15.0, 12.0, 11.0, 16.0),
               box(6.0, 9.0, 15.0, 8.0, 10.0, 16.0),
               box(9.0, 0.0, 15.0, 10.0, 10.0, 16.0),
               box(6.0, 3.0, 15.0, 7.0, 9.0, 16.0),
               box(8.0, 1.0, 15.0, 9.0, 9.0, 16.0),
               box(7.0, 2.0, 15.0, 8.0, 8.0, 16.0),
               box(3.0, 4.0, 15.0, 4.0, 7.0, 16.0),
               box(12.0, 4.0, 15.0, 13.0, 7.0, 16.0),
               box(2.0, 3.0, 15.0, 3.0, 6.0, 16.0),
               box(13.0, 4.0, 15.0, 14.0, 6.0, 16.0),
               box(12.0, 0.0, 15.0, 13.0, 3.0, 16.0),
               box(13.0, 0.0, 15.0, 14.0, 2.0, 16.0)
            }
         );
         case EAST -> Shapes.or(
            box(0.0, 10.0, 6.0, 1.0, 13.0, 10.0),
            new VoxelShape[]{
               box(0.0, 1.0, 5.0, 1.0, 12.0, 6.0),
               box(0.0, 2.0, 10.0, 1.0, 12.0, 11.0),
               box(0.0, 0.0, 4.0, 1.0, 11.0, 5.0),
               box(0.0, 1.0, 11.0, 1.0, 11.0, 12.0),
               box(0.0, 9.0, 6.0, 1.0, 10.0, 8.0),
               box(0.0, 0.0, 9.0, 1.0, 10.0, 10.0),
               box(0.0, 3.0, 6.0, 1.0, 9.0, 7.0),
               box(0.0, 1.0, 8.0, 1.0, 9.0, 9.0),
               box(0.0, 2.0, 7.0, 1.0, 8.0, 8.0),
               box(0.0, 4.0, 3.0, 1.0, 7.0, 4.0),
               box(0.0, 4.0, 12.0, 1.0, 7.0, 13.0),
               box(0.0, 3.0, 2.0, 1.0, 6.0, 3.0),
               box(0.0, 4.0, 13.0, 1.0, 6.0, 14.0),
               box(0.0, 0.0, 12.0, 1.0, 3.0, 13.0),
               box(0.0, 0.0, 13.0, 1.0, 2.0, 14.0)
            }
         );
         case WEST -> Shapes.or(
            box(15.0, 10.0, 6.0, 16.0, 13.0, 10.0),
            new VoxelShape[]{
               box(15.0, 1.0, 10.0, 16.0, 12.0, 11.0),
               box(15.0, 2.0, 5.0, 16.0, 12.0, 6.0),
               box(15.0, 0.0, 11.0, 16.0, 11.0, 12.0),
               box(15.0, 1.0, 4.0, 16.0, 11.0, 5.0),
               box(15.0, 9.0, 8.0, 16.0, 10.0, 10.0),
               box(15.0, 0.0, 6.0, 16.0, 10.0, 7.0),
               box(15.0, 3.0, 9.0, 16.0, 9.0, 10.0),
               box(15.0, 1.0, 7.0, 16.0, 9.0, 8.0),
               box(15.0, 2.0, 8.0, 16.0, 8.0, 9.0),
               box(15.0, 4.0, 12.0, 16.0, 7.0, 13.0),
               box(15.0, 4.0, 3.0, 16.0, 7.0, 4.0),
               box(15.0, 3.0, 13.0, 16.0, 6.0, 14.0),
               box(15.0, 4.0, 2.0, 16.0, 6.0, 3.0),
               box(15.0, 0.0, 3.0, 16.0, 3.0, 4.0),
               box(15.0, 0.0, 2.0, 16.0, 2.0, 3.0)
            }
         );
         default -> Shapes.or(
            box(6.0, 10.0, 0.0, 10.0, 13.0, 1.0),
            new VoxelShape[]{
               box(10.0, 1.0, 0.0, 11.0, 12.0, 1.0),
               box(5.0, 2.0, 0.0, 6.0, 12.0, 1.0),
               box(11.0, 0.0, 0.0, 12.0, 11.0, 1.0),
               box(4.0, 1.0, 0.0, 5.0, 11.0, 1.0),
               box(8.0, 9.0, 0.0, 10.0, 10.0, 1.0),
               box(6.0, 0.0, 0.0, 7.0, 10.0, 1.0),
               box(9.0, 3.0, 0.0, 10.0, 9.0, 1.0),
               box(7.0, 1.0, 0.0, 8.0, 9.0, 1.0),
               box(8.0, 2.0, 0.0, 9.0, 8.0, 1.0),
               box(12.0, 4.0, 0.0, 13.0, 7.0, 1.0),
               box(3.0, 4.0, 0.0, 4.0, 7.0, 1.0),
               box(13.0, 3.0, 0.0, 14.0, 6.0, 1.0),
               box(2.0, 4.0, 0.0, 3.0, 6.0, 1.0),
               box(3.0, 0.0, 0.0, 4.0, 3.0, 1.0),
               box(2.0, 0.0, 0.0, 3.0, 2.0, 1.0)
            }
         );
      };
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      super.createBlockStateDefinition(builder);
      builder.add(new Property[]{FACING, WATERLOGGED});
   }

   public BlockState getStateForPlacement(BlockPlaceContext context) {
      boolean flag = context.getLevel().getFluidState(context.getClickedPos()).getType() == Fluids.WATER;
      return (BlockState)((BlockState)super.getStateForPlacement(context).setValue(FACING, context.getHorizontalDirection().getOpposite())).setValue(WATERLOGGED, flag);
   }

   public BlockState rotate(BlockState state, Rotation rot) {
      return (BlockState)state.setValue(FACING, rot.rotate((Direction)state.getValue(FACING)));
   }

   public BlockState mirror(BlockState state, Mirror mirrorIn) {
      return state.rotate(mirrorIn.getRotation((Direction)state.getValue(FACING)));
   }

   public FluidState getFluidState(BlockState state) {
      return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
   }

   public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor world, BlockPos currentPos, BlockPos facingPos) {
      if ((Boolean)state.getValue(WATERLOGGED)) {
         world.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
      }

      return super.updateShape(state, facing, facingState, world, currentPos, facingPos);
   }

   public void onPlace(BlockState blockstate, Level world, BlockPos pos, BlockState oldState, boolean moving) {
      super.onPlace(blockstate, world, pos, oldState, moving);
      world.scheduleTick(pos, this, 1);
   }

   public void tick(BlockState blockstate, ServerLevel world, BlockPos pos, RandomSource random) {
      super.tick(blockstate, world, pos, random);
      int x = pos.getX();
      int y = pos.getY();
      int z = pos.getZ();
      DreamCatcherOnTickUpdateProcedure.execute(world, x, y, z);
      world.scheduleTick(pos, this, 1);
   }
}
