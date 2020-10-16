package com.faltauno.servicios;

import com.faltauno.entidades.Ciudad;
import com.faltauno.repositorios.CiudadRepositorio;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Emi Garcia
 */

@Service
public class CiudadServicio {

    @Autowired
    public CiudadRepositorio ciudadRepositorio;

    @Transactional
    public void registrarCiudad(String nombre, String pais) {
        validarDatosCiudad(nombre, pais);
        verificarDuplicado(nombre, pais);
        Ciudad ciudad = new Ciudad();
        ciudad.setNombre(nombre);
        ciudad.setNombre(pais);
        ciudadRepositorio.save(ciudad);
    }
    
    @Transactional
    public void editarCiudad (String id, String nombre, String pais){
        Ciudad ciudad = verificarExistenciaCiudad(id);
        ciudad.setNombre(nombre);
        //Acá tengo que devolver el pais;
        ciudadRepositorio.save(ciudad);
    }
    
    @Transactional
    public void eliminarCiudad(String id){
        Ciudad ciudad = verificarExistenciaCiudad(id);
        ciudadRepositorio.delete(ciudad);
    }
    
    //Verifico que ya existe la ciudad con los mismos valores(nombre, pais)
    public Ciudad verificarExistenciaCiudad(String id) throws ErrorServicio{
        Optional<Ciudad> respuesta = ciudadRepositorio.findById(id);
        if(respuesta.isPresent()) {
            Ciudad ciudad = respuesta.get();
            return ciudad;
        }
        throw new ErrorServicio("La ciudad que intenta editar no existe");
    }
    
    //Verifico que no existe dos ciudades con el mismo nombre en un país
    public void verificarDuplicadoCiudad(String nombre, String pais) throws ErrorServicio {
        List<Ciudad> listaCiudades = ciudadRepositorio.buscarCiudadPorPais(pais);
        for (Ciudad c : listaCiudades) {
            if (c.getNombre() == nombre) {
                throw new ErrorServicio("Ya existe la ciudad " + nombre + " en " + pais);
            }
        }
    }

    //Verifico que los datos no estén vacios
    public void validarDatosCiudad(String nombre, String pais) throws ErrorSerivicio {
        if (nombre == null || nombre.isEmpty()) {
            throw new ErrorServicio("El nombre de la Ciudad no puede estar vacio")
        }
        if (pais == null || pais.isEmpty()) {
            throw new ErrorSerivicio("El país no puede estar vacio")
        }
    }

}
