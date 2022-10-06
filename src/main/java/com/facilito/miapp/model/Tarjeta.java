package com.facilito.miapp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "t_tarjeta")

public class Tarjeta {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "char(16)", unique = true)
    @NotNull
    private String nroTarjeta;

    @Column(columnDefinition = "char(5)")
    @NotNull
    private String dueDate;

    @Column(columnDefinition = "char(3)")
    @NotNull
    private String cvv;

    @Column(columnDefinition = "char(3)")
    @NotNull
    private String moneda;

    @Column(columnDefinition = "numeric(18,2)")
    @NotNull
    private Double monto;
}
