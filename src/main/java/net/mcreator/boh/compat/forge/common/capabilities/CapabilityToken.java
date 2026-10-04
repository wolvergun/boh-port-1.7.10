package net.mcreator.boh.compat.forge.common.capabilities;

import java.lang.reflect.Type;

public abstract class CapabilityToken<T> {
    String name() {
        Type t = this.getClass().getGenericSuperclass();
        return t.getTypeName();
    }
}
