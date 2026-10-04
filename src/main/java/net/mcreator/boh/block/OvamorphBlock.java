package net.mcreator.boh.block;

import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.block.BohBlock;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.core.Direction;
import net.mcreator.boh.compat.mc.util.RandomSource;
import net.mcreator.boh.compat.mc.world.item.context.BlockPlaceContext;
import net.mcreator.boh.compat.mc.world.level.block.SimpleWaterloggedBlock;
import net.mcreator.boh.compat.mc.world.level.block.SoundType;
import net.mcreator.boh.compat.mc.world.level.block.state.BlockState;
import net.mcreator.boh.compat.mc.world.level.block.state.Builder;
import net.mcreator.boh.compat.mc.world.level.block.state.Properties;
import net.mcreator.boh.compat.mc.world.level.block.state.properties.BlockStateProperties;
import net.mcreator.boh.compat.mc.world.level.block.state.properties.BooleanProperty;
import net.mcreator.boh.compat.mc.world.level.material.FluidState;
import net.mcreator.boh.compat.mc.world.level.material.Fluids;
import net.mcreator.boh.compat.mc.world.level.pathfinder.BlockPathTypes;
import net.mcreator.boh.compat.mc.world.phys.shapes.CollisionContext;
import net.mcreator.boh.compat.mc.world.phys.shapes.Shapes;
import net.mcreator.boh.compat.mc.world.phys.shapes.VoxelShape;
import net.mcreator.boh.procedures.OvamorphUpdateTickProcedure;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLiving;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class OvamorphBlock extends BohBlock implements SimpleWaterloggedBlock {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public OvamorphBlock() {
        super(Properties.of().sound(SoundType.NETHER_WART).strength(5.0F, 10.0F).noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
        M.registerDefaultState(this, M.setValue(M.any(this.stateDefinition), WATERLOGGED, false));
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
        return box(0.0, 0.0, 0.0, 16.0, 18.0, 16.0);
    }

    @Override
    protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(WATERLOGGED);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        boolean flag = M.getType(M.getFluidState(M.getLevel(context), M.getClickedPos(context))) == Fluids.WATER;
        return M.setValue(super.getStateForPlacement(context), WATERLOGGED, flag);
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

    public BlockPathTypes getBlockPathType(BlockState state, IBlockAccess world, BlockPos pos, EntityLiving entity) {
        return BlockPathTypes.FENCE;
    }

    @Override
    public void onPlace(BlockState blockstate, World world, BlockPos pos, BlockState oldState, boolean moving) {
        super.onPlace(blockstate, world, pos, oldState, moving);
        M.scheduleTick(world, pos, this, 20);
    }

    @Override
    public void tick(BlockState blockstate, WorldServer world, BlockPos pos, RandomSource random) {
        super.tick(blockstate, world, pos, random);
        int x = M.getX(pos);
        int y = M.getY(pos);
        int z = M.getZ(pos);
        OvamorphUpdateTickProcedure.execute(world, x, y, z);
        M.scheduleTick(world, pos, this, 20);
    }
}
