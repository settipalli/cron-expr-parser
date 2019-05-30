package com.settipalli.cronexprparser;

import com.settipalli.cronexprparser.exceptions.InvalidCronExpressionException;
import com.settipalli.cronexprparser.exceptions.InvalidFieldExpressionException;
import com.settipalli.cronexprparser.exceptions.InvalidIncrementsNumeratorValueException;
import com.settipalli.cronexprparser.exceptions.UnknownFieldWhileAssemblingFinalResultException;
import com.settipalli.cronexprparser.exceptions.UnsupportedNumeratorExpressionTypeException;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.regex.Matcher;

public class Application {
    private final String NEWLINE = System.getProperty("line.separator");
    private final int ARGCOUNT = 6;

    private final Logger log = LoggerFactory.getLogger(this.getClass().getName());

    private final String[] cmdArgs;
    private final PatternMatcher patternMatcher;

    // example:
    //      'minute' : (cmd arg index (0), max minutes in an hour (60), start min (0))
    //      'hour' : (cmd arg index (1), max hours in a day (24), start hour (0))
    //      'day of the month' : (cmd arg index (2), max days in a month (31), start day of the month (0))
    private final Map<String, Triple<Integer, Integer, Integer>> fieldNameToIndexMaxAndStartValueMapper;

    public Application(String[] args, PatternMatcher patternMatcher) {
        this.cmdArgs = args;
        this.patternMatcher = patternMatcher;

        fieldNameToIndexMaxAndStartValueMapper = new HashMap<>();
        fieldNameToIndexMaxAndStartValueMapper.put("minute", Triple.of(0, 60, 0));
        fieldNameToIndexMaxAndStartValueMapper.put("hour", Triple.of(1, 24, 0));
        fieldNameToIndexMaxAndStartValueMapper.put("day of month", Triple.of(2, 31, 1));
        fieldNameToIndexMaxAndStartValueMapper.put("month", Triple.of(3, 12, 1));
        fieldNameToIndexMaxAndStartValueMapper.put("day of week", Triple.of(4, 7, 1));
        log.debug("fieldNameToIndexMaxAndStartValueMapper initialized");
    }

    public String generateValuesForExpressionTypeAndMatcher(ExpressionType exprType, Matcher matcher, int maxValue,
                                                            int startValue) {
        if (matcher == null || exprType == null || maxValue < 0) return "";
        StringBuilder sb = new StringBuilder(128);
        int start, stop;
        List<Integer> values;

        matcher.find();
        switch (exprType) {
            case NUMBER:
                sb.append(matcher.group(1));
                break;
            case ALL:
                start = startValue;
                if (start == 0)
                    maxValue--; // If not, it will include '60' in case of minutes, and '24' in case or hours
                sb.append(start);
                while (++start <= maxValue) {
                    sb.append(" ").append(start);
                }
                break;
            case RANGE:
                start = Integer.parseInt(matcher.group(1));
                stop = Integer.parseInt(matcher.group(2));
                if (start < 0 || start >= maxValue ||
                        stop < 0 || stop >= maxValue ||
                        start > stop) {
                    break;
                }
                while (start <= stop) {
                    sb.append(start).append(" ");
                    start++;
                }
                break;
            case VALUES:
                values = new ArrayList<>();
                for (String value : matcher.group(0).split(",")) {
//                    sb.append(min).append(" ");
                    values.add(Integer.parseInt(value));
                }
                Collections.sort(values);
                values.stream().forEach(v -> sb.append(v).append(" "));
                values = null; // prevent loitering
                break;
            case INCREMENTS:
                String numerator = matcher.group(1);
                int denominator = Integer.parseInt(matcher.group(2));

                // Analyse the numerator
                Pair<ExpressionType, Matcher> numeratorExpressionMatcher = patternMatcher.findExpressionType(numerator);
                ExpressionType numeratorExpressionType = numeratorExpressionMatcher.getLeft();
                Matcher numeratorMatcher = numeratorExpressionMatcher.getRight();
                start = 0;
                stop = maxValue - 1;
                numeratorMatcher.find();
                switch (numeratorExpressionType) {
                    case NUMBER:
                        start = Integer.parseInt(numeratorMatcher.group(1));
                        break;
                    case ALL:
                        start = 0;
                        break;
                    case RANGE:
                        start = Integer.parseInt(numeratorMatcher.group(1));
                        stop = Integer.parseInt(numeratorMatcher.group(2));
                        if (start < 0 || start >= maxValue ||
                                stop < 0 || stop >= maxValue ||
                                start > stop) {
                            break;
                        }
                        break;
                    case VALUES:
                        throw new UnsupportedNumeratorExpressionTypeException("Value based numerator is not supported");
                    default:
                        throw new UnsupportedNumeratorExpressionTypeException("Unsupported Numerator expression type");
                }

                if (start >= maxValue || start > stop)
                    throw new InvalidIncrementsNumeratorValueException("Invalid Numerator value.");

                if (denominator > stop)
                    log.warn("WARN: Denominator value exceeds the threshold. Would be rounded off.");

                sb.append(start);
                while ((start + denominator) < stop) {
                    int sum = start + denominator;
                    sb.append(" ").append(sum);
                    start = sum;
                }
                break;
            default:
                // do nothing
                break;
        }

        return sb.toString().trim();
    }

