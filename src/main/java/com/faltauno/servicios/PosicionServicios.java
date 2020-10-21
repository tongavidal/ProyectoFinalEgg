/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.faltauno.servicios;

import com.faltauno.entidades.Posicion;
import com.faltauno.errores.ErrorServicio;
import com.faltauno.repositorios.PosicionRepositorio;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author admin
 */
@Service
public class PosicionServicios {

    @Autowired
    private PosicionRepositorio posicionRepositorio;

    @Transactional
    public void registrarPosicion(String nombre) throws ErrorServicio {
        validarDatosDePosicion(nombre);
        verificarDuplicado(nombre);
        Posicion posicion = new Posicion();
        posicion.setPosicion(nombre);
        posicionRepositorio.save(posicion);

    }

    @Transactional
    public void editarPosicion(String id, String nombre) throws ErrorServicio {
        Posicion posicion = verificarExistencia(id);
        posicion.setPosicion(nombre);
        posicionRepositorio.save(posicion);
    }

    @Transactional
    public void eliminarPosicion(String id) throws ErrorServicio {
        Posicion posicion = verificarExistencia(id);
        posicionRepositorio.delete(posicion);
    }

    public Posicion verificarExistencia(String id) throws ErrorServicio {
        Optional<Posicion> respuesta = posicionRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Posicion posicion = respuesta.get();
            return posicion;
        }
        throw new ErrorServicio("La posicion no esta registrada");

    }
    public List<Posicion>listarPosiciones(){
        return posicionRepositorio.findAll();
    }

    public void validarDatosDePosicion(String nombre) throws ErrorServicio {
        if (nombre == null || nombre.isEmpty()) {
            throw new ErrorServicio("El nombre no puede estar vacio");

        }
    }

    public void verificarDuplicado(String nombre) throws ErrorServicio {
        List<Posicion> listaPosiciones = posicionRepositorio.findAll();

        for (Posicion posicion : listaPosiciones) {
            if (posicion.getPosicion().equals(nombre)) {
                throw new ErrorServicio("La posicion ya fue seleccionada");
            }

        }
    }

}
