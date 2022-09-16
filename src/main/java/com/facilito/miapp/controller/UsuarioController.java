package com.facilito.miapp.controller;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.facilito.miapp.model.Usuario;
import com.facilito.miapp.repository.UsuarioRepository;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {
    
    private final UsuarioRepository _dataUsuarios;

    public UsuarioController(UsuarioRepository dataUsuarios){
        _dataUsuarios = dataUsuarios;
    }      

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model) {
        model.addAttribute("title", "Inicio de sesión");
        model.addAttribute("usuario", new Usuario());
        return "usuario/login";
    }  

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String loginSubmitForm(Model model, @Valid Usuario objUser, HttpServletRequest request, BindingResult result){
        String page="usuario/login";
        model.addAttribute("title", "Inicio de sesión");
        model.addAttribute("usuario", new Usuario());
        if(result.hasFieldErrors()) {
            model.addAttribute("usuario", "No se ha podido loguear");
        }else{
            Optional<Usuario> user = _dataUsuarios.findById(objUser.getUserID());
      
            if(user.isPresent()){
                if(user.get().getPassword().equals(objUser.getPassword())){
                    model.addAttribute("usuario",user.get());
                    model.addAttribute("mensaje", "Usuario existe");
                    
                    request.getSession().setAttribute("usuario", objUser);
                    
                    page="index";  
                }else{
                    model.addAttribute("mensaje", "Password no coincide");  
                }
            }else{
                model.addAttribute("mensaje", "Usuario no existe");
            }
        }
        return page;
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logoutSession(HttpServletRequest request) {
		request.getSession().invalidate();
		return "redirect:/";
	}
    
}
