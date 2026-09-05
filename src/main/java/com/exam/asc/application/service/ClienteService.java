package com.exam.asc.application.service;

import com.exam.asc.application.port.in.ActualizarClienteUseCase;
import com.exam.asc.application.port.in.ConsultarClienteUseCase;
import com.exam.asc.application.port.in.RegistrarClienteUseCase;
import com.exam.asc.application.port.in.SincronizarPedidosClienteUseCase;
import com.exam.asc.application.port.out.ClienteRepositoryPort;
import com.exam.asc.domain.model.Cliente;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClienteService implements ActualizarClienteUseCase, ConsultarClienteUseCase, RegistrarClienteUseCase {

    private final ClienteRepositoryPort clienteRepository;
    private final SincronizarPedidosClienteUseCase sincronizarPedidos;

    public ClienteService(ClienteRepositoryPort clienteRepository,
                          SincronizarPedidosClienteUseCase sincronizarPedidos) {
        this.clienteRepository = clienteRepository;
        this.sincronizarPedidos = sincronizarPedidos;
    }

    @Override
    public void actualizarCliente(Cliente cliente) {
        clienteRepository.actualizar(cliente);
        sincronizarPedidos.sincronizar(cliente.userId());
    }

    @Override
    public Optional<Cliente> consultarCliente(String id) {
        return clienteRepository.buscarPorId(id);
    }

    @Override
    public Cliente crearCliente(Cliente cliente) {
        Cliente guardado = clienteRepository.guardar(cliente);
        sincronizarPedidos.sincronizar(guardado.userId());
        return guardado;
    }
}
