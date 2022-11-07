package io.githib.arkobat.smas.test;

import io.githib.arkobat.smas.IRandom;
import io.githib.arkobat.smas.LinearCongruentialRandom;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static io.githib.arkobat.smas.Program.SEED;
import static java.util.Map.entry;

// Have
public class ChiSq implements Testable {

    private final Random random = new Random(SEED);

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

    private final List<Double> values = new ArrayList<>();

    private final int numbers;

    private final int k;

    public ChiSq(int numbers, int k) {
        this.numbers = numbers;
        this.k = k;
    }

    @Override
    public void test() {
        System.out.println("=======================================================\n" +
                                                "Chi-Square test\n" +
                            "=======================================================");
        System.out.println("For " + numbers + " numbers, k = " + k + " and significance level = 5%\n");

        System.out.println("Java's random library:");
        for (int i = 0; i < numbers; i++) {
            values.add(random.nextDouble());
        }
        chiSquared_v3(values);
        values.clear();
        System.out.println();

        System.out.println("Default LCG settings:");
        for (int i = 0; i < numbers; i++) {
            values.add(linearCongruential1.next());
        }
        chiSquared_v3(values);
        values.clear();
        System.out.println();

        System.out.println("LCG with the RANDU settings:");
        for (int i = 0; i < numbers; i++) {
            values.add(linearCongruential2.next());
        }
        chiSquared_v3(values);
    }

    // H0 - Frequencies are the same
    // Alternative - Different
    // Confidence level - 0.05 (5% significant)
    // We will divide the interval [0, 1] into k = 10 intervals

    private void chiSquared_v3(List<Double> randomNumbers) {
        // Divide the interval [0, 1] into k = 10 intervals
        int[] intervals = new int[k];
        for (double randomNumber : randomNumbers) {
            // Calculate the frequency of each interval i.e. how many numbers are in each interval
            int interval = (int) (randomNumber * k);
            intervals[interval]++;
        }

        int sum = 0;
        double chiSquared = 0;
        // What frequency do we expect in each interval?
        double expected = numbers / k; // Since we have 10,000 numbers divided over 10 categories, we expect 1,000 numbers in each category
        for (int interval : intervals) {
            sum += interval;
            chiSquared += Math.pow(interval - expected, 2) / expected;
        }

        // Sanity check - Make sure all intervals summed up is equal to the number of random numbers
        if (sum != numbers) {
            throw new RuntimeException("Intervals do not sum up to the number of random numbers");
        }

        System.out.println("Chi-squared: " + chiSquared);

        // Compare the chi-squared value with the critical value
        // Our significance level is 5% so we look in the chi-square table at 9 degrees of freedom (k-1) and 0.05 significance level and we get 16.92
        // We can see that our chi-squared value is less than the critical value, so we can't reject the null hypothesis
        System.out.println("Compared to the critical values:");
        checkChiSquared(chiSquared);
    }

    private void checkChiSquared(double chiSquared) {
        // Create a map of degrees of freedom and critical values for 5% significance level
        Map<Integer, Double> criticalValues = Map.ofEntries(
                entry(1, 3.84),
                entry(2, 5.99),
                entry(3, 7.81),
                entry(4, 9.49),
                entry(5, 11.07),
                entry(6, 12.59),
                entry(7, 14.07),
                entry(8, 15.51),
                entry(9, 16.92),
                entry(10, 18.31),
                entry(11, 19.68),
                entry(12, 21.03),
                entry(13, 22.36),
                entry(14, 23.68),
                entry(15, 25.00),
                entry(16, 26.30),
                entry(17, 27.59),
                entry(18, 28.87),
                entry(19, 30.14),
                entry(20, 31.41),
                entry(22, 33.92),
                entry(24, 36.42),
                entry(26, 38.89),
                entry(28, 41.34),
                entry(30, 43.77),
                entry(40, 55.76),
                entry(50, 67.50),
                entry(60, 79.08)
        );

        double criticalValue = criticalValues.get(k - 1);
        boolean result = chiSquared <= criticalValue;
        System.out.println(chiSquared + " <= " + criticalValue + ": " + result);
        System.out.println("The null hypothesis is therefore " + (result ? "not ":"")  + "rejected");
    }
}
