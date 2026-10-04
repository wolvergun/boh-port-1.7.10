package net.mcreator.boh.compat.mc.world.entity.ai.attributes;

import java.util.UUID;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;

public class AttributeInstance {
    private final IAttributeInstance handle;

    public AttributeInstance(IAttributeInstance handle) {
        this.handle = handle;
    }

    public double getValue() {
        return this.handle.getAttributeValue();
    }

    public double getBaseValue() {
        return this.handle.getBaseValue();
    }

    public void setBaseValue(double v) {
        this.handle.setBaseValue(v);
    }

    public void addTransientModifier(AttributeModifier m) {
        if (this.handle.getModifier(m.getID()) == null) {
            this.handle.applyModifier(m);
        }
    }

    public void addPermanentModifier(AttributeModifier m) {
        this.addTransientModifier(m);
    }

    public void removeModifier(AttributeModifier m) {
        this.handle.removeModifier(m);
    }

    public void removeModifier(UUID id) {
        AttributeModifier m = this.handle.getModifier(id);
        if (m != null) {
            this.handle.removeModifier(m);
        }
    }

    public boolean hasModifier(AttributeModifier m) {
        return this.handle.getModifier(m.getID()) != null;
    }

    public AttributeModifier getModifier(UUID id) {
        return this.handle.getModifier(id);
    }

    public IAttributeInstance vanilla() {
        return this.handle;
    }
}
