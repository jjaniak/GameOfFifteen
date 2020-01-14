package com.griddynamics;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;


public class GameSolver {

    public static void main(String[] args) {

//        Board solvedBoard = new Board(Board.SOLVEDCONFIGURATION);
        Board board = new Board(new int[][]{ {11, 13, 2, 4}, {10, 6, 7, 5}, {9, 8, 1, 12}, {3, 14, 15, 0} });

        printBoardToConsole(board);

        board.moveLeft();
        printBoardToConsole(board);

        printBoardToFile("src/test/resources/file.txt", board);
    }

    static void printBoardToFile(String stringPath, Board board) {
        try(BufferedWriter writer = Files.newBufferedWriter(Paths.get(stringPath),
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND)) {
            writer.write(board.toString());
        } catch (IOException ex) {
            ex.printStackTrace();
            System.err.format("IOException: %s%n", ex);
        }
    }

    static void printBoardToConsole(Board board) {
        System.out.println(board.toString());
    }
}