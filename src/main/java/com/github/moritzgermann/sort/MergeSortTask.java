package com.github.moritzgermann.sort;

import java.util.Arrays;
import java.util.concurrent.RecursiveTask;

/**
 * A recursive task for sorting an integer array using the Merge Sort algorithm
 * in parallel via the Fork/Join framework.
 * <p>
 * This class splits the input array recursively, sorts each half in parallel,
 * and merges the sorted subarrays. It is designed to be used with a
 * {@link java.util.concurrent.ForkJoinPool}.
 * </p>
 *
 * <p>Example usage:</p>
 * <pre>{@code
 * ForkJoinPool pool = new ForkJoinPool();
 * int[] sorted = pool.invoke(new MergeSortTask(inputArray));
 * }</pre>
 */
public class MergeSortTask extends RecursiveTask<int[]> {
    private final int[] arr;

    /**
     * Constructs a new {@code MergeSortTask} for the given array.
     *
     * @param arr the array to be sorted
     */
    public MergeSortTask(int[] arr) {
        this.arr = arr;
    }

    /**
     * Computes the sorted result of the array using the parallel merge sort algorithm.
     * If the array has one or zero elements, it is returned as-is.
     * Otherwise, the array is split into two halves which are sorted in parallel and merged.
     *
     * @return a new sorted array containing the same elements as {@code arr}
     */
    @Override
    protected int[] compute() {
        if (arr.length <= 1) return arr;

        int mid = arr.length / 2;
        int[] left = Arrays.copyOfRange(arr, 0, mid);
        int[] right = Arrays.copyOfRange(arr, mid, arr.length);

        MergeSortTask leftTask = new MergeSortTask(left);
        MergeSortTask rightTask = new MergeSortTask(right);

        // Start left task asynchronously
        leftTask.fork();

        // Compute right task synchronously (work-stealing optimization)
        int[] arr1 = rightTask.compute();

        // Wait for left task to complete
        int[] arr2 = leftTask.join();

        // Merge both sorted halves
        return mergeArrays(arr1, arr2);
    }

    /**
     * Merges two sorted arrays into a single sorted array.
     *
     * @param arr1 the first sorted array
     * @param arr2 the second sorted array
     * @return a new array containing all elements of {@code arr1} and {@code arr2}, sorted
     */
    private int[] mergeArrays(int[] arr1, int[] arr2) {
        int[] output = new int[arr1.length + arr2.length];
        int i = 0, j = 0, k = 0;

        while (i < arr1.length && j < arr2.length) {
            output[k++] = (arr1[i] <= arr2[j]) ? arr1[i++] : arr2[j++];
        }
        while (i < arr1.length) output[k++] = arr1[i++];
        while (j < arr2.length) output[k++] = arr2[j++];

        return output;
    }
}
