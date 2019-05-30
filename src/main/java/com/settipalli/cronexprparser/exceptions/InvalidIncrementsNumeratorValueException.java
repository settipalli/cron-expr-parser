package com.settipalli.cronexprparser.exceptions;

public class InvalidIncrementsNumeratorValueException extends RuntimeException {

    public InvalidIncrementsNumeratorValueException(String message) {
        super(message);
    }

    public InvalidIncrementsNumeratorValueException(String message, Throwable cause) {
        super(message, cause);
    }
}
