package com.facilito.miapp.controller.rest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.facilito.miapp.model.Pago;
import com.facilito.miapp.model.Tarjeta;
import com.facilito.miapp.model.TipoCambio;
import com.facilito.miapp.model.dto.ModelFormTarjeta;
import com.facilito.miapp.repository.PagoRepository;
import com.facilito.miapp.repository.TarjetaRepository;
import com.facilito.miapp.repository.TipoCambioRepository;

@RestController
@RequestMapping(value = "api/tarjeta", produces = "application/json")
public class TarjetaRestController {

    @Autowired
    private TarjetaRepository _dataTarjetas;

    @Autowired
    private TipoCambioRepository _dataTipoCambio;

    @Autowired
    private PagoRepository _dataPagos;

    @PostMapping(value = "/pagar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> pagar(@RequestBody ModelFormTarjeta tarjetaForm){

        String dueDate = tarjetaForm.getDueMonth() + "/" + tarjetaForm.getDueYear();
        Tarjeta tarjeta = _dataTarjetas.findByCredenciales(tarjetaForm.getNroTarjeta(), dueDate, tarjetaForm.getCvv());

        Map<String, Object> respuesta = new HashMap<>();

        String status = "";
        String mensaje = "";

        if(tarjeta != null){
            
            if(!tarjetaForm.getMoneda().equals(tarjeta.getMoneda())){ //Si las monedas no son iguales, se volverá a cargar el formulario (reload).

                TipoCambio tcActual = _dataTipoCambio.findLastTipoCambio();
                String moneda = "";

                if(tarjeta.getMoneda().equals("USD")){ //La tarjeta está en dólares, por lo que el monto del formulario se pasa a dólares.
                    
                    moneda = "USD";
                    tarjetaForm.setMoneda(moneda);
                    
                    Double monto = tarjetaForm.getMonto() / tcActual.getVenta();
                    monto = Math.rint(monto * 100) / 100;

                    tarjetaForm.setMonto(monto);

                }else{

                    moneda = "PEN";
                    tarjetaForm.setMoneda(moneda); //La tarjeta está en soles, por lo que el monto del formulario se pasa a soles.
                    
                    Double monto = tarjetaForm.getMonto() * tcActual.getCompra();
                    monto = Math.rint(monto * 100) / 100;

                    tarjetaForm.setMonto(monto);

                }

                status = "reload";
                mensaje = "Se calculó el monto total en " + moneda;

            }else{

                if(tarjeta.getMonto() >= tarjetaForm.getMonto()){

                    Pago p = new Pago();

                    p.setMonto(tarjetaForm.getMonto());
                    p.setTarjeta(tarjeta);
                    p.setFechaHora(new Date());

                    _dataPagos.save(p);
                    tarjeta.setMonto(tarjeta.getMonto() - tarjetaForm.getMonto()); //Se debita el pago y se hace el cobro a la tarjeta.
                    _dataTarjetas.save(tarjeta);
                    status = "success";
                    mensaje = "Pago realizado con éxito!!!";

                }else{
                    status = "error";
                    mensaje = "No cuenta con saldo suficiente para realizar el pago!!!";
                }

            }

        }else{
            status = "error";
            mensaje = "Los datos son incorrectos!!!";
        }

        if(!status.equals("success")){ //Solo si el estado no es success, se regresa el objeto del formulario
            respuesta.put("tarjeta", tarjetaForm);
        }else{
            respuesta.put("tarjeta", null);
        }

        respuesta.put("status", status);
        respuesta.put("mensaje", mensaje);

        return new ResponseEntity<Map<String,Object>>(
            respuesta, HttpStatus.OK);

    }

}
