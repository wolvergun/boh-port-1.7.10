package net.mcreator.boh.geo;

import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.DoubleUnaryOperator;

/** Keyframe easing curves, matching GeckoLib 4's EasingType semantics. */
@FunctionalInterface
public interface EasingType {

    Map<String, EasingType> EASING_TYPES = new ConcurrentHashMap<>(64);

    EasingType LINEAR = register("linear", register("none", value -> easeIn(EasingType::linear)));
    EasingType STEP = register("step", value -> easeIn(step(value)));
    EasingType EASE_IN_SINE = register("easeinsine", value -> easeIn(EasingType::sine));
    EasingType EASE_OUT_SINE = register("easeoutsine", value -> easeOut(EasingType::sine));
    EasingType EASE_IN_OUT_SINE = register("easeinoutsine", value -> easeInOut(EasingType::sine));
    EasingType EASE_IN_QUAD = register("easeinquad", value -> easeIn(EasingType::quadratic));
    EasingType EASE_OUT_QUAD = register("easeoutquad", value -> easeOut(EasingType::quadratic));
    EasingType EASE_IN_OUT_QUAD = register("easeinoutquad", value -> easeInOut(EasingType::quadratic));
    EasingType EASE_IN_CUBIC = register("easeincubic", value -> easeIn(EasingType::cubic));
    EasingType EASE_OUT_CUBIC = register("easeoutcubic", value -> easeOut(EasingType::cubic));
    EasingType EASE_IN_OUT_CUBIC = register("easeinoutcubic", value -> easeInOut(EasingType::cubic));
    EasingType EASE_IN_QUART = register("easeinquart", value -> easeIn(pow(4)));
    EasingType EASE_OUT_QUART = register("easeoutquart", value -> easeOut(pow(4)));
    EasingType EASE_IN_OUT_QUART = register("easeinoutquart", value -> easeInOut(pow(4)));
    EasingType EASE_IN_QUINT = register("easeinquint", value -> easeIn(pow(4)));
    EasingType EASE_OUT_QUINT = register("easeoutquint", value -> easeOut(pow(5)));
    EasingType EASE_IN_OUT_QUINT = register("easeinoutquint", value -> easeInOut(pow(5)));
    EasingType EASE_IN_EXPO = register("easeinexpo", value -> easeIn(EasingType::exp));
    EasingType EASE_OUT_EXPO = register("easeoutexpo", value -> easeOut(EasingType::exp));
    EasingType EASE_IN_OUT_EXPO = register("easeinoutexpo", value -> easeInOut(EasingType::exp));
    EasingType EASE_IN_CIRC = register("easeincirc", value -> easeIn(EasingType::circle));
    EasingType EASE_OUT_CIRC = register("easeoutcirc", value -> easeOut(EasingType::circle));
    EasingType EASE_IN_OUT_CIRC = register("easeinoutcirc", value -> easeInOut(EasingType::circle));
    EasingType EASE_IN_BACK = register("easeinback", value -> easeIn(back(value)));
    EasingType EASE_OUT_BACK = register("easeoutback", value -> easeOut(back(value)));
    EasingType EASE_IN_OUT_BACK = register("easeinoutback", value -> easeInOut(back(value)));
    EasingType EASE_IN_ELASTIC = register("easeinelastic", value -> easeIn(elastic(value)));
    EasingType EASE_OUT_ELASTIC = register("easeoutelastic", value -> easeOut(elastic(value)));
    EasingType EASE_IN_OUT_ELASTIC = register("easeinoutelastic", value -> easeInOut(elastic(value)));
    EasingType EASE_IN_BOUNCE = register("easeinbounce", value -> easeIn(bounce(value)));
    EasingType EASE_OUT_BOUNCE = register("easeoutbounce", value -> easeOut(bounce(value)));
    EasingType EASE_IN_OUT_BOUNCE = register("easeinoutbounce", value -> easeInOut(bounce(value)));
    EasingType CATMULLROM = register("catmullrom", new CatmullRomEasing());

    DoubleUnaryOperator buildTransformer(Double value);

    static double lerpWithOverride(AnimationPoint point, EasingType override) {
        EasingType easing = override;
        if (easing == null) easing = point.keyFrame == null ? LINEAR : point.keyFrame.easingType;
        return easing.apply(point);
    }

    default double apply(AnimationPoint point) {
        Double easingVariable = null;
        if (point.keyFrame != null && point.keyFrame.easingArgs.length > 0) easingVariable = point.keyFrame.easingArgs[0];
        return apply(point, easingVariable, point.currentTick / point.transitionLength);
    }

