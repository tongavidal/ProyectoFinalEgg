/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.faltauno.entidades;

import com.faltauno.enumeraciones.Sexo;
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
 * @author FaltaUno
 */
@Entity
public class Partido {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid",strategy = "uuid2")
    private String id;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    
    @ManyToOne
    private Localidad localidad;
    
    private Integer cantJugador;
    private Integer cantVacantes;
    private Double precio;//--
    
    @ManyToOne
    private Establecimiento establecimiento;
    
    @ManyToOne
    private Usuario creador;
    
    @ManyToMany
    private List<Usuario> jugConfirmados;
    @ManyToMany
    private List<Usuario> jugPostulados;
    
    private Boolean estado;
    private String obsVacante;
    private String obsEstablecimiento;
    
    private Sexo sexo;

    public Partido() {
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
     * @return the fecha
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     * @param fecha the fecha to set
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    /**
     * @return the localidad
     */
    public Localidad getLocalidad() {
        return localidad;
    }

    /**
     * @param localidad the localidad to set
     */
    public void setLocalidad(Localidad localidad) {
        this.localidad = localidad;
    }

    /**
     * @return the cantJugador
     */
    public Integer getCantJugador() {
        return cantJugador;
    }

    /**
     * @param cantJugador the cantJugador to set
     */
    public void setCantJugador(Integer cantJugador) {
        this.cantJugador = cantJugador;
    }

    /**
     * @return the cantVacantes
     */
    public Integer getCantVacantes() {
        return cantVacantes;
    }

    /**
     * @param cantVacantes the cantVacantes to set
     */
    public void setCantVacantes(Integer cantVacantes) {
        this.cantVacantes = cantVacantes;
    }

    /**
     * @return the precio
     */
    public Double getPrecio() {
        return precio;
    }

    /**
     * @param precio the precio to set
     */
    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    /**
     * @return the establecimiento
     */
    public Establecimiento getEstablecimiento() {
        return establecimiento;
    }

    /**
     * @param establecimiento the establecimiento to set
     */
    public void setEstablecimiento(Establecimiento establecimiento) {
        this.establecimiento = establecimiento;
    }

    /**
     * @return the creador
     */
    public Usuario getCreador() {
        return creador;
    }

    /**
     * @param creador the creador to set
     */
    public void setCreador(Usuario creador) {
        this.creador = creador;
    }

    /**
     * @return the jugConfirmados
     */
    public List<Usuario> getJugConfirmados() {
        return jugConfirmados;
    }

    /**
     * @param jugConfirmados the jugConfirmados to set
     */
    public void setJugConfirmados(List<Usuario> jugConfirmados) {
        this.jugConfirmados = jugConfirmados;
    }

    /**
     * @return the jugPostulados
     */
    public List<Usuario> getJugPostulados() {
        return jugPostulados;
    }

    /**
     * @param jugPostulados the jugPostulados to set
     */
    public void setJugPostulados(List<Usuario> jugPostulados) {
        this.jugPostulados = jugPostulados;
    }

    /**
     * @return the estado
     */
    public Boolean getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    /**
     * @return the obsVacante
     */
    public String getObsVacante() {
        return obsVacante;
    }

    /**
     * @param obsVacante the obsVacante to set
     */
    public void setObsVacante(String obsVacante) {
        this.obsVacante = obsVacante;
    }

    /**
     * @return the obsEstablecimiento
     */
    public String getObsEstablecimiento() {
        return obsEstablecimiento;
    }

    /**
     * @param obsEstablecimiento the obsEstablecimiento to set
     */
    public void setObsEstablecimiento(String obsEstablecimiento) {
        this.obsEstablecimiento = obsEstablecimiento;
    }

    /**
     * @return the sexo
     */
    public Sexo getSexo() {
        return sexo;
    }

    /**
     * @param sexo the sexo to set
     */
    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }
    
    
    
    
    
}
