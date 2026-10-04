package net.mcreator.boh.compat.mc.util;

import java.util.Random;

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
        return r instanceof RandomSource ? (RandomSource)r : new RandomSource(r == null ? new Random() : r);
    }

    @Override
    protected int next(int bits) {
        return this.backing == null ? super.next(bits) : this.backing.nextInt() >>> 32 - bits;
    }

    @Override
    public int nextInt() {
        return this.backing.nextInt();
    }

    @Override
    public int nextInt(int bound) {
        return this.backing.nextInt(bound);
    }

    @Override
    public int nextInt(int origin, int bound) {
        return origin >= bound ? origin : origin + this.backing.nextInt(bound - origin);
    }

    public int nextIntBetweenInclusive(int min, int max) {
        return min + this.backing.nextInt(max - min + 1);
    }

    @Override
    public long nextLong() {
        return this.backing.nextLong();
    }

    @Override
    public boolean nextBoolean() {
        return this.backing.nextBoolean();
    }

    @Override
    public float nextFloat() {
        return this.backing.nextFloat();
    }

    @Override
    public double nextDouble() {
        return this.backing.nextDouble();
    }

    @Override
    public synchronized double nextGaussian() {
        return this.backing.nextGaussian();
    }

    public double triangle(double center, double spread) {
        return center + spread * (this.backing.nextDouble() - this.backing.nextDouble());
    }

    @Override
    public synchronized void setSeed(long seed) {
        if (this.backing != null) {
            this.backing.setSeed(seed);
        }
    }

    public RandomSource fork() {
        return new RandomSource(new Random(this.backing.nextLong()));
    }
}
