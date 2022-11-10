package io.githib.arkobat.smas.test;

import java.util.*;

import static io.githib.arkobat.smas.Main.RANDOM_ONE;
import static io.githib.arkobat.smas.Main.RANDOM_TWO;
import static io.githib.arkobat.smas.Program.SEED;

// Have
public class ChiSq implements Testable {

    private final Random random = new Random(SEED);

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
        chiSquared(values);
        values.clear();
        System.out.println();

        System.out.println("Default LCG settings:");
        for (int i = 0; i < numbers; i++) {
            values.add(RANDOM_ONE.get().next());
        }
        chiSquared(values);
        values.clear();
        System.out.println();

        System.out.println("LCG with the RANDU settings:");
        for (int i = 0; i < numbers; i++) {
            values.add(RANDOM_TWO.get().next());
        }
        chiSquared(values);
    }

    // H0 - Frequencies are the same
    // Alternative - Different
    // Confidence level - 0.05 (5% significant)
    // We will divide the interval [0, 1] into k = 10 intervals

    private void chiSquared(List<Double> randomNumbers) {
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
        double expected = (double) numbers / k; // Since we have 10,000 numbers divided over 10 categories, we expect 1,000 numbers in each category
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
        Map<Integer, Double> criticalValues = new HashMap<Integer, Double>() {
            {
                put(1, 3.84);
                put(2, 5.99);
                put(3, 7.81);
                put(4, 9.49);
                put(5, 11.07);
                put(6, 12.59);
                put(7, 14.07);
                put(8, 15.51);
                put(9, 16.92);
                put(10, 18.31);
                put(11, 19.68);
                put(12, 21.03);
                put(13, 22.36);
                put(14, 23.68);
                put(15, 25.00);
                put(16, 26.30);
                put(17, 27.59);
                put(18, 28.87);
                put(19, 30.14);
                put(20, 31.41);
                put(22, 33.92);
                put(24, 36.42);
                put(26, 38.89);
                put(28, 41.34);
                put(30, 43.77);
                put(40, 55.76);
                put(50, 67.50);
                put(60, 79.08);
            }
        };

        double criticalValue = criticalValues.get(k - 1);
        boolean result = chiSquared <= criticalValue;
        System.out.println(chiSquared + " <= " + criticalValue + ": " + result);
        System.out.println("The null hypothesis is therefore " + (result ? "not " : "") + "rejected");
    }
}
