package com.facilito.miapp.controller.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.facilito.miapp.model.Usuario;
import com.facilito.miapp.service.IUsuarioService;

@Controller
@RequestMapping("usuario")
public class UsuarioController {
    
    @Autowired
    private IUsuarioService _dataUsuarios;  

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(Model model) {
        model.addAttribute("title", "Registro de usuario");
        model.addAttribute("usuario", new Usuario());
        return "usuario/create";
    } 
    
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createPost(Model model, @Valid @ModelAttribute Usuario usuario, BindingResult result, RedirectAttributes redirectAttributes ){
        model.addAttribute("title", "Registro de usuario");
        if(result.hasFieldErrors()) {
            redirectAttributes.addFlashAttribute("mensaje", "No se registro un cliente");
            return "redirect:/usuario/create";
        }else{
            usuario.setTipoUsuario("C");
            _dataUsuarios.registrar(usuario);
            model.addAttribute("usuario", usuario);
        }
        return "redirect:/usuario/login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model) {
        model.addAttribute("title", "Inicio de sesión");
        model.addAttribute("usuario", new Usuario());
        return "usuario/login";
    }  
    
}
