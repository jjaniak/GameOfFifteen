package com.griddynamics;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class OutputProcessor {
    private static final String filePath = "";

    static final String FILE_PATH = "src/test/resources/file.txt";
    static final String NEW_LINE = System.getProperty("line.separator");

    void addToFile(String stringPath, String input) {
        try(BufferedWriter writer = Files.newBufferedWriter(Paths.get(stringPath),
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND)) {
            writer.write(board.toString());
        } catch (IOException ex) {
            ex.printStackTrace();
            System.err.format("IOException: %s%n", ex);
        }
    }
}
