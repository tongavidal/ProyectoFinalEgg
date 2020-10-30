/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.faltauno.entidades;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author FaltaUno
 */
@Entity
public class Reputacion {
    
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid",strategy = "uuid2")
    private String id;
    
    
    private Integer puntualidad;
    private Integer habilidad;
    private Integer fairplay;

    public Reputacion() {
    }
    
    
    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the puntualidad
     */
    public Integer getPuntualidad() {
        return puntualidad;
    }

    /**
     * @param puntualidad the puntualidad to set
     */
    public void setPuntualidad(Integer puntualidad) {
        this.puntualidad = puntualidad;
    }

    /**
     * @return the habilidad
     */
    public Integer getHabilidad() {
        return habilidad;
    }

    /**
     * @param habilidad the habilidad to set
     */
    public void setHabilidad(Integer habilidad) {
        this.habilidad = habilidad;
    }

    /**
     * @return the fairplay
     */
    public Integer getFairplay() {
        return fairplay;
    }

    /**
     * @param fairplay the fairplay to set
     */
    public void setFairplay(Integer fairplay) {
        this.fairplay = fairplay;
    }

}
