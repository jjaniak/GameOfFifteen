package com.griddynamics;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FileReadingTests {

    @Test
    public void shouldReadCorrectConfiguration() {
        Board board = new Board(new int[][]{
                { 1,  2,  3,  4},
                { 5,  6,  7,  8},
                { 9, 10, 11,  0},
                {13, 15, 14, 12} });

        String path = FileReadingTests.class.getClassLoader().getResource("unsolvableConf.txt").getPath();
        Board boardReadFromFile = Board.readFromFile(path);

        assertEquals(board.toString(), boardReadFromFile.toString());
    }

    @Test
    public void shouldThrowErrorWhenMissingLine() {
        String path = FileReadingTests.class.getClassLoader().getResource("fileReadingTestsConf/missingLinesConf.txt").getPath();

        Exception e = assertThrows(IllegalArgumentException.class, ()-> Board.readFromFile(path));
        assertEquals("missing lines", e.getMessage());
    }

    @Test
    public void shouldThrowErrorWhenMissingNumber() {
        String path = FileReadingTests.class.getClassLoader().getResource("fileReadingTestsConf/missingNumbersConf.txt").getPath();

        Exception e = assertThrows(IllegalArgumentException.class, ()-> Board.readFromFile(path));
        assertEquals("there should be 4 numbers in line 3", e.getMessage());
    }

    @Test
    public void shouldThrowErrorWhenNotNumber() {
        String path1 = FileReadingTests.class.getClassLoader().getResource("fileReadingTestsConf/notNumberConf1.txt").getPath();

        Exception ex1 = assertThrows(IllegalArgumentException.class, ()-> Board.readFromFile(path1));
        assertEquals("invalid number in line 3: 1,", ex1.getMessage());

        String path2 = FileReadingTests.class.getClassLoader().getResource("fileReadingTestsConf/notNumberConf2.txt").getPath();

        Exception ex2 = assertThrows(IllegalArgumentException.class, ()-> Board.readFromFile(path2));
        assertEquals("invalid number in line 4: %", ex2.getMessage());
    }

    @Test
    public void shouldThrowErrorWhenInvalidNumbers() {
        String path = FileReadingTests.class.getClassLoader().getResource("fileReadingTestsConf/invalidNumbersConf.txt").getPath();

        Exception e = assertThrows(IllegalArgumentException.class, ()-> Board.readFromFile(path));
        assertEquals("The file must contain all numbers from 0 to 15 inclusive", e.getMessage());
    }
}