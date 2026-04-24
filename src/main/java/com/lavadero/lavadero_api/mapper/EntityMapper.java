package com.lavadero.lavadero_api.mapper;

/**
 * Contrato genérico de mapeo de entidad a DTO de respuesta.
 * Todas las implementaciones son @Component y se inyectan por composición.
 */
public interface EntityMapper<E, R> {
    R toResponse(E entity);
}
