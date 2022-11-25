package io.githib.arkobat.smas.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import io.githib.arkobat.smas.IRandom;

// Anni
public class Runs implements Testable {

    private final int numbers;
    private final IRandom random_1, random_2;
    private final List<Double> values = new ArrayList<>();
    int dof;
    List<Integer> lengthList = new ArrayList<>();

    public Runs(IRandom random_1, IRandom random_2, int numbers) {
        this.random_1 = random_1;
        this.random_2 = random_2;
        this.numbers = numbers;
    }

    @Override
    public void test() {
        System.out.println("=======================================================\n" +
                "Runs test\n" +
                "=======================================================");

        // making arraylist of 10_000 random numbers
        System.out.println("Default LCG settings:");
        for (int i = 0; i < numbers; i++) {
            values.add(random_1.next());
        }
        countRuns(values);
        values.clear();

        System.out.println("LCG with the RANDU settings:");
        for (int i = 0; i < numbers; i++) {
            values.add(random_2.next());
        }
        countRuns(values);
        values.clear();
    }

    private void countRuns(List<Double> values) {
        int length = 1;

        for (int i = 0; i < values.size() - 2; i++) {
            if (values.get(i) < values.get(i + 1)
                    && values.get(i + 1) < values.get(i + 2)
                    || values.get(i) > values.get(i + 1)
                            && values.get(i + 1) > values.get(i + 2)) {
                length++;
                if (values.get(i + 2) == values.get(values.size() - 1)) {
                    lengthList.add(length);
                    length = 1;
                }
            } else {
                if (values.get(i + 2) == values.get(values.size() - 1)) {
                    lengthList.add(length);
                    length = 1;
                }
                lengthList.add(length);
                length = 1;
            }
        }
        mapResults(lengthList);
        length = 1;
        lengthList.clear();
    }

    private void mapResults(List<Integer> lengthList) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i : lengthList) {
            if (map.containsKey(i)) {
                map.put(i, map.get(i) + 1);
            } else {
                map.put(i, 1);
            }
        }
        calculate(map);
    }

    public static long factorial(int number) {
        long result = 1;
        for (int factor = 2; factor <= number; factor++) {
            result *= factor;
        }
        return result;
    }

    private void calculate(Map<Integer, Integer> lengthList) {
        final int n = 10000;
        double sum = 0;
        final List<Double> xList = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : lengthList.entrySet()) {
            int i = entry.getKey();
            int o = entry.getValue();
            double e = 2D / factorial(i + 3) *
                    (n *
                            (Math.pow(i, 2) + 3 * i + 1) -
                            (Math.pow(i, 3) + 3 * Math.pow(i, 2) - i - 4));
            double x = Math.pow(e - o, 2) / e;
            xList.add(x);
            sum = xList.stream().mapToDouble(Double::doubleValue).sum();
        }
        checkChiSquared(sum, xList.size());
    }

    private void checkChiSquared(double chiSquared, int dof) {
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
            }
        };

        double criticalValue = criticalValues.get(dof);
        boolean result = chiSquared <= criticalValue;
        System.out.println("Chi-squared compared to the critical value:");
        System.out.println(chiSquared + " <= " + criticalValue + ": " + result);
        System.out.println("The null hypothesis is therefore " + (result ? "not " : "") + "rejected");
        System.out.println();
    }
}
