package com.settipalli.cronexprparser;

import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternMatcher {
    private final String NUMBER_MATCH_PATTERN;
    private final String ALL_MATCH_PATTERN;
    private final String RANGE_MATCH_PATTERN;
    private final String VALUE_MATCH_PATTERN;
    private final String INCREMENT_MATCH_PATTERN;

    private final Map<ExpressionType, Pattern> patternExpressionMapper;

    public PatternMatcher() {
        NUMBER_MATCH_PATTERN = "^(\\d{1,2})$";
        ALL_MATCH_PATTERN = "^(\\*{1})$";
        RANGE_MATCH_PATTERN = "^(\\d+{1,2})-(\\d{1,2})$";
        VALUE_MATCH_PATTERN = "^(\\d{1,2})(,\\d{1,2})+$";
        INCREMENT_MATCH_PATTERN = "^(.+)/(\\d+)$";

        patternExpressionMapper = new HashMap<>();

        patternExpressionMapper.put(ExpressionType.NUMBER, Pattern.compile(NUMBER_MATCH_PATTERN));
        patternExpressionMapper.put(ExpressionType.ALL, Pattern.compile(ALL_MATCH_PATTERN));
        patternExpressionMapper.put(ExpressionType.RANGE, Pattern.compile(RANGE_MATCH_PATTERN));
        patternExpressionMapper.put(ExpressionType.VALUES, Pattern.compile(VALUE_MATCH_PATTERN));
        patternExpressionMapper.put(ExpressionType.INCREMENTS, Pattern.compile(INCREMENT_MATCH_PATTERN));
    }

    public Pair<ExpressionType, Matcher> findExpressionType(String arg) {
        for (ExpressionType exprType : patternExpressionMapper.keySet()) {
            Matcher matcher = patternExpressionMapper.get(exprType).matcher(arg);
            if (matcher.matches()) {
                matcher.reset();
                return Pair.of(exprType, matcher);
            }
        }
        return Pair.of(ExpressionType.UNKNOWN, null);
    }
}
