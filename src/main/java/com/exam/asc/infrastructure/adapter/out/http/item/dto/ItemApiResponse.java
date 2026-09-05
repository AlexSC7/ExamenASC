package com.exam.asc.infrastructure.adapter.out.http.item.dto;

public record ItemApiResponse(
        String id,
        String itemId,
        String skuId,
        int quantity,
        String displayName,
        String deliveryStatus
) {}
