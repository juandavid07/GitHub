/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Formularios;

import Conexion.Conexion;
import com.sun.istack.internal.logging.Logger;
import static com.sun.xml.internal.fastinfoset.alphabet.BuiltInRestrictedAlphabets.table;
import java.awt.Color;
import java.awt.Frame;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Nicolas
 */
public class IProductos extends javax.swing.JInternalFrame {
    DefaultTableModel modelo;
    /**
     * Creates new form IProductos
     */
    public IProductos() {
        initComponents();
        TipoTejas();
        PrepararTabla();
        Tabla.setDefaultRenderer (Object.class, new ColorTable());
        
    }

    
    void PrepararTabla() {
        ResultSet rs = null;
        Conexion cn = new Conexion();
        Connection con;
        con = cn.conexion();
        String Consulta = "SELECT IdPrd, TipoPrd, NumeroPrd, MedidaPrd, NombrePrd, CantidadPrd, NivelPrd FROM tbl_productos";
        String titulos[] = {"Código", "Tipo", "Número", "Medida", "Nombre", "Cantidad", "Nivel"};
        try{
            PreparedStatement pst = con.prepareStatement(Consulta);
            rs = pst.executeQuery();
            ResultSetMetaData rsMd = pst.getMetaData();
            int nc = rsMd.getColumnCount();
            modelo = new DefaultTableModel(null, titulos);
            Tabla.setModel(modelo);

            while(rs.next()){
                Object [] filas = new Object [nc];
                for ( int i = 0; i<nc; i++){
                    filas [i] = rs.getObject(i+1);
                }
                modelo.addRow(filas);
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    public int IdPrd(){
        ResultSet rs = null;
        Conexion cn = new Conexion();
        Connection con;
        con = cn.conexion();
        int n = 0;
        String Consulta = "SELECT MAX(IdPrd)+1 as N FROM tbl_productos";
        try{
            PreparedStatement pst = con.prepareStatement(Consulta);
            rs = pst.executeQuery();
            while(rs.next()){
                n = Integer.parseInt(rs.getString("N"));
//                this.TxtIdCiudad.add("IdCdd", this);
                //TxtCodigoPrd.setText(rs.getString("IdPrd"));
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
        return n;
    }
    
    public int CapturarCantidad(int IdPrd){
        ResultSet rs = null;
        Conexion cn = new Conexion();
        Connection con;
        con = cn.conexion();
        int n = 0;
        String Consulta = "SELECT CantidadPrd FROM tbl_productos WHERE IdPrd ='"+IdPrd+"'";
        try{
            PreparedStatement pst = con.prepareStatement(Consulta);
            rs = pst.executeQuery();
            while(rs.next()){
                n = Integer.parseInt(rs.getString("CantidadPrd"));
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
        return n;
    }
    
    public String TenerNivel(){
        int Cantidad = (int) SpnCantidad.getValue();
        String Level = "";
        if(Cantidad < 10){
            Level = "Bajo";
        }
        if(Cantidad >= 10 && Cantidad < 50){
            Level = "Medio";
        }
        if(Cantidad >= 50){
            Level = "Alto";
        }
        return Level;
    }
    public void ColocarNivel(int IdPrd, int Cantidad){
        if(Cantidad<10){
            Conexion cn = new Conexion();
            Connection con;
            con = cn.conexion();
            String sql = "INSERT INTO `tbl_productos` (`NivelPrd`) VALUES ('Bajo') WHERE IdPrd = '"+IdPrd+"'";
            try {
                PreparedStatement pst = con.prepareStatement(sql);
                int n = pst.executeUpdate();
                JOptionPane.showMessageDialog(null, "Producto Registrado");
                PrepararTabla();
            } catch (SQLException ex) {
                System.out.println(""+ex);
            }
        }
        if(Cantidad > 10 && Cantidad < 50){
            Conexion cn = new Conexion();
            Connection con;
            con = cn.conexion();
            String sql = "INSERT INTO `tbl_productos` (`NivelPrd`) VALUES ('Medio') WHERE IdPrd = '"+IdPrd+"'";
            try {
                PreparedStatement pst = con.prepareStatement(sql);
                int n = pst.executeUpdate();
                JOptionPane.showMessageDialog(null, "Producto Registrado");
                PrepararTabla();
            } catch (SQLException ex) {
                System.out.println(""+ex);
            }
        }
        if(Cantidad > 50){
            Conexion cn = new Conexion();
            Connection con;
            con = cn.conexion();
            String sql = "INSERT INTO `tbl_productos` (`NivelPrd`) VALUES ('Alto') WHERE IdPrd = '"+IdPrd+"'";
            try {
                PreparedStatement pst = con.prepareStatement(sql);
                int n = pst.executeUpdate();
                JOptionPane.showMessageDialog(null, "Producto Registrado");
                PrepararTabla();
            } catch (SQLException ex) {
                System.out.println(""+ex);
            }
        }
    }
    public void TipoTejas(){
        ResultSet rs = null;
        Conexion cn = new Conexion();
        Connection con;
        con = cn.conexion();
        CmbTipotejas.removeAllItems();
        String Consulta = "SELECT * FROM tbl_tipotejas";
        try{
            PreparedStatement pst = con.prepareStatement(Consulta);
            rs = pst.executeQuery();
            while(rs.next()){
                this.CmbTipotejas.addItem(rs.getString("nombre"));
//                this.TxtIdCiudad.add("IdCdd", this);
                //TxtCodigoPrd.setText(rs.getString("IdPrd"));
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
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

        PopupSelec = new javax.swing.JPopupMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        BtnAlmacenar = new javax.swing.JButton();
        BtnEditar = new javax.swing.JButton();
        BtnEliminar = new javax.swing.JButton();
        BtnConsultar = new javax.swing.JButton();
        BtnReportes = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        Tabla = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        CmbTipotejas = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        TxtNombre = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        SpnNumero = new javax.swing.JSpinner();
        jLabel4 = new javax.swing.JLabel();
        CmbMedida = new javax.swing.JComboBox();
        jLabel5 = new javax.swing.JLabel();
        SpnCantidad = new javax.swing.JSpinner();
        jLabel6 = new javax.swing.JLabel();
        TxtCompra = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        TxtVentaD = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        TxtVentaC = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        TxtCodigo = new javax.swing.JTextField();

        PopupSelec.setToolTipText("Seleccionar");
        PopupSelec.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                PopupSelecAncestorAdded(evt);
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });

        jMenuItem1.setText("Seleccionar");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        PopupSelec.add(jMenuItem1);

        setClosable(true);
        setTitle("Productos :: Tejas");

        jPanel1.setBackground(new java.awt.Color(254, 254, 254));

        jPanel3.setBackground(new java.awt.Color(254, 254, 254));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Acciones", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(46, 71, 102))); // NOI18N

        BtnAlmacenar.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        BtnAlmacenar.setForeground(new java.awt.Color(46, 71, 102));
        BtnAlmacenar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/Save.png"))); // NOI18N
        BtnAlmacenar.setText("Almacenar");
        BtnAlmacenar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnAlmacenar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAlmacenarActionPerformed(evt);
            }
        });

        BtnEditar.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        BtnEditar.setForeground(new java.awt.Color(46, 71, 102));
        BtnEditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/Edit.png"))); // NOI18N
        BtnEditar.setText("Editar");
        BtnEditar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnEditarActionPerformed(evt);
            }
        });

        BtnEliminar.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        BtnEliminar.setForeground(new java.awt.Color(46, 71, 102));
        BtnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/Delete.png"))); // NOI18N
        BtnEliminar.setText("Eliminar");
        BtnEliminar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnEliminarActionPerformed(evt);
            }
        });

        BtnConsultar.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        BtnConsultar.setForeground(new java.awt.Color(46, 71, 102));
        BtnConsultar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/Search.png"))); // NOI18N
        BtnConsultar.setText("Consultar");
        BtnConsultar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnConsultar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnConsultarActionPerformed(evt);
            }
        });

        BtnReportes.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        BtnReportes.setForeground(new java.awt.Color(46, 71, 102));
        BtnReportes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/Report.png"))); // NOI18N
        BtnReportes.setText("Reporte");
        BtnReportes.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnReportes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnReportesActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(BtnReportes, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BtnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BtnAlmacenar, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BtnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BtnConsultar, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(BtnAlmacenar)
                .addGap(18, 18, 18)
                .addComponent(BtnEditar)
                .addGap(18, 18, 18)
                .addComponent(BtnEliminar)
                .addGap(18, 18, 18)
                .addComponent(BtnConsultar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(BtnReportes)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        Tabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        Tabla.setComponentPopupMenu(PopupSelec);
        jScrollPane1.setViewportView(Tabla);

        jLabel1.setForeground(new java.awt.Color(46, 71, 102));
        jLabel1.setText("Tipo de Teja");

        CmbTipotejas.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel2.setForeground(new java.awt.Color(46, 71, 102));
        jLabel2.setText("Nombre");

        jLabel3.setForeground(new java.awt.Color(46, 71, 102));
        jLabel3.setText("Número");

        jLabel4.setForeground(new java.awt.Color(46, 71, 102));
        jLabel4.setText("Medida");

        CmbMedida.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "4", "5", "6", "7", "8", "10", "12" }));

        jLabel5.setForeground(new java.awt.Color(46, 71, 102));
        jLabel5.setText("Cantidad");

        jLabel6.setForeground(new java.awt.Color(46, 71, 102));
        jLabel6.setText("Valor de Compra");

        jLabel7.setForeground(new java.awt.Color(46, 71, 102));
        jLabel7.setText("Valor de Venta D.");

        jLabel8.setForeground(new java.awt.Color(46, 71, 102));
        jLabel8.setText("Valor de Venta C.");

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 102, 102));
        jLabel9.setText("PRODUCTOS");

        jLabel10.setForeground(new java.awt.Color(46, 71, 102));
        jLabel10.setText("Código");

        TxtCodigo.setEditable(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(161, 161, 161)
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGap(0, 38, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(CmbTipotejas, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel6)
                                        .addGap(133, 133, 133)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel7)
                                            .addComponent(jLabel5)
                                            .addComponent(SpnCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel3)
                                            .addComponent(SpnNumero, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addComponent(jLabel2)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(TxtNombre)
                                            .addComponent(TxtCompra, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(87, 87, 87)
                                        .addComponent(TxtVentaD, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(62, 62, 62)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel4)
                                    .addComponent(TxtVentaC)
                                    .addComponent(CmbMedida, 0, 131, Short.MAX_VALUE)
                                    .addComponent(jLabel10)
                                    .addComponent(TxtCodigo))
                                .addGap(37, 37, 37)))
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(62, 62, 62)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(CmbTipotejas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(SpnNumero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(CmbMedida, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jLabel5)
                            .addComponent(jLabel10))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(TxtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(SpnCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(TxtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(27, 27, 27)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(TxtCompra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(TxtVentaD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(TxtVentaC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnAlmacenarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAlmacenarActionPerformed
        int IdPrd = IdPrd();
        NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.getDefault());
        String TipoPrd = (String) CmbTipotejas.getSelectedItem();
        int NumeroPrd = (int) SpnNumero.getValue();
        String Medida = (String) CmbMedida.getSelectedItem();
        String Nombre = TxtNombre.getText();
        int Cantidad = (int) SpnCantidad.getValue();
        float Vcompra = Float.parseFloat(TxtCompra.getText());
        float Vventad = Float.parseFloat(TxtVentaD.getText());
        float Vventac = Float.parseFloat(TxtVentaC.getText());
        
        Conexion cn = new Conexion();
        Connection con;
        con = cn.conexion();
        String sql = "INSERT INTO `tbl_productos`(`IdPrd`, `TipoPrd`, `NumeroPrd`, `MedidaPrd`, `NombrePrd`, `CantidadPrd`, `VCompraPrd`, `VVentaDPrd`, `VVentaCPrd`, NivelPrd) "
        + "VALUES(?,?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1 ,IdPrd);
            pst.setString(2, TipoPrd);
            pst.setInt(3 ,NumeroPrd);
            pst.setString(4 ,Medida);
            pst.setString(5 ,Nombre);
            pst.setInt(6 ,Cantidad);
            pst.setFloat(7 ,Vcompra);
            pst.setFloat(8 ,Vventad);
            pst.setFloat(9 ,Vventac);
            pst.setString(10, TenerNivel());
            int n = pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Producto Registrado");
//            int nprd = IdPrd();
//            int Cant = CapturarCantidad(nprd);
//            ColocarNivel(nprd, Cant);
            PrepararTabla();
        } catch (SQLException ex) {
            System.out.println(""+ex);
        }
    }//GEN-LAST:event_BtnAlmacenarActionPerformed

    private void PopupSelecAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_PopupSelecAncestorAdded

    }//GEN-LAST:event_PopupSelecAncestorAdded

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        BtnAlmacenar.setEnabled(false);
        int fila = Tabla.getSelectedRow();
        if(fila >= 0){
            int Id = (int) Tabla.getValueAt(fila, 0);
            ResultSet rs = null;
            Conexion cn = new Conexion();
            Connection con;
            con = cn.conexion();
            CmbTipotejas.removeAllItems();
            String Consulta = "SELECT * FROM tbl_productos WHERE IdPrd = '"+Id+"'";
            try{
                PreparedStatement pst = con.prepareStatement(Consulta);
                rs = pst.executeQuery();
                while(rs.next()){
                    this.CmbTipotejas.addItem(rs.getString("TipoPrd"));
                    SpnNumero.setValue(rs.getInt("NumeroPrd"));
                    TxtNombre.setText(rs.getString("NombrePrd"));
                    TxtCodigo.setText(rs.getString("IdPrd"));
                    TxtCompra.setText(rs.getString("VCompraPrd"));
                    TxtVentaD.setText(rs.getString("VVentaDPrd"));
                    TxtVentaC.setText(rs.getString("VVentaCPrd"));
                    
                    //TxtCodigoPrd.setText(rs.getString("IdPrd"));
                }
                
            }catch(Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void BtnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEditarActionPerformed
        Conexion cn = new Conexion();
        Connection con;
        con = cn.conexion();
        String Id = TxtCodigo.getText();
        if(Id.equals("")){
            JOptionPane.showMessageDialog(null, "Por Favor Seleccione el Producto a Editar");
        }else{
        String TipoPrd = (String) CmbTipotejas.getSelectedItem();
        int NumeroPrd = (int) SpnNumero.getValue();
        String Medida = (String) CmbMedida.getSelectedItem();
        String Nombre = TxtNombre.getText();
        int Cantidad = (int) SpnCantidad.getValue();
        float Vcompra = Float.parseFloat(TxtCompra.getText());
        float Vventad = Float.parseFloat(TxtVentaD.getText());
        float Vventac = Float.parseFloat(TxtVentaC.getText());  
        
        String sql = "UPDATE `tbl_productos` SET `TipoPrd`='"+TipoPrd+"',"
                + "`NumeroPrd`='"+NumeroPrd+"',"
                + "`MedidaPrd`='"+Medida+"',"
                + "`NombrePrd`='"+Nombre+"',"
                + "`CantidadPrd`='"+Cantidad+"',"
                + "`VCompraPrd`='"+Vcompra+"',"
                + "`VVentaDPrd`='"+Vventad+"',"
                + "`VVentaCPrd`='"+Vventac+"',"
                + "`NivelPrd`='"+TenerNivel()+"' WHERE `IdPrd` = '"+Id+"'";
        try {
            PreparedStatement pst = con.prepareStatement(sql);
            int n = pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Producto Editado");
            PrepararTabla();
        } catch (SQLException ex) {
            System.out.println(""+ex);
        }
        
        }
    }//GEN-LAST:event_BtnEditarActionPerformed

    private void BtnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEliminarActionPerformed
        Conexion cn = new Conexion();
        Connection con;
        con = cn.conexion();
        String Id = TxtCodigo.getText();
        if(Id.equals("")){
            JOptionPane.showMessageDialog(null, "Por Favor Seleccione el Producto a Eliminar");
        }else{
                
        String sql = "DELETE FROM `tbl_productos` WHERE `IdPrd` = '"+Id+"'";
        try {
            PreparedStatement pst = con.prepareStatement(sql);
            int n = pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Producto Eliminado");
            PrepararTabla();
        } catch (SQLException ex) {
            System.out.println(""+ex);
        }
        
        }
    }//GEN-LAST:event_BtnEliminarActionPerformed

    private void BtnReportesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnReportesActionPerformed
        Conexion cn = new Conexion();
        Connection con;
        con = cn.conexion();
        
        
        try{
            JasperReport jr = JasperCompileManager.compileReport(System.getProperty("user.dir").concat("/src/Reportes/RptProductos.jrxml"));
            JasperPrint jp = JasperFillManager.fillReport(jr, null, con);
            JasperViewer jv = new JasperViewer(jp, false);
            jv.setVisible(true);
            jv.setTitle("Lista de Productos");
            jv.setExtendedState(Frame.MAXIMIZED_BOTH);
        }catch(JRException ex){
            System.out.print(ex);
        }
        
        
    }//GEN-LAST:event_BtnReportesActionPerformed

    private void BtnConsultarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnConsultarActionPerformed
        Conexion cn = new Conexion();
        Connection con;
        con = cn.conexion();
        
        try{
                String Codigo = TxtCodigo.getText();
                JasperReport report = JasperCompileManager.compileReport(System.getProperty("user.dir").concat("/src/Reportes/RptProducto.jrxml"));
                Map parametros = new HashMap();
                parametros.put("Codigo", TxtCodigo.getText());
                JasperPrint print = JasperFillManager.fillReport(report, parametros, con);
                JasperViewer view = new JasperViewer(print, false);
                view.setTitle("Información Producto");
                view.setExtendedState(Frame.MAXIMIZED_BOTH);
                view.setVisible(true);
            }catch(Exception e){
                System.out.println("Error "+e);
            }
    }//GEN-LAST:event_BtnConsultarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnAlmacenar;
    private javax.swing.JButton BtnConsultar;
    private javax.swing.JButton BtnEditar;
    private javax.swing.JButton BtnEliminar;
    private javax.swing.JButton BtnReportes;
    private javax.swing.JComboBox CmbMedida;
    private javax.swing.JComboBox CmbTipotejas;
    private javax.swing.JPopupMenu PopupSelec;
    private javax.swing.JSpinner SpnCantidad;
    private javax.swing.JSpinner SpnNumero;
    private javax.swing.JTable Tabla;
    private javax.swing.JTextField TxtCodigo;
    private javax.swing.JTextField TxtCompra;
    private javax.swing.JTextField TxtNombre;
    private javax.swing.JTextField TxtVentaC;
    private javax.swing.JTextField TxtVentaD;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
