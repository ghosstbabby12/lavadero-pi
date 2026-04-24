package com.lavadero.lavadero_api.controller;

import com.lavadero.lavadero_api.dto.request.PagoRequest;
import com.lavadero.lavadero_api.dto.response.PagoResponse;
import com.lavadero.lavadero_api.service.PagoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ordenes/{ordenId}/pagos")
@RequiredArgsConstructor
@Tag(name = "Pagos", description = "Registro de pagos por orden de servicio")
public class PagoController {

    private final PagoService pagoService;

    @PostMapping
    @Operation(summary = "Registrar un pago para una orden")
    public ResponseEntity<PagoResponse> registrar(
            @PathVariable Long ordenId,
            @Valid @RequestBody PagoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(pagoService.registrar(ordenId, request));
    }

    @GetMapping
    @Operation(summary = "Listar pagos de una orden")
    public ResponseEntity<List<PagoResponse>> listar(@PathVariable Long ordenId) {
        return ResponseEntity.ok(pagoService.listarPorOrden(ordenId));
    }
}
