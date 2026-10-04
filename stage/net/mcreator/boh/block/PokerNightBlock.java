package net.mcreator.boh.block;

import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;
import net.mcreator.boh.init.BohModBlockEntities;
import net.mcreator.boh.procedures.PokerNightBlockDestroyedByPlayerProcedure;
import net.mcreator.boh.procedures.PokerNightOnTickUpdateProcedure;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.core.Direction;
import net.mcreator.boh.compat.mc.core.Axis;
import net.minecraft.world.WorldServer;
import net.mcreator.boh.compat.mc.util.RandomSource;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.mcreator.boh.compat.mc.world.item.context.BlockPlaceContext;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.mcreator.boh.compat.mc.world.level.block.BaseEntityBlock;
import net.minecraft.block.Block;
import net.mcreator.boh.compat.mc.world.level.block.Blocks;
import net.mcreator.boh.compat.mc.world.level.block.EntityBlock;
import net.mcreator.boh.compat.mc.world.level.block.FaceAttachedHorizontalDirectionalBlock;
import net.mcreator.boh.compat.mc.world.level.block.HorizontalDirectionalBlock;
import net.mcreator.boh.compat.mc.world.level.block.Mirror;
import net.mcreator.boh.compat.mc.world.level.block.RenderShape;
import net.mcreator.boh.compat.mc.world.level.block.Rotation;
import net.mcreator.boh.compat.mc.world.level.block.SimpleWaterloggedBlock;
import net.mcreator.boh.compat.mc.world.level.block.SoundType;
import net.minecraft.tileentity.TileEntity;
import net.mcreator.boh.compat.mc.world.level.block.entity.BlockEntityType;
import net.mcreator.boh.compat.mc.world.level.block.state.BlockState;
import net.mcreator.boh.compat.mc.world.level.block.state.Properties;
import net.mcreator.boh.compat.mc.world.level.block.state.Builder;
import net.mcreator.boh.compat.mc.world.level.block.state.properties.AttachFace;
import net.mcreator.boh.compat.mc.world.level.block.state.properties.BlockStateProperties;
import net.mcreator.boh.compat.mc.world.level.block.state.properties.BooleanProperty;
import net.mcreator.boh.compat.mc.world.level.block.state.properties.DirectionProperty;
import net.mcreator.boh.compat.mc.world.level.block.state.properties.EnumProperty;
import net.mcreator.boh.compat.mc.world.level.block.state.properties.IntegerProperty;
import net.mcreator.boh.compat.mc.world.level.block.state.properties.Property;
import net.mcreator.boh.compat.mc.world.level.material.FluidState;
import net.mcreator.boh.compat.mc.world.level.material.Fluids;
import net.mcreator.boh.compat.mc.world.phys.shapes.CollisionContext;
import net.mcreator.boh.compat.mc.world.phys.shapes.VoxelShape;
import net.mcreator.boh.compat.M;

public class PokerNightBlock extends BaseEntityBlock implements SimpleWaterloggedBlock, EntityBlock {

    public static final IntegerProperty ANIMATION = IntegerProperty.create("animation", 0, 2);

    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    public static final EnumProperty<AttachFace> FACE = FaceAttachedHorizontalDirectionalBlock.FACE;

    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public PokerNightBlock() {
        super(Properties.of().sound(net.mcreator.boh.compat.mc.world.level.block.SoundType.WOOD).strength(1.0F, 10.0F).noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
        M.registerDefaultState(this, (BlockState) M.setValue(((BlockState) M.setValue(((BlockState) M.setValue(((BlockState) M.any(this.stateDefinition)), FACING, Direction.NORTH)), FACE, AttachFace.WALL)), WATERLOGGED, false));
    }

    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @Nullable
    public TileEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return M.create(((BlockEntityType) BohModBlockEntities.POKER_NIGHT.get()), blockPos, blockState);
    }

    public boolean propagatesSkylightDown(BlockState state, IBlockAccess reader, BlockPos pos) {
        return M.isEmpty(M.getFluidState(state));
    }

    public int getLightBlock(BlockState state, IBlockAccess worldIn, BlockPos pos) {
        return 0;
    }

