
package com.faltauno.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.faltauno.entidades.Localidad;
import com.faltauno.errores.ErrorServicio;
import com.faltauno.servicios.UsuarioServicio;



@Controller
@RequestMapping("/")
public class UsuarioControlador {
	
	@Autowired
	private UsuarioServicio usuarioServicio;
	
	@GetMapping("/login")
	public String login(@RequestParam(required = false) String error, ModelMap modelo) {
		modelo.put("title", "Ingresá - Tu Biblioteca");
		if(error != null) {
			modelo.put("error", "Nombre de usuario o clave incorrectos.");
		}
		
		return "registrarse.html";
	}

	@GetMapping("/registrarse")
	public String registrarse(ModelMap modelo) {
		modelo.put("title", "Registrarse - NosFalta1");
		
		return "registrarse.html";
	}
	
	@PostMapping("/registrar")
	public String registrar(ModelMap modelo, MultipartFile archivo, @RequestParam String nombre, @RequestParam String apellido, @RequestParam String edad, @RequestParam String mail, 
			@RequestParam Localidad localidad, @RequestParam String telefono, @RequestParam String clave, String clave1) {
		
		try {
			usuarioServicio.registrarUsuario(nombre, apellido, edad, localidad, mail, clave);
		} catch (ErrorServicio e) {
			
			modelo.put("error", e.getMessage());
			modelo.put("nombre", nombre);
			modelo.put("apellido", apellido);
			modelo.put("edad", edad);
			modelo.put("mail", mail);
			modelo.put("localidad", localidad);
			modelo.put("clave", clave);
			modelo.put("clave1", clave1);
			
			return "registrarse.html";
		}
		
		modelo.put("title", "Te has registrado Correctamente! - Tu Biblioteca");
		modelo.put("titulo", "Felicitaciones!!");
		modelo.put("descripcion", "Tu registro como usuario se ha realizado con éxito.");
		return "exito.html";
	}
}
