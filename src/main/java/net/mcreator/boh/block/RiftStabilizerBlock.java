package net.mcreator.boh.block;

import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.block.BohBlock;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.core.Direction;
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
import net.mcreator.boh.compat.mc.world.phys.shapes.CollisionContext;
import net.mcreator.boh.compat.mc.world.phys.shapes.Shapes;
import net.mcreator.boh.compat.mc.world.phys.shapes.VoxelShape;
import net.minecraft.block.Block;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class RiftStabilizerBlock extends BohBlock implements SimpleWaterloggedBlock {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public RiftStabilizerBlock() {
        super(
            Properties.of()
                .sound(SoundType.METAL)
                .strength(1.0F, 10.0F)
                .lightLevel(s -> 3)
                .noOcclusion()
                .hasPostProcess((bs, br, bp) -> true)
                .emissiveRendering((bs, br, bp) -> true)
                .isRedstoneConductor((bs, br, bp) -> false)
        );
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
        return Shapes.or(
            box(6.0, 12.0, 6.0, 10.0, 16.0, 10.0),
            box(6.0, 0.0, 6.0, 10.0, 5.0, 10.0),
            box(7.0, 5.0, 7.0, 9.0, 12.0, 9.0),
            box(6.8, 3.8, 6.8, 9.2, 11.2, 9.2),
            box(6.8, 9.7, 6.8, 9.2, 16.0, 9.2),
            box(5.8, 11.8, 5.8, 10.2, 16.0, 10.2)
        );
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
}
