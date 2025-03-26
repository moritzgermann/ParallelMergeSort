package com.github.moritzgermann.input;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

import static org.junit.jupiter.api.Assertions.*;

class ParseTaskTest {

    private final ForkJoinPool pool = new ForkJoinPool();

    @Test
    void testSequentialParsingWhenBelowThreshold() {
        List<String> lines = List.of("1", "2", "3");
        int threshold = 10;

        ParseTask task = new ParseTask(lines, 0, lines.size(), threshold);
        int[] result = pool.invoke(task);

        assertArrayEquals(new int[]{1, 2, 3}, result);
    }

    @Test
    void testParallelParsingWhenAboveThreshold() {
        int threshold = 5;
        List<String> lines = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            lines.add(String.valueOf(i));
        }

        ParseTask task = new ParseTask(lines, 0, lines.size(), threshold);
        int[] result = pool.invoke(task);

        int[] expected = new int[20];
        for (int i = 0; i < 20; i++) {
            expected[i] = i;
        }

        assertArrayEquals(expected, result);
    }

    @Test
    void testEmptyInputReturnsEmptyArray() {
        List<String> lines = List.of();
        ParseTask task = new ParseTask(lines, 0, 0, 2);
        int[] result = pool.invoke(task);

        assertArrayEquals(new int[0], result);
    }

    @Test
    void testWhitespaceLinesAreIgnored() {
        List<String> lines = List.of("42", "   ", "\t", "100");
        ParseTask task = new ParseTask(lines, 0, lines.size(), 10);
        int[] result = pool.invoke(task);

        assertArrayEquals(new int[]{42, 100}, result);
    }

    @Test
    void testInvalidIntegerThrowsException() {
        List<String> lines = List.of("1", "2", "notANumber", "4");
        ParseTask task = new ParseTask(lines, 0, lines.size(), 10);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            pool.invoke(task);
        });

        assertTrue(exception.getMessage().contains("Invalid number: 'notANumber'"));
    }

    @Test
    void testCorrectSubRangeParsing() {
        List<String> lines = List.of("10", "20", "30", "40", "50");
        ParseTask task = new ParseTask(lines, 1, 4, 10); // should parse "20", "30", "40"
        int[] result = pool.invoke(task);

        assertArrayEquals(new int[]{20, 30, 40}, result);
    }

    @Test
    void testForceParallelWithThresholdOne() {
        List<String> lines = List.of("7", "8", "9", "10");
        ParseTask task = new ParseTask(lines, 0, lines.size(), 1); // force maximum splitting
        int[] result = pool.invoke(task);

        assertArrayEquals(new int[]{7, 8, 9, 10}, result);
    }
}
