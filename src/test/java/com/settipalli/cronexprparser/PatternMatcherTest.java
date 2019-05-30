package com.settipalli.cronexprparser;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class PatternMatcherTest {

    @Test
    void findExpressionTypeForExprTypeAll() {
        PatternMatcher patternMatcher = new PatternMatcher();
        Pair<ExpressionType, Matcher> result = patternMatcher.findExpressionType("*");
        Matcher matcher = result.getRight();
        matcher.find();
        assertEquals("*", matcher.group(1));
        assertEquals(ExpressionType.ALL, result.getLeft());
    }

    @Test
    void findExpressionTypeForInvalidExprType() {
        PatternMatcher patternMatcher = new PatternMatcher();
        Pair<ExpressionType, Matcher> result = patternMatcher.findExpressionType("?");
        assertEquals(ExpressionType.UNKNOWN, result.getLeft());
        assertNull(result.getRight());
    }

    @Test
    void findExpressionTypeForRepeatedExprTypeAll() {
        PatternMatcher patternMatcher = new PatternMatcher();
        Pair<ExpressionType, Matcher> result = patternMatcher.findExpressionType("**");
        assertEquals(ExpressionType.UNKNOWN, result.getLeft());
        assertNull(result.getRight());
    }

    @Test
    void findExpressionTypeForExprTypeNumber() {
        PatternMatcher patternMatcher = new PatternMatcher();
        Pair<ExpressionType, Matcher> result = patternMatcher.findExpressionType("1");
        Matcher matcher = result.getRight();
        matcher.find();
        assertEquals("1", matcher.group(1));
        assertEquals(ExpressionType.NUMBER, result.getLeft());
    }

    @Test
    void findExpressionTypeForExprTypeInvalidNumber() {
        PatternMatcher patternMatcher = new PatternMatcher();
        Pair<ExpressionType, Matcher> result = patternMatcher.findExpressionType("100");
        assertEquals(ExpressionType.UNKNOWN, result.getLeft());
        assertNull(result.getRight());
    }

    @Test
    void findExpressionTypeForExprTypeNegativeNumber() {
        PatternMatcher patternMatcher = new PatternMatcher();
        Pair<ExpressionType, Matcher> result = patternMatcher.findExpressionType("-1");
        assertEquals(ExpressionType.UNKNOWN, result.getLeft());
        assertNull(result.getRight());
    }

    @Test
    void findExpressionTypeForExprTypeRange() {
        PatternMatcher patternMatcher = new PatternMatcher();
        Pair<ExpressionType, Matcher> result = patternMatcher.findExpressionType("10-15");
        Matcher matcher = result.getRight();
        matcher.find();
        assertEquals("10", matcher.group(1));
        assertEquals("15", matcher.group(2));
        assertEquals(ExpressionType.RANGE, result.getLeft());
    }

    @Test
    void findExpressionTypeForExprTypeInvalidRangeStopValue() {
        PatternMatcher patternMatcher = new PatternMatcher();
        Pair<ExpressionType, Matcher> result = patternMatcher.findExpressionType("20-100");
        assertEquals(ExpressionType.UNKNOWN, result.getLeft());
        assertNull(result.getRight());
    }

    @Test
    void findExpressionTypeForExprTypeInvalidRangeStartValue() {
        PatternMatcher patternMatcher = new PatternMatcher();
        Pair<ExpressionType, Matcher> result = patternMatcher.findExpressionType("-1-10");
        assertEquals(ExpressionType.UNKNOWN, result.getLeft());
        assertNull(result.getRight());
    }

    @Test
    void findExpressionTypeForExprTypeValues() {
        PatternMatcher patternMatcher = new PatternMatcher();
        Pair<ExpressionType, Matcher> result = patternMatcher.findExpressionType("10,15");
        Matcher matcher = result.getRight();
        matcher.find();
        assertEquals(2, matcher.groupCount());
        assertEquals(ExpressionType.VALUES, result.getLeft());
    }

    @Test
    void findExpressionTypeForInvalidExprTypeValues() {
        PatternMatcher patternMatcher = new PatternMatcher();
        Pair<ExpressionType, Matcher> result = patternMatcher.findExpressionType("-10,15");
        assertEquals(ExpressionType.UNKNOWN, result.getLeft());
        assertNull(result.getRight());
    }

    @Test
    void findExpressionTypeForExprTypeValuesWithMissingValues() {
        PatternMatcher patternMatcher = new PatternMatcher();
        Pair<ExpressionType, Matcher> result = patternMatcher.findExpressionType("10,");
        assertEquals(ExpressionType.UNKNOWN, result.getLeft());
        assertNull(result.getRight());
    }

    @Test
    void findExpressionTypeForExprTypeIncrements() {
        PatternMatcher patternMatcher = new PatternMatcher();
        Pair<ExpressionType, Matcher> result = patternMatcher.findExpressionType("*/15");
        Matcher matcher = result.getRight();
        matcher.find();
        assertEquals("*", matcher.group(1));
        assertEquals("15", matcher.group(2));
        assertEquals(ExpressionType.INCREMENTS, result.getLeft());
    }
}