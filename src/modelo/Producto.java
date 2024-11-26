package modelo;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;

public class Producto extends Item{
    private double precioCompra;
    private double precioVenta;
    private int cantidad;
    private String proveedor;

    public Producto() {
    }

    public Producto( String codigo, String nombre, double precioCompra, double precioVenta, int cantidad,String proveedor) {
        super(codigo, nombre);
        this.precioCompra = precioCompra;
        this.precioVenta = precioVenta;
        this.cantidad = cantidad;
        this.proveedor = proveedor;
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
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
        return getCodigo() + "," + getNombre() + "," + precioCompra + "," + precioVenta + "," + getCantidad() + "," + getProveedor();
    }
    
    
}

