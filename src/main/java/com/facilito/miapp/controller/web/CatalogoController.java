package com.facilito.miapp.controller.web;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.facilito.miapp.model.Producto;
import com.facilito.miapp.model.Usuario;
import com.facilito.miapp.repository.ProductoRepository;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
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
    
    @RequestMapping(value = "/add/{id}", method = RequestMethod.GET)
    public String add(@PathVariable("id") Integer id, HttpSession session, Model model, RedirectAttributes redirectAttributes){
        Usuario usuario = (Usuario)session.getAttribute("usuario"); 
        if(usuario==null) {
            redirectAttributes.addFlashAttribute("status", "error");
            redirectAttributes.addFlashAttribute("mensaje", "Debe loguearse antes de agregar");
        }else{
            /*Producto productSeleccionado = productsData.getOne(id);
            Optional<Proforma> item= 
                proformaData.findProformaByUsuarioAndProducto(user, productSeleccionado);
            if(!item.isPresent()){
                Proforma itemCarrito = new Proforma();
                itemCarrito.setCantidad(1);
                itemCarrito.setUser(user);
                itemCarrito.setPrecio(productSeleccionado.getPrecio());
                itemCarrito.setProduct(productSeleccionado);
                proformaData.save(itemCarrito);
                model.addAttribute("mensaje", "Se agrego el producto al carrito");
            }else{
                Proforma itemCarritoExistente=item.get();
                itemCarritoExistente.setCantidad(itemCarritoExistente.getCantidad()+1);
                proformaData.save(itemCarritoExistente);
                model.addAttribute("mensaje", "Se adiciono el producto al carrito");
            }*/
            redirectAttributes.addFlashAttribute("status", "success");
            redirectAttributes.addFlashAttribute("mensaje", "Producto agregado");

        }   
        return "redirect:/catalogo/";
    }  

}