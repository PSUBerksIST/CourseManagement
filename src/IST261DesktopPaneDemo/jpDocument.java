/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IST261DesktopPaneDemo;

import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Nathan
 */
public class jpDocument extends javax.swing.JPanel {

    /**
     * Creates new form jpDocument
     */
    public jpDocument() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jlDocuments = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jbAddDocument = new javax.swing.JButton();
        jbDeleteDocument = new javax.swing.JButton();
        jbRefresh = new javax.swing.JButton();
        jbSave = new javax.swing.JButton();

        jlDocuments.setText("Documents");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"Making Netbeans protable", "details", "Link to file"},
                {"Syllabus", "details", "Link to file"},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Name", "Description", "File"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jbAddDocument.setText("Add Document");
        jbAddDocument.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbAddDocumentActionPerformed(evt);
            }
        });

        jbDeleteDocument.setText("Delete");

        jbRefresh.setText("Refresh");

        jbSave.setText("Save");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jbAddDocument)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbDeleteDocument)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbRefresh)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbSave))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlDocuments))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlDocuments)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbAddDocument)
                    .addComponent(jbDeleteDocument)
                    .addComponent(jbRefresh)
                    .addComponent(jbSave))
                .addContainerGap(27, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jbAddDocumentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbAddDocumentActionPerformed
//        JPanel AddDocument = new jpAddDocument();
//        AddDocument.setName("Add Document");
//        CreateFrame(AddDocument);
//        // TODO add your handling code here:
    }//GEN-LAST:event_jbAddDocumentActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JButton jbAddDocument;
    private javax.swing.JButton jbDeleteDocument;
    private javax.swing.JButton jbRefresh;
    private javax.swing.JButton jbSave;
    private javax.swing.JLabel jlDocuments;
    // End of variables declaration//GEN-END:variables
//    private void CreateFrame(JPanel inPanel) {
//                //  intWindowCounter++;
//        JFrame jifTemp = new jfTempFrames(inPanel.getName());// +"" + intWindowCounter,true,true,true,true);
//        
//        //JPanel jpTemp = new jpClass();
//        inPanel.setPreferredSize(new Dimension(400, 400));
//        jifTemp.add(inPanel);
//        System.out.println(this.getClass());
//        jifTemp.pack();
//        //jdpMain.add(jifTemp);
//        jifTemp.setVisible(true);
//        
//        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }//CreateFrame
}