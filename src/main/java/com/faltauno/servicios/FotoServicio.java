/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.faltauno.servicios;

import com.faltauno.entidades.Foto;
import com.faltauno.errores.ErrorServicio;
import com.faltauno.repositorios.FotoRepositorio;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author FaltaUno
 */
@Service
public class FotoServicio {
    
    @Autowired
    private FotoRepositorio fotoRepositorio;
    
    @Transactional
    public Foto guardar(MultipartFile archivo) throws ErrorServicio{
        if (archivo!=null) {
            try{
                Foto foto =new Foto();
                foto.setMime(archivo.getContentType());
                foto.setNombre(archivo.getName());
                foto.setContenido(archivo.getBytes());
                if (!foto.getMime().equals("application/octet-stream")) {
                    return fotoRepositorio.save(foto);
                }
            }catch(Exception e){
                System.err.println(e.getMessage());
            }
        }
        return null;
    }
    
    @Transactional
    public Foto actualizar(String idFoto, MultipartFile archivo) throws ErrorServicio{
        if (archivo!=null) {
            try{
                Foto foto =new Foto();
                if (idFoto!=null) {
                    Optional<Foto> respuesta=fotoRepositorio.findById(idFoto);
                    if (respuesta.isPresent()) {
                        foto=respuesta.get();
                    }
                }
                foto.setMime(archivo.getContentType());
                foto.setNombre(archivo.getName());
                foto.setContenido(archivo.getBytes());

                if (!foto.getMime().equals("application/octet-stream")) {
                    return fotoRepositorio.save(foto);
                }
            }catch(Exception e){
                System.err.println(e.getMessage());
            }  
        }
        return null;
    }
    
    public Foto buscarPorId(String idFoto) throws ErrorServicio {

        return fotoRepositorio.findById(idFoto).get();
    }

}
