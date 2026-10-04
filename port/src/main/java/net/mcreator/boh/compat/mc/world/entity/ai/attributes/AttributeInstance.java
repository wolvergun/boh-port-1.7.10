package net.mcreator.boh.compat.mc.world.entity.ai.attributes;

import java.util.UUID;

import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;

/** 1.20 AttributeInstance over a 1.7.10 {@link IAttributeInstance}. */
public class AttributeInstance {

    private final IAttributeInstance handle;

    public AttributeInstance(IAttributeInstance handle) {
        this.handle = handle;
    }

    public double getValue() {
        return handle.getAttributeValue();
    }

    public double getBaseValue() {
        return handle.getBaseValue();
    }

    public void setBaseValue(double v) {
        handle.setBaseValue(v);
    }

    public void addTransientModifier(AttributeModifier m) {
        if (handle.getModifier(m.getID()) == null) handle.applyModifier(m);
    }

    public void addPermanentModifier(AttributeModifier m) {
        addTransientModifier(m);
    }

    public void removeModifier(AttributeModifier m) {
        handle.removeModifier(m);
    }

    public void removeModifier(UUID id) {
        AttributeModifier m = handle.getModifier(id);
        if (m != null) handle.removeModifier(m);
    }

    public boolean hasModifier(AttributeModifier m) {
        return handle.getModifier(m.getID()) != null;
    }

    public AttributeModifier getModifier(UUID id) {
        return handle.getModifier(id);
    }

    public IAttributeInstance vanilla() {
        return handle;
    }
}
