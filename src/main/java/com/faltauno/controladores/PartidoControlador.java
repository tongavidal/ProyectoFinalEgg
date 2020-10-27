package com.faltauno.controladores;

import com.faltauno.entidades.Establecimiento;
import com.faltauno.entidades.Localidad;
import com.faltauno.entidades.Partido;
import com.faltauno.entidades.Usuario;
import com.faltauno.enumeraciones.Sexo;
import com.faltauno.errores.ErrorServicio;
import com.faltauno.repositorios.EstablecimientoRepositorio;
import com.faltauno.repositorios.PartidoRepositorio;
import com.faltauno.repositorios.UsuarioRepositorio;
import com.faltauno.servicios.EstablecimientoServicio;
import com.faltauno.servicios.LocalidadServicio;
import com.faltauno.servicios.PartidoServicio;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/partido")
public class PartidoControlador {

    @Autowired
    private PartidoRepositorio partidoRepositorio;

    @Autowired
    private PartidoServicio partidoServicio;

    
    @Autowired
    private EstablecimientoServicio establecimientoServicio;
    
    @Autowired
    private LocalidadServicio localidadServicio;
    
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    
    @GetMapping("/listar-partidos")
    public String partidos(ModelMap modelo) {
        List<Localidad> listaLocalidades = localidadServicio.listarTodasLocalidads();
        modelo.put("localidades", listaLocalidades);
        modelo.put("sexo", Sexo.values());
        modelo.put("title", "Lista de Partidos - NosFalta1");;
        return "listar-partidos.html";
    }
    
    @PostMapping("/listar-partidos-ok")
    public  String listarPartidosFiltrados(ModelMap modelo, @RequestParam String idlocalidad, @RequestParam(required = false) String Sexo){
        modelo.put("title", "Lista de Partidos - NosFalta1");
        List<Partido> listaPartidosFiltrados = partidoServicio.listarPartidosFiltrados(idlocalidad, Sexo);
        modelo.put("partidos", listaPartidosFiltrados);
        return ("listar-partidos-ok.html");
    }
    
    

    @PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
    @GetMapping("/modificar_partido/{id}")
    public String modificar_partido(@PathVariable String id,ModelMap modelo){
        List<Localidad> localidades=localidadServicio.listarPaises();
        modelo.put("localidades", localidades);
        modelo.put("sexo", Sexo.values());
        List<Establecimiento> establecimientos=establecimientoServicio.listaEstablecimientos();
        modelo.put("establecimientos", establecimientos);
        modelo.put("tittle", "Modificar Partido - NosFalta1");
        try {
            Partido partido=partidoServicio.traerPartido(id);
            modelo.addAttribute("partido",partido);
            modelo.addAttribute("horario",partidoServicio.horario(partido.getHorario()));
        } catch (ErrorServicio e) {
            modelo.addAttribute("error",e.getMessage());
        }
        return "modificar-partido.html";
    }
    
