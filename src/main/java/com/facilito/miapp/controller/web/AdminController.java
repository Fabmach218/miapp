package com.facilito.miapp.controller.web;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.facilito.miapp.integration.sendgrid.SendGridAPI;
import com.facilito.miapp.model.Tarjeta;
import com.facilito.miapp.model.dto.ModelFormTarjeta;
import com.facilito.miapp.model.dto.ModelRespuestaPago;
import com.facilito.miapp.repository.TarjetaRepository;
import com.facilito.miapp.util.Constants;

@Secured("ROLE_ADMIN")
@RequestMapping("admin")
@Controller
public class AdminController {

    @Autowired
    private SendGridAPI _sendgrid;

    @Autowired
    private TarjetaRepository _dataTarjetas;

    @Autowired
    private RestTemplate _restTemplate;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model){
        model.addAttribute("title", "Panel de administrador");
        return "admin/index";
    }

    @RequestMapping(value = "/enviarCorreo", method = RequestMethod.GET)
    public String enviarCorreo(Model model){

        int statusCode = _sendgrid.send("abex_smlld33@jyplo.com", "Hola, ahora uso SendGrid", "marangonfabio.727@outlook.com", "Hola, si recibiste este correo, significa que SendGrid funciona correctamente!!!");
        System.out.println(statusCode);
        return "redirect:/admin/";

    }

    @RequestMapping(value = "/crearTarjeta", method = RequestMethod.GET)
    public String crearTarjeta(Model model){

        Tarjeta tarjeta = new Tarjeta();

        tarjeta.setNroTarjeta(generarAleatorio(3000000000000000L, 5999999999999999L) + "");
        String dueDate = generarAleatorio(1, 12) + "/" + generarAleatorio(23, 27);
        String cvv = generarAleatorio(10, 999) + "";

        while(dueDate.length() < 5){
            dueDate = "0" + dueDate;
        }

        while(cvv.length() < 3){
            cvv = "0" + cvv;
        }

        tarjeta.setDueDate(dueDate);
        tarjeta.setCvv(cvv);
        
        long aleMoneda = generarAleatorio(1, 2);
        String moneda;
        Double monto;

        if(aleMoneda == 1){
            moneda = "PEN";
            monto = generarAleatorio(100000, 1000000)/100.0;
        }else{
            moneda = "USD";
            monto = generarAleatorio(20000, 200000)/100.0;
        }

        tarjeta.setMoneda(moneda);
        tarjeta.setMonto(monto);
        
        _dataTarjetas.save(tarjeta);

        return "redirect:/admin/";
    }

    @RequestMapping(value = "/pagar", method = RequestMethod.GET)
    public String pagar(Model model){

        ModelFormTarjeta tarjetaForm = new ModelFormTarjeta();

        long aleMoneda = generarAleatorio(1, 2);
        String moneda = "";
        Double monto = 0.0;

        if(aleMoneda == 1){
            moneda = "PEN";
            monto = generarAleatorio(2000, 10000)/100.0;
        }else{
            moneda = "USD";
            monto = generarAleatorio(500, 2500)/100.0;
        }

        tarjetaForm.setMoneda(moneda);
        tarjetaForm.setMonto(monto);

        model.addAttribute("tarjetaForm", tarjetaForm);
        return "admin/pasarela";

    }

    @RequestMapping(value = "/pagar", method = RequestMethod.POST)
    public String pagar(Model model, @Valid ModelFormTarjeta tarjetaForm, BindingResult result, RedirectAttributes redirectAttributes){

        HttpEntity<Object> entity = new HttpEntity<Object>(tarjetaForm);
        ResponseEntity<ModelRespuestaPago> responseEntity;

        System.out.println(tarjetaForm.getNroTarjeta());

        try{
            
            responseEntity = _restTemplate.exchange(Constants.URI_APP + Constants.ROUTE_TARJETAS + Constants.PAGAR, HttpMethod.POST, entity, ModelRespuestaPago.class);

            ModelRespuestaPago respuesta = responseEntity.getBody();

            if(respuesta.getStatus().equals("reload")){
                model.addAttribute("mensajeRecarga", respuesta.getMensaje());
                model.addAttribute("tarjetaForm", respuesta.getTarjeta());
                return "admin/pasarela";
            }

            if(respuesta.getStatus().equals("error")){
                model.addAttribute("mensajeError", respuesta.getMensaje());
                model.addAttribute("tarjetaForm", respuesta.getTarjeta());
                return "admin/pasarela";
            }

            if(respuesta.getStatus().equals("success")){
                redirectAttributes.addFlashAttribute("status", respuesta.getStatus());
                redirectAttributes.addFlashAttribute("mensaje", respuesta.getMensaje());
                return "redirect:/admin/";
            }

        }catch(Exception e){
            System.out.println(e.getMessage());
        }

        return "redirect:/";
        

    }

    public long generarAleatorio(long min, long max){
        return (long)(Math.random() * (max - min + 1) + min);
    }

}
