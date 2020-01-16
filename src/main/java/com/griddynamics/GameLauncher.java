package com.griddynamics;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;


public class GameLauncher {

    public static void main(String[] args) {

        final Board solvedBoard = new Board(Board.SOLVED_CONFIGURATION);
        Board board = new Board(new int[][]{
                {11, 13, 2, 4},
                {10, 6, 7, 5},
                {9, 8, 1, 12},
                {3, 14, 15, 0} });

        OutputProcessor.printBoardToFile("src/test/resources/file.txt", board);

    }
}