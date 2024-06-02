package dh.backend.clinicamvc.controller;

import dh.backend.clinicamvc.model.Odontologo;
import dh.backend.clinicamvc.service.IOdontologoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/odontologo")
public class OdontologoController {
    private IOdontologoService odontologoService;

    private OdontologoController(IOdontologoService odontologoService) {
        this.odontologoService = odontologoService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Odontologo> buscarOdontologo(@PathVariable Integer id) {
        Odontologo odontologo = odontologoService.buscarPorId(id);
        if (odontologo != null) {
            return ResponseEntity.ok(odontologo);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Odontologo>> buscarTodos() {
        List<Odontologo> odontologos = odontologoService.buscarTodos();
        return ResponseEntity.ok(odontologos);
    }

    @PostMapping
    public ResponseEntity<Odontologo> registrarOdontologo(@RequestBody Odontologo odontologo) {
        Odontologo odontologoRetornar = odontologoService.registrarOdontologo(odontologo);
        if (odontologoRetornar != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(odontologoRetornar);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping
    public ResponseEntity<String> actualizarOdontologo(@RequestBody Odontologo odontologo) {
        odontologoService.actualizarOdontologo(odontologo);
        return ResponseEntity.ok("Odontologo actualizado");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarOdontologo(@PathVariable Integer id) {
        odontologoService.eliminarOdontologo(id);
        return ResponseEntity.ok("Odontologo eliminado");
    }
}
