package com.exam.asc.infrastructure.adapter.out.http.pedido;

import com.exam.asc.application.port.out.exception.PedidosApiNoDisponibleException;
import com.exam.asc.domain.model.Pedido;
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

class PedidosApiAdapterTest {

    private MockWebServer server;
    private PedidosApiAdapter adapter;

    @BeforeEach
    void setUp() throws IOException {
        server = new MockWebServer();
        server.start();
        RestClient restClient = RestClient.builder()
                .baseUrl(server.url("/").toString())
                .build();

        adapter = new PedidosApiAdapter(restClient);
    }

    @AfterEach
    void tearDown() {
        server.close();
    }

    @Test
    void obtenerPedidosCuandoUserIdEsNulo() throws Exception {
        // JSON con un pedido estándar y otro con userId nulo
        String jsonBody = """
                [
                    {
                            "orderRef": "13579",
                            "userId": "6b32",
                            "canal": "physical",
                            "orderStatus": "2025-12-08",
                            "marketPlace": false,
                            "giftRegistry": false,
                            "items": [
                                "20251216366900020031-1171500610",
                                "20251216366900020031-898"
                            ],
                            "storeName": "Liverpool Galerías Toluca",
                            "id": "2"
                        },
                    {
                            "orderRef": "12345",
                            "userId": null,
                            "canal": "physical",
                            "orderStatus": "2025-11-08",
                            "marketPlace": false,
                            "giftRegistry": false,
                            "items": [
                                "20251216366900020031-1171",
                                "20251216366900020031-898"
                            ],
                            "storeName": "Liverpool CDMX",
                            "id": "1"
                        }
                ]
                """;

        server.enqueue(new MockResponse(
                200,
                new Headers(new String[]{"Content-Type", "application/json"}),
                jsonBody));


        List<Pedido> resultado = adapter.obtenerPedidos();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());

        Pedido pedido1 = resultado.getFirst();
        assertEquals("13579", pedido1.orderRef());
        assertEquals("6b32", pedido1.userId());

        Pedido pedidoSinUser = resultado.get(1);
        assertEquals("12345", pedidoSinUser.orderRef());
        assertNull(pedidoSinUser.userId(), "El adapter debe tolerar userId con valor null");

        var recordedRequest = server.takeRequest();
        assertEquals("GET", recordedRequest.getMethod());
    }

    @Test
    void obtenerPedidosLanzaExcepcionCuandoApiRetornaError500() {
        server.enqueue(
                new MockResponse(
                        500,
                                        new Headers.Builder().build(),
                        ""
                                )
        );

        assertThrows(PedidosApiNoDisponibleException.class, () -> adapter.obtenerPedidos());
    }
}