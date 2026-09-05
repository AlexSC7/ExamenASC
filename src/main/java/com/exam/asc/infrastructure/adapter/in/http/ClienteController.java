package com.exam.asc.infrastructure.adapter.in.http;

import com.exam.asc.application.port.in.ActualizarClienteUseCase;
import com.exam.asc.application.port.in.ConsultarClienteUseCase;
import com.exam.asc.application.port.in.RegistrarClienteUseCase;
import com.exam.asc.domain.model.Cliente;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/clientes")
@Tag(name = "Clientes", description = "API para la gestión y sincronización de clientes")
public class ClienteController {

    private final RegistrarClienteUseCase registrarClienteUseCase;
    private final ConsultarClienteUseCase consultarClienteUseCase;
    private final ActualizarClienteUseCase actualizarClienteUseCase;

    public ClienteController(RegistrarClienteUseCase registrarClienteUseCase,
                             ConsultarClienteUseCase consultarClienteUseCase,
                             ActualizarClienteUseCase actualizarClienteUseCase) {
        this.registrarClienteUseCase = registrarClienteUseCase;
        this.consultarClienteUseCase = consultarClienteUseCase;
        this.actualizarClienteUseCase = actualizarClienteUseCase;
    }

    @PostMapping
    @Operation(
            summary = "Registrar un nuevo cliente",
            description = "Crea un cliente en base de datos sincronizando sus pedidos.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Cliente creado exitosamente")
            }
    )
    public ResponseEntity<Cliente> crearCliente(@RequestBody Cliente cliente) {
        Cliente clienteGuardado = registrarClienteUseCase.crearCliente(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteGuardado);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Consultar un cliente por ID",
            description = "Busca un cliente por su identificador (userId).",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
                    @ApiResponse(responseCode = "404", description = "Cliente no encontrado", content = @Content(schema = @Schema(hidden = true)))
            }
    )
    public ResponseEntity<Cliente> consultarCliente(
            @Parameter(description = "ID del cliente") @PathVariable("id") String id) {

        return consultarClienteUseCase.consultarCliente(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Actualizar un cliente",
            description = "Actualiza los datos de un cliente existente y resincroniza sus pedidos.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Cliente actualizado exitosamente")
            }
    )
    public ResponseEntity<Cliente> actualizarCliente(
            @Parameter(description = "ID único del cliente") @PathVariable("id") String id,
            @RequestBody Cliente cliente) {

        if (!id.equals(cliente.userId())) {
            return ResponseEntity.badRequest().build();
        }

        Cliente clienteActualizado = actualizarClienteUseCase.actualizarCliente(cliente);
        return ResponseEntity.ok(clienteActualizado);
    }
}