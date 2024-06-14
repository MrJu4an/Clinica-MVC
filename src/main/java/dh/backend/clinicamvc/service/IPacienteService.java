package dh.backend.clinicamvc.service;

import dh.backend.clinicamvc.entity.Paciente;
import dh.backend.clinicamvc.exception.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface IPacienteService {
    Paciente registrarPaciente(Paciente paciente);

    Optional<Paciente> buscarPorId(Integer id);

    List<Paciente> buscarTodos();

    void actualizarPaciente(Paciente paciente) throws ResourceNotFoundException;

    void eliminarPaciente(Integer id) throws ResourceNotFoundException;

    // Metodos con HQL
    Paciente buscarPorDNI(String dni);
    List<Paciente> buscarPorProvincia(String provincia);


}
