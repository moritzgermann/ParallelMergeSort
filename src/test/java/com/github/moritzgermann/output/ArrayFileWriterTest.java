package com.github.moritzgermann.output;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ArrayFileWriterTest {

    private Path tempDir;
    private Path writtenFile;

    @AfterEach
    void cleanUp() throws IOException {
        if (writtenFile != null && Files.exists(writtenFile)) {
            Files.delete(writtenFile);
        }
        if (tempDir != null && Files.exists(tempDir)) {
            Files.delete(tempDir);
        }
    }

    @Test
    void testWriteArrayToFile_Success() throws IOException {
        tempDir = Files.createTempDirectory("test-dir");
        String fileName = "output.txt";
        int[] array = {1, 2, 3, 42, -5};

        Optional<Path> result = ArrayFileWriter.writeArrayToFile(array, tempDir.toString(), fileName);

        assertTrue(result.isPresent());
        writtenFile = result.get();
        assertTrue(Files.exists(writtenFile));

        List<String> lines = Files.readAllLines(writtenFile);
        assertEquals(array.length, lines.size());
        for (int i = 0; i < array.length; i++) {
            assertEquals(String.valueOf(array[i]), lines.get(i));
        }
    }

    @Test
    void testWriteArrayToFile_DirectoryDoesNotExist() {
        String nonExistentDir = "nonexistent-dir-" + System.currentTimeMillis();
        String fileName = "output.txt";
        int[] array = {1, 2, 3};

        Optional<Path> result = ArrayFileWriter.writeArrayToFile(array, nonExistentDir, fileName);

        assertTrue(result.isEmpty());
    }

    @Test
    void testWriteArrayToFile_EmptyArrayCreatesEmptyFile() throws IOException {
        tempDir = Files.createTempDirectory("test-empty");
        String fileName = "empty.txt";
        int[] array = {};

        Optional<Path> result = ArrayFileWriter.writeArrayToFile(array, tempDir.toString(), fileName);

        assertTrue(result.isPresent());
        writtenFile = result.get();
        List<String> lines = Files.readAllLines(writtenFile);
        assertTrue(lines.isEmpty());
    }
}
