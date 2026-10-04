package net.mcreator.boh.geo;

/** One keyframe segment: lasts {@code length} ticks and moves from startValue to endValue. */
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
        return length;
    }

    public EasingType easingType() {
        return easingType;
    }
}
