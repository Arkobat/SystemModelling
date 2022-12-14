package io.github.arkobat.smas;

import io.github.arkobat.smas.test.Autocorrelation;
import io.github.arkobat.smas.test.ChiSq;
import io.github.arkobat.smas.test.KolmogorovSmirnov;
import io.github.arkobat.smas.test.Runs;

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

        new Runs(RANDOM_ONE.get(), RANDOM_TWO.get(), 10_000).test();
        System.out.println("\n\n");

        new Autocorrelation(RANDOM_ONE.get(), 10_000, 3, 128).test();
        new Autocorrelation(RANDOM_TWO.get(), 10_000, 3, 128).test();
    }

}