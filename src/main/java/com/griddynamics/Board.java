package com.griddynamics;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.logging.Logger;

import static com.griddynamics.Consts.NEW_LINE;

class Board {

    static class Position {
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

    private static final Logger LOGGER = Logger.getLogger(Board.class.getName());
    private static final List<Integer> expectedValues = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15);
    private int[][] configuration;

    Position getEmptyTile() {
        return emptyTile;
    }

    private Position emptyTile;


    Board(int[][] passedConfiguration) {
        this.configuration = new int[4][];
        for (int i = 0; i < 4; i++) {
            this.configuration[i] = Arrays.copyOf(passedConfiguration[i], passedConfiguration[i].length);
        }
        updateEmptyTilePosition();
    }

    int[][] getConfiguration() {
        return configuration;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        for (int[] row : this.configuration) {
            if (null != row) {
                result.append("[ ");
                for (int col : row) {
                    result.append(col).append(" ");
                }
                result.append("]").append(NEW_LINE);
            }
        }
        result.append(NEW_LINE);
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

    void delicatelyShuffle() {
        Random random = new Random();

        for (int i = 0; i < 50; i++) {
            int r = random.nextInt(4);
            switch (r) {
                case 0:
                    move(Movements.UP);
                    break;
                case 1:
                    move(Movements.DOWN);
                    break;
                case 2:
                    move(Movements.RIGHT);
                    break;
                case 3:
                    move(Movements.LEFT);
                    break;
            }
        }
    }

    // Movement in a direction means that empty tile (zero value) is moving in that direction
    // and the tile which place was taken goes to position previously occupied by empty tile.

    boolean move(Movements direction) {
        boolean isPossible = false;

        switch (direction) {
            case RIGHT:
                isPossible = moveRight();
                break;
            case LEFT:
                isPossible = moveLeft();
                break;
            case UP:
                isPossible = moveUp();
                break;
            case DOWN:
                isPossible = moveDown();
                break;
        }
        return isPossible;
    }

    private boolean moveRight() {
        if (emptyTile.column == 3) {
            return false;
        }

        int valueOnRight = configuration[emptyTile.row][emptyTile.column + 1];
        configuration[emptyTile.row][emptyTile.column + 1] = 0;
        configuration[emptyTile.row][emptyTile.column] = valueOnRight;
        emptyTile.column++;
        return true;
    }

    private boolean moveLeft() {
        if (emptyTile.column == 0) {
            return false;
        }

        int valueOnLeft = configuration[emptyTile.row][emptyTile.column - 1];
        configuration[emptyTile.row][emptyTile.column - 1] = 0;
        configuration[emptyTile.row][emptyTile.column] = valueOnLeft;
        emptyTile.column--;
        return true;
    }

    private boolean moveUp() {
        if (emptyTile.row == 0) {
            return false;
        }

        int valueUp = configuration[emptyTile.row - 1][emptyTile.column];
        configuration[emptyTile.row - 1][emptyTile.column] = 0;
        configuration[emptyTile.row][emptyTile.column] = valueUp;
        emptyTile.row--;
        return true;
    }

    private boolean moveDown() {
        if (emptyTile.row == 3) {
            return false;
        }

        int valueDown = configuration[emptyTile.row + 1][emptyTile.column];
        configuration[emptyTile.row + 1][emptyTile.column] = 0;
        configuration[emptyTile.row][emptyTile.column] = valueDown;
        emptyTile.row++;
        return true;
    }

    private void updateEmptyTilePosition() {
        this.emptyTile = getTilePosition(0);
    }

    Position getTilePosition(int value) throws IllegalArgumentException {
         if (value >= 0 && value <= 15) {
             for (int i = 0; i < 4; i++) {
                 for (int j = 0; j < 4; j++) {
                     if (configuration[i][j] == value) {
                         return new Position(i, j);
                     }
                 }
             }
         }
         return null;
    }

    int getTileValue(int r, int c) throws IllegalArgumentException {
        if ((r <  0 || r > 3) || (c <  0 || c > 3)) {
            return -1;
        }
        return configuration[r][c];
    }

    boolean isBoardIdentical(int[][] configuration) {
        return Arrays.deepEquals(this.configuration, configuration);
    }

    static Board readFromFile(String filePath) throws IllegalArgumentException {
        int[][] inputConfiguration = new int[4][4];
        Set<Integer> set = new HashSet<>();

        try(BufferedReader reader = Files.newBufferedReader(Paths.get(filePath))) {
            for (int i = 0; i < 4; i++) {
                String line = reader.readLine();
                if (line == null) {
                    throw new IllegalArgumentException("missing lines");
                }

                String[] numbers = line.split("\\s+");
                if (numbers.length != 4) {
                    throw new IllegalArgumentException("there should be 4 numbers in line " + (i+1));
                }
                for (int j = 0; j < 4; j++) {
                    try {
                        inputConfiguration[i][j] = Integer.parseInt(numbers[j]);
                        set.add(inputConfiguration[i][j]);
                    } catch (NumberFormatException e) {
                        throw new IllegalArgumentException("invalid number in line " + (i+1) + ": " + numbers[j]);
                    }
                }
            }
        } catch (IOException e) {
            LOGGER.severe("Problem with opening file " + e.getMessage());
        }

        if (!set.containsAll(expectedValues)) {
            throw new IllegalArgumentException("The file must contain all numbers from 0 to 15 inclusive");
        }
        return new Board(inputConfiguration);
    }
}