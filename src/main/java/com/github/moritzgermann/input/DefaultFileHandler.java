package com.github.moritzgermann.input;

import com.github.moritzgermann.constants.ApplicationConstants;
import com.github.moritzgermann.output.ArrayFileWriter;

import java.io.File;
import java.nio.file.*;
import java.util.Optional;

import static com.github.moritzgermann.input.InputUtils.scanner;

/**
 * Handles the creation, deletion, and retrieval of a default file used for sorting.
 * If a default file exists, provides the option to reuse it. Otherwise, creates a new one
 * based on user input for the size of randomly generated numbers.
 */
public class DefaultFileHandler {

    /**
     * Handles the default file logic. If a default file exists, it prompts the user to use it.
     * Otherwise, generates a new file based on user input.
     *
     * @return a {@link FileLoadResult} containing the loaded numbers and file path, or null if canceled by the user.
     */
    public static FileLoadResult handleDefaultFile() {
        Path defaultFilePath = Paths
                .get(ApplicationConstants.DEFAULT_FILE_DIRECTORY_PATH)
                .resolve(ApplicationConstants.DEFAULT_FILE_NAME);
        File defaultFile = defaultFilePath.toFile();

        if (defaultFile.exists()) {
            if (askToUseExistingDefaultFile()) {
                FileLoadResult result = UserFileHandler.parseFile(defaultFile);
                if (result == null) deleteFile(defaultFilePath);
                else return result;
            }
        }

        Optional<Integer> fileSize = askUserForFileSize();
        return fileSize.map(size -> generateAndWriteDefaultFile(size, defaultFilePath)).orElse(null);
    }

    /**
     * Prompts the user to decide whether to use the existing default file.
     *
     * @return true if the user chooses to use the file, false otherwise.
     */
    private static boolean askToUseExistingDefaultFile() {
        System.out.println("A default file was found.");
        System.out.print("Do you want to use it? (y/n): ");
        return scanner.nextLine().trim().equalsIgnoreCase("y");
    }

    /**
     * Generates a new file with random numbers of specified size and writes it to disk.
     *
     * @param count the number of integers to generate.
     * @param path  the path to save the generated file.
     * @return a {@link FileLoadResult} containing the generated numbers and the file path.
     */
    private static FileLoadResult generateAndWriteDefaultFile(int count, Path path) {
        System.out.println("\nCreating default file with random numbers...");
        long start = System.currentTimeMillis();

        int[] numbers = generateRandomNumbers(count);
        ArrayFileWriter.writeArrayToFile(numbers,
                ApplicationConstants.DEFAULT_FILE_DIRECTORY_PATH,
                ApplicationConstants.DEFAULT_FILE_NAME);

        long end = System.currentTimeMillis();
        System.out.printf("Default file created successfully in %,d ms.%n%n", (end - start));

        return new FileLoadResult(numbers, path);
    }

    /**
     * Generates an array of random integers within the range [-100,000, 100,000].
     *
     * @param count the number of integers to generate.
     * @return an array of randomly generated integers.
     */
    private static int[] generateRandomNumbers(int count) {
        int[] numbers = new int[count];
        java.util.Random random = new java.util.Random();
        for (int i = 0; i < count; i++) {
            numbers[i] = random.nextInt(200001) - 100000;
        }
        return numbers;
    }

    /**
     * Prompts the user for the number of elements to generate and validates the input.
     *
     * @return an {@link Optional} containing the valid input or empty if the user quits.
     */
    private static Optional<Integer> askUserForFileSize() {
        Integer output = null;
        while (output == null) {
            System.out.printf("Please enter the number of elements to sort (1 - %,d), or type 'q' to quit:%n", ApplicationConstants.MAX_ELEMENTS);
            String input = scanner.nextLine().trim();
            if (InputUtils.userWantsToQuit(input)) return Optional.empty();

            try {
                int parsed = Integer.parseInt(input);
                if (parsed <= 0 || parsed > ApplicationConstants.MAX_ELEMENTS) {
                    System.out.printf("Please enter a positive integer less than %,d.%n", ApplicationConstants.MAX_ELEMENTS);
                } else {
                    output = parsed;
                }
            } catch (NumberFormatException e) {
                System.out.printf("'%s' is not a valid integer.%n", input);
            }
        }
        return Optional.of(output);
    }

    /**
     * Deletes the specified file quietly without throwing an exception.
     *
     * @param path the path of the file to delete.
     */
    private static void deleteFile(Path path) {
        try {
            Files.delete(path);
        } catch (Exception ignored) {
        }
    }
}
