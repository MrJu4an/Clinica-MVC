package dh.backend.clinicamvc.controller;

import dh.backend.clinicamvc.entity.Paciente;
import dh.backend.clinicamvc.exception.ResourceNotFoundException;
import dh.backend.clinicamvc.service.IPacienteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/paciente")
public class PacienteController {
    private IPacienteService pacienteService;

    public PacienteController(IPacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Paciente> buscarPaciente(@PathVariable Integer id) {
        Optional<Paciente> paciente = pacienteService.buscarPorId(id);
        if (paciente.isPresent()) {
            Paciente pacienteRetornar = paciente.get();
            return ResponseEntity.ok(pacienteRetornar);
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
    public ResponseEntity<String> actualizarPaciente(@RequestBody Paciente paciente) throws ResourceNotFoundException {
        pacienteService.actualizarPaciente(paciente);
        return ResponseEntity.ok("{\"message\": \"paciente modificado\"}");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarPaciente(@PathVariable Integer id) throws ResourceNotFoundException {
        pacienteService.eliminarPaciente(id);
        return ResponseEntity.ok("{\"message\": \"paciente eliminado\"}");
    }

    @GetMapping("/dni/{dni}")
    public ResponseEntity<Paciente> buscarPacienteDNI(@PathVariable String dni) {
        Paciente pacienteOptional = pacienteService.buscarPorDNI(dni);
        if (pacienteOptional != null) {
            return ResponseEntity.ok(pacienteOptional);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/domicilio/{provincia}")
    public ResponseEntity<List<Paciente>> buscarPorDomicilio(@PathVariable String provincia) {
        return ResponseEntity.ok(pacienteService.buscarPorProvincia(provincia));
    }
}
