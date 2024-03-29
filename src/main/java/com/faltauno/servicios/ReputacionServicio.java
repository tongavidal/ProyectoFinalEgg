/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.faltauno.servicios;

import com.faltauno.entidades.Partido;
import com.faltauno.entidades.Reputacion;
import com.faltauno.errores.ErrorServicio;
import com.faltauno.repositorios.PartidoRepositorio;
import com.faltauno.repositorios.ReputacionRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.TreeMap;
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
    
    @Autowired
    private UsuarioServicio usuarioServicio;
    
    @Autowired
    private PartidoRepositorio partidoRepositorio;
    
    @Transactional
    public void agregarReputacion(String idUsuario, String idPartido, Integer puntualidad, Integer habilidad, Integer fairplay) throws ErrorServicio {
        validar(puntualidad, habilidad, fairplay);
        Partido partido = partidoRepositorio.getOne(idPartido);
        Reputacion reputacion = new Reputacion();
        reputacion.setPuntualidad(puntualidad);
        reputacion.setFairplay(fairplay);
        reputacion.setHabilidad(habilidad);
        reputacion.setPartido(partido);
        reputacionRepositorio.save(reputacion);
        usuarioServicio.agregarReputacionUsuario(idUsuario, reputacion);

    }
    
    public void comprobarCalificacion(String idUsuario,String idPartido) throws ErrorServicio{
        List<Reputacion> reputaciones=usuarioServicio.reputaciones(idUsuario);
        Partido partido=partidoRepositorio.getOne(idPartido);
        Boolean calificado=false;
        for (Reputacion r : reputaciones) {
            if (r.getPartido() != null) {
                if (r.getPartido().equals(partido)) {
                    calificado = true;
                    break;
                }
            }
        }
        if (calificado) {
             throw new ErrorServicio("Usted ya calificó a este usuario");
        }
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
            throw new ErrorServicio("Surgió algún error");
        }
        
    }
    
    private void validar(Integer puntualidad,Integer habilidad,Integer fairplay) throws ErrorServicio{
        if (puntualidad==null) {
            throw new ErrorServicio("Califique en puntualidad");
        }
        if (habilidad==null) {
            throw new ErrorServicio("Califique en habilidad");
        }
        if (fairplay==null) {
            throw new ErrorServicio("Califique en fairplay");
        }
    }
    
    public Integer promPuntualidad(String id){
        List<Reputacion> reputacionUsuario=usuarioServicio.reputaciones(id);
        if (!reputacionUsuario.isEmpty()) {
            Integer prom=0;
            for (Reputacion reputacion : reputacionUsuario) {
                prom+=reputacion.getPuntualidad();
            }
            return prom/reputacionUsuario.size();
        }else{
            return 0;
        }
    }
    public Integer promHabilidad(String id){
        List<Reputacion> reputacionUsuario=usuarioServicio.reputaciones(id);
        if (!reputacionUsuario.isEmpty()) {
            Integer prom=0;
            for (Reputacion reputacion : reputacionUsuario) {
                prom+=reputacion.getHabilidad();
            }
            return prom/reputacionUsuario.size();
        }else{
            return 0;
        }
    }
    public Integer promFairplay(String id){
        List<Reputacion> reputacionUsuario=usuarioServicio.reputaciones(id);
        if (!reputacionUsuario.isEmpty()) {
            Integer prom=0;
            for (Reputacion reputacion : reputacionUsuario) {
                prom+=reputacion.getFairplay();
            }
            return prom/reputacionUsuario.size();
        }else{
            return 0;
        }
    }
    
    public Reputacion devolverReputacion(String id){
        return reputacionRepositorio.getOne(id);
    }
    
    public TreeMap<String,Integer> promedioReputacion(String id){
        TreeMap<String,Integer> promedio=new TreeMap<>();
        promedio.put("Puntualidad",promPuntualidad(id));
        promedio.put("Habilidad",promHabilidad(id));
        promedio.put("FairPlay",promFairplay(id));
        return promedio;
    }
    
    public Integer promedioReputacionTotal(String id){
        Integer total;
        total = (promPuntualidad(id)+promHabilidad(id)+promFairplay(id))/3;
        return total;
    }
}
