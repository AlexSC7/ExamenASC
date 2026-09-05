package com.exam.asc.infrastructure.adapter.out.http.item;

import com.exam.asc.application.port.out.ItemApiPort;
import com.exam.asc.application.port.out.PedidosApiPort;
import com.exam.asc.domain.model.Item;
import com.exam.asc.domain.model.Pedido;
import com.exam.asc.infrastructure.adapter.out.http.item.dto.ItemApiResponse;
import com.exam.asc.infrastructure.adapter.out.http.item.exception.ItemsApiNoDisponibleException;
import com.exam.asc.infrastructure.adapter.out.http.pedido.dto.PedidoApiResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.util.List;

public class ItemsApiAdapter implements ItemApiPort {

    private final RestClient itemsRestClient;

    public ItemsApiAdapter(RestClient itemsRestClient) {
        this.itemsRestClient = itemsRestClient;
    }

    @Override
    public List<Item> obtenerItems() {
        try {
            List<ItemApiResponse> respuesta = itemsRestClient.get()
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {
                    });

            return respuesta.stream()
                    .map(this::aDominio)
                    .toList();
        } catch (RestClientException ex) {
            throw new ItemsApiNoDisponibleException("No se pudo consultar el servicio de pedidos", ex);
        }
    }

    private Item aDominio(ItemApiResponse dto) {
        return new Item(
                dto.itemId(),
                dto.quantity(),
                dto.displayName()
        );
    }
}
