package com.griddynamics;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

public class OutputProcessor {
    private static final String filePath = "";

    static final String FILE_PATH = "src/test/resources/file.txt";
    static final String NEW_LINE = System.getProperty("line.separator");

    void addToFile(String stringPath, String input) {
        try(BufferedWriter writer = Files.newBufferedWriter(Paths.get(stringPath),
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND)) {
            writer.write(input);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.format("IOException: %s%n", e);
        }
    }

    void addAllMovesToFile(ArrayList<Board.Movements> moves, Board board) {

        for (Board.Movements m : moves) {
            addToFile(FILE_PATH, m.toString() + NEW_LINE);
            switch (m) {
                case UP:
                    board.moveUp();
                    break;
                case DOWN:
                    board.moveDown();
                    break;
                case RIGHT:
                    board.moveRight();
                    break;
                case LEFT:
                    board.moveLeft();
                    break;
            }
            addToFile(FILE_PATH, board.toString());
        }
    }
}
