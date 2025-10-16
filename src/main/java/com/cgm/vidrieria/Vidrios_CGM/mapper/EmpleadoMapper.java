package com.cgm.vidrieria.Vidrios_CGM.mapper;

import com.cgm.vidrieria.Vidrios_CGM.dto.ClienteDTO;
import com.cgm.vidrieria.Vidrios_CGM.dto.EmpleadoDTO;
import com.cgm.vidrieria.Vidrios_CGM.entity.Cliente;
import com.cgm.vidrieria.Vidrios_CGM.entity.Empleado;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring") // Spring lo inyectará automáticamente
public interface EmpleadoMapper {

    EmpleadoMapper INSTANCE = Mappers.getMapper(EmpleadoMapper.class);

    // DTO -> Entity
    Empleado toEntity(EmpleadoDTO dto);

    // Entity -> DTO
    EmpleadoDTO toDTO(Empleado empleado);

    // Listas
    List<EmpleadoDTO> toDTOList(List<Empleado> empleado);
    List<Empleado> toEntityList(List<EmpleadoDTO> dtos);
}