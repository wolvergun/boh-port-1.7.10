package net.mcreator.boh.compat.mc.world.entity.ai.attributes;

import java.util.LinkedHashMap;
import java.util.Map;

import net.minecraft.entity.ai.attributes.IAttribute;

/** 1.20 AttributeSupplier.Builder. */
public class Builder {

    final Map<IAttribute, Double> values = new LinkedHashMap<>();

    public Builder add(IAttribute attribute) {
        values.put(attribute, attribute.getDefaultValue());
        return this;
    }

    public Builder add(IAttribute attribute, double value) {
        values.put(attribute, value);
        return this;
    }

    public Builder combine(Builder other) {
        values.putAll(other.values);
        return this;
    }

    public AttributeSupplier build() {
        return new AttributeSupplier(values);
    }
}
