package com.griddynamics;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.griddynamics.GameLauncher.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class GameLauncherTests {

    @AfterEach
    public void deleteFile() {
        try {
            Files.deleteIfExists(Paths.get(FILE_PATH));
        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        }
    }

    @Test
    public void shouldProperlyPrintNotSolvable() {
        String[] args = new String[] { "invalid" };
        GameLauncher.main(args);

        String content = null;
        try {
            content = Files.readString(Paths.get(FILE_PATH));
        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        }

        assertThat("File does not contain initial message", content, containsString(INITIAL_CONF_MESSAGE));

        String lastLine = content.substring(content.length()-2);
        assertThat("The file does not contain \"-1\" at the end", lastLine, comparesEqualTo("-1"));
    }

    @Test
    public void shouldProperlyPrintSolvable() {
        GameLauncher.main(null);

        String content = null;
        try {
            content = Files.readString(Paths.get(FILE_PATH));
        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        }

        assertThat("File does not contain initial message", content, containsString(INITIAL_CONF_MESSAGE));
        assertThat("File does not contain number of moves message", content, containsString(MOVES_NUMBER_MESSAGE));

        Board solvedBoard = new Board(GameSolver.SOLVED_CONFIGURATION);

        assertThat("File does not contain solved configuration at the very end",
                content, endsWith(solvedBoard.toString()));
    }
}