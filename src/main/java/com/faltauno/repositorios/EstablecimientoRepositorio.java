/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.faltauno.repositorios;

import com.faltauno.entidades.Establecimiento;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author FaltaUno
 */
@Repository
public interface EstablecimientoRepositorio extends JpaRepository<Establecimiento, String>{
    @Query("SELECT e FROM Establecimiento e WHERE e.localidad.id= :id")
    public List<Establecimiento> listarEstablecimientoPorLocalidad(@Param("id") String id);
}
