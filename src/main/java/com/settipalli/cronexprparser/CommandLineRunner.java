package com.settipalli.cronexprparser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

public class CommandLineRunner {
    private final static String NEWLINE = System.getProperty("line.separator");

    public static String getUsageText() {
        InputStream usageTextStream = ClassLoader.getSystemClassLoader().getResourceAsStream("usage.txt");
        StringBuilder sb = new StringBuilder(2048);
        try (BufferedReader br = new BufferedReader(new InputStreamReader(usageTextStream))) {
            br.lines().forEach(line -> sb.append(line).append(NEWLINE));
        } catch (IOException e) {
            // do nothing.
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        PatternMatcher patternMatcher = new PatternMatcher();
        Application application = new Application(args, patternMatcher);
        try {
            String result = application.process();
            System.out.println(result);
        } catch (Exception ex) {
            StringBuilder sb = new StringBuilder(2048);
            sb.append(NEWLINE)
                    .append("Error: ")
                    .append(ex.getLocalizedMessage())
                    .append(getUsageText())
                    .append(NEWLINE);
            System.out.println(sb.toString());
        }
    }
}
