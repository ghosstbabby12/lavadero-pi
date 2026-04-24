package com.lavadero.lavadero_api.controller;

import com.lavadero.lavadero_api.dto.request.PrecioServicioVehiculoRequest;
import com.lavadero.lavadero_api.dto.response.PrecioServicioVehiculoResponse;
import com.lavadero.lavadero_api.service.PrecioServicioVehiculoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/servicios/{servicioId}/precios")
@RequiredArgsConstructor
@Tag(name = "Precios por Tipo de Vehículo", description = "Gestión de precios especiales por tipo de vehículo")
public class PrecioServicioVehiculoController {

    private final PrecioServicioVehiculoService precioService;

    @PostMapping
    @Operation(summary = "Agregar precio especial para un tipo de vehículo")
    public ResponseEntity<PrecioServicioVehiculoResponse> crear(
            @PathVariable Long servicioId,
            @Valid @RequestBody PrecioServicioVehiculoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(precioService.crear(servicioId, request));
    }

    @GetMapping
    @Operation(summary = "Listar precios especiales de un servicio")
    public ResponseEntity<List<PrecioServicioVehiculoResponse>> listar(@PathVariable Long servicioId) {
        return ResponseEntity.ok(precioService.listarPorServicio(servicioId));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar precio especial")
    public ResponseEntity<PrecioServicioVehiculoResponse> actualizar(
            @PathVariable Long servicioId,
            @PathVariable Long id,
            @Valid @RequestBody PrecioServicioVehiculoRequest request) {
        return ResponseEntity.ok(precioService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar precio especial")
    public ResponseEntity<Void> eliminar(
            @PathVariable Long servicioId,
            @PathVariable Long id) {
        precioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
