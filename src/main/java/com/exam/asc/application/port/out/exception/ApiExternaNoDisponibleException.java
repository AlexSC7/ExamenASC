package com.exam.asc.application.port.out.exception;

public abstract class ApiExternaNoDisponibleException extends RuntimeException {
    protected ApiExternaNoDisponibleException(String message, Throwable cause) {
        super(message, cause);
    }
}
