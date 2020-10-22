/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.faltauno.repositorios;

import com.faltauno.entidades.Localidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author admin
 */
@Repository
public interface LocalidadRepositorio extends JpaRepository<Localidad, String>{    

    public Object buscarLocalidadPorNombre(String nombre);
}
