package com.settipalli.cronexprparser.exceptions;

public class InvalidRangeStopValueException extends RuntimeException {

    public InvalidRangeStopValueException(String message) {
        super(message);
    }

    public InvalidRangeStopValueException(String message, Throwable cause) {
        super(message, cause);
    }
}
