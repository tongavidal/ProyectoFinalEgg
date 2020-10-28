/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.faltauno.controladores;

import com.faltauno.entidades.Foto;
import com.faltauno.errores.ErrorServicio;
import com.faltauno.servicios.FotoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author FALTA1
 */
@Controller
@RequestMapping("/")
public class FotoControlador {
	
	@Autowired
	private FotoServicio fotoServicio;

	@GetMapping("/upload/{id}")
	public ResponseEntity<byte[]> foto(ModelMap modelo, @PathVariable String id) {
		Foto foto = null;
		try {
			foto = fotoServicio.buscarPorId(id);
			modelo.put("nombre", foto.getNombre());
		} catch (ErrorServicio e) {
			modelo.put("error", e.getMessage());
			
			
		}
		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.IMAGE_PNG);
		return new ResponseEntity<>(foto.getContenido(), headers, HttpStatus.OK);
	}

}

