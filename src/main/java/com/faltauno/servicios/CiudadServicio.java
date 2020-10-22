package com.faltauno.servicios;

import com.faltauno.entidades.Ciudad;
import com.faltauno.errores.ErrorServicio;
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
    @Autowired
    public PaisServicio paisServicio;

    @Transactional
    public void registrarCiudad(String nombre, String nombrePais) throws ErrorServicio {
        validarDatosCiudad(nombre, nombrePais);
        verificarDuplicadoCiudad(nombre, nombrePais);
        Ciudad ciudad = new Ciudad();
        ciudad.setNombre(nombre);
        ciudad.setPais(paisServicio.buscarPaisPorNombre(nombrePais));
        ciudadRepositorio.save(ciudad);
    }
    
    @Transactional
    public void editarCiudad (String id, String nombre, String nombrePais) throws ErrorServicio{
        Ciudad ciudad = verificarExistenciaCiudad(id);
        ciudad.setNombre(nombre);
        ciudad.setPais(paisServicio.buscarPaisPorNombre(nombre));
        ciudadRepositorio.save(ciudad);
    }
    
    @Transactional
    public void eliminarCiudad(String id) throws ErrorServicio{
        Ciudad ciudad = verificarExistenciaCiudad(id);
        ciudadRepositorio.delete(ciudad);
    }
    
    //Listar todas las ciudades de todos los paises
    @Transactional
    public List<Ciudad> listarTodasCiudades(){
        List<Ciudad> listaCiudades = ciudadRepositorio.findAll();
        return  listaCiudades;
    }
    
    //Listar todas las ciudades de un pais
    @Transactional
    public List<Ciudad> listarCiudadesPais(String nombrePais){
        List<Ciudad> listaCiudades = ciudadRepositorio.buscarCiudadPorPais(nombrePais);
        return listaCiudades;
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
    public void verificarDuplicadoCiudad(String nombre, String nombrePais) throws ErrorServicio {
        List<Ciudad> listaCiudades = ciudadRepositorio.buscarCiudadPorPais(nombrePais);
        for (Ciudad c : listaCiudades) {
            if (c.getNombre().equals(nombre)) {
                throw new ErrorServicio("Ya existe la ciudad " + nombre + " en " + nombrePais);
            }
        }
    }

    //Verifico que los datos no estén vacios
    public void validarDatosCiudad(String nombre, String nombrePais) throws ErrorServicio {
        if (nombre == null || nombre.isEmpty()) {
            throw new ErrorServicio("El nombre de la Ciudad no puede estar vacio");
        }
        if (nombrePais == null || nombrePais.isEmpty()) {
            throw new ErrorServicio("El país no puede estar vacio");
        }
    }

}
