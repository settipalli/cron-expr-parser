package com.settipalli.cronexprparser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

enum ExpressionType {
    NUMBER,
    ALL,
    RANGE,
    VALUES,
    INCREMENTS,
    UNKNOWN
}

public class MainApplication {
    private final String NEWLINE;
    private final int ARG_COUNT;
    private final int EXPRESSION_TYPE_COUNT;
    private final Map<ExpressionType, Pattern> patternExpressionMapper;

    public MainApplication() {
        NEWLINE = System.getProperty("line.separator");
        ARG_COUNT = 6;
        EXPRESSION_TYPE_COUNT = 5;
        patternExpressionMapper = new HashMap<>();

        // Initial regular expression patterns.
        patternExpressionMapper.put(ExpressionType.NUMBER, Pattern.compile("^\\d+$"));
        patternExpressionMapper.put(ExpressionType.ALL, Pattern.compile("^\\*$"));
        patternExpressionMapper.put(ExpressionType.RANGE, Pattern.compile("^\\d+-\\d+$"));
        patternExpressionMapper.put(ExpressionType.VALUES, Pattern.compile("^[\\d|\\D]+,[\\d|\\D]+$"));
        patternExpressionMapper.put(ExpressionType.INCREMENTS, Pattern.compile("^[\\*|\\d]+/\\d+$"));
    }

    public String getHelptext() {
        InputStream usageTextStream = ClassLoader.getSystemClassLoader().getResourceAsStream("usage.txt");
        StringBuilder sb = new StringBuilder(2048);
        try (BufferedReader br = new BufferedReader(new InputStreamReader(usageTextStream))) {
            br.lines().forEach(line -> sb.append(line).append(NEWLINE));
        } catch (IOException e) {
            // do nothing.
        }
        return sb.toString();
    }

    public void printErrAndExit(String errMsg, int exitStatus) {
        StringBuilder sb = new StringBuilder(2048);
        sb.append(NEWLINE)
                .append("Error: ")
                .append(errMsg)
                .append(getHelptext())
                .append(NEWLINE);
        System.out.println(sb.toString());
        System.exit(exitStatus);
    }

    private boolean validCronExpr(String[] args) {
        if (args == null || args.length < ARG_COUNT)
            return false;
        return true;
    }

    private ExpressionType findExprType(String arg) {
        for (ExpressionType exprType : patternExpressionMapper.keySet()) {
            if (patternExpressionMapper.get(exprType).matcher(arg).matches())
                return exprType;
        }
        return ExpressionType.UNKNOWN;
    }

    private String parseMinutes(String arg) {
        ExpressionType exprType = findExprType(arg);
        StringBuilder sb = new StringBuilder(32);
        final int maxMinutes = 60;
        switch(exprType) {
            case NUMBER:
                sb.append(arg);
                break;
            case ALL:
                break;
            case RANGE:
                break;
            case VALUES:
                break;
            case INCREMENTS:
                String[] temp = arg.split("/");
                int start, diff;
                start = ("*".equals(temp[0])) ? 0 : Integer.parseInt(temp[0]);
                diff = Integer.parseInt(temp[1]);
                if(start > maxMinutes - 1)
                    printErrAndExit("The value of the numerator in the <minute> field cannot exceed 59", 3);
                if (diff >= maxMinutes)
                    System.out.println("WARN: Denominator in the <minute> field exceeds tje threshold of 60 minutes.");
                sb.append(start);
                while ((start + diff) < maxMinutes) {
                    int sum = start + diff;
                    sb.append(" ").append(sum);
                    start = sum;
                }
                break;
            default:
                printErrAndExit("Invalid <minute> expression.", 2);
        }
        return sb.toString();
    }

    private Map<String, String> parse(String[] args) {
        Map<String, String> result = new HashMap<>();

        String minutes = parseMinutes(args[0]);
        System.out.println("Minutes: " + minutes);

        return result;
    }

    public static void main(String[] args) {

        System.out.println(Arrays.toString(args));

        MainApplication application = new MainApplication();

        // validate command line arguments
        if (!application.validCronExpr(args)) {
            application.printErrAndExit("Invalid CRON expression.", 1);
        }

        Map<String, String> result = application.parse(args);
    }
}
