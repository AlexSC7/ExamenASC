package com.exam.asc.application.port.in;

import com.exam.asc.domain.model.Cliente;

public interface SincronizarPedidosClienteUseCase {
    Cliente sincronizar(String orderRef);
}