    default double apply(AnimationPoint point, Double easingValue, double lerpValue) {
        if (point.currentTick >= point.transitionLength) return (float) point.animationEndValue;
        return lerp(point.animationStartValue, point.animationEndValue, buildTransformer(easingValue).applyAsDouble(lerpValue));
    }

    static double lerp(double a, double b, double t) {
        return a + (b - a) * t;
    }

    static EasingType register(String name, EasingType type) {
        EASING_TYPES.putIfAbsent(name, type);
        return type;
    }

    static EasingType fromString(String name) {
        if (name == null) return LINEAR;
        return EASING_TYPES.getOrDefault(name.toLowerCase(Locale.ROOT), LINEAR);
    }

    static DoubleUnaryOperator linear(DoubleUnaryOperator f) {
        return f;
    }

    static double catmullRom(double n) {
        return 0.5d * (2d * (n + 1d) + 2d + (2d * n - 5d * (n + 1d) + 4d * (n + 2d) - (n + 3d))
            + (3d * (n + 1d) - n - 3d * (n + 2d) + (n + 3d)));
    }

    static DoubleUnaryOperator easeIn(DoubleUnaryOperator f) {
        return f;
    }

    static DoubleUnaryOperator easeOut(DoubleUnaryOperator f) {
        return t -> 1 - f.applyAsDouble(1 - t);
    }

    static DoubleUnaryOperator easeInOut(DoubleUnaryOperator f) {
        return t -> t < 0.5d ? f.applyAsDouble(t * 2d) / 2d : 1 - f.applyAsDouble((1 - t) * 2d) / 2d;
    }

    static double linear(double n) {
        return n;
    }

    static double quadratic(double n) {
        return n * n;
    }

    static double cubic(double n) {
        return n * n * n;
    }

    static double sine(double n) {
        return 1 - Math.cos(n * Math.PI / 2f);
    }

    static double circle(double n) {
        return 1 - Math.sqrt(1 - n * n);
    }

    static double exp(double n) {
        return Math.pow(2, 10 * (n - 1));
    }

    static DoubleUnaryOperator elastic(Double n) {
        double n2 = n == null ? 1 : n;
        return t -> 1 - Math.pow(Math.cos(t * Math.PI / 2f), 3) * Math.cos(t * n2 * Math.PI);
    }

    static DoubleUnaryOperator bounce(Double n) {
        final double n2 = n == null ? 0.5d : n;
        DoubleUnaryOperator one = x -> 121f / 16f * x * x;
        DoubleUnaryOperator two = x -> 121f / 4f * n2 * Math.pow(x - 6f / 11f, 2) + 1 - n2;
        DoubleUnaryOperator three = x -> 121 * n2 * n2 * Math.pow(x - 9f / 11f, 2) + 1 - n2 * n2;
        DoubleUnaryOperator four = x -> 484 * n2 * n2 * n2 * Math.pow(x - 10.5f / 11f, 2) + 1 - n2 * n2 * n2;
        return t -> Math.min(
            Math.min(one.applyAsDouble(t), two.applyAsDouble(t)),
            Math.min(three.applyAsDouble(t), four.applyAsDouble(t)));
    }

    static DoubleUnaryOperator back(Double n) {
        final double n2 = n == null ? 1.70158d : n * 1.70158d;
        return t -> t * t * ((n2 + 1) * t - n2);
    }

    static DoubleUnaryOperator pow(double n) {
        return t -> Math.pow(t, n);
    }

    static DoubleUnaryOperator step(Double n) {
        double n2 = n == null ? 2 : n;
        if (n2 < 2) n2 = 2;
        final int steps = (int) n2;
        final double stepLength = 1 / (double) steps;
        return t -> {
            if (t < 0) return 0;
            double last = (steps - 1) * stepLength;
            if (t > last) return last;
            return Math.floor(t / stepLength) * stepLength;
        };
    }

    final class CatmullRomEasing implements EasingType {

        static double getPointOnSpline(double delta, double p0, double p1, double p2, double p3) {
            return 0.5d * (2d * p1 + (p2 - p0) * delta
                + (2d * p0 - 5d * p1 + 4d * p2 - p3) * delta * delta
                + (3d * p1 - p0 - 3d * p2 + p3) * delta * delta * delta);
        }

        @Override
        public DoubleUnaryOperator buildTransformer(Double value) {
            return easeInOut(EasingType::catmullRom);
        }

        @Override
        public double apply(AnimationPoint point, Double easingValue, double lerpValue) {
            if (point.currentTick >= point.transitionLength) return point.animationEndValue;
            double[] args = point.keyFrame.easingArgs;
            if (args.length < 2) return lerp(point.animationStartValue, point.animationEndValue, lerpValue);
            return getPointOnSpline(lerpValue, args[0], point.animationStartValue, point.animationEndValue, args[1]);
        }
    }
}
