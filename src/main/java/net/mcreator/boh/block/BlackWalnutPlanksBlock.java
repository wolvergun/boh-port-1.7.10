package net.mcreator.boh.block;

import net.mcreator.boh.compat.block.BohBlock;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.core.Direction;
import net.mcreator.boh.compat.mc.world.level.block.SoundType;
import net.mcreator.boh.compat.mc.world.level.block.state.BlockState;
import net.mcreator.boh.compat.mc.world.level.block.state.Properties;
import net.mcreator.boh.compat.mc.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.IBlockAccess;

public class BlackWalnutPlanksBlock extends BohBlock {
    public BlackWalnutPlanksBlock() {
        super(Properties.of().ignitedByLava().instrument(NoteBlockInstrument.BASS).sound(SoundType.WOOD).strength(3.0F, 4.1494856F));
    }

    @Override
    public int getLightBlock(BlockState state, IBlockAccess worldIn, BlockPos pos) {
        return 15;
    }

    @Override
    public int getFlammability(BlockState state, IBlockAccess world, BlockPos pos, Direction face) {
        return 8;
    }
}
