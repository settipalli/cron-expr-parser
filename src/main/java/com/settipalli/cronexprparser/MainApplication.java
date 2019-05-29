package com.settipalli.cronexprparser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MainApplication {
    private static final String NEWLINE = System.getProperty("line.separator");
    private static final int ARG_COUNT = 6;

    public static String getHelptext() {
        InputStream usageTextStream = ClassLoader.getSystemClassLoader().getResourceAsStream("usage.txt");
        StringBuilder sb = new StringBuilder(2048);
        try (BufferedReader br = new BufferedReader(new InputStreamReader(usageTextStream))) {
            br.lines().forEach(line -> sb.append(line).append(NEWLINE));
        } catch (IOException e) {
            // do nothing.
        }
        return sb.toString();
    }

    private static boolean validCronExpr(String[] args) {
        if (args == null || args.length < ARG_COUNT)
            return false;
        return true;
    }

    private static Map<String, String> parse(String[] args) {
        Map<String, String> result = new HashMap<>();

        String minute = parseMinutes(args[1]);

        return result;
    }

    private static String parseMinutes(String arg) {
        return "";
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(args));

        // validate command line arguments
        if (!validCronExpr(args)) {
            System.err.println("Error: Invalid CRON expression.");
            System.out.println(getHelptext());
            System.exit(1);
        }

        Map<String, String> result = parse(args);
    }
}
