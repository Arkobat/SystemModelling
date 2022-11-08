package io.githib.arkobat.smas.test;

import io.githib.arkobat.smas.DoubleComparator;
import io.githib.arkobat.smas.IRandom;
import io.githib.arkobat.smas.LinearCongruentialRandom;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

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

        List<KolmogorovSmirnovRow> rows = new ArrayList<>();
        final double n = values.size();
        for (int i = 0; i < n; i++) {

            double value = values.get(i);
            double index = (double) i / n;
            double dPlus = index - value;
            double dMinus = value - (i - 1) / n;

            rows.add(new KolmogorovSmirnovRow(value, index, dPlus, dMinus));
        }

        KolmogorovSmirnovRow dMax = rows.stream()
                .max(Comparator.comparing(KolmogorovSmirnovRow::getdPlus))
                .orElseThrow(NoSuchElementException::new);

        KolmogorovSmirnovRow dMin = rows.stream()
                .max(Comparator.comparing(KolmogorovSmirnovRow::getdMinus))
                .orElseThrow(NoSuchElementException::new);

        double d = Math.max(dMax.getdPlus(), dMin.getdMinus());

    }

    private static class KolmogorovSmirnovRow{

        private final double value;
        private final double index;
        private final double dPlus;
        private final double dMinus;

        public KolmogorovSmirnovRow(double a, double b, double c, double d) {
            this.value = a;
            this.index = b;
            this.dPlus = c;
            this.dMinus = d;
        }

        public double getValue() {
            return value;
        }

        public double getIndex() {
            return index;
        }

        public double getdPlus() {
            return dPlus;
        }

        public double getdMinus() {
            return dMinus;
        }
    }

}
