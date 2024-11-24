/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

public class Proveedor {
    private String nombre;
    private String nit;
    private String contacto;

    public Proveedor(String nombre, String nit, String contacto) {
        this.nombre = nombre;
        this.nit = nit;
        this.contacto = contacto;
    }

    public Proveedor() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }
    
    public String toString() {
        return nombre + "," + nit + "," + contacto;
    }
}
