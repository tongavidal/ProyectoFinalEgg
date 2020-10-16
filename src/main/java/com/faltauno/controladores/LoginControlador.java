package com.faltauno.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/login")
public class LoginControlador {
    
    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, ModelMap model){
        if (error != null) {
            model.put("error", "Nombre de usuario o clave incorrectos.");
        }
        return "login";
    }

}