    @PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
    @PostMapping("/listar-postulados")
    public String listarpostulados(ModelMap modelo, @RequestParam String idpartido) throws ErrorServicio {
        try {
            List<Usuario> postulados = partidoRepositorio.findById(idpartido).get().getJugPostulados();
            modelo.put("listado-postulados", postulados);
        } catch (Exception ex) {
            modelo.put("mensaje", ex.getMessage());
            return "listado-postulados";
        }
        return "listado-postulados";
    }

    
    @PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
    @GetMapping("/listarconfirmados")
    public String listarConfirmados(ModelMap modelo, @PathVariable String idPartido) throws ErrorServicio {
        modelo.put("title", "Registrarse - NosFalta1");
        try {
            Partido partido = partidoServicio.traerPartido(idPartido);
            List<Usuario> listaConfirmados = partidoServicio.listarConfirmados(partido);
            modelo.put("confirmados", listaConfirmados);
            return "/partido/listado-confirmados.html";
        } catch (ErrorServicio es) {
            modelo.put("error", es.getMessage());
            return "partido.html";
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
    @PostMapping("/confirmar-postulado")
    public String confirmarpostulado(ModelMap modelo, @RequestParam String idpartido, @RequestParam String idpostulado) throws ErrorServicio {

        try {
            //Consulto si ya no estan completo los postulados y guardo confirmado
            if (partidoServicio.validarVacantes(partidoServicio.traerPartido(idpartido))) {

                partidoServicio.confirmarPostulado(partidoServicio.traerPartido(idpartido), idpartido);
                modelo.put("mensaje", "El jugador fue confirmado con exito");

            } else {
                modelo.put("mensaje", "Ya no hay mas vacantes");
            } //<< muestro mensaje caso contrario            
        } catch (ErrorServicio ex) {
            modelo.put("mensaje", ex.getMessage());
            return "listado-postulados";
        }

        //vuelvo a cargar postulados para mostrar
        List<Usuario> postulados = partidoRepositorio.findById(idpartido).get().getJugPostulados();

        modelo.put("listado-postulados", postulados);
        return "listado-postulados";
    }

    
    @PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
    @PostMapping("/eliminarpostulado")
    public String eliminarPostulado(ModelMap modelo, @PathVariable String idPartido, @PathVariable String idUsuario) throws ErrorServicio {
        modelo.put("title", "Registrarse - NosFalta1");
        try {
            partidoServicio.eliminaPostulado(idPartido, idUsuario);
            Partido partido = partidoServicio.traerPartido(idPartido);
            List<Usuario> listaConfirmados = partidoServicio.listarConfirmados(partido);
            modelo.put("confirmados", listaConfirmados);
            return "listado-confirmados.html";
        } catch (ErrorServicio es) {
            modelo.put("error", es.getMessage());
            return ("listado-postulados");
        }
    }
    @GetMapping("/postularse")
    public String postularse(ModelMap modelo, @RequestParam String idpartido, @RequestParam String idpostulado) throws ErrorServicio {

        try {
        	Partido partido = partidoServicio.traerPartido(idpartido);
            partidoServicio.cargarPostulado(partido, idpostulado);

            //Consulto si ya no estan completo los postulados y guardo confirmado
            if (partidoServicio.validarVacantes(partidoServicio.traerPartido(idpartido))) {

                partidoServicio.confirmarPostulado(partidoServicio.traerPartido(idpartido), idpostulado);
                modelo.put("mensaje", "El jugador fue confirmado con exito");

            } else {
                modelo.put("mensaje", "Ya no hay mas vacantes");
            } //<< muestro mensaje caso contrario

            //vuelvo a cargar postulados para mostrar
            List<Usuario> postulados = partidoRepositorio.findById(idpartido).get().getJugPostulados();
            modelo.put("listado-postulados", postulados);

        } catch (ErrorServicio ex) {
            modelo.put("mensaje", ex.getMessage());
            return "listado-postulados";
        }

        return "listado-postulados";
    }
    
    @PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
    @GetMapping("/alta-partido")
    public String registrarPartido(HttpSession session,ModelMap modelo){
    	modelo.put("title", "Crear Partido - NosFalta1");
        List<Establecimiento> establecimientos=establecimientoServicio.listaEstablecimientos();
        modelo.put("establecimientos", establecimientos);
        List<Localidad> localidades=localidadServicio.listarPaises();
        modelo.put("localidades", localidades);
        modelo.put("sexo", Sexo.values());
        return "alta-partido.html";
    }
    
    @PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
    @PostMapping("/crear_partido")
    public String registroPartido(HttpSession session, @RequestParam String idcreador, ModelMap modelo,@RequestParam String idEstablecimiento,@RequestParam String idLocalidad,@RequestParam Integer cantJugadores,@RequestParam String horario,@RequestParam Integer cantVacantes, @RequestParam Double precio,@RequestParam Sexo sexo,@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd")Date fecha){
        try{ 
           Usuario creador = usuarioRepositorio.getOne(idcreador);
           partidoServicio.crearPartido(idEstablecimiento,idLocalidad, cantJugadores, partidoServicio.horario(horario), cantVacantes, precio,creador, sexo, fecha);
        }catch(ErrorServicio ex){
            List<Establecimiento> establecimientos=establecimientoServicio.listaEstablecimientos();
            modelo.put("establecimientos", establecimientos);
            List<Localidad> localidades=localidadServicio.listarPaises();
            modelo.put("localidades", localidades);
            modelo.put("sexo", Sexo.values());
            modelo.put("mensajeerror",ex.getMessage());
            return "alta-partido.html";
        }
        modelo.put("mensajeexito","El partido fue agregado con exito");
        return "alta-partido.html";
    }

    @GetMapping("/mis-partidos/{idcreador}")
    public String misPartidos(ModelMap modelo, @PathVariable String idcreador) throws ErrorServicio {
        try {
            modelo.put("title", "Mis Partidos - NosFalta1");
            List<Partido> listaMisPartidos = partidoServicio.listarMisPartidos(idcreador);
            modelo.put("partidos", listaMisPartidos);
            return ("mis-postulaciones.html");
        } catch (ErrorServicio es) {
            modelo.put("error", es.getMessage());
            return "partido.html";
        }
    }
    
    
    @PostMapping("/modificar-partido/{id}")
    public String modificarPartido(@PathVariable String id,@RequestParam String idEstablecimiento,@RequestParam String idLocalidad,ModelMap modelo,@RequestParam String horario,@RequestParam Sexo sexo,@RequestParam Double precio,@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd")Date fecha){
        try {
            partidoServicio.modificarPartido(id, idLocalidad,idEstablecimiento,partidoServicio.horario(horario), precio, sexo, fecha);
            
        } catch (ErrorServicio e) {
            List<Localidad> localidades=localidadServicio.listarPaises();
            modelo.put("localidades", localidades);
            modelo.put("sexo", Sexo.values());
            List<Establecimiento> establecimientos=establecimientoServicio.listaEstablecimientos();
            modelo.put("establecimientos", establecimientos);
            modelo.addAttribute("error",e.getMessage());
            return "modificar-partido.html";
        }
        //modelo.put("mensajeexito","El partido fue modificado con exito");
        return "redirect:/partido/listar-partidos";
    }
    
    @PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
    @GetMapping("/eliminar_partido/{id}")
    public String eliminarPartido(@PathVariable String id,ModelMap modelo){
            try {
                partidoServicio.eliminarPartido(id);
            } catch (ErrorServicio e) {
                modelo.addAttribute("error",e.getMessage());
                return "redirect:/partido/listar-partidos";
            }
        return "redirect:/partido/listar-partidos";
    }
    
    
}
