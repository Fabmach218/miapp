package com.facilito.miapp.controller.web;

import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.facilito.miapp.model.Usuario;
import com.facilito.miapp.service.IUsuarioService;

@Controller
public class HomeController {
    
    private IUsuarioService _dataUsuarios;

    @GetMapping({"/", "/index"})
    public String index(Model model){
        
        /*String username = auth.getName();
        
        if(session.getAttribute("usuario") == null){
            Usuario usuario = _dataUsuarios.findByUsername(username);
            usuario.setPassword(null);
            session.setAttribute("usuario", usuario);
        }*/

        model.addAttribute("title", "Inicio");
        return "index";
    }

}
