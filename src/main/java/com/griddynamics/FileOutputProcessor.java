package com.griddynamics;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

class FileOutputProcessor implements OutputProcessor {
    
    private final String filePath;

    FileOutputProcessor(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void append(String input) {
        try(BufferedWriter writer = Files.newBufferedWriter(Paths.get(filePath),
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND)) {
            writer.write(input);
        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        }
    }

    @Override
    public void appendLine(String input) {
        try(BufferedWriter writer = Files.newBufferedWriter(Paths.get(filePath),
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND)) {
            writer.write(input + NEW_LINE);
        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        }
    }
}
