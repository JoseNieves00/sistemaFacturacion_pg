/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package vista;

import controlador.controladorFacturacion;
import controlador.controladorProductos;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import modelo.Factura;
import modelo.ItemFactura;
import modelo.Producto;
import modelo.Proveedor;

public class viewFacturacion extends javax.swing.JFrame {

    private controladorFacturacion controladorFacturacion;
    private controladorProductos controladorProductos;
    private DefaultTableModel modeloTabla;
    int filaSeleccionada;

    /**
     * Creates new form viewFacturacion
     */
    public viewFacturacion() throws IOException {
        initComponents();
        controladorFacturacion = new controladorFacturacion();
        controladorProductos = new controladorProductos();
        modeloTabla = (DefaultTableModel) tblFactura.getModel();
        tblFactura = new JTable(modeloTabla);
        limpiarTabla();
        frmFinalizar.setVisible(false);
    }

    private void limpiarCampos() {
        txtCodigo.setText("");
        txtNombre.setText("");
        txtCantidad.setText("");
        txtStock.setText("");
    }

    private void limpiarTabla() {
        DefaultTableModel modelo = (DefaultTableModel) tblFactura.getModel();
        int rowCount = modelo.getRowCount();

        // Recorrer la tabla en orden inverso para evitar problemas de índice.
        for (int i = rowCount - 1; i >= 0; i--) {
            modelo.removeRow(i);
        }
    }

    private void calcularTotal() {
        double total = 0.0;
        double subtotal = 0.0;

        for (int i = 0; i < modeloTabla.getRowCount(); i++) {
            Object subtotalObj = modeloTabla.getValueAt(i, 4); // Columna del subtotal

            // Verificar el tipo de dato y convertirlo correctamente
            if (subtotalObj instanceof Double) {
                subtotal = (Double) subtotalObj;
            } else if (subtotalObj instanceof String) {
                subtotal = Double.parseDouble((String) subtotalObj);
            }

            total += subtotal;
        }

        double iva = total + (total * 0.19);

        txtSubTotal.setText(String.format("$%.0f", subtotal)); // Actualizar el texto de la etiqueta
        txtTotal.setText(String.format("$%.0f", iva)); // Actualizar el texto de la etiqueta

    }

    private void cargarProductosEnTabla(int cantidad, double subtotal) {
        modeloTabla.setRowCount(0);
        for (Producto producto : controladorProductos.listarProductos()) {
            modeloTabla.addRow(new Object[]{
                producto.getCodigo(),
                producto.getNombre(),
                cantidad,
                producto.getPrecioVenta(),
                subtotal
            });
        }
        calcularTotal();
    }

