package dh.backend.clinicamvc.service;

import dh.backend.clinicamvc.dao.impl.PacienteDaoH2;
import dh.backend.clinicamvc.model.Domicilio;
import dh.backend.clinicamvc.model.Paciente;
import dh.backend.clinicamvc.service.impl.PacienteService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class PacienteServiceTest {

    private static Logger LOGGER = LoggerFactory.getLogger(PacienteServiceTest.class);
    private static PacienteService pacienteService = new PacienteService(new PacienteDaoH2());

    @BeforeAll
    static void crearTablas() {
        Connection connection = null;
        try {
            Class.forName("org.h2.Driver");
            connection = DriverManager.getConnection("jdbc:h2:~/db_clinicamvc_2805;INIT=RUNSCRIPT FROM 'create.sql'", "sa", "sa");
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                LOGGER.error(e.getMessage());
                e.printStackTrace();
            }
        }
    }

    @Test
    @DisplayName("Testear que un paciente fue guardado")
    void testPacienteGuardado() {
        Paciente paciente = new Paciente("Rondon", "Sebastian", "1005753422",
                LocalDate.of(2024, 03, 02),
                new Domicilio("Calle Falsa", 456, "SpringField", "Montana"));

        Paciente pacienteDB = pacienteService.registrarPaciente(paciente);

        assertNotNull(pacienteDB);
    }

    @Test
    @DisplayName("Testear busqueda de Paciente por ID")
    void testBuscarPaciente() {
        Integer id = 1;
        Paciente pacienteEncontrado = pacienteService.buscarPorId(id);

        assertNotNull(pacienteEncontrado);
    }

    @Test
    @DisplayName("Buscar todos los pacientes")
    void testBuscarPacientes() {
        Paciente paciente = new Paciente("Rondon", "Sebastian", "1005753422",
                LocalDate.of(2024, 03, 02),
                new Domicilio("Calle Falsa", 456, "SpringField", "Montana"));

        pacienteService.registrarPaciente(paciente);

        List<Paciente> pacientesEncontrados = pacienteService.buscarTodos();

        assertNotNull(pacientesEncontrados);
    }
}