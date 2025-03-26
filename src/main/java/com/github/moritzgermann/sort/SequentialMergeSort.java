package com.github.moritzgermann.sort;

/**
 * Provides an implementation of the Merge Sort algorithm for sorting integer arrays.
 * <p>
 * This class uses a sequential, in-place merge sort with a single temporary array
 * to minimize memory allocation. The input array will be sorted in ascending order.
 * </p>
 */
public class SequentialMergeSort {

    /**
     * Sorts the specified array of integers in ascending order using merge sort.
     *
     * @param inputArray the array to be sorted
     */
    public static void sort(int[] inputArray) {
        if (inputArray.length <= 1) return;
        int[] tempArray = new int[inputArray.length];
        mergeSort(inputArray, tempArray, 0, inputArray.length - 1);
    }

    /**
     * Recursively splits and sorts the array using merge sort.
     *
     * @param inputArray the original array to sort
     * @param tempArray  a temporary array used for merging
     * @param leftBound  the starting index of the current subarray
     * @param rightBound the ending index of the current subarray (inclusive)
     */
    private static void mergeSort(int[] inputArray, int[] tempArray, int leftBound, int rightBound) {
        if (leftBound >= rightBound) return;
        int middle = (leftBound + rightBound) / 2;
        mergeSort(inputArray, tempArray, leftBound, middle);
        mergeSort(inputArray, tempArray, middle + 1, rightBound);
        merge(inputArray, tempArray, leftBound, middle, rightBound);
    }

    /**
     * Merges two sorted subarrays of {@code tempArray} back into {@code inputArray}.
     * The subarrays are defined by the indices:
     * <ul>
     *   <li>left subarray: {@code leftBound} to {@code middle}</li>
     *   <li>right subarray: {@code middle + 1} to {@code rightBound}</li>
     * </ul>
     * Only the remaining elements from the left subarray need to be copied back,
     * since the right side is already in correct position after merge.
     *
     * @param inputArray the array where the merged result is written
     * @param tempArray  a copy of the relevant range of {@code inputArray}
     * @param leftBound  the starting index of the merge range
     * @param middle     the middle index dividing the two subarrays
     * @param rightBound the ending index of the merge range (inclusive)
     */
    private static void merge(int[] inputArray, int[] tempArray, int leftBound, int middle, int rightBound) {
        if (rightBound + 1 - leftBound >= 0)
            System.arraycopy(inputArray, leftBound, tempArray, leftBound, rightBound + 1 - leftBound);

        int i = leftBound;
        int j = middle + 1;
        int k = leftBound;

        while (i <= middle && j <= rightBound) {
            if (tempArray[i] <= tempArray[j]) {
                inputArray[k++] = tempArray[i++];
            } else {
                inputArray[k++] = tempArray[j++];
            }
        }

        while (i <= middle) {
            inputArray[k++] = tempArray[i++];
        }
    }
}
