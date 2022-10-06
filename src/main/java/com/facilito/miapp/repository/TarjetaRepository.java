package com.facilito.miapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.facilito.miapp.model.Tarjeta;

public interface TarjetaRepository extends JpaRepository<Tarjeta, Integer>{
    @Query(value = "SELECT t FROM Tarjeta t WHERE t.nroTarjeta = :nroTarjeta AND t.dueDate = :dueDate AND t.cvv = :cvv")
    Tarjeta findByCredenciales(@Param("nroTarjeta") String nroTarjeta, @Param("dueDate") String dueDate, @Param("cvv") String cvv);
}
