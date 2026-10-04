package net.mcreator.boh.block;

import net.mcreator.boh.compat.mc.core.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.mcreator.boh.compat.mc.world.level.block.SoundType;
import net.mcreator.boh.compat.mc.world.level.block.state.BlockState;
import net.mcreator.boh.compat.mc.world.level.block.state.Properties;
import net.mcreator.boh.compat.block.BohBlock;

public class SpinelBlockBlock extends BohBlock {

    public SpinelBlockBlock() {
        super(Properties.of().sound(net.mcreator.boh.compat.mc.world.level.block.SoundType.AMETHYST).strength(1.0F, 10.0F));
    }

    public int getLightBlock(BlockState state, IBlockAccess worldIn, BlockPos pos) {
        return 15;
    }
}
