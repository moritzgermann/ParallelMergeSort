package com.github.moritzgermann.sort;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class SequentialMergeSortTest {

    @Test
    void testEmptyArray() {
        int[] input = {};
        SequentialMergeSort.sort(input);
        assertArrayEquals(new int[]{}, input);
    }

    @Test
    void testSingleElementArray() {
        int[] input = {99};
        SequentialMergeSort.sort(input);
        assertArrayEquals(new int[]{99}, input);
    }

    @Test
    void testAlreadySortedArray() {
        int[] input = {1, 2, 3, 4, 5};
        SequentialMergeSort.sort(input);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, input);
    }

    @Test
    void testReverseSortedArray() {
        int[] input = {9, 7, 5, 3, 1};
        SequentialMergeSort.sort(input);
        assertArrayEquals(new int[]{1, 3, 5, 7, 9}, input);
    }

    @Test
    void testUnsortedArrayWithDuplicates() {
        int[] input = {4, 2, 4, 1, 3};
        SequentialMergeSort.sort(input);
        assertArrayEquals(new int[]{1, 2, 3, 4, 4}, input);
    }

    @Test
    void testAllElementsEqual() {
        int[] input = {5, 5, 5, 5};
        SequentialMergeSort.sort(input);
        assertArrayEquals(new int[]{5, 5, 5, 5}, input);
    }

    @Test
    void testNegativeNumbersIncluded() {
        int[] input = {3, -1, 4, -5, 0};
        SequentialMergeSort.sort(input);
        assertArrayEquals(new int[]{-5, -1, 0, 3, 4}, input);
    }

    @Test
    void testLargeRandomArray() {
        int[] input = new int[1000];
        for (int i = 0; i < input.length; i++) {
            input[i] = (int)(Math.random() * 10000 - 5000);
        }

        int[] expected = Arrays.copyOf(input, input.length);
        Arrays.sort(expected);

        SequentialMergeSort.sort(input);
        assertArrayEquals(expected, input);
    }
}
