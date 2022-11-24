package io.github.arkobat.smas;

import io.githib.arkobat.smas.LinearCongruentialRandom;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;

public class LinearCongruentialRandomTest {

    private LinearCongruentialRandom random;

    @Before
    public void setup() {
        random = new LinearCongruentialRandom(
                65_539,
                0,
                (long) Math.pow(2, 31),
                123456789
        );
    }

    @Test
    public void canGenerateNumbers() {
        HashSet<Double> generatedNumbers = new HashSet<>();
        for (int i = 0; i < 10; i++) {
            double next = random.next();
            Assert.assertTrue(generatedNumbers.add(next));
        }
    }

}
