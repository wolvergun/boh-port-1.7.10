package net.mcreator.boh.block;

import io.netty.buffer.Unpooled;
import net.mcreator.boh.block.entity.ComputerBlockEntity;
import net.mcreator.boh.procedures.ComputerOnTickUpdateProcedure;
import net.mcreator.boh.world.inventory.ComputerGUIMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;

public class ComputerBlock extends Block implements SimpleWaterloggedBlock, EntityBlock {
   public static final IntegerProperty BLOCKSTATE = IntegerProperty.create("blockstate", 0, 6);
   public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
   public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
   public static final IntegerProperty DOWNLOADING_TIME = IntegerProperty.create("downloading_time", 0, 23);
   public static final BooleanProperty COMPUTER_POWER = BooleanProperty.create("computer_power");

   public ComputerBlock() {
      super(Properties.of().sound(SoundType.METAL).strength(1.0F, 10.0F).lightLevel(s -> (new Object() {
         public int getLightLevel() {
            if ((Integer)s.getValue(ComputerBlock.BLOCKSTATE) == 1) {
               return 0;
            } else if ((Integer)s.getValue(ComputerBlock.BLOCKSTATE) == 2) {
               return 2;
            } else if ((Integer)s.getValue(ComputerBlock.BLOCKSTATE) == 3) {
               return 2;
            } else if ((Integer)s.getValue(ComputerBlock.BLOCKSTATE) == 4) {
               return 2;
            } else if ((Integer)s.getValue(ComputerBlock.BLOCKSTATE) == 5) {
               return 2;
            } else {
               return s.getValue(ComputerBlock.BLOCKSTATE) == 6 ? 0 : 0;
            }
         }
      }).getLightLevel()).noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
      this.registerDefaultState(
         (BlockState)((BlockState)((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue(FACING, Direction.NORTH))
                  .setValue(DOWNLOADING_TIME, 0))
               .setValue(COMPUTER_POWER, true))
            .setValue(WATERLOGGED, false)
      );
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
      if ((Integer)state.getValue(BLOCKSTATE) == 1) {
         return switch ((Direction)state.getValue(FACING)) {
            case NORTH -> Shapes.or(box(0.0, 0.0, 0.0, 16.0, 3.0, 16.0), box(0.0, 3.0, 5.0, 16.0, 16.0, 16.0));
            case EAST -> Shapes.or(box(0.0, 0.0, 0.0, 16.0, 3.0, 16.0), box(0.0, 3.0, 0.0, 11.0, 16.0, 16.0));
            case WEST -> Shapes.or(box(0.0, 0.0, 0.0, 16.0, 3.0, 16.0), box(5.0, 3.0, 0.0, 16.0, 16.0, 16.0));
            default -> Shapes.or(box(0.0, 0.0, 0.0, 16.0, 3.0, 16.0), box(0.0, 3.0, 0.0, 16.0, 16.0, 11.0));
         };
      } else if ((Integer)state.getValue(BLOCKSTATE) == 2) {
         return switch ((Direction)state.getValue(FACING)) {
            case NORTH -> Shapes.or(box(0.0, 0.0, 0.0, 16.0, 3.0, 16.0), box(0.0, 3.0, 5.0, 16.0, 16.0, 16.0));
            case EAST -> Shapes.or(box(0.0, 0.0, 0.0, 16.0, 3.0, 16.0), box(0.0, 3.0, 0.0, 11.0, 16.0, 16.0));
            case WEST -> Shapes.or(box(0.0, 0.0, 0.0, 16.0, 3.0, 16.0), box(5.0, 3.0, 0.0, 16.0, 16.0, 16.0));
            default -> Shapes.or(box(0.0, 0.0, 0.0, 16.0, 3.0, 16.0), box(0.0, 3.0, 0.0, 16.0, 16.0, 11.0));
         };
      } else if ((Integer)state.getValue(BLOCKSTATE) == 3) {
         return switch ((Direction)state.getValue(FACING)) {
            case NORTH -> Shapes.or(box(0.0, 0.0, 0.0, 16.0, 3.0, 16.0), box(0.0, 3.0, 5.0, 16.0, 16.0, 16.0));
            case EAST -> Shapes.or(box(0.0, 0.0, 0.0, 16.0, 3.0, 16.0), box(0.0, 3.0, 0.0, 11.0, 16.0, 16.0));
            case WEST -> Shapes.or(box(0.0, 0.0, 0.0, 16.0, 3.0, 16.0), box(5.0, 3.0, 0.0, 16.0, 16.0, 16.0));
            default -> Shapes.or(box(0.0, 0.0, 0.0, 16.0, 3.0, 16.0), box(0.0, 3.0, 0.0, 16.0, 16.0, 11.0));
         };
      } else if ((Integer)state.getValue(BLOCKSTATE) == 4) {
         return switch ((Direction)state.getValue(FACING)) {
            case NORTH -> Shapes.or(box(0.0, 0.0, 0.0, 16.0, 3.0, 16.0), box(0.0, 3.0, 5.0, 16.0, 16.0, 16.0));
            case EAST -> Shapes.or(box(0.0, 0.0, 0.0, 16.0, 3.0, 16.0), box(0.0, 3.0, 0.0, 11.0, 16.0, 16.0));
            case WEST -> Shapes.or(box(0.0, 0.0, 0.0, 16.0, 3.0, 16.0), box(5.0, 3.0, 0.0, 16.0, 16.0, 16.0));
            default -> Shapes.or(box(0.0, 0.0, 0.0, 16.0, 3.0, 16.0), box(0.0, 3.0, 0.0, 16.0, 16.0, 11.0));
         };
      } else if ((Integer)state.getValue(BLOCKSTATE) == 5) {
         return switch ((Direction)state.getValue(FACING)) {
            case NORTH -> Shapes.or(box(0.0, 0.0, 0.0, 16.0, 3.0, 16.0), box(0.0, 3.0, 5.0, 16.0, 16.0, 16.0));
            case EAST -> Shapes.or(box(0.0, 0.0, 0.0, 16.0, 3.0, 16.0), box(0.0, 3.0, 0.0, 11.0, 16.0, 16.0));
            case WEST -> Shapes.or(box(0.0, 0.0, 0.0, 16.0, 3.0, 16.0), box(5.0, 3.0, 0.0, 16.0, 16.0, 16.0));
            default -> Shapes.or(box(0.0, 0.0, 0.0, 16.0, 3.0, 16.0), box(0.0, 3.0, 0.0, 16.0, 16.0, 11.0));
         };
      } else if ((Integer)state.getValue(BLOCKSTATE) == 6) {
         return switch ((Direction)state.getValue(FACING)) {
            case NORTH -> Shapes.or(box(0.0, 0.0, 0.0, 16.0, 3.0, 16.0), box(0.0, 3.0, 5.0, 16.0, 16.0, 16.0));
            case EAST -> Shapes.or(box(0.0, 0.0, 0.0, 16.0, 3.0, 16.0), box(0.0, 3.0, 0.0, 11.0, 16.0, 16.0));
            case WEST -> Shapes.or(box(0.0, 0.0, 0.0, 16.0, 3.0, 16.0), box(5.0, 3.0, 0.0, 16.0, 16.0, 16.0));
            default -> Shapes.or(box(0.0, 0.0, 0.0, 16.0, 3.0, 16.0), box(0.0, 3.0, 0.0, 16.0, 16.0, 11.0));
         };
      } else {
         return switch ((Direction)state.getValue(FACING)) {
            case NORTH -> Shapes.or(box(0.0, 0.0, 0.0, 16.0, 3.0, 16.0), box(0.0, 3.0, 5.0, 16.0, 16.0, 16.0));
            case EAST -> Shapes.or(box(0.0, 0.0, 0.0, 16.0, 3.0, 16.0), box(0.0, 3.0, 0.0, 11.0, 16.0, 16.0));
            case WEST -> Shapes.or(box(0.0, 0.0, 0.0, 16.0, 3.0, 16.0), box(5.0, 3.0, 0.0, 16.0, 16.0, 16.0));
            default -> Shapes.or(box(0.0, 0.0, 0.0, 16.0, 3.0, 16.0), box(0.0, 3.0, 0.0, 16.0, 16.0, 11.0));
         };
      }
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      super.createBlockStateDefinition(builder);
      builder.add(new Property[]{FACING, DOWNLOADING_TIME, COMPUTER_POWER, WATERLOGGED, BLOCKSTATE});
   }

   public BlockState getStateForPlacement(BlockPlaceContext context) {
      boolean flag = context.getLevel().getFluidState(context.getClickedPos()).getType() == Fluids.WATER;
      return (BlockState)((BlockState)((BlockState)((BlockState)super.getStateForPlacement(context).setValue(FACING, context.getHorizontalDirection().getOpposite()))
               .setValue(DOWNLOADING_TIME, 0))
            .setValue(COMPUTER_POWER, true))
         .setValue(WATERLOGGED, flag);
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
      world.scheduleTick(pos, this, 15);
   }

   public void tick(BlockState blockstate, ServerLevel world, BlockPos pos, RandomSource random) {
      super.tick(blockstate, world, pos, random);
      int x = pos.getX();
      int y = pos.getY();
      int z = pos.getZ();
      ComputerOnTickUpdateProcedure.execute(world, x, y, z, blockstate);
      world.scheduleTick(pos, this, 15);
   }

   public InteractionResult use(BlockState blockstate, Level world, final BlockPos pos, Player entity, InteractionHand hand, BlockHitResult hit) {
      super.use(blockstate, world, pos, entity, hand, hit);
      if (entity instanceof ServerPlayer player) {
         NetworkHooks.openScreen(player, new MenuProvider() {
            public Component getDisplayName() {
               return Component.literal("GATE Computer");
            }

            public AbstractContainerMenu createMenu(int id, Inventory inventory, Player playerx) {
               return new ComputerGUIMenu(id, inventory, new FriendlyByteBuf(Unpooled.buffer()).writeBlockPos(pos));
            }
         }, pos);
      }

      return InteractionResult.SUCCESS;
   }

   public MenuProvider getMenuProvider(BlockState state, Level worldIn, BlockPos pos) {
      return worldIn.getBlockEntity(pos) instanceof MenuProvider menuProvider ? menuProvider : null;
   }

   public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
      return new ComputerBlockEntity(pos, state);
   }

   public boolean triggerEvent(BlockState state, Level world, BlockPos pos, int eventID, int eventParam) {
      super.triggerEvent(state, world, pos, eventID, eventParam);
      BlockEntity blockEntity = world.getBlockEntity(pos);
      return blockEntity == null ? false : blockEntity.triggerEvent(eventID, eventParam);
   }

   public void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean isMoving) {
      if (state.getBlock() != newState.getBlock()) {
         if (world.getBlockEntity(pos) instanceof ComputerBlockEntity be) {
            Containers.dropContents(world, pos, be);
            world.updateNeighbourForOutputSignal(pos, this);
         }

         super.onRemove(state, world, pos, newState, isMoving);
      }
   }

   public boolean hasAnalogOutputSignal(BlockState state) {
      return true;
   }

   public int getAnalogOutputSignal(BlockState blockState, Level world, BlockPos pos) {
      return world.getBlockEntity(pos) instanceof ComputerBlockEntity be ? AbstractContainerMenu.getRedstoneSignalFromContainer(be) : 0;
   }
}
