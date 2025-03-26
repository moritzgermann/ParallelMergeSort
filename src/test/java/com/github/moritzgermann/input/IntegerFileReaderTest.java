package com.github.moritzgermann.input;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class IntegerFileReaderTest {

    private File testFile;

    @BeforeEach
    void setUp() throws IOException {
        testFile = File.createTempFile("test-numbers", ".txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(testFile))) {
            writer.write("1\n");
            writer.write("42\n");
            writer.write("-5\n");
            writer.write("1000\n");
        }
    }

    @AfterEach
    void tearDown() {
        if (testFile.exists()) {
            assertTrue(testFile.delete(), "Failed to delete test file.");
        }
    }

    @Test
    void testReadNumbers_ValidFile() {
        int[] result = IntegerFileReader.readNumbers(testFile);
        assertArrayEquals(new int[]{1, 42, -5, 1000}, result);
    }

    @Test
    void testReadNumbers_InvalidContent() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(testFile))) {
            writer.write("1\n");
            writer.write("abc\n"); // Invalid line
            writer.write("3\n");
        }

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            IntegerFileReader.readNumbers(testFile);
        });

        assertTrue(exception.getMessage().contains("Parsing error"));
    }

    @Test
    void testReadNumbers_FileNotFound() {
        File nonExistent = new File("nonexistent-file.txt");

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            IntegerFileReader.readNumbers(nonExistent);
        });

        assertTrue(exception.getMessage().contains("Could not read file"));
    }
}
