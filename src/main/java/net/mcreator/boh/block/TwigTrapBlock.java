package net.mcreator.boh.block;

import net.mcreator.boh.block.entity.TwigTrapBlockEntity;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.block.BohBlock;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.core.Direction;
import net.mcreator.boh.compat.mc.util.RandomSource;
import net.mcreator.boh.compat.mc.world.Containers;
import net.mcreator.boh.compat.mc.world.MenuProvider;
import net.mcreator.boh.compat.mc.world.inventory.AbstractContainerMenu;
import net.mcreator.boh.compat.mc.world.item.context.BlockPlaceContext;
import net.mcreator.boh.compat.mc.world.level.block.Blocks;
import net.mcreator.boh.compat.mc.world.level.block.EntityBlock;
import net.mcreator.boh.compat.mc.world.level.block.SimpleWaterloggedBlock;
import net.mcreator.boh.compat.mc.world.level.block.SoundType;
import net.mcreator.boh.compat.mc.world.level.block.state.BlockState;
import net.mcreator.boh.compat.mc.world.level.block.state.Builder;
import net.mcreator.boh.compat.mc.world.level.block.state.Properties;
import net.mcreator.boh.compat.mc.world.level.block.state.properties.BlockStateProperties;
import net.mcreator.boh.compat.mc.world.level.block.state.properties.BooleanProperty;
import net.mcreator.boh.compat.mc.world.level.block.state.properties.NoteBlockInstrument;
import net.mcreator.boh.compat.mc.world.level.material.FluidState;
import net.mcreator.boh.compat.mc.world.level.material.Fluids;
import net.mcreator.boh.compat.mc.world.phys.HitResult;
import net.mcreator.boh.compat.mc.world.phys.shapes.CollisionContext;
import net.mcreator.boh.compat.mc.world.phys.shapes.Shapes;
import net.mcreator.boh.compat.mc.world.phys.shapes.VoxelShape;
import net.mcreator.boh.procedures.TwigTrapEntityCollidesInTheBlockProcedure;
import net.mcreator.boh.procedures.TwigTrapOnTickUpdateProcedure;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class TwigTrapBlock extends BohBlock implements SimpleWaterloggedBlock, EntityBlock {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public TwigTrapBlock() {
        super(
            Properties.of()
                .instrument(NoteBlockInstrument.BASEDRUM)
                .sound(SoundType.VINE)
                .strength(0.1F, 1.0F)
                .noCollission()
                .friction(0.1F)
                .speedFactor(0.1F)
                .jumpFactor(0.1F)
                .noOcclusion()
                .randomTicks()
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

    public ItemStack getCloneItemStack(BlockState state, HitResult target, IBlockAccess world, BlockPos pos, EntityPlayer player) {
        return M.new_ItemStack(Blocks.AIR);
    }

    @Override
    public void tick(BlockState blockstate, WorldServer world, BlockPos pos, RandomSource random) {
        super.tick(blockstate, world, pos, random);
        int x = M.getX(pos);
        int y = M.getY(pos);
        int z = M.getZ(pos);
        TwigTrapOnTickUpdateProcedure.execute(world, x, y, z);
    }

    @Override
    public void entityInside(BlockState blockstate, World world, BlockPos pos, Entity entity) {
        super.entityInside(blockstate, world, pos, entity);
        TwigTrapEntityCollidesInTheBlockProcedure.execute(entity);
    }

    public MenuProvider getMenuProvider(BlockState state, World worldIn, BlockPos pos) {
        return M.getBlockEntity(worldIn, pos) instanceof MenuProvider menuProvider ? menuProvider : null;
    }

    @Override
    public TileEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new TwigTrapBlockEntity(pos, state);
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
            if (M.getBlockEntity(world, pos) instanceof TwigTrapBlockEntity be) {
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
        return M.getBlockEntity(world, pos) instanceof TwigTrapBlockEntity be ? AbstractContainerMenu.getRedstoneSignalFromContainer(be) : 0;
    }
}
