package com.github.moritzgermann.input;

import java.io.File;
import java.nio.file.Path;
import java.util.Optional;

/**
 * The {@code UserFileHandler} class is a utility class for handling file operations
 * such as file parsing and validation.
 * <p>
 * This class provides methods for parsing files containing integer data, handling errors,
 * and validating file existence and format.
 * <p>
 * Example usage:
 * <pre>
 *     Path path = Paths.get("example.txt");
 *     FileLoadResult result = UserFileHandler.handleFileParsing(path);
 *     // Process the result...
 * </pre>
 */
public class UserFileHandler {

    /**
     * Parses the file at the given path, if it exists and is valid.
     * <p>
     * This method attempts to retrieve a file from the specified path and, if successful,
     * parses its content into a {@link FileLoadResult}. If the file is not valid or cannot
     * be parsed, {@code null} is returned.
     *
     * @param inputPath the path of the file to parse
     * @return a {@link FileLoadResult} containing the file's content as an array of
     * integers and its path, or {@code null} if parsing fails
     */
    public static FileLoadResult handleFileParsing(Path inputPath) {
        Optional<File> optionalFile = getFile(inputPath);
        return optionalFile.map(UserFileHandler::parseFile).orElse(null);
    }

    /**
     * Parses the specified file into a {@link FileLoadResult}.
     * <p>
     * The file content is read and converted into an array of integers. If an error occurs
     * during parsing (e.g., invalid file content), a detailed error message is printed
     * to the console, and {@code null} is returned.
     *
     * @param file the file to parse
     * @return a {@link FileLoadResult} containing the file's content as an array of integers
     * and its path, or {@code null} if parsing fails
     */
    public static FileLoadResult parseFile(File file) {
        try {
            System.out.println("Parsing file...");
            long start = System.currentTimeMillis();
            int[] numbers = IntegerFileReader.readNumbers(file);
            long end = System.currentTimeMillis();
            System.out.println("Parsing completed in " + (end - start) + " ms.\n");
            return new FileLoadResult(numbers, file.toPath());
        } catch (RuntimeException e) {
            System.out.println("An error occurred while reading the file: '" + file.getPath() + "'.");
            System.out.println("Error cause: '" + getRootCause(e) + "'.\n");
            return null;
        }
    }

    /**
     * Validates the provided path and retrieves the corresponding file.
     * <p>
     * If the file exists and is valid, an {@link Optional} containing the file is returned.
     * Otherwise, an empty {@link Optional} is returned, and a message is printed to the console.
     *
     * @param path the path of the file to validate
     * @return an {@link Optional} containing a valid {@link File}, or an empty {@link Optional}
     * if the file does not exist or is invalid
     */
    private static Optional<File> getFile(Path path) {
        File file = path.toFile();
        if (!file.exists() || !file.isFile()) {
            System.out.println("File not found or not a valid file: " + file.getPath());
            return Optional.empty();
        }
        return Optional.of(file);
    }

    /**
     * Retrieves the root cause of an exception.
     * <p>
     * This method traverses the exception's cause chain to identify and return the message
     * of the root cause.
     *
     * @param e the exception to analyze
     * @return the message of the root cause, or {@code null} if the exception has no message
     */
    private static String getRootCause(Exception e) {
        Throwable cause = e;
        while (cause.getCause() != null) {
            cause = cause.getCause();
        }
        return cause.getMessage();
    }
}
