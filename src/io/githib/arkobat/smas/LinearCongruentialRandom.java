package io.githib.arkobat.smas;

public class LinearCongruentialRandom implements IRandom {

    private final long a, c, m, seed;

    private long prevResult;

    public LinearCongruentialRandom(int a, int c, long m) {
        this(a, c, m, System.currentTimeMillis());
    }

    public LinearCongruentialRandom(int a, int c, long m, long seed) {
        this.a = a;
        this.c = c;
        this.m = m;
        this.seed = seed;
        this.prevResult = seed;
    }

    @Override
    public double next() {
        prevResult = (a * prevResult + c) % m;
        return prevResult / (double) m;
    }

    @Override
    public long getA() {
        return a;
    }

    @Override
    public long getC() {
        return c;
    }

    @Override
    public long getM() {
        return m;
    }

    @Override
    public long getSeed() {
        return seed;
    }

}
