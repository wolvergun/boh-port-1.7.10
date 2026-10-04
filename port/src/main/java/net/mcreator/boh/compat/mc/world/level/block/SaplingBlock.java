package net.mcreator.boh.compat.mc.world.level.block;

import java.util.Random;

import net.mcreator.boh.compat.block.BohBlock;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.util.RandomSource;
import net.mcreator.boh.compat.mc.world.level.block.grower.AbstractTreeGrower;
import net.mcreator.boh.compat.mc.world.level.block.state.BlockState;
import net.mcreator.boh.compat.mc.world.level.block.state.Builder;
import net.mcreator.boh.compat.mc.world.level.block.state.Properties;
import net.mcreator.boh.compat.mc.world.level.block.state.properties.BlockStateProperties;
import net.mcreator.boh.compat.mc.world.level.block.state.properties.Property;
import net.mcreator.boh.compat.mc.world.phys.shapes.CollisionContext;
import net.mcreator.boh.compat.mc.world.phys.shapes.VoxelShape;
import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.init.Blocks;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

/** 1.20 SaplingBlock: stage 0 -> 1 -> tree, bonemeal through IGrowable. */
public class SaplingBlock extends BohBlock implements IGrowable {

    protected final AbstractTreeGrower treeGrower;

    public SaplingBlock(AbstractTreeGrower grower, Properties props) {
        super(props);
        treeGrower = grower;
        registerDefaultState(defaultBlockState().setValue(BlockStateProperties.STAGE, 0));
        setTickRandomly(true);
    }

    @Override
    protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
        builder.add(new Property[] { BlockStateProperties.STAGE });
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockAccess world, BlockPos pos, CollisionContext ctx) {
        return box(2, 0, 2, 14, 12, 14);
    }

    @Override
    public void randomTick(BlockState state, WorldServer world, BlockPos pos, RandomSource random) {
        super.randomTick(state, world, pos, random);
        if (world.getBlockLightValue(pos.getX(), pos.getY() + 1, pos.getZ()) >= 9 && random.nextInt(7) == 0) advanceTree(world, pos, state, random);
    }

    public void advanceTree(World world, BlockPos pos, BlockState state, RandomSource random) {
        if (state.getValue(BlockStateProperties.STAGE) == 0) {
            net.mcreator.boh.compat.M.setBlock(world, pos, state.cycle(BlockStateProperties.STAGE), 4);
        } else if (treeGrower != null) {
            treeGrower.growTree(world, null, pos, state, random);
        }
    }

    @Override
    public boolean canBlockStay(World w, int x, int y, int z) {
        Block below = w.getBlock(x, y - 1, z);
        return below == Blocks.grass || below == Blocks.dirt || below == Blocks.farmland
            || below.canSustainPlant(w, x, y - 1, z, net.minecraftforge.common.util.ForgeDirection.UP, (net.minecraftforge.common.IPlantable) Blocks.sapling);
    }

    @Override
    public boolean canPlaceBlockAt(World w, int x, int y, int z) {
        return super.canPlaceBlockAt(w, x, y, z) && canBlockStay(w, x, y, z);
    }

    @Override
    public void onNeighborBlockChange(World w, int x, int y, int z, Block neighbor) {
        super.onNeighborBlockChange(w, x, y, z, neighbor);
        if (!canBlockStay(w, x, y, z)) {
            dropBlockAsItem(w, x, y, z, w.getBlockMetadata(x, y, z), 0);
            w.setBlockToAir(x, y, z);
        }
    }

    // IGrowable (bone meal)
    @Override
    public boolean func_149851_a(World w, int x, int y, int z, boolean client) {
        return true;
    }

    @Override
    public boolean func_149852_a(World w, Random r, int x, int y, int z) {
        return r.nextFloat() < 0.45F;
    }

    @Override
    public void func_149853_b(World w, Random r, int x, int y, int z) {
        BlockPos pos = new BlockPos(x, y, z);
        advanceTree(w, pos, stateAt(w, x, y, z), RandomSource.wrap(r));
    }

    public int getFireSpreadSpeed(BlockState state, IBlockAccess world, BlockPos pos, net.mcreator.boh.compat.mc.core.Direction face) {
        return 0;
    }
}
