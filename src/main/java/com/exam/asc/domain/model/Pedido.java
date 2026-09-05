package com.exam.asc.domain.model;

import java.util.List;

public record Pedido(
        String orderRef,
        String userId,
        String canal,
        String fechaEstimadaEntrega,
        String storeName,
        List<String> items
) {

    public Pedido {
        if(orderRef == null || orderRef.isBlank()) {
            throw new IllegalArgumentException("orderRef no puede ser nulo o vacío");
        }
    }

}
