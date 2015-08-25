/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Formularios;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import static jdk.nashorn.internal.runtime.JSType.isNumber;

/**
 *Tabla.setDefaultRenderer (Object.class, new ColorTable());
 * @author Nicolas
 */
public class ColorTable extends DefaultTableCellRenderer{
    private Component Com;
    int c = 5;
    public Component getTableCellRendererComponent (JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){
        setEnabled(table == null || table.isEnabled());
        
        setBackground(Color.white);//color de fondo
        table.setForeground(Color.black);//color de texto
        setHorizontalAlignment(2);//derecha
        
        if(String.valueOf(table.getValueAt(row,column)).equals("Bajo")){
            setBackground(Color.red); 
        }
        if(String.valueOf(table.getValueAt(row,column)).equals("Medio")){
            setBackground(Color.orange); 
        }
        if(String.valueOf(table.getValueAt(row,column)).equals("Alto")){
            setBackground(Color.green); 
        }
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        return this;
    }
    
}
