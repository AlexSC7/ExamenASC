package com.exam.asc.application.port.out.exception;

import org.springframework.web.client.RestClientException;

public class ItemsApiNoDisponibleException extends ApiExternaNoDisponibleException {
    public ItemsApiNoDisponibleException(String message, Throwable ex) {
        super(message, ex);
    }
}
