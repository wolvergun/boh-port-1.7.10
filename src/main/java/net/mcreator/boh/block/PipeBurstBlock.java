package net.mcreator.boh.block;

import net.mcreator.boh.block.entity.PipeBurstBlockEntity;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.block.BohBlock;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.core.Direction;
import net.mcreator.boh.compat.mc.util.RandomSource;
import net.mcreator.boh.compat.mc.world.MenuProvider;
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
import net.mcreator.boh.procedures.PipeBurstBlockAddedProcedure;
import net.mcreator.boh.procedures.PipeBurstOnTickUpdateProcedure;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class PipeBurstBlock extends BohBlock implements EntityBlock {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    public PipeBurstBlock() {
        super(Properties.of().instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.COPPER).strength(99.0F));
        M.registerDefaultState(this, M.setValue(M.any(this.stateDefinition), FACING, Direction.NORTH));
    }

    @Override
    public int getLightBlock(BlockState state, IBlockAccess worldIn, BlockPos pos) {
        return 15;
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
        M.scheduleTick(world, pos, this, 20);
        PipeBurstBlockAddedProcedure.execute(world, M.getX(pos), M.getY(pos), M.getZ(pos));
    }

    @Override
    public void tick(BlockState blockstate, WorldServer world, BlockPos pos, RandomSource random) {
        super.tick(blockstate, world, pos, random);
        int x = M.getX(pos);
        int y = M.getY(pos);
        int z = M.getZ(pos);
        PipeBurstOnTickUpdateProcedure.execute(world, x, y, z, blockstate);
        M.scheduleTick(world, pos, this, 20);
    }

    public MenuProvider getMenuProvider(BlockState state, World worldIn, BlockPos pos) {
        return M.getBlockEntity(worldIn, pos) instanceof MenuProvider menuProvider ? menuProvider : null;
    }

    @Override
    public TileEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new PipeBurstBlockEntity(pos, state);
    }

    @Override
    public boolean triggerEvent(BlockState state, World world, BlockPos pos, int eventID, int eventParam) {
        super.triggerEvent(state, world, pos, eventID, eventParam);
        TileEntity blockEntity = M.getBlockEntity(world, pos);
        return blockEntity == null ? false : M.triggerEvent(blockEntity, eventID, eventParam);
    }
}
