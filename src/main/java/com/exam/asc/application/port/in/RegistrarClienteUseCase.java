package com.exam.asc.application.port.in;

import com.exam.asc.domain.model.Cliente;

public interface RegistrarClienteUseCase {

    Cliente crearCliente(Cliente cliente);
}
