/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.faltauno.repositorios;

import com.faltauno.entidades.Reputacion;
import com.faltauno.entidades.Usuario;
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
public interface UsuarioRepositorio extends JpaRepository<Usuario, String>{
    
    @Query("Select u from Usuario u where u.mail = :mail")
    public Usuario buscarUsuarioPorMail(@Param("mail")String mail);
    
    @Query("SELECT u.reputacion from Usuario u WHERE u.id=:id")
    public List<Reputacion> reputaciones(@Param("id")String id);
    
}
