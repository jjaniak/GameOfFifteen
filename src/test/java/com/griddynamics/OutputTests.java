package com.griddynamics;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.io.FileMatchers.*;


public class OutputTests {

    private static final String TEST_FILE_PATH = "src/test/resources/TestFile.txt";

    @AfterEach
    public void deleteFile() {
        try {
            Files.deleteIfExists(Paths.get(TEST_FILE_PATH));
        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        }
    }

    @Test
    public void fileShouldExistAndNotEmpty() {
        OutputProcessor printer = new FileOutputProcessor(TEST_FILE_PATH);
        printer.appendLine("Just some input to check if file is not empty");

        File file = new File(TEST_FILE_PATH);

        assertThat(file, anExistingFile());
        assertThat(file, aReadableFile());
        // find an assertion to check if file is not empty
    }
}