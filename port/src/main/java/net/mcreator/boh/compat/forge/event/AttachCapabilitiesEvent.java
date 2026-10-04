package net.mcreator.boh.compat.forge.event;

import java.util.LinkedHashMap;
import java.util.Map;

import net.mcreator.boh.compat.forge.common.capabilities.ICapabilityProvider;
import net.minecraft.util.ResourceLocation;
import cpw.mods.fml.common.eventhandler.Event;

/** Forge AttachCapabilitiesEvent (entities only). */
public class AttachCapabilitiesEvent<T> extends Event {

    public AttachCapabilitiesEvent() {
        this(null);
    }

    private final T object;
    private final Map<ResourceLocation, ICapabilityProvider> caps = new LinkedHashMap<>();

    public AttachCapabilitiesEvent(T object) {
        this.object = object;
    }

    public T getObject() {
        return object;
    }

    public void addCapability(ResourceLocation key, ICapabilityProvider p) {
        caps.put(key, p);
    }

    public Map<ResourceLocation, ICapabilityProvider> getCapabilities() {
        return caps;
    }

    public void addListener(Runnable r) {}
}
