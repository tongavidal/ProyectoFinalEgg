/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.faltauno.entidades;

import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author CARMEN
 */
@Entity
public class Partido {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid",strategy = "uuid2")
    private String id;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    
    private Integer cantJugador;
    private Integer cantVacantes;
    private Double precio;//?????
    
    @ManyToOne
    private Establecimiento establecimiento;
    
    @ManyToOne
    private Usuario usuario;
    
    @ManyToMany
    private List<Usuario> jugConfirmados;
    @ManyToMany
    private List<Usuario> jugPostulados;
    
    private Boolean estado;
    private String obsVacante;
    private String obsEstablecimiento;
    /*
    private Sexo sexo;
    private String sexo;
    */
    
    
}
