package net.mcreator.boh.block;

import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.block.BohBlock;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.core.Direction;
import net.mcreator.boh.compat.mc.util.RandomSource;
import net.mcreator.boh.compat.mc.world.item.context.BlockPlaceContext;
import net.mcreator.boh.compat.mc.world.level.block.DirectionalBlock;
import net.mcreator.boh.compat.mc.world.level.block.Mirror;
import net.mcreator.boh.compat.mc.world.level.block.Rotation;
import net.mcreator.boh.compat.mc.world.level.block.SimpleWaterloggedBlock;
import net.mcreator.boh.compat.mc.world.level.block.SoundType;
import net.mcreator.boh.compat.mc.world.level.block.state.BlockState;
import net.mcreator.boh.compat.mc.world.level.block.state.Builder;
import net.mcreator.boh.compat.mc.world.level.block.state.Properties;
import net.mcreator.boh.compat.mc.world.level.block.state.properties.BlockStateProperties;
import net.mcreator.boh.compat.mc.world.level.block.state.properties.BooleanProperty;
import net.mcreator.boh.compat.mc.world.level.block.state.properties.DirectionProperty;
import net.mcreator.boh.compat.mc.world.level.block.state.properties.IntegerProperty;
import net.mcreator.boh.compat.mc.world.level.material.FluidState;
import net.mcreator.boh.compat.mc.world.level.material.Fluids;
import net.mcreator.boh.compat.mc.world.phys.shapes.CollisionContext;
import net.mcreator.boh.compat.mc.world.phys.shapes.Shapes;
import net.mcreator.boh.compat.mc.world.phys.shapes.VoxelShape;
import net.mcreator.boh.procedures.VitaCrawlOnTickUpdateProcedure;
import net.minecraft.block.Block;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class VitaCrawlBlock extends BohBlock implements SimpleWaterloggedBlock {
    public static final DirectionProperty FACING = DirectionalBlock.FACING;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final IntegerProperty TIMER_GROW = IntegerProperty.create("timer_grow", 0, 1000);

    public VitaCrawlBlock() {
        super(Properties.of().sound(SoundType.STEM).strength(0.5F, 5.0F).friction(0.4F).noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
        M.registerDefaultState(
            this, M.setValue(M.setValue(M.setValue(M.any(this.stateDefinition), FACING, Direction.NORTH), TIMER_GROW, 0), WATERLOGGED, false)
        );
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, IBlockAccess reader, BlockPos pos) {
        return M.isEmpty(M.getFluidState(state));
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
            case NORTH -> box(0.0, 0.0, 15.95, 16.0, 16.0, 16.0);
            case EAST -> box(0.0, 0.0, 0.0, 0.05, 16.0, 16.0);
            case WEST -> box(15.95, 0.0, 0.0, 16.0, 16.0, 16.0);
            case UP -> box(0.0, 0.0, 0.0, 16.0, 0.05, 16.0);
            case DOWN -> box(0.0, 15.95, 0.0, 16.0, 16.0, 16.0);
            default -> box(0.0, 0.0, 0.0, 16.0, 16.0, 0.05);
        };
    }

    @Override
    protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING, TIMER_GROW, WATERLOGGED);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        boolean flag = M.getType(M.getFluidState(M.getLevel(context), M.getClickedPos(context))) == Fluids.WATER;
        return M.setValue(
            M.setValue(M.setValue(super.getStateForPlacement(context), FACING, M.getNearestLookingDirection(context).getOpposite()), TIMER_GROW, 0),
            WATERLOGGED,
            flag
        );
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
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? M.getSource(Fluids.WATER, false) : super.getFluidState(state);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, World world, BlockPos currentPos, BlockPos facingPos) {
        if (state.getValue(WATERLOGGED)) {
            M.scheduleTick(world, currentPos, Fluids.WATER, M.getTickDelay(Fluids.WATER, world));
        }

        return super.updateShape(state, facing, facingState, world, currentPos, facingPos);
    }

    @Override
    public void onPlace(BlockState blockstate, World world, BlockPos pos, BlockState oldState, boolean moving) {
        super.onPlace(blockstate, world, pos, oldState, moving);
        M.scheduleTick(world, pos, this, 10);
    }

    @Override
    public void tick(BlockState blockstate, WorldServer world, BlockPos pos, RandomSource random) {
        super.tick(blockstate, world, pos, random);
        int x = M.getX(pos);
        int y = M.getY(pos);
        int z = M.getZ(pos);
        VitaCrawlOnTickUpdateProcedure.execute(world, x, y, z, blockstate);
        M.scheduleTick(world, pos, this, 10);
    }
}
