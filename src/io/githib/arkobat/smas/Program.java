package io.githib.arkobat.smas;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

public class Program {

    public static final long SEED = 987654321;
    private final IRandom linearCongruential1 = new LinearCongruentialRandom(
            101_427,
            321,
            (int) Math.pow(2, 16),
            SEED
    );

    public final IRandom linearCongruential2 = new LinearCongruentialRandom(
            65_539,
            0,
            (int) Math.pow(2, 31),
            SEED
    );

    public void compare() {
        Random random = new Random(SEED);
        List<Double> listOne = generateNumbers(random::nextDouble);
        List<Double> listTwo = generateNumbers(linearCongruential1::next);
        List<Double> listThree = generateNumbers(linearCongruential2::next);

    }

    public List<Double> generateNumbers( Supplier<Double> supplier) {
        List<Double> numbers = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            numbers.add(supplier.get());
        }
        return numbers;
    }

}
