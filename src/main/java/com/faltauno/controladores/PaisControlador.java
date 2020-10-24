
package com.faltauno.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.faltauno.servicios.PaisServicio;
import com.faltauno.errores.ErrorServicio;

@Controller
@RequestMapping("/pais")
public class PaisControlador {

	@Autowired
	private PaisServicio paisServicio;

	@PreAuthorize("hasAnyRole('ROLE_USUARIO_ADMIN')")
	@GetMapping("/alta-pais")
	public String alta_pais(ModelMap modelo) {
		modelo.put("title", "Cargar País - NosFalta1");
		
		return "alta-pais.html";
	}


	@PreAuthorize("hasAnyRole('ROLE_USUARIO_ADMIN')")
	@PostMapping("crear-pais")
	public String registrarPais(ModelMap modelo, @RequestParam String nombrePais) {
		
		try {
			paisServicio.registrarPais(nombrePais);
		} catch (ErrorServicio e) {
			modelo.put("mensajeerror", e.getMessage());
			modelo.put("nombre", nombrePais);
			
			return "alta-pais.html";
		}
		modelo.put("mensajeexito", "El país fue agregado satisfactoriamente!");
		modelo.put("title", "País cargado con Éxito - NosFalta1");
		
		return "alta-pais.html";
	}
	
}
