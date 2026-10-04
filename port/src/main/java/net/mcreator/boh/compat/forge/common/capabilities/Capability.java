package net.mcreator.boh.compat.forge.common.capabilities;

/** Forge Capability key. */
public final class Capability<T> {

    private final String name;

    Capability(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public boolean isRegistered() {
        return true;
    }

    @Override
    public String toString() {
        return name;
    }
}
