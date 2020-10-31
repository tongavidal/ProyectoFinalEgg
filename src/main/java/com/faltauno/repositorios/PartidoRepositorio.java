/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.faltauno.repositorios;

import com.faltauno.entidades.Partido;
import com.faltauno.enumeraciones.Sexo;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author salmonIT
 */
@Repository
public interface PartidoRepositorio extends JpaRepository<Partido, String> {
    
    
    //buscar partido por **localidad** , estado true, fecha de partido no vencida y sexo
    @Query("Select p from Partido p where p.localidad.id = :idlocalidad and p.estado = true and p.fecha > :fechahoy and p.sexo = :sexo")
    public List<Partido> buscarPartidoPorLocalidadSexo(@Param("idlocalidad")String idlocalidad, @Param("fechahoy")Date fechahoy, @Param("sexo")Sexo sexo);    
     
    @Query("Select p from Partido p where p.localidad.id = :idlocalidad")
    public List<Partido> prueba(@Param("idlocalidad") String idlocalidad);
    
    //traer partido por creador
     @Query("Select p from Partido p where p.creador.id = :idcreador")
    public List<Partido> buscarPorCreador(@Param("idcreador")String idcreador);
    
    //buscar partido por localidad, estado true y que partido no vencido
    @Query("Select p from Partido p WHERE p.localidad.id= :idlocalidad and p.estado= true and p.fecha > :fechahoy")
    public List<Partido> buscarPartidoPorLocalidad(@Param("idlocalidad") String idlocalidad, @Param("fechahoy") Date fechahoy); 
    
    
    //buscar partidos donde un jugador se postulo
    @Query("Select p from Partido p, Usuario u where u.id= :idpostulado and p.creador.id<> :idpostulado order by p.fecha desc")
    public List<Partido> listaMisPostulaciones(@Param("idpostulado") String idPostulado);
      
    //buscar partido por una fecha y estado true
    @Query("Select p from Partido p WHERE p.estado= true and p.fecha = :fecha")
    public List<Partido> buscarPartidoPorFecha(@Param("fecha") Date fecha); 
    
    //confirmar postulado
    
    //listar postulados
    
    //listar confirmados
    
    
}
