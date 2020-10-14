/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.faltauno.entidades;

import com.faltauno.enumeraciones.Posiciones;
import com.faltauno.enumeraciones.Sexo;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author CARMEN
 */
@Entity
public class Usuario {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid",strategy = "uuid2")
    private String id;
    
    private String nombre;
    private String apellido;
    private String edad;
    private String mail;
    private String clave;
    private Sexo sexo;
    
    private String ciudad; //?????
    private Posiciones posicion;
    
    @OneToOne
    private Reputacion reputación;
    
    private Integer asistencia; //????
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    
    private Boolean estado;
    
}
