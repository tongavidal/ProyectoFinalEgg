package com.faltauno.controladores;

import com.faltauno.entidades.Partido;
import com.faltauno.entidades.Usuario;
import com.faltauno.servicios.PartidoServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/partido")
public class PartidoControlador {

    @Autowired
    private PartidoServicio partidoServicio;

    @GetMapping("/listarconfirmados")
    public String listarConfirmados(ModelMap modelo, @PathVariable String idPartido) {
        Partido partido = partidoServicio.traerPartido(idPartido);
        List<Usuario> listaConfirmados = partidoServicio.listarConfirmados(partido);
        modelo.put("confirmados", listaConfirmados);
        return "/partido/listado-confirmados.html";
    }

    @PostMapping("/eliminarpostulado")
    public String eliminarPostulado(ModelMap modelo, @PathVariable String idPartido, @PathVariable String idUsuario) {
        partidoServicio.eliminaPostulado(idPartido, idUsuario);
        Partido partido = partidoServicio.traerPartido(idPartido);
        List<Usuario> listaConfirmados = partidoServicio.listarConfirmados(partido);
        modelo.put("confirmados", listaConfirmados);
        return "/partido/listado-confirmados.html";
    }


}
