package com.griddynamics;

import java.util.ArrayList;
import java.util.Arrays;

import static com.griddynamics.Board.Movements;

class GameSolver {

    static final int[][] SOLVED_CONFIGURATION = {
            { 1,  2,  3,  4},
            { 5,  6,  7,  8},
            { 9, 10, 11, 12},
            {13, 14, 15,  0}};

    boolean isGameSolved(Board board) {
        return Arrays.deepEquals(board.getConfiguration(), GameSolver.SOLVED_CONFIGURATION);
    }

    boolean isGameSolvable(Board board) {
        int numberOfInversions = 0;

        // In order to calculate the number of inversions:
        // go through each element
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
        // then compare the current element with all elements following it
                int l = j + 1;
                for (int k = i; k < 4; k++) {
                    for (; l < 4; l++) {
                        if ((board.getConfiguration()[i][j] > board.getConfiguration()[k][l]) && (board.getConfiguration()[k][l] != 0)) {
                            numberOfInversions++;
                        }
                    }
                    l = 0;
                }
            }
        }

        // According to  https://www.cs.bham.ac.uk/~mdr/teaching/modules04/java2/TilesSolvability.html
        // If empty tile is in 1st or 3rd row (index 0 or 2) and the number of inversions is odd  ---> then it is solvable
        // If empty tile is in 2nd or 4th row (index 1 or 3) and the number of inversions is even ---> then it is solvable
        Board.Position emptyTile = board.getEmptyTile();
        return (emptyTile.row % 2 == 0 && numberOfInversions % 2 == 1) || (emptyTile.row % 2 == 1 && numberOfInversions % 2 == 0);
    }

    ArrayList<Movements> solve(Board board) {
        if (!isGameSolvable(board)) {
            return null;
        }

        ArrayList<Movements> moves = new ArrayList<>();

        if (solveRecursive(moves, board)) {
            return moves;
        } else {
            return null;
        }
    }

    private boolean solveRecursive(ArrayList<Movements> moves, Board board) {

        if (isGameSolved(board)) {
            return true;
        }

        Movements last = null;
        if (moves.size() > 0) {
            last = moves.get(moves.size() - 1);
        }

        // todo improve solveRecursive method to be able to solve all solvable configurations,
        //  once done the number of movements could be increased to 80 (or maybe totally removed)
        // Number of movements is limited to prevent the recursion from going too deep. Otherwise, it could end with StackOverflowError.
        // The drawback is that it can only solve configurations that are not too complicated.
        if (moves.size() >= 25) {
            return false;
        }

        for (Movements m : Movements.values()) {
            Movements opposite = getOppositeMovement(m);
            if (last == opposite || !board.move(m)) {
                continue;
            }

            moves.add(m);
            if (solveRecursive(moves, board)) {
                return true;
            }

        //  Did not work - undo the movement and remove it from the list
            board.move(opposite);
            moves.remove(moves.size() - 1);
        }
        return false;
    }

    private Movements getOppositeMovement(Movements move) {
        switch (move) {
            case UP:
                return Movements.DOWN;
            case DOWN:
                return Movements.UP;
            case LEFT:
                return Movements.RIGHT;
            case RIGHT:
                return Movements.LEFT;
        }
        return null; // Should never happen
    }
}