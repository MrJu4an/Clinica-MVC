package dh.backend.clinicamvc.service;

import dh.backend.clinicamvc.entity.Odontologo;
import dh.backend.clinicamvc.exception.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

public interface IOdontologoService {
    Odontologo registrarOdontologo(Odontologo odontologo);

    Optional<Odontologo> buscarPorId(Integer id);

    List<Odontologo> buscarTodos();

    void actualizarOdontologo(Odontologo odontologo) throws ResourceNotFoundException;

    void eliminarOdontologo(Integer id) throws ResourceNotFoundException;

    //Metodos con HQL
    List<Odontologo> buscarPorApellido(String apellido);
    List<Odontologo> buscarPorNombre(String nombre);
}
