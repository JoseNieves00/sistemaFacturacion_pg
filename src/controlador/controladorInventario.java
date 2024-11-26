/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package controlador;

import modelo.Producto;
import java.io.*;
import java.util.ArrayList;

public class controladorInventario {

    private ArrayList<Producto> lista;
    private static final String FILE_NAME = "productos.txt"; // Nombre del archivo

    public controladorInventario() {
        lista = new ArrayList<>();
        cargarDatosDesdeArchivo();
    }

    // Método para cargar productos desde el archivo
    private void cargarDatosDesdeArchivo() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            return; // Si el archivo no existe, no hace falta cargar nada
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 6) {
                    Producto p = new Producto(
                            data[0], data[1],
                            Double.parseDouble(data[2]),
                            Double.parseDouble(data[3]),
                            Integer.parseInt(data[4]),
                            data[5]
                    );
                    lista.add(p);
                }
            }
            System.out.println("Datos cargados exitosamente desde el archivo.");
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error al cargar los datos desde el archivo: " + e.getMessage());
        }
    }

    // Método para obtener la lista de productos
    public ArrayList<Producto> getListaProductos() {
        return lista;
    }

    // Método para obtener productos disponibles para facturar
    public ArrayList<Producto> obtenerProductosFacturables() {
        ArrayList<Producto> facturables = new ArrayList<>();
        for (Producto p : lista) {
            if (p.getCantidad() > 0) { // Solo agregar productos con cantidad > 0
                facturables.add(p);
            }
        }
        return facturables;
    }

    // Método para obtener un producto por su código
    public Producto obtenerProductoPorCodigo(String codigo) {
        for (Producto p : lista) {
            if (p.getCodigo().equals(codigo)) {
                return p;
            }
        }
        return null; // No se encontró el producto
    }
}
