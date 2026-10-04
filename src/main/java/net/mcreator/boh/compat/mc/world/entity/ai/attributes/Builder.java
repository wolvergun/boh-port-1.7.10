package net.mcreator.boh.compat.mc.world.entity.ai.attributes;

import java.util.LinkedHashMap;
import java.util.Map;
import net.minecraft.entity.ai.attributes.IAttribute;

public class Builder {
    final Map<IAttribute, Double> values = new LinkedHashMap<>();

    public Builder add(IAttribute attribute) {
        this.values.put(attribute, attribute.getDefaultValue());
        return this;
    }

    public Builder add(IAttribute attribute, double value) {
        this.values.put(attribute, value);
        return this;
    }

    public Builder combine(Builder other) {
        this.values.putAll(other.values);
        return this;
    }

    public AttributeSupplier build() {
        return new AttributeSupplier(this.values);
    }
}
