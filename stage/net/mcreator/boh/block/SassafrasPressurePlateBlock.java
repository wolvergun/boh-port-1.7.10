package net.mcreator.boh.block;

import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.core.Direction;
import net.minecraft.world.IBlockAccess;
import net.mcreator.boh.compat.mc.world.level.block.PressurePlateBlock;
import net.mcreator.boh.compat.mc.world.level.block.SoundType;
import net.mcreator.boh.compat.mc.world.level.block.Sensitivity;
import net.mcreator.boh.compat.mc.world.level.block.state.BlockState;
import net.mcreator.boh.compat.mc.world.level.block.state.Properties;
import net.mcreator.boh.compat.mc.world.level.block.state.properties.BlockSetType;
import net.mcreator.boh.compat.mc.world.level.block.state.properties.NoteBlockInstrument;

public class SassafrasPressurePlateBlock extends PressurePlateBlock {

    public SassafrasPressurePlateBlock() {
        super(net.mcreator.boh.compat.mc.world.level.block.Sensitivity.EVERYTHING, Properties.of().ignitedByLava().instrument(NoteBlockInstrument.BASS).sound(net.mcreator.boh.compat.mc.world.level.block.SoundType.WOOD).strength(2.0F, 3.0F).dynamicShape().forceSolidOn(), BlockSetType.OAK);
    }

    public int getLightBlock(BlockState state, IBlockAccess worldIn, BlockPos pos) {
        return 0;
    }

    public int getFlammability(BlockState state, IBlockAccess world, BlockPos pos, Direction face) {
        return 5;
    }
}
