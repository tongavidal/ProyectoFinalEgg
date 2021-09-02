/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.faltauno.servicios;

import com.faltauno.compuestos.PostuladoCompuesto;
import com.faltauno.entidades.Establecimiento;
import com.faltauno.entidades.Localidad;
import com.faltauno.entidades.Partido;
import com.faltauno.entidades.Posicion;
import com.faltauno.entidades.Usuario;
import com.faltauno.enumeraciones.Sexo;
import com.faltauno.errores.ErrorServicio;
import com.faltauno.repositorios.EstablecimientoRepositorio;
import com.faltauno.repositorios.LocalidadRepositorio;
import com.faltauno.repositorios.PartidoRepositorio;
import com.faltauno.repositorios.UsuarioRepositorio;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author salmonIT
 */
@Service
public class PartidoServicio {

    @Autowired
    private PartidoRepositorio partidoRepositorio;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private LocalidadRepositorio localidadRepositorio;

    @Autowired
    private EstablecimientoRepositorio establecimientoRepositorio;

    @Autowired
    private ReputacionServicio reputacionServicio;


    /* CREACION DE PARTIDO */
    @Transactional
    public void crearPartido(String idEstablecimiento, String idlocalidad, Integer cantJugador, Integer horario, Integer cantVacantes, Double precio, Usuario creador, /*String obsVacante, String obsEstablecimiento,*/ Sexo sexo, Date fecha) throws ErrorServicio {

        Localidad localidad = localidadRepositorio.getOne(idlocalidad);
        Establecimiento establecimiento = establecimientoRepositorio.getOne(idEstablecimiento);
        validadr(establecimiento, localidad, cantJugador, cantVacantes, precio, fecha);
        Partido p = new Partido();

        p.setEstablecimiento(establecimiento);
        p.setLocalidad(localidad);
        p.setCantJugador(cantJugador);
        p.setCantVacantes(cantVacantes);
        p.setPrecio(precio);
        p.setCreador(creador);
        p.setEstado(true);
        p.setObsVacante("");
        p.setObsEstablecimiento("");
        p.setSexo(sexo);
        p.setFecha(fecha); //Fecha del partido
        p.setHorario(horario);//Horario del partido
        p.setFechaCreacion(new Date());

        partidoRepositorio.save(p);

    }

    @Transactional
    public void modificarPartido(String idpartido, String idlocalidad, String idEstablecimiento, /*Integer cantJugador,*/ Integer horario, /*Integer cantVacantes,*/ Double precio, /*Usuario creador, String obsVacante, String obsEstablecimiento,*/ Sexo sexo, Date fecha) throws ErrorServicio {

        Localidad localidad = localidadRepositorio.findById(idlocalidad).get();
        Establecimiento establecimiento = establecimientoRepositorio.findById(idEstablecimiento).get();
        Partido p = partidoRepositorio.findById(idpartido).get();

        p.setLocalidad(localidad);
        p.setEstablecimiento(establecimiento);
//        p.setCantJugador(cantJugador); >> para nueva version
//        p.setCantVacantes(cantVacantes); >> para nueva version
        p.setPrecio(precio);
        //p.setCreador(creador);        
        //p.setObsVacante(obsVacante);
        //p.setObsEstablecimiento(obsEstablecimiento);
        p.setSexo(sexo);
        p.setFecha(fecha); //Fecha del partido
        p.setHorario(horario);//Horario del partido

        partidoRepositorio.save(p);
    }

    //buscar partido por **localidad** , estado true, fecha de partido no vencida y sexo
    public List<Partido> buscarPartidoXLocalidad(Localidad localidad, Sexo sexo) throws ErrorServicio {
        Date fechaHoy = new Date();
        List<Partido> busqueda = partidoRepositorio.buscarPartidoPorLocalidadSexo(localidad.getId(), fechaHoy, sexo);
        if (busqueda.isEmpty()) {
            throw new ErrorServicio("La busqueda no arrojo resultados");
        }
        return busqueda;
    }

