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
        int runs = 1;
        int length = 1;
        for (int i = 0; i < values.size() - 2; i++) {
            if (values.get(i) < values.get(i + 1) && values.get(i + 1) < values.get(i + 2)
                    || values.get(i) > values.get(i + 1) && values.get(i + 1) > values.get(i + 2)) {
                length++;
            } else {

                lengthList.add(length);
                length = 1;
                runs++;
            }
        }
        mapResults(lengthList);
        runs = 1;
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
            double e = 2D / factorial(i + 3) * (n *
                    (Math.pow(i, 2) + 3 * i + 1) -
                    (Math.pow(i, 3) + +3 * Math.pow(i, 2) - 4));
            double x = Math.pow(e - o, 2) / e;
            RunsResults results = new RunsResults(i, e, o, x);
            results2.add(results);
            xList.add(x);
            //get the sum of the x values in xList
            sum = xList.stream().mapToDouble(Double::doubleValue).sum();
        }
      //  System.out.println(results2 + "\n");
        System.out.println("Chi^2 Value = " + sum);
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