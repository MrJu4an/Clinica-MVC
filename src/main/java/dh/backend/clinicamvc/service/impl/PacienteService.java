package dh.backend.clinicamvc.service.impl;

import dh.backend.clinicamvc.entity.Paciente;
import dh.backend.clinicamvc.exception.ResourceNotFoundException;
import dh.backend.clinicamvc.repository.IPacienteRepository;
import dh.backend.clinicamvc.service.IPacienteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PacienteService implements IPacienteService {
    private static Logger LOGGER = LoggerFactory.getLogger(PacienteService.class);
    private IPacienteRepository pacienteRepository;

    public PacienteService(IPacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    public Paciente registrarPaciente(Paciente paciente) {
        LOGGER.info("Se crea el paciente: " + paciente);
        return pacienteRepository.save(paciente);
    }

    public Optional<Paciente> buscarPorId(Integer id) {
        LOGGER.info("Se busca el paciente por id: " + id);
        return pacienteRepository.findById(id);
    }

    public List<Paciente> buscarTodos() {
        LOGGER.info("Se bucan todos los pacientes");
        return pacienteRepository.findAll();
    }

    @Override
    public void actualizarPaciente(Paciente paciente) throws ResourceNotFoundException {
        Optional<Paciente> pacienteOptional = buscarPorId(paciente.getId());
        if (pacienteOptional.isPresent()) {
            LOGGER.info("Se actualiza el paciente: " + paciente);
            pacienteRepository.save(paciente);
        } else {
            LOGGER.info("No se encontro el paciente " + paciente);
            throw new ResourceNotFoundException("{\"message\": \"paciente no encontrado\"}");
        }

    }

    @Override
    public void eliminarPaciente(Integer id) throws ResourceNotFoundException {
        Optional<Paciente> pacienteOptional = buscarPorId(id);
        if (pacienteOptional.isPresent()) {
            LOGGER.info("Se elimina el paciente con id: " + id);
            pacienteRepository.deleteById(id);
        } else {
            LOGGER.info("No se encontro el paciente con id: " + id);
            throw new ResourceNotFoundException("{\"message\": \"paciente no encontrado\"}");
        }
    }

    @Override
    public Paciente buscarPorDNI(String dni) {
        LOGGER.info("Se busca paciente por DNI: " + dni);
        return pacienteRepository.buscarPorDNI(dni);
    }

    @Override
    public List<Paciente> buscarPorProvincia(String provincia) {
        LOGGER.info("Se busca paciente por provincia: " + provincia);
        return pacienteRepository.buscarPacientesProvincia(provincia);
    }
}
