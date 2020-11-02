
package com.faltauno.controladores;

import com.faltauno.compuestos.PostuladoCompuesto;
import com.faltauno.entidades.Partido;
import com.faltauno.entidades.Usuario;
import com.faltauno.errores.ErrorServicio;
import com.faltauno.servicios.PartidoServicio;
import com.faltauno.servicios.ReputacionServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/reputacion")
public class ReputacionControlador {
    
    @Autowired
    private ReputacionServicio reputacionServicio;
    
    @Autowired
    private PartidoServicio partidoServicio;
    
    
    
    @GetMapping("/calificar")
    public String calificar(ModelMap modelo,@RequestParam String idpartido,@RequestParam String idconfirmado) throws ErrorServicio{
        try {
            modelo.put("title", "Calificar - NosFalta1");
            modelo.put("idPartido",idpartido);
            modelo.put("idUsuario",idconfirmado);
            reputacionServicio.comprobarCalificacion(idconfirmado, idpartido);
            return "calificar.html"; 
        } catch (ErrorServicio e) {
            modelo.put("error",e.getMessage());
            Partido partido = partidoServicio.traerPartido(idpartido);
            List<PostuladoCompuesto> listaConfirmados = partidoServicio.listarConfirmados(partido);
            modelo.put("confirmados", listaConfirmados);
            modelo.put("fecha", false);
            modelo.put("idpartido", idpartido);
           return "listado-confirmados.html";
        }
    }
    
    @PostMapping("/calificar_usuario")
    public String calificar_usuario(ModelMap modelo,@RequestParam String idPartido,@RequestParam String idUsuario,@RequestParam Integer puntualidad,@RequestParam Integer habilidad,@RequestParam Integer fairPlay){
        try {
            reputacionServicio.agregarReputacion(idUsuario,idPartido,puntualidad, habilidad, fairPlay);
            Partido partido = partidoServicio.traerPartido(idPartido);
            List<PostuladoCompuesto> listaConfirmados = partidoServicio.listarConfirmados(partido);
            modelo.put("confirmados", listaConfirmados);
            modelo.put("fecha", false);
            modelo.put("idpartido", idPartido);
            modelo.put("mensajeexito","Se calificó con exito");
           return "listado-confirmados.html";
        } catch (ErrorServicio e) {
            modelo.put("title", "Calificar - NosFalta1");
            modelo.put("idPartido",idPartido);
            modelo.put("idUsuario",idUsuario);
            modelo.put("mensajeerror",e.getMessage());
            return "calificar.html";
        }
        
    }
}
