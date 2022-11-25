package io.githib.arkobat.smas.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.githib.arkobat.smas.IRandom;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

// Anni
public class Runs implements Testable {

    private final int numbers;
    private final IRandom random_1, random_2;
    private final List<Double> values = new ArrayList<>();

    List<Integer> lengthList = new ArrayList<>();
    // List<String> crocodileList = new ArrayList<>();

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
    /*
     * private void countCrocodiles(List<Double> values) {
     * for (int i = 0; i < values.size() - 1; i++) {
     * if (values.get(i) < values.get(i + 1)) {
     * crocodileList.add("<");
     * }
     * else {
     * crocodileList.add(">");
     * }
     * }
     * }
     */

    private void countRuns(List<Double> values) {
        int length = 1;

        // fix length for values, arrays start at 0, might not get the last number
        for (int i = 0; i < values.size() - 2; i++) {
            if (values.get(i) < values.get(i + 1)
                    && values.get(i + 1) < values.get(i + 2)
                    || values.get(i) > values.get(i + 1)
                            && values.get(i + 1) > values.get(i + 2)) {
                length++;
                // last case
                // actually gives: 4.13187384203095
                // supposed to give : 4.130859
                // -0,00101484203095 wrong
                // skal den være der? 
                //det bliver mere forkert hvis jeg sletter den, men så er fejlen positiv
                if (values.get(i + 2) == values.get(values.size() - 1)) {
                    lengthList.add(length);
                    length = 1;
                }
            } else {
                // last case
                // actually gives: 10.092822005498583
                // supposed to give: 10.0931221
                // 0,000300094501416 wrong
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
        List<RunsResults> results2 = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : lengthList.entrySet()) {
            int i = entry.getKey();
            int o = entry.getValue();
            double e = 2D / factorial(i + 3) * 
                    (n *
                    (Math.pow(i, 2) + 3 * i + 1) -
                    (Math.pow(i, 3) + 3 * Math.pow(i, 2) - i - 4));
            double x = Math.pow(e - o, 2) / e;
            RunsResults results = new RunsResults(i, e, o, x);
            results2.add(results);
            xList.add(x);
            // get the sum of the x values in xList
            sum = xList.stream().mapToDouble(Double::doubleValue).sum();
        }
        System.out.println("Chi^2 Value = " + sum + "\n");
    }

    @Getter
    @AllArgsConstructor
    @ToString
    public class RunsResults {
        private int i;
        private double e;
        private int o;
        private double x;
    }
}