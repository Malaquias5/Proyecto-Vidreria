package com.cgm.vidrieria.Vidrios_CGM.repository;

import com.cgm.vidrieria.Vidrios_CGM.entity.Cliente;
import com.cgm.vidrieria.Vidrios_CGM.entity.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {
}
