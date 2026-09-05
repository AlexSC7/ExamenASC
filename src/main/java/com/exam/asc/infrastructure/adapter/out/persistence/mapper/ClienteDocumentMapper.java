package com.exam.asc.infrastructure.adapter.out.persistence.mapper;

import com.exam.asc.domain.model.Cliente;
import com.exam.asc.infrastructure.adapter.out.persistence.document.ClienteDocument;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClienteDocumentMapper {
    ClienteDocument aDocumento(Cliente cliente);
    Cliente aDominio(ClienteDocument documento);
}
