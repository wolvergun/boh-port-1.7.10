package net.mcreator.boh.compat.mc.world.level.block.entity;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;

/** 1.20 BlockEntityType.Builder. */
public class Builder<T extends TileEntity> {

    private final BlockEntitySupplier<? extends T> factory;
    private final Block[] blocks;

    private Builder(BlockEntitySupplier<? extends T> factory, Block[] blocks) {
        this.factory = factory;
        this.blocks = blocks;
    }

    public static <T extends TileEntity> Builder<T> of(BlockEntitySupplier<? extends T> factory, Block... blocks) {
        return new Builder<>(factory, blocks);
    }

    public static <T extends TileEntity> Builder<T> of(BlockEntitySupplier<? extends T> factory, Class<?> cls, Block... blocks) {
        return new Builder<>(factory, blocks);
    }

    public BlockEntityType<T> build(Object dataFixerType) {
        return BlockEntityType.create(factory, blocks);
    }
}
