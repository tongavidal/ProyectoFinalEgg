
package com.faltauno.controladores;

import com.faltauno.errores.ErrorServicio;
import com.faltauno.servicios.PaisServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/pais")
public class PaisControlador {
    
    @Autowired
    private PaisServicio paisServicio;
    
    @PostMapping("/alta-pais")
    public String altapais(ModelMap modelo, @RequestParam(required = false) String nombrepais) throws ErrorServicio {
        try {
            paisServicio.verificarDatosPais(nombrepais);
            paisServicio.verificarDuplicadoPais(nombrepais);
            paisServicio.registrarPais(nombrepais);
        } catch (Exception ex) {
            modelo.put("mensaje", ex.getMessage());
            return "alta-pais";
        }
        modelo.put("mensaje", "El pais se cargo con exito");
        return "alta-pais";
    }
    
    
}
