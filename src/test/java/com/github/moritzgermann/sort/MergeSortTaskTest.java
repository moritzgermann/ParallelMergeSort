package com.github.moritzgermann.sort;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;

import static org.junit.jupiter.api.Assertions.*;

class MergeSortTaskTest {

    private final ForkJoinPool pool = new ForkJoinPool();

    @Test
    void testEmptyArray() {
        int[] input = {};
        int[] sorted = pool.invoke(new MergeSortTask(input));
        assertArrayEquals(new int[]{}, sorted);
    }

    @Test
    void testSingleElementArray() {
        int[] input = {42};
        int[] sorted = pool.invoke(new MergeSortTask(input));
        assertArrayEquals(new int[]{42}, sorted);
    }

    @Test
    void testAlreadySortedArray() {
        int[] input = {1, 2, 3, 4, 5};
        int[] sorted = pool.invoke(new MergeSortTask(input));
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, sorted);
    }

    @Test
    void testReverseSortedArray() {
        int[] input = {5, 4, 3, 2, 1};
        int[] sorted = pool.invoke(new MergeSortTask(input));
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, sorted);
    }

    @Test
    void testUnsortedArrayWithDuplicates() {
        int[] input = {4, 1, 3, 2, 4, 1};
        int[] sorted = pool.invoke(new MergeSortTask(input));
        assertArrayEquals(new int[]{1, 1, 2, 3, 4, 4}, sorted);
    }

    @Test
    void testLargeArray() {
        int[] input = new int[1_000];
        for (int i = 0; i < input.length; i++) {
            input[i] = (int)(Math.random() * 10_000);
        }

        int[] expected = Arrays.copyOf(input, input.length);
        Arrays.sort(expected);

        int[] result = pool.invoke(new MergeSortTask(input));
        assertArrayEquals(expected, result);
    }
}
