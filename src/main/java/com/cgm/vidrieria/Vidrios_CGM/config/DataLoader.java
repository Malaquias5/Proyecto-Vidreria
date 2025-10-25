package com.cgm.vidrieria.Vidrios_CGM.config;

import com.cgm.vidrieria.Vidrios_CGM.entity.Categoria;
import com.cgm.vidrieria.Vidrios_CGM.entity.Cliente;
import com.cgm.vidrieria.Vidrios_CGM.entity.Empleado;
import com.cgm.vidrieria.Vidrios_CGM.entity.Producto;
import com.cgm.vidrieria.Vidrios_CGM.repository.CategoriaRepository;
import com.cgm.vidrieria.Vidrios_CGM.repository.ClienteRepository;
import com.cgm.vidrieria.Vidrios_CGM.repository.EmpleadoRepository;
import com.cgm.vidrieria.Vidrios_CGM.repository.ProductoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initData(CategoriaRepository categoriaRepository,
                               ProductoRepository productoRepository,
                               ClienteRepository clienteRepository,
                               EmpleadoRepository empleadoRepository) {
        return args -> {
            if (categoriaRepository.count() == 0) {
                Categoria cat = new Categoria();
                cat.setNombre("Vidrios");
                cat.setDescripcion("Categoria para vidrios");
                cat = categoriaRepository.save(cat);

                Producto p1 = new Producto();
                p1.setCategoria(cat);
                p1.setCodigo("P001");
                p1.setNombre("Vidrio 4mm");
                p1.setDescripcion("Vidrio templado 4mm");
                p1.setTipo("templado");
                p1.setGrosor(new BigDecimal("4.0"));
                p1.setAlto(new BigDecimal("100"));
                p1.setAncho(new BigDecimal("200"));
                p1.setColor("Transparente");
                p1.setPrecioCompra(new BigDecimal("20.00"));
                p1.setPrecioVenta(new BigDecimal("30.00"));
                p1.setStockActual(50);
                p1.setEstado(true);
                productoRepository.save(p1);

                Producto p2 = new Producto();
                p2.setCategoria(cat);
                p2.setCodigo("P002");
                p2.setNombre("Vidrio 6mm");
                p2.setDescripcion("Vidrio templado 6mm");
                p2.setTipo("templado");
                p2.setGrosor(new BigDecimal("6.0"));
                p2.setAlto(new BigDecimal("100"));
                p2.setAncho(new BigDecimal("200"));
                p2.setColor("Transparente");
                p2.setPrecioCompra(new BigDecimal("30.00"));
                p2.setPrecioVenta(new BigDecimal("45.00"));
                p2.setStockActual(20);
                p2.setEstado(true);
                productoRepository.save(p2);
            }

            if (clienteRepository.count() == 0) {
                Cliente c = new Cliente();
                c.setNombre("Juan");
                c.setApellido("Pérez");
                c.setDocumento("12345678");
                c.setTelefono("987654321");
                c.setDireccion("Calle Falsa 123");
                c.setEmail("juan@example.com");
                c.setTipoCliente("regular");
                clienteRepository.save(c);
            }

            if (empleadoRepository.count() == 0) {
                Empleado e = new Empleado();
                e.setNombre("Ana");
                e.setApellido("Lopez");
                e.setDni("87654321");
                e.setTelefono("912345678");
                e.setDireccion("Oficina");
                e.setUsuario("ana");
                e.setCargo("Vendedora");
                e.setEstado(true);
                empleadoRepository.save(e);
            }
        };
    }
}
