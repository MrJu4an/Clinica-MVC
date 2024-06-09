package dh.backend.clinicamvc.service;

import dh.backend.clinicamvc.entity.Domicilio;
import dh.backend.clinicamvc.entity.Paciente;
import dh.backend.clinicamvc.service.impl.PacienteService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class PacienteServiceTest {

    private static Logger LOGGER = LoggerFactory.getLogger(PacienteServiceTest.class);
    @Autowired
    private PacienteService pacienteService;
    private Paciente paciente;

    @BeforeEach
    void setUp() {
        paciente = new Paciente();
        paciente.setNombre("Menganito");
        paciente.setApellido("Cosme");
        paciente.setDni("464646");
        paciente.setFechaIngreso(LocalDate.of(2024, 01, 12));
        Domicilio domicilio = new Domicilio();
        domicilio.setCalle("Calle falsa");
        domicilio.setNumero(456);
        domicilio.setProvincia("Sprinfield");
        domicilio.setLocalidad("Montana");
        paciente.setDomicilio(domicilio);
    }

    @Test
    @DisplayName("Testear que un paciente fue guardado")
    void testPacienteGuardado() {
        Paciente pacienteDB = pacienteService.registrarPaciente(paciente);

        assertNotNull(pacienteDB);
    }

    @Test
    @DisplayName("Testear busqueda de Paciente por ID")
    void testBuscarPaciente() {
        Integer id = 1;
        Optional<Paciente> pacienteEncontrado = pacienteService.buscarPorId(id);
        Paciente paciente1 = pacienteEncontrado.get();

        assertEquals(id, paciente1.getId());
    }

    @Test
    @DisplayName("Buscar todos los pacientes")
    void testBuscarPacientes() {

        pacienteService.registrarPaciente(paciente);
        List<Paciente> pacientesEncontrados = pacienteService.buscarTodos();

        assertNotNull(pacientesEncontrados);
    }
}