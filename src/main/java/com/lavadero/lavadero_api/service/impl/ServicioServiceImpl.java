package com.lavadero.lavadero_api.service.impl;

import com.lavadero.lavadero_api.dto.request.ServicioRequest;
import com.lavadero.lavadero_api.dto.response.ServicioResponse;
import com.lavadero.lavadero_api.exception.BusinessException;
import com.lavadero.lavadero_api.exception.ResourceNotFoundException;
import com.lavadero.lavadero_api.mapper.ServicioMapper;
import com.lavadero.lavadero_api.model.Servicio;
import com.lavadero.lavadero_api.repository.ServicioRepository;
import com.lavadero.lavadero_api.service.ServicioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ServicioServiceImpl implements ServicioService {

    private final ServicioRepository servicioRepository;
    private final ServicioMapper servicioMapper;

    @Override
    @Transactional
    public ServicioResponse crear(ServicioRequest request) {
        if (servicioRepository.existsByNombre(request.getNombre())) {
            throw new BusinessException("Ya existe un servicio con el nombre: " + request.getNombre());
        }
        Servicio servicio = Servicio.builder()
                .nombre(request.getNombre())
                .descripcion(request.getDescripcion())
                .precioBase(request.getPrecioBase())
                .activo(request.getActivo() != null ? request.getActivo() : true)
                .build();
        return servicioMapper.toResponse(servicioRepository.save(servicio));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ServicioResponse> listar(boolean soloActivos) {
        List<Servicio> servicios = soloActivos
                ? servicioRepository.findByActivoTrue()
                : servicioRepository.findAll();
        return servicios.stream().map(servicioMapper::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ServicioResponse obtener(Long id) {
        return servicioMapper.toResponse(buscarPorId(id));
    }

    @Override
    @Transactional
    public ServicioResponse actualizar(Long id, ServicioRequest request) {
        Servicio servicio = buscarPorId(id);
        if (!servicio.getNombre().equals(request.getNombre())
                && servicioRepository.existsByNombre(request.getNombre())) {
            throw new BusinessException("Ya existe un servicio con el nombre: " + request.getNombre());
        }
        servicio.setNombre(request.getNombre());
        servicio.setDescripcion(request.getDescripcion());
        servicio.setPrecioBase(request.getPrecioBase());
        if (request.getActivo() != null) servicio.setActivo(request.getActivo());
        return servicioMapper.toResponse(servicioRepository.save(servicio));
    }

    @Override
    @Transactional(readOnly = true)
    public Servicio buscarPorId(Long id) {
        return servicioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Servicio no encontrado con id: " + id));
    }
}
