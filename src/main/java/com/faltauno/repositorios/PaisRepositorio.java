package com.faltauno.repositorios;

import com.faltauno.entidades.Pais;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Emi Garcia
 */
@Repository
public interface PaisRepositorio extends JpaRepository<Pais, String>{
    
    @Query("SELECT p FROM Pais p WHERE p.nombre like :nombre")
    public Pais buscarPaisPorNombre(@Param("nombre")String nombre);
    
    @Query("SELECT p FROM Pais p WHERE p.id = :id")
    public Pais buscarPaisPorId(@Param("id")String id);
    
}
