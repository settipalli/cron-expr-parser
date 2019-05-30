package com.settipalli.cronexprparser.exceptions;

public class RangeStartValueGreaterThanStopValueException extends RuntimeException {

    public RangeStartValueGreaterThanStopValueException(String message) {
        super(message);
    }

    public RangeStartValueGreaterThanStopValueException(String message, Throwable cause) {
        super(message, cause);
    }
}
