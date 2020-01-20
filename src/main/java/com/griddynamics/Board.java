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
                    return "Up";
                case DOWN:
                    return "Down";
                case LEFT:
                    return "Left";
                case RIGHT:
                    return "Right";
                default:
                    return "invalid content";
            }
        }
    }

    private int[][] configuration;

    public Position getEmptyTile() {
        return emptyTile;
    }

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

    boolean isBoardIdentical(int[][] configuration) {
        return Arrays.deepEquals(this.configuration, configuration);
    }
}