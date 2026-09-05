package com.exam.asc.application.service;

import com.exam.asc.application.port.in.BuscarPedidosUseCase;
import com.exam.asc.application.port.in.CriteriosBusqueda;
import com.exam.asc.application.port.in.SincronizarPedidosClienteUseCase;
import com.exam.asc.application.port.out.ClienteRepositoryPort;
import com.exam.asc.application.port.out.PedidosApiPort;
import com.exam.asc.domain.model.Cliente;
import com.exam.asc.domain.model.Pedido;
import com.exam.asc.domain.model.PedidoConItems;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SincronizarPedidosClienteService implements SincronizarPedidosClienteUseCase {

    private final BuscarPedidosUseCase buscarPedidos;
    private final ClienteRepositoryPort clienteRepository;

    public SincronizarPedidosClienteService(BuscarPedidosUseCase buscarPedidos,
                                            ClienteRepositoryPort clienteRepository) {
        this.buscarPedidos = buscarPedidos;
        this.clienteRepository = clienteRepository;
    }

    public void sincronizar(String userId) {

        List<PedidoConItems> pedidos = buscarPedidos.buscarPedidosFiltrados(new CriteriosBusqueda(null, null, null, null));

        // Filtrar pedidos por userId
        List<PedidoConItems> pedidosUsuario = pedidos.stream()
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
