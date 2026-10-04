package net.mcreator.boh.block;

import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.block.BohBlock;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.core.Direction;
import net.mcreator.boh.compat.mc.util.RandomSource;
import net.mcreator.boh.compat.mc.world.InteractionHand;
import net.mcreator.boh.compat.mc.world.InteractionResult;
import net.mcreator.boh.compat.mc.world.item.context.BlockPlaceContext;
import net.mcreator.boh.compat.mc.world.level.block.HorizontalDirectionalBlock;
import net.mcreator.boh.compat.mc.world.level.block.Mirror;
import net.mcreator.boh.compat.mc.world.level.block.Rotation;
import net.mcreator.boh.compat.mc.world.level.block.SoundType;
import net.mcreator.boh.compat.mc.world.level.block.state.BlockState;
import net.mcreator.boh.compat.mc.world.level.block.state.Builder;
import net.mcreator.boh.compat.mc.world.level.block.state.Properties;
import net.mcreator.boh.compat.mc.world.level.block.state.properties.DirectionProperty;
import net.mcreator.boh.compat.mc.world.phys.BlockHitResult;
import net.mcreator.boh.compat.mc.world.phys.HitResult;
import net.mcreator.boh.compat.mc.world.phys.shapes.CollisionContext;
import net.mcreator.boh.compat.mc.world.phys.shapes.Shapes;
import net.mcreator.boh.compat.mc.world.phys.shapes.VoxelShape;
import net.mcreator.boh.init.BohModBlocks;
import net.mcreator.boh.procedures.AnalogTVSixUpdateTickProcedure;
import net.mcreator.boh.procedures.AnalogTVStaticOnBlockRightClickedProcedure;
import net.mcreator.boh.procedures.AnalogTVStaticUpdateTickProcedure;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class AnalogTVSixBlock extends BohBlock {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    public AnalogTVSixBlock() {
        super(Properties.of().sound(SoundType.WOOD).strength(1.0F, 10.0F).lightLevel(s -> 3).noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
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

    public ItemStack getCloneItemStack(BlockState state, HitResult target, IBlockAccess world, BlockPos pos, EntityPlayer player) {
        return M.new_ItemStack(BohModBlocks.ANALOG_TELEVISION.get());
    }

    @Override
    public void onPlace(BlockState blockstate, World world, BlockPos pos, BlockState oldState, boolean moving) {
        super.onPlace(blockstate, world, pos, oldState, moving);
        M.scheduleTick(world, pos, this, 20);
        AnalogTVStaticUpdateTickProcedure.execute(world, M.getX(pos), M.getY(pos), M.getZ(pos));
    }

    @Override
    public void tick(BlockState blockstate, WorldServer world, BlockPos pos, RandomSource random) {
        super.tick(blockstate, world, pos, random);
        int x = M.getX(pos);
        int y = M.getY(pos);
        int z = M.getZ(pos);
        AnalogTVSixUpdateTickProcedure.execute(world, x, y, z);
        M.scheduleTick(world, pos, this, 20);
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
        AnalogTVStaticOnBlockRightClickedProcedure.execute(world, x, y, z);
        return InteractionResult.SUCCESS;
    }
}
