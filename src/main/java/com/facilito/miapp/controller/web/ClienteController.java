package com.facilito.miapp.controller.web;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.facilito.miapp.model.Cliente;
import com.facilito.miapp.model.Usuario;
import com.facilito.miapp.repository.ClienteRepository;
import com.facilito.miapp.repository.UsuarioRepository;

@Controller
@RequestMapping("cliente")
public class ClienteController {
    
    private final ClienteRepository _dataClientes;
    private final UsuarioRepository _dataUsuarios;

    public ClienteController(ClienteRepository dataClientes, UsuarioRepository dataUsuarios){
        _dataClientes = dataClientes;
        _dataUsuarios = dataUsuarios;
    }      

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String index(Model model) {
        model.addAttribute("title", "Registro de usuario");
        model.addAttribute("cliente", new Cliente());
        return "cliente/create";
    } 
    
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createSubmitForm(Model model, 
        @Valid Cliente objCliente, BindingResult result ){
        model.addAttribute("title", "Registro de usuario");
        if(result.hasFieldErrors()) {
            model.addAttribute("mensaje", "No se registro un cliente");
        }else{
            Usuario usuario = objCliente.getUsuario();
            usuario.setTipoUsuario("C");
            _dataUsuarios.save(usuario);
            _dataUsuarios.flush();
            _dataClientes.save(objCliente);
            model.addAttribute("cliente", objCliente);
            model.addAttribute("mensaje", "Se registro un cliente");
        }
        return "cliente/create";
    }

}
