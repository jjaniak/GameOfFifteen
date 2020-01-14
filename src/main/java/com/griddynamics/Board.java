package com.griddynamics;

public class Board {
    static final int[][] SOLVEDCONFIGURATION = { {1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}, {13, 14, 15, 0} };
    private int[][] configuration;
    int rowWhereZero = 0, columnWhereZero = 0;


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

//    Movement in a direction mean that Zero tile is moving in that direction:

    boolean moveRight(int[][] currentConfiguration) {
        if (columnWhereZero == 3) {
            System.out.println("It's impossible to move to the right");
            return false;
        } else {
            int valueOnRight = currentConfiguration[rowWhereZero][columnWhereZero + 1];
            currentConfiguration[rowWhereZero][columnWhereZero + 1] = 0;
            currentConfiguration[rowWhereZero][columnWhereZero] = valueOnRight;
            columnWhereZero ++;
            System.out.println("0 moved to the right");
            return true;
        }
    }

    boolean moveLeft(int[][] currentConfiguration) {
        if (columnWhereZero == 0) {
            System.out.println("It's impossible to move to the left");
            return false;
        } else {
            int valueOnLeft = currentConfiguration[rowWhereZero][columnWhereZero - 1];
            currentConfiguration[rowWhereZero][columnWhereZero - 1] = 0;
            currentConfiguration[rowWhereZero][columnWhereZero] = valueOnLeft;
            columnWhereZero --;
            System.out.println("0 moved to the left");
            return true;
        }
    }

    boolean moveUp(int[][] currentConfiguration) {
        if (rowWhereZero == 0) {
            System.out.println("It's impossible to move up");
            return false;
        } else {
            int valueUp = currentConfiguration[rowWhereZero - 1][columnWhereZero];
            currentConfiguration[rowWhereZero - 1][columnWhereZero] = 0;
            currentConfiguration[rowWhereZero][columnWhereZero] = valueUp;
            rowWhereZero --;
            System.out.println("0 moved up");
            return true;
        }
    }

    boolean moveDown(int[][] currentConfiguration) {
        if (rowWhereZero == 3) {
            System.out.println("It's impossible to move down");
            return false;
        } else {
            int valueDown = currentConfiguration[rowWhereZero + 1][columnWhereZero];
            currentConfiguration[rowWhereZero + 1][columnWhereZero] = 0;
            currentConfiguration[rowWhereZero][columnWhereZero] = valueDown;
            rowWhereZero ++;
            System.out.println("0 moved down");
            return true;
        }
    }

    void findWhereZero(int [][] currentConfiguration) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (currentConfiguration[i][j] == 0) {
                    this.rowWhereZero = i;
                    this.columnWhereZero = j;
                    break;
                }
            }
        }
    }

    int[] findWhereNumber(int [][] currentConfiguration, int numberWeLookFor) {
        int rowOfSearchedNumber = 0, columnOfSearchedNumber = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (currentConfiguration[i][j] == numberWeLookFor) {
                    rowOfSearchedNumber = i;
                    columnOfSearchedNumber = j;
                    break;
                }
            }
        }
        return new int[]{rowOfSearchedNumber, columnOfSearchedNumber};
    }
}