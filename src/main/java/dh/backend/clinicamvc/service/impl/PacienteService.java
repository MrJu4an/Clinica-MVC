package dh.backend.clinicamvc.service.impl;

import dh.backend.clinicamvc.entity.Paciente;
import dh.backend.clinicamvc.exception.BadRequestException;
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

    public Optional<Paciente> buscarPorId(Integer id) throws ResourceNotFoundException {
        Optional<Paciente> pacienteOptional = pacienteRepository.findById(id);
        if (!pacienteOptional.isPresent()) {
            LOGGER.info("No existe el paciente con el id: " + id);
            throw new ResourceNotFoundException("{\"message\": \"paciente no encontrado\"}");
        }
        LOGGER.info("Se encuentra el paciente por id: " + id);
        return pacienteOptional;
    }

    public List<Paciente> buscarTodos() throws ResourceNotFoundException {
        List<Paciente> pacientes = pacienteRepository.findAll();
        if (pacientes.isEmpty()) {
            LOGGER.info("No existen pacientes");
            throw new ResourceNotFoundException("{\"message\": \"no existen pacientes\"}");
        }
        LOGGER.info("Se encuentran todos los pacientes");
        return pacienteRepository.findAll();
    }

    public Paciente registrarPaciente(Paciente paciente) throws BadRequestException {
        Paciente pacienteRegistrar = pacienteRepository.save(paciente);
        if (paciente != null) {
            LOGGER.info("Se crea el paciente: " + paciente);
            return pacienteRegistrar;
        } else {
            LOGGER.info("Error al crear el paciente: " + paciente);
            throw new BadRequestException("{\"message\": \"Error al crear paciente, revise los datos enviados\"}");
        }

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
    public Paciente buscarPorDNI(String dni) throws ResourceNotFoundException {
        LOGGER.info("Se busca paciente por DNI: " + dni);
        Paciente paciente = pacienteRepository.buscarPorDNI(dni);
        if (paciente == null) {
            LOGGER.info("Paciente no encontrado");
            throw new ResourceNotFoundException("{\"message\": \"paciente no encontrado\"}");
        }
        return paciente;
    }

    @Override
    public List<Paciente> buscarPorProvincia(String provincia) throws ResourceNotFoundException {
        LOGGER.info("Se busca paciente por provincia: " + provincia);
        List<Paciente> pacientes = pacienteRepository.buscarPacientesProvincia(provincia);
        if (pacientes.isEmpty()){
            LOGGER.info("No se encuentran pacientes con la provincia: " + provincia);
            throw new ResourceNotFoundException("{\"message\": \"pacientes no encontrados\"}");
        }
        return pacientes;
    }
}
