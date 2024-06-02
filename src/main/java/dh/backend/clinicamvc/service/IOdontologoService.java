package dh.backend.clinicamvc.service;

import dh.backend.clinicamvc.model.Odontologo;
import dh.backend.clinicamvc.model.Paciente;
import org.springframework.stereotype.Service;

import java.util.List;

public interface IOdontologoService {
    Odontologo registrarOdontologo(Odontologo odontologo);

    Odontologo buscarPorId(Integer id);

    List<Odontologo> buscarTodos();

    Odontologo actualizarOdontologo(Odontologo odontologo);

    void eliminarOdontologo(Integer id);
}
