package net.mcreator.boh.block;

import java.util.List;
import net.mcreator.boh.procedures.DreamCatcherOnTickUpdateProcedure;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.core.Direction;
import net.mcreator.boh.compat.mc.network.chat.Component;
import net.minecraft.world.WorldServer;
import net.mcreator.boh.compat.mc.util.RandomSource;
import net.minecraft.item.ItemStack;
import net.mcreator.boh.compat.mc.world.item.TooltipFlag;
import net.mcreator.boh.compat.mc.world.item.context.BlockPlaceContext;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.block.Block;
import net.mcreator.boh.compat.mc.world.level.block.HorizontalDirectionalBlock;
import net.mcreator.boh.compat.mc.world.level.block.Mirror;
import net.mcreator.boh.compat.mc.world.level.block.Rotation;
import net.mcreator.boh.compat.mc.world.level.block.SimpleWaterloggedBlock;
import net.mcreator.boh.compat.mc.world.level.block.SoundType;
import net.mcreator.boh.compat.mc.world.level.block.state.BlockState;
import net.mcreator.boh.compat.mc.world.level.block.state.Properties;
import net.mcreator.boh.compat.mc.world.level.block.state.Builder;
import net.mcreator.boh.compat.mc.world.level.block.state.properties.BlockStateProperties;
import net.mcreator.boh.compat.mc.world.level.block.state.properties.BooleanProperty;
import net.mcreator.boh.compat.mc.world.level.block.state.properties.DirectionProperty;
import net.mcreator.boh.compat.mc.world.level.block.state.properties.Property;
import net.mcreator.boh.compat.mc.world.level.material.FluidState;
import net.mcreator.boh.compat.mc.world.level.material.Fluids;
import net.mcreator.boh.compat.mc.world.phys.shapes.CollisionContext;
import net.mcreator.boh.compat.mc.world.phys.shapes.Shapes;
import net.mcreator.boh.compat.mc.world.phys.shapes.VoxelShape;
import net.mcreator.boh.compat.block.BohBlock;
import net.mcreator.boh.compat.M;

