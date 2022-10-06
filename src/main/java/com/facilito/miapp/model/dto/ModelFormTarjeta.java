package com.facilito.miapp.model.dto;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ModelFormTarjeta {
    private String nroTarjeta;
    private String dueMonth;
    private String dueYear;
    private String cvv;
    private String moneda;
    private Double monto;
}
