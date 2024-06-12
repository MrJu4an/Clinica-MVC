package dh.backend.clinicamvc.repository;

import dh.backend.clinicamvc.entity.Turno;
import org.springframework.cglib.core.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ITurnoRepository extends JpaRepository<Turno, Integer> {
    //Buscar turnos entre dos fechas
    @Query("Select t from Turno t where t.fecha BETWEEN :startDate and :endDate")
    List<Turno> buscarTurnosPorFecha(@Param("startDate") LocalDate startDate,
                                     @Param("endDate") LocalDate endDate);

    //Buscar turnos mayores a la fecha actual
    @Query("Select t from Turno t where t.fecha > current_date() ")
    List<Turno> buscarTurnosMayoresFecha();
}
