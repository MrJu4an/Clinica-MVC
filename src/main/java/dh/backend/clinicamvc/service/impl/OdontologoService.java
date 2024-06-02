package dh.backend.clinicamvc.service.impl;


import dh.backend.clinicamvc.dao.IDao;
import dh.backend.clinicamvc.model.Odontologo;
import dh.backend.clinicamvc.service.IOdontologoService;
import dh.backend.clinicamvc.service.IPacienteService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OdontologoService implements IOdontologoService {
    private IDao<Odontologo> odontologoIDao;

    public OdontologoService(IDao<Odontologo> odontologoIDao) {
        this.odontologoIDao = odontologoIDao;
    }

    public Odontologo registrarOdontologo(Odontologo odontologo) {
        return odontologoIDao.registrar(odontologo);
    }

    public Odontologo buscarPorId(Integer id) {
        return odontologoIDao.buscarPorId(id);
    }

    public List<Odontologo> buscarTodos() {
        return odontologoIDao.buscarTodos();
    }

    @Override
    public Odontologo actualizarOdontologo(Odontologo odontologo) {
        return odontologoIDao.actualizar(odontologo);
    }

    @Override
    public void eliminarOdontologo(Integer id) {
        odontologoIDao.eliminar(id);
    }
}
