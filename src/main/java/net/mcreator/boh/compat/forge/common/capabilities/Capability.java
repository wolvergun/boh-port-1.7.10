package net.mcreator.boh.compat.forge.common.capabilities;

public final class Capability<T> {
    private final String name;

    Capability(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public boolean isRegistered() {
        return true;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
