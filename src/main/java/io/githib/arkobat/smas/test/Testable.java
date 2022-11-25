package io.githib.arkobat.smas.test;

import io.githib.arkobat.smas.ConsoleColor;

public interface Testable {

    void test();

    default void accept(String text) {
        System.out.printf("%s  Not rejected: %s  %s%n", ConsoleColor.GREEN_BACKGROUND, text, ConsoleColor.RESET);
    }

    default void reject(String text) {
        System.out.printf("%s  Rejected: %s   %s%n", ConsoleColor.RED_BACKGROUND, text, ConsoleColor.RESET);
    }

}
