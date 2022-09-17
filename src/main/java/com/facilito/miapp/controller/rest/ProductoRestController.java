package com.facilito.miapp.controller.rest;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.facilito.miapp.model.Producto;
import com.facilito.miapp.repository.ProductoRepository;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping(value = "api/producto", produces = "application/json")
public class ProductoRestController {
    
    private final ProductoRepository _dataProductos;

    public ProductoRestController(ProductoRepository dataProductos){
        _dataProductos = dataProductos;
    }

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Producto>> productos(){
        return  new ResponseEntity<List<Producto>>(
            _dataProductos.findAll(), HttpStatus.OK);
    }

    @PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Integer> create(@RequestBody Producto e){
        _dataProductos.save(e);
        _dataProductos.flush();
        return new ResponseEntity<Integer>(e.getId(),HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Producto> Productos(@PathVariable int id){
        Optional<Producto> optionalEntity = _dataProductos.findById(id);
        if(optionalEntity.isPresent())
            return new ResponseEntity<Producto>(
                optionalEntity.get(), HttpStatus.OK);
        else
            return new ResponseEntity<Producto>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity delete(@PathVariable int id){
        _dataProductos.deleteById(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Producto> update(@RequestBody Producto update){
        Optional<Producto> optinalEntity = _dataProductos.findById(update.getId());
        if(optinalEntity.isPresent()){
            Producto current = optinalEntity.get();
            current.setDescripcion(update.getDescripcion());
            create(current);
        }
        return new ResponseEntity<Producto>(HttpStatus.OK);
    }

}
