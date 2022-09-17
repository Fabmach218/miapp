package com.facilito.miapp.controller.web;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.facilito.miapp.model.Contacto;
import com.facilito.miapp.repository.ContactoRepository;

import java.util.List;

@Controller
@RequestMapping("contacto")
public class ContactoController {
    
    private final ContactoRepository _dataContactos;

    public ContactoController(ContactoRepository dataContactos){
        _dataContactos = dataContactos;
    }


    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model){
        model.addAttribute("title", "Contacto");
        List<Contacto> listaContactos = _dataContactos.findAll();
        model.addAttribute("listaContactos", listaContactos);
        return "contacto/index";
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(Model model){
        model.addAttribute("title", "Crear Contacto");
        model.addAttribute("contacto", new Contacto());
        return "contacto/create";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(Model model, @Valid Contacto contacto, BindingResult result){
        _dataContactos.save(contacto);
        return "redirect:/contacto/";
    }

}
