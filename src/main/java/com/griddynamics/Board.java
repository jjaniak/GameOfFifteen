package com.griddynamics;

import java.util.Arrays;
import java.util.Random;

public class Board {

    public static class Position {
        int row;
        int column;

        Position(int row, int column) {
            this.row = row;
            this.column = column;
        }
    }

    public enum Movements {
        UP,
        DOWN,
        LEFT,
        RIGHT;

        @Override
        public String toString() {
            switch (this) {
                case UP:
                    return "up";
                case DOWN:
                    return "down";
                case LEFT:
                    return "left";
                case RIGHT:
                    return "right";
                default:
                    return "invalid content";
            }
        }
    }

    static final int[][] SOLVED_CONFIGURATION = {
            { 1,  2,  3,  4},
            { 5,  6,  7,  8},
            { 9, 10, 11, 12},
            {13, 14, 15,  0}};

    private int[][] configuration;
    private Position emptyTile;


    public Board(int[][] passedConfiguration) {
        this.configuration = new int[4][];
        for (int i = 0; i < 4; i++) {
            this.configuration[i] = Arrays.copyOf(passedConfiguration[i], passedConfiguration[i].length);
        }
        updateEmptyTilePosition();
    }

    public int[][] getConfiguration() {
        return configuration;
    }

    public void setConfiguration(int[][] configuration) {
        this.configuration = configuration;
        updateEmptyTilePosition();
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        String newLine = System.getProperty("line.separator");

        for (int[] row : this.configuration) {
            if (row != null) {
                result.append("[ ");
                for (int col : row) {
                    result.append(col).append(" ");
                }
                result.append("]").append(newLine);
            }
        }
        result.append(newLine);
        return result.toString();
    }

     void shuffleBoardConfiguration() {
        Random random = new Random();
        for (int i = configuration.length - 1; i > 0; i--) {
            for (int j = configuration[i].length - 1; j > 0; j--) {
                int m = random.nextInt(i + 1);
                int n = random.nextInt(j + 1);

                int temp = configuration[i][j];
                configuration[i][j] = configuration[m][n];
                configuration[m][n] = temp;
            }
        }
    }

//    Movement in a direction means that empty tile (Zero) is moving in that direction:

    boolean moveRight() {
        if (emptyTile.column == 3) {
            System.out.println("It's impossible to move to the right");
            return false;
        }

        int valueOnRight = configuration[emptyTile.row][emptyTile.column + 1];
        configuration[emptyTile.row][emptyTile.column + 1] = 0;
        configuration[emptyTile.row][emptyTile.column] = valueOnRight;
        emptyTile.column++;
        System.out.println("0 moved to the right");
        return true;
    }

    boolean moveLeft() {
        if (emptyTile.column == 0) {
            System.out.println("It's impossible to move to the left");
            return false;
        }

        int valueOnLeft = configuration[emptyTile.row][emptyTile.column - 1];
        configuration[emptyTile.row][emptyTile.column - 1] = 0;
        configuration[emptyTile.row][emptyTile.column] = valueOnLeft;
        emptyTile.column--;
        System.out.println("0 moved to the left");
        return true;
    }

    boolean moveUp() {
        if (emptyTile.row == 0) {
            System.out.println("It's impossible to move up");
            return false;
        }

        int valueUp = configuration[emptyTile.row - 1][emptyTile.column];
        configuration[emptyTile.row - 1][emptyTile.column] = 0;
        configuration[emptyTile.row][emptyTile.column] = valueUp;
        emptyTile.row--;
        System.out.println("0 moved up");
        return true;
    }

    boolean moveDown() {
        if (emptyTile.row == 3) {
            System.out.println("It's impossible to move down");
            return false;
        }

        int valueDown = configuration[emptyTile.row + 1][emptyTile.column];
        configuration[emptyTile.row + 1][emptyTile.column] = 0;
        configuration[emptyTile.row][emptyTile.column] = valueDown;
        emptyTile.row++;
        System.out.println("0 moved down");
        return true;
    }

    private void updateEmptyTilePosition() {
        this.emptyTile = getTilePosition(0);
    }

    Position getTilePosition(int value) throws IllegalArgumentException {
         if (value < 0 || value > 15)
             throw new IllegalArgumentException(value + " is not a valid value");

         for (int i = 0; i < 4; i++) {
             for (int j = 0; j < 4; j++) {
                 if (configuration[i][j] == value) {
                     return new Position(i, j);
                 }
             }
         }
         // Should never happen
         throw new IllegalArgumentException(value + " not found");
    }

//  Not sure if I'm going to need this method
  /*  int getTileValue(Position t) {
         return configuration[t.row][t.column];
    }*/

    int getTileValue(int r, int c) throws IllegalArgumentException {
        if ((r <  0 || r > 3) && (c <  0 || c > 3))
            throw new IllegalArgumentException(r + " and " + c + " are not valid values");
        else if (r <  0 || r > 3)
            throw new IllegalArgumentException(r + " is not a valid value");
        else if (c <  0 || c > 3)
            throw new IllegalArgumentException(c + " is not a valid value");

        return configuration[r][c];
    }

    boolean isGameSolved() {
        return Arrays.deepEquals(this.configuration, Board.SOLVED_CONFIGURATION);
    }

    boolean isBoardIdentical(int[][] configuration) {
        return Arrays.deepEquals(this.configuration, configuration);
    }

    boolean IsGameSolvable() {
        int numberOfInversions = 0;

//      In order to calculate the number of inversions:
//          go through each element
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
//          then compare the current element with all elements following it until the end
                int l = j + 1;
                for (int k = i; k < 4; k++) {
                    for (; l < 4; l++) {
                        if ((configuration[i][j] > configuration[k][l]) && (configuration[k][l] != 0)) {
                            numberOfInversions++;
                        }
                    }
                    l = 0;
                }
            }
        }

//      If empty tile is in 1st or 3rd row (index 0 or 2) and the number of inversions is odd  ---> then it's solvable
//      If empty tile is in 2nd or 4th row (index 1 or 3) and the number of inversions is even ---> then it's solvable
        if ((emptyTile.row % 2 == 0 && numberOfInversions % 2 == 1) || (emptyTile.row % 2 == 1 && numberOfInversions % 2== 0)) {
            return true;
        } else {
            return false;
        }
    }
}