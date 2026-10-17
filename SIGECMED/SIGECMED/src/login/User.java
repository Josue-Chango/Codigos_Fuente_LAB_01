/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author frani
 */
public class User extends RecursiveTreeObject<User>{

    private final SimpleIntegerProperty id_user = new SimpleIntegerProperty(this, "id_user");
    private final SimpleIntegerProperty id_rol = new SimpleIntegerProperty(this, "id_rol");
    private final SimpleStringProperty usuario = new SimpleStringProperty(this, "usuario");
    private String contrasena;
    private String salt;
    private final SimpleIntegerProperty id_asociado = new SimpleIntegerProperty(this, "id_asociado");

    public User() {}
    
    public User(int idUser, int idRol, String usuario, String contrasena, String salt, int idAsociado) {
        setId_user(idUser);
        setId_rol(idRol);
        setUsuario(usuario);
        this.contrasena = contrasena;
        this.salt = salt;
        setId_asociado(idAsociado);
    }
    
    public IntegerProperty id_userProperty() {
        return id_user;
    }

    public final int getId_user() {
        return id_userProperty().get();
    }

    public final void setId_user(int idUser) {
        id_userProperty().set(idUser);
    }
    
    public IntegerProperty id_rolProperty() {
        return id_rol;
    }

    public final int getId_rol() {
        return id_rolProperty().get();
    }

    public final void setId_rol(int idRol) {
        id_rolProperty().set(idRol);
    }
    
    public StringProperty usuarioProperty() {
        return usuario;
    }

    public final String getUsuario() {
        return usuarioProperty().get();
    }

    public final void setUsuario(String usuario) {
        usuarioProperty().set(usuario);
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
    
    public IntegerProperty id_asociadoProperty() {
        return id_asociado;
    }

    public final int getId_asociado() {
        return id_asociadoProperty().get();
    }

    public final void setId_asociado(int idAsociado) {
        id_asociadoProperty().set(idAsociado);
    }
    
}
