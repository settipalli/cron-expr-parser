package com.settipalli.cronexprparser.exceptions;

public class UnsupportedNumeratorExpressionTypeException extends RuntimeException {

    public UnsupportedNumeratorExpressionTypeException(String message) {
        super(message);
    }

    public UnsupportedNumeratorExpressionTypeException(String message, Throwable cause) {
        super(message, cause);
    }
}
