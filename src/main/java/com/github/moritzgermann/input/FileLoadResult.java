package com.github.moritzgermann.input;

import java.nio.file.Path;

/**
 * Holds the result of loading a file, including the parsed array and the source file path.
 */
public class FileLoadResult {
    private final int[] numbers;
    private final Path sourcePath;

    /**
     * Constructs a new {@code FileLoadResult}.
     *
     * @param numbers    the parsed array of numbers
     * @param sourcePath the path to the original input file
     */
    public FileLoadResult(int[] numbers, Path sourcePath) {
        this.numbers = numbers;
        this.sourcePath = sourcePath;
    }

    /**
     * @return the parsed array of numbers
     */
    public int[] getNumbers() {
        return numbers;
    }

    /**
     * @return the original file path from which the data was loaded
     */
    public Path getSourcePath() {
        return sourcePath;
    }
}

