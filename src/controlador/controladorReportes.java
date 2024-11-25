package controlador;

import java.awt.Desktop;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.JOptionPane;

public class controladorReportes {

    private final String carpetaFacturas = "facturas/";
    private final String carpetaReportes = "reportes/";
    private final String archivoContadorReportes = "contador_reportes.txt";
    private int contadorReportes;

    public controladorReportes() throws IOException {
        // Crear la carpeta de reportes si no existe
        File carpeta = new File(carpetaReportes);
        if (!carpeta.exists()) {
            carpeta.mkdir();
        }

        // Cargar el contador de reportes
        contadorReportes = cargarContadorReportes();
    }

    private int cargarContadorReportes() {
        File archivo = new File(carpetaReportes + archivoContadorReportes);
        if (!archivo.exists()) {
            return 1; // Si no existe, inicia el contador en 1
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea = reader.readLine();
            return (linea != null) ? Integer.parseInt(linea) : 1;
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
            return 1; // En caso de error, iniciar en 1
        }
    }

    private void guardarContadorReportes() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(carpetaReportes + archivoContadorReportes))) {
            writer.write(String.valueOf(contadorReportes));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

public void generarReporteVentas() {
    File carpeta = new File(carpetaFacturas);

    if (!carpeta.exists() || !carpeta.isDirectory()) {
        JOptionPane.showMessageDialog(null, "La carpeta de facturas no existe.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Variables para consolidar datos
    double totalVentas = 0;
    int totalProductosVendidos = 0;
    Map<String, Integer> productosVendidos = new HashMap<>();
    String fechaActual = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

    // Leer todos los archivos en la carpeta de facturas
    File[] archivos = carpeta.listFiles((dir, name) -> name.startsWith("FACT"));
    if (archivos == null || archivos.length == 0) {
        JOptionPane.showMessageDialog(null, "No se encontraron facturas para generar el reporte.", "Información", JOptionPane.INFORMATION_MESSAGE);
        return;
    }

    StringBuilder detalleFacturas = new StringBuilder();
    detalleFacturas.append("Reporte Detallado de Ventas\n\n");
    detalleFacturas.append("Fecha del Reporte: ").append(fechaActual).append("\n\n");
    detalleFacturas.append("---------------------------------------------------\n");

    for (File archivo : archivos) {
        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            boolean esDetalle = false;
            String facturaId = "";
            double facturaSubtotal = 0;
            double facturaIVA = 0;
            double facturaTotal = 0;

            detalleFacturas.append("Factura: ").append(archivo.getName().replace(".txt", "")).append("\n");

            while ((linea = reader.readLine()) != null) {
                linea=linea.trim();
                
                if(linea.isEmpty()||linea.startsWith("-----")){
                    continue;
                }
                // Procesar el encabezado y otros detalles
                if (linea.startsWith("Factura:")) {
                    facturaId = linea.split(":")[1].trim();
                } else if (linea.startsWith("Subtotal:")) {
                    facturaSubtotal = Double.parseDouble(linea.split(":")[1].replace("$", "").trim());
                } else if (linea.startsWith("IVA")) {
                    facturaIVA = Double.parseDouble(linea.split(":")[1].replace("$", "").trim());
                } else if (linea.startsWith("Total:")) {
                    facturaTotal = Double.parseDouble(linea.split(":")[1].replace("$", "").trim());
                }

                // Procesar los detalles de productos
                if (linea.startsWith("Código")) {
                    esDetalle = true;
                    detalleFacturas.append("Código\tNombre\t\tCantidad\tPrecio\tSubtotal\n");
                    continue;
                }

                if (esDetalle && linea.trim().length() > 0 && !linea.startsWith("----")) {
                    String[] partes = linea.split("\\s+");
                    if (partes.length >= 5) {
                        String codigo = partes[0];
                        String nombre = partes[1];
                        int cantidad = Integer.parseInt(partes[2].trim());
                        double subtotal = Double.parseDouble(partes[4].replace("$", "").replace(",", "").trim());

                        totalProductosVendidos += cantidad;
                        totalVentas += subtotal/100;

                        productosVendidos.put(nombre, productosVendidos.getOrDefault(nombre, 0) + cantidad);

                        detalleFacturas.append(String.format("%s\t%s\t\t%d\t\t$%.2f\t$%.2f\n", codigo, nombre, cantidad, (subtotal / cantidad)/100, subtotal/100));
                        detalleFacturas.append("\n");
                    }
                }
            }

            detalleFacturas.append("---------------------------------------------------\n");
            detalleFacturas.append(String.format("Subtotal: $%.2f\n", facturaSubtotal/100));
            detalleFacturas.append(String.format("IVA 19%%: $%.2f\n", facturaIVA/100));
            detalleFacturas.append(String.format("Total: $%.2f\n", facturaTotal/100));
            detalleFacturas.append("---------------------------------------------------\n\n");
        } catch (IOException | NumberFormatException e) {
            System.out.println(e);
        }
    }

    // Consolidar resumen final
    detalleFacturas.append("\n\nResumen General:\n");
    detalleFacturas.append("---------------------------------------------------\n");
    detalleFacturas.append(String.format("Total de productos vendidos: %d\n", totalProductosVendidos));
    detalleFacturas.append(String.format("Total de ventas: $%.2f\n", totalVentas));
    detalleFacturas.append("---------------------------------------------------\n");

    // Guardar el reporte en un archivo
    String numeroReporte = String.format("REPORTE-%05d", contadorReportes);
    String archivoReporte = carpetaReportes + numeroReporte + ".txt";

    try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivoReporte))) {
        writer.write(detalleFacturas.toString());
        JOptionPane.showMessageDialog(null, "Reporte generado exitosamente","Confirmacion", JOptionPane.INFORMATION_MESSAGE);
        contadorReportes++;
        guardarContadorReportes();
        abrirArchivo(archivoReporte);
    } catch (IOException e) {
        JOptionPane.showMessageDialog(null, "Error al guardar el reporte: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}

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
