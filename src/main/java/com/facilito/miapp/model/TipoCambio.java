package com.facilito.miapp.model;

import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
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
@Table(name = "t_tipo_cambio")

public class TipoCambio {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
    
    @Column(columnDefinition = "numeric(22,6)")
    @NotNull
    private Double compra;
    
    @Column(columnDefinition = "numeric(22,6)")
    @NotNull
    private Double venta;
    
    @NotNull
    @CreationTimestamp
    private Date fechaHora;

}
