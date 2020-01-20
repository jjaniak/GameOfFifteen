package com.griddynamics;

import java.util.ArrayList;

public class GameLauncher {

    public static void main(String[] args) {
        GameSolver solver = new GameSolver();

        OutputProcessor printer = new OutputProcessor();
        String path = OutputProcessor.FILE_PATH;
        String nl = OutputProcessor.NEW_LINE;

        int[][] conf = new int[][]{
                { 1,  2,  3,  4},
                { 5,  7,  0,  8},
                { 9,  6, 10, 11},
                {13, 14, 15, 12}};

        Board easyBoard = new Board(conf);
        Board copyBoard = new Board(conf);
//        When the game solving algorithm is improved, the following line can be uncommented
//        easyBoard.shuffleBoardConfiguration();

        printer.addToFile(path, "Initial configuration: " + nl + easyBoard);

        ArrayList<Board.Movements> moves = solver.solve(copyBoard);

        if (moves == null) {
//          If it is impossible to solve the game, then -1 will be returned
            printer.addToFile(path, "-1");
        } else if (moves.size() == 0) {
            printer.addToFile(path, "No movement was needed to solve the game." + nl + "The initial configuration was already solved");
        } else {
        printer.addToFile(path, "Number of tiles movements needed to solve the game: " + moves.size() + nl + nl);
        printer.addAllMovesToFile(moves, easyBoard);
        }
    }
}