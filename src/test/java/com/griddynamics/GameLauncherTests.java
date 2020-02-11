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
    public void deleteFile() throws IOException {
        Files.deleteIfExists(Paths.get(OUTPUT_FILE_PATH));
    }

    @Test
    public void shouldProperlyPrintNotSolvable() throws IOException {
        String path = FileReadingTests.class.getClassLoader().getResource("unsolvableConf.txt").getPath();
        GameLauncher.main(new String[] {path});

        String content = new String(Files.readAllBytes(Paths.get(OUTPUT_FILE_PATH)));

        assertThat("File does not contain initial message", content, containsString(INITIAL_CONF_MESSAGE));

        String lastLine = content.substring(content.length()-2);
        assertThat("The file does not contain \"-1\" at the end", lastLine, comparesEqualTo("-1"));
    }

    @Test
    public void shouldProperlyPrintSolvable() throws IOException {
        GameLauncher.main(null);

        String content = new String(Files.readAllBytes(Paths.get(OUTPUT_FILE_PATH)));

        assertThat("File does not contain initial message", content, containsString(INITIAL_CONF_MESSAGE));
        assertThat("File does not contain number of moves message", content, containsString(MOVES_NUMBER_MESSAGE));

        Board solvedBoard = new Board(GameSolver.SOLVED_CONFIGURATION);

        assertThat("File does not contain solved configuration at the very end",
                content, endsWith(solvedBoard.toString()));
    }
}