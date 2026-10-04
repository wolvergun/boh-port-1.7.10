package net.mcreator.boh.compat.mc.world.level.block;

import java.util.Random;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.block.BohBlock;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.core.Direction;
import net.mcreator.boh.compat.mc.util.RandomSource;
import net.mcreator.boh.compat.mc.world.level.block.grower.AbstractTreeGrower;
import net.mcreator.boh.compat.mc.world.level.block.state.BlockState;
import net.mcreator.boh.compat.mc.world.level.block.state.Builder;
import net.mcreator.boh.compat.mc.world.level.block.state.Properties;
import net.mcreator.boh.compat.mc.world.level.block.state.properties.BlockStateProperties;
import net.mcreator.boh.compat.mc.world.phys.shapes.CollisionContext;
import net.mcreator.boh.compat.mc.world.phys.shapes.VoxelShape;
import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;

public class SaplingBlock extends BohBlock implements IGrowable {
    protected final AbstractTreeGrower treeGrower;

    public SaplingBlock(AbstractTreeGrower grower, Properties props) {
        super(props);
        this.treeGrower = grower;
        this.registerDefaultState(this.defaultBlockState().setValue(BlockStateProperties.STAGE, 0));
        this.setTickRandomly(true);
    }

    @Override
    protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
        builder.add(BlockStateProperties.STAGE);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockAccess world, BlockPos pos, CollisionContext ctx) {
        return box(2.0, 0.0, 2.0, 14.0, 12.0, 14.0);
    }

    @Override
    public void randomTick(BlockState state, WorldServer world, BlockPos pos, RandomSource random) {
        super.randomTick(state, world, pos, random);
        if (world.getBlockLightValue(pos.getX(), pos.getY() + 1, pos.getZ()) >= 9 && random.nextInt(7) == 0) {
            this.advanceTree(world, pos, state, random);
        }
    }

    public void advanceTree(World world, BlockPos pos, BlockState state, RandomSource random) {
        if (state.getValue(BlockStateProperties.STAGE) == 0) {
            M.setBlock(world, pos, state.cycle(BlockStateProperties.STAGE), 4);
        } else if (this.treeGrower != null) {
            this.treeGrower.growTree(world, null, pos, state, random);
        }
    }

    @Override
    public boolean canBlockStay(World w, int x, int y, int z) {
        Block below = w.getBlock(x, y - 1, z);
        return below == net.minecraft.init.Blocks.grass
            || below == net.minecraft.init.Blocks.dirt
            || below == net.minecraft.init.Blocks.farmland
            || below.canSustainPlant(w, x, y - 1, z, ForgeDirection.UP, (IPlantable)net.minecraft.init.Blocks.sapling);
    }

    @Override
    public boolean canPlaceBlockAt(World w, int x, int y, int z) {
        return super.canPlaceBlockAt(w, x, y, z) && this.canBlockStay(w, x, y, z);
    }

    @Override
    public void onNeighborBlockChange(World w, int x, int y, int z, Block neighbor) {
        super.onNeighborBlockChange(w, x, y, z, neighbor);
        if (!this.canBlockStay(w, x, y, z)) {
            this.dropBlockAsItem(w, x, y, z, w.getBlockMetadata(x, y, z), 0);
            w.setBlockToAir(x, y, z);
        }
    }

    public boolean func_149851_a(World w, int x, int y, int z, boolean client) {
        return true;
    }

    public boolean func_149852_a(World w, Random r, int x, int y, int z) {
        return r.nextFloat() < 0.45F;
    }

    public void func_149853_b(World w, Random r, int x, int y, int z) {
        BlockPos pos = new BlockPos(x, y, z);
        this.advanceTree(w, pos, this.stateAt(w, x, y, z), RandomSource.wrap(r));
    }

    @Override
    public int getFireSpreadSpeed(BlockState state, IBlockAccess world, BlockPos pos, Direction face) {
        return 0;
    }
}
