package com.exam.asc.domain.model;

public record Item (
        String itemId,
        int cantidad,
        String nombreProducto
){
    public Item {
        if(itemId == null || itemId.isBlank()) {
            throw new IllegalArgumentException("itemId no puede ser nulo o vacío");
        }
    }
}
