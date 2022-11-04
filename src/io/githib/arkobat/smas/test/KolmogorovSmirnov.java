package io.githib.arkobat.smas.test;

import io.githib.arkobat.smas.DoubleComparator;
import io.githib.arkobat.smas.IRandom;
import io.githib.arkobat.smas.LinearCongruentialRandom;

import java.util.ArrayList;
import java.util.List;

// Kristian
public class KolmogorovSmirnov implements Testable {

    private static final long SEED = 123456789L;
    private final IRandom random = new LinearCongruentialRandom(
            101_427,
            321,
            (int) Math.pow(2, 16),
            SEED
    );
    private final int numbers;
    private final List<Double> values = new ArrayList<>();

    public KolmogorovSmirnov(int numbers) {
        this.numbers = numbers;
    }

    public void test() {
        for (int i = 0; i < numbers; i++) {
            values.add(random.next());
        }

        values.sort(new DoubleComparator());

    }

}
