package com.facilito.miapp.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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

    @NotNull
    @NotBlank(message = "El nombre es obligatorio")
    private String nombres;
    
    @NotNull
    @NotBlank(message = "El nombre es obligatorio")
    private String apellidos;
    
    @Column(unique = true)
    @NotNull
    @NotBlank(message = "El email es obligatorio")
    private String email;
    
    @NotNull
    @NotBlank(message = "El teléfono es obligatorio")
    private String telefono; 
    
    @NotNull(message = "La fecha de nacimiento es obligatoria")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date fechaNacimiento; 
    
    @NotNull
    @NotBlank(message = "El género es obligatorio")
    private String genero;
    
    
    @Column(unique = true)
    @NotNull
    @NotBlank(message = "El username es obligatorio")
    @Size(min = 2, max = 20)
    private String username;
    
    @NotNull
    @NotBlank(message = "La contraseña es obligatoria")
    private String password;
    
    @NotNull
    @Size(max = 1)
    private String tipoUsuario = "C";
}
