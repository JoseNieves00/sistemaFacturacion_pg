/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import vista.viewMenu2;

/**
 *
 * @author hp
 */
public class Menu {
    public static void main(String[] args){
        viewMenu2 m;
        try {
            m = new viewMenu2();
            m.setVisible(true);
        } catch (IOException ex) {
            Logger.getLogger(viewMenu2.class.getName()).log(Level.SEVERE, null, ex);
        }
    } 
    
}
