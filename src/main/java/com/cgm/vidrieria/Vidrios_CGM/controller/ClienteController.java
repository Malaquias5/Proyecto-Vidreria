package com.cgm.vidrieria.Vidrios_CGM.controller;

import com.cgm.vidrieria.Vidrios_CGM.dto.ClienteDTO;
import com.cgm.vidrieria.Vidrios_CGM.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    // Listar todos
    @GetMapping
    public List<ClienteDTO> listar() {
        return clienteService.listarTodos();
    }

    // Obtener por ID
    @GetMapping("/{id}")
    public ClienteDTO obtener(@PathVariable Long id) {
        return clienteService.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con ID: " + id));
    }

    // Crear nuevo cliente
    @PostMapping
    public ClienteDTO crear(@RequestBody ClienteDTO cliente) {
        return clienteService.guardar(cliente);
    }

    // Eliminar por ID
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        clienteService.eliminar(id);
    }
}
