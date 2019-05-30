package com.settipalli.cronexprparser.exceptions;

public class UnknownFieldWhileAssemblingFinalResultException extends RuntimeException {

    public UnknownFieldWhileAssemblingFinalResultException(String message) {
        super(message);
    }

    public UnknownFieldWhileAssemblingFinalResultException(String message, Throwable cause) {
        super(message, cause);
    }
}
