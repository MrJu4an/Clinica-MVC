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
    public ResponseEntity<TurnoResponseDto> buscarTurno(@PathVariable Integer id) {
        TurnoResponseDto turno = turnoService.buscarPorId(id);
        if (turno != null) {
            return ResponseEntity.ok(turno);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<TurnoResponseDto>> buscarTodos() {
        return ResponseEntity.ok(turnoService.buscarTodos());
    }

    @PostMapping
    public ResponseEntity<TurnoResponseDto> registrarTurno(@RequestBody TurnoRequestDto turno) throws BadRequestException {
        TurnoResponseDto turnoRetornar = turnoService.registrarTurno(turno);
        return ResponseEntity.status(HttpStatus.CREATED).body(turnoRetornar);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> actualizarTurno(@PathVariable Integer id, @RequestBody TurnoRequestDto turno) {
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
    public ResponseEntity<List<TurnoResponseDto>> buscarPorFechas(@PathVariable String inicio, @PathVariable String fin) {
        LocalDate fechaInicio = LocalDate.parse(inicio, formatter);
        LocalDate fechaFinal = LocalDate.parse(fin, formatter);
        return ResponseEntity.ok(turnoService.buscarPorFechas(fechaInicio, fechaFinal));
    }

    @GetMapping("/fechaActual")
    public ResponseEntity<List<TurnoResponseDto>> buscarMayorFechaActual() {
        return ResponseEntity.ok(turnoService.buscarMayorFechaActual());
    }
}
