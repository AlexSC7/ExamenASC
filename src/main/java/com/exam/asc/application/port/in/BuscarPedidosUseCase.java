package com.exam.asc.application.port.in;

import com.exam.asc.domain.model.Pedido;
import com.exam.asc.domain.model.PedidoConItems;

import java.util.List;

public interface BuscarPedidosUseCase {

    List<PedidoConItems> buscarPedidosFiltrados(CriteriosBusqueda criterios);
}
