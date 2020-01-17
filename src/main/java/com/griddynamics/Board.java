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

    static final int[][] SOLVED_CONFIGURATION = { {1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}, {13, 14, 15, 0} };
    private int[][] configuration;
    private int rowWhereZero = 0, columnWhereZero = 0;


    public Board() {
        this.configuration = new int[4][4];
    }

    public Board(int[][] configuration) {
        this.configuration = configuration;
    }

    public int[][] getConfiguration() {
        return configuration;
    }

    public void setConfiguration(int[][] configuration) {
        this.configuration = configuration;
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

//    Movement in a direction mean that Zero tile is moving in that direction:

    boolean moveRight() {
        if (columnWhereZero == 3) {
            System.out.println("It's impossible to move to the right");
            return false;
        }

        int valueOnRight = configuration[rowWhereZero][columnWhereZero + 1];
        configuration[rowWhereZero][columnWhereZero + 1] = 0;
        configuration[rowWhereZero][columnWhereZero] = valueOnRight;
        columnWhereZero++;
        System.out.println("0 moved to the right");
        return true;
    }

    boolean moveLeft() {
        if (columnWhereZero == 0) {
            System.out.println("It's impossible to move to the left");
            return false;
        }

        int valueOnLeft = configuration[rowWhereZero][columnWhereZero - 1];
        configuration[rowWhereZero][columnWhereZero - 1] = 0;
        configuration[rowWhereZero][columnWhereZero] = valueOnLeft;
        columnWhereZero--;
        System.out.println("0 moved to the left");
        return true;
    }

    boolean moveUp() {
        if (rowWhereZero == 0) {
            System.out.println("It's impossible to move up");
            return false;
        }

        int valueUp = configuration[rowWhereZero - 1][columnWhereZero];
        configuration[rowWhereZero - 1][columnWhereZero] = 0;
        configuration[rowWhereZero][columnWhereZero] = valueUp;
        rowWhereZero--;
        System.out.println("0 moved up");
        return true;
    }

    boolean moveDown() {
        if (rowWhereZero == 3) {
            System.out.println("It's impossible to move down");
            return false;
        }

        int valueDown = configuration[rowWhereZero + 1][columnWhereZero];
        configuration[rowWhereZero + 1][columnWhereZero] = 0;
        configuration[rowWhereZero][columnWhereZero] = valueDown;
        rowWhereZero++;
        System.out.println("0 moved down");
        return true;
    }

    void findWhereZero(int [][] configuration) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (configuration[i][j] == 0) {
                    this.rowWhereZero = i;
                    this.columnWhereZero = j;
                    break;
                }
            }
        }
    }

    int[] findWhereNumber(int [][] configuration, int numberWeLookFor) {
        int rowOfSearchedNumber = 0, columnOfSearchedNumber = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (configuration[i][j] == numberWeLookFor) {
                    rowOfSearchedNumber = i;
                    columnOfSearchedNumber = j;
                    break;
                }
            }
        }
        return new int[]{rowOfSearchedNumber, columnOfSearchedNumber};
    }

    boolean isGameSolved() {
        return Arrays.deepEquals(this.configuration, Board.SOLVED_CONFIGURATION);
    }

    boolean isBoardIdentical(int[][] configuration) {
        return Arrays.deepEquals(this.configuration, configuration);
    }
}