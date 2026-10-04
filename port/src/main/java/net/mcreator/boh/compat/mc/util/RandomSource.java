package net.mcreator.boh.compat.mc.util;

import java.util.Random;

/** 1.20 RandomSource as a {@link Random}, so it can be handed straight to 1.7.10 APIs. */
public class RandomSource extends Random {

    private final Random backing;

    private RandomSource(Random backing) {
        this.backing = backing;
    }

    public static RandomSource create() {
        return new RandomSource(new Random());
    }

    public static RandomSource create(long seed) {
        return new RandomSource(new Random(seed));
    }

    public static RandomSource wrap(Random r) {
        if (r instanceof RandomSource) return (RandomSource) r;
        return new RandomSource(r == null ? new Random() : r);
    }

    @Override
    protected int next(int bits) {
        // only reached through methods we do not override; keep the sequence on the backing generator
        return backing == null ? super.next(bits) : backing.nextInt() >>> (32 - bits);
    }

    @Override
    public int nextInt() {
        return backing.nextInt();
    }

    @Override
    public int nextInt(int bound) {
        return backing.nextInt(bound);
    }

    public int nextInt(int origin, int bound) {
        return origin >= bound ? origin : origin + backing.nextInt(bound - origin);
    }

    public int nextIntBetweenInclusive(int min, int max) {
        return min + backing.nextInt(max - min + 1);
    }

    @Override
    public long nextLong() {
        return backing.nextLong();
    }

    @Override
    public boolean nextBoolean() {
        return backing.nextBoolean();
    }

    @Override
    public float nextFloat() {
        return backing.nextFloat();
    }

    @Override
    public double nextDouble() {
        return backing.nextDouble();
    }

    @Override
    public synchronized double nextGaussian() {
        return backing.nextGaussian();
    }

    public double triangle(double center, double spread) {
        return center + spread * (backing.nextDouble() - backing.nextDouble());
    }

    @Override
    public synchronized void setSeed(long seed) {
        if (backing != null) backing.setSeed(seed);
    }

    public RandomSource fork() {
        return new RandomSource(new Random(backing.nextLong()));
    }
}
