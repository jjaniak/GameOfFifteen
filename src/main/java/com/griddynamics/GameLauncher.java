package com.griddynamics;

import java.util.ArrayList;

public class GameLauncher {

    public static void main(String[] args) {
        GameSolver solver = new GameSolver();

        final String filePath = "src/test/resources/file.txt";
        OutputProcessor printer = new FileOutputProcessor(filePath);

        int[][] conf = new int[][]{
                { 1,  2,  3,  4},
                { 5,  7,  0,  8},
                { 9,  6, 10, 11},
                {13, 14, 15, 12}};

        Board easyBoard = new Board(conf);
        Board copyBoard = new Board(conf);
//        When the game solving algorithm is improved, the following line can be uncommented
//        easyBoard.shuffleBoardConfiguration();

        printer.append("Initial configuration: ");
        printer.appendNewLine();
        printer.append(easyBoard.toString());

        ArrayList<Board.Movements> moves = solver.solve(copyBoard);

        if (moves == null) {
//          If it is impossible to solve the game, then -1 will be printed in the file
            printer.append("-1");
        } else {
            printer.append("Number of tiles movements needed to solve the game: " + moves.size());
            printer.appendNewLine();
            printer.appendNewLine();

            if (moves.size() == 0) {
                printer.append("No movement was needed to solve the game as the initial configuration was already solved");
            }

//          recreating configurations from moves and appending them to the file
            for (Board.Movements m : moves) {
                printer.append(m.toString());
                printer.appendNewLine();
                switch (m) {
                    case UP:
                        easyBoard.moveUp();
                        break;
                    case DOWN:
                        easyBoard.moveDown();
                        break;
                    case RIGHT:
                        easyBoard.moveRight();
                        break;
                    case LEFT:
                        easyBoard.moveLeft();
                        break;
                }
                printer.append(easyBoard.toString());
            }
        }
    }
}