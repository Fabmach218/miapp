package com.facilito.miapp.model.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ModelRespuestaPago {
    private String status;
    private ModelFormTarjeta tarjeta;
    private String mensaje;
}
