package com.facilito.miapp.controller.web;

import java.util.List;
import java.util.Optional;

import com.facilito.miapp.model.Producto;
import com.facilito.miapp.repository.ProductoRepository;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

@Controller
@RequestMapping("catalogo")
public class CatalogoController{
 
    private final ProductoRepository _dataProductos;

    public CatalogoController(ProductoRepository dataProductos){
        _dataProductos = dataProductos;
    }      

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(@RequestParam(defaultValue="") String searchName, Model model){
        List<Producto> listaProductos = null;
        if(searchName.isEmpty()){
            listaProductos = _dataProductos.getAllActiveProductos();
        }else{
            listaProductos = _dataProductos.getAllActiveProductosBySearch(searchName);
        }
        model.addAttribute("title", "Catálogo de productos");
        model.addAttribute("productos", listaProductos);
        return "catalogo/index";
    }    
  
}