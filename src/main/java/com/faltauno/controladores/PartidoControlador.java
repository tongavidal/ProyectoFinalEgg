
package com.faltauno.controladores;

import com.faltauno.entidades.Establecimiento;
import com.faltauno.entidades.Usuario;
import com.faltauno.enumeraciones.Sexo;
import com.faltauno.errores.ErrorServicio;
import com.faltauno.servicios.EstablecimientoServicio;
import com.faltauno.servicios.PartidoServicio;
import com.faltauno.servicios.UsuarioServicio;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/partido")
public class PartidoControlador {
    
    @Autowired
    private EstablecimientoServicio establecimientoServicio;
    
//    @Autowired
//    private LocalidadServicio localidadServicio;
    @Autowired
    private UsuarioServicio usuarioServicio;
    
    @Autowired
    private PartidoServicio partidoServicio;
    
    @GetMapping("/alta-partido")
    public String registrarPartido(HttpSession session,ModelMap modelo){
        List<Establecimiento> establecimientos=establecimientoServicio.listaEstablecimientos();
        modelo.put("establecimientos", establecimientos);
//        List<Localidad> localidades=localidadServicio.listaLocalidades();
//        modelo.put("localidades", establecimientos);
        modelo.put("sexo", Sexo.values());
        return "HTML";
    }
    
    @PostMapping("/alta-partido-ok")
    public String registroPartido(HttpSession session,ModelMap modelo,@RequestParam String idLocalidad,@RequestParam Integer cantJugador,@RequestParam String horario,@RequestParam Integer cantVacantes, @RequestParam Double precio, @RequestParam String obsVacante,@RequestParam String obsEstablecimiento,@RequestParam Sexo sex,@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd")Date fecha){
        try{
            Usuario creador=(Usuario)session.getAttribute("usuariosession");
            partidoServicio.crearPartido(idlocalidad, cantJugador, cantJugador, Integer.parseInt(horario), cantVacantes, precio,creador, obsVacante, obsEstablecimiento, sex, fecha);
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }
        
        return"html";
    }
    
    
}
