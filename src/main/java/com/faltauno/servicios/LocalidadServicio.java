/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.faltauno.servicios;

import com.faltauno.entidades.Localidad;
import com.faltauno.errores.ErrorServicio;
import com.faltauno.repositorios.LocalidadRepositorio;
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
public class LocalidadServicio {

@Autowired
private LocalidadRepositorio localidadRepositorio;

@Transactional
public void registrarLocalidad(String nombre) throws ErrorServicio{
        verificarDatosLocalidad(nombre);
        verificarDuplicadoLocalidad(nombre);
        Localidad localidad=new Localidad();
        localidad.setNombre(nombre);
       localidadRepositorio .save(localidad);
        
}
public void editarLocalidad(String id, String nombre) throws ErrorServicio {
        Localidad localidad= verficarExistenciaLocalidad(id);
        localidad.setNombre(nombre);
        localidadRepositorio.save(localidad);
    }

    public void eliminarlLocalidad(String id) throws ErrorServicio {
         Localidad localidad =verificarExistenciaLocalidad(id);
         localidadRepositorio.delete(localidad);
    }

    public void verificarDatosLocalidad(String nombre) throws ErrorServicio {
        if (nombre == null || nombre.isEmpty()) {
            throw new ErrorServicio("El nombre de la localidad no puede estar vacio");
        }
    }

    @Transactional
    public void verificarDuplicadoLocalidad(String nombre) throws ErrorServicio {
        if (nombre.equals(localidadRepositorio.buscarLocalidadPorNombre(nombre).equals(nombre))) {
            throw new ErrorServicio("Ya existe el pais " + nombre);
        } else {
        }
    }

    @Transactional
    public List<Localidad> listarPaises() {
        List<Localidad> listaPaises = localidadRepositorio.findAll();
        return listaPaises;
    }

    //verifico que existe un pais con el id
    @Transactional
    public Localidad verficarExistenciaLocalidad(String id) throws ErrorServicio {
        Optional<Localidad> respuesta = localidadRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Localidad localidad = respuesta.get();
            return localidad;
        } else {
            throw new ErrorServicio("No existe la locacion");
        }
    }

    @Transactional
    public Localidad buscarLocacionPorNombre(String nombre) throws ErrorServicio {
        Localidad localidad = (Localidad) localidadRepositorio.buscarLocalidadPorNombre(nombre);
        if (localidad == null) {
            throw new ErrorServicio("No existe la locacion " + nombre);
        } else {
            return localidad;
        }
    }
    
    @Transactional
    public Localidad buscarLocalidadPorId(String idLocalidad) throws ErrorServicio{
        Localidad localidad = localidadRepositorio.findById(idLocalidad).get();
        if (localidad == null){
            throw new ErrorServicio("No existe la locación");
        }
        return localidad;
    }

    public Localidad verificarExistenciaLocalidad(String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public List<Localidad> listarTodasLocalidads(){
        List<Localidad> listaLocalidades = localidadRepositorio.findAll();
        return listaLocalidades;
    }

}
