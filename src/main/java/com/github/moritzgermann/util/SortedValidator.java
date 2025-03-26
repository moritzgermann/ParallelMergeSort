package com.github.moritzgermann.util;

/**
 * Utility class for validating the sort order of integer arrays.
 */
public class SortedValidator {

    /**
     * Checks whether the given array is sorted in ascending order.
     * <p>
     * This method iterates through the array and verifies that each element is
     * less than or equal to its successor. An empty array or an array with a single
     * element is considered sorted.
     * </p>
     *
     * @param inputArray the array to check
     * @return {@code true} if the array is sorted in ascending order, {@code false} otherwise
     */
    public static boolean isSortedAscending(int[] inputArray) {
        for (int i = 0; i < inputArray.length - 1; i++) {
            if (inputArray[i] > inputArray[i + 1]) return false;
        }
        return true;
    }
}
