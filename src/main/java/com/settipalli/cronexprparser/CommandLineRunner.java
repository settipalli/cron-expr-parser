package com.settipalli.cronexprparser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class CommandLineRunner {
    private final static String NEWLINE = System.getProperty("line.separator");
    private final static Logger log = LoggerFactory.getLogger(CommandLineRunner.class.getName());

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
        log.info("Application Started.");
        PatternMatcher patternMatcher = new PatternMatcher();
        log.debug("Pattern Matcher initialized.");
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
