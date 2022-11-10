package io.githib.arkobat.smas;

import io.githib.arkobat.smas.test.*;

import java.util.function.Supplier;

public class Main {

    private static final long SEED = 123456789;
    public static final Supplier<IRandom> RANDOM_ONE =() -> new LinearCongruentialRandom(
            101_427,
            321,
            (long) Math.pow(2, 16),
            SEED
    );

    public static final Supplier <IRandom> RANDOM_TWO = () -> new LinearCongruentialRandom(
            65_539,
            0,
            (long) Math.pow(2, 31),
            SEED
    );

    public static void main(String[] args) {
        new KolmogorovSmirnov(RANDOM_ONE.get(), 100).test();
        new KolmogorovSmirnov(RANDOM_TWO.get(), 100).test();
        System.out.println("\n\n");

        new ChiSq(RANDOM_ONE.get(), RANDOM_TWO.get(), 10_000, 10).test();
        System.out.println("\n\n");

        new Runs().test();
        System.out.println("\n\n");

        new Autocorrelation().test();
    }

}