/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.faltauno.servicios;

import com.faltauno.entidades.Localidad;
import com.faltauno.entidades.Partido;
import com.faltauno.entidades.Usuario;
import com.faltauno.enumeraciones.Sexo;
import com.faltauno.errores.ErrorServicio;
import com.faltauno.repositorios.PartidoRepositorio;
import com.faltauno.repositorios.UsuarioRepositorio;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author salmonIT
 */
@Service
public class PartidoServicio {

    @Autowired
    private PartidoRepositorio partidoRepositorio;
    
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    /* CREACION DE PARTIDO */
    @Transactional
    public void crearPartido(Localidad localidad, Integer cantJugador, Integer horario, Integer cantVacantes, Double precio, Usuario creador, String obsVacante, String obsEstablecimiento, Sexo sexo, Date fecha) {

        Partido p = new Partido();

        p.setLocalidad(localidad);
        p.setCantJugador(cantJugador);
        p.setCantVacantes(cantVacantes);
        p.setPrecio(precio);
        p.setCreador(creador);
        p.setEstado(true);
        p.setObsVacante(obsVacante);
        p.setObsEstablecimiento(obsEstablecimiento);
        p.setSexo(sexo);
        p.setFecha(fecha); //Fecha del partido
        p.setHorario(horario);//Horario del partido
        p.setFechaCreacion(new Date());

        partidoRepositorio.save(p);

    }

    //buscar partido por **localidad** , estado true, fecha de partido no vencida y sexo
    public List<Partido> buscarPartidoXLocalidad(Localidad localidad, String sexo) throws ErrorServicio {
        Date fechaHoy = new Date();
        List<Partido> busqueda = partidoRepositorio.buscarPartidoPorLocalidadSexo(localidad.getId(), fechaHoy, sexo);
        if (busqueda.isEmpty()) {
            throw new ErrorServicio("La busqueda no arrojo resultados");
        }
        return busqueda;
    }

    //traer partido por creador
    public List<Partido> buscarPartidosXCreador(Usuario creador) throws ErrorServicio {
        List<Partido> busqueda = partidoRepositorio.buscarPorCreador(creador.getId());
        if (busqueda.isEmpty()) {
            throw new ErrorServicio("La busqueda no arrojo resultados");
        }
        return busqueda;
    }

    //Traer Partido
    public Partido traerPartido(String idPartido) {
        Partido p = partidoRepositorio.findById(idPartido).get();
        return p;
    }

    //validar si hay o no vacantes
    public Boolean validarVacantes(Partido partido) {
        Boolean vacante = false;
        int cont = 0;
        for (Usuario u : partido.getJugConfirmados()) {
            cont++;
        }
        if (cont < partido.getCantVacantes()) {

            vacante = true;
        }

        return vacante;
    }

    //contar vacantes
    public Integer contarVacantes(Partido partido) {
        Integer vacante = partido.getCantVacantes();
        for (Usuario u : partido.getJugConfirmados()) {
            vacante--;
        }
        return vacante;
    }

    //cargar postulado
    @Transactional
    public void cargarPostulado(Partido partido, String idUsuario){
            
       List<Usuario> usuList = null;
       //Recorro y cargo la lista de usuarios postulados
       for (Usuario u : partido.getJugPostulados()) {            
           usuList.add(u);
        }
       //Agrego el postulado a la lista
       usuList.add(usuarioRepositorio.findById(idUsuario).get());
       //Agrego la lista actualizada
       partido.setJugPostulados(usuList);
       //Guardo el partido
       partidoRepositorio.save(partido);
    }
    
    
    //confirmar postulado
    @Transactional
    public void confirmarPostulado(Partido partido, String idUsuario){
            
       List<Usuario> usuList = null;
       //Recorro y cargo la lista de usuarios postulados
       for (Usuario u : partido.getJugConfirmados()) {            
           usuList.add(u);
        }
       //Agrego el postulado a la lista
       usuList.add(usuarioRepositorio.findById(idUsuario).get());
       //Agrego la lista actualizada
       partido.setJugConfirmados(usuList);
       //Guardo el partido
       partidoRepositorio.save(partido);
    }
    
    
    //listar postulados
    public List<Usuario> listarPostulados(Partido partido){
    List<Usuario> usuList = null;
       //Recorro y cargo la lista de usuarios postulados
       for (Usuario u : partido.getJugPostulados()) {            
           usuList.add(u);
        }        
    return usuList;
    }
        
    
    //listar confirmados
    public List<Usuario> listarConfirmados(Partido partido){
    List<Usuario> usuList = null;
       //Recorro y cargo la lista de usuarios postulados
       for (Usuario u : partido.getJugConfirmados()) {            
           usuList.add(u);
        }        
    return usuList;
    }
    
}
