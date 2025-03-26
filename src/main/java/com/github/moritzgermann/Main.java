package com.github.moritzgermann;

import com.github.moritzgermann.constants.ApplicationConstants;
import com.github.moritzgermann.input.FileInputHandler;
import com.github.moritzgermann.input.FileLoadResult;
import com.github.moritzgermann.output.ArrayFileWriter;
import com.github.moritzgermann.sort.ParallelMergeSort;
import com.github.moritzgermann.sort.SequentialMergeSort;
import com.github.moritzgermann.util.SortedValidator;

import java.nio.file.Path;
import java.util.Optional;

/**
 * The {@code Main} class serves as the entry point for the Parallel Merge Sort application.
 * It provides functionality to load an array of numbers from a file, sort them using both
 * parallel and sequential merge sort algorithms, and save the sorted results to a file.
 */
public class Main {
    /**
     * The main method initializes the application, processes user input for file loading,
     * performs sorting operations using parallel and sequential merge sort algorithms,
     * and writes the sorted results to an output file.
     *
     * @param args Command-line arguments (not used in this application).
     */
    public static void main(String[] args) {
        System.out.println("\nWelcome to Parallel Merge Sort!");
        while (true) {
            Optional<FileLoadResult> fileLoadResultOptional = FileInputHandler.handleFileInput();
            if (fileLoadResultOptional.isEmpty()) break;

            FileLoadResult fileLoadResult = fileLoadResultOptional.get();

            int[] numbers = fileLoadResult.getNumbers();
            Path sourcePath = fileLoadResult.getSourcePath();
            System.out.println("Number of elements to sort: " + numbers.length + "\n");

            int[] sorted = parallelMergeSort(numbers);
            sequentialMergeSort(numbers);
            writeSortingResultToFile(sorted, sourcePath.getFileName().toString());
        }
        System.out.println("\nThank you for using Parallel Merge Sort!\n");
    }

    /**
     * Sorts the given array using the Parallel Merge Sort algorithm and validates the result.
     *
     * @param numbers The array of integers to be sorted.
     * @return A new array containing the sorted integers.
     */
    private static int[] parallelMergeSort(int[] numbers) {
        System.out.println("Sorting using Parallel Merge Sort...");
        long start = System.currentTimeMillis();
        int[] sorted = ParallelMergeSort.sort(numbers);
        long end = System.currentTimeMillis();
        System.out.println("Parallel Merge Sort completed in " + (end - start) + " ms.");
        System.out.println("Array is correctly sorted: " + SortedValidator.isSortedAscending(sorted) + "\n");
        return sorted;
    }

    /**
     * Sorts the given array using the Sequential Merge Sort algorithm and validates the result.
     *
     * @param numbers The array of integers to be sorted. The array is sorted in place.
     */
    private static void sequentialMergeSort(int[] numbers) {
        System.out.println("Sorting using Sequential Merge Sort...");
        long start = System.currentTimeMillis();
        SequentialMergeSort.sort(numbers);
        long end = System.currentTimeMillis();
        System.out.println("Sequential Merge Sort completed in " + (end - start) + " ms.");
        System.out.println("Array is correctly sorted: " + SortedValidator.isSortedAscending(numbers) + "\n");
    }

    /**
     * Writes the sorted array to a file in the specified output directory.
     *
     * @param sorted         The array of integers that was sorted.
     * @param sourceFileName The name of the original source file (used to generate the output file name).
     */
    private static void writeSortingResultToFile(int[] sorted, String sourceFileName) {
        String sortedFileName = getSortedFileName(sourceFileName);
        Optional<Path> path = ArrayFileWriter.writeArrayToFile(sorted, ApplicationConstants.OUTPUT_DIRECTORY_PATH, sortedFileName);
        if (path.isPresent()) {
            System.out.println("Sorted file successfully saved to:");
            System.out.println(path.get() + "\n");
        } else {
            System.out.println("Failed to save the sorted array to a file.\n");
        }
    }

    /**
     * Generates the output file name for the sorted results by appending "_sorted" to the original file name.
     *
     * @param sourceFileName The name of the original source file.
     * @return The generated file name for the sorted results.
     */
    private static String getSortedFileName(String sourceFileName) {
        int dotIndex = sourceFileName.indexOf('.');
        return (dotIndex == -1)
                ? sourceFileName + "_sorted.txt"
                : sourceFileName.substring(0, dotIndex) + "_sorted" + sourceFileName.substring(dotIndex);
    }
}

