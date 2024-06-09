package dh.backend.clinicamvc.service.impl;

import dh.backend.clinicamvc.Dto.request.TurnoRequestDto;
import dh.backend.clinicamvc.Dto.response.OdontologoResponseDto;
import dh.backend.clinicamvc.Dto.response.PacienteResponseDto;
import dh.backend.clinicamvc.Dto.response.TurnoResponseDto;
import dh.backend.clinicamvc.entity.Odontologo;
import dh.backend.clinicamvc.entity.Paciente;
import dh.backend.clinicamvc.entity.Turno;
import dh.backend.clinicamvc.repository.ITurnoRepository;
import dh.backend.clinicamvc.service.IOdontologoService;
import dh.backend.clinicamvc.service.IPacienteService;
import dh.backend.clinicamvc.service.ITurnoService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TurnoService implements ITurnoService {
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
    public TurnoResponseDto registrarTurno(TurnoRequestDto turnoRequestDto) {
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

            //Armamos el turno a devolver
            turnoDevolver = mapToResponseDto(turnoGuardado);
        }
        return turnoDevolver;
    }

    @Override
    public TurnoResponseDto buscarPorId(Integer id) {
        Optional<Turno> turnoOptional = turnoRepository.findById(id);
        if (turnoOptional.isPresent()) {
            Turno turnoEncontrado = turnoOptional.get();
            TurnoResponseDto turnoDevolver = mapToResponseDto(turnoEncontrado);
            return turnoDevolver;
        }
        return null;
    }

    @Override
    public List<TurnoResponseDto> buscarTodos() {
        List<Turno> turnos = turnoRepository.findAll();
        List<TurnoResponseDto> turnosDevolver = new ArrayList<>();
        TurnoResponseDto turnoAuxiliar = null;
        for (Turno turno : turnos) {
            turnoAuxiliar = mapToResponseDto(turno);
            turnosDevolver.add(turnoAuxiliar);
        }
        return turnosDevolver;
    }

    @Override
    public void actualizarTurno(Integer id, TurnoRequestDto turnoRequestDto) {
        //Buscamos el paciente y odontologo correspondientes
        Optional<Paciente> paciente = pacienteService.buscarPorId(turnoRequestDto.getPaciente_id());
        Optional<Odontologo> odontologo = odontologoService.buscarPorId(turnoRequestDto.getOdontologo_id());
        Optional<Turno> turno = turnoRepository.findById(id);

        //Variables a utilizar y devolver
        Turno turnoModificar = new Turno();
        Turno turnoDevolver = null;
        Turno turnoGuaradado = null;

        if (paciente.isPresent() && odontologo.isPresent()) {
            //Creamos el turno a guardar
            turnoModificar.setId(id);
            turnoModificar.setPaciente(paciente.get());
            turnoModificar.setOdontologo(odontologo.get());
            turnoModificar.setFecha(LocalDate.parse(turnoRequestDto.getFecha()));
            turnoRepository.save(turnoModificar);
        }
    }

    @Override
    public void eliminarTurno(Integer id) {
        turnoRepository.deleteById(id);
    }

    private TurnoResponseDto mapToResponseDto(Turno turno) {
        TurnoResponseDto turnoResponseDto = modelMapper.map(turno, TurnoResponseDto.class);
        turnoResponseDto.setOdontologo(modelMapper.map(turno.getOdontologo(), OdontologoResponseDto.class));
        turnoResponseDto.setPaciente(modelMapper.map(turno.getPaciente(), PacienteResponseDto.class));
        return turnoResponseDto;
    }
}
