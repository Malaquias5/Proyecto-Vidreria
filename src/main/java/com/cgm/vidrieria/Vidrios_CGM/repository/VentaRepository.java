package com.cgm.vidrieria.Vidrios_CGM.repository;

import com.cgm.vidrieria.Vidrios_CGM.entity.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Long> {
}

