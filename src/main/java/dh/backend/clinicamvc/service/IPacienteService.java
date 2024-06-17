package dh.backend.clinicamvc.service;

import dh.backend.clinicamvc.entity.Paciente;
import dh.backend.clinicamvc.exception.BadRequestException;
import dh.backend.clinicamvc.exception.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface IPacienteService {
    Paciente registrarPaciente(Paciente paciente) throws BadRequestException;

    Optional<Paciente> buscarPorId(Integer id) throws ResourceNotFoundException;

    List<Paciente> buscarTodos() throws ResourceNotFoundException;

    void actualizarPaciente(Paciente paciente) throws ResourceNotFoundException;

    void eliminarPaciente(Integer id) throws ResourceNotFoundException;

    // Metodos con HQL
    Paciente buscarPorDNI(String dni) throws ResourceNotFoundException;
    List<Paciente> buscarPorProvincia(String provincia) throws ResourceNotFoundException;


}
