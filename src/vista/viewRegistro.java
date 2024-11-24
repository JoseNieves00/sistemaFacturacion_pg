/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package vista;

import controlador.controladorProductos;
import modelo.Producto;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author hp
 */
public class viewRegistro extends javax.swing.JFrame {

    private controladorProductos controlador;
    ArrayList<Producto> lista;
    DefaultTableModel modelo;
    int fila;

    public viewRegistro() throws IOException {
        initComponents();
        controlador = new controladorProductos();
        modelo = new DefaultTableModel();
        modelo =  (DefaultTableModel) tblRegistro.getModel();
        txtID.grabFocus();
        setLocationRelativeTo(null);
        AgregarTabla();
    }
   
    private void AgregarTabla() {
        limpiarTabla();
        modelo.setRowCount(0); // Limpiar la tabla
        ArrayList<Producto> productos = controlador.listarProductos();
        if (productos != null && !productos.isEmpty()) {
            for (Producto producto : productos) {
                modelo.addRow(new Object[]{producto.getCodigo(), producto.getNombre(), producto.getPrecioCompra(),producto.getPrecioVenta(),producto.getCantidad()});
            }
        } else {
            // Si la lista está vacía, puedes mostrar un mensaje
            JOptionPane.showMessageDialog(this, "No se encontraron productos.");
        }

        tblRegistro.setModel(modelo);
    }



    void limpiarCampos() {
        txtID.setText("");
        txtNombre.setText("");
        txtDescripcion.setText("");
        txtCompra.setText("");
        txtVenta.setText("");
        txtCantidad.setText("");
        tbnAgregar.setEnabled(true);
    }

    void limpiarTabla() {
        DefaultTableModel modelo = (DefaultTableModel) tblRegistro.getModel();
        int rowCount = modelo.getRowCount();

        // Recorrer la tabla en orden inverso para evitar problemas de índice.
        for (int i = rowCount - 1; i >= 0; i--) {
            modelo.removeRow(i);
        }
    }

