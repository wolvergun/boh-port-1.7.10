package net.mcreator.boh.block;

import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.core.Direction;
import net.mcreator.boh.compat.mc.world.level.block.ButtonBlock;
import net.mcreator.boh.compat.mc.world.level.block.SoundType;
import net.mcreator.boh.compat.mc.world.level.block.state.BlockState;
import net.mcreator.boh.compat.mc.world.level.block.state.Properties;
import net.mcreator.boh.compat.mc.world.level.block.state.properties.BlockSetType;
import net.mcreator.boh.compat.mc.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.IBlockAccess;

public class SinistreeButtonBlock extends ButtonBlock {
    public SinistreeButtonBlock() {
        super(
            Properties.of().ignitedByLava().instrument(NoteBlockInstrument.BASS).sound(SoundType.WOOD).strength(2.0F, 3.0F).dynamicShape(),
            BlockSetType.OAK,
            30,
            true
        );
    }

    @Override
    public int getLightBlock(BlockState state, IBlockAccess worldIn, BlockPos pos) {
        return 0;
    }

    @Override
    public int getFlammability(BlockState state, IBlockAccess world, BlockPos pos, Direction face) {
        return 5;
    }
}
