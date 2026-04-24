package com.lavadero.lavadero_api.service.impl;

import com.lavadero.lavadero_api.dto.request.CambioEstadoRequest;
import com.lavadero.lavadero_api.dto.request.DetalleOrdenRequest;
import com.lavadero.lavadero_api.dto.request.OrdenRequest;
import com.lavadero.lavadero_api.dto.response.OrdenResponse;
import com.lavadero.lavadero_api.exception.BusinessException;
import com.lavadero.lavadero_api.exception.ResourceNotFoundException;
import com.lavadero.lavadero_api.mapper.OrdenMapper;
import com.lavadero.lavadero_api.model.DetalleOrden;
import com.lavadero.lavadero_api.model.OrdenServicio;
import com.lavadero.lavadero_api.model.Servicio;
import com.lavadero.lavadero_api.model.Vehiculo;
import com.lavadero.lavadero_api.model.enums.EstadoOrden;
import com.lavadero.lavadero_api.orden.OrdenEstadoMachine;
import com.lavadero.lavadero_api.precio.PrecioResolutionStrategy;
import com.lavadero.lavadero_api.repository.DetalleOrdenRepository;
import com.lavadero.lavadero_api.repository.OrdenRepository;
import com.lavadero.lavadero_api.service.OrdenService;
import com.lavadero.lavadero_api.service.ServicioService;
import com.lavadero.lavadero_api.service.VehiculoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrdenServiceImpl implements OrdenService {

    private final OrdenRepository ordenRepository;
    private final DetalleOrdenRepository detalleOrdenRepository;

    // Composición con interfaces — cada colaborador puede ser reemplazado
    private final VehiculoService vehiculoService;
    private final ServicioService servicioService;

    // Delegación: lógica de estados encapsulada en su propio componente
    private final OrdenEstadoMachine estadoMachine;

    // Delegación: resolución de precio encapsulada en su propia estrategia
    private final PrecioResolutionStrategy precioStrategy;

    // Delegación: mapeo de entidad encapsulado en OrdenMapper (compuesto de sub-mappers)
    private final OrdenMapper ordenMapper;

    @Override
    @Transactional
    public OrdenResponse crear(OrdenRequest request) {
        Vehiculo vehiculo = vehiculoService.buscarPorId(request.getVehiculoId());
        OrdenServicio orden = OrdenServicio.builder()
                .vehiculo(vehiculo)
                .observaciones(request.getObservaciones())
                .build();
        return ordenMapper.toResponse(ordenRepository.save(orden));
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrdenResponse> listar(EstadoOrden estado) {
        List<OrdenServicio> ordenes = estado != null
                ? ordenRepository.findByEstadoWithVehiculo(estado)
                : ordenRepository.findAllWithVehiculo();
        return ordenes.stream().map(ordenMapper::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public OrdenResponse obtenerDetalle(Long id) {
        OrdenServicio orden = ordenRepository.findByIdWithDetalles(id)
                .orElseThrow(() -> new ResourceNotFoundException("Orden no encontrada con id: " + id));
        return ordenMapper.toResponse(orden);
    }

    @Override
    @Transactional
    public OrdenResponse cambiarEstado(Long id, CambioEstadoRequest request) {
        OrdenServicio orden = buscarPorId(id);
        EstadoOrden nuevoEstado = request.getEstado();

        // Delegado: la máquina de estados valida la transición
        estadoMachine.validarTransicion(orden.getEstado(), nuevoEstado);

        if (nuevoEstado == EstadoOrden.EN_PROCESO && !detalleOrdenRepository.existsByOrdenId(id)) {
            throw new BusinessException("No se puede procesar una orden sin servicios agregados");
        }
        orden.setEstado(nuevoEstado);
        return ordenMapper.toResponse(ordenRepository.save(orden));
    }

    @Override
    @Transactional
    public OrdenResponse agregarDetalle(Long ordenId, DetalleOrdenRequest request) {
        OrdenServicio orden = buscarPorId(ordenId);

        // Delegado: la máquina valida si el estado permite modificaciones
        if (!estadoMachine.esModificable(orden.getEstado())) {
            throw new BusinessException("No se pueden agregar servicios a una orden en estado: " + orden.getEstado());
        }

        Servicio servicio = servicioService.buscarPorId(request.getServicioId());
        if (!servicio.getActivo()) {
            throw new BusinessException("El servicio no está disponible: " + servicio.getNombre());
        }

        // Precio manual → estrategia de resolución automática (delegado)
        BigDecimal precio = request.getPrecioAplicado() != null
                ? request.getPrecioAplicado()
                : precioStrategy.resolver(servicio, orden.getVehiculo());

        DetalleOrden detalle = DetalleOrden.builder()
                .orden(orden)
                .servicio(servicio)
                .precioAplicado(precio)
                .observaciones(request.getObservaciones())
                .build();
        orden.getDetalles().add(detalle);
        orden.recalcularTotal();
        return ordenMapper.toResponse(ordenRepository.save(orden));
    }

    @Override
    @Transactional
    public OrdenResponse eliminarDetalle(Long ordenId, Long detalleId) {
        OrdenServicio orden = buscarPorId(ordenId);
        if (orden.getEstado() != EstadoOrden.REGISTRADA) {
            throw new BusinessException("Solo se pueden eliminar servicios de órdenes en estado REGISTRADA");
        }
        DetalleOrden detalle = detalleOrdenRepository.findById(detalleId)
                .orElseThrow(() -> new ResourceNotFoundException("Detalle no encontrado con id: " + detalleId));
        if (!detalle.getOrden().getId().equals(ordenId)) {
            throw new BusinessException("El detalle no pertenece a la orden indicada");
        }
        orden.getDetalles().removeIf(d -> d.getId().equals(detalleId));
        orden.recalcularTotal();
        return ordenMapper.toResponse(ordenRepository.save(orden));
    }

    @Override
    @Transactional
    public void cancelar(Long id) {
        OrdenServicio orden = buscarPorId(id);
        if (orden.getEstado() == EstadoOrden.CANCELADA) {
            throw new BusinessException("La orden ya está cancelada");
        }
        // Delegado: valida que ENTREGADA no se puede cancelar
        estadoMachine.validarTransicion(orden.getEstado(), EstadoOrden.CANCELADA);
        orden.setEstado(EstadoOrden.CANCELADA);
        ordenRepository.save(orden);
    }

    @Override
    @Transactional(readOnly = true)
    public OrdenServicio buscarPorId(Long id) {
        return ordenRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Orden no encontrada con id: " + id));
    }
}
