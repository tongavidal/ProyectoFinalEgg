/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.faltauno.repositorios;

import com.faltauno.entidades.Usuario;
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
    
}
