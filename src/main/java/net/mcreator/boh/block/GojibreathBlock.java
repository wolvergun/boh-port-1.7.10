package net.mcreator.boh.block;

import net.mcreator.boh.block.entity.GojibreathBlockEntity;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.util.RandomSource;
import net.mcreator.boh.compat.mc.world.Containers;
import net.mcreator.boh.compat.mc.world.MenuProvider;
import net.mcreator.boh.compat.mc.world.inventory.AbstractContainerMenu;
import net.mcreator.boh.compat.mc.world.level.block.EntityBlock;
import net.mcreator.boh.compat.mc.world.level.block.FallingBlock;
import net.mcreator.boh.compat.mc.world.level.block.SoundType;
import net.mcreator.boh.compat.mc.world.level.block.state.BlockState;
import net.mcreator.boh.compat.mc.world.level.block.state.Properties;
import net.mcreator.boh.compat.mc.world.phys.HitResult;
import net.mcreator.boh.compat.mc.world.phys.shapes.CollisionContext;
import net.mcreator.boh.compat.mc.world.phys.shapes.Shapes;
import net.mcreator.boh.compat.mc.world.phys.shapes.VoxelShape;
import net.mcreator.boh.init.BohModBlocks;
import net.mcreator.boh.procedures.GojibreathUpdateTickProcedure;
import net.mcreator.boh.procedures.XenomorphBloodEntityWalksOnTheBlockProcedure;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class GojibreathBlock extends FallingBlock implements EntityBlock {
    public GojibreathBlock() {
        super(
            Properties.of()
                .liquid()
                .sound(SoundType.SCULK)
                .strength(99.0F, 50.0F)
                .noCollission()
                .speedFactor(0.9F)
                .jumpFactor(0.9F)
                .noOcclusion()
                .isRedstoneConductor((bs, br, bp) -> false)
        );
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
        return box(0.0, 0.0, 0.0, 16.0, 1.0, 16.0);
    }

    public ItemStack getCloneItemStack(BlockState state, HitResult target, IBlockAccess world, BlockPos pos, EntityPlayer player) {
        return M.new_ItemStack(BohModBlocks.GOJIBREATH.get());
    }

    @Override
    public void onPlace(BlockState blockstate, World world, BlockPos pos, BlockState oldState, boolean moving) {
        super.onPlace(blockstate, world, pos, oldState, moving);
        M.scheduleTick(world, pos, this, 100);
    }

    @Override
    public void tick(BlockState blockstate, WorldServer world, BlockPos pos, RandomSource random) {
        super.tick(blockstate, world, pos, random);
        int x = M.getX(pos);
        int y = M.getY(pos);
        int z = M.getZ(pos);
        GojibreathUpdateTickProcedure.execute(world, x, y, z);
        M.scheduleTick(world, pos, this, 100);
    }

    @Override
    public void entityInside(BlockState blockstate, World world, BlockPos pos, Entity entity) {
        super.entityInside(blockstate, world, pos, entity);
        XenomorphBloodEntityWalksOnTheBlockProcedure.execute(entity);
    }

    @Override
    public void stepOn(World world, BlockPos pos, BlockState blockstate, Entity entity) {
        super.stepOn(world, pos, blockstate, entity);
        XenomorphBloodEntityWalksOnTheBlockProcedure.execute(entity);
    }

    public MenuProvider getMenuProvider(BlockState state, World worldIn, BlockPos pos) {
        return M.getBlockEntity(worldIn, pos) instanceof MenuProvider menuProvider ? menuProvider : null;
    }

    @Override
    public TileEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new GojibreathBlockEntity(pos, state);
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
            if (M.getBlockEntity(world, pos) instanceof GojibreathBlockEntity be) {
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
        return M.getBlockEntity(world, pos) instanceof GojibreathBlockEntity be ? AbstractContainerMenu.getRedstoneSignalFromContainer(be) : 0;
    }
}
