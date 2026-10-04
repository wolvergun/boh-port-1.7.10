package net.mcreator.boh.compat.mc.core;

/** 1.20 Direction.Axis. */
public enum Axis {

    X,
    Y,
    Z;

    public boolean isHorizontal() {
        return this != Y;
    }

    public boolean isVertical() {
        return this == Y;
    }

    public String getName() {
        return name().toLowerCase();
    }

    public String getSerializedName() {
        return getName();
    }

    public int choose(int x, int y, int z) {
        return this == X ? x : this == Y ? y : z;
    }

    public double choose(double x, double y, double z) {
        return this == X ? x : this == Y ? y : z;
    }
}
