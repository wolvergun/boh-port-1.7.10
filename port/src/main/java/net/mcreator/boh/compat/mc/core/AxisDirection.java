package net.mcreator.boh.compat.mc.core;

/** 1.20 Direction.AxisDirection. */
public enum AxisDirection {

    POSITIVE(1),
    NEGATIVE(-1);

    private final int step;

    AxisDirection(int step) {
        this.step = step;
    }

    public int getStep() {
        return step;
    }
}
