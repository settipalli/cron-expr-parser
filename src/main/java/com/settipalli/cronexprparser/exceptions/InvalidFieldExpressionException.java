package com.settipalli.cronexprparser.exceptions;

public class InvalidFieldExpressionException extends RuntimeException {

    public InvalidFieldExpressionException(String message) {
        super(message);
    }

    public InvalidFieldExpressionException(String message, Throwable cause) {
        super(message, cause);
    }
}
