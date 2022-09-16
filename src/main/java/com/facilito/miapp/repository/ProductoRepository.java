package com.facilito.miapp.repository;

import com.facilito.miapp.model.Producto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface  ProductoRepository extends JpaRepository<Producto, Integer>{

    @Query(value = "SELECT p FROM Producto p WHERE p.estado='A'")
    List<Producto> getAllActiveProductos();

    @Query(value = "SELECT p FROM Producto p WHERE p.descripcion LIKE %:searchName% and p.estado='A'")
    List<Producto> getAllActiveProductosBySearch(@Param("searchName") String searchName);
}