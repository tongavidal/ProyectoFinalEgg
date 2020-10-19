
package com.faltauno.repositorios;

import com.faltauno.entidades.Asistencia;
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
    
    @Query("SELECT count(id), usuario_id FROM asistencia WHERE usuario_id= :idUsuario")
    public Integer cantPartdosPorJugador(@Param("idUsuario") String idUsuario);
    
    @Query("SELECT count(id), usuario_id FROM asistencia WHERE usuario_id= :idUsuario and asistencia=1")
    public Integer cantuAsistencuaPorJugador(@Param("idUsuario") String idUsuario);
    

    
}
