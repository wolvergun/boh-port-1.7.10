package net.mcreator.boh.compat.mc.world.level.block.entity;

import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.world.level.block.state.BlockState;
import net.minecraft.tileentity.TileEntity;

/** 1.20 BlockEntityType.BlockEntitySupplier. */
@FunctionalInterface
public interface BlockEntitySupplier<T extends TileEntity> {

    T create(BlockPos pos, BlockState state);
}
