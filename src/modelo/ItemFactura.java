package modelo;

public class ItemFactura extends Item{
    private int cantidad;
    private double precio;
    private double subtotal;

    public ItemFactura( String codigo, String nombre,int cantidad, double precio,double subtotal) {
        super(codigo, nombre);
        this.cantidad = cantidad;
        this.precio = precio;
        this.subtotal = subtotal;
   
    }

    public ItemFactura() {
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPreico(double precio) {
        this.precio = precio;
    }
    
    
}
