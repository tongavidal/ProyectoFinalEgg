
package com.faltauno.controladores;

import com.faltauno.errores.ErrorServicio;
import com.faltauno.servicios.ReputacionServicio;
import com.faltauno.servicios.UsuarioServicio;
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
    private UsuarioServicio usuarioServicio;
    
    @GetMapping("/calificar")
    public String calificar(ModelMap modelo){
        modelo.put("title", "Calificar - NosFalta1");
        return "calificar.html";
    }
    
    @PostMapping("/calificar_usuario")
    public String calificar_usuario(ModelMap modelo,@RequestParam String idUsuario,@RequestParam Integer puntualidad,@RequestParam Integer habilidad,@RequestParam Integer fairPlay){
        try {
            reputacionServicio.agregarReputacion(puntualidad, habilidad, fairPlay);
            usuarioServicio.agregarReputacionUsuario(idUsuario, reputacionServicio.devolverReputacion(idUsuario));
        } catch (ErrorServicio e) {
            modelo.put("mensajeerror",e.getMessage());
        }
        return "calificar.html";
    }
}
