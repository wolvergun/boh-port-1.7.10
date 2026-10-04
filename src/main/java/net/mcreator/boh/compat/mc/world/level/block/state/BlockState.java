package net.mcreator.boh.compat.mc.world.level.block.state;

import com.google.common.collect.ImmutableMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import net.mcreator.boh.compat.mc.tags.TagKey;
import net.mcreator.boh.compat.mc.world.level.block.state.properties.Property;
import net.mcreator.boh.compat.registry.LegacyIds;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;

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

    public StateDefinition definition() {
        return this.block instanceof BlockState.HasStateDefinition
            ? ((BlockState.HasStateDefinition)this.block).getStateDefinition()
            : StateDefinition.empty(this.block);
    }

    public Block getBlock() {
        return this.block;
    }

    public int meta() {
        return this.meta;
    }

    public int ext() {
        return this.ext;
    }

    public boolean hasProperty(Property<?> p) {
        return this.definition().has(p);
    }

    public <T extends Comparable<T>> T getValue(Property<T> p) {
        StateDefinition def = this.definition();
        int i = def.indexOf(p);
        if (i < 0) {
            Object legacy = LegacyIds.legacyPropertyValue(this.block, this.meta, p);
            return (T)(legacy != null ? legacy : p.byIndex(0));
        } else {
            return p.byIndex(def.decode(this.meta, this.ext)[i]);
        }
    }

    public <T extends Comparable<T>> Optional<T> getOptionalValue(Property<T> p) {
        return this.hasProperty(p) ? Optional.of(this.getValue(p)) : Optional.empty();
    }

    public <T extends Comparable<T>, V extends T> BlockState setValue(Property<T> p, V value) {
        StateDefinition def = this.definition();
        int i = def.indexOf(p);
        if (i < 0) {
            Integer legacyMeta = LegacyIds.legacyPropertyMeta(this.block, this.meta, p, value);
            return legacyMeta == null ? this : new BlockState(this.block, legacyMeta, this.ext);
        } else {
            int[] idx = def.decode(this.meta, this.ext);
            idx[i] = p.indexOf((T)value);
            int[] enc = def.encode(idx);
            return new BlockState(this.block, enc[0], enc[1]);
        }
    }

    public <T extends Comparable<T>> BlockState cycle(Property<T> p) {
        List<T> vals = new ArrayList<>(p.getPossibleValues());
        T cur = this.getValue(p);
        return this.setValue(p, vals.get((vals.indexOf(cur) + 1) % vals.size()));
    }

    public ImmutableMap<Property<?>, Comparable<?>> getValues() {
        Map<Property<?>, Comparable<?>> out = new LinkedHashMap<>();

        for (Property<?> p : this.definition().getProperties()) {
            out.put(p, this.getValue((Property<Comparable<?>>)p));
        }

        return ImmutableMap.copyOf(out);
    }

    public Collection<Property<?>> getProperties() {
        return this.definition().getProperties();
    }

    public boolean is(Block b) {
        return this.block == b;
    }

    public boolean is(TagKey<?> tag) {
        return tag.contains(LegacyIds.blockKey(this.block));
    }

    public boolean isAir() {
        return this.block.getMaterial() == Material.air;
    }

    public boolean liquid() {
        return this.block.getMaterial().isLiquid();
    }

    public boolean isSolid() {
        return this.block.getMaterial().isSolid();
    }

    public boolean blocksMotion() {
        return this.block.getMaterial().blocksMovement();
    }

    public boolean canOcclude() {
        return this.block.isOpaqueCube();
    }

    public boolean isCollisionShapeFullBlock(Object world, Object pos) {
        return this.block.renderAsNormalBlock();
    }

    public boolean canBeReplaced() {
        return this.block.getMaterial().isReplaceable();
    }

    public int getLightEmission() {
        return this.block.getLightValue();
    }

    public boolean ignitedByLava() {
        return this.block.getMaterial().getCanBurn();
    }

    @Override
    public boolean equals(Object o) {
        return !(o instanceof BlockState s) ? false : s.block == this.block && s.meta == this.meta && s.ext == this.ext;
    }

    @Override
    public int hashCode() {
        return Objects.hash(System.identityHashCode(this.block), this.meta, this.ext);
    }

    @Override
    public String toString() {
        return "Block{" + Block.blockRegistry.getNameForObject(this.block) + "}" + (this.getValues().isEmpty() ? "[" + this.meta + "]" : this.getValues());
    }

    static Map<Property<?>, Comparable<?>> none() {
        return Collections.emptyMap();
    }

    public interface HasStateDefinition {
        StateDefinition getStateDefinition();
    }
}
