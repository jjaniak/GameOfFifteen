package com.griddynamics;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


public class GameSolverTests {

    private GameSolver solver = new GameSolver();

    @Test
    public void checkIsGameSolvedMethod() {
        Board solvedBoard = new Board(GameSolver.SOLVED_CONFIGURATION);

        solvedBoard.moveUp();
        assertFalse(solver.isGameSolved(solvedBoard));

        solvedBoard.moveDown();
        assertTrue(solver.isGameSolved(solvedBoard));
    }

    @Test
    public void SolvableGameShouldBeSolvable() {
        int[][] conf = new int[][]{
                {12,  1, 10,  2},
                { 7, 11,  4, 14},
                { 5,  0,  9, 15},
                { 8, 13,  6,  3} };

        Board board1 = new Board(conf);
        assertTrue(solver.isGameSolvable(board1));

        conf = new int[][]{
                { 1,  2,  3,  4},
                { 5,  6,  0,  7},
                { 9, 10, 11,  8},
                {13, 14, 15, 12}};

        Board board2 = new Board(conf);
        assertTrue(solver.isGameSolvable(board2));
    }

    @Test
    public void NotSolvableGameShouldNotBeSolvable() {
        int[][] conf = new int[][]{
                { 1,  2,  3,  4},
                { 5,  6,  7,  8},
                { 9, 10, 11,  0},
                {13, 15, 14, 12} };

        Board board1 = new Board(conf);
        assertFalse(solver.isGameSolvable(board1));

        conf = new int[][]{
                {12,  1, 10,  2},
                { 7, 11,  4, 14},
                { 5,  9, 15,  8},
                { 0, 13,  6,  3} };

        Board board2 = new Board(conf);
        assertFalse(solver.isGameSolvable(board2));
    }

    @Test
    public void ShouldNotSolveWhenImpossible() {
        int[][] conf1 = new int[][] {
                { 1,  2,  3,  4},
                { 5,  6,  7,  8},
                { 9, 10, 11,  0},
                {13, 15, 14, 12} };

        Board board1 = new Board(conf1);
        ArrayList<Board.Movements> moves1 = solver.solve(board1);

        assertNull(moves1);

        int[][] conf2 = new int[][] {
                { 1,  2,  7,  3},
                { 5,  0,  6,  4},
                { 9, 10, 11,  8},
                {13, 15, 14, 12} };

        Board board2 = new Board(conf2);
        ArrayList<Board.Movements> moves2 = solver.solve(board2);

        assertNull(moves2);
    }

    @Test
    public void ShouldSolveGame() {
//  this is a simplified board configuration (not shuffled a lot) which my application manages to solve
        Board easyBoard = new Board(new int[][]{
                { 1,  0,  2,  4},
                { 5,  7,  3,  8},
                { 9,  6, 10, 11},
                {13, 14, 15, 12}});

        Board copyBoard = new Board(easyBoard.getConfiguration());
        ArrayList<Board.Movements> moves = solver.solve(copyBoard);

        assertNotEquals(null, moves);
    }
}