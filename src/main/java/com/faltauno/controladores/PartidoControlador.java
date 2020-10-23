package com.faltauno.controladores;

import com.faltauno.entidades.Partido;
import com.faltauno.entidades.Usuario;
import com.faltauno.errores.ErrorServicio;
import com.faltauno.repositorios.PartidoRepositorio;
import com.faltauno.servicios.PartidoServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class PartidoControlador {

    @Autowired
    private PartidoRepositorio partidoRepositorio;

    @Autowired
    private PartidoServicio partidoServicio;

    @GetMapping("/partidos")
    public String partidos(ModelMap modelo) {
        modelo.put("tittle", "Partidos - NosFalta1");
        return "partidos.html";
    }
    
    @GetMapping("/modificar_partido")
    public String modificar_partido(ModelMap modelo){
        modelo.put("tittle", "Modificar Partido - NosFalta1");
        return "modificar_partido";
    }

    @PostMapping("/listar-postulados")
    public String listarpostulados(ModelMap modelo, @RequestParam String idpartido) throws ErrorServicio {
        try {
            List<Usuario> postulados = partidoRepositorio.findById(idpartido).get().getJugPostulados();
            modelo.put("listado-postulados", postulados);
        } catch (Exception ex) {
            modelo.put("mensaje", ex.getMessage());
            return "listado-postulados";
        }
        return "listado-postulados";
    }
    
    @GetMapping("/listarconfirmados")
    public String listarConfirmados(ModelMap modelo, @PathVariable String idPartido) throws ErrorServicio {
        Partido partido = partidoServicio.traerPartido(idPartido);
        List<Usuario> listaConfirmados = partidoServicio.listarConfirmados(partido);
        modelo.put("confirmados", listaConfirmados);
        return "/partido/listado-confirmados.html";
    }

    @PostMapping("/confirmar-postulado")
    public String confirmarpostulado(ModelMap modelo, @RequestParam String idpartido, @RequestParam String idpostulado) throws ErrorServicio {

        try {
            //Consulto si ya no estan completo los postulados y guardo confirmado
            if (partidoServicio.validarVacantes(partidoServicio.traerPartido(idpartido))) {

                partidoServicio.confirmarPostulado(partidoServicio.traerPartido(idpartido), idpartido);
                modelo.put("mensaje", "El jugador fue confirmado con exito");

            } else {
                modelo.put("mensaje", "Ya no hay mas vacantes");
            } //<< muestro mensaje caso contrario            
        } catch (ErrorServicio ex) {
            modelo.put("mensaje", ex.getMessage());
            return "listado-postulados";
        }

        //vuelvo a cargar postulados para mostrar
        List<Usuario> postulados = partidoRepositorio.findById(idpartido).get().getJugPostulados();

        modelo.put("listado-postulados", postulados);
        return "listado-postulados";
    }
    
    @PostMapping("/eliminarpostulado")
    public String eliminarPostulado(ModelMap modelo, @PathVariable String idPartido, @PathVariable String idUsuario) throws ErrorServicio {
        partidoServicio.eliminaPostulado(idPartido, idUsuario);
        Partido partido = partidoServicio.traerPartido(idPartido);
        List<Usuario> listaConfirmados = partidoServicio.listarConfirmados(partido);
        modelo.put("confirmados", listaConfirmados);
        return "/partido/listado-confirmados.html";
    }

    @PostMapping("/postularse")
    public String postularse(ModelMap modelo, @RequestParam String idpartido, @RequestParam String idpostulado) throws ErrorServicio {

        try {
            partidoServicio.cargarPostulado(partidoServicio.traerPartido(idpartido), idpostulado);

            //Consulto si ya no estan completo los postulados y guardo confirmado
            if (partidoServicio.validarVacantes(partidoServicio.traerPartido(idpartido))) {

                partidoServicio.confirmarPostulado(partidoServicio.traerPartido(idpartido), idpartido);
                modelo.put("mensaje", "El jugador fue confirmado con exito");

            } else {
                modelo.put("mensaje", "Ya no hay mas vacantes");
            } //<< muestro mensaje caso contrario

            //vuelvo a cargar postulados para mostrar
            List<Usuario> postulados = partidoRepositorio.findById(idpartido).get().getJugPostulados();
            modelo.put("listado-postulados", postulados);

        } catch (ErrorServicio ex) {
            modelo.put("mensaje", ex.getMessage());
            return "listado-postulados";
        }

        return "listado-postulados";
    }

}
