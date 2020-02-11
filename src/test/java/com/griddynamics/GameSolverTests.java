package com.griddynamics;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static com.griddynamics.Board.Movements;
import static org.junit.jupiter.api.Assertions.*;


public class GameSolverTests {

    private GameSolver solver = new GameSolver();

    @Test
    public void checkIsGameSolvedMethod() {
        Board solvedBoard = new Board(GameSolver.SOLVED_CONFIGURATION);

        solvedBoard.move(Movements.UP);
        assertFalse(solver.isGameSolved(solvedBoard));

        solvedBoard.move(Movements.DOWN);
        assertTrue(solver.isGameSolved(solvedBoard));
    }

    @Test
    public void solvableGameShouldBeSolvable() {
        int[][] conf = new int[][]{
                {12, 1, 10, 2},
                {7, 11, 4, 14},
                {5, 0, 9, 15},
                {8, 13, 6, 3}};

        Board board1 = new Board(conf);
        assertTrue(solver.isGameSolvable(board1));

        conf = new int[][]{
                {1, 2, 3, 4},
                {5, 6, 0, 7},
                {9, 10, 11, 8},
                {13, 14, 15, 12}};

        Board board2 = new Board(conf);
        assertTrue(solver.isGameSolvable(board2));
    }

    @Test
    public void notSolvableGameShouldNotBeSolvable() {
        int[][] conf = new int[][]{
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 0},
                {13, 15, 14, 12}};

        Board board1 = new Board(conf);
        assertFalse(solver.isGameSolvable(board1));

        conf = new int[][]{
                {12, 1, 10, 2},
                {7, 11, 4, 14},
                {5, 9, 15, 8},
                {0, 13, 6, 3}};

        Board board2 = new Board(conf);
        assertFalse(solver.isGameSolvable(board2));
    }

    @Test
    public void shouldNotSolveWhenImpossible() {
        int[][] conf1 = new int[][]{
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 0},
                {13, 15, 14, 12}};

        Board board1 = new Board(conf1);
        ArrayList<Movements> moves1 = solver.solve(board1);

        assertNull(moves1);

        int[][] conf2 = new int[][]{
                {1, 2, 7, 3},
                {5, 0, 6, 4},
                {9, 10, 11, 8},
                {13, 15, 14, 12}};

        Board board2 = new Board(conf2);
        ArrayList<Movements> moves2 = solver.solve(board2);

        assertNull(moves2);
    }

    @Test
    public void shouldSolveGame() {
        // this is a simplified board configuration (not extremely shuffled) which the application can solve for sure
        Board board = new Board(new int[][]{
                {1, 0, 2, 4},
                {5, 7, 3, 8},
                {9, 6, 10, 11},
                {13, 14, 15, 12}});

        ArrayList<Movements> moves = solver.solve(board);

        assertNotEquals(null, moves);
        assertTrue(solver.isGameSolved(board));
    }

    @Test
    public void returnedMovesShouldBeValid() {
        Board easyBoard = new Board(new int[][]{
                {1, 0, 2, 4},
                {5, 7, 3, 8},
                {9, 6, 10, 11},
                {13, 14, 15, 12}});

        Board copyBoard = new Board(easyBoard.getConfiguration());
        ArrayList<Movements> moves = solver.solve(copyBoard);

        for (Movements m : moves) {
            boolean isValid = easyBoard.move(m);
            assertTrue(isValid);
        }
        assertTrue(solver.isGameSolved(easyBoard));
    }
}