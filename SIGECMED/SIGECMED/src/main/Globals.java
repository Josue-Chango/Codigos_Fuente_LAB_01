/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author frani
 */
public class Globals {
    
    public final static String DRIVER_CLASS_NAME;
    public final static String DB_URL;
    public final static String DB_USER;
    public final static String DB_PASSWORD;
    
    private Globals() {
    }
    
    static {
        Properties props = new Properties();
        try (FileInputStream in = new FileInputStream("database.properties")) {
            props.load(in);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Globals.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Globals.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        DRIVER_CLASS_NAME = props.getProperty("jdbc.driver");
        DB_URL = props.getProperty("jdbc.url");
        DB_USER = props.getProperty("jdbc.username");
        DB_PASSWORD = props.getProperty("jdbc.password");
    }
    
}
