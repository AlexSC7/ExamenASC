package com.exam.asc.service;

import com.exam.asc.application.port.in.CriteriosBusqueda;
import com.exam.asc.application.port.out.ItemsApiPort;
import com.exam.asc.application.port.out.PedidosApiPort;
import com.exam.asc.application.service.BuscarPedidosService;
import com.exam.asc.domain.model.Item;
import com.exam.asc.domain.model.Pedido;
import com.exam.asc.domain.model.PedidoConItems;
import com.exam.asc.domain.service.PedidoItemConciliador;
import com.exam.asc.domain.service.ToleranciaTextoService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class BuscarPedidosServiceTest {

    PedidosApiPort pedidosApiMock =
            Mockito.mock(PedidosApiPort.class);

    ItemsApiPort itemsApiMock =
            Mockito.mock(ItemsApiPort.class);

    PedidoItemConciliador conciliadorMock =
            Mockito.mock(PedidoItemConciliador.class);

    ToleranciaTextoService toleranciaTextoMock =
            Mockito.mock(ToleranciaTextoService.class);

    @Test
    void debeRetornarTodosLosPedidosCuandoNoHayFiltros() {

        Pedido pedido = pedidoDePrueba();
        Item item = itemDePrueba();

        PedidoConItems pedidoConItems =
                new PedidoConItems(
                        "id",
                        "user123",
                        "online",
                        "10/10/2026",
                        "Liv",
                        List.of(item)
                );

        when(pedidosApiMock.obtenerPedidos())
                .thenReturn(List.of(pedido));

        when(itemsApiMock.obtenerItems())
                .thenReturn(List.of(item));

        when(conciliadorMock.conciliar(
                List.of(pedido),
                List.of(item)
        )).thenReturn(List.of(pedidoConItems));

        BuscarPedidosService service =
                new BuscarPedidosService(
                        pedidosApiMock,
                        itemsApiMock,
                        conciliadorMock,
                        toleranciaTextoMock
                );

        List<PedidoConItems> resultado =
                service.buscarPedidosFiltrados(
                        new CriteriosBusqueda(null, null, null, null)
                );

        assertEquals(List.of(pedidoConItems), resultado);

        verify(pedidosApiMock).obtenerPedidos();
        verify(itemsApiMock).obtenerItems();
        verify(conciliadorMock).conciliar(
                List.of(pedido),
                List.of(item)
        );
    }


    @Test
    void debeRetornarVacioCuandoNoHayCoincidencias() {

        PedidoConItems pedido =
                new PedidoConItems(
                        "12345",
                        "user123",
                        "online",
                        "10/10/2026",
                        "Liv",
                        List.of()
                );

        configurarConciliacion(
                List.of(),
                List.of(),
                List.of(pedido)
        );

        when(toleranciaTextoMock.coincidencia(
                "Liv",
                "Amazon"
        )).thenReturn(false);

        BuscarPedidosService service =
                new BuscarPedidosService(
                        pedidosApiMock,
                        itemsApiMock,
                        conciliadorMock,
                        toleranciaTextoMock
                );

        List<PedidoConItems> resultado =
                service.buscarPedidosFiltrados(
                        new CriteriosBusqueda(
                                null,
                                null,
                                "Amazon",
                                null
                        )
                );

        assertTrue(resultado.isEmpty());
    }

    private void configurarConciliacion(
            List<Pedido> pedidos,
            List<Item> items,
            List<PedidoConItems> resultado) {

        when(pedidosApiMock.obtenerPedidos())
                .thenReturn(pedidos);

        when(itemsApiMock.obtenerItems())
                .thenReturn(items);

        when(conciliadorMock.conciliar(pedidos, items))
                .thenReturn(resultado);
    }

    private Pedido pedidoDePrueba() {
        return new Pedido(
                "id",
                "user123",
                "online",
                "10/10/2026",
                "Liv",
                List.of()
        );
    }

    private Item itemDePrueba() {
        return new Item(
                "id",
                1,
                "Pantalon"
        );
    }
}