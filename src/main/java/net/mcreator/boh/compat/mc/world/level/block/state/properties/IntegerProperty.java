package net.mcreator.boh.compat.mc.world.level.block.state.properties;

public class IntegerProperty extends Property<Integer> {
    private final int min;
    private final int max;

    protected IntegerProperty(String name, int min, int max) {
        super(name, Integer.class);
        this.min = min;
        this.max = max;

        for (int i = min; i <= max; i++) {
            this.values.add(i);
        }
    }

    public static IntegerProperty create(String name, int min, int max) {
        return new IntegerProperty(name, min, max);
    }

    public String getName(Integer v) {
        return v.toString();
    }

    public int indexOf(Integer v) {
        return v == null ? 0 : Math.max(0, Math.min(this.max - this.min, v - this.min));
    }

    public Integer byIndex(int i) {
        return this.min + Math.max(0, Math.min(this.max - this.min, i));
    }
}
