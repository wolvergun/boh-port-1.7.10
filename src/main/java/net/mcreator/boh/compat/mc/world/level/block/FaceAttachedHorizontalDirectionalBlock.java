package net.mcreator.boh.compat.mc.world.level.block;

import net.mcreator.boh.compat.mc.world.level.block.state.Properties;
import net.mcreator.boh.compat.mc.world.level.block.state.properties.AttachFace;
import net.mcreator.boh.compat.mc.world.level.block.state.properties.BlockStateProperties;
import net.mcreator.boh.compat.mc.world.level.block.state.properties.EnumProperty;

public class FaceAttachedHorizontalDirectionalBlock extends HorizontalDirectionalBlock {
    public static final EnumProperty<AttachFace> FACE = BlockStateProperties.ATTACH_FACE;

    public FaceAttachedHorizontalDirectionalBlock(Properties p) {
        super(p);
    }
}
