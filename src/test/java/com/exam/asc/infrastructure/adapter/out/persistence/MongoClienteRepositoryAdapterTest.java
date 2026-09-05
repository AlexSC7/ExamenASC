package com.exam.asc.infrastructure.adapter.out.persistence;

import com.exam.asc.domain.model.Cliente;
import com.exam.asc.domain.model.Item;
import com.exam.asc.domain.model.Pedido;
import com.exam.asc.infrastructure.adapter.out.persistence.document.ClienteDocument;
import com.exam.asc.infrastructure.adapter.out.persistence.mapper.ClienteDocumentMapper;
import com.exam.asc.infrastructure.adapter.out.persistence.repository.ClienteMongoRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class MongoClienteRepositoryAdapterTest {

    ClienteMongoRepository repoFalso = Mockito.mock(ClienteMongoRepository.class);
    ClienteDocumentMapper mapperFalso = Mockito.mock(ClienteDocumentMapper.class);
    MongoClienteRepositoryAdapter adapter = new MongoClienteRepositoryAdapter(repoFalso, mapperFalso);

    @Test
    void debeGuardarUnCliente() {
        Cliente cliente = new Cliente(
                "id",
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
                )));

        ClienteDocument documento = new ClienteDocument();

        when(mapperFalso.aDocumento(cliente)).thenReturn(documento);
        when(repoFalso.save(documento)).thenReturn(documento);
        when(mapperFalso.aDominio(documento)).thenReturn(cliente);

        Cliente resultado = adapter.guardar(cliente);

        assertEquals("Juan", resultado.nombre());
    }

    @Test
    void debeBuscarUnClientePorId() {
        Cliente cliente = new Cliente(
                "id",
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
                )));

        ClienteDocument documento = new ClienteDocument();

        when(repoFalso.findById("id")).thenReturn(Optional.of(documento));
        when(mapperFalso.aDominio(documento)).thenReturn(cliente);

        Optional<Cliente> resultado = adapter.buscarPorId("id");

        assertEquals("Juan", resultado.map(Cliente::nombre).orElse(null));
    }

    @Test
    void debeActualizarUnCliente() {
        Cliente cliente = new Cliente(
                "id",
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
                )));

        ClienteDocument documento = new ClienteDocument();

        when(mapperFalso.aDocumento(cliente)).thenReturn(documento);
        when(repoFalso.save(documento)).thenReturn(documento);
        when(mapperFalso.aDominio(documento)).thenReturn(cliente);

        Cliente resultado = adapter.actualizar(cliente);

        assertEquals("Juan", resultado.nombre());
    }
}