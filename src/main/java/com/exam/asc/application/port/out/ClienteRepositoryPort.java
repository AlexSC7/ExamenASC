package com.exam.asc.application.port.out;

import com.exam.asc.domain.model.Cliente;

import java.util.Optional;

public interface ClienteRepositoryPort {

    Optional<Cliente> buscarPorId(String id);

    Cliente guardar(Cliente cliente);

    void actualizar(Cliente cliente);
}
