package com.lavadero.lavadero_api.controller;

import com.lavadero.lavadero_api.dto.request.VehiculoRequest;
import com.lavadero.lavadero_api.dto.response.VehiculoResponse;
import com.lavadero.lavadero_api.service.VehiculoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehiculos")
@RequiredArgsConstructor
@Tag(name = "Vehículos", description = "Gestión de vehículos registrados")
public class VehiculoController {

    private final VehiculoService vehiculoService;

    @PostMapping
    @Operation(summary = "Registrar un nuevo vehículo")
    public ResponseEntity<VehiculoResponse> crear(@Valid @RequestBody VehiculoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(vehiculoService.crear(request));
    }

    @GetMapping
    @Operation(summary = "Listar todos los vehículos")
    public ResponseEntity<List<VehiculoResponse>> listar() {
        return ResponseEntity.ok(vehiculoService.listar());
    }

    @GetMapping("/cliente/{clienteId}")
    @Operation(summary = "Listar vehículos de un cliente")
    public ResponseEntity<List<VehiculoResponse>> listarPorCliente(@PathVariable Long clienteId) {
        return ResponseEntity.ok(vehiculoService.listarPorCliente(clienteId));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener vehículo por ID")
    public ResponseEntity<VehiculoResponse> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(vehiculoService.obtener(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar datos de un vehículo")
    public ResponseEntity<VehiculoResponse> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody VehiculoRequest request) {
        return ResponseEntity.ok(vehiculoService.actualizar(id, request));
    }
}
