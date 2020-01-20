package com.griddynamics;

import java.util.ArrayList;

public class GameLauncher {

    public static void main(String[] args) {
        GameSolver solver = new GameSolver();
        OutputProcessor printer = new OutputProcessor();
        String path = OutputProcessor.FILE_PATH;

        int[][] conf = new int[][]{
                { 1,  2,  3,  4},
                { 5,  7,  0,  8},
                { 9,  6, 10, 11},
                {13, 14, 15, 12}};

        Board easyBoard = new Board(conf);
//        When the game solving algorithm is improved, the following line can be uncommented;
//        easyBoard.shuffleBoardConfiguration();

        printer.addToFile(path, "Initial configuration: \n\n" + easyBoard);

        ArrayList<Board.Movements> moves = solver.solve(easyBoard);

        if (moves == null) {
//          it was impossible to solve the game, then -1 will be returned
            printer.addToFile(path, "-1");
        } else if (moves.size() == 0) {
            printer.addToFile(path, "No movement was needed to solve the game. " +
                    "\n The initial configuration was already solved");
        } else {
        printer.addToFile(path, "Number of tiles movements: " + moves.size() + "\n");
        printer.addAllMovesToFile(moves, easyBoard);
        }
    }
}