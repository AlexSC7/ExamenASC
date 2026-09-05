package com.exam.asc.application.port.in;

import com.exam.asc.domain.model.Cliente;

import java.util.Optional;

public interface ConsultarClienteUseCase {
    Optional<Cliente> consultarCliente(String id);
}
