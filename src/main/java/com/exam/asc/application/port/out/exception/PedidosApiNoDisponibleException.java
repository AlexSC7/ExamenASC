package com.exam.asc.application.port.out.exception;

public class PedidosApiNoDisponibleException extends ApiExternaNoDisponibleException {
    public PedidosApiNoDisponibleException(String message, Throwable  ex) {
        super(message, ex);
    }
}
