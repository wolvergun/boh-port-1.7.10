package net.mcreator.boh.compat.mc.world.level.block;

import net.mcreator.boh.compat.block.BohBlock;
import net.mcreator.boh.compat.mc.world.level.block.state.Properties;
import net.mcreator.boh.compat.mc.world.level.block.state.properties.BlockStateProperties;
import net.mcreator.boh.compat.mc.world.level.block.state.properties.DirectionProperty;

public class DirectionalBlock extends BohBlock {
    public static final DirectionProperty FACING = BlockStateProperties.FACING;

    public DirectionalBlock(Properties p) {
        super(p);
    }
}