    //traer partido por creador
    public List<Partido> buscarPartidosXCreador(Usuario creador) throws ErrorServicio {
        List<Partido> busqueda = partidoRepositorio.buscarPorCreador(creador.getId());
        if (busqueda.isEmpty()) {
            throw new ErrorServicio("La busqueda no arrojo resultados");
        }
        return busqueda;
    }

    //Traer Partido
    public Partido traerPartido(String idPartido) throws ErrorServicio {
        Partido p = partidoRepositorio.getOne(idPartido);
        return p;
    }

    //validar si hay o no vacantes
    public Boolean validarVacantes(Partido partido) throws ErrorServicio {
        Boolean vacante = false;
        int cont = 0;
        for (Usuario u : partido.getJugConfirmados()) {
            cont++;
        }
        if (cont < partido.getCantVacantes()) {

            vacante = true;
        }

        return vacante;
    }

    //contar vacantes
    public Integer contarVacantes(Partido partido) throws ErrorServicio {
        Integer vacante = partido.getCantVacantes();
        for (Usuario u : partido.getJugConfirmados()) {
            vacante--;
        }
        return vacante;
    }

    //cargar postulado
    @Transactional
    public void cargarPostulado(Partido partido, String idUsuario) throws ErrorServicio {

        verificarDuplicacionPostulado(partido, idUsuario);

        List<Usuario> usuList = new ArrayList<>();
        System.out.println();
        //Recorro y cargo la lista de usuarios postulados
        for (Usuario u : partido.getJugPostulados()) {
            usuList.add(u);
        }
        //Agrego el postulado a la lista
        usuList.add(usuarioRepositorio.getOne(idUsuario));
        //Agrego la lista actualizada
        partido.setJugPostulados(usuList);
        //Guardo el partido
        partidoRepositorio.save(partido);
    }

    @Transactional
    public void verificarDuplicacionPostulado(Partido partido, String idUsuario) throws ErrorServicio {
        List<Usuario> listaPostulados = partidoRepositorio.findById(partido.getId()).get().getJugPostulados();;
        for (Usuario u : listaPostulados) {
            if (u.getId().equals(idUsuario)) {
                throw new ErrorServicio("¿Tantas ganas de pisarla y encarar? Ya estás postulado a este partido.");
            }
        }
    }

    //confirmar postulado
    @Transactional
    public void confirmarPostulado(Partido partido, String idUsuario) throws ErrorServicio {
        boolean existe = false;
        List<Usuario> usuList = new ArrayList<>();
        //Recorro y cargo la lista de usuarios postulados
        for (Usuario u : partido.getJugConfirmados()) {
            usuList.add(u);
            //verifico si el usuario ya esta confirmado en la lista
            if (u.getId().equals(idUsuario)) {
                existe = true;
            }
        }
        if (!existe) {
            //Agrego el postulado a la lista
            usuList.add(usuarioRepositorio.getOne(idUsuario));
            //Agrego la lista actualizada
            partido.setJugConfirmados(usuList);
            //Guardo el partido
            partidoRepositorio.save(partido);
        } else {
            throw new ErrorServicio("Ni que fuera Messi! Este jugador ya está confirmado");
        }
    }

    //listar postulados
    public List<PostuladoCompuesto> listarPostulados(String idpartido) throws ErrorServicio {
    
        List<PostuladoCompuesto> postulados = new ArrayList<>();
        List<Usuario> listPostulados = partidoRepositorio.findById(idpartido).get().getJugPostulados();
        //Recorro y cargo la lista de usuarios postulados
        for (Usuario u : listPostulados) {
        	PostuladoCompuesto p = new PostuladoCompuesto();
        	
            p.setId(u.getId());
            p.setNombre(u.getNombre());
            p.setApellido(u.getApellido());
            p.setEdad(u.getEdad());
            p.setMail(idpartido);
            p.setLocalidad(u.getLocalidad().getNombre());
            p.setFoto(u.getFoto());
            p.setFechaAlta(u.getFechaCreacion());

            //Transformo posiciones en cadena de texto y lo paso
            List<Posicion> posiciones = u.getPosiciones();
            int cont = 0;
            String textPosiciones = "";
            for (Posicion pos : posiciones) {
                if (cont == 0) {
                    textPosiciones = pos.getPosicion();
                } else {
                    textPosiciones = textPosiciones + ", " + pos.getPosicion();
                }
                cont++;
            }
            //cargo finalmente las posiciones
            p.setPosiciones(textPosiciones);

            //Cargo el promedio general de su reputacion
            p.setReputacion(reputacionServicio.promedioReputacionTotal(u.getId()));

            //Consulto estado y seteto en modo texto
            if (u.getEstado()) {
                p.setEstado("Activo");
            } else {
                p.setEstado("Inactivo");
            }

            postulados.add(p);
            
        }
        return postulados;
    }

