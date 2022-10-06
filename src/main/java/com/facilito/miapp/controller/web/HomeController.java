package com.facilito.miapp.controller.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.facilito.miapp.integration.sendgrid.SendGridAPI;
import com.facilito.miapp.model.TipoCambio;
import com.facilito.miapp.model.Usuario;
import com.facilito.miapp.repository.TipoCambioRepository;
import com.facilito.miapp.service.IUsuarioService;

@Controller
public class HomeController {
    
    @Autowired
    private IUsuarioService _dataUsuarios;

    @Autowired
    private TipoCambioRepository _dataTipoCambio;

    @GetMapping({"/", "/index"})
    public String index(Authentication auth, HttpSession session, Model model){
        
        if(auth != null){
            String username = auth.getName();
            Usuario usuario = _dataUsuarios.findByUsername(username);
            usuario.setPassword(null);
            session.setAttribute("usuario", usuario);
        }

        TipoCambio tcActual = _dataTipoCambio.findLastTipoCambio();
        
        model.addAttribute("compra", tcActual.getCompra());
        model.addAttribute("venta", tcActual.getVenta());
        model.addAttribute("title", "Inicio");
        return "index";
    }

}
