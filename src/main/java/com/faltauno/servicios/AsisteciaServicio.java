package com.faltauno.servicios;

import com.faltauno.entidades.Asistencia;
import com.faltauno.entidades.Usuario;
import com.faltauno.repositorios.AsistenciaRepositorio;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Emi Garcia
 */
@Service
public class AsisteciaServicio {

    @Autowired
    private AsistenciaRepositorio asistenciaRepositorio;
    @Autowired
    private UsuarioServicio usuarioServicio;
    //@Autowired 
    //private PartidoServicio partidoServicio;

    //Cuando un jugador es aceptado a un partido, este método asocia el usuario al partido.
    //Luego, en otro método se determina si aistió al partido o no.  
    @Transactional
    public void asociarPartido(String idUsuario, String idPartido) {
        //un método que busque un usuario por id y me devuelva un jugador
        //Usuario usuario = usuarioServicio;
        //un método que busque un partido por id y me devuelva un partido
        //Partido partido = ;
        Asistencia asistencia = new Asistencia();
        //asistencia.setUsuario(usuario);
        //asistencia.setPartido(partido);
        asistencia.setAsistencia(null);
        asistenciaRepositorio.save(asistencia);
    }

    //Luego jugado el partido, el creador determina si el usuario que se aceptó asistió
    //al partido.
    @Transactional
    public void registarAsistencia(String idAsistencia, Boolean asistio) {
        Asistencia asistencia = asistenciaRepositorio.findById(idAsistencia).get();
        asistencia.setAsistencia(asistio);
        asistenciaRepositorio.save(asistencia);
    }

    public Integer calcularPorcentajeAsistencia(String idUsuario) {
        Integer cantPart = calcularCantPart(idUsuario);
        Integer asistencia = calcularAsistencia(idUsuario);
        Integer porcAsistencia = (asistencia/cantPart)*100;
        return porcAsistencia;
    }

    public Integer calcularCantPart(String idUsuario) {
        List<Asistencia> listaAsistencia = asistenciaRepositorio.listarPartdosDelUsuario(idUsuario);
        Integer cantPart = 0;
        for (Asistencia a : listaAsistencia) {
            cantPart++;
        }
        return cantPart;
    }

    public Integer calcularAsistencia(String idUsuario) {
        List<Asistencia> listaAsistencia = asistenciaRepositorio.listarPartdosDelUsuario(idUsuario);
        Integer asistencia = 0;
        for (Asistencia a : listaAsistencia) {
            if (a.getAsistencia() == true) {
                asistencia++;
            }
        }
        return asistencia;
    }

}

