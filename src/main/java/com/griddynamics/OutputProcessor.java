package com.griddynamics;

public interface OutputProcessor {
    String NEW_LINE = System.getProperty("line.separator");

    void append(String input);

    void appendLine(String input);
}
