package net.mcreator.boh.compat.mc.world.level.block;

import java.util.Random;

import net.mcreator.boh.compat.block.BohBlock;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.tags.BlockTags;
import net.mcreator.boh.compat.mc.util.RandomSource;
import net.mcreator.boh.compat.mc.world.item.context.BlockPlaceContext;
import net.mcreator.boh.compat.mc.world.level.block.state.BlockState;
import net.mcreator.boh.compat.mc.world.level.block.state.Builder;
import net.mcreator.boh.compat.mc.world.level.block.state.Properties;
import net.mcreator.boh.compat.mc.world.level.block.state.properties.BlockStateProperties;
import net.mcreator.boh.compat.mc.world.level.block.state.properties.Property;
import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

/** 1.20 LeavesBlock: distance-to-log decay with persistent leaves when placed by players. */
public class LeavesBlock extends BohBlock {

    private static final net.mcreator.boh.compat.mc.tags.TagKey<Object> LOGS = BlockTags.create(new ResourceLocation("minecraft", "logs"));

    public LeavesBlock(Properties props) {
        super(props);
        registerDefaultState(defaultBlockState().setValue(BlockStateProperties.DISTANCE, 7).setValue(BlockStateProperties.PERSISTENT, false));
        setTickRandomly(true);
        setLightOpacity(1);
    }

    @Override
    protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
        builder.add(new Property[] { BlockStateProperties.DISTANCE, BlockStateProperties.PERSISTENT });
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        return defaultBlockState().setValue(BlockStateProperties.PERSISTENT, true);
    }

    @Override
    public void randomTick(BlockState state, WorldServer world, BlockPos pos, RandomSource random) {
        super.randomTick(state, world, pos, random);
        int x = pos.getX(), y = pos.getY(), z = pos.getZ();
        if (world.getBlock(x, y, z) != this) return;
        BlockState s = stateAt(world, x, y, z);
        if (s.getValue(BlockStateProperties.PERSISTENT)) return;
        int d = distanceAt(world, x, y, z);
        if (d >= 7) {
            dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
            world.setBlockToAir(x, y, z);
        } else if (d != s.getValue(BlockStateProperties.DISTANCE)) {
            net.mcreator.boh.compat.M.setBlock(world, pos, s.setValue(BlockStateProperties.DISTANCE, d), 4);
        }
    }

    private int distanceAt(World w, int x, int y, int z) {
        int best = 7;
        int[][] dirs = { { 1, 0, 0 }, { -1, 0, 0 }, { 0, 1, 0 }, { 0, -1, 0 }, { 0, 0, 1 }, { 0, 0, -1 } };
        for (int[] d : dirs) {
            int nx = x + d[0], ny = y + d[1], nz = z + d[2];
            Block b = w.getBlock(nx, ny, nz);
            if (isLog(w, b, nx, ny, nz)) return 1;
            if (b instanceof LeavesBlock) best = Math.min(best, stateAt(w, nx, ny, nz).getValue(BlockStateProperties.DISTANCE) + 1);
        }
        return Math.min(best, 7);
    }

    private static boolean isLog(World w, Block b, int x, int y, int z) {
        if (b.isWood(w, x, y, z)) return true;
        String n = Block.blockRegistry.getNameForObject(b);
        return n != null && LOGS.contains(new ResourceLocation(n));
    }

    @Override
    public boolean isLeaves(IBlockAccess world, int x, int y, int z) {
        return true;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public boolean shouldSideBeRendered(IBlockAccess w, int x, int y, int z, int side) {
        return true;
    }

    @Override
    public void beginLeavesDecay(World world, int x, int y, int z) {}

    public int quantityDroppedBase(Random r) {
        return 0;
    }
}
