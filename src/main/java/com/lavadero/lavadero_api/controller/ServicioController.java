package com.lavadero.lavadero_api.controller;

import com.lavadero.lavadero_api.dto.request.ServicioRequest;
import com.lavadero.lavadero_api.dto.response.ServicioResponse;
import com.lavadero.lavadero_api.service.ServicioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/servicios")
@RequiredArgsConstructor
@Tag(name = "Servicios", description = "Catálogo de servicios del lavadero")
public class ServicioController {

    private final ServicioService servicioService;

    @PostMapping
    @Operation(summary = "Crear un nuevo servicio")
    public ResponseEntity<ServicioResponse> crear(@Valid @RequestBody ServicioRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(servicioService.crear(request));
    }

    @GetMapping
    @Operation(summary = "Listar servicios (activos o todos)")
    public ResponseEntity<List<ServicioResponse>> listar(
            @RequestParam(defaultValue = "true") boolean soloActivos) {
        return ResponseEntity.ok(servicioService.listar(soloActivos));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener servicio por ID")
    public ResponseEntity<ServicioResponse> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(servicioService.obtener(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un servicio")
    public ResponseEntity<ServicioResponse> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody ServicioRequest request) {
        return ResponseEntity.ok(servicioService.actualizar(id, request));
    }
}
