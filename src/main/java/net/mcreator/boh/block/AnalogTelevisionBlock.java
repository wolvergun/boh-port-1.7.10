package net.mcreator.boh.block;

import net.mcreator.boh.block.entity.AnalogTelevisionBlockEntity;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.block.BohBlock;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.core.Direction;
import net.mcreator.boh.compat.mc.util.RandomSource;
import net.mcreator.boh.compat.mc.world.Containers;
import net.mcreator.boh.compat.mc.world.InteractionHand;
import net.mcreator.boh.compat.mc.world.InteractionResult;
import net.mcreator.boh.compat.mc.world.MenuProvider;
import net.mcreator.boh.compat.mc.world.inventory.AbstractContainerMenu;
import net.mcreator.boh.compat.mc.world.item.context.BlockPlaceContext;
import net.mcreator.boh.compat.mc.world.level.block.EntityBlock;
import net.mcreator.boh.compat.mc.world.level.block.HorizontalDirectionalBlock;
import net.mcreator.boh.compat.mc.world.level.block.Mirror;
import net.mcreator.boh.compat.mc.world.level.block.Rotation;
import net.mcreator.boh.compat.mc.world.level.block.SoundType;
import net.mcreator.boh.compat.mc.world.level.block.state.BlockState;
import net.mcreator.boh.compat.mc.world.level.block.state.Builder;
import net.mcreator.boh.compat.mc.world.level.block.state.Properties;
import net.mcreator.boh.compat.mc.world.level.block.state.properties.DirectionProperty;
import net.mcreator.boh.compat.mc.world.level.block.state.properties.NoteBlockInstrument;
import net.mcreator.boh.compat.mc.world.phys.BlockHitResult;
import net.mcreator.boh.compat.mc.world.phys.shapes.CollisionContext;
import net.mcreator.boh.compat.mc.world.phys.shapes.Shapes;
import net.mcreator.boh.compat.mc.world.phys.shapes.VoxelShape;
import net.mcreator.boh.procedures.AnalogTVOnBlockRightClickedProcedure;
import net.mcreator.boh.procedures.AnalogTelevisionUpdateTickProcedure;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class AnalogTelevisionBlock extends BohBlock implements EntityBlock {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    public AnalogTelevisionBlock() {
        super(
            Properties.of()
                .instrument(NoteBlockInstrument.BASEDRUM)
                .sound(SoundType.WOOD)
                .strength(1.0F, 10.0F)
                .noOcclusion()
                .randomTicks()
                .isRedstoneConductor((bs, br, bp) -> false)
        );
        M.registerDefaultState(this, M.setValue(M.any(this.stateDefinition), FACING, Direction.NORTH));
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, IBlockAccess reader, BlockPos pos) {
        return true;
    }

    @Override
    public int getLightBlock(BlockState state, IBlockAccess worldIn, BlockPos pos) {
        return 0;
    }

    @Override
    public VoxelShape getVisualShape(BlockState state, IBlockAccess world, BlockPos pos, CollisionContext context) {
        return Shapes.empty();
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockAccess world, BlockPos pos, CollisionContext context) {
        return switch ((Direction)state.getValue(FACING)) {
            case NORTH -> Shapes.or(
                box(-2.0, 2.0, 2.0, 18.0, 16.0, 14.0),
                box(1.0, 0.0, 2.0, 15.0, 2.0, 14.0),
                box(-1.0, 11.0, 1.0, 1.0, 13.0, 2.0),
                box(-1.0, 8.0, 1.0, 1.0, 10.0, 2.0)
            );
            case EAST -> Shapes.or(
                box(2.0, 2.0, -2.0, 14.0, 16.0, 18.0),
                box(2.0, 0.0, 1.0, 14.0, 2.0, 15.0),
                box(14.0, 11.0, -1.0, 15.0, 13.0, 1.0),
                box(14.0, 8.0, -1.0, 15.0, 10.0, 1.0)
            );
            case WEST -> Shapes.or(
                box(2.0, 2.0, -2.0, 14.0, 16.0, 18.0),
                box(2.0, 0.0, 1.0, 14.0, 2.0, 15.0),
                box(1.0, 11.0, 15.0, 2.0, 13.0, 17.0),
                box(1.0, 8.0, 15.0, 2.0, 10.0, 17.0)
            );
            default -> Shapes.or(
                box(-2.0, 2.0, 2.0, 18.0, 16.0, 14.0),
                box(1.0, 0.0, 2.0, 15.0, 2.0, 14.0),
                box(15.0, 11.0, 14.0, 17.0, 13.0, 15.0),
                box(15.0, 8.0, 14.0, 17.0, 10.0, 15.0)
            );
        };
    }

    @Override
    protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return M.setValue(super.getStateForPlacement(context), FACING, M.getHorizontalDirection(context).getOpposite());
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rot) {
        return M.setValue(state, FACING, M.rotate(rot, state.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return M.rotate(state, M.getRotation(mirrorIn, state.getValue(FACING)));
    }

    @Override
    public void tick(BlockState blockstate, WorldServer world, BlockPos pos, RandomSource random) {
        super.tick(blockstate, world, pos, random);
        int x = M.getX(pos);
        int y = M.getY(pos);
        int z = M.getZ(pos);
        AnalogTelevisionUpdateTickProcedure.execute(world, x, y, z);
    }

    @Override
    public InteractionResult use(BlockState blockstate, World world, BlockPos pos, EntityPlayer entity, InteractionHand hand, BlockHitResult hit) {
        super.use(blockstate, world, pos, entity, hand, hit);
        int x = M.getX(pos);
        int y = M.getY(pos);
        int z = M.getZ(pos);
        double hitX = M.getLocation(hit).x;
        double hitY = M.getLocation(hit).y;
        double hitZ = M.getLocation(hit).z;
        Direction direction = M.getDirection(hit);
        AnalogTVOnBlockRightClickedProcedure.execute(world, x, y, z, entity);
        return InteractionResult.SUCCESS;
    }

    public MenuProvider getMenuProvider(BlockState state, World worldIn, BlockPos pos) {
        return M.getBlockEntity(worldIn, pos) instanceof MenuProvider menuProvider ? menuProvider : null;
    }

    @Override
    public TileEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new AnalogTelevisionBlockEntity(pos, state);
    }

    @Override
    public boolean triggerEvent(BlockState state, World world, BlockPos pos, int eventID, int eventParam) {
        super.triggerEvent(state, world, pos, eventID, eventParam);
        TileEntity blockEntity = M.getBlockEntity(world, pos);
        return blockEntity == null ? false : M.triggerEvent(blockEntity, eventID, eventParam);
    }

    @Override
    public void onRemove(BlockState state, World world, BlockPos pos, BlockState newState, boolean isMoving) {
        if (M.getBlock(state) != M.getBlock(newState)) {
            if (M.getBlockEntity(world, pos) instanceof AnalogTelevisionBlockEntity be) {
                Containers.dropContents(world, pos, be);
                M.updateNeighbourForOutputSignal(world, pos, this);
            }

            super.onRemove(state, world, pos, newState, isMoving);
        }
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(BlockState blockState, World world, BlockPos pos) {
        return M.getBlockEntity(world, pos) instanceof AnalogTelevisionBlockEntity be ? AbstractContainerMenu.getRedstoneSignalFromContainer(be) : 0;
    }
}
