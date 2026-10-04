package net.mcreator.boh.block;

import net.mcreator.boh.block.entity.ComputerBlockEntity;
import net.mcreator.boh.procedures.ComputerOnTickUpdateProcedure;
import net.mcreator.boh.world.inventory.ComputerGUIMenu;
import io.netty.buffer.Unpooled;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.core.Direction;
import net.mcreator.boh.compat.mc.network.FriendlyByteBuf;
import net.mcreator.boh.compat.mc.network.chat.Component;
import net.minecraft.world.WorldServer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.mcreator.boh.compat.mc.util.RandomSource;
import net.mcreator.boh.compat.mc.world.Containers;
import net.mcreator.boh.compat.mc.world.InteractionHand;
import net.mcreator.boh.compat.mc.world.InteractionResult;
import net.mcreator.boh.compat.mc.world.MenuProvider;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.mcreator.boh.compat.mc.world.inventory.AbstractContainerMenu;
import net.mcreator.boh.compat.mc.world.item.context.BlockPlaceContext;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.block.Block;
import net.mcreator.boh.compat.mc.world.level.block.EntityBlock;
import net.mcreator.boh.compat.mc.world.level.block.HorizontalDirectionalBlock;
import net.mcreator.boh.compat.mc.world.level.block.Mirror;
import net.mcreator.boh.compat.mc.world.level.block.Rotation;
import net.mcreator.boh.compat.mc.world.level.block.SimpleWaterloggedBlock;
import net.mcreator.boh.compat.mc.world.level.block.SoundType;
import net.minecraft.tileentity.TileEntity;
import net.mcreator.boh.compat.mc.world.level.block.state.BlockState;
import net.mcreator.boh.compat.mc.world.level.block.state.Properties;
import net.mcreator.boh.compat.mc.world.level.block.state.Builder;
import net.mcreator.boh.compat.mc.world.level.block.state.properties.BlockStateProperties;
import net.mcreator.boh.compat.mc.world.level.block.state.properties.BooleanProperty;
import net.mcreator.boh.compat.mc.world.level.block.state.properties.DirectionProperty;
import net.mcreator.boh.compat.mc.world.level.block.state.properties.IntegerProperty;
import net.mcreator.boh.compat.mc.world.level.block.state.properties.Property;
import net.mcreator.boh.compat.mc.world.level.material.FluidState;
import net.mcreator.boh.compat.mc.world.level.material.Fluids;
import net.mcreator.boh.compat.mc.world.phys.BlockHitResult;
import net.mcreator.boh.compat.mc.world.phys.shapes.CollisionContext;
import net.mcreator.boh.compat.mc.world.phys.shapes.Shapes;
import net.mcreator.boh.compat.mc.world.phys.shapes.VoxelShape;
import net.mcreator.boh.compat.forge.network.NetworkHooks;
import net.mcreator.boh.compat.block.BohBlock;
import net.mcreator.boh.compat.M;

public class ComputerBlock extends BohBlock implements SimpleWaterloggedBlock, EntityBlock {

    public static final IntegerProperty BLOCKSTATE = IntegerProperty.create("blockstate", 0, 6);

    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public static final IntegerProperty DOWNLOADING_TIME = IntegerProperty.create("downloading_time", 0, 23);

    public static final BooleanProperty COMPUTER_POWER = BooleanProperty.create("computer_power");

