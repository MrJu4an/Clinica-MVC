package dh.backend.clinicamvc.service;

import dh.backend.clinicamvc.entity.Odontologo;
import dh.backend.clinicamvc.exception.BadRequestException;
import dh.backend.clinicamvc.exception.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

public interface IOdontologoService {
    Odontologo registrarOdontologo(Odontologo odontologo) throws BadRequestException;

    Optional<Odontologo> buscarPorId(Integer id) throws ResourceNotFoundException;

    List<Odontologo> buscarTodos() throws ResourceNotFoundException;

    void actualizarOdontologo(Odontologo odontologo) throws ResourceNotFoundException;

    void eliminarOdontologo(Integer id) throws ResourceNotFoundException;

    //Metodos con HQL
    List<Odontologo> buscarPorApellido(String apellido) throws ResourceNotFoundException;
    List<Odontologo> buscarPorNombre(String nombre) throws ResourceNotFoundException;
}
