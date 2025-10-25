package com.cgm.vidrieria.Vidrios_CGM.controller;

import com.cgm.vidrieria.Vidrios_CGM.dto.VentaCreateDTO;
import com.cgm.vidrieria.Vidrios_CGM.dto.VentaDTO;
import com.cgm.vidrieria.Vidrios_CGM.service.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ventas")
@CrossOrigin(origins = "http://localhost:4200") // 🔹 Para que Angular pueda conectarse
public class VentaController {

    @Autowired
    private VentaService ventaService;

    // Crear venta
    @PostMapping
    public ResponseEntity<VentaDTO> crearVenta(@RequestBody VentaCreateDTO dto) {
        VentaDTO res = ventaService.crearVenta(dto);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    // Listado paginado
    @GetMapping
    public Page<VentaDTO> listar(Pageable pageable) {
        return ventaService.listarTodos(pageable);
    }

    // Obtener venta por ID
    @GetMapping("/{id}")
    public ResponseEntity<VentaDTO> obtener(@PathVariable Long id) {
        return ventaService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Cancelar venta y revertir stock
    @PutMapping("/{id}/cancel")
    public ResponseEntity<VentaDTO> cancelar(@PathVariable Long id,
                                             @RequestParam(required = false) String motivo) {
        VentaDTO dto = ventaService.cancelarVenta(id, motivo);
        return ResponseEntity.ok(dto);
    }
}
