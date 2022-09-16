package com.facilito.miapp.repository;

import com.facilito.miapp.model.Cliente;
import com.facilito.miapp.model.Usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer>{
    @Query(value = "SELECT c FROM Cliente c WHERE c.usuario=?1")
    Cliente findByUsuario(Usuario usuario);
}
