/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.faltauno.servicios;

import com.faltauno.repositorios.ReputacionRepositorio;
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
    
    /*
        private Integer puntualidad;
    private Integer habilidad;
    private Integer fairplay;
    
    */
    
    @Transactional
    public void agregarReputacion(){
        
    }
}
