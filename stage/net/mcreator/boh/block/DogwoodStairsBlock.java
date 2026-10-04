package net.mcreator.boh.block;

import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.core.Direction;
import net.minecraft.world.IBlockAccess;
import net.mcreator.boh.compat.mc.world.level.block.Blocks;
import net.mcreator.boh.compat.mc.world.level.block.SoundType;
import net.mcreator.boh.compat.mc.world.level.block.StairBlock;
import net.mcreator.boh.compat.mc.world.level.block.state.BlockState;
import net.mcreator.boh.compat.mc.world.level.block.state.Properties;
import net.mcreator.boh.compat.mc.world.level.block.state.properties.NoteBlockInstrument;
import net.mcreator.boh.compat.M;

public class DogwoodStairsBlock extends StairBlock {

    public DogwoodStairsBlock() {
        super(() -> M.defaultBlockState(Blocks.AIR), Properties.of().ignitedByLava().instrument(NoteBlockInstrument.BASS).sound(net.mcreator.boh.compat.mc.world.level.block.SoundType.WOOD).strength(3.0F, 2.0F).dynamicShape());
    }

    public float getExplosionResistance() {
        return 2.0F;
    }

    public boolean isRandomlyTicking(BlockState state) {
        return false;
    }

    public int getLightBlock(BlockState state, IBlockAccess worldIn, BlockPos pos) {
        return 0;
    }

    public int getFlammability(BlockState state, IBlockAccess world, BlockPos pos, Direction face) {
        return 5;
    }
}
