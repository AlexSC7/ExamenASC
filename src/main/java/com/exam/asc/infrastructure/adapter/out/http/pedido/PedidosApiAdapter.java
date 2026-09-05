package com.exam.asc.infrastructure.adapter.out.http.pedido;

import com.exam.asc.application.port.out.PedidosApiPort;
import com.exam.asc.domain.model.Pedido;
import com.exam.asc.infrastructure.adapter.out.http.pedido.dto.PedidoApiResponse;
import com.exam.asc.infrastructure.adapter.out.http.pedido.exception.PedidosApiNoDisponibleException;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.util.List;

public class PedidosApiAdapter implements PedidosApiPort {

    private final RestClient pedidosRestClient;

    public PedidosApiAdapter(RestClient pedidosRestClient) {
        this.pedidosRestClient = pedidosRestClient;
    }

    @Override
    public List<Pedido> obtenerPedidos() {
        try {
            List<PedidoApiResponse> respuesta = pedidosRestClient.get()
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {
                    });

            return respuesta.stream()
                    .map(this::aDominio)
                    .toList();
        } catch (RestClientException ex) {
            throw new PedidosApiNoDisponibleException("No se pudo consultar el servicio de pedidos", ex);
        }
    }

    private Pedido aDominio(PedidoApiResponse dto) {
        return new Pedido(
                dto.orderRef(),
                dto.userId(),
                dto.canal(),
                dto.orderStatus(),
                dto.storeName(),
                dto.items()
        );
    }
}
