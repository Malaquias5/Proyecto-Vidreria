package com.cgm.vidrieria.Vidrios_CGM.controller;

import com.cgm.vidrieria.Vidrios_CGM.dto.ClienteDTO;
import com.cgm.vidrieria.Vidrios_CGM.dto.EmpleadoDTO;
import com.cgm.vidrieria.Vidrios_CGM.service.ClienteService;
import com.cgm.vidrieria.Vidrios_CGM.service.EmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/empleados")
public class EmpleadoController {

    @Autowired
    private EmpleadoService empleadoService;

    // Listar todos
    @GetMapping
    public List<EmpleadoDTO> listar() {
        return empleadoService.listarTodos();
    }

    // Obtener por ID
    @GetMapping("/{id}")
    public EmpleadoDTO obtener(@PathVariable Long id) {
        return empleadoService.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("empleado no encontrado con ID: " + id));
    }

    // Crear nuevo cliente
    @PostMapping
    public EmpleadoDTO crear(@RequestBody EmpleadoDTO empleado) {
        return empleadoService.guardar(empleado);
    }

    // Eliminar por ID
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        empleadoService.eliminar(id);
    }
}
