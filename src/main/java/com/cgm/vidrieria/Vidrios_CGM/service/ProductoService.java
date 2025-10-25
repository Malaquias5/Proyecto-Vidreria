package com.cgm.vidrieria.Vidrios_CGM.service;

import com.cgm.vidrieria.Vidrios_CGM.entity.Producto;
import com.cgm.vidrieria.Vidrios_CGM.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    public List<Producto> listarTodos() {
        return productoRepository.findAll();
    }

    public Optional<Producto> buscarPorId(Long id) {
        return productoRepository.findById(id);
    }

    public Producto guardar(Producto p) {
        return productoRepository.save(p);
    }

    public void eliminar(Long id) {
        productoRepository.deleteById(id);
    }
}

