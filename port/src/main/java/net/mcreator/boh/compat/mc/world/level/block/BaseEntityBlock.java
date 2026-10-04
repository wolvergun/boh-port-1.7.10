package net.mcreator.boh.compat.mc.world.level.block;

import net.mcreator.boh.compat.block.BohBlock;
import net.mcreator.boh.compat.mc.world.level.block.state.BlockState;
import net.mcreator.boh.compat.mc.world.level.block.state.Properties;

/** 1.20 BaseEntityBlock: a block with a block entity; INVISIBLE render shape unless overridden. */
public abstract class BaseEntityBlock extends BohBlock implements EntityBlock {

    protected BaseEntityBlock(Properties props) {
        super(props);
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.INVISIBLE;
    }

    @Override
    public int getRenderType() {
        return getRenderShape(defaultBlockState()) == RenderShape.MODEL ? super.getRenderType() : -1;
    }

    @Override
    public boolean isOpaqueCube() {
        return defaultBlockState() != null && getRenderShape(defaultBlockState()) == RenderShape.MODEL && super.isOpaqueCube();
    }

    @Override
    public boolean renderAsNormalBlock() {
        return defaultBlockState() != null && getRenderShape(defaultBlockState()) == RenderShape.MODEL && super.renderAsNormalBlock();
    }
}
