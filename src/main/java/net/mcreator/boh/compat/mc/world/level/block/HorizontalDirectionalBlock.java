package net.mcreator.boh.compat.mc.world.level.block;

import net.mcreator.boh.compat.block.BohBlock;
import net.mcreator.boh.compat.mc.world.level.block.state.Properties;
import net.mcreator.boh.compat.mc.world.level.block.state.properties.BlockStateProperties;
import net.mcreator.boh.compat.mc.world.level.block.state.properties.DirectionProperty;

public class HorizontalDirectionalBlock extends BohBlock {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    public HorizontalDirectionalBlock(Properties p) {
        super(p);
    }
}
