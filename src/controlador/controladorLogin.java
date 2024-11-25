package controlador;

import modelo.Usuario;

import java.io.*;
import java.util.ArrayList;

public class controladorLogin {

    private ArrayList<Usuario> usuarios;
    private final String archivoUsuarios = "usuarios.txt";
    private static Usuario usuarioActivo; // Usuario actualmente autenticado

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
                if (partes.length == 4) {
                    usuarios.add(new Usuario(partes[0], partes[1], partes[2], partes[3])); // username, password, nombre, rol
                }
            }
        }

        return usuarios;
    }

    // Crear archivo de usuarios con un usuario admin por defecto
    private void crearArchivoUsuariosPorDefecto() throws IOException {
        ArrayList<String> lineas = new ArrayList<>();
        lineas.add("admin,admin,Administrador,admin"); // Usuario admin por defecto
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
                usuarioActivo = usuario;
                return usuario; // Devuelve el usuario si las credenciales son correctas
            }
        }
        return null; // Si no se encuentra un usuario válido
    }

    // Obtener el usuario actualmente activo
    public static Usuario getUsuarioActivo() {
        return usuarioActivo;
    }

    // Método para configurar el usuario activo
    public static void setUsuarioActivo(Usuario usuario) {
        usuarioActivo = usuario;
    }

    // Agregar un nuevo usuario
    public void agregarUsuario(String username, String password, String nombre, String rol) throws IOException {
        if (buscarUsuario(username) != null) {
            throw new IllegalArgumentException("El usuario ya existe.");
        }

        Usuario nuevoUsuario = new Usuario(username, password, nombre, rol);
        usuarios.add(nuevoUsuario);
        guardarUsuarios();
    }

    // Listar todos los usuarios
    public ArrayList<Usuario> listarUsuarios() {
        return usuarios;
    }

    // Modificar un usuario existente
    public void modificarUsuario(String username, Usuario usuarioModificado) throws IOException {
        Usuario usuario = buscarUsuario(username);
        if (usuario == null) {
            throw new IllegalArgumentException("El usuario no existe.");
        }

        // Actualizar los datos del usuario
        usuario.setPassword(usuarioModificado.getPassword());
        usuario.setNombre(usuarioModificado.getNombre());
        usuario.setRole(usuarioModificado.getRole());

        guardarUsuarios(); // Guardar los cambios en el archivo
    }

    // Eliminar un usuario
    public void eliminarUsuario(String username) throws IOException {
        Usuario usuario = buscarUsuario(username);
        if (usuario == null) {
            throw new IllegalArgumentException("El usuario no existe.");
        }

        usuarios.remove(usuario);
        guardarUsuarios(); // Guardar los cambios en el archivo
    }

    // Buscar un usuario por username
    private Usuario buscarUsuario(String username) {
        for (Usuario usuario : usuarios) {
            if (usuario.getUsername().equals(username)) {
                return usuario;
            }
        }
        return null;
    }
    
    private void guardarUsuarios() throws IOException {
    ArrayList<String> lineas = new ArrayList<>();
    for (Usuario usuario : usuarios) {
        lineas.add(usuario.getUsername() + "," + usuario.getPassword() + "," + usuario.getNombre() + "," + usuario.getRole());
    }
    escribirArchivo(lineas);
}
}
