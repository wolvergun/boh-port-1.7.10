package net.mcreator.boh.compat.mc.world.entity.ai.attributes;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.BaseAttributeMap;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;

public class AttributeSupplier {
    private static final Map<Class<?>, AttributeSupplier> BY_CLASS = new HashMap<>();
    final Map<IAttribute, Double> values;

    AttributeSupplier(Map<IAttribute, Double> values) {
        this.values = new LinkedHashMap<>(values);
    }

    public static void register(Class<?> entityClass, AttributeSupplier supplier) {
        BY_CLASS.put(entityClass, supplier);
    }

    public static AttributeSupplier forClass(Class<?> entityClass) {
        for (Class<?> c = entityClass; c != null; c = c.getSuperclass()) {
            AttributeSupplier s = BY_CLASS.get(c);
            if (s != null) {
                return s;
            }
        }

        return null;
    }

    public void applyTo(EntityLivingBase entity) {
        BaseAttributeMap map = entity.getAttributeMap();

        for (Entry<IAttribute, Double> e : this.values.entrySet()) {
            IAttributeInstance inst = map.getAttributeInstance(e.getKey());
            if (inst == null) {
                inst = map.registerAttribute(e.getKey());
            }

            inst.setBaseValue(e.getValue());
        }
    }

    public double getBaseValue(IAttribute attribute) {
        Double d = this.values.get(attribute);
        return d == null ? attribute.getDefaultValue() : d;
    }

    public boolean hasAttribute(IAttribute attribute) {
        return this.values.containsKey(attribute);
    }

    public static Builder builder() {
        return new Builder();
    }
}
