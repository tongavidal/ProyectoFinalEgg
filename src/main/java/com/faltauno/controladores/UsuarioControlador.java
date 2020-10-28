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
import com.faltauno.enumeraciones.Sexo;
import com.faltauno.errores.ErrorServicio;
import com.faltauno.repositorios.UsuarioRepositorio;
import com.faltauno.servicios.LocalidadServicio;
import com.faltauno.servicios.ReputacionServicio;
import com.faltauno.servicios.UsuarioServicio;
import java.util.List;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequestMapping("/usuario")
public class UsuarioControlador {

    @Autowired
    private UsuarioServicio usuarioServicio;
    
    @Autowired
    private ReputacionServicio reputacionServicio;
    
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    
    @Autowired
    private LocalidadServicio localidadServicio;

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, ModelMap modelo) {
        modelo.put("title", "Ingresá - Tu Biblioteca");
        List<Localidad> localidades = localidadServicio.listarTodasLocalidads();
        modelo.put("localidades", localidades);
        modelo.put("sexo", Sexo.values());
        if (error != null) {
            modelo.put("error", "Nombre de usuario o clave incorrectos.");
            return "registrarse.html";
        }
        return "registrarse.html";
    }

    @PostMapping("/loguinchek")
    public String loguinchek() {
        return "index.html";
    }

    @GetMapping("/logout")
    public String logout() {
        return "index.html";
    }

    @GetMapping("/registrarse")
    public String registrarse(ModelMap modelo) {
        modelo.put("title", "Registrarse - NosFalta1");
        List<Localidad> localidades = localidadServicio.listarPaises();
        modelo.put("localidades", localidades);
        modelo.put("sexo", Sexo.values());
        return "registrarse.html";
    }

    @PostMapping("/registrar")
    public String registrar(ModelMap modelo, MultipartFile archivo, @RequestParam String nombre, @RequestParam String apellido, @RequestParam String edad, @RequestParam String mail,
            @RequestParam Localidad localidad, @RequestParam String clave, String clave1) {

        try {
            usuarioServicio.registrarUsuario(archivo,nombre, apellido, edad, localidad, mail, clave, clave1);
        } catch (ErrorServicio e) {
            modelo.put("errorRegistrarse", e.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("apellido", apellido);
            modelo.put("edad", edad);
            modelo.put("mail", mail);
            List<Localidad>listaLocalidades = localidadServicio.listarTodasLocalidads();
            modelo.put("localidades", listaLocalidades);
            modelo.put("sexo", Sexo.values());
            modelo.put("clave", clave);
            modelo.put("clave1", clave1);

            return "registrarse.html";
        }

        modelo.put("title", "Te has registrado Correctamente! - Tu Biblioteca");
        modelo.put("titulo", "Felicitaciones!!");
        modelo.put("descripcion", "Tu registro como usuario se ha realizado con éxito.");
        return "index.html";
    }
    
    @GetMapping("/editar-perfil/{idusuario}")
    public String editarPerfiGet(ModelMap modelo, @PathVariable String idusuario){
        modelo.put("title", "Editar Perfil - NosFalta1");
        return "editar-perfil.html";
    }
    
    @PostMapping("editar-perfil/{idusuario}")
    public String editarPerfilPost(ModelMap modelo, @PathVariable String idusuario, @RequestParam String nombre, @RequestParam String apellido,
            @RequestParam String edad, @RequestParam String idLocalidad, @RequestParam String mail, @RequestParam String clave, @RequestParam String clave1) throws ErrorServicio{
        try{
            usuarioServicio.modificarUsuario(idusuario, nombre, apellido, edad, idLocalidad, mail, clave, clave1);
            return ("listar-perfil.html");
        }catch (ErrorServicio es) {
            modelo.put("error", es.getMessage());
            return "editar-perfil.html";
        }
    }
    
    @GetMapping("/buscar-usuario")
    public String buscarUsuarioGet(ModelMap modelo){
        modelo.put("title", "Buscar Usuario - NosFalta1");
        return "buscar-usuario";
    }
    
    @GetMapping("/ver-perfil/{idUsuario}")
    public String verPerfil(ModelMap modelo,@PathVariable String idUsuario){
        modelo.put("title", "Perfil - NosFalta1");
        modelo.put("usuario",usuarioRepositorio.getOne(idUsuario));
        modelo.put("reputacionU", reputacionServicio.promedioReputacion(idUsuario));
        return "ver-perfil.html";
    }
    
    
    
}