    public String parseField(String arg, String fieldType, int maxValueForField, int startValueForField) {
        Pair<ExpressionType, Matcher> exprTypeAndMatcher = patternMatcher.findExpressionType(arg);

        String result = generateValuesForExpressionTypeAndMatcher(exprTypeAndMatcher.getLeft(),
                exprTypeAndMatcher.getRight(), maxValueForField, startValueForField);

        log.debug(String.format("generateValuesForExpressionTypeAndMatcher(%s, %s, %d, %d) = %s",
                exprTypeAndMatcher.getLeft(), exprTypeAndMatcher.getRight(), maxValueForField, startValueForField,
                result));

        if (result == null || result.length() == 0) {
            log.debug("parseField: result is either null or empty.");
            throw new InvalidFieldExpressionException(String.format("Invalid %s expression", fieldType));
        }

        return result;
    }

    public Map<String, String> parse() {
        Map<String, String> result = new HashMap<>();

        for (String field : fieldNameToIndexMaxAndStartValueMapper.keySet()) {

            Triple<Integer, Integer, Integer> indexAndMaxAndStartValue =
                    fieldNameToIndexMaxAndStartValueMapper.get(field);

            String parseResult = parseField(cmdArgs[indexAndMaxAndStartValue.getLeft()],
                    field, indexAndMaxAndStartValue.getMiddle(), indexAndMaxAndStartValue.getRight());

            log.debug(String.format("parseField(%s, %s, %d, %d) = %s", cmdArgs[indexAndMaxAndStartValue.getLeft()],
                    field, indexAndMaxAndStartValue.getMiddle(), indexAndMaxAndStartValue.getRight(), parseResult));

            result.put(field, parseResult);
        }

        // Add the command
        StringBuilder sb = new StringBuilder();
        for (int i = ARGCOUNT - 1; i < cmdArgs.length; ++i) {
            sb.append(cmdArgs[i]).append(" ");
        }
        result.put("cmd", sb.toString().trim());

        return result;
    }

    public String process() {
        // validate command line arguments
        if (cmdArgs == null || cmdArgs.length < ARGCOUNT) {
            log.debug(String.format("cmdArgs (%s) is either empty or the #args are less than ARGCOUNT."),
                    Arrays.toString(cmdArgs));
            throw new InvalidCronExpressionException("Invalid CRON expression.");
        }

        Map<String, String> result = parse();
        log.debug(String.format("parse: %s", result));

        String[] fieldOrder = {"minute", "hour", "day of month", "month", "day of week", "cmd"};
        StringBuilder sb = new StringBuilder(2048);
        for (String field : fieldOrder) {

            if (!result.containsKey(field)) {
                throw new UnknownFieldWhileAssemblingFinalResultException("WARN: found an unknown field while" +
                        " assembling the final result. Terminating ...");
            }

            sb.append(String.format("%-14s %s", field, result.get(field)))
                    .append(NEWLINE);
        }
        return sb.toString();
    }
}
