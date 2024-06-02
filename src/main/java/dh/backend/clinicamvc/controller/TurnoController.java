package dh.backend.clinicamvc.controller;

import dh.backend.clinicamvc.model.Turno;
import dh.backend.clinicamvc.service.ITurnoService;
import dh.backend.clinicamvc.service.impl.TurnoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/turno")
public class TurnoController {
    ITurnoService turnoService;

    public TurnoController(ITurnoService turnoService) {
        this.turnoService = turnoService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Turno> buscarTurno(@PathVariable Integer id) {
        Turno turno = turnoService.buscarPorId(id);
        if (turno != null) {
            return ResponseEntity.ok(turno);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Turno>> buscarTodos() {
        return ResponseEntity.ok(turnoService.buscarTodos());
    }

    @PostMapping
    public ResponseEntity<Turno> registrarTurno(@RequestBody Turno turno) {
        Turno turnoRetornar = turnoService.registrarTurno(turno);
        if (turnoRetornar != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(turnoRetornar);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping
    public ResponseEntity<String> actualizarTurno(@RequestBody Turno turno) {
        turnoService.actualizarTurno(turno);
        return ResponseEntity.ok("Turno actualizado");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarTurno(@PathVariable Integer id) {
        turnoService.eliminarTurno(id);
        return ResponseEntity.ok("Turno eliminado");
    }
}
