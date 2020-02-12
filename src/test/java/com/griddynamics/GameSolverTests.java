package com.griddynamics;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static com.griddynamics.Board.Movements;
import static org.junit.jupiter.api.Assertions.*;


public class GameSolverTests {

    private GameSolver solver = new GameSolver();
    private final String folder = "gameSolverTestsConf";
    private final Board unsolvableBoard1 = Board.readFromFile(getResourcePath("unsolvableConf1.txt"));
    private final Board unsolvableBoard2 = Board.readFromFile(getResourcePath("unsolvableConf2.txt"));
    private final Board solvableBoard = Board.readFromFile(getResourcePath("solvableConf3.txt"));

    private String getResourcePath(String filename) {
        return GameSolverTests.class.getClassLoader().getResource(folder + "/" + filename).getPath();
    }


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
        Board board1 = Board.readFromFile(getResourcePath("solvableConf1.txt"));
        assertTrue(solver.isGameSolvable(board1));

        Board board2 = Board.readFromFile(getResourcePath("solvableConf2.txt"));
        assertTrue(solver.isGameSolvable(board2));
    }

    @Test
    public void notSolvableGameShouldNotBeSolvable() {
        assertFalse(solver.isGameSolvable(unsolvableBoard1));

        assertFalse(solver.isGameSolvable(unsolvableBoard2));
    }

    @Test
    public void shouldNotSolveWhenImpossible() {
        ArrayList<Movements> moves1 = solver.solve(unsolvableBoard1);
        assertNull(moves1);

        ArrayList<Movements> moves2 = solver.solve(unsolvableBoard2);
        assertNull(moves2);
    }

    @Test
    public void shouldSolveGame() {
        // this is a simplified board configuration (not extremely shuffled) which the application can solve for sure
        ArrayList<Movements> moves = solver.solve(solvableBoard);

        assertNotEquals(null, moves);
        assertTrue(solver.isGameSolved(solvableBoard));
    }

    @Test
    public void returnedMovesShouldBeValid() {
        Board copyBoard = new Board(solvableBoard.getConfiguration());
        ArrayList<Movements> moves = solver.solve(copyBoard);

        for (Movements m : moves) {
            boolean isValid = solvableBoard.move(m);
            assertTrue(isValid);
        }
        assertTrue(solver.isGameSolved(solvableBoard));
    }
}