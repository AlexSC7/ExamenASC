package com.exam.asc.infrastructure.adapter.in.http;

import com.exam.asc.application.port.out.exception.ApiExternaNoDisponibleException;
import com.exam.asc.domain.exception.ClienteNoEncontradoException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


    @ExceptionHandler(ApiExternaNoDisponibleException.class)
    public ResponseEntity<ErrorResponse> handleApiExterna(ApiExternaNoDisponibleException ex,
                                                          HttpServletRequest request) {
        log.error("Servicio externo no disponible: {}", ex.getMessage(), ex);
        return construir(HttpStatus.SERVICE_UNAVAILABLE, "Servicio no disponible", ex.getMessage(), request);
    }

    @ExceptionHandler(ClienteNoEncontradoException.class)
    public ResponseEntity<ErrorResponse> handleClienteNoEncontrado(ClienteNoEncontradoException ex,
                                                                   HttpServletRequest request) {
        return construir(HttpStatus.NOT_FOUND, "Cliente no encontrado", ex.getMessage(), request);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(IllegalArgumentException ex,
                                                               HttpServletRequest request) {
        return construir(HttpStatus.BAD_REQUEST, "Solicitud inválida", ex.getMessage(), request);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidacion(MethodArgumentNotValidException ex,
                                                          HttpServletRequest request) {
        String detalle = ex.getBindingResult().getFieldErrors().stream()
                .map(fe -> fe.getField() + ": " + fe.getDefaultMessage())
                .collect(Collectors.joining("; "));
        return construir(HttpStatus.BAD_REQUEST, "Validación fallida", detalle, request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenerico(Exception ex, HttpServletRequest request) {
        log.error("Error no controlado", ex);
        return construir(HttpStatus.INTERNAL_SERVER_ERROR, "Error interno",
                "Ocurrió un error inesperado", request);
    }

    private ResponseEntity<ErrorResponse> construir(HttpStatus status, String error, String mensaje,
                                                    HttpServletRequest request) {
        ErrorResponse body = new ErrorResponse(Instant.now(), status.value(), error, mensaje,
                request.getRequestURI());
        return ResponseEntity.status(status).body(body);
    }
}