    private void agregarProductosFactura() {
        // Obtener el código del producto y la cantidad desde los campos de texto
        String codigo = txtCodigo.getText().trim();
        int cantidad;

        // Validar si la cantidad ingresada es un número entero
        try {
            cantidad = Integer.parseInt(txtCantidad.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Ingrese una cantidad válida", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        limpiarCampos();

        // Buscar el producto por código
        Producto producto = controladorProductos.buscarProducto(codigo);
        if (producto == null) {
            JOptionPane.showMessageDialog(this, "Producto no encontrado!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Verificar si hay suficiente stock en el inventario
        if (producto.getCantidad() < cantidad) {
            JOptionPane.showMessageDialog(this, "Stock insuficiente", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Comprobar si el producto ya está en la factura
        boolean productoExistente = false;
        for (int i = 0; i < modeloTabla.getRowCount(); i++) {
            String codigoExistente = (String) modeloTabla.getValueAt(i, 0);
            if (codigoExistente.equalsIgnoreCase(codigo)) {
                // Si el producto ya existe, sumamos la nueva cantidad a la existente
                int cantidadActual = (int) modeloTabla.getValueAt(i, 2);
                int nuevaCantidad = cantidadActual + cantidad;
                double subtotal = nuevaCantidad * producto.getPrecioVenta();

                System.out.println(subtotal);

                // Actualizar la cantidad y el subtotal en la tabla
                modeloTabla.setValueAt(nuevaCantidad, i, 2); // Actualizar cantidad
                modeloTabla.setValueAt(subtotal, i, 4); // Actualizar subtotal

                cargarProductosEnTabla(nuevaCantidad, subtotal);

                productoExistente = true;
                break;
            }
        }

        // Si el producto no está en la factura, agregarlo como nueva fila
        if (!productoExistente) {
            double subtotal = cantidad * producto.getPrecioVenta();
            cargarProductosEnTabla(cantidad, subtotal);
        }

        // Reducir el stock temporalmente en el inventario (solo en memoria)
        try {
            // No se actualiza el archivo hasta que se facture
            controladorProductos.reducirStock(codigo, cantidad); // Reducimos temporalmente el stock
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al reducir stock: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

    }

    private void factura(double recibido, double cambio, String metodoPago) {
        System.out.println("facturacion");
        ArrayList<ItemFactura> items = new ArrayList<>();

        for (int i = 0; i < modeloTabla.getRowCount(); i++) {
            String codigo = (String) modeloTabla.getValueAt(i, 0);
            String nombre = (String) modeloTabla.getValueAt(i, 1);
            int cantidad = (int) modeloTabla.getValueAt(i, 2);
            double precio;
            precio = (double) modeloTabla.getValueAt(i, 3);
            Object subtotal = modeloTabla.getValueAt(i, 4);

            items.add(new ItemFactura(codigo, nombre, cantidad, precio, (double) subtotal));
        }

        try {
            Factura factura = controladorFacturacion.crearFactura(items, recibido, cambio, metodoPago);

            limpiarTabla();
            limpiarCampos();
            txtTotal.setText("");
            txtSubTotal.setText("");

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al generar Factura");
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

        frmFinalizar = new javax.swing.JFrame();
        jPanel3 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtCompra = new javax.swing.JTextField();
        txtRecibido = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        btnFacturar = new javax.swing.JButton();
        cmbPago = new javax.swing.JComboBox<>();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblFactura = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtCantidad = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtStock = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtSubTotal = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtTotal = new javax.swing.JTextField();
        btnAgregar = new javax.swing.JButton();
        btnFinalizar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 0));
        jLabel8.setText("Facturación");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 0, 0));
        jLabel9.setText("Total Compra");

        txtCompra.setEditable(false);
        txtCompra.setFocusable(false);
        txtCompra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCompraActionPerformed(evt);
            }
        });

        txtRecibido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtRecibidoActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 0, 0));
        jLabel10.setText("Total Recibido");

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 0, 0));
        jLabel11.setText("Metodo de Pago");

        btnFacturar.setBackground(new java.awt.Color(0, 0, 0));
        btnFacturar.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnFacturar.setForeground(new java.awt.Color(255, 255, 255));
        btnFacturar.setText("Facturar Venta");
        btnFacturar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFacturarActionPerformed(evt);
            }
        });

        cmbPago.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccione un metodo de pago", "Efectivo", "Tarjeta de Debito", "Tarjeta de Credito", "Otro", " " }));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addComponent(jLabel9)
                        .addGap(33, 33, 33)
                        .addComponent(txtCompra, javax.swing.GroupLayout.PREFERRED_SIZE, 345, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addComponent(jLabel10)
                        .addGap(27, 27, 27)
                        .addComponent(txtRecibido, javax.swing.GroupLayout.PREFERRED_SIZE, 345, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnFacturar, javax.swing.GroupLayout.PREFERRED_SIZE, 490, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cmbPago, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(211, 211, 211)
                        .addComponent(jLabel8)))
                .addContainerGap(31, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(jLabel8)
                .addGap(32, 32, 32)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCompra, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtRecibido, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbPago, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addComponent(btnFacturar, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(36, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout frmFinalizarLayout = new javax.swing.GroupLayout(frmFinalizar.getContentPane());
        frmFinalizar.getContentPane().setLayout(frmFinalizarLayout);
        frmFinalizarLayout.setHorizontalGroup(
            frmFinalizarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, frmFinalizarLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
        frmFinalizarLayout.setVerticalGroup(
            frmFinalizarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setSize(new java.awt.Dimension(1398, 834));

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));
        jPanel1.setPreferredSize(new java.awt.Dimension(1398, 834));

        tblFactura.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Codigo", "Nombre", "Cantidad", "Precio", "Subtotal"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblFactura.setPreferredSize(new java.awt.Dimension(500, 80));
        tblFactura.setSelectionBackground(new java.awt.Color(0, 153, 153));
        tblFactura.setSelectionForeground(new java.awt.Color(51, 51, 0));
        tblFactura.setShowGrid(false);
        tblFactura.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblFacturaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblFactura);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Facturación - Sistema de Facturación e Inventario");

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("Codigo");

        txtCodigo.setBackground(new java.awt.Color(204, 204, 204));
        txtCodigo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodigoFocusLost(evt);
            }
        });
        txtCodigo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCodigoKeyReleased(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Nombre");

        txtNombre.setEditable(false);
        txtNombre.setBackground(new java.awt.Color(204, 204, 204));
        txtNombre.setEnabled(false);
        txtNombre.setFocusable(false);
        txtNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("Cantidad");

        txtCantidad.setBackground(new java.awt.Color(204, 204, 204));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("Stock Disponible");

        txtStock.setEditable(false);
        txtStock.setBackground(new java.awt.Color(204, 204, 204));
        txtStock.setEnabled(false);
        txtStock.setFocusable(false);

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("Subtotal");

        txtSubTotal.setEditable(false);
        txtSubTotal.setBackground(new java.awt.Color(204, 204, 204));
        txtSubTotal.setEnabled(false);
        txtSubTotal.setFocusable(false);
        txtSubTotal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSubTotalActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setText("Total + IVA");

        txtTotal.setEditable(false);
        txtTotal.setBackground(new java.awt.Color(204, 204, 204));
        txtTotal.setEnabled(false);
        txtTotal.setFocusable(false);
        txtTotal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTotalActionPerformed(evt);
            }
        });

        btnAgregar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnAgregar.setText("Agregar Producto +");
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });

        btnFinalizar.setBackground(new java.awt.Color(0, 0, 0));
        btnFinalizar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnFinalizar.setForeground(new java.awt.Color(255, 255, 255));
        btnFinalizar.setText("Finalizar Venta");
        btnFinalizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFinalizarActionPerformed(evt);
            }
        });

        btnEliminar.setText("Eliminar");
        btnEliminar.setEnabled(false);
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        btnModificar.setText("Modificar");
        btnModificar.setEnabled(false);
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(50, 50, 50)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtCantidad, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(64, 64, 64)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtStock, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(58, 58, 58)
                        .addComponent(btnAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnEliminar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnModificar, javax.swing.GroupLayout.DEFAULT_SIZE, 96, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 142, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtSubTotal, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(45, 45, 45)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(btnFinalizar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(32, 32, 32))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(16, 16, 16)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel3)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel7))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtSubTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5))
                        .addGap(0, 2, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtStock, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(15, 15, 15))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnFinalizar, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1355, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 1347, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(18, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(jLabel1)
                .addGap(15, 15, 15)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 569, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(37, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreActionPerformed

    private void txtSubTotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSubTotalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSubTotalActionPerformed

    private void txtTotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTotalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTotalActionPerformed

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        // TODO add your handling code here:
        agregarProductosFactura();
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void btnFinalizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFinalizarActionPerformed
        // TODO add your handling code here:
        frmFinalizar.setVisible(true);
        frmFinalizar.setSize(559, 440);

        String valoTotal = txtTotal.getText();

        txtCompra.setText(String.valueOf(valoTotal));
    }//GEN-LAST:event_btnFinalizarActionPerformed

    private void txtCodigoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoKeyReleased
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            String codigo = txtCodigo.getText().trim();
            Producto producto = controladorProductos.buscarProducto(codigo);

            if (producto != null) {
                txtNombre.setText(producto.getNombre());
                txtStock.setText(String.valueOf(producto.getCantidad()));
            } else {
                txtNombre.setText("");
                txtStock.setText("");
            }
        }
    }//GEN-LAST:event_txtCodigoKeyReleased

    private void txtCodigoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodigoFocusLost
        // TODO add your handling code here:
        String codigo = txtCodigo.getText().trim();
        Producto producto = controladorProductos.buscarProducto(codigo);

        if (producto != null) {
            txtNombre.setText(producto.getNombre());
            txtStock.setText(String.valueOf(producto.getCantidad()));
        } else {
            txtNombre.setText("");
            txtStock.setText("");
        }
    }//GEN-LAST:event_txtCodigoFocusLost

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        // TODO add your handling code here:

        try {
            String codigo = txtCodigo.getText().trim();
            int nuevaCantidad = Integer.parseInt(txtCantidad.getText().trim());

            Producto producto = controladorProductos.buscarProducto(codigo);

            if (producto == null) {
                JOptionPane.showMessageDialog(this, "Producto no encontrado en inventario.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int cantidadAnterior = (int) modeloTabla.getValueAt(filaSeleccionada, 2);

            if (producto.getCantidad() + cantidadAnterior < nuevaCantidad) {
                JOptionPane.showMessageDialog(this, "Stock insuficiente para la nueva cantidad.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            producto.setCantidad(producto.getCantidad() + cantidadAnterior - nuevaCantidad);

            double precio = producto.getPrecioVenta();
            double nuevoSubtotal = nuevaCantidad * precio;

            modeloTabla.setValueAt(nuevaCantidad, filaSeleccionada, 2);
            modeloTabla.setValueAt(nuevoSubtotal, filaSeleccionada, 4);
            calcularTotal();

            tblFactura.clearSelection();
            limpiarCampos();
            btnModificar.setEnabled(false);
            btnEliminar.setEnabled(false);
            btnAgregar.setEnabled(true);
            txtCodigo.setEnabled(true);
            txtCodigo.setEditable(true);
            JOptionPane.showMessageDialog(this, "Producto modificado exitosamente.");

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Ingrese una cantidad válida.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnModificarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        // TODO add your handling code here:
        int confirmacion = JOptionPane.showConfirmDialog(null, "¿Esta Seguro de que desea eliminar este producto?", "confirmar eliminacion", JOptionPane.YES_NO_OPTION);

        if (confirmacion == JOptionPane.YES_OPTION) {

            String codigoProducto = (String) modeloTabla.getValueAt(filaSeleccionada, 0);

            Producto producto = controladorProductos.buscarProducto(codigoProducto);

            if (producto != null) {
                int cantidadEliminada = (int) modeloTabla.getValueAt(filaSeleccionada, 2);
                producto.setCantidad(producto.getCantidad() + cantidadEliminada);

                try {
                    controladorProductos.guardarProductos();
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(null, "Error al actualizar producto");
                }
            }

            modeloTabla.removeRow(filaSeleccionada);

            calcularTotal();

            limpiarCampos();
            btnModificar.setEnabled(false);
            btnEliminar.setEnabled(false);
            btnAgregar.setEnabled(true);
            txtCodigo.setEnabled(true);
            txtCodigo.setEditable(true);
            JOptionPane.showMessageDialog(null, "Producto eliminado exitosamente");
        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void tblFacturaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblFacturaMouseClicked
        // TODO add your handling code here:
        btnModificar.setEnabled(true);
        btnEliminar.setEnabled(true);
        btnAgregar.setEnabled(false);
        txtCodigo.setEnabled(false);
        txtCodigo.setEditable(false);
        filaSeleccionada = tblFactura.rowAtPoint(evt.getPoint());

        if (filaSeleccionada >= 0) {
            txtCodigo.setText((String) modeloTabla.getValueAt(filaSeleccionada, 0));
            txtNombre.setText((String) modeloTabla.getValueAt(filaSeleccionada, 1));
            txtCantidad.setText(String.valueOf(modeloTabla.getValueAt(filaSeleccionada, 2)));

            Producto producto = controladorProductos.buscarProducto(txtCodigo.getText());
            txtStock.setText(String.valueOf(producto.getCantidad()));
        }
    }//GEN-LAST:event_tblFacturaMouseClicked

    private void txtCompraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCompraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCompraActionPerformed

    private void txtRecibidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtRecibidoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtRecibidoActionPerformed

    private void btnFacturarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFacturarActionPerformed
        // TODO add your handling code here:

        try {
            double valorRecibido = Double.parseDouble(txtRecibido.getText());

            String totalString = txtCompra.getText().replace("$", "").trim();

            // Ahora podemos convertirlo en un número
            double total = Double.parseDouble(totalString);

            double cambio = valorRecibido - total;

            int indexMetodoPago = cmbPago.getSelectedIndex();

            String metodoPago = cmbPago.getItemAt(indexMetodoPago);

            System.out.println(metodoPago);

            if (indexMetodoPago != 0) {
                factura(valorRecibido, cambio, metodoPago);
            } else {
                JOptionPane.showMessageDialog(this, "Selecciona un metodo de pago.", "Error", JOptionPane.ERROR_MESSAGE);

            }

            frmFinalizar.setVisible(false);
            txtCompra.setText("");
            txtRecibido.setText("");
            cmbPago.setSelectedIndex(0);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Ingrese valores validos.", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_btnFacturarActionPerformed

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
            java.util.logging.Logger.getLogger(viewFacturacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(viewFacturacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(viewFacturacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(viewFacturacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new viewFacturacion().setVisible(true);
                } catch (IOException ex) {
                    Logger.getLogger(viewFacturacion.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnFacturar;
    private javax.swing.JButton btnFinalizar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JComboBox<String> cmbPago;
    private javax.swing.JFrame frmFinalizar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblFactura;
    private javax.swing.JTextField txtCantidad;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtCompra;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtRecibido;
    private javax.swing.JTextField txtStock;
    private javax.swing.JTextField txtSubTotal;
    private javax.swing.JTextField txtTotal;
    // End of variables declaration//GEN-END:variables
}
