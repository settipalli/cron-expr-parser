package com.settipalli.cronexprparser;

import org.apache.commons.cli.Options;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class MainApplication {
    private static final String NEWLINE = System.getProperty("line.separator");

    public static void printHelp() {
        StringBuilder sb = new StringBuilder(4096);
        sb.append(NEWLINE)
                .append("usage: cron-expr-parser <minute> <hour> <day-of-month> <month> <day-of-week> <command>")
                .append(NEWLINE);
        System.out.println(sb.toString());
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(args));
        // validate command line arguments
        if (!validCronExpr(args)) {
            System.out.println("Invalid CRON expression.");
            printHelp();
            System.exit(1);
        }
    }

    private static boolean validCronExpr(String[] args) {
        if (args == null || args.length < 6)
            return false;
        return true;
     }
}