    //listar confirmados
    public List<Usuario> listarConfirmados(Partido partido) throws ErrorServicio {
        List<Usuario> usuList = new ArrayList();
        //Recorro y cargo la lista de usuarios postulados
        for (Usuario u : partido.getJugConfirmados()) {
            usuList.add(u);
        }
        return usuList;
    }

    @Transactional
    public void eliminaPostulado(String idPartido, String idUsuario) throws ErrorServicio {
        //Busco partido por id
        Partido partido = traerPartido(idPartido);
        //Busco usuario por id
        Usuario usuario = usuarioRepositorio.findById(idUsuario).get();
        //Pido la lista de jugadores confirmados del partido y le elimino el jugador
        partido.getJugPostulados().remove(usuario);
        //Guardo el partido con los cambios
        partidoRepositorio.save(partido);
    }

    public Integer horario(String horario) throws ErrorServicio {
        if (horario == null || horario.isEmpty()) {
            throw new ErrorServicio("Por favor indique un horario para el partido");
        } else {
            Integer hora = Integer.parseInt(horario.substring(0, 2));
            Integer min = Integer.parseInt(horario.substring(3, 5));
            return hora * 100 + min;
        }
    }

    public String horario(Integer horario) throws ErrorServicio {
        Integer hora = horario / 100;
        Integer min = horario % 100;
        String h = "";
        String m = "";
        if (hora < 10) {
            h = "0";
        }
        if (min < 10) {
            m = "0";
        }
        h += Integer.toString(hora);
        m += Integer.toString(min);
        return h + ":" + m;
    }

    public List<Partido> listarMisPartidos(String idCreador) throws ErrorServicio {
        List<Partido> listaMisPartidos = partidoRepositorio.buscarPorCreador(idCreador);
        if (listaMisPartidos == null) {
            throw new ErrorServicio("No tenés partidos creados");
        }
        return listaMisPartidos;
    }

    private void validadr(Establecimiento establecimiento, Localidad localidad, Integer cantJugador, Integer cantVacantes, Double precio/*, Usuario creador, String obsVacante, String obsEstablecimiento*/, Date fecha) throws ErrorServicio {
        if (establecimiento == null) {
            throw new ErrorServicio("Indique un establecimiento");
        }
        if (localidad == null) {
            throw new ErrorServicio("Indique una localidad");
        }
        if (cantJugador == null) {
            throw new ErrorServicio("La cantidad de jugadores no debe ser nula");
        }
        if (cantVacantes == null) {
            throw new ErrorServicio("Indique un nro de vacantes");
        }
        if (precio == null) {
            throw new ErrorServicio("El precio no puede estar vacio");
        }
        /*
        if (creador==null) {
            throw new ErrorServicio("Existe algun problema con su usuario");
        }*/

        if (fecha == null) {
            throw new ErrorServicio("Indique una fecha");
        }

    }

    @Transactional
    public void eliminarPartido(String id) throws ErrorServicio {
        try {
            Partido partido = partidoRepositorio.getOne(id);
            partido.setEstado(false);
            //partidoRepositorio.delete(partido);
        } catch (Exception e) {
            throw new ErrorServicio("Existe un error al eliminar");
        }
    }

