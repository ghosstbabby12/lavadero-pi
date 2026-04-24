package com.lavadero.lavadero_api.exception;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class ErrorResponse {
    private int status;
    private String error;
    private String mensaje;
    private List<String> detalles;
    private LocalDateTime timestamp;
}
