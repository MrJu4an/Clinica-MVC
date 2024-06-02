package dh.backend.clinicamvc.service;

import dh.backend.clinicamvc.model.Paciente;
import dh.backend.clinicamvc.model.Turno;

import java.util.List;

public interface ITurnoService {
    Turno registrarTurno(Turno turno);

    Turno buscarPorId(Integer id);

    List<Turno> buscarTodos();

    Turno actualizarTurno(Turno turno);

    void eliminarTurno(Integer id);
}
