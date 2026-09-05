package com.exam.asc.application.service;

import com.exam.asc.application.port.in.BuscarPedidosUseCase;
import com.exam.asc.application.port.in.CriteriosBusqueda;
import com.exam.asc.application.port.in.SincronizarPedidosClienteUseCase;
import com.exam.asc.application.port.out.ClienteRepositoryPort;
import com.exam.asc.application.port.out.exception.ApiExternaNoDisponibleException;
import com.exam.asc.domain.model.Cliente;
import com.exam.asc.domain.model.PedidoConItems;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class SincronizarPedidosClienteService implements SincronizarPedidosClienteUseCase {

    private final BuscarPedidosUseCase buscarPedidos;
    private final ClienteRepositoryPort clienteRepository;

    public SincronizarPedidosClienteService(BuscarPedidosUseCase buscarPedidos,
                                            ClienteRepositoryPort clienteRepository) {
        this.buscarPedidos = buscarPedidos;
        this.clienteRepository = clienteRepository;
    }

    public Cliente sincronizar(String userId) {
        List<PedidoConItems> pedidos;
        try{
            pedidos = buscarPedidos.buscarPedidosFiltrados(
                    new CriteriosBusqueda(null, null, null, null))
                    .stream()
                    .filter(pedido -> pedido.userId().equals(userId))
                    .toList();;

        } catch (ApiExternaNoDisponibleException ex) {
            log.warn("No se pudo sincronizar pedidos del cliente {}: {}", userId, ex.getMessage());
            return null;
        }

        return clienteRepository.buscarPorId(userId)
                .map(cliente -> {
                    Cliente actualizado = cliente.conOrdenes(pedidos);
                    return clienteRepository.actualizar(actualizado);
                })
                .orElse(null);
    }
}
