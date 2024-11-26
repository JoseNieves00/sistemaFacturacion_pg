package modelo;

public class Empresa{
    private String nombre;
    private String direccion;
    private String telefono;
    private String nit;
    private Boolean isEdited;

    public Empresa(String nombre, String direccion, String telefono, String nit,Boolean isEdited) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.nit = nit;
        this.isEdited = isEdited;
    }

    public Boolean getIsEdited() {
        return isEdited;
    }

    public void setIsEdited(Boolean isEdited) {
        this.isEdited = isEdited;
    }

    public Empresa() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }
    
     @Override
    public String toString() {
        return String.format("%s,%s,%s,%s,%b", nombre, direccion, telefono, nit,isEdited);
    }

}
