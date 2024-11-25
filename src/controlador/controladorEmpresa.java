package controlador;

import java.io.*;
import modelo.Empresa;

public class controladorEmpresa {

    private final String archivoEmpresa = "empresa.txt";
    private Empresa empresa;

    public controladorEmpresa() throws IOException {
        empresa = cargarDatosEmpresa();
        if (empresa == null) { // Validación de seguridad
            empresa = new Empresa("Nombre Empresa", "Dirección Predeterminada", "000-000-0000", "12345678");
        }
    }

    // Cargar los datos de la empresa desde el archivo
private Empresa cargarDatosEmpresa() throws IOException {
    File archivo = new File(archivoEmpresa);

    if (!archivo.exists()) {
        Empresa empresaPredeterminada = new Empresa(
            "Nombre Empresa", "Dirección Predeterminada", "000-000-0000", "12345678"
        );
        guardarDatosEmpresa(empresaPredeterminada);
        return empresaPredeterminada;
    }

    try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
        String linea = reader.readLine();
        if (linea != null) {
            if (!linea.isEmpty()) {
                    String[] partes = linea.split(",");
                    if (partes.length == 4) {
                        return new Empresa(
                                partes[0], partes[1],
                                partes[2],partes[3]
                        );
                    }
                }
        }
    } catch (Exception e) {
        System.err.println("Error al cargar los datos de la empresa: " + e.getMessage());
    }

    // Si algo falla, devuelve un objeto predeterminado
    return new Empresa("Nombre Empresa", "Dirección Predeterminada", "000-000-0000", "12345678");
}

    // Guardar los datos de la empresa en el archivo
    public void guardarDatosEmpresa(Empresa empresa) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivoEmpresa))) {
            writer.write(empresa.toString());
        }
    }

    // Obtener los datos de la empresa
    public Empresa getEmpresa() {
        return empresa;
    }

    // Actualizar los datos de la empresa
    public void actualizarDatosEmpresa(String nombre, String direccion, String telefono, String nit) throws IOException {
        empresa.setNombre(nombre);
        empresa.setDireccion(direccion);
        empresa.setTelefono(telefono);
        empresa.setNit(nit);
        guardarDatosEmpresa(empresa);
    }
}
