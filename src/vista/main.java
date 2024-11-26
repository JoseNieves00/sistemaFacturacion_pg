package vista;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class main {
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            try {
                new viewLogin().setVisible(true);
            } catch (IOException ex) {
                Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
}
