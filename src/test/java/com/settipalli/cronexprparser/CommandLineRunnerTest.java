package com.settipalli.cronexprparser;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static org.junit.jupiter.api.Assertions.*;

class CommandLineRunnerTest {

    private final static String NEWLINE = System.getProperty("line.separator");

    @Test
    void getUsageText() {
        String usageText = CommandLineRunner.getUsageText();
        InputStream usageTextStream = getClass().getClassLoader().getResourceAsStream("testusage.txt");
        StringBuilder sb = new StringBuilder(2048);
        try (BufferedReader br = new BufferedReader(new InputStreamReader(usageTextStream))) {
            br.lines().forEach(line -> sb.append(line).append(NEWLINE));
        } catch (IOException e) {
            // do nothing.
        }
        assertEquals(sb.toString(), usageText);
    }
}