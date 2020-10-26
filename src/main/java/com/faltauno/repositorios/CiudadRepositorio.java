
package com.faltauno.repositorios;

import com.faltauno.entidades.Ciudad;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Emi Garcia
 */
@Repository
public interface CiudadRepositorio extends JpaRepository<Ciudad, String> {
    
    @Query("SELECT c FROM Ciudad c WHERE c.pais = :pais")
    public List<Ciudad> buscarCiudadPorPais(@Param("pais")String pais);
    
    @Query("SELECT c FROM Ciudad c WHERE c.nombre like :nombre")
    public Ciudad buscarCiudadPorNombre(@Param("nombre")String nombre);
    
}
