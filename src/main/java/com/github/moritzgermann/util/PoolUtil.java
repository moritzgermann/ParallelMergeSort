package com.github.moritzgermann.util;

import java.util.concurrent.ForkJoinPool;

/**
 * Utility class providing a shared {@link ForkJoinPool} instance for parallel task execution.
 * <p>
 * The pool is initialized with a parallelism level equal to the number of available processors
 * (as returned by {@link Runtime#availableProcessors()}). This allows optimal use of system resources
 * for compute-intensive parallel tasks, such as sorting or parsing operations.
 * </p>
 *
 * <p>This class is intended to be used as a shared pool for parallel algorithms across the application.</p>
 *
 * <p>Example usage:</p>
 * <pre>{@code
 * int[] result = PoolUtil.pool.invoke(new MergeSortTask(input));
 * }</pre>
 */
public class PoolUtil {

    /**
     * The shared {@link ForkJoinPool} used for parallel task execution.
     * Initialized once with a parallelism level equal to the number of available CPU cores.
     */
    public static final ForkJoinPool pool;

    static {
        int parallelism = Runtime.getRuntime().availableProcessors();
        pool = new ForkJoinPool(parallelism);
    }
}
