package dh.backend.clinicamvc.repository;

import dh.backend.clinicamvc.entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IPacienteRepository extends JpaRepository<Paciente, Integer> {

    //Buscar pacientes por DNI
    @Query("Select p from Paciente p where p.dni = :dni")
    Paciente buscarPorDNI(@Param("dni") String dni);

    //Buscar pacientes por direccion
    @Query("Select p from Paciente p INNER JOIN p.domicilio d WHERE d.provincia = LOWER(:provincia)")
    List<Paciente> buscarPacientesProvincia(@Param("provincia") String provincia);
}
