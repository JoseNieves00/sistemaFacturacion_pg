/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.io.*;
import java.util.ArrayList;
import modelo.Producto;
import modelo.Proveedor;

public class controladorProductos {

    public boolean verificarCodigo(String codigo) {
    for (Producto p : productos) {
        if (p.getCodigo().equals(codigo)) {
            return true; // El código ya existe en la lista
        }
    }
    return false; // El código no existe
}
   

    private final String archivoProductos = "inventario.txt";
    private ArrayList<Producto> productos;

    public controladorProductos() throws IOException {
        productos = cargarProductos();
    }

    // Cargar proveedores desde el archivo
    private ArrayList<Producto> cargarProductos() throws IOException {
        ArrayList<Producto> lista = new ArrayList<>();
        File archivo = new File(archivoProductos);

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
                    if (partes.length == 5) { // Verificar que hay 3 partes
                        lista.add(new Producto(partes[0], partes[1], Double.parseDouble(partes[2]),Double.parseDouble(partes[3]),Integer.parseInt(partes[4])));
                    } else {
                        System.err.println("Línea inválida en el archivo: " + linea);
                    }
                }
            }
        }

        return lista;
    }

    // Agregar un proveedor y guardar en el archivo
    public void agregarProducto(Producto producto) throws IOException {
        productos.add(producto);
        guardarProducto();
    }

    // Guardar todos los proveedores en el archivo
    private void guardarProducto() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivoProductos, false))) {
            for (Producto producto : productos) {
                writer.write(producto.toString());
                writer.newLine();
            }
        }
    }

    public void eliminarProducto(String nit) throws IOException {
        // Buscar y eliminar el proveedor con el NIT
        for (int i = 0; i < productos.size(); i++) {
            if (productos.get(i).getCodigo().equals(nit)) {
                productos.remove(i); // Eliminar proveedor de la lista
                break;
            }
        }
        // Guardar los cambios en el archivo
        guardarProducto();
    }

    public void modificarProveedor(String nit, Producto nuevoProducto) throws IOException {
        for (int i = 0; i < productos.size(); i++) {
            if (productos.get(i).getCodigo().equals(nit)) {
                productos.set(i, nuevoProducto); // Actualizar el proveedor con los nuevos datos
                break;
            }
        }
        // Guardar los cambios en el archivo
        guardarProducto();
    }

    // Obtener la lista de proveedores
    public ArrayList<Producto> listarProductos() {
        return productos;
    }}


