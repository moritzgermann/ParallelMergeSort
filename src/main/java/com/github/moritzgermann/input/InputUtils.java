package com.github.moritzgermann.input;

import java.util.Scanner;

/**
 * Utility class for handling user input and providing common input-related operations.
 * <p>
 * This class is designed to facilitate user interaction, such as detecting quitting commands and providing
 * a shared {@link Scanner} instance for input handling throughout the application.
 * </p>
 */
public class InputUtils {
    public static final Scanner scanner = new Scanner(System.in);

    /**
     * Checks if the given input represents a command to quit the application.
     * <p>
     * This method considers "q", "quit", and "exit" (case-insensitive) as valid quitting commands.
     * </p>
     *
     * @param input the input string to evaluate
     * @return {@code true} if the input is a quit command; {@code false} otherwise
     */
    public static boolean userWantsToQuit(String input) {
        return input.equalsIgnoreCase("q") || input.equalsIgnoreCase("quit") || input.equalsIgnoreCase("exit");
    }
}
