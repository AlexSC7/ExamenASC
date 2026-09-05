package com.exam.asc.infrastructure.adapter.out.http.pedido.exception;

import org.springframework.web.client.RestClientException;

public class PedidosApiNoDisponibleException extends RuntimeException {
    public PedidosApiNoDisponibleException(String message, RestClientException ex) {
        super(message);
    }
}
