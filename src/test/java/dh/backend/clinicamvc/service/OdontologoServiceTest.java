package dh.backend.clinicamvc.service;

import dh.backend.clinicamvc.entity.Odontologo;
import dh.backend.clinicamvc.service.impl.OdontologoService;
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
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class OdontologoServiceTest {
    private static Logger LOGGER = LoggerFactory.getLogger(OdontologoServiceTest.class);
    @Autowired
    private OdontologoService odontologoService;
    private Odontologo odontologo;
    @BeforeEach
    void setUp() {
        odontologo = new Odontologo();
        odontologo.setIdMatricula("MrJu4an");
        odontologo.setNombre("Sebastian");
        odontologo.setApellido("Apellido");
    }

    @Test
    @DisplayName("Buscar todos los odontologos")
    void testBuscarOdontologos() {
        List<Odontologo> odontologos = new ArrayList<>();

        odontologoService.registrarOdontologo(odontologo);
        odontologos = odontologoService.buscarTodos();

        assertNotNull(odontologos);
        assertEquals(1, odontologos.size());
    }
}