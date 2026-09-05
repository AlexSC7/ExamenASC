package com.exam.asc.infrastructure.adapter.in.http;

import com.exam.asc.application.port.in.CriteriosBusqueda;
import com.exam.asc.application.service.BuscarPedidosService;
import com.exam.asc.domain.model.PedidoConItems;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pedidos")
@Tag(name = "Pedidos", description = "API para la consulta de pedidos con criterios de busqueda")
public class PedidoController {

    private final BuscarPedidosService buscarPedidosService;

    public PedidoController(BuscarPedidosService buscarPedidosService) {
        this.buscarPedidosService = buscarPedidosService;
    }

    @GetMapping("/buscar")
    @Operation(
            summary = "Buscar pedidos",
            description = "Retorna una lista de pedidos con sus items aplicando filtros opcionales de búsqueda.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Búsqueda exitosa"),
                    @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                            content = @Content(schema = @Schema(hidden = true)))
            }
    )
    public ResponseEntity<List<PedidoConItems>> buscarPedidos(
            @Parameter(description = "Referencia del pedido") @RequestParam(required = false) String orderRef,
            @Parameter(description = "Estado actual (fecha estimada) del pedido") @RequestParam(required = false) String orderStatus,
            @Parameter(description = "Nombre de la tienda") @RequestParam(required = false) String storeName,
            @Parameter(description = "Nombre del producto en pedido") @RequestParam(required = false) String displayName
    ) {
        CriteriosBusqueda criterios = new CriteriosBusqueda(orderRef, orderStatus, storeName, displayName);
        List<PedidoConItems> resultado = buscarPedidosService.buscarPedidosFiltrados(criterios);
        return ResponseEntity.ok(resultado);
    }
}
