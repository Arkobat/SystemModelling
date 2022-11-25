package io.githib.arkobat.smas.test;

import io.githib.arkobat.smas.ConsoleColor;
import io.githib.arkobat.smas.IRandom;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.*;

// Kristian
public class KolmogorovSmirnov implements Testable {

    private final int numbers;
    private final IRandom random;
    private final List<Double> values = new ArrayList<>();

    public KolmogorovSmirnov(IRandom random, int numbers) {
        this.random = random;
        this.numbers = numbers;
    }

    public void test() {
        System.out.println("=============================");
        System.out.println("     Kolmogorov Smirnov     ");
        System.out.println("=============================");

        System.out.printf(" a:................. %d%n", random.getA());
        System.out.printf(" c:.................... %d%n", random.getC());
        System.out.printf(" m:.................. %d%n", random.getM());
        System.out.printf(" seed:........... %d%n", random.getSeed());
        System.out.printf(" Entries in test:...... %d%n", numbers);

        for (int i = 0; i < numbers; i++) {
            values.add(random.next());
        }
        Collections.sort(values);

        List<KolmogorovSmirnovRow> rows = new ArrayList<>();
        for (int i = 0; i < numbers; i++) {

            double value = values.get(i);
            double index = (double) i / numbers;
            double dPlus = (( i+1D) / numbers) - value;
            double dMinus = value - (double) (i) / numbers;

            rows.add(new KolmogorovSmirnovRow(value, index, dPlus, dMinus));
        }

        KolmogorovSmirnovRow dMax = rows.stream()
                .max(Comparator.comparing(KolmogorovSmirnovRow::getDPlus))
                .orElseThrow(NoSuchElementException::new);

        KolmogorovSmirnovRow dMin = rows.stream()
                .max(Comparator.comparing(KolmogorovSmirnovRow::getDMinus))
                .orElseThrow(NoSuchElementException::new);

        double d = Math.max(dMax.getDPlus(), dMin.getDMinus());

        SignificanceLevel significanceLevel = new SignificanceLevel(0.05);
        double da = significanceLevel.getDA(numbers);

        System.out.println("=============================");
        System.out.printf(" Significance Level: %.5f%n", significanceLevel.a);
        System.out.printf(" D+:................ %.5f%n", dMax.getDPlus());
        System.out.printf(" D-:................ %.5f%n", dMin.getDMinus());
        System.out.printf(" D:................. %.5f%n", d);
        System.out.println("=============================");
        if (d <= da) { // Accepted
            accept(String.format("%.3f <= %.3f", d, da));
        } else { // Rejected
            reject(String.format(" %.3f > %.3f", d, da));
        }
        System.out.println("=============================");
    }

    @Getter
    @AllArgsConstructor
    private static class KolmogorovSmirnovRow {

        private final double value;
        private final double index;
        private final double dPlus;
        private final double dMinus;

    }

    @RequiredArgsConstructor
    private static class SignificanceLevel {

        private final double a;

        public double getDA(int entries) {
            if (entries <= 35) throw new IllegalArgumentException("Table is not defined for 35 or less entries ");

            if (a == 0.20) {
                return 1.07 / Math.sqrt(entries);
            } else if (a == 0.15) {
                return 1.14 / Math.sqrt(entries);
            } else if (a == 0.10) {
                return 1.22 / Math.sqrt(entries);
            } else if (a == 0.05) {
                return 1.36 / Math.sqrt(entries);
            } else if (a == 0.01) {
                return 1.63 / Math.sqrt(entries);
            }
            throw new IllegalArgumentException("Invalid a");
        }
    }


}