public class DreamCatcherBlock extends BohBlock implements SimpleWaterloggedBlock {

    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public DreamCatcherBlock() {
        super(Properties.of().sound(net.mcreator.boh.compat.mc.world.level.block.SoundType.WOOD).strength(0.3F, 2.0F).noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
        M.registerDefaultState(this, (BlockState) M.setValue(((BlockState) M.setValue(((BlockState) M.any(this.stateDefinition)), FACING, Direction.NORTH)), WATERLOGGED, false));
    }

    public void appendHoverText(ItemStack itemstack, IBlockAccess level, List<Component> list, TooltipFlag flag) {
        super.appendHoverText(itemstack, level, list, flag);
        list.add(Component.translatable("block.boh.dream_catcher.description_0"));
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
        return switch((Direction) state.getValue(FACING)) {
            case NORTH ->
                Shapes.or(box(6.0, 10.0, 15.0, 10.0, 13.0, 16.0), new VoxelShape[] { box(5.0, 1.0, 15.0, 6.0, 12.0, 16.0), box(10.0, 2.0, 15.0, 11.0, 12.0, 16.0), box(4.0, 0.0, 15.0, 5.0, 11.0, 16.0), box(11.0, 1.0, 15.0, 12.0, 11.0, 16.0), box(6.0, 9.0, 15.0, 8.0, 10.0, 16.0), box(9.0, 0.0, 15.0, 10.0, 10.0, 16.0), box(6.0, 3.0, 15.0, 7.0, 9.0, 16.0), box(8.0, 1.0, 15.0, 9.0, 9.0, 16.0), box(7.0, 2.0, 15.0, 8.0, 8.0, 16.0), box(3.0, 4.0, 15.0, 4.0, 7.0, 16.0), box(12.0, 4.0, 15.0, 13.0, 7.0, 16.0), box(2.0, 3.0, 15.0, 3.0, 6.0, 16.0), box(13.0, 4.0, 15.0, 14.0, 6.0, 16.0), box(12.0, 0.0, 15.0, 13.0, 3.0, 16.0), box(13.0, 0.0, 15.0, 14.0, 2.0, 16.0) });
            case EAST ->
                Shapes.or(box(0.0, 10.0, 6.0, 1.0, 13.0, 10.0), new VoxelShape[] { box(0.0, 1.0, 5.0, 1.0, 12.0, 6.0), box(0.0, 2.0, 10.0, 1.0, 12.0, 11.0), box(0.0, 0.0, 4.0, 1.0, 11.0, 5.0), box(0.0, 1.0, 11.0, 1.0, 11.0, 12.0), box(0.0, 9.0, 6.0, 1.0, 10.0, 8.0), box(0.0, 0.0, 9.0, 1.0, 10.0, 10.0), box(0.0, 3.0, 6.0, 1.0, 9.0, 7.0), box(0.0, 1.0, 8.0, 1.0, 9.0, 9.0), box(0.0, 2.0, 7.0, 1.0, 8.0, 8.0), box(0.0, 4.0, 3.0, 1.0, 7.0, 4.0), box(0.0, 4.0, 12.0, 1.0, 7.0, 13.0), box(0.0, 3.0, 2.0, 1.0, 6.0, 3.0), box(0.0, 4.0, 13.0, 1.0, 6.0, 14.0), box(0.0, 0.0, 12.0, 1.0, 3.0, 13.0), box(0.0, 0.0, 13.0, 1.0, 2.0, 14.0) });
            case WEST ->
                Shapes.or(box(15.0, 10.0, 6.0, 16.0, 13.0, 10.0), new VoxelShape[] { box(15.0, 1.0, 10.0, 16.0, 12.0, 11.0), box(15.0, 2.0, 5.0, 16.0, 12.0, 6.0), box(15.0, 0.0, 11.0, 16.0, 11.0, 12.0), box(15.0, 1.0, 4.0, 16.0, 11.0, 5.0), box(15.0, 9.0, 8.0, 16.0, 10.0, 10.0), box(15.0, 0.0, 6.0, 16.0, 10.0, 7.0), box(15.0, 3.0, 9.0, 16.0, 9.0, 10.0), box(15.0, 1.0, 7.0, 16.0, 9.0, 8.0), box(15.0, 2.0, 8.0, 16.0, 8.0, 9.0), box(15.0, 4.0, 12.0, 16.0, 7.0, 13.0), box(15.0, 4.0, 3.0, 16.0, 7.0, 4.0), box(15.0, 3.0, 13.0, 16.0, 6.0, 14.0), box(15.0, 4.0, 2.0, 16.0, 6.0, 3.0), box(15.0, 0.0, 3.0, 16.0, 3.0, 4.0), box(15.0, 0.0, 2.0, 16.0, 2.0, 3.0) });
            default ->
                Shapes.or(box(6.0, 10.0, 0.0, 10.0, 13.0, 1.0), new VoxelShape[] { box(10.0, 1.0, 0.0, 11.0, 12.0, 1.0), box(5.0, 2.0, 0.0, 6.0, 12.0, 1.0), box(11.0, 0.0, 0.0, 12.0, 11.0, 1.0), box(4.0, 1.0, 0.0, 5.0, 11.0, 1.0), box(8.0, 9.0, 0.0, 10.0, 10.0, 1.0), box(6.0, 0.0, 0.0, 7.0, 10.0, 1.0), box(9.0, 3.0, 0.0, 10.0, 9.0, 1.0), box(7.0, 1.0, 0.0, 8.0, 9.0, 1.0), box(8.0, 2.0, 0.0, 9.0, 8.0, 1.0), box(12.0, 4.0, 0.0, 13.0, 7.0, 1.0), box(3.0, 4.0, 0.0, 4.0, 7.0, 1.0), box(13.0, 3.0, 0.0, 14.0, 6.0, 1.0), box(2.0, 4.0, 0.0, 3.0, 6.0, 1.0), box(3.0, 0.0, 0.0, 4.0, 3.0, 1.0), box(2.0, 0.0, 0.0, 3.0, 2.0, 1.0) });
        };
    }

    protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(new Property[] { FACING, WATERLOGGED });
    }

    public BlockState getStateForPlacement(BlockPlaceContext context) {
        boolean flag = M.getType(M.getFluidState(M.getLevel(context), M.getClickedPos(context))) == Fluids.WATER;
        return (BlockState) M.setValue(((BlockState) M.setValue(super.getStateForPlacement(context), FACING, M.getHorizontalDirection(context).getOpposite())), WATERLOGGED, flag);
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
        M.scheduleTick(world, pos, this, 1);
    }

    public void tick(BlockState blockstate, WorldServer world, BlockPos pos, RandomSource random) {
        super.tick(blockstate, world, pos, random);
        int x = M.getX(pos);
        int y = M.getY(pos);
        int z = M.getZ(pos);
        DreamCatcherOnTickUpdateProcedure.execute(world, x, y, z);
        M.scheduleTick(world, pos, this, 1);
    }
}
