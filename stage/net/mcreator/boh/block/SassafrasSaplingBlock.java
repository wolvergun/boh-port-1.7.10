package net.mcreator.boh.block;

import net.mcreator.boh.block.grower.SassafrasSaplingTreeGrower;
import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.core.Direction;
import net.minecraft.world.IBlockAccess;
import net.mcreator.boh.compat.mc.world.level.block.SaplingBlock;
import net.mcreator.boh.compat.mc.world.level.block.SoundType;
import net.mcreator.boh.compat.mc.world.level.block.state.BlockState;
import net.mcreator.boh.compat.mc.world.level.block.state.OffsetType;
import net.mcreator.boh.compat.mc.world.level.block.state.Properties;
import net.mcreator.boh.compat.mc.world.level.material.MapColor;
import net.mcreator.boh.compat.mc.world.level.material.PushReaction;

public class SassafrasSaplingBlock extends SaplingBlock {

    public SassafrasSaplingBlock() {
        super(new SassafrasSaplingTreeGrower(), Properties.of().mapColor(MapColor.PLANT).randomTicks().sound(net.mcreator.boh.compat.mc.world.level.block.SoundType.GRASS).instabreak().noCollission().offsetType(OffsetType.NONE).pushReaction(PushReaction.DESTROY));
    }

    public int getFlammability(BlockState state, IBlockAccess world, BlockPos pos, Direction face) {
        return 100;
    }

    public int getFireSpreadSpeed(BlockState state, IBlockAccess world, BlockPos pos, Direction face) {
        return 60;
    }
}
