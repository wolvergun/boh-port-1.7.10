package net.mcreator.boh.block;

import net.mcreator.boh.compat.block.BohBlock;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.world.level.block.SoundType;
import net.mcreator.boh.compat.mc.world.level.block.state.BlockState;
import net.mcreator.boh.compat.mc.world.level.block.state.Properties;
import net.minecraft.world.IBlockAccess;

public class SpinelBlockBlock extends BohBlock {
    public SpinelBlockBlock() {
        super(Properties.of().sound(SoundType.AMETHYST).strength(1.0F, 10.0F));
    }

    @Override
    public int getLightBlock(BlockState state, IBlockAccess worldIn, BlockPos pos) {
        return 15;
    }
}
