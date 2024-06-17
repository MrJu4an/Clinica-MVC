package dh.backend.clinicamvc.service.impl;

import dh.backend.clinicamvc.entity.Odontologo;
import dh.backend.clinicamvc.exception.BadRequestException;
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

    public Optional<Odontologo> buscarPorId(Integer id) throws ResourceNotFoundException {
        Optional<Odontologo> odontologoOptional = odontologoRepository.findById(id);
        if (!odontologoOptional.isPresent()) {
            LOGGER.info("Odontologo no encontrado");
            throw new ResourceNotFoundException("{\"message\": \"odontologo no encontrado\"}");
        }
        LOGGER.info("Se encuentra el odontologo con id: " + id);
        return odontologoOptional;
    }

    public List<Odontologo> buscarTodos() throws ResourceNotFoundException {
        LOGGER.info("Se busca todos los odontologos");
        List<Odontologo> odontologos = odontologoRepository.findAll();
        if (odontologos.isEmpty()) {
            throw new ResourceNotFoundException("{\"message\": \"no existen odontologos\"}");
        }
        return odontologos;
    }

    public Odontologo registrarOdontologo(Odontologo odontologo) throws BadRequestException {
        Odontologo odontologoRegistrar = odontologoRepository.save(odontologo);
        if (odontologo != null) {
            LOGGER.info("Se registra el odontologo: " + odontologo);
            return odontologoRegistrar;
        } else {
            throw new BadRequestException("{\"message\": \"Error al crear odontologo, revise los datos enviados\"}");
        }

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
    public List<Odontologo> buscarPorApellido(String apellido) throws ResourceNotFoundException {
        LOGGER.info("Se busca los odontologos por apellido: " + apellido);
        List<Odontologo> odontologos = odontologoRepository.buscarPorApellido(apellido);
        if (odontologos.isEmpty()) {
            LOGGER.info("No existen odontologos");
            throw new ResourceNotFoundException("{\"message\": \"odontologos no encontrados\"}");
        }
        return odontologos;
    }

    @Override
    public List<Odontologo> buscarPorNombre(String nombre) throws ResourceNotFoundException {
        LOGGER.info("Se busca los odontologos por nombre: " + nombre);
        List<Odontologo> odontologos = odontologoRepository.buscarPorNombre(nombre);
        if (odontologos.isEmpty()) {
            LOGGER.info("No existen odontologos");
            throw new ResourceNotFoundException("{\"message\": \"odontologos no encontrados\"}");
        }
        return odontologos;
    }
}