    public ComputerBlock() {
        super(Properties.of().sound(net.mcreator.boh.compat.mc.world.level.block.SoundType.METAL).strength(1.0F, 10.0F).lightLevel(s -> (new Object() {

            public int getLightLevel() {
                if ((Integer) s.getValue(ComputerBlock.BLOCKSTATE) == 1) {
                    return 0;
                } else if ((Integer) s.getValue(ComputerBlock.BLOCKSTATE) == 2) {
                    return 2;
                } else if ((Integer) s.getValue(ComputerBlock.BLOCKSTATE) == 3) {
                    return 2;
                } else if ((Integer) s.getValue(ComputerBlock.BLOCKSTATE) == 4) {
                    return 2;
                } else if ((Integer) s.getValue(ComputerBlock.BLOCKSTATE) == 5) {
                    return 2;
                } else {
                    return s.getValue(ComputerBlock.BLOCKSTATE) == 6 ? 0 : 0;
                }
            }
        }).getLightLevel()).noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
        M.registerDefaultState(this, (BlockState) M.setValue(((BlockState) M.setValue(((BlockState) M.setValue(((BlockState) M.setValue(((BlockState) M.any(this.stateDefinition)), FACING, Direction.NORTH)), DOWNLOADING_TIME, 0)), COMPUTER_POWER, true)), WATERLOGGED, false));
    }

    public boolean propagatesSkylightDown(BlockState state, IBlockAccess reader, BlockPos pos) {
        return M.isEmpty(M.getFluidState(state));
    }

    public int getLightBlock(BlockState state, IBlockAccess worldIn, BlockPos pos) {
        return 0;
    }

    public VoxelShape getVisualShape(BlockState state, IBlockAccess world, BlockPos pos, CollisionContext context) {
        return Shapes.empty();
    }

    public VoxelShape getShape(BlockState state, IBlockAccess world, BlockPos pos, CollisionContext context) {
        if ((Integer) state.getValue(BLOCKSTATE) == 1) {
            return switch((Direction) state.getValue(FACING)) {
                case NORTH ->
                    Shapes.or(box(0.0, 0.0, 0.0, 16.0, 3.0, 16.0), box(0.0, 3.0, 5.0, 16.0, 16.0, 16.0));
                case EAST ->
                    Shapes.or(box(0.0, 0.0, 0.0, 16.0, 3.0, 16.0), box(0.0, 3.0, 0.0, 11.0, 16.0, 16.0));
                case WEST ->
                    Shapes.or(box(0.0, 0.0, 0.0, 16.0, 3.0, 16.0), box(5.0, 3.0, 0.0, 16.0, 16.0, 16.0));
                default ->
                    Shapes.or(box(0.0, 0.0, 0.0, 16.0, 3.0, 16.0), box(0.0, 3.0, 0.0, 16.0, 16.0, 11.0));
            };
        } else if ((Integer) state.getValue(BLOCKSTATE) == 2) {
            return switch((Direction) state.getValue(FACING)) {
                case NORTH ->
                    Shapes.or(box(0.0, 0.0, 0.0, 16.0, 3.0, 16.0), box(0.0, 3.0, 5.0, 16.0, 16.0, 16.0));
                case EAST ->
                    Shapes.or(box(0.0, 0.0, 0.0, 16.0, 3.0, 16.0), box(0.0, 3.0, 0.0, 11.0, 16.0, 16.0));
                case WEST ->
                    Shapes.or(box(0.0, 0.0, 0.0, 16.0, 3.0, 16.0), box(5.0, 3.0, 0.0, 16.0, 16.0, 16.0));
                default ->
                    Shapes.or(box(0.0, 0.0, 0.0, 16.0, 3.0, 16.0), box(0.0, 3.0, 0.0, 16.0, 16.0, 11.0));
            };
        } else if ((Integer) state.getValue(BLOCKSTATE) == 3) {
            return switch((Direction) state.getValue(FACING)) {
                case NORTH ->
                    Shapes.or(box(0.0, 0.0, 0.0, 16.0, 3.0, 16.0), box(0.0, 3.0, 5.0, 16.0, 16.0, 16.0));
                case EAST ->
                    Shapes.or(box(0.0, 0.0, 0.0, 16.0, 3.0, 16.0), box(0.0, 3.0, 0.0, 11.0, 16.0, 16.0));
                case WEST ->
                    Shapes.or(box(0.0, 0.0, 0.0, 16.0, 3.0, 16.0), box(5.0, 3.0, 0.0, 16.0, 16.0, 16.0));
                default ->
                    Shapes.or(box(0.0, 0.0, 0.0, 16.0, 3.0, 16.0), box(0.0, 3.0, 0.0, 16.0, 16.0, 11.0));
            };
        } else if ((Integer) state.getValue(BLOCKSTATE) == 4) {
            return switch((Direction) state.getValue(FACING)) {
                case NORTH ->
                    Shapes.or(box(0.0, 0.0, 0.0, 16.0, 3.0, 16.0), box(0.0, 3.0, 5.0, 16.0, 16.0, 16.0));
                case EAST ->
                    Shapes.or(box(0.0, 0.0, 0.0, 16.0, 3.0, 16.0), box(0.0, 3.0, 0.0, 11.0, 16.0, 16.0));
                case WEST ->
                    Shapes.or(box(0.0, 0.0, 0.0, 16.0, 3.0, 16.0), box(5.0, 3.0, 0.0, 16.0, 16.0, 16.0));
                default ->
                    Shapes.or(box(0.0, 0.0, 0.0, 16.0, 3.0, 16.0), box(0.0, 3.0, 0.0, 16.0, 16.0, 11.0));
            };
        } else if ((Integer) state.getValue(BLOCKSTATE) == 5) {
            return switch((Direction) state.getValue(FACING)) {
                case NORTH ->
                    Shapes.or(box(0.0, 0.0, 0.0, 16.0, 3.0, 16.0), box(0.0, 3.0, 5.0, 16.0, 16.0, 16.0));
                case EAST ->
                    Shapes.or(box(0.0, 0.0, 0.0, 16.0, 3.0, 16.0), box(0.0, 3.0, 0.0, 11.0, 16.0, 16.0));
                case WEST ->
                    Shapes.or(box(0.0, 0.0, 0.0, 16.0, 3.0, 16.0), box(5.0, 3.0, 0.0, 16.0, 16.0, 16.0));
                default ->
                    Shapes.or(box(0.0, 0.0, 0.0, 16.0, 3.0, 16.0), box(0.0, 3.0, 0.0, 16.0, 16.0, 11.0));
            };
        } else if ((Integer) state.getValue(BLOCKSTATE) == 6) {
            return switch((Direction) state.getValue(FACING)) {
                case NORTH ->
                    Shapes.or(box(0.0, 0.0, 0.0, 16.0, 3.0, 16.0), box(0.0, 3.0, 5.0, 16.0, 16.0, 16.0));
                case EAST ->
                    Shapes.or(box(0.0, 0.0, 0.0, 16.0, 3.0, 16.0), box(0.0, 3.0, 0.0, 11.0, 16.0, 16.0));
                case WEST ->
                    Shapes.or(box(0.0, 0.0, 0.0, 16.0, 3.0, 16.0), box(5.0, 3.0, 0.0, 16.0, 16.0, 16.0));
                default ->
                    Shapes.or(box(0.0, 0.0, 0.0, 16.0, 3.0, 16.0), box(0.0, 3.0, 0.0, 16.0, 16.0, 11.0));
            };
        } else {
            return switch((Direction) state.getValue(FACING)) {
                case NORTH ->
                    Shapes.or(box(0.0, 0.0, 0.0, 16.0, 3.0, 16.0), box(0.0, 3.0, 5.0, 16.0, 16.0, 16.0));
                case EAST ->
                    Shapes.or(box(0.0, 0.0, 0.0, 16.0, 3.0, 16.0), box(0.0, 3.0, 0.0, 11.0, 16.0, 16.0));
                case WEST ->
                    Shapes.or(box(0.0, 0.0, 0.0, 16.0, 3.0, 16.0), box(5.0, 3.0, 0.0, 16.0, 16.0, 16.0));
                default ->
                    Shapes.or(box(0.0, 0.0, 0.0, 16.0, 3.0, 16.0), box(0.0, 3.0, 0.0, 16.0, 16.0, 11.0));
            };
        }
    }

    protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(new Property[] { FACING, DOWNLOADING_TIME, COMPUTER_POWER, WATERLOGGED, BLOCKSTATE });
    }

