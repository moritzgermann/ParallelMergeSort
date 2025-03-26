package com.github.moritzgermann.input;

import com.github.moritzgermann.constants.ApplicationConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

/**
 * A {@link RecursiveTask} implementation that parses a list of strings into integers using the fork/join framework.
 * <p>
 * If the number of lines to be parsed is below the threshold defined in {@link ApplicationConstants#FILE_PARSE_LINE_THRESHOLD},
 * the lines are parsed sequentially. Otherwise, the task is split into two subtasks and processed recursively in parallel.
 * </p>
 */
public class ParseTask extends RecursiveTask<int[]> {

    private final List<String> lines;
    private final int start, end, threshold;

    /**
     * Constructs a new {@code ParseTask} for parsing a sublist of lines.
     *
     * @param lines the list of strings to parse
     * @param start the start index (inclusive) of the range to parse
     * @param end   the end index (exclusive) of the range to parse
     */
    public ParseTask(List<String> lines, int start, int end, int threshold) {
        this.lines = lines;
        this.start = start;
        this.end = end;
        this.threshold = threshold;
    }

    /**
     * Computes and returns the result of parsing the lines.
     * <p>
     * If the range is small enough, parsing is done sequentially. Otherwise, the task is divided into
     * two subtasks that are processed in parallel.
     * </p>
     *
     * @return an array of parsed integers
     * @throws IllegalArgumentException if a line cannot be parsed into an integer
     */
    @Override
    protected int[] compute() {
        if (end - start <= this.threshold) {
            List<String> chunk = lines.subList(start, end);
            List<Integer> integers = parseSequentially(chunk);
            return convertToArray(integers);
        } else {
            int mid = (start + end) / 2;
            ParseTask leftTask = new ParseTask(lines, start, mid, this.threshold);
            ParseTask rightTask = new ParseTask(lines, mid, end, this.threshold);
            leftTask.fork();
            int[] rightTaskResult = rightTask.compute();
            int[] leftTaskResult = leftTask.join();
            return mergeArrays(leftTaskResult, rightTaskResult);
        }
    }

    /**
     * Parses a list of strings into a list of integers sequentially.
     *
     * @param chunk the list of strings to parse
     * @return a list of parsed integers
     * @throws IllegalArgumentException if a string is not a valid integer
     */
    private List<Integer> parseSequentially(List<String> chunk) {
        List<Integer> resultList = new ArrayList<>();
        for (String line : chunk) {
            String trimmed = line.trim();
            if (trimmed.isEmpty()) continue;
            try {
                resultList.add(Integer.parseInt(trimmed));
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid number: '" + trimmed + "'");
            }
        }
        return resultList;
    }

    /**
     * Converts a list of {@link Integer} objects into a primitive {@code int[]} array.
     *
     * @param list the list of integers to convert
     * @return an array of primitive ints
     */
    private int[] convertToArray(List<Integer> list) {
        int[] output = new int[list.size()];
        for (int i = 0; i < output.length; i++) {
            output[i] = list.get(i);
        }
        return output;
    }

    /**
     * Merges two {@code int[]} arrays into a single array.
     *
     * @param left  the first array
     * @param right the second array
     * @return a merged array containing all elements from {@code left} followed by {@code right}
     */
    private int[] mergeArrays(int[] left, int[] right) {
        int[] output = new int[left.length + right.length];
        System.arraycopy(left, 0, output, 0, left.length);
        System.arraycopy(right, 0, output, left.length, right.length);
        return output;
    }
}
