package com.lavadero.lavadero_api.service.impl;

import com.lavadero.lavadero_api.dto.request.ClienteRequest;
import com.lavadero.lavadero_api.dto.response.ClienteResponse;
import com.lavadero.lavadero_api.exception.BusinessException;
import com.lavadero.lavadero_api.exception.ResourceNotFoundException;
import com.lavadero.lavadero_api.mapper.ClienteMapper;
import com.lavadero.lavadero_api.model.Cliente;
import com.lavadero.lavadero_api.repository.ClienteRepository;
import com.lavadero.lavadero_api.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;
    private final ClienteMapper clienteMapper;

    @Override
    @Transactional
    public ClienteResponse crear(ClienteRequest request) {
        if (clienteRepository.existsByDocumento(request.getDocumento())) {
            throw new BusinessException("Ya existe un cliente con el documento: " + request.getDocumento());
        }
        Cliente cliente = Cliente.builder()
                .nombre(request.getNombre())
                .apellido(request.getApellido())
                .documento(request.getDocumento())
                .telefono(request.getTelefono())
                .email(request.getEmail())
                .build();
        return clienteMapper.toResponse(clienteRepository.save(cliente));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClienteResponse> listar() {
        return clienteRepository.findAll().stream().map(clienteMapper::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ClienteResponse obtener(Long id) {
        return clienteMapper.toResponse(buscarPorId(id));
    }

    @Override
    @Transactional
    public ClienteResponse actualizar(Long id, ClienteRequest request) {
        Cliente cliente = buscarPorId(id);
        if (!cliente.getDocumento().equals(request.getDocumento())
                && clienteRepository.existsByDocumento(request.getDocumento())) {
            throw new BusinessException("Ya existe un cliente con el documento: " + request.getDocumento());
        }
        cliente.setNombre(request.getNombre());
        cliente.setApellido(request.getApellido());
        cliente.setDocumento(request.getDocumento());
        cliente.setTelefono(request.getTelefono());
        cliente.setEmail(request.getEmail());
        return clienteMapper.toResponse(clienteRepository.save(cliente));
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        if (!clienteRepository.existsById(id)) {
            throw new ResourceNotFoundException("Cliente no encontrado con id: " + id);
        }
        clienteRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Cliente buscarPorId(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con id: " + id));
    }
}
