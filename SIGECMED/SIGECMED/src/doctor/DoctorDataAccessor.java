/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doctor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author frani
 */
public class DoctorDataAccessor {

    private static final String ID_ESPECIALIDAD = "ID_ESPECIALIDAD";
    private static final String NOMBRE_SEC = "NOMBRE_SEC";
    private static final String NOMBRE = "NOMBRE";

    private Connection connection;

    public DoctorDataAccessor(String driverClassName, String dbURL, String user, String password)
            throws SQLException, ClassNotFoundException {
        Class.forName(driverClassName);
        connection = DriverManager.getConnection(dbURL, user, password);
    }

    public void shutdown() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }

    public Doctor getDoctor(int id) {
        String query = "select ID_DOCTOR, ID_ESPECIALIDAD, NOMBRE_SEC, CEDULA_SEC, DIRECCION_SEC, TELEFONO_SEC, CORREO_SEC from doctor where ID_DOCTOR = ?";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int idDoctor = rs.getInt("ID_DOCTOR");
                    int idEspecialidad = rs.getInt(ID_ESPECIALIDAD);
                    String nombre = rs.getString(NOMBRE_SEC);
                    String cedula = rs.getString("CEDULA_SEC");
                    String direccion = rs.getString("DIRECCION_SEC");
                    String telefono = rs.getString("TELEFONO_SEC");
                    String correo = rs.getString("CORREO_SEC");
                    return new Doctor(idDoctor, idEspecialidad, nombre, cedula, direccion, telefono, correo);
                } else {
                    return new Doctor();
                }
            }
        } catch (SQLException ex) {
            return null;
        }
    }

    public Especialidad getEspecialidad(int id) {
        String query = "select ID_ESPECIALIDAD, NOMBRE from especialidad where ID_ESPECIALIDAD = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    int idEspecialidad = rs.getInt(ID_ESPECIALIDAD);
                    String nombre = rs.getString(NOMBRE);
                    Especialidad esp = new Especialidad(idEspecialidad, nombre);
                    return esp;
                } else {
                    return new Especialidad();
                }
            }
        } catch (SQLException ex) {
            return null;
        }
    }

    public List<Doctor> getDoctorsList() {
        try (
                Statement stmnt = connection.createStatement();
                ResultSet rs = stmnt.executeQuery("select ID_DOCTOR, ID_ESPECIALIDAD, NOMBRE_SEC, CEDULA_SEC, DIRECCION_SEC, TELEFONO_SEC, CORREO_SEC from doctor");) {
            List<Doctor> doctorList = new ArrayList<>();
            while (rs.next()) {
                int idDoctor = rs.getInt("ID_DOCTOR");
                int idEspecialidad = rs.getInt(ID_ESPECIALIDAD);
                String nombre = rs.getString(NOMBRE_SEC);
                String cedula = rs.getString("CEDULA_SEC");
                String direccion = rs.getString("DIRECCION_SEC");
                String telefono = rs.getString("TELEFONO_SEC");
                String correo = rs.getString("CORREO_SEC");
                Doctor doctor = new Doctor(idDoctor, idEspecialidad, nombre, cedula, direccion, telefono, correo);
                doctorList.add(doctor);
            }
            return doctorList;
        } catch (SQLException ex) {
            return new ArrayList<>();
        }
    }

    public List<String> getDoctorsStringList() {
        try (
                Statement stmnt = connection.createStatement();
                ResultSet rs = stmnt.executeQuery("select ID_DOCTOR, ID_ESPECIALIDAD, NOMBRE_SEC, CEDULA_SEC, DIRECCION_SEC, TELEFONO_SEC, CORREO_SEC from doctor");) {
            List<String> doctorList = new ArrayList<>();
            while (rs.next()) {
                int idEspecialidad = rs.getInt(ID_ESPECIALIDAD);
                String especialidad = getEspecialidad(idEspecialidad).getNombre();
                String nombre = rs.getString(NOMBRE_SEC);
                String salida = nombre + " - " + especialidad;
                doctorList.add(salida);
            }
            return doctorList;
        } catch (SQLException ex) {
            return new ArrayList<>();
        }
    }

    public List<String> getEspecialidadStringList() {
        try (
                Statement stmnt = connection.createStatement();
                ResultSet rs = stmnt.executeQuery("select ID_ESPECIALIDAD, NOMBRE from especialidad");) {
            List<String> especialidadList = new ArrayList<>();
            while (rs.next()) {
                String nombre = rs.getString(NOMBRE);
                especialidadList.add(nombre);
            }
            return especialidadList;
        } catch (SQLException ex) {
            return new ArrayList<>();
        }
    }

    public List<Especialidad> getEspecialidadList() {
        try (
                Statement stmnt = connection.createStatement();
                ResultSet rs = stmnt.executeQuery("select ID_ESPECIALIDAD, NOMBRE from especialidad");) {
            List<Especialidad> especialidadList = new ArrayList<>();
            while (rs.next()) {
                int id = rs.getInt(ID_ESPECIALIDAD);
                String nombre = rs.getString(NOMBRE);
                Especialidad esp = new Especialidad(id, nombre);
                especialidadList.add(esp);
            }
            return especialidadList;
        } catch (SQLException ex) {
            return new ArrayList<>();
        }
    }

    public boolean insertNewDoctor(Doctor doctor) {
        String query = "INSERT INTO doctor (ID_ESPECIALIDAD, NOMBRE_SEC, CEDULA_SEC, DIRECCION_SEC, TELEFONO_SEC, CORREO_SEC) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(query)){
            ps.setInt(1, doctor.getId_especialidad());
            ps.setString(2, doctor.getNombre());
            ps.setString(3, doctor.getCedula());
            ps.setString(4, doctor.getDireccion());
            ps.setString(5, doctor.getTelefono());
            ps.setString(6, doctor.getCorreo());

            ps.execute();
            connection.commit();

            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

    public boolean updateDoctor(Doctor doctor) {
        String query = "UPDATE doctor SET ID_ESPECIALIDAD = ?, NOMBRE_SEC = ?, CEDULA_SEC = ?, DIRECCION_SEC = ?, TELEFONO_SEC = ?, CORREO_SEC = ? WHERE ID_DOCTOR = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)){
            
            ps.setInt(1, doctor.getId_especialidad());
            ps.setString(2, doctor.getNombre());
            ps.setString(3, doctor.getCedula());
            ps.setString(4, doctor.getDireccion());
            ps.setString(5, doctor.getTelefono());
            ps.setString(6, doctor.getCorreo());
            ps.setInt(7, doctor.getId_doctor());

            ps.executeUpdate();
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

    public boolean deleteDoctor(int id) {
        String query = "DELETE FROM doctor WHERE ID_DOCTOR = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);

            ps.execute();

            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

    public boolean insertNewEsp(Especialidad especialidad) {
        String query = "INSERT INTO especialidad (NOMBRE) VALUES (?)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, especialidad.getNombre());

            ps.execute();
            connection.commit();

            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

    public boolean updateEsp(Especialidad especialidad) {
        String query = "UPDATE especialidad SET NOMBRE = ? WHERE ID_ESPECIALIDAD = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, especialidad.getNombre());
            ps.setInt(2, especialidad.getId_especialidad());

            ps.executeUpdate();

            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

    public boolean deleteEspecialidad(int id) {
        String query = "DELETE FROM especialidad WHERE ID_ESPECIALIDAD = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);

            ps.execute();

            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

}
