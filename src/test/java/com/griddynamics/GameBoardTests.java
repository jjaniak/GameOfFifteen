package com.griddynamics;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GameBoardTests {

    private final Board solvedBoard = new Board(Board.SOLVED_CONFIGURATION);

    private final int[][] mixedConfiguration = new int[][]{
            {11, 15, 10,  8},
            { 2,  0,  7,  5},
            { 9,  4,  1, 12},
            { 3, 14, 13,  6} };
    private Board board = new Board(mixedConfiguration);


    @Test
    public void checkShufflingConfigurationWorks() {
        assertTrue(board.isBoardIdentical(mixedConfiguration));

        board.shuffleBoardConfiguration();
        assertFalse(board.isBoardIdentical(mixedConfiguration));
    }

    @Test
    public void moveRightCorrectly() {
        assertTrue(board.moveRight());
        assertEquals(board.getTileValue(1, 2), 0);
        assertEquals(board.getTileValue(1, 1), 7);

        assertTrue(board.moveRight());
        assertEquals(board.getTileValue(1, 3), 0);
        assertEquals(board.getTileValue(1, 2), 5);
    }

    @Test
    public void moveRightIncorrectly() {
        board.moveRight();
        board.moveRight();

        assertFalse(board.moveRight());
        assertEquals(board.getTileValue(1, 3), 0);
    }

    @Test
    public void moveLeftCorrectly() {
        assertTrue(board.moveLeft());
        assertEquals(board.getTileValue(1, 0), 0);
        assertEquals(board.getTileValue(1, 1), 2);
    }

    @Test
    public void moveLeftIncorrectly() {
        board.moveLeft();

        assertFalse(board.moveLeft());
        assertEquals(board.getTileValue(1, 0), 0);
    }

    @Test
    public void moveUptCorrectly() {
        assertTrue(board.moveUp());
        assertEquals(board.getTileValue(0, 1), 0);
        assertEquals(board.getTileValue(1, 1), 15);
    }

    @Test
    public void moveUpIncorrectly() {
        board.moveUp();

        assertFalse(board.moveUp());
        assertEquals(board.getTileValue(0, 1), 0);
    }

    @Test
    public void moveDownCorrectly() {
        assertTrue(board.moveDown());
        assertEquals(board.getTileValue(2, 1), 0);
        assertEquals(board.getTileValue(1, 1), 4);

        assertTrue(board.moveDown());
        assertEquals(board.getTileValue(3, 1), 0);
        assertEquals(board.getTileValue(2, 1), 14);
    }

    @Test
    public void moveDownIncorrectly() {
        board.moveDown();
        board.moveDown();

        assertFalse(board.moveDown());
        assertEquals(board.getTileValue(3, 1), 0);

    }

    @Test
    public void getTileValueGivenCorrectCoordinates() {
        assertEquals(board.getTileValue(3, 1), 14);
        assertEquals(board.getTileValue(0, 3), 8);
    }

    @Test
    @DisplayName("Test checks if IllegalArgumentException is thrown when given invalid position coordinates")
    public void getTileValueGivenIncorrectCoordinates() {
        Exception exception1 = assertThrows(IllegalArgumentException.class, ()-> board.getTileValue(0, -1));
        System.out.println(exception1.getMessage());
        assertEquals("-1 is not a valid value", exception1.getMessage());

        Exception exception2 = assertThrows(IllegalArgumentException.class, ()-> board.getTileValue(16, 16));
        System.out.println(exception2.getMessage());
        assertEquals("16 and 16 are not valid values", exception2.getMessage());
    }

    @Test
    public void getTilePositionGivenCorrectValue() {
        assertEquals(board.getTilePosition(7).row, 1);
        assertEquals(board.getTilePosition(7).column, 2);
    }

    @Test
    @DisplayName("Test checks if IllegalArgumentException is thrown when given invalid value")
    public void getTilePositionGivenIncorrectValue() {
        assertThrows(IllegalArgumentException.class, ()-> board.getTilePosition(-1));
        assertThrows(IllegalArgumentException.class, ()-> board.getTilePosition(16));
    }
}
