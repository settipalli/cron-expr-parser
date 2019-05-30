package com.settipalli.cronexprparser.exceptions;

public class InvalidRangeStartValueException extends RuntimeException {

    public InvalidRangeStartValueException(String message) {
        super(message);
    }

    public InvalidRangeStartValueException(String message, Throwable cause) {
        super(message, cause);
    }
}
