package com.exam.asc.application.port.out;

import com.exam.asc.domain.model.Pedido;

import java.util.List;

public interface PedidosApiPort {

    List<Pedido> obtenerPedidos();
}
