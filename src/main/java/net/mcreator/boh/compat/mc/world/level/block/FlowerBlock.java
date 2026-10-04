package net.mcreator.boh.compat.mc.world.level.block;

import java.util.function.Supplier;
import net.mcreator.boh.compat.block.BohBlock;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.world.level.block.state.BlockState;
import net.mcreator.boh.compat.mc.world.level.block.state.Properties;
import net.mcreator.boh.compat.mc.world.phys.shapes.CollisionContext;
import net.mcreator.boh.compat.mc.world.phys.shapes.VoxelShape;
import net.minecraft.block.Block;
import net.minecraft.potion.Potion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class FlowerBlock extends BohBlock {
    private final Supplier<? extends Potion> effect;
    private final int duration;

    public FlowerBlock(Supplier<? extends Potion> effect, int duration, Properties props) {
        super(props);
        this.effect = effect;
        this.duration = duration;
    }

    public FlowerBlock(Potion effect, int duration, Properties props) {
        this((Supplier<? extends Potion>)(() -> effect), duration, props);
    }

    public Potion getSuspiciousEffect() {
        return this.effect.get();
    }

    public int getEffectDuration() {
        return this.duration;
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockAccess world, BlockPos pos, CollisionContext ctx) {
        return box(5.0, 0.0, 5.0, 11.0, 10.0, 11.0);
    }

    @Override
    public boolean canBlockStay(World w, int x, int y, int z) {
        Block below = w.getBlock(x, y - 1, z);
        return below == net.minecraft.init.Blocks.grass
            || below == net.minecraft.init.Blocks.dirt
            || below == net.minecraft.init.Blocks.farmland;
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
}
