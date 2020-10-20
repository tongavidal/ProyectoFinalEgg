
package com.faltauno.repositorios;

import com.faltauno.entidades.Asistencia;
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
public interface AsistenciaRepositorio extends JpaRepository<Asistencia, String>{
    
    @Query("SELECT a FROM Asistencia a WHERE a.usuario.id= :idUsuario")
    public List<Asistencia> listarPartdosDelUsuario(@Param("idUsuario") String idUsuario);
    
}