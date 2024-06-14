package dh.backend.clinicamvc.service.impl;

import dh.backend.clinicamvc.entity.Odontologo;
import dh.backend.clinicamvc.exception.ResourceNotFoundException;
import dh.backend.clinicamvc.repository.IOdontologoRepository;
import dh.backend.clinicamvc.service.IOdontologoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OdontologoService implements IOdontologoService {
    private static final Logger LOGGER = LoggerFactory.getLogger(OdontologoService.class);
    private IOdontologoRepository odontologoRepository;

    public OdontologoService(IOdontologoRepository odontologoRepository) {
        this.odontologoRepository = odontologoRepository;
    }

    public Odontologo registrarOdontologo(Odontologo odontologo) {
        LOGGER.info("Se registra el odontologo: " + odontologo);
        return odontologoRepository.save(odontologo);
    }

    public Optional<Odontologo> buscarPorId(Integer id) {
        LOGGER.info("Se busca el odontologo con id: " + id);
        return odontologoRepository.findById(id);
    }

    public List<Odontologo> buscarTodos() {
        LOGGER.info("Se busca todos los odontologos");
        return odontologoRepository.findAll();
    }

    @Override
    public void actualizarOdontologo(Odontologo odontologo) throws ResourceNotFoundException {
        Optional<Odontologo> odontologoOptional = buscarPorId(odontologo.getId());
        if (odontologoOptional.isPresent()) {
            LOGGER.info("Se actualiza el odontologo: " + odontologo);
            odontologoRepository.save(odontologo);
        } else {
            LOGGER.info("Odontologo no encontrado: " + odontologo);
            throw new ResourceNotFoundException("{\"message\": \"odontologo no encontrado\"}");
        }

    }

    @Override
    public void eliminarOdontologo(Integer id) throws ResourceNotFoundException {
        Optional<Odontologo> odontologoOptional = buscarPorId(id);
        if (odontologoOptional.isPresent()) {
            LOGGER.info("Se elimina el odontologo con id: " + id);
            odontologoRepository.deleteById(id);
        } else {
            LOGGER.info("Odontologo no encontrado: " + id);
            throw new ResourceNotFoundException("{\"message\": \"odontologo no encontrado\"}");
        }
    }

    @Override
    public List<Odontologo> buscarPorApellido(String apellido) {
        LOGGER.info("Se busca los odontologos por apellido: " + apellido);
        return odontologoRepository.buscarPorApellido(apellido);
    }

    @Override
    public List<Odontologo> buscarPorNombre(String nombre) {
        LOGGER.info("Se busca los odontologos por nombre: " + nombre);
        return odontologoRepository.buscarPorNombre(nombre);
    }
}
