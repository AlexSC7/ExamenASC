package com.exam.asc.application.port.in;

public record CriteriosBusqueda(
        String orderRef,
        String orderStatus,
        String storeName,
        String displayName
) {}