package com.griddynamics;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FileReadingTests {

    private final String folder = "fileReadingTestsConf";
    private String getResourcePath(String filename) {
        return FileReadingTests.class.getClassLoader().getResource(folder + "/" + filename).getPath();
    }

    @Test
    public void shouldReadCorrectConfiguration() {
        Board board = new Board(new int[][]{
                { 1,  2,  3,  4},
                { 5,  6,  7,  8},
                { 9, 10, 11,  0},
                {13, 15, 14, 12} });

        Board boardReadFromFile = Board.readFromFile(getResourcePath("unsolvableConf.txt"));

        assertEquals(board.toString(), boardReadFromFile.toString());
    }

    @Test
    public void shouldThrowErrorWhenMissingLine() {
        Exception e = assertThrows(IllegalArgumentException.class, ()-> Board.readFromFile(getResourcePath("missingLinesConf.txt")));

        assertEquals("missing lines", e.getMessage());
    }

    @Test
    public void shouldThrowErrorWhenMissingNumber() {
        Exception e = assertThrows(IllegalArgumentException.class, ()-> Board.readFromFile(getResourcePath("missingNumbersConf.txt")));

        assertEquals("there should be 4 numbers in line 3", e.getMessage());
    }

    @Test
    public void shouldThrowErrorWhenNotNumber() {
        Exception ex1 = assertThrows(IllegalArgumentException.class, ()-> Board.readFromFile(getResourcePath("notNumberConf1.txt")));

        assertEquals("invalid number in line 3: 1,", ex1.getMessage());

        Exception ex2 = assertThrows(IllegalArgumentException.class, ()-> Board.readFromFile(getResourcePath("notNumberConf2.txt")));

        assertEquals("invalid number in line 4: %", ex2.getMessage());
    }

    @Test
    public void shouldThrowErrorWhenInvalidNumbers() {
        Exception e = assertThrows(IllegalArgumentException.class, ()-> Board.readFromFile(getResourcePath("invalidNumbersConf.txt")));
        assertEquals("The file must contain all numbers from 0 to 15 inclusive", e.getMessage());
    }
}