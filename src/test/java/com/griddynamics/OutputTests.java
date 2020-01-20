package com.griddynamics;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.io.FileMatchers.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;


public class OutputTests {

    private static final String TEST_FILE_PATH = "src/test/resources/TestFile.txt";

//   test just one class or run main inside the tests? Run main in order to generate output file."

    @AfterEach
    public void deleteFile() throws IOException {
        try {
            System.out.println("deleting file");
            Files.deleteIfExists(Paths.get(TEST_FILE_PATH));
            System.out.println("file deleted");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.format("IOException: %s%n", e);
        }
    }

    @Test
    public void fileShouldExistAndNotEmpty() {
        OutputProcessor printer = new OutputProcessor();
        int[][] conf = new int[][]{
                { 1,  2,  3,  4},
                { 5,  7,  0,  8},
                { 9,  6, 10, 11},
                {13, 14, 15, 12}};

        Board easyBoard = new Board(conf);
        printer.addToFile(TEST_FILE_PATH, easyBoard.toString());

        File file = new File(TEST_FILE_PATH);

        assertThat(file, anExistingFile());
        assertThat(file, aReadableFile());
//        find an assertion to check if file is not empty
    }

    @Test
    public void outputShouldContainInitialConfiguration() {
        OutputProcessor printer = new OutputProcessor();
        int[][] conf = new int[][]{
                { 1,  2,  3,  4},
                { 5,  7,  0,  8},
                { 9,  6, 10, 11},
                {13, 14, 15, 12}};

        Board easyBoard = new Board(conf);
        printer.addToFile(TEST_FILE_PATH, "Initial configuration: \n\n" + easyBoard);

        File file = new File(TEST_FILE_PATH);

    }

    @Test
    public void outputShouldContainCorrectFinalConfiguration() {
    }

    @Test
    public void checkIfMovesNumberPresentAndValid() {
    }


    // ?Should be included in GameSolving, Output or GameLauncher tests?
    @Test
    public void shouldProperlyPrintNotSolvable() {
//     print -1 to file for not solvable
    }
}