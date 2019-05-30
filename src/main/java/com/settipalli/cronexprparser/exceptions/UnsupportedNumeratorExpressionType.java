package com.settipalli.cronexprparser.exceptions;

public class UnsupportedNumeratorExpressionType extends RuntimeException {

    public UnsupportedNumeratorExpressionType(String message) {
        super(message);
    }

    public UnsupportedNumeratorExpressionType(String message, Throwable cause) {
        super(message, cause);
    }
}
