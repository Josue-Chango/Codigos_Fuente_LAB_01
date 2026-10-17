/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author frani
 */
public class UserDataAccessor {
    
    private Connection connection;
    
    public UserDataAccessor(String driverClassName, String dbURL, String user, String password) throws SQLException, ClassNotFoundException {
        Class.forName(driverClassName);
        connection = DriverManager.getConnection(dbURL, user, password);
    }
    
    public void shutdown() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }
    
    public User getUser(String usuarioAux) {
        String query = "select ID_USUARIO, ID_ROL, USUARIO, CONTRASENA, SALT, ID_ASOCIADO from usuario where USUARIO = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, usuarioAux);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int idUser = rs.getInt("ID_USUARIO");
                    int idRol = rs.getInt("ID_ROL");
                    String usuario = rs.getString("USUARIO");
                    String contrasena = rs.getString("CONTRASENA");
                    String salt = rs.getString("SALT");
                    int idAsociado = rs.getInt("ID_ASOCIADO");
                    return new User(idUser, idRol, usuario, contrasena, salt, idAsociado);
                } else {
                    return new User();
                }
            }
        } catch (SQLException ex) {
            return null;
        }
    }
    
    public String getAssociateUserName(int idA, int idRol) {
        String query;
        switch(idRol) {
            case 2:
                query = "select NOMBRE_SEC from doctor where ID_DOCTOR = ?";
                break;
            case 3:
                query = "select NOMBRE_SEC from secretaria where ID_SECRETARIA = ?";
                break;
            default:
                return null;
        }
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, idA);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("NOMBRE_SEC");
                } else {
                    return "";
                }
            }
        } catch (SQLException ex) {
            return null;
        }
    }
    
    public List<User> getUsersList() {
        try (
            Statement stmnt = connection.createStatement();
            ResultSet rs = stmnt.executeQuery("select ID_USUARIO, ID_ROL, USUARIO, CONTRASENA, SALT, ID_ASOCIADO from usuario");) {
            List<User> userList = new ArrayList<>();
            while (rs.next()) {
                int idUser = rs.getInt("ID_USUARIO");
                int idRol = rs.getInt("ID_ROL");
                String usuario = rs.getString("USUARIO");
                String contrasena = rs.getString("CONTRASENA");
                String salt = rs.getString("SALT");
                int idAsociado = rs.getInt("ID_ASOCIADO");
                User person = new User(idUser, idRol, usuario, contrasena, salt, idAsociado);
                userList.add(person);
            }
            return userList;
        } catch (SQLException ex) {
            return new ArrayList<>();
        }
    }
    
    public boolean insertNewUser(User user) {
        String query = "INSERT INTO usuario (ID_ROL, USUARIO, CONTRASENA, ID_ASOCIADO, SALT) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, user.getId_rol());
            ps.setString(2, user.getUsuario());
            ps.setString(3, user.getContrasena());
            ps.setInt(4, user.getId_asociado());
            ps.setString(5, user.getSalt());

            ps.execute();
            connection.commit();
            
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }
    
    public boolean updateUser(User user) {
        String query = "UPDATE usuario SET ID_ROL = ?, USUARIO = ?, CONTRASENA = ?, ID_ASOCIADO = ?, SALT = ? WHERE ID_USUARIO = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, user.getId_rol());
            ps.setString(2, user.getUsuario());
            ps.setString(3, user.getContrasena());
            ps.setInt(4, user.getId_asociado());
            ps.setString(5, user.getSalt());
            ps.setInt(6, user.getId_user());

            ps.executeUpdate();
            
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }
    
    public boolean deleteUser(int id) {
        String query = "DELETE FROM usuario WHERE ID_USUARIO = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);

            ps.execute();
            
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }
    
}
