package com.exam.asc.domain.model;

import java.util.List;

public record PedidoConItems(
        String orderRef,
        String userId,
        String canal,
        String fechaEstimadaEntrega,
        String storeName,
        List<Item> items
) { }
