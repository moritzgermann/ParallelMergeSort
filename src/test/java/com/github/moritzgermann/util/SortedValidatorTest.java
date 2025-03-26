package com.github.moritzgermann.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SortedValidatorTest {

    @Test
    void testEmptyArrayIsSorted() {
        int[] input = {};
        assertTrue(SortedValidator.isSortedAscending(input));
    }

    @Test
    void testSingleElementArrayIsSorted() {
        int[] input = {42};
        assertTrue(SortedValidator.isSortedAscending(input));
    }

    @Test
    void testSortedArray() {
        int[] input = {1, 2, 3, 4, 5};
        assertTrue(SortedValidator.isSortedAscending(input));
    }

    @Test
    void testSortedArrayWithDuplicates() {
        int[] input = {1, 2, 2, 3, 4, 4, 5};
        assertTrue(SortedValidator.isSortedAscending(input));
    }

    @Test
    void testUnsortedArray() {
        int[] input = {5, 4, 3, 2, 1};
        assertFalse(SortedValidator.isSortedAscending(input));
    }

    @Test
    void testPartiallySortedArray() {
        int[] input = {1, 2, 5, 4, 6};
        assertFalse(SortedValidator.isSortedAscending(input));
    }

    @Test
    void testNegativeNumbersSorted() {
        int[] input = {-10, -5, 0, 5, 10};
        assertTrue(SortedValidator.isSortedAscending(input));
    }

    @Test
    void testNegativeNumbersUnsorted() {
        int[] input = {-1, -2, -3};
        assertFalse(SortedValidator.isSortedAscending(input));
    }
}
