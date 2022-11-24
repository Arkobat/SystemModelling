package io.githib.arkobat.smas.test;

import java.util.ArrayList;
import java.util.List;

import io.githib.arkobat.smas.IRandom;

// Anton
public class Autocorrelation implements Testable {

    private final IRandom random;
    private final int numbers;
    private final int index;
    private final int lag;

    private final List<Double> values = new ArrayList<>();

    public Autocorrelation(IRandom random, int numbers, int index, int lag) {
        this.random = random;
        this.numbers = numbers;
        this.index = index;
        this.lag = lag;
    }

    private double getM() {
        double M = 0;
        for (int i = 0; i < values.size(); i++) {
            if (values.size() >= index + (M + 2) * lag) {
                M++;
            } else {
                break;
            }
        }
        return M;
    }

    public double getAutocorrelation(List<Double> values) {
        double M = getM();
        double autocorrelation = 0;

        for (int i = ((index - 1) + lag); i < values.size(); i += lag) {
            autocorrelation += values.get(i - lag) * values.get(i);
        }

        double rho = (1 / (M + 1)) * autocorrelation - 0.25;

        double sigma = (Math.sqrt(13 * (double) M + 7)) / (12 * ((double) M + 1));

        double z0 = rho / sigma;

        return z0;
    }

    private void populateValues() {
        for (int i = 0; i < numbers; i++) {
            values.add(random.next());
        }
    }

    // make autocorrelation test
    @Override
    public void test() {
        System.out.println(
                "=======================================================\n" +
                        "Autocorrelation Test\n" +
                        "=======================================================");

        System.out.println("Random");
        System.out.println(
            "seed: " + random.getSeed() + " | " + "a: " + random.getA() + " | " +
                        "c: " + random.getC() + " | " +
                        "m: " + random.getM());

        System.out.println("----");
        System.out.println("Numbers: " + numbers);
        System.out.println("Index: " + index);
        System.out.println("Lag: " + lag);

        populateValues();

        System.out.println("z: " + getAutocorrelation(values));

    }
}
