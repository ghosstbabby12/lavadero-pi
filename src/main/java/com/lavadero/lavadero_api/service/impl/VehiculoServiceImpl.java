package com.lavadero.lavadero_api.service.impl;

import com.lavadero.lavadero_api.dto.request.VehiculoRequest;
import com.lavadero.lavadero_api.dto.response.VehiculoResponse;
import com.lavadero.lavadero_api.exception.BusinessException;
import com.lavadero.lavadero_api.exception.ResourceNotFoundException;
import com.lavadero.lavadero_api.mapper.VehiculoMapper;
import com.lavadero.lavadero_api.model.Cliente;
import com.lavadero.lavadero_api.model.Vehiculo;
import com.lavadero.lavadero_api.repository.VehiculoRepository;
import com.lavadero.lavadero_api.service.ClienteService;
import com.lavadero.lavadero_api.service.VehiculoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VehiculoServiceImpl implements VehiculoService {

    private final VehiculoRepository vehiculoRepository;
    private final ClienteService clienteService;        // composición: delega búsqueda de cliente
    private final VehiculoMapper vehiculoMapper;

    @Override
    @Transactional
    public VehiculoResponse crear(VehiculoRequest request) {
        if (vehiculoRepository.existsByPlaca(request.getPlaca())) {
            throw new BusinessException("Ya existe un vehículo con la placa: " + request.getPlaca());
        }
        Cliente cliente = clienteService.buscarPorId(request.getClienteId());
        Vehiculo vehiculo = Vehiculo.builder()
                .placa(request.getPlaca().toUpperCase())
                .marca(request.getMarca())
                .modelo(request.getModelo())
                .anio(request.getAnio())
                .color(request.getColor())
                .tipoVehiculo(request.getTipoVehiculo())
                .cliente(cliente)
                .build();
        return vehiculoMapper.toResponse(vehiculoRepository.save(vehiculo));
    }

    @Override
    @Transactional(readOnly = true)
    public List<VehiculoResponse> listar() {
        return vehiculoRepository.findAll().stream().map(vehiculoMapper::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<VehiculoResponse> listarPorCliente(Long clienteId) {
        clienteService.buscarPorId(clienteId);
        return vehiculoRepository.findByClienteId(clienteId).stream().map(vehiculoMapper::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public VehiculoResponse obtener(Long id) {
        return vehiculoMapper.toResponse(buscarPorId(id));
    }

    @Override
    @Transactional
    public VehiculoResponse actualizar(Long id, VehiculoRequest request) {
        Vehiculo vehiculo = buscarPorId(id);
        String placaNueva = request.getPlaca().toUpperCase();
        if (!vehiculo.getPlaca().equals(placaNueva)
                && vehiculoRepository.existsByPlaca(placaNueva)) {
            throw new BusinessException("Ya existe un vehículo con la placa: " + placaNueva);
        }
        Cliente cliente = clienteService.buscarPorId(request.getClienteId());
        vehiculo.setPlaca(placaNueva);
        vehiculo.setMarca(request.getMarca());
        vehiculo.setModelo(request.getModelo());
        vehiculo.setAnio(request.getAnio());
        vehiculo.setColor(request.getColor());
        vehiculo.setTipoVehiculo(request.getTipoVehiculo());
        vehiculo.setCliente(cliente);
        return vehiculoMapper.toResponse(vehiculoRepository.save(vehiculo));
    }

    @Override
    @Transactional(readOnly = true)
    public Vehiculo buscarPorId(Long id) {
        return vehiculoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehículo no encontrado con id: " + id));
    }
}
