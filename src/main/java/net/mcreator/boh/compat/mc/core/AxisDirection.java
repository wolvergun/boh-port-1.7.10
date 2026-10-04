package net.mcreator.boh.compat.mc.core;

public enum AxisDirection {
    POSITIVE(1),
    NEGATIVE(-1);

    private final int step;

    private AxisDirection(int step) {
        this.step = step;
    }

    public int getStep() {
        return this.step;
    }
}
