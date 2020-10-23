/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.faltauno.servicios;

import com.faltauno.entidades.Establecimiento;
import com.faltauno.entidades.Localidad;
import com.faltauno.errores.ErrorServicio;
import com.faltauno.repositorios.EstablecimientoRepositorio;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author FaltaUno
 */

@Service
public class EstablecimientoServicio {
    
    @Autowired
    private EstablecimientoRepositorio establecimientoRepositorio;
    
    /*@Autowired
    private LocalidadRepositorio localidadRepositorio;
    */
    
    @Transactional
    public void agregarEstablecimiento(String idLocalidad, String nombre, String direccion) throws  ErrorServicio{
        //Localidad localidad=localidadRepositorio.getOne(idLocalidad);
        validar(nombre, direccion);
        Establecimiento establecimiento=new Establecimiento();
        establecimiento.setNombre(nombre);
        establecimiento.setDireccion(direccion);
        establecimiento.setEstado(true);
        establecimiento.setLocalidad(new Localidad());
        
        establecimientoRepositorio.save(establecimiento);
    }
    
    @Transactional
    public void modificar(String id, String idLocalidad,String nombre,String direccion) throws ErrorServicio{
        //Localidad localidad=localidadRepositorio.getOne(idLocalidad);
        validar(nombre, direccion);
        
        Optional<Establecimiento> respuesta=establecimientoRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Establecimiento establecimiento=respuesta.get();
            establecimiento.setNombre(nombre);
            establecimiento.setDireccion(direccion);
            establecimiento.setEstado(true);
            establecimiento.setLocalidad(new Localidad());
            establecimientoRepositorio.save(establecimiento);
        }else{
            throw  new ErrorServicio("No se encontro el establecimiento");
        }
    }
    
    @Transactional
    public void deshabilitar(String id) throws ErrorServicio{
        Optional<Establecimiento> respuesta=establecimientoRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Establecimiento establecimiento=respuesta.get();
            establecimiento.setEstado(false);
            establecimientoRepositorio.save(establecimiento);
        }else{
           throw  new ErrorServicio("No se encontro el establecimiento"); 
        }
    }
    
    @Transactional
    public void habilitar(String id) throws ErrorServicio{
        Optional<Establecimiento> respuesta=establecimientoRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Establecimiento establecimiento=respuesta.get();
            establecimiento.setEstado(true);
            establecimientoRepositorio.save(establecimiento);
        }else{
           throw  new ErrorServicio("No se encontro el establecimiento"); 
        }
    }
    
    public List<Establecimiento> listaEstablecimientos(){
        return establecimientoRepositorio.findAll();
    }
    
    private void validar(String nombre, String direccion) throws ErrorServicio{
        if (nombre==null || nombre.isEmpty()) {
            throw new ErrorServicio(("El nombre no puede estar vacio"));
        }
        if (direccion==null || direccion.isEmpty()) {
            throw new ErrorServicio("Debe indicar alguna direccion");
        }
    }
}
