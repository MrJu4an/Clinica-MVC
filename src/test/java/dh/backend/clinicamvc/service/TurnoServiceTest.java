package dh.backend.clinicamvc.service;

import dh.backend.clinicamvc.Dto.request.TurnoRequestDto;
import dh.backend.clinicamvc.Dto.response.TurnoResponseDto;
import dh.backend.clinicamvc.entity.Odontologo;
import dh.backend.clinicamvc.exception.BadRequestException;
import dh.backend.clinicamvc.exception.ResourceNotFoundException;
import dh.backend.clinicamvc.service.impl.OdontologoService;
import dh.backend.clinicamvc.service.impl.TurnoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
public class TurnoServiceTest {
    private static Logger LOGGER = LoggerFactory.getLogger(TurnoServiceTest.class);
    @Autowired
    private TurnoService turnoService;

    private TurnoRequestDto turnoRequestDto;

    @BeforeEach
    void setUp() {
        turnoRequestDto = new TurnoRequestDto();
        turnoRequestDto.setPaciente_id(1);
        turnoRequestDto.setOdontologo_id(1);
        turnoRequestDto.setFecha(LocalDate.of(2024, 06,20).toString());
    }

    @Test
    @DisplayName("Crear Turno")
    void testCrearTurno() throws BadRequestException, ResourceNotFoundException {
        TurnoResponseDto turnoResponseDto = turnoService.registrarTurno(turnoRequestDto);
        assertNotNull(turnoResponseDto);
    }

    @Test
    @DisplayName("Buscar todos los turnos")
    void testBuscarTurnos() throws ResourceNotFoundException {
        List<TurnoResponseDto> turnos = turnoService.buscarTodos();
        assertEquals(6, turnos.size());
        assertNotNull(turnos);
    }
}
