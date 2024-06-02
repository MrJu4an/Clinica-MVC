package dh.backend.clinicamvc.dao.impl;


import dh.backend.clinicamvc.dao.IDao;
import dh.backend.clinicamvc.db.H2Connection;
import dh.backend.clinicamvc.model.Domicilio;
import dh.backend.clinicamvc.model.Paciente;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class PacienteDaoH2 implements IDao<Paciente> {
    private static Logger LOGGER = LoggerFactory.getLogger(PacienteDaoH2.class);
    private static String SQL_INSERT = "INSERT INTO PACIENTES VALUES (DEFAULT, ?, ?, ?, ?, ?) ";
    private static String SQL_SELECT_ID = "SELECT * FROM PACIENTES WHERE ID = ? ";
    private static String SQL_SELECT_ALL = "SELECT * FROM PACIENTES ";
    private static String SQL_UPDATE = "UPDATE PACIENTES SET APELLIDO = ?, NOMBRE = ?, DNI = ?, FECHA_INGRESO = ?, ID_DOMICILIO = ? WHERE ID = ? ";
    private static String SQL_DELETE = "DELETE FROM PACIENTES WHERE ID = ? ";
    @Override
    public Paciente registrar(Paciente paciente) {
        Connection connection = null;
        DomicilioDaoH2 domicilioDaoH2 = new DomicilioDaoH2();
        Paciente pacienteRegistrado = null;
        try {
            connection = H2Connection.getConnection();
            connection.setAutoCommit(false);

            Domicilio domicilioPersistido = domicilioDaoH2.registrar(paciente.getDomicilio());

            PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, paciente.getApellido());
            preparedStatement.setString(2, paciente.getNombre());
            preparedStatement.setString(3, paciente.getDni());
            preparedStatement.setDate(4, Date.valueOf(paciente.getFechaIngreso()));
            preparedStatement.setInt(5, domicilioPersistido.getId());
            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            while (resultSet.next()) {
                Integer id = resultSet.getInt(1);
                pacienteRegistrado = new Paciente(id, paciente.getApellido(), paciente.getNombre(),
                        paciente.getDni(), paciente.getFechaIngreso(), domicilioPersistido);
            }

            LOGGER.info("Paciente persistido: " + pacienteRegistrado);

            connection.commit();
            connection.setAutoCommit(true);
        } catch (Exception e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    LOGGER.error(ex.getMessage());
                    ex.printStackTrace();
                }
            }
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                LOGGER.error(e.getMessage());
                e.printStackTrace();
            }
        }
        return pacienteRegistrado;
    }

    @Override
    public Paciente buscarPorId(Integer id) {
        Connection connection = null;
        Paciente pacienteEncontrado = null;
        DomicilioDaoH2 domicilioDaoH2 = new DomicilioDaoH2();
        try {
            connection = H2Connection.getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_ID);
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Integer idDevuelto = resultSet.getInt(1);
                String apellido = resultSet.getString(2);
                String nombre = resultSet.getString(3);
                String dni = resultSet.getString(4);
                LocalDate fecha = resultSet.getDate(5).toLocalDate();
                Domicilio domicilioEncontrado = domicilioDaoH2.buscarPorId(resultSet.getInt(6));

                pacienteEncontrado = new Paciente(idDevuelto, apellido, nombre, dni, fecha, domicilioEncontrado);
            }
            LOGGER.info("Paciente encontrado: " + pacienteEncontrado);

        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                LOGGER.error(e.getMessage());
                e.printStackTrace();
            }
        }

        return pacienteEncontrado;
    }

    @Override
    public List<Paciente> buscarTodos() {
        Connection connection = null;
        List<Paciente> pacientesEncontrados = new ArrayList<>();
        DomicilioDaoH2 domicilioDaoH2 = new DomicilioDaoH2();
        try {
            connection = H2Connection.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL);

            while (resultSet.next()) {
                Integer idDevuelto = resultSet.getInt(1);
                String apellido = resultSet.getString(2);
                String nombre = resultSet.getString(3);
                String dni = resultSet.getString(4);
                LocalDate fecha = resultSet.getDate(5).toLocalDate();
                Domicilio domicilioEncontrado = domicilioDaoH2.buscarPorId(resultSet.getInt(6));

                Paciente paciente = new Paciente(idDevuelto, apellido, nombre, dni, fecha, domicilioEncontrado);

                LOGGER.info("Paciente listado: " + paciente);
                pacientesEncontrados.add(paciente);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                LOGGER.error(e.getMessage());
                e.printStackTrace();
            }
        }

        return pacientesEncontrados;
    }

    @Override
    public Paciente actualizar(Paciente paciente) {
        Connection connection = null;
        DomicilioDaoH2 domicilioDaoH2 = new DomicilioDaoH2();
        Paciente pacienteActualizado = null;
        try {
            connection = H2Connection.getConnection();
            connection.setAutoCommit(false);

            domicilioDaoH2.actualizar(paciente.getDomicilio());

            PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE);
            preparedStatement.setString(1, paciente.getApellido());
            preparedStatement.setString(2, paciente.getNombre());
            preparedStatement.setString(3, paciente.getDni());
            preparedStatement.setDate(4, Date.valueOf(paciente.getFechaIngreso()));
            preparedStatement.setInt(5, paciente.getDomicilio().getId());
            preparedStatement.setInt(6, paciente.getId());
            preparedStatement.executeUpdate();

            LOGGER.info("Paciente actualizado: " + pacienteActualizado);

            connection.commit();
            connection.setAutoCommit(true);
        } catch (Exception e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    LOGGER.error(ex.getMessage());
                    ex.printStackTrace();
                }
            }
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                LOGGER.error(e.getMessage());
                e.printStackTrace();
            }
        }
        return pacienteActualizado;
    }

    @Override
    public void eliminar(Integer id) {
        Connection connection = null;
        try {
            connection = H2Connection.getConnection();
            connection.setAutoCommit(false);

            PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

            LOGGER.info("Paciente eliminado con ID: " + id);

            connection.commit();
            connection.setAutoCommit(true);
        } catch (Exception e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    LOGGER.error(ex.getMessage());
                    ex.printStackTrace();
                }
            }
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                LOGGER.error(e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
