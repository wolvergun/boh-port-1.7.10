package net.mcreator.boh.compat.mc.world.level.block.entity;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import net.mcreator.boh.compat.mc.core.BlockPos;
import net.mcreator.boh.compat.mc.world.level.block.state.BlockState;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

/** 1.20 BlockEntityType: a tile entity factory plus its valid blocks. */
public class BlockEntityType<T extends TileEntity> {

    private final BlockEntitySupplier<? extends T> factory;
    private final Set<Block> validBlocks;
    private Class<? extends TileEntity> tileClass;
    private ResourceLocation id;

    BlockEntityType(BlockEntitySupplier<? extends T> factory, Set<Block> validBlocks) {
        this.factory = factory;
        this.validBlocks = validBlocks;
    }

    public T create(BlockPos pos, BlockState state) {
        return factory.create(pos, state);
    }

    public boolean isValid(BlockState state) {
        return validBlocks.contains(state.getBlock());
    }

    public Set<Block> getValidBlocks() {
        return validBlocks;
    }

    /** The tile entity class, discovered by instantiating once (1.7.10 registers tiles by class). */
    public Class<? extends TileEntity> tileClass() {
        if (tileClass == null) tileClass = factory.create(BlockPos.ZERO, null).getClass();
        return tileClass;
    }

    public ResourceLocation getId() {
        return id;
    }

    public void setRegistryName(ResourceLocation id) {
        this.id = id;
    }

    static <T extends TileEntity> BlockEntityType<T> create(BlockEntitySupplier<? extends T> f, Block... blocks) {
        return new BlockEntityType<>(f, new HashSet<>(Arrays.asList(blocks)));
    }
}
