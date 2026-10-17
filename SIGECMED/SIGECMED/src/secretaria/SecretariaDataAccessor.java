/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package secretaria;

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
public class SecretariaDataAccessor {
    
    private Connection connection;
    
    public SecretariaDataAccessor(String driverClassName, String dbURL, String user, String password) throws SQLException, ClassNotFoundException {
        Class.forName(driverClassName);
        connection = DriverManager.getConnection(dbURL, user, password);
    }
    
    public void shutdown() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }
    
    public Secretaria getSecretaria(int id) {
        String query = "select ID_SECRETARIA, NOMBRE_SEC, CEDULA_SEC, DIRECCION_SEC, TELEFONO_SEC, CORREO_SEC from secretaria where ID_SECRETARIA = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int idSecretaria = rs.getInt("ID_SECRETARIA");
                    String nombre = rs.getString("NOMBRE_SEC");
                    String cedula = rs.getString("CEDULA_SEC");
                    String direccion = rs.getString("DIRECCION_SEC");
                    String telefono = rs.getString("TELEFONO_SEC");
                    String correo = rs.getString("CORREO_SEC");
                    return new Secretaria(idSecretaria, nombre, cedula, direccion, telefono, correo);
                } else {
                    return new Secretaria();
                }
            }
        } catch (SQLException ex) {
            return null;
        }
    }
    
    public List<Secretaria> getSecretariaList() {
        try (
            Statement stmnt = connection.createStatement();
            ResultSet rs = stmnt.executeQuery("select ID_SECRETARIA, NOMBRE_SEC, CEDULA_SEC, DIRECCION_SEC, TELEFONO_SEC, CORREO_SEC from secretaria");) {
            List<Secretaria> secretariaList = new ArrayList<>();
            while (rs.next()) {
                int idSecretaria = rs.getInt("ID_SECRETARIA");
                String nombre = rs.getString("NOMBRE_SEC");
                String cedula = rs.getString("CEDULA_SEC");
                String direccion = rs.getString("DIRECCION_SEC");
                String telefono = rs.getString("TELEFONO_SEC");
                String correo = rs.getString("CORREO_SEC");
                Secretaria secretaria = new Secretaria(idSecretaria, nombre, cedula, direccion, telefono, correo);
                secretariaList.add(secretaria);
            }
            return secretariaList;
        } catch (SQLException ex) {
            return new ArrayList<>();
        }
    }
    
    public boolean insertNewSec(Secretaria sec) {
        String query = "INSERT INTO secretaria (NOMBRE_SEC, CEDULA_SEC, DIRECCION_SEC, TELEFONO_SEC, CORREO_SEC) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, sec.getNombre());
            ps.setString(2, sec.getCedula());
            ps.setString(3, sec.getDireccion());
            ps.setString(4, sec.getTelefono());
            ps.setString(5, sec.getCorreo());

            ps.execute();
            connection.commit();
            
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }
    
    public boolean updateSec(Secretaria sec) {
        String query = "UPDATE secretaria SET NOMBRE_SEC = ?, CEDULA_SEC = ?, DIRECCION_SEC = ?, TELEFONO_SEC = ?, CORREO_SEC = ? WHERE ID_SECRETARIA = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, sec.getNombre());
            ps.setString(2, sec.getCedula());
            ps.setString(3, sec.getDireccion());
            ps.setString(4, sec.getTelefono());
            ps.setString(5, sec.getCorreo());
            ps.setInt(6, sec.getId_secretaria());

            ps.executeUpdate();
            
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }
    
    public boolean deleteSec(int id) {
        String query = "DELETE FROM secretaria WHERE ID_SECRETARIA = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);

            ps.execute();
            
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }
    
}
