package com.cgm.vidrieria.Vidrios_CGM.service;

import com.cgm.vidrieria.Vidrios_CGM.dto.DetalleVentaCreateDTO;
import com.cgm.vidrieria.Vidrios_CGM.dto.DetalleVentaDTO;
import com.cgm.vidrieria.Vidrios_CGM.dto.VentaCreateDTO;
import com.cgm.vidrieria.Vidrios_CGM.dto.VentaDTO;
import com.cgm.vidrieria.Vidrios_CGM.entity.DetalleVenta;
import com.cgm.vidrieria.Vidrios_CGM.entity.Producto;
import com.cgm.vidrieria.Vidrios_CGM.entity.Venta;
import com.cgm.vidrieria.Vidrios_CGM.exception.StockInsuficienteException;
import com.cgm.vidrieria.Vidrios_CGM.repository.ClienteRepository;
import com.cgm.vidrieria.Vidrios_CGM.repository.EmpleadoRepository;
import com.cgm.vidrieria.Vidrios_CGM.repository.ProductoRepository;
import com.cgm.vidrieria.Vidrios_CGM.repository.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class VentaService {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private ClienteRepository
            clienteRepository;

    @Autowired
    private EmpleadoRepository empleadoRepository;

    // Puente para compatibilidad con el controlador: delega al método existente
    public VentaDTO crearVenta(VentaCreateDTO dto) {
        return crearVentas(dto);
    }

    @Transactional
    public VentaDTO crearVentas(VentaCreateDTO dto) {
        if (dto.getItems() == null || dto.getItems().isEmpty()) {
            throw new IllegalArgumentException("La venta debe tener al menos un item");
        }

        Venta venta = new Venta();
        venta.setFecha(LocalDateTime.now());
        venta.setTipoPago(dto.getTipoPago());
        venta.setEstado("PAGADA"); // por defecto consideramos pagada al crear

        if (dto.getClienteId() != null) {
            clienteRepository.findById(dto.getClienteId()).ifPresent(venta::setCliente);
        }
        if (dto.getVendedorId() != null) {
            empleadoRepository.findById(dto.getVendedorId()).ifPresent(venta::setVendedor);
        }

        List<DetalleVenta> detalles = new ArrayList<>();
        BigDecimal subtotal = BigDecimal.ZERO;
        BigDecimal descuentoTotal = BigDecimal.ZERO;

        // Intentamos decrementar el stock por cada item (operación atómica por producto)
        for (DetalleVentaCreateDTO item : dto.getItems()) {
            Long prodId = item.getProductoId();
            Integer qty = item.getCantidad();
            if (qty == null || qty <= 0) throw new IllegalArgumentException("Cantidad inválida para producto: " + prodId);

            // Fetch product (to get price and name)
            Producto producto = productoRepository.findById(prodId)
                    .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado id: " + prodId));

            // Intentar decrementar stock
            int updated = productoRepository.decrementStockIfAvailable(prodId, qty);
            if (updated == 0) {
                // obtener stock disponible para mensaje
                Integer disponible = producto.getStockActual();
                throw new StockInsuficienteException(prodId, disponible);
            }

            BigDecimal precioUnitario = item.getPrecioUnitario() != null ? item.getPrecioUnitario() : producto.getPrecioVenta();
            BigDecimal descuento = item.getDescuento() != null ? item.getDescuento() : BigDecimal.ZERO;
            BigDecimal lineSubtotal = precioUnitario.multiply(BigDecimal.valueOf(qty)).subtract(descuento);

            DetalleVenta detalle = new DetalleVenta();
            detalle.setProducto(producto);
            detalle.setCantidad(qty);
            detalle.setPrecioUnitario(precioUnitario);
            detalle.setDescuento(descuento);
            detalle.setSubtotal(lineSubtotal);
            detalle.setVenta(venta);

            detalles.add(detalle);

            subtotal = subtotal.add(precioUnitario.multiply(BigDecimal.valueOf(qty)));
            descuentoTotal = descuentoTotal.add(descuento);
        }

        venta.setItems(detalles);
        venta.setSubtotal(subtotal);
        // Impuesto simple: 0 por ahora o calcular según necesidad
        venta.setImpuesto(BigDecimal.ZERO);
        venta.setDescuentoTotal(descuentoTotal);
        venta.setTotal(subtotal.add(venta.getImpuesto() == null ? BigDecimal.ZERO : venta.getImpuesto()).subtract(descuentoTotal));

        Venta saved = ventaRepository.save(venta);

        return toDTO(saved);
    }

    public Page<VentaDTO> listarTodos(Pageable pageable) {
        Page<Venta> page = ventaRepository.findAll(pageable);
        List<VentaDTO> dtos = new ArrayList<>();
        for (Venta v : page.getContent()) dtos.add(toDTO(v));
        return new PageImpl<>(dtos, pageable, page.getTotalElements());
    }

    public Optional<VentaDTO> obtenerPorId(Long id) {
        return ventaRepository.findById(id).map(this::toDTO);
    }

    @Transactional
    public VentaDTO cancelarVenta(Long id, String motivo) {
        Venta venta = ventaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Venta no encontrada id: " + id));

        if ("CANCELADA".equalsIgnoreCase(venta.getEstado())) {
            throw new IllegalArgumentException("La venta ya está cancelada");
        }

        // Revertir stock por cada detalle
        if (venta.getItems() != null) {
            for (DetalleVenta d : venta.getItems()) {
                Long prodId = d.getProducto().getIdProducto();
                Integer qty = d.getCantidad();
                productoRepository.incrementStock(prodId, qty);
            }
        }

        venta.setEstado("CANCELADA");
        // No tenemos campo motivo; se podría añadir si se desea
        Venta saved = ventaRepository.save(venta);
        return toDTO(saved);
    }

    private VentaDTO toDTO(Venta v) {
        VentaDTO dto = new VentaDTO();
        dto.setIdVenta(v.getIdVenta());
        dto.setFecha(v.getFecha());
        if (v.getCliente() != null) {
            dto.setClienteId(v.getCliente().getIdCliente());
            dto.setClienteNombre(v.getCliente().getNombre() + " " + v.getCliente().getApellido());
        }
        if (v.getVendedor() != null) {
            dto.setVendedorId(v.getVendedor().getIdEmpleado());
            dto.setVendedorNombre(v.getVendedor().getNombre() + " " + v.getVendedor().getApellido());
        }
        dto.setSubtotal(v.getSubtotal());
        dto.setImpuesto(v.getImpuesto());
        dto.setDescuentoTotal(v.getDescuentoTotal());
        dto.setTotal(v.getTotal());
        dto.setTipoPago(v.getTipoPago());
        dto.setEstado(v.getEstado());

        List<DetalleVentaDTO> detalles = new ArrayList<>();
        if (v.getItems() != null) {
            for (DetalleVenta d : v.getItems()) {
                DetalleVentaDTO dd = new DetalleVentaDTO();
                dd.setProductoId(d.getProducto().getIdProducto());
                dd.setNombreProducto(d.getProducto().getNombre());
                dd.setCantidad(d.getCantidad());
                dd.setPrecioUnitario(d.getPrecioUnitario());
                dd.setDescuento(d.getDescuento());
                dd.setSubtotal(d.getSubtotal());
                detalles.add(dd);
            }
        }
        dto.setItems(detalles);
        return dto;
    }
}
