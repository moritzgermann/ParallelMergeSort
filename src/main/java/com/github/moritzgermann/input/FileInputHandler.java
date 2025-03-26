package com.github.moritzgermann.input;

import java.nio.file.Path;
import java.util.Optional;

import static com.github.moritzgermann.input.InputUtils.scanner;
import static com.github.moritzgermann.input.InputUtils.userWantsToQuit;

/**
 * Handles user input for selecting a file to be sorted.
 * <p>
 * This class provides a method to interactively ask the user for a file path via the command line.
 * Users can enter a custom path, press ENTER to use the default file path, or type 'q'/'quit'/'exit' to cancel.
 * </p>
 */
public class FileInputHandler {
    public static Optional<FileLoadResult> handleFileInput() {
        FileLoadResult output = null;

        while (output == null) {
            System.out.println("\nPlease enter the path to the file you want to sort, press ENTER to use the default file, or type 'q' to quit:");
            String input = scanner.nextLine().trim();

            if (userWantsToQuit(input)) {
                return Optional.empty();
            }

            if (input.isEmpty()) {
                output = DefaultFileHandler.handleDefaultFile();
            } else {
                output = UserFileHandler.handleFileParsing(Path.of(input));
            }
        }

        return Optional.of(output);
    }
}
