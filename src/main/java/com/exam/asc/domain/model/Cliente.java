package com.exam.asc.domain.model;

import java.util.ArrayList;
import java.util.List;

public record Cliente(
        String userId,
        String nombre,
        String apellidoPaterno,
        String apellidoMaterno,
        String correoElectronico,
        String direccion,
        List<PedidoConItems> ordenes
) {

    public Cliente {
        if(userId == null || userId.isBlank()) {
            throw new IllegalArgumentException("userId no puede ser nulo o vacío");
        }
    }

    public Cliente conOrdenes(List<PedidoConItems> nuevasOrdenes) {
        return new Cliente(
                userId,
                nombre,
                apellidoPaterno,
                apellidoMaterno,
                correoElectronico,
                direccion,
                nuevasOrdenes
        );
    }
}
