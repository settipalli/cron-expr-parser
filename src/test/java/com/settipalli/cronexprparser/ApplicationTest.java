package com.settipalli.cronexprparser;

import com.settipalli.cronexprparser.exceptions.InvalidFieldExpressionException;
import com.settipalli.cronexprparser.exceptions.UnsupportedNumeratorExpressionTypeException;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


class ApplicationTest {

    private final String NEWLINE = System.getProperty("line.separator");
    private Application application;
    private PatternMatcher patternMatcher;

    @BeforeEach
    void setUp() {
        String[] args = {"*", "0", "1,15", "*", "1-5", "/bin/ls", "-a"};
        patternMatcher = new PatternMatcher();
        application = new Application(args, patternMatcher);
    }

    @AfterEach
    void tearDown() {
        patternMatcher = null; // prevent loitering
        application = null; // prevent loitering
    }

    @Test
    void generateValuesForExpressionTypeAndMatcherExprTypeAllMinute() {
        Pair<ExpressionType, Matcher> patternMatcherResult = patternMatcher.findExpressionType("*");
        String actualResult = application.generateValuesForExpressionTypeAndMatcher(patternMatcherResult.getLeft(),
                patternMatcherResult.getRight(), 60, 0);
        String expectedResult = "0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30" +
                " 31 32 33 34 35 36 37 38 39 40 41 42 43 44 45 46 47 48 49 50 51 52 53 54 55 56 57 58 59";
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void generateValuesForExpressionTypeAndMatcherExprTypeAllMinuteInvalid() {
        Pair<ExpressionType, Matcher> patternMatcherResult = patternMatcher.findExpressionType("+");
        String actualResult = application.generateValuesForExpressionTypeAndMatcher(patternMatcherResult.getLeft(),
                patternMatcherResult.getRight(), 60, 0);
        String expectedResult = "";
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void generateValuesForExpressionTypeAndMatcherExprTypeNumberMinute() {
        Pair<ExpressionType, Matcher> patternMatcherResult = patternMatcher.findExpressionType("1");
        String actualResult = application.generateValuesForExpressionTypeAndMatcher(patternMatcherResult.getLeft(),
                patternMatcherResult.getRight(), 60, 0);
        String expectedResult = "1";
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void generateValuesForExpressionTypeAndMatcherExprTypeNumberMinuteInvalid() {
        Pair<ExpressionType, Matcher> patternMatcherResult = patternMatcher.findExpressionType("-1");
        String actualResult = application.generateValuesForExpressionTypeAndMatcher(patternMatcherResult.getLeft(),
                patternMatcherResult.getRight(), 60, 0);
        String expectedResult = "";
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void generateValuesForExpressionTypeAndMatcherExprTypeRangeMinute() {
        Pair<ExpressionType, Matcher> patternMatcherResult = patternMatcher.findExpressionType("1-5");
        String actualResult = application.generateValuesForExpressionTypeAndMatcher(patternMatcherResult.getLeft(),
                patternMatcherResult.getRight(), 60, 0);
        String expectedResult = "1 2 3 4 5";
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void generateValuesForExpressionTypeAndMatcherExprTypeRangeMinuteInvalid() {
        Pair<ExpressionType, Matcher> patternMatcherResult = patternMatcher.findExpressionType("-1-5");
        String actualResult = application.generateValuesForExpressionTypeAndMatcher(patternMatcherResult.getLeft(),
                patternMatcherResult.getRight(), 60, 0);
        String expectedResult = "";
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void generateValuesForExpressionTypeAndMatcherExprTypeRangeMinuteStartGreaterThanStop() {
        Pair<ExpressionType, Matcher> patternMatcherResult = patternMatcher.findExpressionType("5-1");
        String actualResult = application.generateValuesForExpressionTypeAndMatcher(patternMatcherResult.getLeft(),
                patternMatcherResult.getRight(), 60, 0);
        String expectedResult = "";
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void generateValuesForExpressionTypeAndMatcherExprTypeValueMinute() {
        Pair<ExpressionType, Matcher> patternMatcherResult = patternMatcher.findExpressionType("1,5");
        String actualResult = application.generateValuesForExpressionTypeAndMatcher(patternMatcherResult.getLeft(),
                patternMatcherResult.getRight(), 60, 0);
        String expectedResult = "1 5";
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void generateValuesForExpressionTypeAndMatcherExprTypeValueMinuteMoreThanTwo() {
        Pair<ExpressionType, Matcher> patternMatcherResult = patternMatcher.findExpressionType("1,5,3,2,9");
        String actualResult = application.generateValuesForExpressionTypeAndMatcher(patternMatcherResult.getLeft(),
                patternMatcherResult.getRight(), 60, 0);
        String expectedResult = "1 2 3 5 9";
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void generateValuesForExpressionTypeAndMatcherExprTypeValueMinuteInvalid() {
        Pair<ExpressionType, Matcher> patternMatcherResult = patternMatcher.findExpressionType("-1,5,3,2,9");
        String actualResult = application.generateValuesForExpressionTypeAndMatcher(patternMatcherResult.getLeft(),
                patternMatcherResult.getRight(), 60, 0);
        String expectedResult = "";
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void generateValuesForExpressionTypeAndMatcherExprTypeValueMinuteAdditionalComma() {
        Pair<ExpressionType, Matcher> patternMatcherResult = patternMatcher.findExpressionType("1,5,");
        String actualResult = application.generateValuesForExpressionTypeAndMatcher(patternMatcherResult.getLeft(),
                patternMatcherResult.getRight(), 60, 0);
        String expectedResult = "";
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void generateValuesForExpressionTypeAndMatcherExprTypeIncrementMinute() {
        Pair<ExpressionType, Matcher> patternMatcherResult = patternMatcher.findExpressionType("*/15");
        String actualResult = application.generateValuesForExpressionTypeAndMatcher(patternMatcherResult.getLeft(),
                patternMatcherResult.getRight(), 60, 0);
        String expectedResult = "0 15 30 45";
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void generateValuesForExpressionTypeAndMatcherExprTypeIncrementMinuteWithRangeNumerator() {
        Pair<ExpressionType, Matcher> patternMatcherResult = patternMatcher.findExpressionType("1-45/15");
        String actualResult = application.generateValuesForExpressionTypeAndMatcher(patternMatcherResult.getLeft(),
                patternMatcherResult.getRight(), 60, 0);
        String expectedResult = "1 16 31";
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void generateValuesForExpressionTypeAndMatcherExprTypeIncrementMinuteWithNumericNumerator() {
        Pair<ExpressionType, Matcher> patternMatcherResult = patternMatcher.findExpressionType("5/15");
        String actualResult = application.generateValuesForExpressionTypeAndMatcher(patternMatcherResult.getLeft(),
                patternMatcherResult.getRight(), 60, 0);
        String expectedResult = "5 20 35 50";
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void generateValuesForExpressionTypeAndMatcherExprTypeIncrementMinuteWithValuesNumerator() {
        Pair<ExpressionType, Matcher> patternMatcherResult = patternMatcher.findExpressionType("1,5/15");
        assertThrows(UnsupportedNumeratorExpressionTypeException.class, () -> {
            application.generateValuesForExpressionTypeAndMatcher(patternMatcherResult.getLeft(),
                    patternMatcherResult.getRight(), 60, 0);
        });
    }

    @Test
    void generateValuesForExpressionTypeAndMatcherExprTypeAllHour() {
        Pair<ExpressionType, Matcher> patternMatcherResult = patternMatcher.findExpressionType("*");
        String actualResult = application.generateValuesForExpressionTypeAndMatcher(patternMatcherResult.getLeft(),
                patternMatcherResult.getRight(), 24, 0);
        String expectedResult = "0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23";
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void generateValuesForExpressionTypeAndMatcherExprTypeAllDayOfMonth() {
        Pair<ExpressionType, Matcher> patternMatcherResult = patternMatcher.findExpressionType("*");
        String actualResult = application.generateValuesForExpressionTypeAndMatcher(patternMatcherResult.getLeft(),
                patternMatcherResult.getRight(), 31, 1);
        String expectedResult = "1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30 31";
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void generateValuesForExpressionTypeAndMatcherExprTypeAllMonth() {
        Pair<ExpressionType, Matcher> patternMatcherResult = patternMatcher.findExpressionType("*");
        String actualResult = application.generateValuesForExpressionTypeAndMatcher(patternMatcherResult.getLeft(),
                patternMatcherResult.getRight(), 12, 1);
        String expectedResult = "1 2 3 4 5 6 7 8 9 10 11 12";
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void generateValuesForExpressionTypeAndMatcherExprTypeAllDayOfWeek() {
        Pair<ExpressionType, Matcher> patternMatcherResult = patternMatcher.findExpressionType("*");
        String actualResult = application.generateValuesForExpressionTypeAndMatcher(patternMatcherResult.getLeft(),
                patternMatcherResult.getRight(), 7, 1);
        String expectedResult = "1 2 3 4 5 6 7";
        assertEquals(expectedResult, actualResult);
    }


    @Test
    void generateValuesForExpressionTypeAndMatcherExprTypeNumberHour() {
        Pair<ExpressionType, Matcher> patternMatcherResult = patternMatcher.findExpressionType("1");
        String actualResult = application.generateValuesForExpressionTypeAndMatcher(patternMatcherResult.getLeft(),
                patternMatcherResult.getRight(), 12, 0);
        String expectedResult = "1";
        assertEquals(expectedResult, actualResult);
    }


    @Test
    void parseFieldMinuteExprAll() {
        String actualResult = application.parseField("*", "minute", 60, 0);
        String expectedResult = "0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30" +
                " 31 32 33 34 35 36 37 38 39 40 41 42 43 44 45 46 47 48 49 50 51 52 53 54 55 56 57 58 59";
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void parseFieldMinuteInvalidExprAll() {
        assertThrows(InvalidFieldExpressionException.class, () -> {
            application.parseField("+", "minute", 60, 0);
        });
    }

    @Test
    void parseFieldMinuteExprNumber() {
        String actualResult = application.parseField("1", "minute", 60, 0);
        String expectedResult = "1";
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void parseFieldMinuteExprInvalidNumber() {
        assertThrows(InvalidFieldExpressionException.class, () -> {
            application.parseField("100", "minute", 60, 0);
        });
    }

    @Test
    void parseFieldMinuteExprTwoValues() {
        String actualResult = application.parseField("1,2", "minute", 60, 0);
        String expectedResult = "1 2";
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void parseFieldMinuteExprMultipleValues() {
        String actualResult = application.parseField("1,2,3,4,5,6,7,8,9,10", "minute", 60, 0);
        String expectedResult = "1 2 3 4 5 6 7 8 9 10";
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void parseFieldMinuteExprRange() {
        String actualResult = application.parseField("1-15", "minute", 60, 0);
        String expectedResult = "1 2 3 4 5 6 7 8 9 10 11 12 13 14 15";
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void parseFieldMinuteExprInvalidRange() {
        assertThrows(InvalidFieldExpressionException.class, () -> {
            application.parseField("15-1", "minute", 60, 0);
        });
    }

    @Test
    void parse() {
        Map<String, String> actualResult = application.parse();
        Map<String, String> expectedResult = new HashMap<>();
        expectedResult.put("minute", "0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30" +
                " 31 32 33 34 35 36 37 38 39 40 41 42 43 44 45 46 47 48 49 50 51 52 53 54 55 56 57 58 59");
        expectedResult.put("hour", "0");
        expectedResult.put("day of month", "1 15");
        expectedResult.put("month", "1 2 3 4 5 6 7 8 9 10 11 12");
        expectedResult.put("day of week", "1 2 3 4 5");
        expectedResult.put("cmd", "/bin/ls -a");

        for (String field : actualResult.keySet()) {
            assertTrue(expectedResult.containsKey(field) &&
                    expectedResult.get(field).equalsIgnoreCase(actualResult.get(field)));
        }
    }

    @Test
    void process() {
        String actualResult = application.process();
        String expectedResult = "" +
                "minute         0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30" +
                " 31 32 33 34 35 36 37 38 39 40 41 42 43 44 45 46 47 48 49 50 51 52 53 54 55 56 57 58 59\n" +
                "hour           0\n" +
                "day of month   1 15\n" +
                "month          1 2 3 4 5 6 7 8 9 10 11 12\n" +
                "day of week    1 2 3 4 5\n" +
                "cmd            /bin/ls -a\n";
        assertEquals(expectedResult, actualResult);
    }
}