package com.lavadero.lavadero_api.service.impl;

import com.lavadero.lavadero_api.dto.request.PrecioServicioVehiculoRequest;
import com.lavadero.lavadero_api.dto.response.PrecioServicioVehiculoResponse;
import com.lavadero.lavadero_api.exception.BusinessException;
import com.lavadero.lavadero_api.exception.ResourceNotFoundException;
import com.lavadero.lavadero_api.mapper.PrecioServicioVehiculoMapper;
import com.lavadero.lavadero_api.model.PrecioServicioVehiculo;
import com.lavadero.lavadero_api.model.Servicio;
import com.lavadero.lavadero_api.repository.PrecioServicioVehiculoRepository;
import com.lavadero.lavadero_api.service.PrecioServicioVehiculoService;
import com.lavadero.lavadero_api.service.ServicioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PrecioServicioVehiculoServiceImpl implements PrecioServicioVehiculoService {

    private final PrecioServicioVehiculoRepository precioRepository;
    private final ServicioService servicioService;                       // composición
    private final PrecioServicioVehiculoMapper precioMapper;

    @Override
    @Transactional
    public PrecioServicioVehiculoResponse crear(Long servicioId, PrecioServicioVehiculoRequest request) {
        Servicio servicio = servicioService.buscarPorId(servicioId);
        if (precioRepository.existsByServicioIdAndTipoVehiculo(servicioId, request.getTipoVehiculo())) {
            throw new BusinessException(
                    "Ya existe un precio especial para " + request.getTipoVehiculo()
                    + " en el servicio: " + servicio.getNombre());
        }
        PrecioServicioVehiculo precio = PrecioServicioVehiculo.builder()
                .servicio(servicio)
                .tipoVehiculo(request.getTipoVehiculo())
                .precio(request.getPrecio())
                .build();
        return precioMapper.toResponse(precioRepository.save(precio));
    }

    @Override
    @Transactional(readOnly = true)
    public List<PrecioServicioVehiculoResponse> listarPorServicio(Long servicioId) {
        servicioService.buscarPorId(servicioId);
        return precioRepository.findByServicioId(servicioId).stream().map(precioMapper::toResponse).toList();
    }

    @Override
    @Transactional
    public PrecioServicioVehiculoResponse actualizar(Long id, PrecioServicioVehiculoRequest request) {
        PrecioServicioVehiculo precio = buscarPorId(id);
        if (!precio.getTipoVehiculo().equals(request.getTipoVehiculo())
                && precioRepository.existsByServicioIdAndTipoVehiculo(
                        precio.getServicio().getId(), request.getTipoVehiculo())) {
            throw new BusinessException("Ya existe un precio para ese tipo de vehículo en este servicio");
        }
        precio.setTipoVehiculo(request.getTipoVehiculo());
        precio.setPrecio(request.getPrecio());
        return precioMapper.toResponse(precioRepository.save(precio));
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        if (!precioRepository.existsById(id)) {
            throw new ResourceNotFoundException("Precio especial no encontrado con id: " + id);
        }
        precioRepository.deleteById(id);
    }

    private PrecioServicioVehiculo buscarPorId(Long id) {
        return precioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Precio especial no encontrado con id: " + id));
    }
}
