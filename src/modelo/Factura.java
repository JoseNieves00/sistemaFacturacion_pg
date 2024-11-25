/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.util.ArrayList;
import java.util.Date;

public class Factura {

    private String numeroFactura;
    private Date fecha;
    private ArrayList<ItemFactura> items;
    private double subtotal;
    private double total;
    private double recibido;
    private double cambio;
    private String metodoPago;

    public Factura(String numeroFactura) {
        this.numeroFactura = numeroFactura;
        this.fecha = new Date();
        this.items = new ArrayList<>();
        this.subtotal = 0.0;
        this.total = 0.0;
        this.recibido = 0.0;
        this.cambio = 0.0;
        this.metodoPago = metodoPago;
    }

    public double getRecibido() {
        return recibido;
    }

    public void setRecibido(double recibido) {
        this.recibido = recibido;
    }

    public double getCambio() {
        return cambio;
    }

    public void setCambio(double cambio) {
        this.cambio = cambio;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    public Factura() {
    }

    public String getNumeroFactura() {
        return numeroFactura;
    }

    public void setNumeroFactura(String numeroFactura) {
        this.numeroFactura = numeroFactura;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public ArrayList<ItemFactura> getItems() {
        return items;
    }

    public void setItems(ArrayList<ItemFactura> items) {
        this.items = items;
    }

    public double getTotal() {
        return total+(total*0.19);
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public void agregarItem(ItemFactura item) {
        items.add(item);
        total += item.getSubtotal();
        subtotal += item.getSubtotal();
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

}