    private void validarCampos() throws IOException, Exception {
        try {
            double precioCompra = Double.parseDouble(txtCompra.getText());
            double precioVenta = Double.parseDouble(txtVenta.getText());
            int cantidad = Integer.parseInt(txtCantidad.getText());

            if (precioCompra <= 0 || precioVenta <= 0) {
                throw new Exception("Los precios deben ser mayores a 0.");
            }
            if (cantidad <= 0) {
                throw new Exception("La cantidad debe ser mayor a 0.");
            }
        } catch (NumberFormatException e) {
            throw new Exception("Por favor, ingrese valores numéricos válidos para precios y cantidad.");
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        ID = new javax.swing.JLabel();
        NOMBRE = new javax.swing.JLabel();
        DESCRIPCION = new javax.swing.JLabel();
        COMPRA = new javax.swing.JLabel();
        VENTA = new javax.swing.JLabel();
        CANTIDAD = new javax.swing.JLabel();
        txtID = new javax.swing.JTextField();
        txtNombre = new javax.swing.JTextField();
        txtDescripcion = new javax.swing.JTextField();
        txtCompra = new javax.swing.JTextField();
        txtVenta = new javax.swing.JTextField();
        txtCantidad = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblRegistro = new javax.swing.JTable();
        tbnAgregar = new javax.swing.JButton();
        tbnModificar = new javax.swing.JButton();
        tbnEliminar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        jLabel1.setText("Registro Productos");

        ID.setText("ID");

        NOMBRE.setText("NOMBRE");

        DESCRIPCION.setText("DESCRIPCION");

        COMPRA.setText("PRECIO COMPRA");

        VENTA.setText("PRECIO VENTA");

        CANTIDAD.setText("CANTIDAD");

        txtID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIDActionPerformed(evt);
            }
        });

        txtNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreActionPerformed(evt);
            }
        });

        txtDescripcion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDescripcionActionPerformed(evt);
            }
        });

        txtCompra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCompraActionPerformed(evt);
            }
        });

        txtVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtVentaActionPerformed(evt);
            }
        });

        txtCantidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCantidadActionPerformed(evt);
            }
        });

        tblRegistro.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "NOMBRE", "PRECIO COMPRA", "PRECIO VENTA", "CANTIDAD"
            }
        ));
        tblRegistro.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblRegistroMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblRegistro);

        tbnAgregar.setText("AGREGAR");
        tbnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tbnAgregarActionPerformed(evt);
            }
        });

        tbnModificar.setText("MODIFICAR");
        tbnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tbnModificarActionPerformed(evt);
            }
        });

        tbnEliminar.setText("ELIMINAR");
        tbnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tbnEliminarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1072, Short.MAX_VALUE)
                .addGap(58, 58, 58))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ID)
                    .addComponent(NOMBRE)
                    .addComponent(DESCRIPCION)
                    .addComponent(COMPRA)
                    .addComponent(VENTA)
                    .addComponent(CANTIDAD))
                .addGap(33, 33, 33)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtDescripcion, javax.swing.GroupLayout.DEFAULT_SIZE, 213, Short.MAX_VALUE)
                    .addComponent(txtID)
                    .addComponent(txtNombre)
                    .addComponent(txtCompra)
                    .addComponent(txtVenta)
                    .addComponent(txtCantidad))
                .addGap(49, 49, 49)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(tbnModificar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(tbnAgregar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(tbnEliminar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(50, 50, 50)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 610, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(ID)
                            .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tbnAgregar))
                        .addGap(21, 21, 21)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(NOMBRE)
                            .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tbnModificar))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(74, 74, 74)
                                .addComponent(DESCRIPCION)
                                .addGap(94, 94, 94))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(tbnEliminar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(30, 30, 30)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(COMPRA)
                            .addComponent(txtCompra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(40, 40, 40)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(VENTA)
                            .addComponent(txtVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(33, 33, 33)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(CANTIDAD)
                            .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 402, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(90, 90, 90))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIDActionPerformed

    private void tbnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tbnAgregarActionPerformed
        // TODO add your handling code here:
        try {
            validarCampos();
            String codigo = txtID.getText();
            if (controlador.verificarCodigo(codigo)) {
                JOptionPane.showMessageDialog(this, "El Producto ya existe!");
            }

            Producto p = new Producto(codigo,
                    txtNombre.getText(),
                    Double.parseDouble(txtCompra.getText()),
                    Double.parseDouble(txtVenta.getText()),
                    Integer.parseInt(txtCantidad.getText())
            );
            controlador.agregarProducto(p);
            AgregarTabla();
            limpiarCampos();
            JOptionPane.showMessageDialog(this, "Producto agregado con éxito");

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese valores numéricos válidos para precios y cantidad", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            Logger.getLogger(viewRegistro.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_tbnAgregarActionPerformed

    private void tbnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tbnModificarActionPerformed
        // TODO add your handling code here:
        /*try {
            if (fila == -1) {
                throw new Exception("Por favor, seleccione un producto para modificar.");
            }

            validarCampos();

            String nuevoCodigo = txtID.getText().trim();
            Producto productoExist = lista.get(fila);

            // Verificar si el nuevo código ya existe en otro producto
            if (!nuevoCodigo.equals(productoExist.getCodigo()) && verificarCodigo(nuevoCodigo)) {
                throw new Exception("El nuevo código ya existe en la lista.");
            }

            // Actualizar el producto existente
            productoExist.setCodigo(nuevoCodigo);
            productoExist.setNombre(txtNombre.getText().trim());
            productoExist.setDescripcion(txtDescripcion.getText().trim());
            productoExist.setPrecioCompra(Double.parseDouble(txtCompra.getText().trim()));
            productoExist.setPrecioVenta(Double.parseDouble(txtVenta.getText().trim()));
            productoExist.setCantidad(Integer.parseInt(txtCantidad.getText().trim()));

            modelo.setRowCount(0); // Limpiar la tabla
            agregarTabla();       // Cargar los datos actualizados
            limpiarCampos();      // Limpiar los campos de texto
            guardarDatosEnArchivo(); // Save data after modifying a product
            tbnAgregar.setEnabled(true); // Habilitar el botón de agregar

            JOptionPane.showMessageDialog(this, "Producto modificado con éxito", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese valores numéricos válidos para precios y cantidad", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }*/
        String ModiId, ModiNom, ModiDes;
        ModiId = txtID.getText();
        ModiNom = txtNombre.getText();
        ModiDes = txtDescripcion.getText();
        double ModiComp, ModiVent;
        ModiComp = Double.parseDouble(txtCompra.getText());
        ModiVent = Double.parseDouble(txtVenta.getText());
        int ModiCan;
        ModiCan = Integer.parseInt(txtCantidad.getText());

        lista.get(fila).setCodigo(ModiId);
        lista.get(fila).setNombre(ModiNom);
        lista.get(fila).setPrecioCompra(ModiComp);
        lista.get(fila).setPrecioVenta(ModiVent);
        lista.get(fila).setCantidad(ModiCan);

        modelo.setRowCount(0);
        for (int i = 0; i < lista.size(); i++) {
            Object[] objs = {lista.get(i).getCodigo(), lista.get(i).getNombre(),
                lista.get(i).getPrecioCompra(), lista.get(i).getPrecioVenta(), lista.get(i).getCantidad()};

            modelo.addRow(objs);

        }
        limpiarCampos();
        tbnAgregar.setEnabled(true);


    }//GEN-LAST:event_tbnModificarActionPerformed

    private void tbnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tbnEliminarActionPerformed
        // TODO add your handling code here:
        try {
            String codigo = txtID.getText();

            int confirmacion = JOptionPane.showConfirmDialog(this,
                    "¿Está seguro de que desea eliminar este producto?",
                    "Confirmación", JOptionPane.YES_NO_OPTION);

            if (confirmacion == JOptionPane.YES_OPTION) {
                lista.remove(fila);
                AgregarTabla();
                limpiarCampos();
                controlador.eliminarProducto(codigo); // Save data after deleting a product
                JOptionPane.showMessageDialog(this, "Producto eliminado con éxito");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_tbnEliminarActionPerformed

    private void txtNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreActionPerformed

    private void txtDescripcionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDescripcionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDescripcionActionPerformed

    private void txtCompraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCompraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCompraActionPerformed

    private void txtVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtVentaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtVentaActionPerformed

    private void txtCantidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCantidadActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCantidadActionPerformed

    private void tblRegistroMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblRegistroMouseClicked
        // TODO add your handling code here:
        fila = tblRegistro.rowAtPoint(evt.getPoint());
        Producto p = lista.get(fila);
        // Llenar los campos de texto con la información del producto
        txtID.setText(String.valueOf(p.getCodigo()));
        txtNombre.setText(p.getNombre());
        txtCompra.setText(String.valueOf(p.getPrecioCompra()));
        txtVenta.setText(String.valueOf(p.getPrecioVenta()));
        txtCantidad.setText(String.valueOf(p.getCantidad()));

        tbnAgregar.setEnabled(false);

    }//GEN-LAST:event_tblRegistroMouseClicked

        // TODO add your handling code here:
        /**
         * @param args the command line arguments
         */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(viewRegistro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(viewRegistro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(viewRegistro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(viewRegistro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new viewRegistro().setVisible(true);
                } catch (IOException ex) {
                    Logger.getLogger(viewRegistro.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel CANTIDAD;
    private javax.swing.JLabel COMPRA;
    private javax.swing.JLabel DESCRIPCION;
    private javax.swing.JLabel ID;
    private javax.swing.JLabel NOMBRE;
    private javax.swing.JLabel VENTA;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable tblRegistro;
    private javax.swing.JButton tbnAgregar;
    private javax.swing.JButton tbnEliminar;
    private javax.swing.JButton tbnModificar;
    private javax.swing.JTextField txtCantidad;
    private javax.swing.JTextField txtCompra;
    private javax.swing.JTextField txtDescripcion;
    private javax.swing.JTextField txtID;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtVenta;
    // End of variables declaration//GEN-END:variables
}
