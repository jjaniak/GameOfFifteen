package com.griddynamics;

import java.util.ArrayList;
import java.util.Arrays;

public class GameSolver {

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

//      In order to calculate the number of inversions:
//          go through each element
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
//          then compare the current element with all elements following it
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

//      According to  https://www.cs.bham.ac.uk/~mdr/teaching/modules04/java2/TilesSolvability.html
//      If empty tile is in 1st or 3rd row (index 0 or 2) and the number of inversions is odd  ---> then it is solvable
//      If empty tile is in 2nd or 4th row (index 1 or 3) and the number of inversions is even ---> then it is solvable
        Board.Position emptyTile = board.getEmptyTile();
        return (emptyTile.row % 2 == 0 && numberOfInversions % 2 == 1) || (emptyTile.row % 2 == 1 && numberOfInversions % 2 == 0);
    }

    public ArrayList<Board.Movements> solve(Board board) {
        if (!isGameSolvable(board)){
            System.out.println("The game is not solvable");
            return null;
        }

        ArrayList<Board.Movements> moves = new ArrayList<>();

        if (solveRecursive(moves, board))
            return moves;
        else
            return null;
    }

    private boolean solveRecursive(ArrayList<Board.Movements> moves, Board board) {
        if (isGameSolved(board))
            return true;

        Board.Movements last = null;
        if (moves.size() > 0)
            last = moves.get(moves.size() - 1);

        if (moves.size() > 9)
            return false;

        for (Board.Movements m : Board.Movements.values()) {
            switch (m) {
                case UP:
                    if (last == Board.Movements.DOWN || !board.move(Board.Movements.UP))
                        continue;
                    moves.add(m);
                    if (solveRecursive(moves, board))
                        return true;
                    board.move(Board.Movements.DOWN); // undo the move
                    moves.remove(moves.size() - 1);
                    break;
                case DOWN:
                    if (last == Board.Movements.UP || !board.move(Board.Movements.DOWN))
                        continue;
                    moves.add(m);
                    if (solveRecursive(moves, board))
                        return true;
                    board.move(Board.Movements.UP); // undo the move
                    moves.remove(moves.size() - 1);
                    break;
                case LEFT:
                    if (last == Board.Movements.RIGHT || !board.move(Board.Movements.LEFT))
                        continue;
                    moves.add(m);
                    if (solveRecursive(moves, board))
                        return true;
                    board.move(Board.Movements.RIGHT); // undo the move
                    moves.remove(moves.size() - 1);
                    break;
                case RIGHT:
                    if (last == Board.Movements.LEFT || !board.move(Board.Movements.RIGHT))
                        continue;
                    moves.add(m);
                    if (solveRecursive(moves, board))
                        return true;
                    board.move(Board.Movements.LEFT); // undo the move
                    moves.remove(moves.size() - 1);
                    break;
            }
        }
        return false;
    }
}