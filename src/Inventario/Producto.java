/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Inventario;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;

/**
 *
 * @author hp
 */
public class Producto extends Item{
    private String Descripcion;
    private double precioCompra;
    private double precioVenta;
    private int cantidad;

    public Producto() {
    }

    public Producto( String codigo, String nombre, String Descripcion, double precioCompra, double precioVenta, int cantidad) {
        super(codigo, nombre);
        this.Descripcion = Descripcion;
        this.precioCompra = precioCompra;
        this.precioVenta = precioVenta;
        this.cantidad = cantidad;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String Descripcion) {
        this.Descripcion = Descripcion;
    }

    public double getPrecioCompra() {
        return precioCompra;
    }

    public void setPrecioCompra(double precioCompra) {
        this.precioCompra = precioCompra;
    }

    public double getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(double precioVenta) {
        this.precioVenta = precioVenta;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    
    
    void mostrar() {
        JTextArea salida = new JTextArea();
        salida.setText("Código\tNombre\tDescripcion\tPrecio Compra\tPrecio Venta\tCantidad\n");
        salida.append(getCodigo() + "\t" + getNombre() + "\t" + getDescripcion()+ "\t"+ precioCompra+ "\t"+ precioVenta +"\t" + + getCantidad() + "\n");
        JOptionPane.showMessageDialog(null, salida);
    }
    
}
