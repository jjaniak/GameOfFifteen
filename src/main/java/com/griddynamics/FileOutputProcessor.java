package com.griddynamics;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.logging.Logger;

import static com.griddynamics.Consts.NEW_LINE;

class FileOutputProcessor implements OutputProcessor {
    
    private final String filePath;
    private static final Logger LOGGER = Logger.getLogger(FileOutputProcessor.class.getName());


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
            LOGGER.severe("problem writing to file " + e.getMessage());
        }
    }

    @Override
    public void appendLine(String input) {
        try(BufferedWriter writer = Files.newBufferedWriter(Paths.get(filePath),
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND)) {
            writer.write(input + NEW_LINE);
        } catch (IOException e) {
            LOGGER.severe("problem writing to file " + e.getMessage());
        }
    }
}