    public BlockState getStateForPlacement(BlockPlaceContext context) {
        boolean flag = M.getType(M.getFluidState(M.getLevel(context), M.getClickedPos(context))) == Fluids.WATER;
        return (BlockState) M.setValue(((BlockState) M.setValue(((BlockState) M.setValue(((BlockState) M.setValue(super.getStateForPlacement(context), FACING, M.getHorizontalDirection(context).getOpposite())), DOWNLOADING_TIME, 0)), COMPUTER_POWER, true)), WATERLOGGED, flag);
    }

    public BlockState rotate(BlockState state, Rotation rot) {
        return (BlockState) M.setValue(state, FACING, M.rotate(rot, (Direction) state.getValue(FACING)));
    }

    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return M.rotate(state, M.getRotation(mirrorIn, (Direction) state.getValue(FACING)));
    }

    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? M.getSource(Fluids.WATER, false) : super.getFluidState(state);
    }

    public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, World world, BlockPos currentPos, BlockPos facingPos) {
        if ((Boolean) state.getValue(WATERLOGGED)) {
            M.scheduleTick(world, currentPos, Fluids.WATER, M.getTickDelay(Fluids.WATER, world));
        }
        return super.updateShape(state, facing, facingState, world, currentPos, facingPos);
    }

    public void onPlace(BlockState blockstate, World world, BlockPos pos, BlockState oldState, boolean moving) {
        super.onPlace(blockstate, world, pos, oldState, moving);
        M.scheduleTick(world, pos, this, 15);
    }

    public void tick(BlockState blockstate, WorldServer world, BlockPos pos, RandomSource random) {
        super.tick(blockstate, world, pos, random);
        int x = M.getX(pos);
        int y = M.getY(pos);
        int z = M.getZ(pos);
        ComputerOnTickUpdateProcedure.execute(world, x, y, z, blockstate);
        M.scheduleTick(world, pos, this, 15);
    }

    public InteractionResult use(BlockState blockstate, World world, final BlockPos pos, EntityPlayer entity, InteractionHand hand, BlockHitResult hit) {
        super.use(blockstate, world, pos, entity, hand, hit);
        if (entity instanceof EntityPlayerMP player) {
            NetworkHooks.openScreen(player, new MenuProvider() {

                public Component getDisplayName() {
                    return Component.literal("GATE Computer");
                }

                public AbstractContainerMenu createMenu(int id, InventoryPlayer inventory, EntityPlayer playerx) {
                    return new ComputerGUIMenu(id, inventory, M.writeBlockPos(new FriendlyByteBuf(Unpooled.buffer()), pos));
                }
            }, pos);
        }
        return InteractionResult.SUCCESS;
    }

    public MenuProvider getMenuProvider(BlockState state, World worldIn, BlockPos pos) {
        return M.getBlockEntity(worldIn, pos) instanceof MenuProvider menuProvider ? menuProvider : null;
    }

    public TileEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new ComputerBlockEntity(pos, state);
    }

    public boolean triggerEvent(BlockState state, World world, BlockPos pos, int eventID, int eventParam) {
        super.triggerEvent(state, world, pos, eventID, eventParam);
        TileEntity blockEntity = M.getBlockEntity(world, pos);
        return blockEntity == null ? false : M.triggerEvent(blockEntity, eventID, eventParam);
    }

    public void onRemove(BlockState state, World world, BlockPos pos, BlockState newState, boolean isMoving) {
        if (M.getBlock(state) != M.getBlock(newState)) {
            if (M.getBlockEntity(world, pos) instanceof ComputerBlockEntity be) {
                Containers.dropContents(world, pos, be);
                M.updateNeighbourForOutputSignal(world, pos, this);
            }
            super.onRemove(state, world, pos, newState, isMoving);
        }
    }

    public boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    public int getAnalogOutputSignal(BlockState blockState, World world, BlockPos pos) {
        return M.getBlockEntity(world, pos) instanceof ComputerBlockEntity be ? AbstractContainerMenu.getRedstoneSignalFromContainer(be) : 0;
    }
}
