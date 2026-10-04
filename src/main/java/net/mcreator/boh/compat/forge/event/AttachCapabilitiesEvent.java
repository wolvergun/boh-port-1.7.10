package net.mcreator.boh.compat.forge.event;

import cpw.mods.fml.common.eventhandler.Event;
import java.util.LinkedHashMap;
import java.util.Map;
import net.mcreator.boh.compat.forge.common.capabilities.ICapabilityProvider;
import net.minecraft.util.ResourceLocation;

public class AttachCapabilitiesEvent<T> extends Event {
    private final T object;
    private final Map<ResourceLocation, ICapabilityProvider> caps = new LinkedHashMap<>();

    public AttachCapabilitiesEvent() {
        this(null);
    }

    public AttachCapabilitiesEvent(T object) {
        this.object = object;
    }

    public T getObject() {
        return this.object;
    }

    public void addCapability(ResourceLocation key, ICapabilityProvider p) {
        this.caps.put(key, p);
    }

    public Map<ResourceLocation, ICapabilityProvider> getCapabilities() {
        return this.caps;
    }

    public void addListener(Runnable r) {
    }
}
