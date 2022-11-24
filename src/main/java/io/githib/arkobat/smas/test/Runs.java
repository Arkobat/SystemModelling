package io.githib.arkobat.smas.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.githib.arkobat.smas.IRandom;
import lombok.AllArgsConstructor;
import lombok.Getter;

// Anni
public class Runs implements Testable {

    // make list of 10000 random numbers
    // make array/object of number of runs and length of runs
    // count number of runs and length of runs
    // Number of runs above/below n
    // Compare count to expected value using known distribution

    private final int numbers;
    private final IRandom random_1, random_2;
    private final List<Double> values = new ArrayList<>();

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

    List<Integer> lengthList = new ArrayList<>();
    Integer biggerThanX = 0;

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
        System.out.println("Total runs: " + lengthList.size());

        // find out how many runs are bigger than x
        for (Integer int1 : lengthList) {
            if (int1 > 3) {
                biggerThanX++;
            }
        }
        mapResults(lengthList);

        System.out.println("There are: " + biggerThanX + " runs longer than 3");
        runs = 1;
        length = 1;
        lengthList.clear();
        biggerThanX = 0;
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
        System.out.println(map);
    }

    public static long factorial(int number) {
        long result = 1;

        for (int factor = 2; factor <= number; factor++) {
            result *= factor;
        }

        return result;
    }

    private void calculate(Map<Integer, Integer> lengthList) {
        System.out.println("u suck");
        final int n = 100;
        List<RunsResults> results2 = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : lengthList.entrySet()) {
            int i = entry.getKey();
            int o = entry.getValue();
            double e = 2 / factorial(i + 3) * (n *
                    (Math.pow(i, 2) + 3 * i + 1) -
                    (Math.pow(i, 3) + +3 *
                            Math.pow(i, 2) - 4));
            RunsResults results = new RunsResults(i, e, o, 0);
            
        }

    }

    @Getter
    @AllArgsConstructor
    public class RunsResults {
        private int i;
        private double e;
        private int o;
        private double eo;

    }
}