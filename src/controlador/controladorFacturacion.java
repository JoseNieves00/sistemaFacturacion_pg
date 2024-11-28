package controlador;

import java.awt.Desktop;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import modelo.Empresa;
import modelo.Factura;
import modelo.ItemFactura;
import modelo.Producto;
import modelo.Usuario;

public class controladorFacturacion {

    private final String carpetaFacturas = "facturas/";
    private final String archivoContador = "contador_facturas.txt";
    private controladorProductos controladorProductos;
    private controladorEmpresa controladorEmpresa;
    private int contadorFacturas;
    private controladorLogin controladorLogin;

    public controladorFacturacion() throws IOException {
        controladorProductos = new controladorProductos();
        controladorEmpresa = new controladorEmpresa();
        controladorLogin = new controladorLogin();

        // Crear carpeta de facturas si no existe
        File carpeta = new File(carpetaFacturas);
        if (!carpeta.exists()) {
            carpeta.mkdir();
        }

        // Inicializar contador
        contadorFacturas = cargarContadorFacturas();
    }

    // Método para cargar el contador de facturas desde el archivo
    private int cargarContadorFacturas() {
        File archivo = new File(carpetaFacturas + archivoContador);

        if (!archivo.exists()) {
            return 1; // Si no existe, el contador comienza en 1
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea = reader.readLine();
            return (linea != null) ? Integer.parseInt(linea) : 1;
        } catch (IOException | NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar el contador de facturas: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            return 1; // En caso de error, comienza en 1
        }
    }

    // Método para guardar el contador de facturas en el archivo
    private void guardarContadorFacturas() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(carpetaFacturas + archivoContador))) {
            writer.write(String.valueOf(contadorFacturas));
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar el contador de facturas: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Crear una factura con los productos seleccionados
    public Factura crearFactura(ArrayList<ItemFactura> items, double recibido, double cambio, String metodoPago) throws IOException {
        String numeroFactura = String.format("FACT-%05d", contadorFacturas);
        Factura factura = new Factura(numeroFactura);

        factura.setCambio(cambio);
        factura.setRecibido(recibido);
        factura.setMetodoPago(metodoPago);

        Empresa empresa = controladorEmpresa.getEmpresa();

        for (ItemFactura item : items) {
            Producto producto = controladorProductos.buscarProducto(item.getCodigo());

            if (producto == null) {
                JOptionPane.showMessageDialog(null, "Producto no encontrado: " + item.getCodigo(),
                        "Error", JOptionPane.ERROR_MESSAGE);
                continue; // Pasar al siguiente producto
            }

            if (producto.getCantidad() < item.getCantidad()) {
                JOptionPane.showMessageDialog(null, "Stock insuficiente para el producto: " + producto.getNombre(),
                        "Error", JOptionPane.ERROR_MESSAGE);
                continue; // Pasar al siguiente producto
            }

            // Reducir stock
            producto.setCantidad(producto.getCantidad() - item.getCantidad());
            factura.agregarItem(item);
        }

        if (factura.getItems().isEmpty()) {
            JOptionPane.showMessageDialog(null, "No se pudo generar la factura. Verifique los productos.",
                    "Advertencia", JOptionPane.WARNING_MESSAGE);
            return null;
        }

        // Guardar factura y actualizar el contador
        controladorProductos.guardarProductos(); // Actualizar inventario
        guardarFacturaEnArchivo(factura, empresa); // Guardar la factura en el archivo
        contadorFacturas++;
        guardarContadorFacturas();

        return factura;
    }

    // Guardar una factura en un archivo
    private void guardarFacturaEnArchivo(Factura factura, Empresa empresa) throws IOException {
        String archivoFactura = carpetaFacturas + factura.getNumeroFactura() + ".txt";
        Usuario usuarioActivo = controladorLogin.getUsuarioActivo();

        if (usuarioActivo == null) {
            JOptionPane.showMessageDialog(null, "No se pudo identificar al usuario activo.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivoFactura))) {
            writer.write(empresa.getNombre());
            writer.newLine();
            writer.write("NIT " + empresa.getNit());
            writer.newLine();
            writer.write("Dirección " + empresa.getDireccion());
            writer.newLine();
            writer.write("Telefono " + empresa.getTelefono());
            writer.newLine();
            writer.newLine();
            writer.write("Vendedor: " + usuarioActivo.getNombre().toUpperCase());
            writer.newLine();
            writer.write("Factura: " + factura.getNumeroFactura());
            writer.newLine();
            writer.write("Fecha: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(factura.getFecha()));
            writer.newLine();
            writer.newLine();
            writer.write("----------------------------------------------------------------------------------------------------");
            writer.newLine();
            writer.write("\nCódigo\t\t Nombre  \t\t\t Cantidad  \t\t\t\tPrecio  \t\tSubtotal");
            writer.newLine();

            for (ItemFactura item : factura.getItems()) {
                writer.write(String.format("%s \t\t" +"|"+ "\t%s\t\t" +"|"+ "\t\t%d\t\t" +"|"+ "\t$%.0f\t\t" +"|"+ "\t$%.0f\t",
                        item.getCodigo(),
                        item.getNombre(),
                        item.getCantidad(),
                        item.getPrecio(),
                        item.getSubtotal()));
                writer.newLine();
            }

            writer.write("\n-------------------------------------------------------------------------------------------------");
            writer.newLine();
            writer.write("\nSubtotal: $" + factura.getSubtotal());
            writer.newLine();
            writer.write("IVA 19%: $" + factura.getSubtotal() * 0.19);
            writer.newLine();
            writer.write("Total: $" + factura.getTotal());
            writer.newLine();
            writer.write("\nPago: \t$" + factura.getRecibido() + " \t" + factura.getMetodoPago());
            writer.newLine();
            writer.write("Cambio: $" + factura.getCambio());
            writer.newLine();
            writer.write("\n--------------------------------------------------------------------------------------------------");
            writer.newLine();
            writer.write("\nMuchas gracias por su compra!");

        }

        JOptionPane.showMessageDialog(null, "Factura generada Exitosamente");

        // Abrir la factura generada automáticamente
        abrirArchivo(archivoFactura);
    }

    // Abrir un archivo generado
    private void abrirArchivo(String rutaArchivo) {
        File archivo = new File(rutaArchivo);
        if (!archivo.exists()) {
            JOptionPane.showMessageDialog(null, "El archivo no existe: " + rutaArchivo,
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            Desktop desktop = Desktop.getDesktop();
            if (desktop.isSupported(Desktop.Action.OPEN)) {
                desktop.open(archivo);
            } else {
                JOptionPane.showMessageDialog(null, "Abrir archivos no está soportado en este sistema.",
                        "Advertencia", JOptionPane.WARNING_MESSAGE);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al abrir el archivo: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
