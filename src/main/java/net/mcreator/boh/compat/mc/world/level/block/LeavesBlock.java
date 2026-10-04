package net.mcreator.boh.compat.mc.world.level.block;

import java.util.Random;
import net.mcreator.boh.compat.M;
import net.mcreator.boh.compat.block.BohBlock;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.tags.BlockTags;
import net.mcreator.boh.compat.mc.tags.TagKey;
import net.mcreator.boh.compat.mc.util.RandomSource;
import net.mcreator.boh.compat.mc.world.item.context.BlockPlaceContext;
import net.mcreator.boh.compat.mc.world.level.block.state.BlockState;
import net.mcreator.boh.compat.mc.world.level.block.state.Builder;
import net.mcreator.boh.compat.mc.world.level.block.state.Properties;
import net.mcreator.boh.compat.mc.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class LeavesBlock extends BohBlock {
    private static final TagKey<Object> LOGS = BlockTags.create(new ResourceLocation("minecraft", "logs"));

    public LeavesBlock(Properties props) {
        super(props);
        this.registerDefaultState(this.defaultBlockState().setValue(BlockStateProperties.DISTANCE, 7).setValue(BlockStateProperties.PERSISTENT, false));
        this.setTickRandomly(true);
        this.setLightOpacity(1);
    }

    @Override
    protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
        builder.add(BlockStateProperties.DISTANCE, BlockStateProperties.PERSISTENT);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        return this.defaultBlockState().setValue(BlockStateProperties.PERSISTENT, true);
    }

    @Override
    public void randomTick(BlockState state, WorldServer world, BlockPos pos, RandomSource random) {
        super.randomTick(state, world, pos, random);
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
        if (world.getBlock(x, y, z) == this) {
            BlockState s = this.stateAt(world, x, y, z);
            if (!s.getValue(BlockStateProperties.PERSISTENT)) {
                int d = this.distanceAt(world, x, y, z);
                if (d >= 7) {
                    this.dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
                    world.setBlockToAir(x, y, z);
                } else if (d != s.getValue(BlockStateProperties.DISTANCE)) {
                    M.setBlock(world, pos, s.setValue(BlockStateProperties.DISTANCE, d), 4);
                }
            }
        }
    }

    private int distanceAt(World w, int x, int y, int z) {
        int best = 7;
        int[][] dirs = new int[][]{{1, 0, 0}, {-1, 0, 0}, {0, 1, 0}, {0, -1, 0}, {0, 0, 1}, {0, 0, -1}};

        for (int[] d : dirs) {
            int nx = x + d[0];
            int ny = y + d[1];
            int nz = z + d[2];
            Block b = w.getBlock(nx, ny, nz);
            if (isLog(w, b, nx, ny, nz)) {
                return 1;
            }

            if (b instanceof LeavesBlock) {
                best = Math.min(best, this.stateAt(w, nx, ny, nz).getValue(BlockStateProperties.DISTANCE) + 1);
            }
        }

        return Math.min(best, 7);
    }

    private static boolean isLog(World w, Block b, int x, int y, int z) {
        if (b.isWood(w, x, y, z)) {
            return true;
        } else {
            String n = Block.blockRegistry.getNameForObject(b);
            return n != null && LOGS.contains(new ResourceLocation(n));
        }
    }

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

    public boolean shouldSideBeRendered(IBlockAccess w, int x, int y, int z, int side) {
        return true;
    }

    public void beginLeavesDecay(World world, int x, int y, int z) {
    }

    public int quantityDroppedBase(Random r) {
        return 0;
    }
}
