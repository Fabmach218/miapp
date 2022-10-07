package com.facilito.miapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.facilito.miapp.model.Pago;

public interface PagoRepository extends JpaRepository<Pago, Integer>{
    
}
