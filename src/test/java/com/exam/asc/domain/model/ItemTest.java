package com.exam.asc.domain.model;

import com.exam.asc.domain.model.Item;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ItemTest {

    @Test
    void noDebePermitirItemConIdVacio() {
        assertThrows(IllegalArgumentException.class,
                () -> new Item(
                                "",
                                1,
                                "pantalon"
                        ));
    }

    @Test
    void noDebePermitirItemConIdNulo() {
        assertThrows(IllegalArgumentException.class,
                () -> new Item(
                                null,
                                1,
                                "pantalon"
                        ));
    }
}
