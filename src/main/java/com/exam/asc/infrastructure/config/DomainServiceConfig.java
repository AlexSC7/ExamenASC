package com.exam.asc.infrastructure.config;

import com.exam.asc.application.port.out.ItemsApiPort;
import com.exam.asc.application.port.out.PedidosApiPort;
import com.exam.asc.application.service.BuscarPedidosService;
import com.exam.asc.domain.service.PedidoItemConciliador;
import com.exam.asc.domain.service.ToleranciaTextoService;
import org.springframework.context.annotation.Bean;

public class DomainServiceConfig {

    @Bean
    public ToleranciaTextoService toleranciaTextoService() {
        return new ToleranciaTextoService();
    }

    @Bean
    public PedidoItemConciliador pedidoItemConciliador() {
        return new PedidoItemConciliador();
    }

    @Bean
    public BuscarPedidosService buscarPedidosService(
            PedidosApiPort pedidosApiPort,
            ItemsApiPort itemsApiPort,
            PedidoItemConciliador conciliador,
            ToleranciaTextoService toleranciaTextoService) {

        return new BuscarPedidosService(
                pedidosApiPort,
                itemsApiPort,
                conciliador,
                toleranciaTextoService
        );
    }
}
