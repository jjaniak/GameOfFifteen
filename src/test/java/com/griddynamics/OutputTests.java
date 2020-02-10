package com.griddynamics;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.io.FileMatchers.aReadableFile;
import static org.hamcrest.io.FileMatchers.anExistingFile;


public class OutputTests {

    private static final String TEST_FILE_PATH = "src/test/resources/TestFile.txt";

    @AfterEach
    public void deleteFile() throws IOException {
        Files.deleteIfExists(Paths.get(TEST_FILE_PATH));
    }

    @Test
    public void fileShouldExistAndNotEmpty() throws IOException {
        OutputProcessor printer = new FileOutputProcessor(TEST_FILE_PATH);
        printer.append("Just some input to check if file is not empty");

        File file = new File(TEST_FILE_PATH);
        assertThat("File does not exist",  file, anExistingFile());
        assertThat("File is not readable", file, aReadableFile());

        String content = Files.readString(Paths.get(TEST_FILE_PATH));

        assertThat("File is unexpectedly empty", (null != content) && (content.length() > 0));
    }
}