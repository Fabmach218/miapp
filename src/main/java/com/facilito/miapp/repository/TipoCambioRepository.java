package com.facilito.miapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.facilito.miapp.model.TipoCambio;

public interface TipoCambioRepository extends JpaRepository<TipoCambio, Integer>{
    @Query(value = "SELECT * FROM t_tipo_cambio WHERE fecha_hora = (SELECT MAX(fecha_hora) FROM t_tipo_cambio)", nativeQuery = true)
    TipoCambio findLastTipoCambio();
}
