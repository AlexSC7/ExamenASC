package com.exam.asc.infrastructure.adapter.out.http.pedido.dto;

import java.util.List;

public record PedidoApiResponse(
        String id,
        String orderRef,
        String userId,
        String canal,
        String orderStatus,
        String storeName,
        boolean marketPlace,
        boolean giftRegistry,
        List<String> items
) {}
