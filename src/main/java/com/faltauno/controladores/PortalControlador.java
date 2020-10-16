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
        
        @GetMapping("/registrar_usuario")
        public String registrar_usuario(){
            return "registrar_usuario";
        }
        
        @GetMapping("/postular_partido")
        public String postular_partido(){
            return "postular_partido";
        }
        
        @GetMapping("/postular_jugador")
        public String postular_jugador(){
            return "postular_jugador";
        }
        
        @GetMapping("/postular_desafio")
        public String postular_desafio(){
            return "postular_desafio";
        }
        
        @GetMapping("/login")
        public String login_usuario(){
            return "login_usuario";
        }
        

}
