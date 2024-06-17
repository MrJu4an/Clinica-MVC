package dh.backend.clinicamvc.service.impl;

import dh.backend.clinicamvc.Dto.request.TurnoRequestDto;
import dh.backend.clinicamvc.Dto.response.OdontologoResponseDto;
import dh.backend.clinicamvc.Dto.response.PacienteResponseDto;
import dh.backend.clinicamvc.Dto.response.TurnoResponseDto;
import dh.backend.clinicamvc.entity.Odontologo;
import dh.backend.clinicamvc.entity.Paciente;
import dh.backend.clinicamvc.entity.Turno;
import dh.backend.clinicamvc.exception.BadRequestException;
import dh.backend.clinicamvc.exception.ResourceNotFoundException;
import dh.backend.clinicamvc.repository.ITurnoRepository;
import dh.backend.clinicamvc.service.IOdontologoService;
import dh.backend.clinicamvc.service.IPacienteService;
import dh.backend.clinicamvc.service.ITurnoService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TurnoService implements ITurnoService {
    private Logger LOGGER = LoggerFactory.getLogger(TurnoService.class);
    private ITurnoRepository turnoRepository;
    private IPacienteService pacienteService;
    private IOdontologoService odontologoService;
    private ModelMapper modelMapper;

    public TurnoService(ITurnoRepository turnoRepository, IPacienteService pacienteService, IOdontologoService odontologoService, ModelMapper modelMapper) {
        this.turnoRepository = turnoRepository;
        this.pacienteService = pacienteService;
        this.odontologoService = odontologoService;
        this.modelMapper = modelMapper;
    }

    @Override
    public TurnoResponseDto buscarPorId(Integer id) throws ResourceNotFoundException {
        Optional<Turno> turnoOptional = turnoRepository.findById(id);
        if (turnoOptional.isPresent()) {
            Turno turnoEncontrado = turnoOptional.get();
            TurnoResponseDto turnoDevolver = mapToResponseDto(turnoEncontrado);
            LOGGER.info("Se retorna el turno encontrado: " + turnoDevolver);
            return turnoDevolver;
        } else {
            LOGGER.info("Turno no encontrado");
            throw new ResourceNotFoundException("{\"message\": \"turno no encontrado\"}");
        }
    }

    @Override
    public List<TurnoResponseDto> buscarTodos() throws ResourceNotFoundException {
        List<Turno> turnos = turnoRepository.findAll();
        if (!turnos.isEmpty()) {
            List<TurnoResponseDto> turnosDevolver = new ArrayList<>();
            TurnoResponseDto turnoAuxiliar = null;
            for (Turno turno : turnos) {
                turnoAuxiliar = mapToResponseDto(turno);
                turnosDevolver.add(turnoAuxiliar);
            }
            LOGGER.info("Se retorna todos los turnos: " + turnosDevolver);
            return turnosDevolver;
        } else {
            throw new ResourceNotFoundException("{\"message\": \"no existen turnos\"}");
        }
    }

    @Override
    public TurnoResponseDto registrarTurno(TurnoRequestDto turnoRequestDto) throws BadRequestException, ResourceNotFoundException {
        //Buscamos el paciente y odontologo correspondientes
        Optional<Paciente> paciente = pacienteService.buscarPorId(turnoRequestDto.getPaciente_id());
        Optional<Odontologo> odontologo = odontologoService.buscarPorId(turnoRequestDto.getOdontologo_id());

        //Declaramos variables a utilizar y devolver
        Turno turnoRegistrar = new Turno();
        Turno turnoGuardado = null;
        TurnoResponseDto turnoDevolver = null;

        if (paciente.isPresent() && odontologo.isPresent()) {
            //Creamos el turno a guardar
            turnoRegistrar.setPaciente(paciente.get());
            turnoRegistrar.setOdontologo(odontologo.get());
            turnoRegistrar.setFecha(LocalDate.parse(turnoRequestDto.getFecha()));
            turnoGuardado = turnoRepository.save(turnoRegistrar);
            LOGGER.info("Se guarda el turno");

            //Armamos el turno a devolver
            turnoDevolver = mapToResponseDto(turnoGuardado);

            return turnoDevolver;
        } else {
            LOGGER.info("Odontologo o paciente no encontrado: " + turnoRequestDto);
            throw new BadRequestException("{\"message\": \"odontologo o paciente no encontrados\"}");
        }
    }

    @Override
    public void actualizarTurno(Integer id, TurnoRequestDto turnoRequestDto) throws ResourceNotFoundException, BadRequestException {
        //Buscamos el paciente y odontologo correspondientes
        Optional<Paciente> paciente = pacienteService.buscarPorId(turnoRequestDto.getPaciente_id());
        Optional<Odontologo> odontologo = odontologoService.buscarPorId(turnoRequestDto.getOdontologo_id());
        Optional<Turno> turno = turnoRepository.findById(id);

        //Variables a utilizar y devolver
        Turno turnoModificar = new Turno();

        if (paciente.isPresent() && odontologo.isPresent()) {
            //Creamos el turno a guardar
            turnoModificar.setId(id);
            turnoModificar.setPaciente(paciente.get());
            turnoModificar.setOdontologo(odontologo.get());
            turnoModificar.setFecha(LocalDate.parse(turnoRequestDto.getFecha()));
            LOGGER.info("Se actualiza la información del turno: " + turnoModificar);
            turnoRepository.save(turnoModificar);
        } else {
            LOGGER.info("Odontologo o paciente no encontrado: " + turnoRequestDto);
            throw new BadRequestException("{\"message\": \"odontologo o paciente no encontrados\"}");
        }
    }

    @Override
    public void eliminarTurno(Integer id) throws ResourceNotFoundException {
        TurnoResponseDto turnoResponseDto = buscarPorId(id);
        if (turnoResponseDto != null) {
            LOGGER.info("Se elimina el turno con id: " + id);
            turnoRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("{\"message\": \"turno no encontrado\"}");
        }

    }

    @Override
    public List<TurnoResponseDto> buscarPorFechas(LocalDate startDate, LocalDate endDate) throws ResourceNotFoundException {
        List<Turno> listadoTurnos = turnoRepository.buscarTurnosPorFecha(startDate, endDate);
        if (!listadoTurnos.isEmpty()) {
            List<TurnoResponseDto> listadoRetornar = new ArrayList<>();
            TurnoResponseDto turnoAuxiliar = null;
            for (Turno turno: listadoTurnos) {
                turnoAuxiliar = mapToResponseDto(turno);
                listadoRetornar.add(turnoAuxiliar);
            }
            LOGGER.info("Se retorna los turnos buscado por el rango de fechas: " + listadoRetornar);
            return listadoRetornar;
        } else {
            throw new ResourceNotFoundException("{\"message\": \"turnos no encontrados\"}");
        }
    }

    @Override
    public List<TurnoResponseDto> buscarMayorFechaActual() throws ResourceNotFoundException {
        List<Turno> listadoTurnos = turnoRepository.buscarTurnosMayoresFecha();
        if (!listadoTurnos.isEmpty()) {
            List<TurnoResponseDto> listadoRetornar = new ArrayList<>();
            TurnoResponseDto turnoAuxiliar = null;
            for (Turno turno: listadoTurnos) {
                turnoAuxiliar = mapToResponseDto(turno);
                listadoRetornar.add(turnoAuxiliar);
            }
            LOGGER.info("Se retorna los turnos con fecha mayor a la actual: " + listadoRetornar);
            return listadoRetornar;
        } else {
            throw new ResourceNotFoundException("{\"message\": \"turnos no encontrados\"}");
        }
    }

    private TurnoResponseDto mapToResponseDto(Turno turno) {
        TurnoResponseDto turnoResponseDto = modelMapper.map(turno, TurnoResponseDto.class);
        turnoResponseDto.setOdontologo(modelMapper.map(turno.getOdontologo(), OdontologoResponseDto.class));
        turnoResponseDto.setPaciente(modelMapper.map(turno.getPaciente(), PacienteResponseDto.class));
        return turnoResponseDto;
    }
}
