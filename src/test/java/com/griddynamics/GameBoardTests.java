package com.griddynamics;

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
    public void checkIsGameSolvedMethod() {
        solvedBoard.moveUp();
        assertFalse(solvedBoard.isGameSolved());

        solvedBoard.moveDown();
        assertTrue(solvedBoard.isGameSolved());

    }

    @Test
    public void moveRightCorrectly() {
        assertTrue(board.moveRight());
        assertEquals(board.getConfiguration()[1][2], 0);
        assertEquals(board.getConfiguration()[1][1], 7);
    }

    @Test
    public void moveRightIncorrectly() {
        board.moveRight();
        board.moveRight();

        assertFalse(board.moveRight());
        assertEquals(board.getConfiguration()[1][3], 0);
    }

    @Test
    public void moveLeftCorrectly() {
        assertTrue(board.moveLeft());
        assertEquals(board.getConfiguration()[1][0], 0);
        assertEquals(board.getConfiguration()[1][1], 2);
    }

    @Test
    public void moveLeftIncorrectly() {
        board.moveLeft();

        assertFalse(board.moveLeft());
        assertEquals(board.getConfiguration()[1][0], 0);
    }

    @Test
    public void moveUptCorrectly() {
        assertTrue(board.moveUp());
        assertEquals(board.getConfiguration()[0][1], 0);
        assertEquals(board.getConfiguration()[1][1], 15);
    }

    @Test
    public void moveUpIncorrectly() {
        board.moveUp();

        assertFalse(board.moveUp());
        assertEquals(board.getConfiguration()[0][1], 0);
    }

    @Test
    public void moveDownCorrectly() {
        assertTrue(board.moveDown());
        assertEquals(board.getConfiguration()[2][1], 0);
        assertEquals(board.getConfiguration()[1][1], 4);
    }

    @Test
    public void moveDownIncorrectly() {
        board.moveDown();
        board.moveDown();

        assertFalse(board.moveDown());
        assertEquals(board.getConfiguration()[3][1], 0);

    }
}
