/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Formularios;

import Conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Nicolas
 */
public class IFactura extends javax.swing.JInternalFrame {
    DefaultTableModel modelo;
    /**
     * Creates new form IFactura
     */
    public IFactura() {
        initComponents();
        Fecha();
        PrepararTabla();
        Productos();
        Clientes();
        NFct();
        TxtDescuento.setText(""+0);
        Subtotal();
        IVA();
    }
    
    public void NFct(){
        ResultSet rs = null;
        Conexion cn = new Conexion();
        Connection con;
        con = cn.conexion();
        String Consulta = "SELECT (MAX(IdFct))+1 AS N FROM tbl_facturas";
        try{
            PreparedStatement pst = con.prepareStatement(Consulta);
            rs = pst.executeQuery();
            while(rs.next()){
                TxtNFct.setText(rs.getString("N"));
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    
    void PrepararTabla() {
        ResultSet rs = null;
        Conexion cn = new Conexion();
        Connection con;
        con = cn.conexion();
        String Consulta = "SELECT `IdLinea`, `CodigoFct`, `CodigoPrd`, `CantidadFPrd`, `ValorUnidad`, TRUNCATE(`CantidadFPrd`*`ValorUnidad`, 0) AS `TotalPrd`\n" +
                            "FROM `tbl_prdfactura`\n" +
                            "INNER JOIN tbl_productos\n" +
                            "ON CodigoPrd =  IdPrd ";
        String titulos[] = {"Lin. Venta", "Código", "Producto", "Cantidad", "Precio", "Valor Total"};
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
    
    public int NoFactura(){
        int nof= 0;
        Conexion cn = new Conexion();
        Connection con;
        con = cn.conexion();
        
        try {
            String sql="SELECT MAX(IdFct) AS IdFct FROM tbl_facturas";
                 Statement st = con.createStatement();
                 ResultSet rs = st.executeQuery(sql);
          if(rs.next()){
               nof = rs.getInt("IdFct");
            }
            int nofa = nof+1;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "ErrorMsg", "Failure", JOptionPane.ERROR_MESSAGE);
        }
        return nof;
    }
    
    public void Fecha(){
        Calendar c1 = GregorianCalendar.getInstance();
        Date Fecha = c1.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        TxtVence.setText( ""+sdf.format(Fecha)); 
        TxtFecha.setText( ""+sdf.format(Fecha)); 
    }
    
    
    public void Productos(){

        CmbPrd.removeAllItems();
        ResultSet rs = null;
        Conexion cn = new Conexion();
        Connection con;
        con = cn.conexion();
        String Consulta = "SELECT * FROM tbl_productos";
        try{
            PreparedStatement pst = con.prepareStatement(Consulta);
            rs = pst.executeQuery();
            while(rs.next()){
                this.CmbPrd.addItem(rs.getString("NombrePrd"));
//                this.TxtIdCiudad.add("IdCdd", this);
                TxtIdPrd.setText(rs.getString("IdPrd"));
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
        
    }
    
    public void Clientes(){
        CmbClientes.removeAllItems();
        ResultSet rs = null;
        Conexion cn = new Conexion();
        Connection con;
        con = cn.conexion();
        String Consulta = "SELECT IdCln, NombreCln FROM tbl_clientes";
        try{
            PreparedStatement pst = con.prepareStatement(Consulta);
            rs = pst.executeQuery();
            while(rs.next()){
                this.CmbClientes.addItem(rs.getString("NombreCln"));
//                this.TxtIdCiudad.add("IdCdd", this);
                TxtIdCliente.setText(rs.getString("IdCln"));
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public String TipoCliente(String IdCln){
        ResultSet rs = null;
        Conexion cn = new Conexion();
        Connection con;
        con = cn.conexion();
        String TipoC = "";
        String Consulta = "SELECT TipoCln FROM tbl_clientes WHERE IdCln = '"+IdCln+"'";
        try{
            PreparedStatement pst = con.prepareStatement(Consulta);
            rs = pst.executeQuery();
            while(rs.next()){
                TipoC = rs.getString("TipoCln");
//                this.TxtIdCiudad.add("IdCdd", this);
                //TxtCodigoPrd.setText(rs.getString("IdPrd"));
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
        return TipoC;
    }
    
    public void Subtotal(){
        ResultSet rs = null;
        Conexion cn = new Conexion();
        Connection con;
        con = cn.conexion();
        float subtotal = 0;
        int fct = NoFactura()+1;
        NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.getDefault());
        String Consulta = "SELECT SUM(CantidadFPrd*ValorUnidad) AS SubTotal FROM tbl_prdfactura WHERE CodigoFct = '"+fct+"'";
        try{
            PreparedStatement pst = con.prepareStatement(Consulta);
            rs = pst.executeQuery();
            while(rs.next()){
                subtotal = rs.getFloat("SubTotal");
                String cur = nf.format(subtotal);
                LblSubtotal.setText(cur);
//                this.TxtIdCiudad.add("IdCdd", this);
                //TxtCodigoPrd.setText(rs.getString("IdPrd"));
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    public int Cantidad(int IdPrd){
        ResultSet rs = null;
        Conexion cn = new Conexion();
        Connection con;
        con = cn.conexion();
        int cantidad = 0;
        String Consulta = "SELECT CantidadPrd FROM tbl_productos WHERE IdPrd = '"+IdPrd+"'";
        try{
            PreparedStatement pst = con.prepareStatement(Consulta);
            rs = pst.executeQuery();
            while(rs.next()){
                cantidad = rs.getInt("CantidadPrd");
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
        return cantidad;
    }
    public void UpdateCantidad(int IdPrd, int cantidadmenos, String Accion){
        boolean rs = false;
        Conexion cn = new Conexion();
            Connection con;
            con = cn.conexion();
            String Level = "";
            int can = Cantidad(IdPrd);
            int newcant = 0;
            if(Accion.equals("Agregar")){
                newcant = can - cantidadmenos;
            }
            if(Accion.equals("Eliminar")){
                newcant = can + cantidadmenos;
            }
            if(newcant < 10){
            Level = "Bajo";
            }
            if(newcant >= 10 && newcant < 50){
                Level = "Medio";
            }
            if(newcant >= 50){
                Level = "Alto";
            }
            String Consulta = "UPDATE `tbl_productos` SET `CantidadPrd`= ?, `NivelPrd`= ? WHERE IdPrd = ?";
            try{
                PreparedStatement pst = con.prepareStatement(Consulta);
                pst.setInt(1 ,newcant);
                pst.setString(2, Level);
                pst.setInt(3 ,IdPrd);
                rs = pst.executeUpdate() == 1;
            }catch(Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
    }
    
    public void IVA(){
       ResultSet rs = null;
        Conexion cn = new Conexion();
        Connection con;
        con = cn.conexion();
        String prc = (String) CmbIva.getSelectedItem();
        float vprc = 0;
        if(prc.equals("16.0%")){
            vprc = (float) 0.16;
        }
        float subtotal = 0;
        int fct = NoFactura()+1;
        NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.getDefault());
        String Consulta = "SELECT SUM(CantidadFPrd*ValorUnidad) AS SubTotal FROM tbl_prdfactura WHERE CodigoFct = '"+fct+"'";
        try{
            PreparedStatement pst = con.prepareStatement(Consulta);
            rs = pst.executeQuery();
            while(rs.next()){
                subtotal = (rs.getFloat("SubTotal")*vprc);
                String cur = nf.format(subtotal);
                LblIVA.setText(cur);
//                this.TxtIdCiudad.add("IdCdd", this);
                //TxtCodigoPrd.setText(rs.getString("IdPrd"));
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        } 
    }
    
    public void Total(){
        
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel9 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton8 = new javax.swing.JButton();
        PopupSelect = new javax.swing.JPopupMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        TxtIdCliente = new javax.swing.JTextField();
        CmbClientes = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        TxtVence = new javax.swing.JTextField();
        TxtNFct = new javax.swing.JTextField();
        TxtFecha = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        CmbIva = new javax.swing.JComboBox();
        jLabel5 = new javax.swing.JLabel();
        TxtDescuento = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        TxtIdPrd = new javax.swing.JTextField();
        CmbPrd = new javax.swing.JComboBox();
        jLabel7 = new javax.swing.JLabel();
        TxtCantidad = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        TxtPrecio = new javax.swing.JTextField();
        BtnAdd = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        Tabla = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        LblSubtotal = new javax.swing.JLabel();
        LblDesc = new javax.swing.JLabel();
        LblIVA = new javax.swing.JLabel();
        LblTotal = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        TxtObs = new javax.swing.JTextArea();
        jPanel4 = new javax.swing.JPanel();
        BtnGuardar = new javax.swing.JButton();
        BtnImprimir = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 102, 102));
        jLabel9.setText("PRODUCTOS");

        jLabel8.setText("jLabel8");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jButton8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/Save.png"))); // NOI18N

        jMenuItem1.setText("Eliminar");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        PopupSelect.add(jMenuItem1);

        setClosable(true);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 102, 102));
        jLabel10.setText("CREAR FACTURA");

        jLabel1.setForeground(new java.awt.Color(46, 71, 102));
        jLabel1.setText("Cliente");

        TxtIdCliente.setEditable(false);

        CmbClientes.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel2.setForeground(new java.awt.Color(46, 71, 102));
        jLabel2.setText("Vence");

        TxtVence.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        TxtVence.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        TxtVence.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TxtVenceActionPerformed(evt);
            }
        });

        TxtNFct.setEditable(false);
        TxtNFct.setBackground(new java.awt.Color(255, 255, 255));
        TxtNFct.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        TxtNFct.setForeground(new java.awt.Color(204, 0, 51));
        TxtNFct.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        TxtNFct.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 0, 51)));

        TxtFecha.setEditable(false);
        TxtFecha.setBackground(new java.awt.Color(255, 255, 255));
        TxtFecha.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        TxtFecha.setForeground(new java.awt.Color(0, 153, 102));
        TxtFecha.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        TxtFecha.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 153, 102)));

        jLabel3.setForeground(new java.awt.Color(204, 204, 204));
        jLabel3.setText("dd/mm/aaaa");

        jLabel4.setForeground(new java.awt.Color(46, 71, 102));
        jLabel4.setText("IVA");

        CmbIva.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0.0 %", "16.0%" }));

        jLabel5.setForeground(new java.awt.Color(46, 71, 102));
        jLabel5.setText("Descuento");

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Seleccionar Producto", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(0, 102, 102))); // NOI18N

        jLabel6.setForeground(new java.awt.Color(46, 71, 102));
        jLabel6.setText("Producto");

        TxtIdPrd.setEditable(false);

        CmbPrd.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        CmbPrd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CmbPrdActionPerformed(evt);
            }
        });

