package net.mcreator.boh.compat.forge.common.capabilities;

import net.mcreator.boh.compat.forge.common.util.LazyOptional;
import net.mcreator.boh.compat.mc.core.Direction;

/** Forge ICapabilityProvider. */
public interface ICapabilityProvider {

    <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side);

    default <T> LazyOptional<T> getCapability(Capability<T> cap) {
        return getCapability(cap, null);
    }
}
