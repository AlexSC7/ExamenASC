package com.exam.asc.infrastructure.adapter.out.http.item;

import com.exam.asc.application.port.out.exception.ItemsApiNoDisponibleException;
import com.exam.asc.domain.model.Item;
import mockwebserver3.MockResponse;
import mockwebserver3.MockWebServer;
import okhttp3.Headers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestClient;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ItemsApiAdapterTest {
    private MockWebServer server;
    private ItemsApiAdapter adapter;

    @BeforeEach
    void setUp() throws IOException {
        server = new MockWebServer();
        server.start();
        RestClient restClient = RestClient.builder()
                .baseUrl(server.url("/").toString())
                .build();

        adapter = new ItemsApiAdapter(restClient);
    }

    @AfterEach
    void tearDown() {
        server.close();
    }

    @Test
    void obtenerItemsExitoso() throws Exception {
        // JSON con un items
        String jsonBody = """
                [
                    {
                            "itemId": "3010091676",
                            "skuId": "12345",
                            "quantity": 3,
                            "displayName": "Pantalón Levi´s",
                            "deliveryStatus": "Compra en línea",
                            "id": "1"
                        },
                    {
                            "itemId": "1132351437",
                            "skuId": "13579",
                            "quantity": 1,
                            "displayName": "Pantalón AE",
                            "deliveryStatus": "Compra en línea",
                            "id": "2"
                        }
                ]
                """;

        server.enqueue(new MockResponse(
                200,
                new Headers(new String[]{"Content-Type", "application/json"}),
                jsonBody));


        List<Item> resultado = adapter.obtenerItems();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());

        Item item1 = resultado.getFirst();
        assertEquals("3010091676", item1.itemId());
        assertEquals("Pantalón Levi´s", item1.nombreProducto());
        assertEquals(3, item1.cantidad());

        Item item2 = resultado.get(1);
        assertEquals("1132351437", item2.itemId());
        assertEquals("Pantalón AE", item2.nombreProducto());
        assertEquals(1, item2.cantidad());

        var recordedRequest = server.takeRequest();
        assertEquals("GET", recordedRequest.getMethod());
    }

    @Test
    void obtenerItemsLanzaExcepcionCuandoApiRetornaError500() {
        server.enqueue(
                new MockResponse(
                        500,
                        new Headers.Builder().build(),
                        ""
                )
        );

        assertThrows(ItemsApiNoDisponibleException.class, () -> adapter.obtenerItems());
    }
}
