package com.exam.asc.domain.model;

import java.util.List;

public record Cliente(
        String userId,
        String nombre,
        String apellidoPaterno,
        String apellidoMaterno,
        String correoElectronico,
        String direccion,
        List<Pedido> ordenes
) {

    public Cliente {
        if(userId == null || userId.isBlank()) {
            throw new IllegalArgumentException("userId no puede ser nulo o vacío");
        }
    }
}
