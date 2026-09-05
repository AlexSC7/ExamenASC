package com.exam.asc.infrastructure.adapter.in.http.dto;

public record ClienteRequest(
        String userId,
        String nombre,
        String apellidoPaterno,
        String apellidoMaterno,
        String correoElectronico,
        String direccion
) {}
