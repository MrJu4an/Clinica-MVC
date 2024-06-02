package dh.backend.clinicamvc.controller;

import dh.backend.clinicamvc.model.Paciente;
import dh.backend.clinicamvc.service.IPacienteService;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/paciente")
public class PacienteController {
    private IPacienteService pacienteService;

    public PacienteController(IPacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Paciente> buscarPaciente(@PathVariable Integer id) {
        Paciente paciente = pacienteService.buscarPorId(id);
        if (paciente != null) {
            return ResponseEntity.ok(paciente);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Paciente>> buscarTodos() {
        return ResponseEntity.ok(pacienteService.buscarTodos());
    }

    @PostMapping
    public ResponseEntity<Paciente> registrarPaciente(@RequestBody Paciente paciente) {
        Paciente pacienteRetornar = pacienteService.registrarPaciente(paciente);

        if (pacienteRetornar != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(pacienteRetornar);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping
    public ResponseEntity<String> actualizarPaciente(@RequestBody Paciente paciente) {
        pacienteService.actualizarPaciente(paciente);
        return ResponseEntity.ok("Paciente actualizado");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarPaciente(@PathVariable Integer id) {
        pacienteService.eliminarPaciente(id);
        return ResponseEntity.ok("Paciente eliminado");
    }
}
