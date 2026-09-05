package com.exam.asc.infrastructure.adapter.out.http.item.exception;

import org.springframework.web.client.RestClientException;

public class ItemsApiNoDisponibleException extends RuntimeException {
    public ItemsApiNoDisponibleException(String message, RestClientException ex) {
        super(message);
    }
}
