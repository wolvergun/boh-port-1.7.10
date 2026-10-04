package net.mcreator.boh.compat.mc.world.level.block.state;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import net.mcreator.boh.compat.mc.world.level.block.state.properties.Property;
import net.minecraft.block.Block;

public final class StateDefinition {
    private final Block block;
    private final List<Property<?>> properties;
    private final int[] radix;
    private final int metaProps;
    private final int metaCapacity;

    StateDefinition(Block block, List<Property<?>> properties) {
        this.block = block;
        this.properties = Collections.unmodifiableList(new ArrayList<>(properties));
        this.radix = new int[properties.size()];
        int product = 1;
        int n = 0;

        for (int i = 0; i < properties.size(); i++) {
            this.radix[i] = properties.get(i).size();
            if (n == i && product * this.radix[i] <= 16) {
                product *= this.radix[i];
                n++;
            }
        }

        this.metaProps = n;
        this.metaCapacity = product;
    }

    public static StateDefinition empty(Block block) {
        return new StateDefinition(block, Collections.emptyList());
    }

    public Block getOwner() {
        return this.block;
    }

    public Collection<Property<?>> getProperties() {
        return this.properties;
    }

    public Property<?> getProperty(String name) {
        for (Property<?> p : this.properties) {
            if (p.getName().equals(name)) {
                return p;
            }
        }

        return null;
    }

    public boolean has(Property<?> p) {
        return this.properties.contains(p);
    }

    public int indexOf(Property<?> p) {
        return this.properties.indexOf(p);
    }

    public boolean needsExtended() {
        return this.metaProps < this.properties.size();
    }

    public int[] encode(int[] idx) {
        int meta = 0;
        int mul = 1;

        for (int i = 0; i < this.metaProps; i++) {
            meta += idx[i] * mul;
            mul *= this.radix[i];
        }

        int ext = 0;
        mul = 1;

        for (int i = this.metaProps; i < this.properties.size(); i++) {
            ext += idx[i] * mul;
            mul *= this.radix[i];
        }

        return new int[]{meta, ext};
    }

    public int[] decode(int meta, int ext) {
        int[] idx = new int[this.properties.size()];
        int m = this.metaCapacity == 0 ? 0 : meta % Math.max(1, this.metaCapacity);

        for (int i = 0; i < this.metaProps; i++) {
            idx[i] = m % this.radix[i];
            m /= this.radix[i];
        }

        for (int i = this.metaProps; i < this.properties.size(); i++) {
            idx[i] = ext % this.radix[i];
            ext /= this.radix[i];
        }

        return idx;
    }

    public BlockState any() {
        return BlockState.of(this.block, 0);
    }

    public List<BlockState> getPossibleStates() {
        List<BlockState> out = new ArrayList<>();
        int total = 1;

        for (int r : this.radix) {
            total *= r;
        }

        for (int i = 0; i < Math.min(total, 4096); i++) {
            int[] idx = new int[this.radix.length];
            int v = i;

            for (int k = 0; k < this.radix.length; k++) {
                idx[k] = v % this.radix[k];
                v /= this.radix[k];
            }

            int[] enc = this.encode(idx);
            out.add(new BlockState(this.block, enc[0], enc[1]));
        }

        return out;
    }
}
