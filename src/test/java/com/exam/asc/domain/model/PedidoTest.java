package com.exam.asc.domain.model;

import com.exam.asc.domain.model.Item;
import com.exam.asc.domain.model.Pedido;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class PedidoTest {

    @Test
    void noDebePermitirPedidoConIdVacio() {
        assertThrows(IllegalArgumentException.class,
                () -> new Pedido(
                        "",
                        "id",
                        "online",
                        "10/10/2026",
                        "Liv",
                        List.of(new Item(
                                "id",
                                1,
                                "pantalon"
                        ))));
    }

    @Test
    void noDebePermitirPedidoConIdNulo() {
        assertThrows(IllegalArgumentException.class,
                () -> new Pedido(
                null,
                        "id",
                "online",
                "10/10/2026",
                "Liv",
                List.of(new Item(
                        "id",
                        1,
                        "pantalon"
                ))));
    }
}
