package com.exam.asc.infrastructure.adapter.out.persistence.document;

import com.exam.asc.domain.model.PedidoConItems;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "clientes")
@Data
public class ClienteDocument {

    @Id
    private String userId;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String correoElectronico;
    private String direccion;
    private List<PedidoConItems> ordenes;
}