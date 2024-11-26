/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import modelo.Proveedor;

import java.io.*;
import java.util.ArrayList;

public class controladorProveedor {

    private final String archivoProveedores = "proveedores.txt";
    private ArrayList<Proveedor> proveedores;

    public controladorProveedor() throws IOException {
        proveedores = cargarProveedores();
    }

    // Cargar proveedores desde el archivo
    private ArrayList<Proveedor> cargarProveedores() throws IOException {
        ArrayList<Proveedor> lista = new ArrayList<>();
        File archivo = new File(archivoProveedores);

        // Si el archivo no existe, lo creamos vacío
        if (!archivo.exists()) {
            archivo.createNewFile();
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                // Validar que la línea no esté vacía y tenga al menos 3 partes
                if (!linea.trim().isEmpty()) {
                    String[] partes = linea.split(",");
                    if (partes.length == 3) { // Verificar que hay 3 partes
                        lista.add(new Proveedor(partes[0], partes[1], partes[2]));
                    } else {
                        System.err.println("Línea inválida en el archivo: " + linea);
                    }
                }
            }
        }

        return lista;
    }

    // Agregar un proveedor y guardar en el archivo
    public void agregarProveedor(Proveedor proveedor) throws IOException {
        proveedores.add(proveedor);
        guardarProveedores();
    }

    // Guardar todos los proveedores en el archivo
    private void guardarProveedores() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivoProveedores, false))) {
            for (Proveedor proveedor : proveedores) {
                writer.write(proveedor.toString());
                writer.newLine();
            }
        }
    }

    public void eliminarProveedor(String nit) throws IOException {
        // Buscar y eliminar el proveedor con el NIT
        for (int i = 0; i < proveedores.size(); i++) {
            if (proveedores.get(i).getNit().equals(nit)) {
                proveedores.remove(i); // Eliminar proveedor de la lista
                break;
            }
        }
        // Guardar los cambios en el archivo
        guardarProveedores();
    }

    public void modificarProveedor(String nit, Proveedor nuevoProveedor) throws IOException {
        for (int i = 0; i < proveedores.size(); i++) {
            if (proveedores.get(i).getNit().equals(nit)) {
                proveedores.set(i, nuevoProveedor); // Actualizar el proveedor con los nuevos datos
                break;
            }
        }
        // Guardar los cambios en el archivo
        guardarProveedores();
    }

    // Obtener la lista de proveedores
    public ArrayList<Proveedor> listarProveedores() {
        return proveedores;
    }
    
    public ArrayList<String> obtenerProveedores() {
    ArrayList<String> nombresProveedores = new ArrayList<>();
    for (Proveedor proveedor : proveedores) {
        System.out.println(proveedor.getNombre());
        nombresProveedores.add(proveedor.getNombre());
    }
    return nombresProveedores;
}

}
