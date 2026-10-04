package net.mcreator.boh.block;

import net.mcreator.boh.compat.mc.core.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.mcreator.boh.compat.mc.world.level.block.SoundType;
import net.mcreator.boh.compat.mc.world.level.block.state.BlockState;
import net.mcreator.boh.compat.mc.world.level.block.state.Properties;
import net.mcreator.boh.compat.mc.world.level.block.state.properties.NoteBlockInstrument;
import net.mcreator.boh.compat.block.BohBlock;

public class SpinelOreOreBlock extends BohBlock {

    public SpinelOreOreBlock() {
        super(Properties.of().instrument(NoteBlockInstrument.BASEDRUM).sound(net.mcreator.boh.compat.mc.world.level.block.SoundType.STONE).strength(3.0F, 5.0F).requiresCorrectToolForDrops());
    }

    public int getLightBlock(BlockState state, IBlockAccess worldIn, BlockPos pos) {
        return 15;
    }
}
