package com.exam.asc.application.port.out.exception;

public class ItemsApiNoDisponibleException extends ApiExternaNoDisponibleException {
    public ItemsApiNoDisponibleException(String message, Throwable ex) {
        super(message, ex);
    }
}
