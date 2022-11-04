package io.githib.arkobat.smas;

import java.util.Comparator;

public class DoubleComparator implements Comparator<Double> {

    @Override
    public int compare(Double o1, Double o2) {
        double result = o1 - o2;
        if (result > 0) return 1;
        if (result < 0) return -1;
        return 0;
    }
}
