package net.mcreator.boh.compat.forge.common.capabilities;

import net.mcreator.boh.compat.forge.common.util.LazyOptional;
import net.mcreator.boh.compat.mc.core.Direction;

public interface ICapabilityProvider {
    <T> LazyOptional<T> getCapability(Capability<T> var1, Direction var2);

    default <T> LazyOptional<T> getCapability(Capability<T> cap) {
        return this.getCapability(cap, null);
    }
}
