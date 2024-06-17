package dh.backend.clinicamvc.controller;

import dh.backend.clinicamvc.Dto.request.TurnoRequestDto;
import dh.backend.clinicamvc.Dto.response.TurnoResponseDto;
import dh.backend.clinicamvc.entity.Turno;
import dh.backend.clinicamvc.exception.BadRequestException;
import dh.backend.clinicamvc.exception.ResourceNotFoundException;
import dh.backend.clinicamvc.service.ITurnoService;
import org.springframework.cglib.core.Local;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/turno")
public class TurnoController {
    ITurnoService turnoService;

    public TurnoController(ITurnoService turnoService) {
        this.turnoService = turnoService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<TurnoResponseDto> buscarTurno(@PathVariable Integer id) throws ResourceNotFoundException {
        return ResponseEntity.ok(turnoService.buscarPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<TurnoResponseDto>> buscarTodos() throws ResourceNotFoundException {
        return ResponseEntity.ok(turnoService.buscarTodos());
    }

    @PostMapping
    public ResponseEntity<TurnoResponseDto> registrarTurno(@RequestBody TurnoRequestDto turno) throws BadRequestException, ResourceNotFoundException {
        return ResponseEntity.status(HttpStatus.CREATED).body(turnoService.registrarTurno(turno));
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> actualizarTurno(@PathVariable Integer id, @RequestBody TurnoRequestDto turno) throws ResourceNotFoundException, BadRequestException {
        turnoService.actualizarTurno(id, turno);
        return ResponseEntity.ok("Turno actualizado");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarTurno(@PathVariable Integer id) throws ResourceNotFoundException {
        turnoService.eliminarTurno(id);
        return ResponseEntity.ok("Turno eliminado");
    }

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    @GetMapping("/fechas/{inicio}/{fin}")
    public ResponseEntity<List<TurnoResponseDto>> buscarPorFechas(@PathVariable String inicio, @PathVariable String fin) throws ResourceNotFoundException {
        LocalDate fechaInicio = LocalDate.parse(inicio, formatter);
        LocalDate fechaFinal = LocalDate.parse(fin, formatter);
        return ResponseEntity.ok(turnoService.buscarPorFechas(fechaInicio, fechaFinal));
    }

    @GetMapping("/fechaActual")
    public ResponseEntity<List<TurnoResponseDto>> buscarMayorFechaActual() throws ResourceNotFoundException {
        return ResponseEntity.ok(turnoService.buscarMayorFechaActual());
    }
}
