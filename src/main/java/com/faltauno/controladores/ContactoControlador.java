package com.faltauno.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ContactoControlador {

    @GetMapping("/contacto")
    public String contacto(){
        return "contacto";
    }
        
}
