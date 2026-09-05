package com.exam.asc.application.service;

import com.exam.asc.application.port.in.SincronizarPedidosClienteUseCase;
import com.exam.asc.application.port.out.ClienteRepositoryPort;
import com.exam.asc.application.port.out.PedidosApiPort;
import com.exam.asc.domain.model.Cliente;
import com.exam.asc.domain.model.Pedido;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SincronizarPedidosClienteService implements SincronizarPedidosClienteUseCase {

    private final PedidosApiPort pedidosApiPort;
    private final ClienteRepositoryPort clienteRepository;

    public SincronizarPedidosClienteService(PedidosApiPort pedidosApiPort,
                                            ClienteRepositoryPort clienteRepository) {
        this.pedidosApiPort = pedidosApiPort;
        this.clienteRepository = clienteRepository;
    }

    public void sincronizar(String userId) {

        List<Pedido> pedidos = pedidosApiPort.obtenerPedidos();
        // Filtrar pedidos por userId
        List<Pedido> pedidosUsuario = pedidos.stream()
                .filter(pedido -> pedido.userId().equals(userId))
                .toList();

        // Busca al cliente por userId para actualizarlo
        Optional<Cliente> cliente = clienteRepository.buscarPorId(userId);

        // Actualiza campo `orders`
        cliente.ifPresent(c -> {
            c.ordenes().clear();
            c.ordenes().addAll(pedidosUsuario);
            clienteRepository.actualizar(c);
        });
    }
}
