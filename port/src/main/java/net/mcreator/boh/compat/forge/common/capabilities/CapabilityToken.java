package net.mcreator.boh.compat.forge.common.capabilities;

/** Forge CapabilityToken (subclassed anonymously to capture the type). */
public abstract class CapabilityToken<T> {

    String name() {
        java.lang.reflect.Type t = getClass().getGenericSuperclass();
        return t.getTypeName();
    }
}
