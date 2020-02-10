package com.griddynamics;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.griddynamics.Board.Movements.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameBoardTests {

    private int[][] conf;
    private Board board;


    @BeforeEach
    public void setup(){
         conf = new int[][]{
                {11, 15, 10,  8},
                { 2,  0,  7,  5},
                { 9,  4,  1, 12},
                { 3, 14, 13,  6} };
         board = new Board(conf);
    }


    @Test
    public void checkShufflingConfigurationWorks() {
        assertTrue(board.isBoardIdentical(conf));

        board.shuffleBoardConfiguration();
        assertFalse(board.isBoardIdentical(conf));
    }

    @Test
    public void checkShufflingDelicatelyWorks() {
        assertTrue(board.isBoardIdentical(conf));

        board.delicatelyShuffle();
        assertFalse(board.isBoardIdentical(conf));
    }

    @Test
    public void moveRightCorrectly() {
        assertTrue(board.move(RIGHT));
        assertEquals(board.getTileValue(1, 2), 0);
        assertEquals(board.getTileValue(1, 1), 7);

        assertTrue(board.move(RIGHT));
        assertEquals(board.getTileValue(1, 3), 0);
        assertEquals(board.getTileValue(1, 2), 5);
    }

    @Test
    public void moveRightIncorrectly() {
        board.move(RIGHT);
        board.move(RIGHT);

        assertFalse(board.move(RIGHT));
        assertEquals(board.getTileValue(1, 3), 0);
    }

    @Test
    public void moveLeftCorrectly() {
        assertTrue(board.move(LEFT));
        assertEquals(board.getTileValue(1, 0), 0);
        assertEquals(board.getTileValue(1, 1), 2);
    }

    @Test
    public void moveLeftIncorrectly() {
        board.move(LEFT);

        assertFalse(board.move(LEFT));
        assertEquals(board.getTileValue(1, 0), 0);
    }

    @Test
    public void moveUptCorrectly() {
        assertTrue(board.move(UP));
        assertEquals(board.getTileValue(0, 1), 0);
        assertEquals(board.getTileValue(1, 1), 15);
    }

    @Test
    public void moveUpIncorrectly() {
        board.move(UP);

        assertFalse(board.move(UP));
        assertEquals(board.getTileValue(0, 1), 0);
    }

    @Test
    public void moveDownCorrectly() {
        assertTrue(board.move(DOWN));
        assertEquals(board.getTileValue(2, 1), 0);
        assertEquals(board.getTileValue(1, 1), 4);

        assertTrue(board.move(DOWN));
        assertEquals(board.getTileValue(3, 1), 0);
        assertEquals(board.getTileValue(2, 1), 14);
    }

    @Test
    public void moveDownIncorrectly() {
        board.move(DOWN);
        board.move(DOWN);

        assertFalse(board.move(DOWN));
        assertEquals(board.getTileValue(3, 1), 0);

    }

    @Test
    public void getTileValueGivenCorrectCoordinates() {
        assertEquals(board.getTileValue(3, 1), 14);
        assertEquals(board.getTileValue(0, 3), 8);
    }

    @Test
    @DisplayName("Test checks if -1 is returned when given invalid position coordinates")
    public void getTileValueGivenIncorrectCoordinates() {
        assertEquals(-1, board.getTileValue(0, -1));
        assertEquals(-1, board.getTileValue(16, 16));
    }

    @Test
    public void getTilePositionGivenCorrectValue() {
        assertEquals(board.getTilePosition(7).row, 1);
        assertEquals(board.getTilePosition(7).column, 2);
    }

    @Test
    public void getTilePositionGivenIncorrectValue() {
        assertNull(board.getTilePosition(-1));
        assertNull(board.getTilePosition(16));
    }
}
