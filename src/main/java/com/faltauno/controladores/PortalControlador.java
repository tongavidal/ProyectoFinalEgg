package com.faltauno.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class PortalControlador {
	
	@GetMapping("/")
	public String index(ModelMap modelo) {
		modelo.put("title", "Home - NosFalta1");
		
		return "index.html";
	}
        
        @GetMapping("/listar_partidos")
        public String listar_partidos(){
            return "partidos2.html";
        }

}
