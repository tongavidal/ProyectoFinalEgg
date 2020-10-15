/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.faltauno.servicios;

import com.faltauno.entidades.Reputacion;
import com.faltauno.errores.ErrorServicio;
import com.faltauno.repositorios.ReputacionRepositorio;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author FaltaUno
 */

@Service
public class ReputacionServicio {
    
    @Autowired
    private ReputacionRepositorio reputacionRepositorio;
    
    
    @Transactional
    public void agregarReputacion(Integer puntualidad,Integer habilidad,Integer fairplay) throws ErrorServicio{
        validar(puntualidad, habilidad, fairplay);
        Reputacion reputacion=new Reputacion();
        reputacion.setPuntualidad(puntualidad);
        reputacion.setFairplay(fairplay);
        reputacion.setHabilidad(habilidad);
        reputacionRepositorio.save(reputacion);
    }
    
    @Transactional
    public void modificar(String id,Integer puntualidad,Integer habilidad,Integer fairplay) throws ErrorServicio{
        validar(puntualidad, habilidad, fairplay);
        Optional<Reputacion> respuesta=reputacionRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Reputacion reputacion=respuesta.get();
            reputacion.setPuntualidad(puntualidad);
            reputacion.setFairplay(fairplay);
            reputacion.setHabilidad(habilidad);
            reputacionRepositorio.save(reputacion);
        }else{
            throw new ErrorServicio("No se encontro el usuario");
        }
        
    }
    
    private void validar(Integer puntualidad,Integer habilidad,Integer fairplay) throws ErrorServicio{
        if (puntualidad==null) {
            throw new ErrorServicio("Dato Nulo");
        }
        if (habilidad==null) {
            throw new ErrorServicio("Dato Nulo");
        }
        if (fairplay==null) {
            throw new ErrorServicio("Dato Nulo");
        }
    }
    
}
