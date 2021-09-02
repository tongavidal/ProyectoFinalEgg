package com.faltauno.controladores;

import com.faltauno.errores.ErrorServicio;
import com.faltauno.servicios.PartidoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class PortalControlador {
        
        @Autowired
        private PartidoServicio partidoServicio;
	
	@GetMapping("/")
	public String index(ModelMap modelo) throws ErrorServicio {
            
                modelo.put("domingo", partidoServicio.buscarPartidosXDia("domingo"));
                modelo.put("lunes", partidoServicio.buscarPartidosXDia("lunes"));
                modelo.put("martes", partidoServicio.buscarPartidosXDia("martes"));
                modelo.put("miercoles", partidoServicio.buscarPartidosXDia("miercoles"));
                modelo.put("jueves", partidoServicio.buscarPartidosXDia("jueves"));
                modelo.put("viernes", partidoServicio.buscarPartidosXDia("viernes"));
                modelo.put("sabado", partidoServicio.buscarPartidosXDia("sabado"));
		modelo.put("title", "Home - NosFalta1");
		
		return "index.html";
	}
        
                
        @GetMapping("/listarlocalidad")
        public String listarlocalidad(){
            return "listar-localidad.html";
        }
        
        @GetMapping("/ver_perfil")
        public String ver_perfil(){
            return "ver_perfil.html";
        }

}
