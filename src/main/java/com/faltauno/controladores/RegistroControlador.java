package com.faltauno.controladores;

import com.faltauno.errores.ErrorServicio;
import com.faltauno.servicios.UsuarioServicio;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/registrar_usuario")
public class RegistroControlador {
    
    @Autowired
    UsuarioServicio usuarioServicio;
	
    @PostMapping("/registrarUsuario")
    public String registrarUsuario(ModelMap modelo, @RequestParam String nombre, @RequestParam String apellido, @RequestParam String edad,
            @RequestParam String mail, @RequestParam String clave, ) throws ErrorServicio{
        try{
            usuarioServicio.registrarUsuario(nombre, apellido, mail, clave);
        }catch(ErrorServicio ex){
            modelo.put("error", ex.getMessage());
            Logger.getLogger(RegistroControlador.class.getName()).log(Level.SEVERE, null, ex);
            modelo.put("nombre", nombre);
            modelo.put("mail", mail);
            
            return "cargar_usuario";
        }
        return "exito_usuario.html";
    }

}
