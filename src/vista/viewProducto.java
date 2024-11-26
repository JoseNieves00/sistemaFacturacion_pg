package vista;

import controlador.controladorProductos;
import controlador.controladorProveedor;
import java.awt.BorderLayout;
import modelo.Producto;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author hp
 */
public class viewProducto extends javax.swing.JFrame {

    private controladorProductos controlador;
    private controladorProveedor controladorProveedor;
    private DefaultTableModel modelo;

    private int fila;

    public viewProducto() throws IOException {
        initComponents();
        try {
            controlador = new controladorProductos();
            controladorProveedor = new controladorProveedor();

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar los proveedores: " + e.getMessage());
            return;
        }

        modelo = (DefaultTableModel) tblProductos.getModel();
        tblProductos = new JTable(modelo);
        actualizarTabla();
        add(new JScrollPane(tblProductos), BorderLayout.CENTER);
        btnModificar.setEnabled(false);
        btnEliminar.setEnabled(false);
        cargarProveedores();
        this.setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                // Abrir el menú principal antes de cerrar
                viewMenu2 m;
                try {
                    m = new viewMenu2();
                    m.setVisible(true);
                } catch (IOException ex) {
                    Logger.getLogger(viewLogin.class.getName()).log(Level.SEVERE, null, ex);
                }
                dispose(); // Cerrar esta ventana
            }
        });
    }

    private void cargarProveedores() {
        try {
            ArrayList<String> proveedores = controladorProveedor.obtenerProveedores();
            COMBOBOXP.removeAllItems(); // Limpiar elementos previos
            COMBOBOXPM.removeAllItems();
            for (String proveedor : proveedores) {
                System.out.println(proveedor);
                COMBOBOXP.addItem(proveedor);
                COMBOBOXPM.addItem(proveedor);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar proveedores: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizarTabla() {
        limpiarTabla();
        ArrayList<Producto> productos = controlador.listarProductos();
        if (productos != null && !productos.isEmpty()) {
            for (Producto producto : productos) {
                modelo.addRow(new Object[]{producto.getCodigo(), producto.getNombre(), producto.getPrecioCompra(), producto.getPrecioVenta(), producto.getCantidad(),producto.getProveedor()
                });
            }
        }
        tblProductos.setModel(modelo);
    }

    private void limpiarCampos() {
        txtID.setText("");
        txtNombre.setText("");
        txtCompra.setText("");
        txtCantidad.setText("");
        txtVenta.setText("");
        btnAgregar.setEnabled(true);
    }

    private void limpiarTabla() {
        for (int i = 0; i < modelo.getRowCount(); i++) {
            modelo.removeRow(i);
            i = i - 1;
        }
    }

    private void validarCampos() throws Exception {
        int indexProveedor = COMBOBOXP.getSelectedIndex();
        
        if (txtID.getText().trim().isEmpty() || txtNombre.getText().trim().isEmpty() || txtCompra.getText().trim().isEmpty() || txtCantidad.getText().trim().isEmpty() || txtVenta.getText().trim().isEmpty() || COMBOBOXP.getItemAt(indexProveedor).trim().isEmpty()) {
            throw new Exception("Todos los campos deben estar llenos.");
        }

        try {
            double precioCompra = Double.parseDouble(txtCompra.getText());
            double precioVenta = Double.parseDouble(txtCantidad.getText());
            int cantidad = Integer.parseInt(txtVenta.getText());

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

    private void validarCamposModificar() throws Exception {
        int indexProveedorM = COMBOBOXPM.getSelectedIndex();
        
        if (txtIDM.getText().trim().isEmpty() || txtNombreM.getText().trim().isEmpty() || txtCompraM.getText().trim().isEmpty() || txtVentaM.getText().trim().isEmpty() || txtCantidadM.getText().trim().isEmpty() || COMBOBOXPM.getItemAt(indexProveedorM).trim().isEmpty()) {
            throw new Exception("Todos los campos deben estar llenos.");
        }

        try {
            double precioCompra = Double.parseDouble(txtCompraM.getText());
            double precioVenta = Double.parseDouble(txtVentaM.getText());
            int cantidad = Integer.parseInt(txtCantidadM.getText());

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
     * Creates new form viewProducto
     */
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        frmCrearPP = new javax.swing.JFrame();
        jPanel5 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        btnAgregarP = new javax.swing.JButton();
        ID = new javax.swing.JLabel();
        txtID = new javax.swing.JTextField();
        NOMBRE = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        COMPRA = new javax.swing.JLabel();
        txtCompra = new javax.swing.JTextField();
        VENTA = new javax.swing.JLabel();
        txtCantidad = new javax.swing.JTextField();
        CANTIDAD = new javax.swing.JLabel();
        txtVenta = new javax.swing.JTextField();
        COMBOBOXP = new javax.swing.JComboBox<>();
        PROVEEDOR = new javax.swing.JLabel();
        frmModificarPP = new javax.swing.JFrame();
        jPanel6 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        btnModificarP = new javax.swing.JButton();
        ID1 = new javax.swing.JLabel();
        txtIDM = new javax.swing.JTextField();
        NOMBRE1 = new javax.swing.JLabel();
        txtNombreM = new javax.swing.JTextField();
        COMPRA1 = new javax.swing.JLabel();
        txtCompraM = new javax.swing.JTextField();
        VENTA1 = new javax.swing.JLabel();
        txtVentaM = new javax.swing.JTextField();
        CANTIDAD1 = new javax.swing.JLabel();
        txtCantidadM = new javax.swing.JTextField();
        COMBOBOXPM = new javax.swing.JComboBox<>();
        PROVEEDOR1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblProductos = new javax.swing.JTable();
        btnModificar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnAgregar = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        frmCrearPP.setTitle("Crear Producto");
        frmCrearPP.setSize(new java.awt.Dimension(586, 430));

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));

        jLabel5.setFont(new java.awt.Font("Book Antiqua", 1, 24)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("CREAR PRODUCTO");

        btnAgregarP.setBackground(new java.awt.Color(170, 190, 190));
        btnAgregarP.setFont(new java.awt.Font("Book Antiqua", 0, 18)); // NOI18N
        btnAgregarP.setText("AGREGAR");
        btnAgregarP.setAutoscrolls(true);
        btnAgregarP.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.black, java.awt.Color.white, java.awt.Color.black, java.awt.Color.darkGray));
        btnAgregarP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarPActionPerformed(evt);
            }
        });

        ID.setFont(new java.awt.Font("Book Antiqua", 0, 18)); // NOI18N
        ID.setText("ID");

        txtID.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.black, null));
        txtID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIDActionPerformed(evt);
            }
        });

        NOMBRE.setFont(new java.awt.Font("Book Antiqua", 0, 18)); // NOI18N
        NOMBRE.setText("NOMBRE");

        txtNombre.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.black, null));
        txtNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreActionPerformed(evt);
            }
        });

        COMPRA.setFont(new java.awt.Font("Book Antiqua", 0, 18)); // NOI18N
        COMPRA.setText("PRECIO COMPRA");

        txtCompra.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.black, null));
        txtCompra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCompraActionPerformed(evt);
            }
        });

        VENTA.setFont(new java.awt.Font("Book Antiqua", 0, 18)); // NOI18N
        VENTA.setText("PRECIO VENTA");

        txtCantidad.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.black, null));
        txtCantidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCantidadActionPerformed(evt);
            }
        });

        CANTIDAD.setFont(new java.awt.Font("Book Antiqua", 0, 18)); // NOI18N
        CANTIDAD.setText("CANTIDAD");

        txtVenta.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.black, null));
        txtVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtVentaActionPerformed(evt);
            }
        });

        COMBOBOXP.setFont(new java.awt.Font("Book Antiqua", 0, 14)); // NOI18N
        COMBOBOXP.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.gray, java.awt.Color.lightGray, null, java.awt.Color.darkGray));
        COMBOBOXP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                COMBOBOXPActionPerformed(evt);
            }
        });

        PROVEEDOR.setFont(new java.awt.Font("Book Antiqua", 0, 18)); // NOI18N
        PROVEEDOR.setText("PROVEEDOR");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ID, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(NOMBRE))
                        .addGap(91, 91, 91)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtNombre, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
                            .addComponent(txtID)))
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 510, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(COMPRA)
                        .addGap(15, 15, 15)
                        .addComponent(txtCompra, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(VENTA)
                            .addComponent(CANTIDAD)
                            .addComponent(PROVEEDOR))
                        .addGap(35, 35, 35)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtVenta, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
                            .addComponent(txtCantidad)
                            .addComponent(COMBOBOXP, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnAgregarP, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(172, 172, 172))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(jLabel5)
                .addGap(28, 28, 28)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ID)
                    .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(NOMBRE)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(COMPRA, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCompra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(VENTA)
                    .addComponent(txtVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CANTIDAD)
                    .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(COMBOBOXP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(PROVEEDOR))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                .addComponent(btnAgregarP, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );

        javax.swing.GroupLayout frmCrearPPLayout = new javax.swing.GroupLayout(frmCrearPP.getContentPane());
        frmCrearPP.getContentPane().setLayout(frmCrearPPLayout);
        frmCrearPPLayout.setHorizontalGroup(
            frmCrearPPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(frmCrearPPLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        frmCrearPPLayout.setVerticalGroup(
            frmCrearPPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(frmCrearPPLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        frmModificarPP.setSize(new java.awt.Dimension(568, 424));

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));

        jLabel9.setFont(new java.awt.Font("Book Antiqua", 1, 24)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("MODIFICAR PRODUCTO");

        btnModificarP.setBackground(new java.awt.Color(150, 170, 190));
        btnModificarP.setFont(new java.awt.Font("Book Antiqua", 0, 18)); // NOI18N
        btnModificarP.setText("MODIFICAR");
        btnModificarP.setToolTipText("");
        btnModificarP.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, java.awt.Color.black, java.awt.Color.darkGray, java.awt.Color.black));
        btnModificarP.setCursor(new java.awt.Cursor(java.awt.Cursor.MOVE_CURSOR));
        btnModificarP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarPActionPerformed(evt);
            }
        });

        ID1.setFont(new java.awt.Font("Book Antiqua", 0, 18)); // NOI18N
        ID1.setText("ID");

        txtIDM.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.black, null));
        txtIDM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIDMActionPerformed(evt);
            }
        });

        NOMBRE1.setFont(new java.awt.Font("Book Antiqua", 0, 18)); // NOI18N
        NOMBRE1.setText("NOMBRE");

        txtNombreM.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.black, null));
        txtNombreM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreMActionPerformed(evt);
            }
        });

        COMPRA1.setFont(new java.awt.Font("Book Antiqua", 0, 18)); // NOI18N
        COMPRA1.setText("PRECIO COMPRA");

        txtCompraM.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.black, null));
        txtCompraM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCompraMActionPerformed(evt);
            }
        });

        VENTA1.setFont(new java.awt.Font("Book Antiqua", 0, 18)); // NOI18N
        VENTA1.setText("PRECIO VENTA");

        txtVentaM.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.black, null));
        txtVentaM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtVentaMActionPerformed(evt);
            }
        });

        CANTIDAD1.setFont(new java.awt.Font("Book Antiqua", 0, 18)); // NOI18N
        CANTIDAD1.setText("CANTIDAD");

        txtCantidadM.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.black, null));
        txtCantidadM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCantidadMActionPerformed(evt);
            }
        });

        COMBOBOXPM.setFont(new java.awt.Font("Book Antiqua", 0, 14)); // NOI18N
        COMBOBOXPM.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.gray, java.awt.Color.lightGray, null, java.awt.Color.darkGray));
        COMBOBOXPM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                COMBOBOXPMActionPerformed(evt);
            }
        });

        PROVEEDOR1.setFont(new java.awt.Font("Book Antiqua", 0, 18)); // NOI18N
        PROVEEDOR1.setText("PROVEEDOR");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(51, 51, 51)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(ID1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(NOMBRE1))
                                .addGap(91, 91, 91)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtNombreM, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
                                    .addComponent(txtIDM)))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(COMPRA1)
                                    .addComponent(CANTIDAD1)
                                    .addComponent(VENTA1)
                                    .addComponent(PROVEEDOR1))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtCompraM, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
                                    .addComponent(txtCantidadM)
                                    .addComponent(txtVentaM)
                                    .addComponent(COMBOBOXPM, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 510, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(21, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btnModificarP, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(167, 167, 167))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(jLabel9)
                .addGap(29, 29, 29)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ID1)
                    .addComponent(txtIDM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(NOMBRE1)
                    .addComponent(txtNombreM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(COMPRA1, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCompraM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(VENTA1)
                    .addComponent(txtVentaM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CANTIDAD1)
                    .addComponent(txtCantidadM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(COMBOBOXPM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(PROVEEDOR1))
                .addGap(18, 18, 18)
                .addComponent(btnModificarP, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(23, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout frmModificarPPLayout = new javax.swing.GroupLayout(frmModificarPP.getContentPane());
        frmModificarPP.getContentPane().setLayout(frmModificarPPLayout);
        frmModificarPPLayout.setHorizontalGroup(
            frmModificarPPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(frmModificarPPLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        frmModificarPPLayout.setVerticalGroup(
            frmModificarPPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(frmModificarPPLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Productos");

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));

        jLabel1.setFont(new java.awt.Font("Book Antiqua", 1, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("PRODUCTOS - SISTEMA DE FACTURACION  E INVENTARIO");
        jLabel1.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.gray, null));

        tblProductos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "NOMBRE", "PRECIO COMPRA", "PRECIO VENTA", "CANTIDAD", "PROVEEDOR"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblProductos.setEditingColumn(0);
        tblProductos.setEditingRow(0);
        tblProductos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblProductosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblProductos);

        btnModificar.setFont(new java.awt.Font("Book Antiqua", 1, 18)); // NOI18N
        btnModificar.setText("MODIFICAR");
        btnModificar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, java.awt.Color.black, null, java.awt.Color.black));
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });

        btnEliminar.setFont(new java.awt.Font("Book Antiqua", 1, 18)); // NOI18N
        btnEliminar.setText("ELIMINAR");
        btnEliminar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, java.awt.Color.black, null, java.awt.Color.black));
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        btnAgregar.setFont(new java.awt.Font("Book Antiqua", 1, 18)); // NOI18N
        btnAgregar.setText("AGREGAR");
        btnAgregar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, java.awt.Color.black, null, java.awt.Color.black));
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });

        jButton1.setBackground(new java.awt.Color(153, 153, 255));
        jButton1.setFont(new java.awt.Font("Book Antiqua", 1, 18)); // NOI18N
        jButton1.setText("VOLVER AL MENU");
        jButton1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.gray, java.awt.Color.lightGray, java.awt.Color.black, java.awt.Color.darkGray));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(68, 68, 68)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 981, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(36, 36, 36)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btnEliminar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnModificar, javax.swing.GroupLayout.DEFAULT_SIZE, 197, Short.MAX_VALUE)
                                    .addComponent(btnAgregar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(45, 45, 45)
                                .addComponent(jButton1))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(299, 299, 299)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 779, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(153, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel1)
                .addGap(28, 28, 28)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(117, 117, 117)
                        .addComponent(btnAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(40, 40, 40)
                        .addComponent(btnModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 542, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(473, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tblProductosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblProductosMouseClicked
        // TODO add your handling code here:

        fila = tblProductos.rowAtPoint(evt.getPoint());

        txtIDM.setText(String.valueOf(tblProductos.getValueAt(fila, 0)));
        txtNombreM.setText(String.valueOf(tblProductos.getValueAt(fila, 1)));
        txtCompraM.setText(String.valueOf(tblProductos.getValueAt(fila, 2)));
        txtVentaM.setText(String.valueOf(tblProductos.getValueAt(fila, 3)));
        txtCantidadM.setText(String.valueOf(tblProductos.getValueAt(fila, 4)));

        btnModificar.setEnabled(true);
        btnEliminar.setEnabled(true);
    }//GEN-LAST:event_tblProductosMouseClicked

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        // TODO add your handling code here:
        frmModificarPP.setVisible(true);
        frmCrearPP.setSize(586, 505);
    }//GEN-LAST:event_btnModificarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        // TODO add your handling code here:
        try {
            if (fila < 0) {
                JOptionPane.showMessageDialog(this, "Por favor, seleccione un producto para eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int confirmacion = JOptionPane.showConfirmDialog(this, "¿Está seguro de que desea eliminar este producto?", "Confirmación", JOptionPane.YES_NO_OPTION);
            if (confirmacion == JOptionPane.YES_OPTION) {
                String codigo = (String) modelo.getValueAt(fila, 0);
                controlador.eliminarProducto(codigo);
                actualizarTabla();
                limpiarCampos();
                JOptionPane.showMessageDialog(this, "Producto eliminado con éxito");

                btnModificar.setEnabled(true);
                btnEliminar.setEnabled(true);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        // TODO add your handling code here:
        frmCrearPP.setVisible(true);
        frmCrearPP.setSize(586, 480);
        actualizarTabla();
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void btnAgregarPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarPActionPerformed
        // TODO add your handling code here:
        try {
            validarCampos();
            String codigo = txtID.getText().trim();

            if (controlador.verificarCodigo(codigo)) {
                JOptionPane.showMessageDialog(this, "El Producto ya existe!");
                return;
            }
            
            int indexProveedor = COMBOBOXP.getSelectedIndex();
            String proveedor = COMBOBOXP.getItemAt(indexProveedor);

            Producto p = new Producto(codigo, txtNombre.getText().trim(), Double.parseDouble(txtCompra.getText()), Double.parseDouble(txtVenta.getText()), Integer.parseInt(txtCantidad.getText()),proveedor);
            controlador.agregarProducto(p);
            actualizarTabla();
            limpiarCampos();
            JOptionPane.showMessageDialog(this, "Producto agregado con éxito");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_btnAgregarPActionPerformed

    private void txtIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIDActionPerformed

    private void txtNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreActionPerformed

    private void txtCompraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCompraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCompraActionPerformed

    private void txtCantidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCantidadActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCantidadActionPerformed

    private void txtVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtVentaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtVentaActionPerformed

    private void btnModificarPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarPActionPerformed
        // TODO add your handling code here:
        frmModificarPP.setVisible(false);

        try {
            if (fila < 0) {
                JOptionPane.showMessageDialog(this, "Por favor, seleccione un producto para modificar.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            validarCamposModificar();
            
            int indexProveedorM = COMBOBOXPM.getSelectedIndex();
            String proveedorM = COMBOBOXPM.getItemAt(indexProveedorM);


            String codigo = (String) modelo.getValueAt(fila, 0);
            Producto producto = new Producto(txtIDM.getText().trim(), txtNombreM.getText().trim(), Double.parseDouble(txtCompraM.getText()), Double.parseDouble(txtVentaM.getText()), Integer.parseInt(txtCantidadM.getText()),proveedorM);
            controlador.modificarProducto(codigo, producto);
            actualizarTabla();
            limpiarCampos();
            JOptionPane.showMessageDialog(this, "Producto modificado con éxito");
            btnModificar.setEnabled(false);
            btnEliminar.setEnabled(false);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }


    }//GEN-LAST:event_btnModificarPActionPerformed

    private void txtIDMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIDMActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIDMActionPerformed

    private void txtNombreMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreMActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreMActionPerformed

    private void txtCompraMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCompraMActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCompraMActionPerformed

    private void txtVentaMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtVentaMActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtVentaMActionPerformed

    private void txtCantidadMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCantidadMActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCantidadMActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        viewMenu2 m;
        try {
            m = new viewMenu2();
            m.setVisible(rootPaneCheckingEnabled);
        } catch (IOException ex) {
            Logger.getLogger(viewMenu2.class.getName()).log(Level.SEVERE, null, ex);
        }
        dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void COMBOBOXPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_COMBOBOXPActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_COMBOBOXPActionPerformed

    private void COMBOBOXPMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_COMBOBOXPMActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_COMBOBOXPMActionPerformed

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
            java.util.logging.Logger.getLogger(viewProducto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(viewProducto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(viewProducto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(viewProducto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new viewProducto().setVisible(true);
                } catch (IOException ex) {
                    Logger.getLogger(viewProducto.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel CANTIDAD;
    private javax.swing.JLabel CANTIDAD1;
    private javax.swing.JComboBox<String> COMBOBOXP;
    private javax.swing.JComboBox<String> COMBOBOXPM;
    private javax.swing.JLabel COMPRA;
    private javax.swing.JLabel COMPRA1;
    private javax.swing.JLabel ID;
    private javax.swing.JLabel ID1;
    private javax.swing.JLabel NOMBRE;
    private javax.swing.JLabel NOMBRE1;
    private javax.swing.JLabel PROVEEDOR;
    private javax.swing.JLabel PROVEEDOR1;
    private javax.swing.JLabel VENTA;
    private javax.swing.JLabel VENTA1;
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnAgregarP;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnModificarP;
    private javax.swing.JFrame frmCrearPP;
    private javax.swing.JFrame frmModificarPP;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblProductos;
    private javax.swing.JTextField txtCantidad;
    private javax.swing.JTextField txtCantidadM;
    private javax.swing.JTextField txtCompra;
    private javax.swing.JTextField txtCompraM;
    private javax.swing.JTextField txtID;
    private javax.swing.JTextField txtIDM;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtNombreM;
    private javax.swing.JTextField txtVenta;
    private javax.swing.JTextField txtVentaM;
    // End of variables declaration//GEN-END:variables

}
