/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import modelo.Usuario;

import java.io.*;
import java.util.ArrayList;

public class controladorLogin {

    private ArrayList<Usuario> usuarios;
    private final String archivoUsuarios = "usuarios.txt";

    public controladorLogin() throws IOException {
        usuarios = cargarUsuarios();
    }

    // Cargar usuarios desde el archivo
    private ArrayList<Usuario> cargarUsuarios() throws IOException {
        ArrayList<Usuario> usuarios = new ArrayList<>();
        File archivo = new File(archivoUsuarios);

        // Si el archivo no existe, lo creamos con un usuario admin por defecto
        if (!archivo.exists()) {
            crearArchivoUsuariosPorDefecto();
        }

        // Leer el archivo de usuarios
        try (BufferedReader reader = new BufferedReader(new FileReader(archivoUsuarios))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(",");
                usuarios.add(new Usuario(partes[0], partes[1])); // username, password, role
            }
        }

        return usuarios;
    }

    // Crear archivo de usuarios con un usuario admin por defecto
    private void crearArchivoUsuariosPorDefecto() throws IOException {
        ArrayList<String> lineas = new ArrayList<>();
        lineas.add("admin,admin,admin"); // Usuario admin por defecto
        escribirArchivo(lineas);
    }

    // Escribir usuarios en el archivo
    private void escribirArchivo(ArrayList<String> lineas) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivoUsuarios, false))) {
            for (String linea : lineas) {
                writer.write(linea);
                writer.newLine();
            }
        }
    }

    // Método para autenticar al usuario
    public Usuario autenticar(String username, String password) {
        for (Usuario usuario : usuarios) {
            if (usuario.validarCredenciales(username, password)) {
                return usuario; // Devuelve el usuario si las credenciales son correctas
            }
        }
        return null; // Si no se encuentra un usuario válido
    }
}

