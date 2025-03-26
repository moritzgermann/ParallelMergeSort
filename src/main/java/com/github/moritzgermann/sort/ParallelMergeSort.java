package com.github.moritzgermann.sort;

import com.github.moritzgermann.util.PoolUtil;

/**
 * Provides a parallel implementation of the Merge Sort algorithm using the Fork/Join framework.
 * <p>
 * This class serves as a simple entry point for sorting an integer array in parallel.
 * It delegates the sorting task to a {@link MergeSortTask} and uses the configured {@link java.util.concurrent.ForkJoinPool}
 * from {@link PoolUtil} to execute the task.
 * </p>
 */
public class ParallelMergeSort {

    /**
     * Sorts the given array in ascending order using parallel merge sort.
     * <p>
     * The method creates a new {@link MergeSortTask} and executes it in the common fork-join pool.
     * The input array itself is not modified; the method returns a new sorted array.
     * </p>
     *
     * @param input the array to be sorted
     * @return a new array containing the sorted elements of {@code input}
     */
    public static int[] sort(int[] input) {
        MergeSortTask mergeSortTask = new MergeSortTask(input);
        return PoolUtil.pool.invoke(mergeSortTask);
    }
}
