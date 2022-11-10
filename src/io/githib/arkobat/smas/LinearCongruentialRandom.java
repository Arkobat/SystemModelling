package io.githib.arkobat.smas;

public class LinearCongruentialRandom implements IRandom {

    private final int a, c;

    private final long m;

    private long prevResult;

    public LinearCongruentialRandom(int a, int c, long m) {
        this(a, c, m, System.currentTimeMillis());
    }

    public LinearCongruentialRandom(int a, int c, long m, long seed) {
        this.a = a;
        this.c = c;
        this.m = m;
        this.prevResult = seed;
    }

    @Override
    public double next() {
        prevResult = (a * prevResult + c) % m;
        return prevResult / (double) m;
    }

}
