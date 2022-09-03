package com.facilito.miapp.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.facilito.miapp.model.Calculadora;

@Controller
@RequestMapping("/calculadora")
public class CalculadoraController {
    
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model, @Valid Calculadora objCalculadora, BindingResult result){
        model.addAttribute("title", "Calculadora");
        if(objCalculadora == new Calculadora()){
            model.addAttribute("calculadora", new Calculadora());
            model.addAttribute("resultado", "");
        }else{
            Double resultado = 0.0;
            if("+".equals(objCalculadora.getOperador())){
                resultado = objCalculadora.getOperando1() + objCalculadora.getOperando2();
            }else if("-".equals(objCalculadora.getOperador())){
                resultado = objCalculadora.getOperando1() - objCalculadora.getOperando2();
            }
            String respuesta = "El resultado es: " + resultado;
            model.addAttribute("resultado", respuesta);    
        }
        return "calculadora/index";
    }
}
