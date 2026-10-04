package net.mcreator.boh.compat.mc.world.level.block;

import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.world.level.block.state.BlockState;
import net.minecraft.tileentity.TileEntity;

/** 1.20 EntityBlock: blocks with a block entity. */
public interface EntityBlock {

    TileEntity newBlockEntity(BlockPos pos, BlockState state);
}
