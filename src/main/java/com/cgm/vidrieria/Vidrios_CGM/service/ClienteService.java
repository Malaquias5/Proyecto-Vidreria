package com.cgm.vidrieria.Vidrios_CGM.service;

import com.cgm.vidrieria.Vidrios_CGM.dto.ClienteDTO;
import com.cgm.vidrieria.Vidrios_CGM.entity.Cliente;
import com.cgm.vidrieria.Vidrios_CGM.mapper.ClienteMapper;
import com.cgm.vidrieria.Vidrios_CGM.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ClienteMapper clienteMapper;

    // Listar todos los clientes
    public List<ClienteDTO> listarTodos() {
        List<Cliente> clientes = clienteRepository.findAll();
        return clienteMapper.toDTOList(clientes);
    }

    // Guardar cliente
    public ClienteDTO guardar(ClienteDTO dto) {
        Cliente c = clienteMapper.toEntity(dto);
        Cliente guardado = clienteRepository.save(c);
        return clienteMapper.toDTO(guardado);
    }

    // Buscar por ID (ahora devuelve Optional)
    public Optional<ClienteDTO> buscarPorId(Long id) {
        return clienteRepository.findById(id)
                .map(clienteMapper::toDTO);
    }

    // Eliminar
    public void eliminar(Long id) {
        clienteRepository.deleteById(id);
    }
}

