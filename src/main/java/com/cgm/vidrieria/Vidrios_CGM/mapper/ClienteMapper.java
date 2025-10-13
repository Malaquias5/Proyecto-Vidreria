package com.cgm.vidrieria.Vidrios_CGM.mapper;

import com.cgm.vidrieria.Vidrios_CGM.dto.ClienteDTO;
import com.cgm.vidrieria.Vidrios_CGM.entity.Cliente;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring") // Spring lo inyectará automáticamente
public interface ClienteMapper {

    ClienteMapper INSTANCE = Mappers.getMapper(ClienteMapper.class);

    // DTO -> Entity
    Cliente toEntity(ClienteDTO dto);

    // Entity -> DTO
    ClienteDTO toDTO(Cliente cliente);

    // Listas
    List<ClienteDTO> toDTOList(List<Cliente> clientes);
    List<Cliente> toEntityList(List<ClienteDTO> dtos);
}

