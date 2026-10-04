package net.mcreator.boh.block;

import net.mcreator.boh.init.BohModBlocks;
import net.mcreator.boh.procedures.AnalogTVSixUpdateTickProcedure;
import net.mcreator.boh.procedures.AnalogTVStaticOnBlockRightClickedProcedure;
import net.mcreator.boh.procedures.AnalogTVStaticUpdateTickProcedure;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
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
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class AnalogTVSixBlock extends Block {
   public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

   public AnalogTVSixBlock() {
      super(Properties.of().sound(SoundType.WOOD).strength(1.0F, 10.0F).lightLevel(s -> 3).noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
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
            box(-2.0, 2.0, 2.0, 18.0, 16.0, 14.0),
            new VoxelShape[]{box(1.0, 0.0, 2.0, 15.0, 2.0, 14.0), box(-1.0, 11.0, 1.0, 1.0, 13.0, 2.0), box(-1.0, 8.0, 1.0, 1.0, 10.0, 2.0)}
         );
         case EAST -> Shapes.or(
            box(2.0, 2.0, -2.0, 14.0, 16.0, 18.0),
            new VoxelShape[]{box(2.0, 0.0, 1.0, 14.0, 2.0, 15.0), box(14.0, 11.0, -1.0, 15.0, 13.0, 1.0), box(14.0, 8.0, -1.0, 15.0, 10.0, 1.0)}
         );
         case WEST -> Shapes.or(
            box(2.0, 2.0, -2.0, 14.0, 16.0, 18.0),
            new VoxelShape[]{box(2.0, 0.0, 1.0, 14.0, 2.0, 15.0), box(1.0, 11.0, 15.0, 2.0, 13.0, 17.0), box(1.0, 8.0, 15.0, 2.0, 10.0, 17.0)}
         );
         default -> Shapes.or(
            box(-2.0, 2.0, 2.0, 18.0, 16.0, 14.0),
            new VoxelShape[]{
               box(1.0, 0.0, 2.0, 15.0, 2.0, 14.0), box(15.0, 11.0, 14.0, 17.0, 13.0, 15.0), box(15.0, 8.0, 14.0, 17.0, 10.0, 15.0)
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

   public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter world, BlockPos pos, Player player) {
      return new ItemStack((ItemLike)BohModBlocks.ANALOG_TELEVISION.get());
   }

   public void onPlace(BlockState blockstate, Level world, BlockPos pos, BlockState oldState, boolean moving) {
      super.onPlace(blockstate, world, pos, oldState, moving);
      world.scheduleTick(pos, this, 20);
      AnalogTVStaticUpdateTickProcedure.execute(world, pos.getX(), pos.getY(), pos.getZ());
   }

   public void tick(BlockState blockstate, ServerLevel world, BlockPos pos, RandomSource random) {
      super.tick(blockstate, world, pos, random);
      int x = pos.getX();
      int y = pos.getY();
      int z = pos.getZ();
      AnalogTVSixUpdateTickProcedure.execute(world, x, y, z);
      world.scheduleTick(pos, this, 20);
   }

   public InteractionResult use(BlockState blockstate, Level world, BlockPos pos, Player entity, InteractionHand hand, BlockHitResult hit) {
      super.use(blockstate, world, pos, entity, hand, hit);
      int x = pos.getX();
      int y = pos.getY();
      int z = pos.getZ();
      double hitX = hit.getLocation().x;
      double hitY = hit.getLocation().y;
      double hitZ = hit.getLocation().z;
      Direction direction = hit.getDirection();
      AnalogTVStaticOnBlockRightClickedProcedure.execute(world, x, y, z);
      return InteractionResult.SUCCESS;
   }
}
