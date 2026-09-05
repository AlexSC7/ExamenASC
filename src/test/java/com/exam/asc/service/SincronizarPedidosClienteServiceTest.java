package com.exam.asc.service;

import com.exam.asc.application.port.in.BuscarPedidosUseCase;
import com.exam.asc.application.port.in.CriteriosBusqueda;
import com.exam.asc.application.port.out.ClienteRepositoryPort;
import com.exam.asc.application.port.out.PedidosApiPort;
import com.exam.asc.application.service.SincronizarPedidosClienteService;
import com.exam.asc.domain.model.Cliente;
import com.exam.asc.domain.model.Item;
import com.exam.asc.domain.model.Pedido;
import com.exam.asc.domain.model.PedidoConItems;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SincronizarPedidosClienteServiceTest {
    BuscarPedidosUseCase buscarPedidosMock = Mockito.mock(BuscarPedidosUseCase.class);
    ClienteRepositoryPort clienteRepoMock = Mockito.mock(ClienteRepositoryPort.class);

    @Test
    void debeSincronizarSoloLosPedidosDelUsuario() {


        when(buscarPedidosMock.buscarPedidosFiltrados(
                new CriteriosBusqueda(null, null, null, null)))
                .thenReturn(List.of(new PedidoConItems(
                        "id",
                        "user-123",
                        "online",
                        "10/10/2026",
                        "Liv",
                        List.of(new Item(
                                "id",
                                1,
                                "pantalon"
                        )))));

        when(clienteRepoMock.buscarPorId("user-123"))
                .thenReturn(Optional.of(clienteDePrueba()));

        SincronizarPedidosClienteService service =
                new SincronizarPedidosClienteService(buscarPedidosMock, clienteRepoMock);

        service.sincronizar("user-123");

        verify(clienteRepoMock).actualizar(argThat(cliente ->
                cliente.ordenes().contains(new PedidoConItems(
                        "id",
                        "user-123",
                        "online",
                        "10/10/2026",
                        "Liv",
                        List.of(new Item(
                                "id",
                                1,
                                "pantalon"
                        ))))
                        && cliente.ordenes().size() == 1
        ));


    }

    Cliente clienteDePrueba() {
        return new Cliente(
                "user-123",
                "Juan",
                "Perez",
                "Lopez",
                "correo@correo",
                "calle",
                new ArrayList<>(List.of(
                        new Pedido(
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
                        )
                ))
        );
    }
}
