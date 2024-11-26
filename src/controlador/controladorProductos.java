package controlador;

import java.io.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import modelo.Producto;

public class controladorProductos {

    public static void modificarPoductos(String codigo, Producto productoModificado) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

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
    public ArrayList<Producto> cargarProductos() throws IOException {
        ArrayList<Producto> lista = new ArrayList<>();
        File archivo = new File(archivoProductos);

        if (!archivo.exists()) {
            archivo.createNewFile(); // Crear el archivo vacío si no existe
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {

                if (!linea.isEmpty()) {
                    String[] partes = linea.split(",");
                    if (partes.length == 6) {
                        Producto producto = new Producto(
                                partes[0], partes[1],
                                Double.parseDouble(partes[2]), Double.parseDouble(partes[3]),
                                Integer.parseInt(partes[4]),partes[5]
                        );
                        lista.add(producto);
                    }
                }
            }
        }

        return lista; // Devuelve la lista cargada
    }

    // Agregar un proveedor y guardar en el archivo
    public void agregarProducto(Producto producto) throws IOException {
        productos.add(producto);
        guardarProductos();
    }

    // Guardar todos los proveedores en el archivo
    public void guardarProducto() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivoProductos))) {
            for (Producto producto : productos) {
                writer.write(producto.toString());
                writer.newLine();
            }
        }
    }

    public void guardarProductos() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivoProductos))) {
            for (Producto producto : productos) {
                writer.write(producto.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar productos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            throw e;
        }
    }

    public void eliminarProducto(String codigo) throws IOException {
        // Buscar y eliminar el proveedor con el NIT
        for (int i = 0; i < productos.size(); i++) {
            if (productos.get(i).getCodigo().equals(codigo)) {
                productos.remove(i); // Eliminar proveedor de la lista
                break;
            }
        }
        // Guardar los cambios en el archivo
        guardarProducto();
    }

    public void reducirStock(String codigo, int cantidad) throws IOException {
        for (Producto producto : productos) {
            if (producto.getCodigo().equalsIgnoreCase(codigo)) {
                // Verificamos si hay suficiente stock para reducir temporalmente
                if (producto.getCantidad() >= cantidad) {
                    // Reducir la cantidad en el inventario temporalmente
                    producto.setCantidad(producto.getCantidad() - cantidad);
                    return; // Salimos del método después de reducir el stock
                } else {
                    throw new IllegalArgumentException("Stock insuficiente para el producto: " + producto.getNombre());
                }
            }
        }
        throw new IllegalArgumentException("Producto no encontrado en el inventario.");
    }

    public void modificarProducto(String codigo, Producto nuevoProducto) throws IOException {
        for (int i = 0; i < productos.size(); i++) {
            if (productos.get(i).getCodigo().equals(codigo)) {
                productos.set(i, nuevoProducto); // Actualizar el proveedor con los nuevos datos
                break;
            }
        }
        // Guardar los cambios en el archivo
        guardarProducto();
    }

    public Producto buscarProducto(String codigo) {
        codigo = codigo.trim();
        for (Producto producto : productos) {
            String productCodigo = producto.getCodigo().trim();
            if (productCodigo.equals(codigo)) {
                return producto;
            }
        }
        JOptionPane.showMessageDialog(null, "Producto no encontrado!", "Error", JOptionPane.ERROR_MESSAGE);
        return null;
    }

    // Obtener la lista de proveedores
    public ArrayList<Producto> listarProductos() {
        return productos;
    }
}
