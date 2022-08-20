package com.facilito.miapp.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.facilito.miapp.model.Calculadora;

@Controller
public class CalculadoraController {
    
    @GetMapping("/calculadora/")
    public String index(Model model){
        model.addAttribute("calculadora", new Calculadora());
        return "calculadora/index";
    }

    @PostMapping("/calcular")
    public String calcular(Model model, 
        @Valid Calculadora objCalculadora, BindingResult result ){
            
        Double resultado = 0.0;
        if("+".equals(objCalculadora.getOperador())){
            resultado = objCalculadora.getOperando1() + objCalculadora.getOperando2();
        }
        String respuesta = "El resultado es: " + resultado;
        model.addAttribute("resultado", respuesta);
        return "calculadora/index";    
    }

}
