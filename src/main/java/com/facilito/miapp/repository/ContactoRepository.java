package com.facilito.miapp.repository;

import com.facilito.miapp.model.Contacto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface  ContactoRepository extends JpaRepository<Contacto, Integer>{
    
}