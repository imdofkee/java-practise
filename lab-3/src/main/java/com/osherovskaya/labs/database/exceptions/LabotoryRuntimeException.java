package com.osherovskaya.labs.database.exceptions;

public class LabotoryRuntimeException extends RuntimeException {
    public LabotoryRuntimeException (String message, Throwable cause) {
        super(message,
                cause);
    }
}
