package dh.backend.clinicamvc.service;

import dh.backend.clinicamvc.Dto.request.TurnoRequestDto;
import dh.backend.clinicamvc.Dto.response.TurnoResponseDto;
import dh.backend.clinicamvc.entity.Turno;
import dh.backend.clinicamvc.exception.BadRequestException;
import dh.backend.clinicamvc.exception.ResourceNotFoundException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ITurnoService {
    TurnoResponseDto registrarTurno(TurnoRequestDto turnoRequestDto) throws BadRequestException, ResourceNotFoundException;

    TurnoResponseDto buscarPorId(Integer id) throws ResourceNotFoundException;

    List<TurnoResponseDto> buscarTodos() throws ResourceNotFoundException;

    void actualizarTurno(Integer id, TurnoRequestDto turnoRequestDto) throws ResourceNotFoundException, BadRequestException;

    void eliminarTurno(Integer id) throws ResourceNotFoundException;

    // Metodos con HQL
    List<TurnoResponseDto> buscarPorFechas(LocalDate startDate, LocalDate endDate) throws ResourceNotFoundException;
    List<TurnoResponseDto> buscarMayorFechaActual() throws ResourceNotFoundException;

}
