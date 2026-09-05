package com.exam.asc.application.port.in;

import com.exam.asc.domain.model.Pedido;

import java.util.List;

public interface BuscarPedidosUseCase {

    List<Pedido> buscarPedidosFiltrados(String orderRef, String orderStatus, String storeName, String displayName);
}
