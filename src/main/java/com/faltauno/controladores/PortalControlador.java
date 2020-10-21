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
	
	@GetMapping("/registrarse")
	public String registrarse(ModelMap modelo) {
		modelo.put("title", "Registrarse - NosFalta1");
		
		return "registrarse.html";
	}
        
        @GetMapping("/login")
        public String login(){
            return "login.html";
        }
        
        @GetMapping("/partidos")
        public String partidos(){
            return "partidos";
        }
        
        @GetMapping("/alta-partido")
        public String alta_partido(){
            return "alta-partido";
        }

}
