package com.facilito.miapp.controller.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.facilito.miapp.model.Usuario;
import com.facilito.miapp.service.IUsuarioService;

@Controller
public class HomeController {
    
    @Autowired
    private IUsuarioService _dataUsuarios;

    @GetMapping({"/", "/index"})
    public String index(Authentication auth, HttpSession session, Model model){

        if(auth != null){
            String username = auth.getName();
            Usuario usuario = _dataUsuarios.findByUsername(username);
            usuario.setPassword(null);
            session.setAttribute("usuario", usuario);
        }
        
        model.addAttribute("title", "Inicio");
        return "index";
    }

}
