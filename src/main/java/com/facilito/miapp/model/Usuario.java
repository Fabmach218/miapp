package com.facilito.miapp.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "t_usuario")
public class Usuario implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nombres;
    private String apellidos;
    private String email;
    private String telefono; 
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date fechaNacimiento; 
    private String genero;
    @Column(unique = true)
    private String username;
    private String password;
    private String tipoUsuario;
}
