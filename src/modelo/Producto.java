/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;

public class Producto extends Item{
    private double precioCompra;
    private double precioVenta;
    private int cantidad;

    public Producto() {
    }

    public Producto( String codigo, String nombre, double precioCompra, double precioVenta, int cantidad) {
        super(codigo, nombre);
        this.precioCompra = precioCompra;
        this.precioVenta = precioVenta;
        this.cantidad = cantidad;
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
        salida.setText("Código\tNombre\tPrecio Compra\tPrecio Venta\tCantidad\n");
        salida.append(getCodigo() + "\t" + getNombre() + "\t"+ precioCompra+ "\t"+ precioVenta +"\t" + + getCantidad() + "\n");
        JOptionPane.showMessageDialog(null, salida);
    }

    public void setCodigo(int nuevoCodigo) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public String toString() {
        return getCodigo() + "," + getNombre() + "," + precioCompra + "," + precioVenta + "," + getCantidad();
    }
    
    
}

