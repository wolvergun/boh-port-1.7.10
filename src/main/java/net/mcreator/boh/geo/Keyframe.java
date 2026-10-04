package net.mcreator.boh.geo;

public final class Keyframe {
    final double length;
    final double startValue;
    final double endValue;
    final EasingType easingType;
    final double[] easingArgs;

    public Keyframe(double length, double startValue, double endValue, EasingType easingType, double[] easingArgs) {
        this.length = length;
        this.startValue = startValue;
        this.endValue = endValue;
        this.easingType = easingType;
        this.easingArgs = easingArgs;
    }

    public double length() {
        return this.length;
    }

    public EasingType easingType() {
        return this.easingType;
    }
}
