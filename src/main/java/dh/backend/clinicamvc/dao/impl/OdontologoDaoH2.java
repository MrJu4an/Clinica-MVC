package dh.backend.clinicamvc.dao.impl;

import dh.backend.clinicamvc.dao.IDao;
import dh.backend.clinicamvc.db.H2Connection;
import dh.backend.clinicamvc.model.Odontologo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class OdontologoDaoH2 implements IDao<Odontologo> {
    private static Logger LOGGER = LoggerFactory.getLogger(OdontologoDaoH2.class);
    private static String SQL_INSERT = "INSERT INTO ODONTOLOGOS VALUES (DEFAULT, ?, ?, ?) ";
    private static String SQL_SELECT_ID = "SELECT * FROM ODONTOLOGOS WHERE ID = ? ";
    private static String SQL_SELECT = "SELECT * FROM ODONTOLOGOS";
    private static String SQL_UPDATE = "UPDATE ODONTOLOGOS SET IDMATRICULA = ?, NOMBRES = ?, APELLIDOS = ? WHERE ID = ? ";
    private static String SQL_DELETE = "DELETE ODONTOLOGOS WHERE ID = ? ";
    @Override
    public Odontologo registrar(Odontologo odontologo) {
        Connection connection = null;
        Odontologo odontologoRegistrado = null;
        try {
            connection = H2Connection.getConnection();
            connection.setAutoCommit(false);

            PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, odontologo.getIdMatricula());
            preparedStatement.setString(2, odontologo.getNombre());
            preparedStatement.setString(3, odontologo.getApellido());
            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            while (resultSet.next()) {
                Integer id = resultSet.getInt(1);
                odontologoRegistrado = new Odontologo(id, odontologo.getIdMatricula(), odontologo.getNombre(),
                        odontologo.getApellido());
            }

            LOGGER.info("Odontologo registrado: " + odontologoRegistrado);

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

        return odontologoRegistrado;
    }

    @Override
    public Odontologo buscarPorId(Integer id) {
        Connection connection = null;
        Odontologo odontologoEncontrado = null;

        try {
            connection = H2Connection.getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_ID);
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Integer idDevuelto = resultSet.getInt(1);
                String matricula = resultSet.getString(2);
                String nombre = resultSet.getString(3);
                String apellido = resultSet.getString(4);

                odontologoEncontrado = new Odontologo(idDevuelto, matricula, nombre, apellido);
            }
            LOGGER.info("Odontologo encontrado: " + odontologoEncontrado);

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
        return odontologoEncontrado;
    }

    @Override
    public List<Odontologo> buscarTodos() {
        Connection connection = null;
        List<Odontologo> odontologos = new ArrayList<>();
        try {
            connection = H2Connection.getConnection();

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_SELECT);

            while (resultSet.next()) {
                Integer id = resultSet.getInt(1);
                String idMatricula = resultSet.getString(2);
                String nombre = resultSet.getString(3);
                String apellido = resultSet.getString(4);

                Odontologo odontologo = new Odontologo(id, idMatricula, nombre, apellido);

                LOGGER.info("Odontologo listado: " + odontologo);
                odontologos.add(odontologo);
            }

            LOGGER.info("Lista de odontologos: " + odontologos);
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

        return odontologos;
    }

    @Override
    public Odontologo actualizar(Odontologo odontologo) {
        Connection connection = null;
        Odontologo odontologoActualizado = null;
        try {
            connection = H2Connection.getConnection();
            connection.setAutoCommit(false);

            PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE);
            preparedStatement.setString(1, odontologo.getIdMatricula());
            preparedStatement.setString(2, odontologo.getNombre());
            preparedStatement.setString(3, odontologo.getApellido());
            preparedStatement.setInt(4, odontologo.getId());
            preparedStatement.executeUpdate();

            LOGGER.info("Odontologo actualizado: " + odontologoActualizado);

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

        return odontologoActualizado;
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

            LOGGER.info("Odontologo eliminado con ID: " + id);

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
