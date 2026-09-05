package com.exam.asc.infrastructure.adapter.out.persistence;

import com.exam.asc.application.port.out.ClienteRepositoryPort;
import com.exam.asc.domain.model.Cliente;
import com.exam.asc.infrastructure.adapter.out.persistence.mapper.ClienteDocumentMapper;
import com.exam.asc.infrastructure.adapter.out.persistence.repository.ClienteMongoRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class MongoClienteRepositoryAdapter implements ClienteRepositoryPort {

    private final ClienteMongoRepository mongoRepository;
    private final ClienteDocumentMapper mapper;

    public MongoClienteRepositoryAdapter(ClienteMongoRepository mongoRepository,
                                         ClienteDocumentMapper mapper) {
        this.mongoRepository = mongoRepository;
        this.mapper = mapper;
    }

    @Override
    public Cliente guardar(Cliente cliente) {
        return mapper.aDominio(mongoRepository.save(mapper.aDocumento(cliente)));
    }

    @Override
    public Optional<Cliente> buscarPorId(String userId) {
        return mongoRepository.findById(userId).map(mapper::aDominio);
    }

    @Override
    public Cliente actualizar(Cliente cliente) {
        return guardar(cliente);
    }

}
