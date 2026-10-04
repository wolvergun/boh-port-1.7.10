package net.mcreator.boh.compat.mc.world.level.block.state;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

import net.mcreator.boh.compat.mc.tags.TagKey;
import net.mcreator.boh.compat.mc.world.level.block.state.properties.Property;
import net.mcreator.boh.compat.registry.LegacyIds;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;

/** 1.20 BlockState: block + metadata (+ extended properties for mod blocks with large state spaces). */
public final class BlockState implements StateHolder {

    private final Block block;
    private final int meta;
    private final int ext;

    public BlockState(Block block, int meta, int ext) {
        this.block = block == null ? Blocks.air : block;
        this.meta = meta & 15;
        this.ext = ext;
    }

    public static BlockState of(Block block, int meta) {
        return new BlockState(block, meta, 0);
    }

    public static BlockState of(Block block) {
        return new BlockState(block, 0, 0);
    }

    public static BlockState of(Block block, int meta, int ext) {
        return new BlockState(block, meta, ext);
    }

    /** The definition of the block: mod blocks carry their own, everything else has no properties. */
    public StateDefinition definition() {
        return block instanceof HasStateDefinition ? ((HasStateDefinition) block).getStateDefinition()
            : StateDefinition.empty(block);
    }

    public Block getBlock() {
        return block;
    }

    public int meta() {
        return meta;
    }

    public int ext() {
        return ext;
    }

    public boolean hasProperty(Property<?> p) {
        return definition().has(p);
    }

    @SuppressWarnings("unchecked")
    public <T extends Comparable<T>> T getValue(Property<T> p) {
        StateDefinition def = definition();
        int i = def.indexOf(p);
        if (i < 0) {
            Object legacy = LegacyIds.legacyPropertyValue(block, meta, p);
            if (legacy != null) return (T) legacy;
            return p.byIndex(0);
        }
        return p.byIndex(def.decode(meta, ext)[i]);
    }

    public <T extends Comparable<T>> java.util.Optional<T> getOptionalValue(Property<T> p) {
        return hasProperty(p) ? java.util.Optional.of(getValue(p)) : java.util.Optional.empty();
    }

    public <T extends Comparable<T>, V extends T> BlockState setValue(Property<T> p, V value) {
        StateDefinition def = definition();
        int i = def.indexOf(p);
        if (i < 0) {
            Integer legacyMeta = LegacyIds.legacyPropertyMeta(block, meta, p, value);
            return legacyMeta == null ? this : new BlockState(block, legacyMeta, ext);
        }
        int[] idx = def.decode(meta, ext);
        idx[i] = p.indexOf(value);
        int[] enc = def.encode(idx);
        return new BlockState(block, enc[0], enc[1]);
    }

    public <T extends Comparable<T>> BlockState cycle(Property<T> p) {
        java.util.List<T> vals = new java.util.ArrayList<>(p.getPossibleValues());
        T cur = getValue(p);
        return setValue(p, vals.get((vals.indexOf(cur) + 1) % vals.size()));
    }

    public com.google.common.collect.ImmutableMap<Property<?>, Comparable<?>> getValues() {
        Map<Property<?>, Comparable<?>> out = new LinkedHashMap<>();
        for (Property<?> p : definition().getProperties()) out.put(p, getValue((Property) p));
        return com.google.common.collect.ImmutableMap.copyOf(out);
    }

    public Collection<Property<?>> getProperties() {
        return definition().getProperties();
    }

    public boolean is(Block b) {
        return block == b;
    }

    public boolean is(TagKey<?> tag) {
        return tag.contains(LegacyIds.blockKey(block));
    }

    public boolean isAir() {
        return block.getMaterial() == Material.air;
    }

    public boolean liquid() {
        return block.getMaterial().isLiquid();
    }

    public boolean isSolid() {
        return block.getMaterial().isSolid();
    }

    public boolean blocksMotion() {
        return block.getMaterial().blocksMovement();
    }

    public boolean canOcclude() {
        return block.isOpaqueCube();
    }

    public boolean isCollisionShapeFullBlock(Object world, Object pos) {
        return block.renderAsNormalBlock();
    }

    public boolean canBeReplaced() {
        return block.getMaterial().isReplaceable();
    }

    public int getLightEmission() {
        return block.getLightValue();
    }

    public boolean ignitedByLava() {
        return block.getMaterial().getCanBurn();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof BlockState)) return false;
        BlockState s = (BlockState) o;
        return s.block == block && s.meta == meta && s.ext == ext;
    }

    @Override
    public int hashCode() {
        return Objects.hash(System.identityHashCode(block), meta, ext);
    }

    @Override
    public String toString() {
        return "Block{" + Block.blockRegistry.getNameForObject(block) + "}" + (getValues().isEmpty() ? "[" + meta + "]" : getValues());
    }

    /** Implemented by blocks that declare 1.20 properties (the compat BohBlock). */
    public interface HasStateDefinition {

        StateDefinition getStateDefinition();
    }

    static Map<Property<?>, Comparable<?>> none() {
        return Collections.emptyMap();
    }
}
