/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IST261DesktopPaneDemo;

import java.awt.Dimension;
import java.sql.ResultSet;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
// Downloaded from http://www.4shared.com/file/69Se66yD/rs2xml.html
// or for IST261, download from ANGEL
// Test Push from Nathan
import net.proteanit.sql.DbUtils;

/**
 *
 * @author whb108
 */
public class jpTableDisplay extends javax.swing.JPanel {

    int intKeyColumn;
    /**
     * Creates new form jpTableDisplay
     */
    public jpTableDisplay(ResultSet rsIn, int intInKeyColumn, int[] arrHideCols) 
    {
        initComponents();
        TableModel tmResults = DbUtils.resultSetToTableModel(rsIn);
        jtDisplayTable.setModel(tmResults);
        intKeyColumn = intInKeyColumn;
        
        int intNumColsToHide = arrHideCols.length;
        
        if (intNumColsToHide > 0)
        {
            
        
        for(int intLCV = 0; intLCV < intNumColsToHide;intLCV++)
        {
           TableColumn column = jtDisplayTable.getColumnModel().getColumn(arrHideCols[intLCV]);
            System.out.println("Hiding column " + column.getHeaderValue().toString());     
    column.setMinWidth(0);
    column.setMaxWidth(0);
    column.setWidth(0);
    column.setPreferredWidth(0);
            
        } // for
            } // if there are columns to hide
        
        //TODO 
        //TODO set columns to appropriate width
      
        
        
      
       jtDisplayTable.setAutoCreateRowSorter(true);
       
       // jScrollPane1.setMaximumSize(new Dimension(500, 500));
        jScrollPane1.doLayout();
        jtDisplayTable.doLayout();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jtDisplayTable = new javax.swing.JTable();

        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                formComponentResized(evt);
            }
        });

        jtDisplayTable.setModel(new javax.swing.table.DefaultTableModel(
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
        jtDisplayTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        jScrollPane1.setViewportView(jtDisplayTable);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized
        // TODO add your handling code here:
        
        System.out.println("panel resized");
    }//GEN-LAST:event_formComponentResized


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jtDisplayTable;
    // End of variables declaration//GEN-END:variables
}