    public VoxelShape getShape(BlockState state, IBlockAccess world, BlockPos pos, CollisionContext context) {
        return switch((Direction) state.getValue(FACING)) {
            case NORTH ->
                {
                    switch((AttachFace) state.getValue(FACE)) {
                        case FLOOR:
                            yield box(0.0, 0.0, 0.0, 16.0, 19.0, 16.0);
                        case WALL:
                            yield box(0.0, 0.0, -3.0, 16.0, 16.0, 16.0);
                        case CEILING:
                            yield box(0.0, -3.0, 0.0, 16.0, 16.0, 16.0);
                        default:
                            throw new IncompatibleClassChangeError();
                    }
                }
            case EAST ->
                {
                    switch((AttachFace) state.getValue(FACE)) {
                        case FLOOR:
                            yield box(0.0, 0.0, 0.0, 16.0, 19.0, 16.0);
                        case WALL:
                            yield box(0.0, 0.0, 0.0, 19.0, 16.0, 16.0);
                        case CEILING:
                            yield box(0.0, -3.0, 0.0, 16.0, 16.0, 16.0);
                        default:
                            throw new IncompatibleClassChangeError();
                    }
                }
            case WEST ->
                {
                    switch((AttachFace) state.getValue(FACE)) {
                        case FLOOR:
                            yield box(0.0, 0.0, 0.0, 16.0, 19.0, 16.0);
                        case WALL:
                            yield box(-3.0, 0.0, 0.0, 16.0, 16.0, 16.0);
                        case CEILING:
                            yield box(0.0, -3.0, 0.0, 16.0, 16.0, 16.0);
                        default:
                            throw new IncompatibleClassChangeError();
                    }
                }
            default ->
                {
                    switch((AttachFace) state.getValue(FACE)) {
                        case FLOOR:
                            yield box(0.0, 0.0, 0.0, 16.0, 19.0, 16.0);
                        case WALL:
                            yield box(0.0, 0.0, 0.0, 16.0, 16.0, 19.0);
                        case CEILING:
                            yield box(0.0, -3.0, 0.0, 16.0, 16.0, 16.0);
                        default:
                            throw new IncompatibleClassChangeError();
                    }
                }
        };
    }

    protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
        builder.add(new Property[] { ANIMATION, FACING, FACE, WATERLOGGED });
    }

    public BlockState getStateForPlacement(BlockPlaceContext context) {
        boolean flag = M.getType(M.getFluidState(M.getLevel(context), M.getClickedPos(context))) == Fluids.WATER;
        return (BlockState) M.setValue(((BlockState) M.setValue(((BlockState) M.setValue(M.defaultBlockState(this), FACE, this.faceForDirection(M.getNearestLookingDirection(context)))), FACING, M.getHorizontalDirection(context).getOpposite())), WATERLOGGED, flag);
    }

    public BlockState rotate(BlockState state, Rotation rot) {
        return (BlockState) M.setValue(state, FACING, M.rotate(rot, (Direction) state.getValue(FACING)));
    }

    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return M.rotate(state, M.getRotation(mirrorIn, (Direction) state.getValue(FACING)));
    }

    private AttachFace faceForDirection(Direction direction) {
        if (direction.getAxis() == Axis.Y) {
            return direction == Direction.UP ? AttachFace.CEILING : AttachFace.FLOOR;
        } else {
            return AttachFace.WALL;
        }
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

    public List<ItemStack> getDrops(BlockState state, Object builder) {
        List<ItemStack> dropsOriginal = super.getDrops(state, builder);
        return !M.isEmpty(dropsOriginal) ? dropsOriginal : Collections.singletonList(M.new_ItemStack(Blocks.AIR));
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
        PokerNightOnTickUpdateProcedure.execute(world, x, y, z);
        M.scheduleTick(world, pos, this, 1);
    }

    public boolean onDestroyedByPlayer(BlockState blockstate, World world, BlockPos pos, EntityPlayer entity, boolean willHarvest, FluidState fluid) {
        boolean retval = super.onDestroyedByPlayer(blockstate, world, pos, entity, willHarvest, fluid);
        PokerNightBlockDestroyedByPlayerProcedure.execute(world, M.getX(pos), M.getY(pos), M.getZ(pos));
        return retval;
    }

    public void wasExploded(World world, BlockPos pos, Explosion e) {
        super.wasExploded(world, pos, e);
        PokerNightBlockDestroyedByPlayerProcedure.execute(world, M.getX(pos), M.getY(pos), M.getZ(pos));
    }
}
