package com.griddynamics;

import java.util.ArrayList;

public class GameLauncher {
    static final String FILE_PATH = "src/test/resources/file.txt";
    static final String INITIAL_CONF_MESSAGE = "Initial configuration: ";
    static final String MOVES_NUMBER_MESSAGE = "Number of tiles movements needed to solve the game: ";

    public static void main(String[] args) {
        GameSolver solver = new GameSolver();
        OutputProcessor printer = new FileOutputProcessor(FILE_PATH);

        Board board = new Board(GameSolver.SOLVED_CONFIGURATION);

        // When the game solving algorithm is improved delicatelyShuffle() can be replaced with shuffleBoardConfiguration()
        board.delicatelyShuffle();
        Board copyBoard = new Board(board.getConfiguration());

        printer.appendLine(INITIAL_CONF_MESSAGE);
        printer.append(board.toString());

        ArrayList<Board.Movements> moves = solver.solve(copyBoard);
        if (moves == null) {
            // If the solution is unattainable (the configuration isn't solvable or it's too difficult for now),
            // then -1 will be printed in the file
            printer.append("-1");
        } else {
            printer.append(MOVES_NUMBER_MESSAGE + moves.size());
            printer.appendLine(OutputProcessor.NEW_LINE);

            if (moves.size() == 0) {
                printer.append("No movement was needed to solve the game as the initial configuration was already solved");
            }

            printMovements(board, moves, printer);
        }
    }

    private static void printMovements(Board board, ArrayList<Board.Movements> moves, OutputProcessor printer) {
        // recreating configurations from moves and appending them to the file
        for (Board.Movements m : moves) {
            printer.appendLine(m.toString());
            board.move(m);
            printer.append(board.toString());
        }
    }
}