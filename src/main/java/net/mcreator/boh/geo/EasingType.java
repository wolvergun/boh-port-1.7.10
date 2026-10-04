package net.mcreator.boh.geo;

import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.DoubleUnaryOperator;

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
    EasingType EASE_IN_QUART = register("easeinquart", value -> easeIn(pow(4.0)));
    EasingType EASE_OUT_QUART = register("easeoutquart", value -> easeOut(pow(4.0)));
    EasingType EASE_IN_OUT_QUART = register("easeinoutquart", value -> easeInOut(pow(4.0)));
    EasingType EASE_IN_QUINT = register("easeinquint", value -> easeIn(pow(4.0)));
    EasingType EASE_OUT_QUINT = register("easeoutquint", value -> easeOut(pow(5.0)));
    EasingType EASE_IN_OUT_QUINT = register("easeinoutquint", value -> easeInOut(pow(5.0)));
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
    EasingType CATMULLROM = register("catmullrom", new EasingType.CatmullRomEasing());

    DoubleUnaryOperator buildTransformer(Double var1);

    static double lerpWithOverride(AnimationPoint point, EasingType override) {
        EasingType easing = override;
        if (override == null) {
            easing = point.keyFrame == null ? LINEAR : point.keyFrame.easingType;
        }

        return easing.apply(point);
    }

    default double apply(AnimationPoint point) {
        Double easingVariable = null;
        if (point.keyFrame != null && point.keyFrame.easingArgs.length > 0) {
            easingVariable = point.keyFrame.easingArgs[0];
        }

        return this.apply(point, easingVariable, point.currentTick / point.transitionLength);
    }

    default double apply(AnimationPoint point, Double easingValue, double lerpValue) {
        return point.currentTick >= point.transitionLength
            ? (float)point.animationEndValue
            : lerp(point.animationStartValue, point.animationEndValue, this.buildTransformer(easingValue).applyAsDouble(lerpValue));
    }

    static double lerp(double a, double b, double t) {
        return a + (b - a) * t;
    }

    static EasingType register(String name, EasingType type) {
        EASING_TYPES.putIfAbsent(name, type);
        return type;
    }

    static EasingType fromString(String name) {
        return name == null ? LINEAR : EASING_TYPES.getOrDefault(name.toLowerCase(Locale.ROOT), LINEAR);
    }

    static DoubleUnaryOperator linear(DoubleUnaryOperator f) {
        return f;
    }

    static double catmullRom(double n) {
        return 0.5 * (2.0 * (n + 1.0) + 2.0 + (2.0 * n - 5.0 * (n + 1.0) + 4.0 * (n + 2.0) - (n + 3.0)) + (3.0 * (n + 1.0) - n - 3.0 * (n + 2.0) + (n + 3.0)));
    }

    static DoubleUnaryOperator easeIn(DoubleUnaryOperator f) {
        return f;
    }

    static DoubleUnaryOperator easeOut(DoubleUnaryOperator f) {
        return t -> 1.0 - f.applyAsDouble(1.0 - t);
    }

    static DoubleUnaryOperator easeInOut(DoubleUnaryOperator f) {
        return t -> t < 0.5 ? f.applyAsDouble(t * 2.0) / 2.0 : 1.0 - f.applyAsDouble((1.0 - t) * 2.0) / 2.0;
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
        return 1.0 - Math.cos(n * Math.PI / 2.0);
    }

    static double circle(double n) {
        return 1.0 - Math.sqrt(1.0 - n * n);
    }

    static double exp(double n) {
        return Math.pow(2.0, 10.0 * (n - 1.0));
    }

    static DoubleUnaryOperator elastic(Double n) {
        double n2 = n == null ? 1.0 : n;
        return t -> 1.0 - Math.pow(Math.cos(t * Math.PI / 2.0), 3.0) * Math.cos(t * n2 * Math.PI);
    }

    static DoubleUnaryOperator bounce(Double n) {
        double n2 = n == null ? 0.5 : n;
        DoubleUnaryOperator one = x -> 7.5625 * x * x;
        DoubleUnaryOperator two = x -> 30.25 * n2 * Math.pow(x - 0.54545456F, 2.0) + 1.0 - n2;
        DoubleUnaryOperator three = x -> 121.0 * n2 * n2 * Math.pow(x - 0.8181818F, 2.0) + 1.0 - n2 * n2;
        DoubleUnaryOperator four = x -> 484.0 * n2 * n2 * n2 * Math.pow(x - 0.95454544F, 2.0) + 1.0 - n2 * n2 * n2;
        return t -> Math.min(Math.min(one.applyAsDouble(t), two.applyAsDouble(t)), Math.min(three.applyAsDouble(t), four.applyAsDouble(t)));
    }

    static DoubleUnaryOperator back(Double n) {
        double n2 = n == null ? 1.70158 : n * 1.70158;
        return t -> t * t * ((n2 + 1.0) * t - n2);
    }

    static DoubleUnaryOperator pow(double n) {
        return t -> Math.pow(t, n);
    }

    static DoubleUnaryOperator step(Double n) {
        double n2 = n == null ? 2.0 : n;
        if (n2 < 2.0) {
            n2 = 2.0;
        }

        int steps = (int)n2;
        double stepLength = 1.0 / steps;
        return t -> {
            if (t < 0.0) {
                return 0.0;
            } else {
                double last = (steps - 1) * stepLength;
                return t > last ? last : Math.floor(t / stepLength) * stepLength;
            }
        };
    }

    public static final class CatmullRomEasing implements EasingType {
        static double getPointOnSpline(double delta, double p0, double p1, double p2, double p3) {
            return 0.5
                * (
                    2.0 * p1
                        + (p2 - p0) * delta
                        + (2.0 * p0 - 5.0 * p1 + 4.0 * p2 - p3) * delta * delta
                        + (3.0 * p1 - p0 - 3.0 * p2 + p3) * delta * delta * delta
                );
        }

        @Override
        public DoubleUnaryOperator buildTransformer(Double value) {
            return EasingType.easeInOut(EasingType::catmullRom);
        }

        @Override
        public double apply(AnimationPoint point, Double easingValue, double lerpValue) {
            if (point.currentTick >= point.transitionLength) {
                return point.animationEndValue;
            } else {
                double[] args = point.keyFrame.easingArgs;
                return args.length < 2
                    ? EasingType.lerp(point.animationStartValue, point.animationEndValue, lerpValue)
                    : getPointOnSpline(lerpValue, args[0], point.animationStartValue, point.animationEndValue, args[1]);
            }
        }
    }
}
