package com.exam.asc.application.port.out.exception;

import org.springframework.web.client.RestClientException;

public class PedidosApiNoDisponibleException extends ApiExternaNoDisponibleException {
    public PedidosApiNoDisponibleException(String message, Throwable  ex) {
        super(message, ex);
    }
}
