/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Formularios;

import Conexion.Conexion;
import java.awt.Frame;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Nicolas
 */
public class IClientes extends javax.swing.JInternalFrame {
    DefaultTableModel modelo;
    /**
     * Creates new form IClientes
     */
    public IClientes() {
        initComponents();
        PrepararTabla();
    }

    void PrepararTabla() {
        ResultSet rs = null;
        Conexion cn = new Conexion();
        Connection con;
        con = cn.conexion();
        String Consulta = "SELECT `IdCln`, `TipoCln`, `NombreCln`, `TelefonoCln`, `DireccionCln`, `NIT` FROM `tbl_clientes`";
        String titulos[] = {"Código", "Tipo", "Nombre", "Teléfono", "Dirección", "NIT"};
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
    
    public int IdCln(){
        ResultSet rs = null;
        Conexion cn = new Conexion();
        Connection con;
        con = cn.conexion();
        int n = 0;
        String Consulta = "SELECT MAX(IdCln)+1 as N FROM tbl_clientes";
        try{
            PreparedStatement pst = con.prepareStatement(Consulta);
            rs = pst.executeQuery();
            while(rs.next()){
                n = Integer.parseInt(rs.getString("N"));
//                this.TxtIdCiudad.add("IdCdd", this);
                TxtCodigo.setText(rs.getString(""+n));
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
        return n;
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel3 = new javax.swing.JLabel();
        PopupMenuSelec = new javax.swing.JPopupMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Tabla = new javax.swing.JTable();
        CmbTipoCln = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        TxtNombre = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        BtnAlmacenar = new javax.swing.JButton();
        BtnEditar = new javax.swing.JButton();
        BtnEliminar = new javax.swing.JButton();
        BtnReportes = new javax.swing.JButton();
        TxtTelefono = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        TxtDireccion = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        TxtNit = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        TxtCodigo = new javax.swing.JTextField();

        jLabel3.setText("jLabel3");

        jMenuItem1.setText("Seleccionar");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        PopupMenuSelec.add(jMenuItem1);

        setBackground(new java.awt.Color(255, 255, 255));
        setClosable(true);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setForeground(new java.awt.Color(46, 71, 102));
        jLabel1.setText("Tipo de Cliente");

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
        Tabla.setComponentPopupMenu(PopupMenuSelec);
        jScrollPane1.setViewportView(Tabla);

        CmbTipoCln.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Depósito", "Cliente" }));

        jLabel2.setForeground(new java.awt.Color(46, 71, 102));
        jLabel2.setText("Nombre");

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 102, 102));
        jLabel9.setText("CLIENTES");

        jLabel4.setForeground(new java.awt.Color(46, 71, 102));
        jLabel4.setText("Teléfono");

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
                    .addComponent(BtnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BtnAlmacenar, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BtnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(BtnReportes, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addComponent(BtnReportes)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        TxtTelefono.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TxtTelefonoActionPerformed(evt);
            }
        });

        jLabel5.setForeground(new java.awt.Color(46, 71, 102));
        jLabel5.setText("Dirección");

        jLabel6.setForeground(new java.awt.Color(46, 71, 102));
        jLabel6.setText("NIT");

        jLabel7.setForeground(new java.awt.Color(46, 71, 102));
        jLabel7.setText("Código");

        TxtCodigo.setEditable(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(46, 46, 46)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(TxtNit, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(CmbTipoCln, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(TxtTelefono, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel6))
                                .addGap(122, 122, 122)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jLabel2)
                                        .addComponent(TxtNombre)
                                        .addComponent(jLabel5)
                                        .addComponent(TxtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(TxtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(178, 178, 178)
                                .addComponent(jLabel9)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 104, Short.MAX_VALUE)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(54, 54, 54)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(CmbTipoCln, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(TxtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(TxtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(TxtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(TxtNit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(TxtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 57, Short.MAX_VALUE)
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
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnAlmacenarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAlmacenarActionPerformed
        int IdPrd = IdCln();
        String TipoCln = (String) CmbTipoCln.getSelectedItem();
        String Nombre = TxtNombre.getText();
        String Telefono = TxtTelefono.getText();
        String Direccion = TxtDireccion.getText();
        String Nit = TxtNit.getText();
        Conexion cn = new Conexion();
        Connection con;
        con = cn.conexion();
        String sql = "INSERT INTO `tbl_clientes`(`IdCln`, `TipoCln`, `NombreCln`, `TelefonoCln`, `DireccionCln`, `NIT`) "
        + "VALUES(?,?,?,?,?)";
        try {
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1 ,IdPrd);
            pst.setString(2, TipoCln);
            pst.setString(3 ,Nombre);
            pst.setString(4 ,Telefono);
            pst.setString(5 ,Direccion);
            pst.setString(6 ,Nit);
           
            int n = pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Cliente Registrado");
            //            int nprd = IdPrd();
            //            int Cant = CapturarCantidad(nprd);
            //            ColocarNivel(nprd, Cant);
            PrepararTabla();
        } catch (SQLException ex) {
            System.out.println(""+ex);
        }
    }//GEN-LAST:event_BtnAlmacenarActionPerformed

    private void BtnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEditarActionPerformed
        Conexion cn = new Conexion();
        Connection con;
        con = cn.conexion();
        String Id = TxtCodigo.getText();
        if(Id.equals("")){
            JOptionPane.showMessageDialog(null, "Por Favor Seleccione el Cliente a Editar");
        }else{
            String TipoCln = (String) CmbTipoCln.getSelectedItem();
            String Nombre = TxtNombre.getText();
            String Telefono = TxtTelefono.getText();
            String Direccion = TxtDireccion.getText();
            String Nit = TxtNit.getText();

            String sql = "UPDATE `tbl_clientes` SET `TipoCln`='"+TipoCln+"',"
            + "`NombreCln`='"+Nombre+"',"
            + "`TelefonoCln`='"+Telefono+"',"
            + "`DireccionCln`='"+Direccion+"',"
            + "`NIT`='"+Nit+"' WHERE `IdCln` = '"+Id+"'";
            try {
                PreparedStatement pst = con.prepareStatement(sql);
                int n = pst.executeUpdate();
                JOptionPane.showMessageDialog(null, "Cliente Editado");
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
            JOptionPane.showMessageDialog(null, "Por Favor Seleccione el Cliente a Eliminar");
        }else{

            String sql = "DELETE FROM `tbl_clientes` WHERE `IdCln` = '"+Id+"'";
            try {
                PreparedStatement pst = con.prepareStatement(sql);
                int n = pst.executeUpdate();
                JOptionPane.showMessageDialog(null, "Cliente Eliminado");
                PrepararTabla();
            } catch (SQLException ex) {
                System.out.println(""+ex);
            }

        }
    }//GEN-LAST:event_BtnEliminarActionPerformed

    private void TxtTelefonoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TxtTelefonoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TxtTelefonoActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        int fila = Tabla.getSelectedRow();
        if(fila >= 0){
            int Id = (int) Tabla.getValueAt(fila, 0);
            ResultSet rs = null;
            Conexion cn = new Conexion();
            Connection con;
            con = cn.conexion();
            CmbTipoCln.removeAllItems();
            String Consulta = "SELECT * FROM tbl_clientes WHERE IdCln = '"+Id+"'";
            try{
                PreparedStatement pst = con.prepareStatement(Consulta);
                rs = pst.executeQuery();
                while(rs.next()){
                    this.CmbTipoCln.addItem(rs.getString("TipoCln"));
                    TxtNombre.setText(rs.getString("NombreCln"));
                    TxtCodigo.setText(rs.getString("IdCln"));
                    TxtTelefono.setText(rs.getString("TelefonoCln"));
                    TxtDireccion.setText(rs.getString("DireccionCln"));
                    TxtNit.setText(rs.getString("NIT"));
                    
                    //TxtCodigoPrd.setText(rs.getString("IdPrd"));
                }
            }catch(Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void BtnReportesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnReportesActionPerformed
        Conexion cn = new Conexion();
        Connection con;
        con = cn.conexion();
        
        
        try{
            JasperReport jr = JasperCompileManager.compileReport(System.getProperty("user.dir").concat("/src/Reportes/RptClientes.jrxml"));
            JasperPrint jp = JasperFillManager.fillReport(jr, null, con);
            JasperViewer jv = new JasperViewer(jp, false);
            jv.setVisible(true);
            jv.setTitle("Lista de Clientes");
            jv.setExtendedState(Frame.MAXIMIZED_BOTH);
        }catch(JRException ex){
            System.out.print(ex);
        }
    }//GEN-LAST:event_BtnReportesActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnAlmacenar;
    private javax.swing.JButton BtnEditar;
    private javax.swing.JButton BtnEliminar;
    private javax.swing.JButton BtnReportes;
    private javax.swing.JComboBox CmbTipoCln;
    private javax.swing.JPopupMenu PopupMenuSelec;
    private javax.swing.JTable Tabla;
    private javax.swing.JTextField TxtCodigo;
    private javax.swing.JTextField TxtDireccion;
    private javax.swing.JTextField TxtNit;
    private javax.swing.JTextField TxtNombre;
    private javax.swing.JTextField TxtTelefono;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}