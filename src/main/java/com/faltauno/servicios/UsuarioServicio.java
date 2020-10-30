/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.faltauno.servicios;

import com.faltauno.entidades.Foto;
import com.faltauno.entidades.Localidad;
import com.faltauno.entidades.Posicion;
import com.faltauno.entidades.Reputacion;
import com.faltauno.entidades.Usuario;
import com.faltauno.enumeraciones.Sexo;
import com.faltauno.errores.ErrorServicio;
import com.faltauno.repositorios.UsuarioRepositorio;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author CARMEN
 */
@Service
public class UsuarioServicio implements  UserDetailsService{

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    
    @Autowired
    private NotificacionServicio notificacionServicio;
    
    @Autowired
    private LocalidadServicio localidadServicio;
    
    @Autowired
    private FotoServicio fotoServicio;
    
    @Transactional
    public void registrarUsuario(List<Posicion> posiciones,MultipartFile archivo,String nombre, String apellido, String edad, Localidad localidad, String mail, String clave, String clave1,Sexo sexo) throws ErrorServicio {

        validarRegistroUsuario(posiciones,nombre, apellido, edad, localidad, mail, clave, clave1,sexo);

        Usuario u = new Usuario();
        //u.setId(UUID.randomUUID().toString().substring(0, 8));
        u.setNombre(nombre);
        u.setApellido(apellido);
        u.setEdad(edad);
        u.setLocalidad(localidad);
        u.setMail(mail);        
        u.setAcceso("1");
        u.setFechaCreacion(new Date());
        u.setEstado(false);
        u.setSexo(sexo);
        u.setPosiciones(posiciones);
        //Encripto clave
        String encriptada = new BCryptPasswordEncoder().encode(clave);
        u.setClave(encriptada);
        
        Foto foto=fotoServicio.guardar(archivo);
        u.setFoto(foto);
        
        usuarioRepositorio.save(u);
        
       // notificacionServicio.enviar("Bienvenido a la webapp FaltaUno", "Alta de usuario Falta Uno", u.getMail());

    }

    @Transactional
    public void modificarUsuario(String id, String nombre, String apellido, String edad, String idLocalidad, String mail, String clave, String clave1) throws ErrorServicio {

                  Localidad localidad = localidadServicio.buscarLocalidadPorId(idLocalidad);
    	validarEditarUsuario(nombre, apellido, edad, localidad, mail, clave, clave1);

        Optional<Usuario> respuesta = usuarioRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Usuario u = respuesta.get();
            u.setNombre(nombre);
            u.setApellido(apellido);
            u.setMail(mail);

            //Encripto clave
            String encriptada = new BCryptPasswordEncoder().encode(clave);
            u.setClave(encriptada);

            usuarioRepositorio.save(u);
        } else {
            throw new ErrorServicio("No se encontro el usuario buscado");
        }
    }

    @Transactional
    public void bajaDeUsuario(String id) throws ErrorServicio {

        Optional<Usuario> respuesta = usuarioRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Usuario u = respuesta.get();
            u.setFechaBaja(new Date());

            usuarioRepositorio.save(u);
        } else {
            throw new ErrorServicio("No se encontro el usuario buscado");
        }
    }

    @Transactional
    public void habilitarUsuario(String id) throws ErrorServicio {

        Optional<Usuario> respuesta = usuarioRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Usuario u = respuesta.get();
            u.setFechaBaja(null);

            usuarioRepositorio.save(u);
        } else {
            throw new ErrorServicio("No se encontro el usuario buscado");
        }
    }

    public void validarRegistroUsuario(List<Posicion>posiciones,String nombre, String apellido, String edad, Localidad localidad, String mail, String clave, String clave1,Sexo sexo) throws ErrorServicio {

        if (nombre == "" || nombre.isEmpty()) {

            throw new ErrorServicio("El nombre no puede estar vacio");
        }
        if (apellido == "" || apellido.isEmpty()) {

            throw new ErrorServicio("El apellido no puede estar vacio");
        }
        if (edad == "" || edad.isEmpty()) {

            throw new ErrorServicio("La edad no puede estar vacio");
        }
        if (localidad == null) {

            throw new ErrorServicio("La localidad no puede estar vacía");
        }
        if (mail == "" || mail.isEmpty()) {

            throw new ErrorServicio("El mail no puede estar vacio");

        } else {

            if (usuarioRepositorio.buscarUsuarioPorMail(mail) != null) {
                throw new ErrorServicio("Ya existe un usuario con este mail");
            }
        }
        if (clave == "" || clave.isEmpty() || 6 > clave.length()) {

            throw new ErrorServicio("La clave no puede estar vacia y no puede ser menor a 6 digitos");
        }
        if (sexo==null) {
            throw new ErrorServicio("El campo sexo no debe ser nulo");
        }
        if (posiciones.isEmpty()) {
            throw new ErrorServicio("Seleccione al menos una posicion");
        }

    }
    
    public void validarEditarUsuario(String nombre, String apellido, String edad, Localidad localidad, String mail, String clave, String clave1) throws ErrorServicio {

        if (nombre == "" || nombre.isEmpty()) {

            throw new ErrorServicio("El nombre no puede estar vacio");
        }
        if (apellido == "" || apellido.isEmpty()) {

            throw new ErrorServicio("El apellido no puede estar vacio");
        }
        if (edad == "" || edad.isEmpty()) {

            throw new ErrorServicio("La edad no puede estar vacio");
        }
        if (localidad == null) {

            throw new ErrorServicio("La localidad no puede estar vacía");
        }
        if (mail == "" || mail.isEmpty()) {

            throw new ErrorServicio("El mail no puede estar vacio");

        } 
        if (clave == "" || clave.isEmpty() || 6 > clave.length()) {

            throw new ErrorServicio("La clave no puede estar vacia y no puede ser menor a 6 digitos");
        }

    }

    @Override
    public UserDetails loadUserByUsername(String mail){
        Usuario user = usuarioRepositorio.buscarUsuarioPorMail(mail);

        if (user != null) {

            List<GrantedAuthority> permisos = new ArrayList<>();

            GrantedAuthority p1 = new SimpleGrantedAuthority("ROLE_USUARIO_REGISTRADO");
            permisos.add(p1);
//            GrantedAuthority p2 = new SimpleGrantedAuthority("MODULO_CLIENTES");
//            permisos.add(p2);
//            GrantedAuthority p3 = new SimpleGrantedAuthority("MODULO_LIBROS");
//            permisos.add(p3);
//            GrantedAuthority p4 = new SimpleGrantedAuthority("MODULO_EDITORIALES");
//            permisos.add(p4);

            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpSession session = attr.getRequest().getSession(true);
            session.setAttribute("usuariosession", user);


           User userOK = new User(user.getMail(), user.getClave(), permisos);

            return userOK;
        } else {

            return null;
        }

    }
    
    public List<Reputacion> reputaciones(String id){
        return usuarioRepositorio.reputaciones(id);
    }
    
    public void agregarReputacionUsuario(String id,Reputacion reputacion) throws ErrorServicio{
        try {
            Usuario usuario=usuarioRepositorio.getOne(id);
            usuario.getReputacion().add(reputacion);
            usuarioRepositorio.save(usuario); 
        } catch (Exception e) {
            throw new ErrorServicio("Cosas pasaron");
        }
    }
    
    public Usuario buscarUsuarioPorId(String idUsuario){
        Usuario usuario = usuarioRepositorio.findById(idUsuario).get();
        return usuario;
    } 
}
