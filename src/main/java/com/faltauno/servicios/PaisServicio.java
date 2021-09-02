package com.faltauno.servicios;

import com.faltauno.entidades.Pais;
import com.faltauno.errores.ErrorServicio;
import com.faltauno.repositorios.PaisRepositorio;
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
public class PaisServicio {

    @Autowired
    private PaisRepositorio paisRepositorio;

    @Transactional
    public void registrarPais(String nombre) throws ErrorServicio {
        verificarDatosPais(nombre);
        Pais pais = new Pais();
        pais.setNombre(nombre);
        paisRepositorio.save(pais);
    }

    public void editarPais(String nombre) throws ErrorServicio {
    	verificarDatosPais(nombre);
        Pais pais = buscarPaisPorNombre(nombre);
        pais.setNombre(nombre);
        paisRepositorio.save(pais);
    }

    public void eliminarPais(String id) throws ErrorServicio {
        Pais pais = buscarPaisPorId(id);
        paisRepositorio.delete(pais);
    }

    // verifico que los datos del pais no estén vacios
    public void verificarDatosPais(String nombre) throws ErrorServicio {
        if (nombre == null || nombre.isEmpty()) {
            throw new ErrorServicio("El nombre del país no puede estar vacio");
        }
        if (paisRepositorio.buscarPaisPorNombre(nombre) != null) {
            throw new ErrorServicio("Ya existe el pais " + nombre);
        }
        
    }

    @Transactional
    public List<Pais> listarPaises() {
        List<Pais> listaPaises = paisRepositorio.findAll();
        return listaPaises;
    }

    //verifico que existe un pais con el id
    @Transactional
    public Pais verficarExistenciaPais(String id) throws ErrorServicio {
        Optional<Pais> respuesta = paisRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Pais pais = respuesta.get();
            return pais;
        } else {
            throw new ErrorServicio("No existe el pais");
        }
    }

    @Transactional
    public Pais buscarPaisPorNombre(String nombre) throws ErrorServicio {
        Pais pais = paisRepositorio.buscarPaisPorNombre(nombre);
        if (pais == null) {
            throw new ErrorServicio("No existe el pais " + nombre);
        } else {
            return pais;
        }
    }
    
    @Transactional
    public Pais buscarPaisPorId(String id) throws ErrorServicio {
        Pais pais = paisRepositorio.buscarPaisPorId(id);
        if (pais == null) {
            throw new ErrorServicio("No existe el pais " + pais.getNombre());
        } else {
            return pais;
        }
    }
    
}

