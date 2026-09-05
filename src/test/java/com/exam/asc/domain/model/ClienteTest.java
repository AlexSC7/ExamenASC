package com.exam.asc.domain.model;

import com.exam.asc.domain.model.Cliente;
import com.exam.asc.domain.model.Item;
import com.exam.asc.domain.model.Pedido;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ClienteTest {

    @Test
    void noDebePermitirClienteConIdVacio() {
        assertThrows(IllegalArgumentException.class,
                () -> new Cliente(
                        "",
                        "Juan",
                        "Perez",
                        "Lopez",
                        "correo@correo",
                        "calle",
                        List.of(new Pedido(
                                "id",
                                "id",
                                "online",
                                "10/10/2026",
                                "Liv",
                                List.of(new Item(
                                        "id",
                                        1,
                                        "pantalon"
                                ))
                        ))));
    }

    @Test
    void noDebePermitirClienteConIdNulo() {
        assertThrows(IllegalArgumentException.class,
                () -> new Cliente(
                        null,
                        "Juan",
                        "Perez",
                        "Lopez",
                        "correo@correo",
                        "calle",
                        List.of(new Pedido(
                                "id",
                                "id",
                                "online",
                                "10/10/2026",
                                "Liv",
                                List.of(new Item(
                                        "id",
                                        1,
                                        "pantalon"
                                ))
                        ))));
    }
}
