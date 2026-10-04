package net.mcreator.boh.block;

import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.block.BohBlock;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.core.Direction;
import net.mcreator.boh.compat.mc.world.item.context.BlockPlaceContext;
import net.mcreator.boh.compat.mc.world.level.block.HorizontalDirectionalBlock;
import net.mcreator.boh.compat.mc.world.level.block.Mirror;
import net.mcreator.boh.compat.mc.world.level.block.Rotation;
import net.mcreator.boh.compat.mc.world.level.block.SoundType;
import net.mcreator.boh.compat.mc.world.level.block.state.BlockState;
import net.mcreator.boh.compat.mc.world.level.block.state.Builder;
import net.mcreator.boh.compat.mc.world.level.block.state.Properties;
import net.mcreator.boh.compat.mc.world.level.block.state.properties.DirectionProperty;
import net.mcreator.boh.compat.mc.world.level.material.FluidState;
import net.mcreator.boh.compat.mc.world.phys.shapes.CollisionContext;
import net.mcreator.boh.compat.mc.world.phys.shapes.Shapes;
import net.mcreator.boh.compat.mc.world.phys.shapes.VoxelShape;
import net.mcreator.boh.procedures.VoodooDollBlockAddedProcedure;
import net.mcreator.boh.procedures.VoodooDollBlockDestroyedByPlayerProcedure;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class VoodooDollBlock extends BohBlock {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    public VoodooDollBlock() {
        super(Properties.of().ignitedByLava().sound(SoundType.VINE).strength(1.0F, 10.0F).noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
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
                box(7.0, 6.0, 14.0, 9.0, 9.0, 16.0),
                box(8.0, 4.0, 14.5, 9.0, 6.0, 15.5),
                box(9.0, 7.5, 14.75, 11.0, 8.5, 15.75),
                box(7.0, 4.0, 14.5, 8.0, 6.0, 15.5),
                box(5.0, 7.5, 14.75, 7.0, 8.5, 15.75),
                box(6.5, 9.0, 13.0, 9.5, 12.0, 16.0)
            );
            case EAST -> Shapes.or(
                box(0.0, 6.0, 7.0, 2.0, 9.0, 9.0),
                box(0.5, 4.0, 8.0, 1.5, 6.0, 9.0),
                box(0.25, 7.5, 9.0, 1.25, 8.5, 11.0),
                box(0.5, 4.0, 7.0, 1.5, 6.0, 8.0),
                box(0.25, 7.5, 5.0, 1.25, 8.5, 7.0),
                box(0.0, 9.0, 6.5, 3.0, 12.0, 9.5)
            );
            case WEST -> Shapes.or(
                box(14.0, 6.0, 7.0, 16.0, 9.0, 9.0),
                box(14.5, 4.0, 7.0, 15.5, 6.0, 8.0),
                box(14.75, 7.5, 5.0, 15.75, 8.5, 7.0),
                box(14.5, 4.0, 8.0, 15.5, 6.0, 9.0),
                box(14.75, 7.5, 9.0, 15.75, 8.5, 11.0),
                box(13.0, 9.0, 6.5, 16.0, 12.0, 9.5)
            );
            default -> Shapes.or(
                box(7.0, 6.0, 0.0, 9.0, 9.0, 2.0),
                box(7.0, 4.0, 0.5, 8.0, 6.0, 1.5),
                box(5.0, 7.5, 0.25, 7.0, 8.5, 1.25),
                box(8.0, 4.0, 0.5, 9.0, 6.0, 1.5),
                box(9.0, 7.5, 0.25, 11.0, 8.5, 1.25),
                box(6.5, 9.0, 0.0, 9.5, 12.0, 3.0)
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
    public void onPlace(BlockState blockstate, World world, BlockPos pos, BlockState oldState, boolean moving) {
        super.onPlace(blockstate, world, pos, oldState, moving);
        VoodooDollBlockAddedProcedure.execute(world, M.getX(pos), M.getY(pos), M.getZ(pos));
    }

    @Override
    public boolean onDestroyedByPlayer(BlockState blockstate, World world, BlockPos pos, EntityPlayer entity, boolean willHarvest, FluidState fluid) {
        boolean retval = super.onDestroyedByPlayer(blockstate, world, pos, entity, willHarvest, fluid);
        VoodooDollBlockDestroyedByPlayerProcedure.execute(world, M.getX(pos), M.getY(pos), M.getZ(pos));
        return retval;
    }
}
