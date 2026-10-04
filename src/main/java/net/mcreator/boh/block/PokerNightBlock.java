package net.mcreator.boh.block;

import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.mc.core.Axis;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.core.Direction;
import net.mcreator.boh.compat.mc.util.RandomSource;
import net.mcreator.boh.compat.mc.world.item.context.BlockPlaceContext;
import net.mcreator.boh.compat.mc.world.level.block.BaseEntityBlock;
import net.mcreator.boh.compat.mc.world.level.block.Blocks;
import net.mcreator.boh.compat.mc.world.level.block.EntityBlock;
import net.mcreator.boh.compat.mc.world.level.block.FaceAttachedHorizontalDirectionalBlock;
import net.mcreator.boh.compat.mc.world.level.block.HorizontalDirectionalBlock;
import net.mcreator.boh.compat.mc.world.level.block.Mirror;
import net.mcreator.boh.compat.mc.world.level.block.RenderShape;
import net.mcreator.boh.compat.mc.world.level.block.Rotation;
import net.mcreator.boh.compat.mc.world.level.block.SimpleWaterloggedBlock;
import net.mcreator.boh.compat.mc.world.level.block.SoundType;
import net.mcreator.boh.compat.mc.world.level.block.state.BlockState;
import net.mcreator.boh.compat.mc.world.level.block.state.Builder;
import net.mcreator.boh.compat.mc.world.level.block.state.Properties;
import net.mcreator.boh.compat.mc.world.level.block.state.properties.AttachFace;
import net.mcreator.boh.compat.mc.world.level.block.state.properties.BlockStateProperties;
import net.mcreator.boh.compat.mc.world.level.block.state.properties.BooleanProperty;
import net.mcreator.boh.compat.mc.world.level.block.state.properties.DirectionProperty;
import net.mcreator.boh.compat.mc.world.level.block.state.properties.EnumProperty;
import net.mcreator.boh.compat.mc.world.level.block.state.properties.IntegerProperty;
import net.mcreator.boh.compat.mc.world.level.material.FluidState;
import net.mcreator.boh.compat.mc.world.level.material.Fluids;
import net.mcreator.boh.compat.mc.world.phys.shapes.CollisionContext;
import net.mcreator.boh.compat.mc.world.phys.shapes.VoxelShape;
import net.mcreator.boh.init.BohModBlockEntities;
import net.mcreator.boh.procedures.PokerNightBlockDestroyedByPlayerProcedure;
import net.mcreator.boh.procedures.PokerNightOnTickUpdateProcedure;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class PokerNightBlock extends BaseEntityBlock implements SimpleWaterloggedBlock, EntityBlock {
    public static final IntegerProperty ANIMATION = IntegerProperty.create("animation", 0, 2);
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final EnumProperty<AttachFace> FACE = FaceAttachedHorizontalDirectionalBlock.FACE;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public PokerNightBlock() {
        super(Properties.of().sound(SoundType.WOOD).strength(1.0F, 10.0F).noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
        M.registerDefaultState(
            this, M.setValue(M.setValue(M.setValue(M.any(this.stateDefinition), FACING, Direction.NORTH), FACE, AttachFace.WALL), WATERLOGGED, false)
        );
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @Nullable
    @Override
    public TileEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return M.create(BohModBlockEntities.POKER_NIGHT.get(), blockPos, blockState);
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
    public VoxelShape getShape(BlockState state, IBlockAccess world, BlockPos pos, CollisionContext context) {
        return switch ((Direction)state.getValue(FACING)) {
            case NORTH -> {
                switch ((AttachFace)state.getValue(FACE)) {
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
            case EAST -> {
                switch ((AttachFace)state.getValue(FACE)) {
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
            case WEST -> {
                switch ((AttachFace)state.getValue(FACE)) {
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
            default -> {
                switch ((AttachFace)state.getValue(FACE)) {
                    case FLOOR:
                        yield box(0.0, 0.0, 0.0, 16.0, 19.0, 16.0);
                        break;
                    case WALL:
                        yield box(0.0, 0.0, 0.0, 16.0, 16.0, 19.0);
                        break;
                    case CEILING:
                        yield box(0.0, -3.0, 0.0, 16.0, 16.0, 16.0);
                        break;
                    default:
                        throw new IncompatibleClassChangeError();
                }
            }
        };
    }

    @Override
    protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
        builder.add(ANIMATION, FACING, FACE, WATERLOGGED);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        boolean flag = M.getType(M.getFluidState(M.getLevel(context), M.getClickedPos(context))) == Fluids.WATER;
        return M.setValue(
            M.setValue(
                M.setValue(M.defaultBlockState(this), FACE, this.faceForDirection(M.getNearestLookingDirection(context))),
                FACING,
                M.getHorizontalDirection(context).getOpposite()
            ),
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

    private AttachFace faceForDirection(Direction direction) {
        if (direction.getAxis() == Axis.Y) {
            return direction == Direction.UP ? AttachFace.CEILING : AttachFace.FLOOR;
        } else {
            return AttachFace.WALL;
        }
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
    public List<ItemStack> getDrops(BlockState state, Object builder) {
        List<ItemStack> dropsOriginal = super.getDrops(state, builder);
        return !M.isEmpty(dropsOriginal) ? dropsOriginal : Collections.singletonList(M.new_ItemStack(Blocks.AIR));
    }

    @Override
    public void onPlace(BlockState blockstate, World world, BlockPos pos, BlockState oldState, boolean moving) {
        super.onPlace(blockstate, world, pos, oldState, moving);
        M.scheduleTick(world, pos, this, 1);
    }

    @Override
    public void tick(BlockState blockstate, WorldServer world, BlockPos pos, RandomSource random) {
        super.tick(blockstate, world, pos, random);
        int x = M.getX(pos);
        int y = M.getY(pos);
        int z = M.getZ(pos);
        PokerNightOnTickUpdateProcedure.execute(world, x, y, z);
        M.scheduleTick(world, pos, this, 1);
    }

    @Override
    public boolean onDestroyedByPlayer(BlockState blockstate, World world, BlockPos pos, EntityPlayer entity, boolean willHarvest, FluidState fluid) {
        boolean retval = super.onDestroyedByPlayer(blockstate, world, pos, entity, willHarvest, fluid);
        PokerNightBlockDestroyedByPlayerProcedure.execute(world, M.getX(pos), M.getY(pos), M.getZ(pos));
        return retval;
    }

    @Override
    public void wasExploded(World world, BlockPos pos, Explosion e) {
        super.wasExploded(world, pos, e);
        PokerNightBlockDestroyedByPlayerProcedure.execute(world, M.getX(pos), M.getY(pos), M.getZ(pos));
    }
}
