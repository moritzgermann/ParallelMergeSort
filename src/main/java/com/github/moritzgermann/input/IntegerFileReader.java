package com.github.moritzgermann.input;

import com.github.moritzgermann.constants.ApplicationConstants;
import com.github.moritzgermann.util.PoolUtil;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

/**
 * Utility class for reading and parsing integer numbers from a text file.
 * <p>
 * The class reads all lines from the specified file and parses them into an {@code int[]} using a parallel task.
 * Each line is expected to contain a valid integer.
 * Parsing is done using a {@link ParseTask} executed in a common {@link java.util.concurrent.ForkJoinPool} via {@link PoolUtil}.
 * </p>
 */
public class IntegerFileReader {

    /**
     * Reads all lines from the given file and parses them into an array of integers.
     * <p>
     * The method reads the entire file into memory and then processes the content in parallel using {@code ParseTask}.
     * If the file cannot be read or contains invalid content, a {@link RuntimeException} is thrown.
     * </p>
     *
     * @param file the file to read and parse
     * @return an array of integers parsed from the file
     * @throws RuntimeException if the file cannot be read or parsing fails
     */
    public static int[] readNumbers(File file) {
        List<String> lines;
        try {
            lines = Files.readAllLines(file.toPath());
        } catch (IOException e) {
            throw new RuntimeException("Could not read file: " + file.getAbsolutePath(), e);
        }

        try {
            ParseTask parseTask = new ParseTask(lines, 0, lines.size(), ApplicationConstants.FILE_PARSE_LINE_THRESHOLD);
            return PoolUtil.pool.invoke(parseTask);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Parsing error: " + e.getMessage(), e);
        }
    }
}
