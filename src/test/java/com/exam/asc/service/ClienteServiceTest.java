package com.exam.asc.service;

import com.exam.asc.application.port.in.SincronizarPedidosClienteUseCase;
import com.exam.asc.application.port.out.ClienteRepositoryPort;
import com.exam.asc.application.service.ClienteService;
import com.exam.asc.domain.model.Cliente;
import com.exam.asc.domain.model.Item;
import com.exam.asc.domain.model.Pedido;
import com.exam.asc.domain.model.PedidoConItems;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class ClienteServiceTest {

    ClienteRepositoryPort repoFalso = Mockito.mock(ClienteRepositoryPort.class);
    SincronizarPedidosClienteUseCase sincronizarPedidos = Mockito.mock(SincronizarPedidosClienteUseCase.class);
    ClienteService service = new ClienteService(repoFalso, sincronizarPedidos);

    @Test
    void debeRegistrarUnCliente() {

        Cliente cliente = new Cliente(
                "id",
                "Juan",
                "Perez",
                "Lopez",
                "correo@correo",
                "calle",
                List.of(new PedidoConItems(
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
                )));
        when(repoFalso.guardar(cliente)).thenReturn(cliente);

        Cliente resultado = service.crearCliente(cliente);

        assertEquals("Juan", resultado.nombre());
    }

    @Test
    void debeConsultaUnClientePorId() {

        Cliente cliente = new Cliente(
                "id",
                "Juan",
                "Perez",
                "Lopez",
                "correo@correo",
                "calle",
                List.of(new PedidoConItems(
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
                )));

        when(repoFalso.buscarPorId("id")).thenReturn(Optional.of(cliente));

        Optional<Cliente> resultado = service.consultarCliente("id");

        assertEquals("Juan", resultado.<Object>map(Cliente::nombre).orElse(null));
    }



}