    //Busca los partidos filtrados por Localidad y Sexo
    public List<Partido> listarPartidosFiltrados(String idlocalidad, Sexo sexo) throws ErrorServicio {
        Date fechahoy = new Date();
        // Determino que Query usar, según si Sexo viaja nulo
        if (sexo == null) {
            List<Partido> listaPartidosFiltrados = partidoRepositorio.buscarPartidoPorLocalidad(idlocalidad, fechahoy);
            if (listaPartidosFiltrados.isEmpty()) {
                Localidad localidad = localidadRepositorio.findById(idlocalidad).get();
                throw new ErrorServicio("No hay ningún partidos en " + localidad.getNombre() + ". Créalo tú!!");
            }
            return listaPartidosFiltrados;
        } else {
            List<Partido> listaPartidosFiltrados = partidoRepositorio.buscarPartidoPorLocalidadSexo(idlocalidad, fechahoy, sexo);
            if (listaPartidosFiltrados.isEmpty()) {
                Localidad localidad = localidadRepositorio.findById(idlocalidad).get();
                throw new ErrorServicio("No existe ningún partido " + sexo.name() + " en " + localidad.getNombre() + ". Créalo tú!!");
            }
            return listaPartidosFiltrados;
        }
    }


    public List<Partido> listarMisPostulaciones(String idusuario) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Transactional
    public void cancelarConfirmado(String idPartido, String idConfirmado) throws ErrorServicio{
        Partido partido = traerPartido(idPartido);
        for (Usuario u : partido.getJugConfirmados()) {
            if (u.getId().equals(idConfirmado)) {
                partido.getJugConfirmados().remove(u);
                break;
            }
        }
        
    }
    
    @Transactional 
    public  List<Partido> listMisPostulaciones (String idPostulado) {
        List<Partido> listaPartidos = partidoRepositorio.listaMisPostulaciones(idPostulado);
        List<Partido> listaMisPostulaciones = new ArrayList();
        for (Partido p : listaPartidos) {
            for (Usuario u : p.getJugPostulados()) {
                if (u.getId().equals(idPostulado)){
                    listaMisPostulaciones.add(p);
                    break;
                }
            }
        }
        return listaMisPostulaciones;
    }

    public boolean fecha(Date fecha) {
        Date hoy = new Date();
        return hoy.before(fecha);
    }

    //traer por el dia de la semana que viene
    public List<Partido> buscarPartidosXDia(String dia) throws ErrorServicio {

        //que dia es hoy
        Date fecha = new Date();
        //avreguaro que dia de la semana es
        String diaEs = queDiaEs(fecha);
        
        
        //Averiguar la fecha del dia elegido
        Boolean encontro = false;
        while(encontro==false){
        
          if(dia.equals(diaEs)){          
            encontro=true;
          }else{
          //sumo un dia a la fecha
          fecha=sumarRestarDiasFecha(fecha, 1);
          //Averigo que dia de la semana es
          diaEs=queDiaEs(fecha);
          }
        }
        
        //hago la consulta y devuelvo los partidos de dicha fecha
        List<Partido> listaPartidosDia = partidoRepositorio.buscarPartidoPorFecha(fecha);
        return listaPartidosDia;                
    }

    public String queDiaEs(Date date) {
 
        String diaNombre = "";
        
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        
        int dia = c.get(Calendar.DAY_OF_WEEK);
        
        if (dia == Calendar.SUNDAY) {
            diaNombre="domingo";
        }
        if (dia == Calendar.MONDAY) {
            diaNombre="lunes";
        }
        if (dia == Calendar.TUESDAY) {
            diaNombre="martes";
        }
        if (dia == Calendar.WEDNESDAY) {
            diaNombre="miercoles";
        }
        if (dia == Calendar.THURSDAY) {
            diaNombre="jueves";
        }
        if (dia == Calendar.FRIDAY) {
            diaNombre="viernes";
        }
        if (dia == Calendar.SATURDAY) {
            diaNombre="sabado";
        }
                
        return diaNombre;

    }
    
    public Date sumarRestarDiasFecha(Date fecha, int dias){
	
      Calendar calendar = Calendar.getInstance();
	
      calendar.setTime(fecha); // Configuramos la fecha que se recibe
	
      calendar.add(Calendar.DAY_OF_YEAR, dias);  // numero de días a añadir, o restar en caso de días<0

      return calendar.getTime(); // Devuelve el objeto Date con los nuevos días añadidos	 
	
 }


}
