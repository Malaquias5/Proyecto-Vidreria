package com.cgm.vidrieria.Vidrios_CGM.service;

import com.cgm.vidrieria.Vidrios_CGM.dto.ClienteDTO;
import com.cgm.vidrieria.Vidrios_CGM.dto.EmpleadoDTO;
import com.cgm.vidrieria.Vidrios_CGM.entity.Cliente;
import com.cgm.vidrieria.Vidrios_CGM.entity.Empleado;
import com.cgm.vidrieria.Vidrios_CGM.mapper.ClienteMapper;
import com.cgm.vidrieria.Vidrios_CGM.mapper.EmpleadoMapper;
import com.cgm.vidrieria.Vidrios_CGM.repository.ClienteRepository;
import com.cgm.vidrieria.Vidrios_CGM.repository.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmpleadoService {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Autowired
    private EmpleadoMapper empleadoMapper;

    // Listar todos los clientes
    public List<EmpleadoDTO> listarTodos() {
        List<Empleado> empleados = empleadoRepository.findAll();
        return empleadoMapper.toDTOList(empleados);
    }

    // Guardar cliente
    public EmpleadoDTO guardar(EmpleadoDTO dto) {
        Empleado e = empleadoMapper.toEntity(dto);
        Empleado guardado = empleadoRepository.save(e);
        return empleadoMapper.toDTO(guardado);
    }

    // Buscar por ID (ahora devuelve Optional)
    public Optional<EmpleadoDTO> buscarPorId(Long id) {
        return empleadoRepository.findById(id)
                .map(empleadoMapper::toDTO);
    }

    // Eliminar
    public void eliminar(Long id) {
        empleadoRepository.deleteById(id);
    }
}

