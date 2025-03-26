package com.github.moritzgermann.output;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

/**
 * Utility class for writing integer arrays to text files.
 * <p>
 * Provides functionality to write the contents of an integer array to a file,
 * with each element written to a new line. The file can be saved to a specified
 * directory with a given name.
 * </p>
 */
public class ArrayFileWriter {

    /**
     * Writes an integer array to a file at the specified location.
     *
     * @param outputArray     the array of integers to write
     * @param targetDirectory the directory where the file should be created
     * @param fileName        the name of the file to create
     * @return an {@link Optional} containing the {@link Path} of the written file,
     * or {@link Optional#empty()} if the writing process failed
     */
    public static Optional<Path> writeArrayToFile(int[] outputArray, String targetDirectory, String fileName) {
        Path targetDirectoryPath = Path.of(targetDirectory);
        Path filePath = targetDirectoryPath.resolve(fileName);
        try {
            writeToFile(filePath, outputArray);
        } catch (IOException e) {
            return Optional.empty();
        }
        return Optional.of(filePath);
    }

    /**
     * Writes the given array of integers to a specified file.
     * <p>
     * Each element of the array is written to a new line in the file. If the file already exists, its content
     * will be replaced. If the parent directories of the file do not exist, they must be created beforehand.
     * </p>
     *
     * @param filePath    the path of the file where the array should be written
     * @param outputArray the array of integers to write to the file
     * @throws IOException if an I/O error occurs while writing to the file
     */
    private static void writeToFile(Path filePath, int[] outputArray) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(filePath)) {
            for (int num : outputArray) {
                writer.write(String.valueOf(num));
                writer.newLine();
            }
        }
    }
}
