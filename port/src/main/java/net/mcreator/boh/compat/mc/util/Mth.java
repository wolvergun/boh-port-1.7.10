package net.mcreator.boh.compat.mc.util;

import java.util.Random;

/** 1.20 Mth helpers. */
public final class Mth {

    public static final float PI = (float) Math.PI;
    public static final float HALF_PI = (float) (Math.PI / 2);
    public static final float TWO_PI = (float) (Math.PI * 2);
    public static final float DEG_TO_RAD = (float) (Math.PI / 180);
    public static final float RAD_TO_DEG = (float) (180 / Math.PI);

    private Mth() {}

    public static float sin(float v) {
        return (float) Math.sin(v);
    }

    public static float cos(float v) {
        return (float) Math.cos(v);
    }

    public static float sqrt(float v) {
        return (float) Math.sqrt(v);
    }

    public static int floor(float v) {
        return (int) Math.floor(v);
    }

    public static int floor(double v) {
        return (int) Math.floor(v);
    }

    public static long lfloor(double v) {
        return (long) Math.floor(v);
    }

    public static int ceil(float v) {
        return (int) Math.ceil(v);
    }

    public static int ceil(double v) {
        return (int) Math.ceil(v);
    }

    public static float abs(float v) {
        return Math.abs(v);
    }

    public static int abs(int v) {
        return Math.abs(v);
    }

    public static int clamp(int v, int min, int max) {
        return v < min ? min : Math.min(v, max);
    }

    public static long clamp(long v, long min, long max) {
        return v < min ? min : Math.min(v, max);
    }

    public static float clamp(float v, float min, float max) {
        return v < min ? min : Math.min(v, max);
    }

    public static double clamp(double v, double min, double max) {
        return v < min ? min : Math.min(v, max);
    }

    public static double clampedLerp(double a, double b, double t) {
        return t < 0 ? a : t > 1 ? b : lerp(t, a, b);
    }

    public static float lerp(float t, float a, float b) {
        return a + t * (b - a);
    }

    public static double lerp(double t, double a, double b) {
        return a + t * (b - a);
    }

    public static float rotLerp(float t, float a, float b) {
        return a + t * wrapDegrees(b - a);
    }

    public static float wrapDegrees(float v) {
        float f = v % 360f;
        if (f >= 180f) f -= 360f;
        if (f < -180f) f += 360f;
        return f;
    }

    public static double wrapDegrees(double v) {
        double d = v % 360.0;
        if (d >= 180.0) d -= 360.0;
        if (d < -180.0) d += 360.0;
        return d;
    }

    public static int wrapDegrees(int v) {
        int i = v % 360;
        if (i >= 180) i -= 360;
        if (i < -180) i += 360;
        return i;
    }

    public static float degreesDifference(float a, float b) {
        return wrapDegrees(b - a);
    }

    public static float approachDegrees(float from, float to, float step) {
        float d = degreesDifference(from, to);
        return from + clamp(d, -step, step);
    }

    public static int nextInt(Random r, int min, int max) {
        return min >= max ? min : r.nextInt(max - min + 1) + min;
    }

    public static float nextFloat(Random r, float min, float max) {
        return min >= max ? min : r.nextFloat() * (max - min) + min;
    }

    public static double nextDouble(Random r, double min, double max) {
        return min >= max ? min : r.nextDouble() * (max - min) + min;
    }

    public static int sign(double v) {
        return v == 0 ? 0 : v > 0 ? 1 : -1;
    }

    public static double atan2(double y, double x) {
        return Math.atan2(y, x);
    }

    public static float square(float v) {
        return v * v;
    }

    public static double square(double v) {
        return v * v;
    }

    public static double length(double x, double y) {
        return Math.sqrt(x * x + y * y);
    }

    public static double length(double x, double y, double z) {
        return Math.sqrt(x * x + y * y + z * z);
    }

    public static int positiveModulo(int a, int b) {
        return Math.floorMod(a, b);
    }

    public static double frac(double v) {
        return v - Math.floor(v);
    }

    public static float frac(float v) {
        return v - (float) Math.floor(v);
    }

    public static int color(float r, float g, float b) {
        return color(floor(r * 255f), floor(g * 255f), floor(b * 255f));
    }

    public static int color(int r, int g, int b) {
        return (r << 16) + (g << 8) + b;
    }

    public static double inverseLerp(double v, double a, double b) {
        return (v - a) / (b - a);
    }

    public static boolean equal(double a, double b) {
        return Math.abs(b - a) < 1.0E-5;
    }

    public static boolean equal(float a, float b) {
        return Math.abs(b - a) < 1.0E-5f;
    }
}