        jLabel7.setForeground(new java.awt.Color(46, 71, 102));
        jLabel7.setText("Cantidad");

        jLabel11.setForeground(new java.awt.Color(46, 71, 102));
        jLabel11.setText("Precio");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(TxtIdPrd, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(CmbPrd, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(26, 26, 26)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(TxtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addGap(0, 103, Short.MAX_VALUE))
                    .addComponent(TxtPrecio))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TxtIdPrd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(CmbPrd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TxtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TxtPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        BtnAdd.setBackground(new java.awt.Color(255, 255, 255));
        BtnAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/Check.png"))); // NOI18N
        BtnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAddActionPerformed(evt);
            }
        });

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
        Tabla.setComponentPopupMenu(PopupSelect);
        jScrollPane2.setViewportView(Tabla);

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel12.setText("Subtotal");

        jLabel13.setText("Desc (-)");

        jLabel14.setText("I.V.A.");

        jLabel15.setText("Total");

        LblSubtotal.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        LblSubtotal.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        LblSubtotal.setText("0");
        LblSubtotal.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        LblDesc.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        LblDesc.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        LblDesc.setText("0");
        LblDesc.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        LblIVA.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        LblIVA.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        LblIVA.setText("0");
        LblIVA.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        LblTotal.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        LblTotal.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        LblTotal.setText("0");
        LblTotal.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel14)
                    .addComponent(jLabel13)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel12)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addGap(14, 14, 14)
                            .addComponent(jLabel15))))
                .addGap(54, 54, 54)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(LblSubtotal, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LblDesc, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LblIVA, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LblTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(54, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(LblSubtotal))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(LblDesc))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(LblIVA))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(LblTotal))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel20.setForeground(new java.awt.Color(46, 71, 102));
        jLabel20.setText("Observaciones");

        TxtObs.setColumns(20);
        TxtObs.setRows(5);
        jScrollPane3.setViewportView(TxtObs);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        BtnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/Save.png"))); // NOI18N
        BtnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnGuardarActionPerformed(evt);
            }
        });

        BtnImprimir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/Report.png"))); // NOI18N
        BtnImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnImprimirActionPerformed(evt);
            }
        });

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/Search.png"))); // NOI18N

        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/Edit.png"))); // NOI18N

        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/Delete.png"))); // NOI18N

        jButton9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/Save.png"))); // NOI18N

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(BtnGuardar)
                        .addGap(27, 27, 27)
                        .addComponent(BtnImprimir))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jButton6)
                        .addGap(27, 27, 27)
                        .addComponent(jButton7)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton5, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton9, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap(18, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton5)
                    .addComponent(BtnImprimir)
                    .addComponent(BtnGuardar))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton6)
                    .addComponent(jButton7)
                    .addComponent(jButton9))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        jLabel21.setForeground(new java.awt.Color(46, 71, 102));
        jLabel21.setText("N°. Factura");

        jLabel22.setForeground(new java.awt.Color(46, 71, 102));
        jLabel22.setText("Fecha");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(289, 289, 289)
                        .addComponent(jLabel10))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(68, 68, 68)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(18, 18, 18)
                                .addComponent(TxtVence, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(18, 18, 18)
                                .addComponent(TxtIdCliente)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(CmbClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(CmbIva, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(TxtDescuento)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel21)
                            .addComponent(jLabel22))
                        .addGap(28, 28, 28)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(TxtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(TxtNFct, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(33, 33, 33))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(60, 60, 60))))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(68, 68, 68)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(BtnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel20))
                                .addGap(18, 18, 18)
                                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(22, 22, 22))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TxtNFct, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TxtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22))
                .addGap(3, 3, 3)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(TxtIdCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(CmbClientes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(25, 25, 25)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(TxtVence, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(CmbIva, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(TxtDescuento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(BtnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel20)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane3)))
                .addGap(112, 112, 112))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 658, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TxtVenceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TxtVenceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TxtVenceActionPerformed

    private void CmbPrdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CmbPrdActionPerformed
        ResultSet rs = null;
        Conexion cn = new Conexion();
        Connection con;
        con = cn.conexion();
        String TPC = TipoCliente(TxtIdCliente.getText());
        String Consulta = "";
        if(TPC.equals("Depósito")){
            Consulta = "SELECT IdPrd, VVentaDPrd AS Precio FROM tbl_productos WHERE NombrePrd ='"+this.CmbPrd.getSelectedItem()+"'";
        }
        else if(TPC.equals("Cliente")){
            Consulta = "SELECT IdPrd, VVentaCPrd AS Precio FROM tbl_productos WHERE NombrePrd ='"+this.CmbPrd.getSelectedItem()+"'";
        }

        try{
            PreparedStatement pst = con.prepareStatement(Consulta);
            //System.out.println("hasta quie bien 1");
            rs = pst.executeQuery();
            //System.out.println("hasta quie bien 2");
            if(rs.next()){
               TxtIdPrd.setText(String.valueOf(rs.getString("IdPrd")));
               TxtPrecio.setText(String.valueOf(rs.getInt("Precio")));
            }  

            
        }catch(Exception e){
            System.out.println(""+e);
        }
    }//GEN-LAST:event_CmbPrdActionPerformed

    private void BtnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAddActionPerformed
        int total = 0;
        NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.getDefault());
        String cur = nf.format(TxtDescuento.getText());
        LblDesc.setText(cur);
        ResultSet rs = null;
        Conexion cn = new Conexion();
        Connection con;
        con = cn.conexion();
        int IdFct = NoFactura()+1;
        String IdPrd = TxtIdPrd.getText();
        String Cantidad = TxtCantidad.getText();
        String Precio = TxtPrecio.getText();
        int ValorTotal = Integer.parseInt(TxtCantidad.getText())*Integer.parseInt(TxtPrecio.getText());
        
        String sql = "INSERT INTO tbl_prdfactura (CodigoFct, CodigoPrd, CantidadFPrd, ValorUnidad) VALUES (?, ?, ?, ?)"; 
        try { 
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1 ,IdFct);
            pst.setString(2, IdPrd);
            pst.setString(3 ,Cantidad);
            pst.setString(4 ,Precio);
            int n = pst.executeUpdate();
            UpdateCantidad(Integer.parseInt(IdPrd), Integer.parseInt(Cantidad), "Agregar");
            PrepararTabla();
            Subtotal();
            IVA();
        } catch (SQLException ex) {
            System.out.println(""+ex);
        }
    }//GEN-LAST:event_BtnAddActionPerformed

    private void BtnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnGuardarActionPerformed
        ResultSet rs = null;
        Conexion cn = new Conexion();
        Connection con;
        con = cn.conexion();
        int Id = Integer.parseInt(TxtNFct.getText());
        String Fecha = TxtFecha.getText();
        String FechaV = TxtVence.getText();
        int Cliente = Integer.parseInt(TxtIdCliente.getText());
        double Iva = 0;
        if(CmbIva.getSelectedItem().equals("16.0%")){
            Iva = 0.16;
        }
        double Descuento = Double.parseDouble(TxtDescuento.getText());
        String Obs = TxtObs.getText();
        
        String Consulta = "INSERT INTO `tbl_facturas`(`IdFct`, `FechaFct`, `FechaVFct`, `ClienteFct`, `IvaFct`, `DescuentoFct`, `ObsFct`) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try{
            PreparedStatement pst = con.prepareStatement(Consulta);
            pst.setInt(1, Id);
            pst.setString(2, Fecha);
            pst.setString(3, FechaV);
            pst.setInt(4, Cliente);
            pst.setDouble(5, Iva);
            pst.setDouble(6, Descuento);
            pst.setString(7, Obs);
        }catch(Exception ex){
            System.out.println("Error"+ex);
        }
    }//GEN-LAST:event_BtnGuardarActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        int fila = Tabla.getSelectedRow();
        
        if(fila >= 0){
            int Line = (int) Tabla.getValueAt(fila, 0);
            int IdPrd = (int) Tabla.getValueAt(fila, 2);
            int Cantidad = (int) Tabla.getValueAt(fila, 3);
            boolean rs = false;
            Conexion cn = new Conexion();
            Connection con;
            con = cn.conexion();
            String Consulta = "DELETE FROM `tbl_prdfactura` WHERE IdLinea = '"+Line+"'";
            try{
                PreparedStatement pst = con.prepareStatement(Consulta);
                rs = pst.executeUpdate() == 1;
                UpdateCantidad(IdPrd, Cantidad, "Eliminar");
                PrepararTabla();
            }catch(Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void BtnImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnImprimirActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnImprimirActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnAdd;
    private javax.swing.JButton BtnGuardar;
    private javax.swing.JButton BtnImprimir;
    private javax.swing.JComboBox CmbClientes;
    private javax.swing.JComboBox CmbIva;
    private javax.swing.JComboBox CmbPrd;
    private javax.swing.JLabel LblDesc;
    private javax.swing.JLabel LblIVA;
    private javax.swing.JLabel LblSubtotal;
    private javax.swing.JLabel LblTotal;
    private javax.swing.JPopupMenu PopupSelect;
    private javax.swing.JTable Tabla;
    private javax.swing.JTextField TxtCantidad;
    private javax.swing.JTextField TxtDescuento;
    private javax.swing.JTextField TxtFecha;
    private javax.swing.JTextField TxtIdCliente;
    private javax.swing.JTextField TxtIdPrd;
    private javax.swing.JTextField TxtNFct;
    private javax.swing.JTextArea TxtObs;
    private javax.swing.JTextField TxtPrecio;
    private javax.swing.JTextField TxtVence;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
