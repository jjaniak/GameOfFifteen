package com.griddynamics;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.logging.Logger;

import static com.griddynamics.Consts.NEW_LINE;

public class GameLauncher {
    static final String OUTPUT_FILE_PATH = "src/main/resources/gameResult.txt";
    static final String INITIAL_CONF_MESSAGE = "Initial configuration: ";
    static final String MOVES_NUMBER_MESSAGE = "Number of tiles movements needed to solve the game: ";
    private static final Logger LOGGER = Logger.getLogger(GameLauncher.class.getName());

    public static void main(String[] args) throws IOException {
        Board board;

        if (null == args || args.length == 0) {
            board = new Board(GameSolver.SOLVED_CONFIGURATION);
            // When the game solving algorithm is improved delicatelyShuffle() can be replaced with shuffleBoardConfiguration()
            board.delicatelyShuffle();
        } else {
            try {
                board = Board.readFromFile(args[0]);
            } catch (Exception e) {
                LOGGER.severe(e.getMessage());
                return;
            }
        }

        GameSolver solver = new GameSolver();

        Files.deleteIfExists(Paths.get(OUTPUT_FILE_PATH));
        OutputProcessor printer = new FileOutputProcessor(OUTPUT_FILE_PATH);

        Board copyBoard = new Board(board.getConfiguration());

        printer.appendLine(INITIAL_CONF_MESSAGE);
        printer.append(board.toString());

        ArrayList<Board.Movements> moves = solver.solve(copyBoard);
        if ( null == moves) {
            // If the solution is unattainable (the configuration is unsolvable or it's too difficult for now),
            // then -1 will be printed in the file
            printer.append("-1");
        } else {
            printer.append(MOVES_NUMBER_MESSAGE + moves.size());
            printer.appendLine(NEW_LINE);

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