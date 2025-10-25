package com.cgm.vidrieria.Vidrios_CGM.repository;

import com.cgm.vidrieria.Vidrios_CGM.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}

