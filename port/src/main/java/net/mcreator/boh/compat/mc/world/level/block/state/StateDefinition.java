package net.mcreator.boh.compat.mc.world.level.block.state;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import net.mcreator.boh.compat.mc.world.level.block.state.properties.Property;
import net.minecraft.block.Block;

/**
 * Property layout of a block. Properties are packed in declaration order; the ones that fit go into the 4-bit
 * block metadata, the rest into an "extended" int kept by {@link ExtendedStateStore}.
 */
public final class StateDefinition {

    private final Block block;
    private final List<Property<?>> properties;
    private final int[] radix;
    private final int metaProps;
    private final int metaCapacity;

    StateDefinition(Block block, List<Property<?>> properties) {
        this.block = block;
        this.properties = Collections.unmodifiableList(new ArrayList<>(properties));
        radix = new int[properties.size()];
        int product = 1, n = 0;
        for (int i = 0; i < properties.size(); i++) {
            radix[i] = properties.get(i).size();
            if (n == i && product * radix[i] <= 16) {
                product *= radix[i];
                n++;
            }
        }
        metaProps = n;
        metaCapacity = product;
    }

    public static StateDefinition empty(Block block) {
        return new StateDefinition(block, Collections.emptyList());
    }

    public Block getOwner() {
        return block;
    }

    public Collection<Property<?>> getProperties() {
        return properties;
    }

    public Property<?> getProperty(String name) {
        for (Property<?> p : properties) if (p.getName().equals(name)) return p;
        return null;
    }

    public boolean has(Property<?> p) {
        return properties.contains(p);
    }

    public int indexOf(Property<?> p) {
        return properties.indexOf(p);
    }

    public boolean needsExtended() {
        return metaProps < properties.size();
    }

    /** Value indices -> (meta, ext). */
    public int[] encode(int[] idx) {
        int meta = 0, mul = 1;
        for (int i = 0; i < metaProps; i++) {
            meta += idx[i] * mul;
            mul *= radix[i];
        }
        int ext = 0;
        mul = 1;
        for (int i = metaProps; i < properties.size(); i++) {
            ext += idx[i] * mul;
            mul *= radix[i];
        }
        return new int[] { meta, ext };
    }

    /** (meta, ext) -> value indices. */
    public int[] decode(int meta, int ext) {
        int[] idx = new int[properties.size()];
        int m = metaCapacity == 0 ? 0 : meta % Math.max(1, metaCapacity);
        for (int i = 0; i < metaProps; i++) {
            idx[i] = m % radix[i];
            m /= radix[i];
        }
        for (int i = metaProps; i < properties.size(); i++) {
            idx[i] = ext % radix[i];
            ext /= radix[i];
        }
        return idx;
    }

    public BlockState any() {
        return BlockState.of(block, 0);
    }

    public List<BlockState> getPossibleStates() {
        List<BlockState> out = new ArrayList<>();
        int total = 1;
        for (int r : radix) total *= r;
        for (int i = 0; i < Math.min(total, 4096); i++) {
            int[] idx = new int[radix.length];
            int v = i;
            for (int k = 0; k < radix.length; k++) {
                idx[k] = v % radix[k];
                v /= radix[k];
            }
            int[] enc = encode(idx);
            out.add(new BlockState(block, enc[0], enc[1]));
        }
        return out;
    }
}
