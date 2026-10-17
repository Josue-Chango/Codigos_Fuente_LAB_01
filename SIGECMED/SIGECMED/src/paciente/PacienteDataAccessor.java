/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paciente;

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
public class PacienteDataAccessor {
    
        private Connection connection;
    
    public PacienteDataAccessor(String driverClassName, String dbURL, String user, String password) throws SQLException, ClassNotFoundException {
        Class.forName(driverClassName);
        connection = DriverManager.getConnection(dbURL, user, password);
    }
    
    public void shutdown() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }
    
    public Paciente getPaciente(int id) {
        String query = "select ID_PACIENTE, NOMBRE_SEC, CEDULA_SEC, DIRECCION_SEC, TELEFONO_SEC, CORREO_SEC from paciente where ID_PACIENTE = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int idPaciente = rs.getInt("ID_PACIENTE");
                    String nombre = rs.getString("NOMBRE_SEC");
                    String cedula = rs.getString("CEDULA_SEC");
                    String direccion = rs.getString("DIRECCION_SEC");
                    String telefono = rs.getString("TELEFONO_SEC");
                    String correo = rs.getString("CORREO_SEC");
                    return new Paciente(idPaciente, nombre, cedula, direccion, telefono, correo);
                } else {
                    return new Paciente();
                }
            }
        } catch (SQLException ex) {
            return null;
        }
    }
    
    public List<Paciente> getPacienteList() {
        try (
            Statement stmnt = connection.createStatement();
            ResultSet rs = stmnt.executeQuery("select ID_PACIENTE, NOMBRE_SEC, CEDULA_SEC, DIRECCION_SEC, TELEFONO_SEC, CORREO_SEC from paciente");) {
            List<Paciente> pacienteList = new ArrayList<>();
            while (rs.next()) {
                int idPaciente = rs.getInt("ID_PACIENTE");
                String nombre = rs.getString("NOMBRE_SEC");
                String cedula = rs.getString("CEDULA_SEC");
                String direccion = rs.getString("DIRECCION_SEC");
                String telefono = rs.getString("TELEFONO_SEC");
                String correo = rs.getString("CORREO_SEC");
                Paciente paciente = new Paciente(idPaciente, nombre, cedula, direccion, telefono, correo);
                pacienteList.add(paciente);
            }
            return pacienteList;
        } catch (SQLException ex) {
            return new ArrayList<>();
        }
    }
    
    public boolean insertNewPac(Paciente pac) {
        String query = "INSERT INTO paciente (NOMBRE_SEC, CEDULA_SEC, DIRECCION_SEC, TELEFONO_SEC, CORREO_SEC, TRATAMIENTO) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, pac.getNombre());
            ps.setString(2, pac.getCedula());
            ps.setString(3, pac.getDireccion());
            ps.setString(4, pac.getTelefono());
            ps.setString(5, pac.getCorreo());
            ps.setBoolean(6, false);

            ps.execute();
            connection.commit();
            
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }
    
    public boolean updatePac(Paciente pac) {
        String query = "UPDATE paciente SET NOMBRE_SEC = ?, CEDULA_SEC = ?, DIRECCION_SEC = ?, TELEFONO_SEC = ?, CORREO_SEC = ? WHERE ID_PACIENTE = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, pac.getNombre());
            ps.setString(2, pac.getCedula());
            ps.setString(3, pac.getDireccion());
            ps.setString(4, pac.getTelefono());
            ps.setString(5, pac.getCorreo());
            ps.setInt(6, pac.getId_paciente());

            ps.executeUpdate();
            
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }
    
    public boolean deletePac(int id) {
        String query = "DELETE FROM paciente WHERE ID_PACIENTE = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);

            ps.execute();
            
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }
    
}
