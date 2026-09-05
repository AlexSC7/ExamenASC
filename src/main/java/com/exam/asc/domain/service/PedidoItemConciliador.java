package com.exam.asc.domain.service;

import com.exam.asc.domain.model.Item;
import com.exam.asc.domain.model.Pedido;
import com.exam.asc.domain.model.PedidoConItems;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class PedidoItemConciliador {

    public List<PedidoConItems> conciliar(List<Pedido> pedidos, List<Item> items) {

        // Creacion de map con llave de item id para conciliar en metodo facilmente
        // Si llegaran a coincidir dos llaves, se queda el que llego primero
        Map<String, Item> itemsPorId = items.stream()
                .collect(Collectors.toMap(
                        Item::itemId,
                        item -> item,
                        (itemExistente, itemNuevo) -> itemExistente
                ));

        return pedidos.stream()
                .map(pedido -> conciliarUno(pedido, itemsPorId))
                .toList();
    }

    private PedidoConItems conciliarUno(Pedido pedido, Map<String, Item> itemsPorId) {

        //Si no se llegara a encontrar un item de los pedidos este se setea nulo
        List<Item> itemsResueltos = pedido.items().stream()
                .map(itemsPorId::get)
                .filter(Objects::nonNull)
                .toList();

        return new PedidoConItems(
                pedido.orderRef(),
                pedido.userId(),
                pedido.canal(),
                pedido.fechaEstimadaEntrega(),
                pedido.storeName(),
                itemsResueltos
        );
    }
}
