package net.mcreator.boh.compat.mc.world.level.block;

import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.world.level.block.state.BlockState;
import net.minecraft.tileentity.TileEntity;

public interface EntityBlock {
    TileEntity newBlockEntity(BlockPos var1, BlockState var2);
}
