package net.mcreator.boh.compat.mc.world.level.block;

import net.mcreator.boh.compat.block.BohBlock;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.util.RandomSource;
import net.mcreator.boh.compat.mc.world.level.block.state.BlockState;
import net.mcreator.boh.compat.mc.world.level.block.state.Properties;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

/** 1.20 FallingBlock: falls when unsupported (scheduled 2 ticks after placement or a neighbour change). */
public class FallingBlock extends BohBlock {

    public FallingBlock(Properties props) {
        super(props);
    }

    @Override
    public void onPlace(BlockState state, World world, BlockPos pos, BlockState old, boolean moving) {
        super.onPlace(state, world, pos, old, moving);
        schedule(world, pos);
    }

    @Override
    public void neighborChanged(BlockState state, World world, BlockPos pos, Block neighbor, BlockPos from, boolean moving) {
        super.neighborChanged(state, world, pos, neighbor, from, moving);
        schedule(world, pos);
    }

    private void schedule(World world, BlockPos pos) {
        markScheduled(world, pos.getX(), pos.getY(), pos.getZ());
        world.scheduleBlockUpdate(pos.getX(), pos.getY(), pos.getZ(), this, getDelayAfterPlace());
    }

    protected int getDelayAfterPlace() {
        return 2;
    }

    @Override
    public void tick(BlockState state, WorldServer world, BlockPos pos, RandomSource random) {
        super.tick(state, world, pos, random);
        int x = pos.getX(), y = pos.getY(), z = pos.getZ();
        if (world.getBlock(x, y, z) != this || y < 1) return;
        if (!BlockFalling.func_149831_e(world, x, y - 1, z)) return;
        EntityFallingBlock e = new EntityFallingBlock(world, x + 0.5, y + 0.5, z + 0.5, this, world.getBlockMetadata(x, y, z));
        world.spawnEntityInWorld(e);
    }
}
