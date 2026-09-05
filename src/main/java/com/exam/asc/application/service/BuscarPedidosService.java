package com.exam.asc.application.service;

import com.exam.asc.application.port.in.BuscarPedidosUseCase;
import com.exam.asc.application.port.in.CriteriosBusqueda;
import com.exam.asc.application.port.out.ItemsApiPort;
import com.exam.asc.application.port.out.PedidosApiPort;
import com.exam.asc.domain.model.Item;
import com.exam.asc.domain.model.Pedido;
import com.exam.asc.domain.model.PedidoConItems;
import com.exam.asc.domain.service.PedidoItemConciliador;
import com.exam.asc.domain.service.ToleranciaTextoService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BuscarPedidosService implements BuscarPedidosUseCase {
    private final PedidosApiPort pedidosApiPort;
    private final ItemsApiPort itemsApiPort;
    private final PedidoItemConciliador conciliador;
    private final ToleranciaTextoService toleranciaTextoService;

    public BuscarPedidosService(PedidosApiPort pedidosApiPort,
                                ItemsApiPort itemsApiPort,
                                PedidoItemConciliador conciliador,
                                ToleranciaTextoService toleranciaTextoService) {
        this.pedidosApiPort = pedidosApiPort;
        this.itemsApiPort = itemsApiPort;
        this.conciliador = conciliador;
        this.toleranciaTextoService = toleranciaTextoService;
    }

    @Override
    public List<PedidoConItems> buscarPedidosFiltrados(CriteriosBusqueda criterios) {
        List<Pedido> pedidos = pedidosApiPort.obtenerPedidos();
        List<Item> items = itemsApiPort.obtenerItems();

        List<PedidoConItems> conciliados = conciliador.conciliar(pedidos, items);

        //Se aplican filtros con criterios
        return conciliados.stream()
                .filter(pedido -> coincide(pedido.orderRef(), criterios.orderRef()))
                .filter(pedido -> coincide(pedido.fechaEstimadaEntrega(), criterios.orderStatus()))
                .filter(pedido -> coincide(pedido.storeName(), criterios.storeName()))
                .filter(pedido -> coincideDisplayName(pedido, criterios.displayName()))
                .toList();
    }

    private boolean coincide(String valorPedido, String valorCriterio) {
        if (valorCriterio == null || valorCriterio.isBlank()) {
            return true;
        }
        if (valorPedido == null) {
            return false;
        }
        //Aplicamos la tolerancia
        return toleranciaTextoService.coincidencia(valorPedido, valorCriterio);
    }


    private boolean coincideDisplayName(PedidoConItems pedido, String displayNameCriterio) {
        if (displayNameCriterio == null || displayNameCriterio.isBlank()) {
            return true;
        }
        if (pedido.items() == null || pedido.items().isEmpty()) {
            return false;
        }

        return pedido.items().stream()
                .anyMatch(item -> coincide(item.nombreProducto(), displayNameCriterio));
    }
}
