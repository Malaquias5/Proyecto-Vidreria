package com.cgm.vidrieria.Vidrios_CGM.repository;

import com.cgm.vidrieria.Vidrios_CGM.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    // Intenta decrementar el stock; devuelve 1 si se actualizó, 0 si no había stock suficiente
    @Modifying
    @Query("UPDATE Producto p SET p.stockActual = p.stockActual - :qty WHERE p.idProducto = :id AND p.stockActual >= :qty")
    int decrementStockIfAvailable(@Param("id") Long id, @Param("qty") Integer qty);

    // Incrementa el stock (usado al cancelar ventas)
    @Modifying
    @Query("UPDATE Producto p SET p.stockActual = p.stockActual + :qty WHERE p.idProducto = :id")
    int incrementStock(@Param("id") Long id, @Param("qty") Integer qty);
}
