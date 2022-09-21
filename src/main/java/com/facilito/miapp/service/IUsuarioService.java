package com.facilito.miapp.service;

import org.springframework.stereotype.Service;

import com.facilito.miapp.model.Usuario;

@Service
public interface IUsuarioService {
    public Usuario findByUsername(String username);
    public Usuario registrar(Usuario u);
}
