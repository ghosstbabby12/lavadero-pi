package com.lavadero.lavadero_api.controller;

import com.lavadero.lavadero_api.dto.request.CambioEstadoRequest;
import com.lavadero.lavadero_api.dto.request.DetalleOrdenRequest;
import com.lavadero.lavadero_api.dto.request.OrdenRequest;
import com.lavadero.lavadero_api.dto.response.OrdenResponse;
import com.lavadero.lavadero_api.model.enums.EstadoOrden;
import com.lavadero.lavadero_api.service.OrdenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ordenes")
@RequiredArgsConstructor
@Tag(name = "Órdenes de Servicio", description = "Gestión de órdenes de atención")
public class OrdenController {

    private final OrdenService ordenService;

    @PostMapping
    @Operation(summary = "Crear una nueva orden de servicio")
    public ResponseEntity<OrdenResponse> crear(@Valid @RequestBody OrdenRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ordenService.crear(request));
    }

    @GetMapping
    @Operation(summary = "Listar órdenes (opcionalmente filtrar por estado)")
    public ResponseEntity<List<OrdenResponse>> listar(
            @RequestParam(required = false) EstadoOrden estado) {
        return ResponseEntity.ok(ordenService.listar(estado));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener detalle completo de una orden")
    public ResponseEntity<OrdenResponse> obtenerDetalle(@PathVariable Long id) {
        return ResponseEntity.ok(ordenService.obtenerDetalle(id));
    }

    @PatchMapping("/{id}/estado")
    @Operation(summary = "Cambiar estado de una orden")
    public ResponseEntity<OrdenResponse> cambiarEstado(
            @PathVariable Long id,
            @Valid @RequestBody CambioEstadoRequest request) {
        return ResponseEntity.ok(ordenService.cambiarEstado(id, request));
    }

    @PostMapping("/{id}/servicios")
    @Operation(summary = "Agregar un servicio a la orden")
    public ResponseEntity<OrdenResponse> agregarServicio(
            @PathVariable Long id,
            @Valid @RequestBody DetalleOrdenRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ordenService.agregarDetalle(id, request));
    }

    @DeleteMapping("/{id}/servicios/{detalleId}")
    @Operation(summary = "Eliminar un servicio de la orden")
    public ResponseEntity<OrdenResponse> eliminarServicio(
            @PathVariable Long id,
            @PathVariable Long detalleId) {
        return ResponseEntity.ok(ordenService.eliminarDetalle(id, detalleId));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Cancelar una orden")
    public ResponseEntity<Void> cancelar(@PathVariable Long id) {
        ordenService.cancelar(id);
        return ResponseEntity.noContent().build();
    }
}
