/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IST261DesktopPaneDemo;

import java.awt.Dimension;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author Nathan
 */
public class jpResources extends javax.swing.JPanel {

    List<Integer> selectedResources = new ArrayList<Integer>();
    
    /**
     * Creates new form jpDocument
     */
    
    private Connection dbConnection;
    private Statement st;
    public jpResources() {
        initComponents();
    }

    public jpResources(Connection inConnection){
        initComponents();
        setdbConnection(inConnection);;
        setResources();
        
    }
    
    // This sets the local datbase connection
    private void setdbConnection(Connection inConnection){
        dbConnection = inConnection;
        try {             
            st = dbConnection.createStatement();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
    
    public void setResources()
    {
        try {

            DefaultTableModel model = (DefaultTableModel) jtResources.getModel();

            // Reset the JTable in case we are coming back a second time
            int rowCount = model.getRowCount();
            for (int i = rowCount - 1; i >= 0; i--) {
                model.removeRow(i);
            }
            model.setColumnCount(0);
            model.setRowCount(0);
            
            // Create our columns
            model.addColumn("Select");
            model.addColumn("ID");
            //model.addColumn("Name");
            model.addColumn("Description");
           // model.addColumn("Points");
           // model.addColumn("Group");
            
            // Set our model and also create our listeners
            jtResources.getModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {

                // On a table change update our local store of selectedAssignmentIDs
                    for(int i = 0; i < jtResources.getModel().getRowCount(); i++)
                    {

                        int ResourcesId = Integer.parseInt(jtResources.getModel().getValueAt(i,1).toString());

                        if ((Boolean) jtResources.getModel().getValueAt(i,0))
                        {  

                            System.out.println("Selected ID: " + ResourcesId);

                            // Add the ID if we do not have it already
                            if(!selectedResources.contains(ResourcesId))
                            {
                                selectedResources.add(ResourcesId);
                            }

                        }
                        else
                        {

                            System.out.println("Selected ID: " + ResourcesId);

                            for (Iterator<Integer> iterator = selectedResources.iterator(); iterator.hasNext(); ) {
                                Integer id = iterator.next();
                                if (id == ResourcesId) 
                                {
                                    iterator.remove();
                                }
                            }

                      }

                    }     
                    
                    // Show the programmer what IDs are selected
                    System.out.println(selectedResources);
                }

            });
            
            // JTable will make our checkboxes for us
            TableColumn tc = jtResources.getColumnModel().getColumn(0);
            tc.setCellEditor(jtResources.getDefaultEditor(Boolean.class));  
            tc.setCellRenderer(jtResources.getDefaultRenderer(Boolean.class)); 
            
            // Result Set 
            ResultSet result = st.executeQuery("SELECT -1 AS 'Select', ID, Description FROM Resources order by ID asc;");

            int i = 0;
            while (result.next()) 
            {
                
                // SQLite won't do Booleans so lets convert it to one
                boolean b = (Integer.parseInt(result.getString("Select")) != -1);
               // boolean g = (Integer.parseInt(result.getString("Group")) != 1);
               // String group = (g) ? "no" : "yes";
                // Add our row to the JTable
                model.addRow(new Object[]{ b, result.getString("ID"), result.getString("Description")});
                // Authorize the checkbox to be editable
                model.isCellEditable(i, 0);
                i++;   
                
            }

        }
        catch (SQLException ex) 
        {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
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

        jlDocuments = new javax.swing.JLabel();
        jbAddResources = new javax.swing.JButton();
        jbDeleteDocument = new javax.swing.JButton();
        jbRefresh = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtResources = new javax.swing.JTable();

        jlDocuments.setText("Resources");

        jbAddResources.setText("Add Resources");
        jbAddResources.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbAddResourcesActionPerformed(evt);
            }
        });

        jbDeleteDocument.setText("Delete");
        jbDeleteDocument.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbDeleteDocumentActionPerformed(evt);
            }
        });

        jbRefresh.setText("Refresh");
        jbRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbRefreshActionPerformed(evt);
            }
        });

        jtResources.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Select", "ID", "Description"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                true, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jtResources);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(jlDocuments))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(jbAddResources)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbDeleteDocument)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbRefresh)))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlDocuments)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbAddResources)
                    .addComponent(jbDeleteDocument)
                    .addComponent(jbRefresh))
                .addContainerGap(102, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jbAddResourcesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbAddResourcesActionPerformed
        JPanel AddResources = new jpAddResources(dbConnection);
        AddResources.setName("Add Resources");
        CreateFrame(AddResources);
        // TODO add your handling code here:
    }//GEN-LAST:event_jbAddResourcesActionPerformed

    private void jbDeleteDocumentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbDeleteDocumentActionPerformed
             
        if(!selectedResources.isEmpty())
        {
            String options[] = {"Yes","No"};

            int PromptResult = JOptionPane.showOptionDialog(null,"Are you sure you want to delete?","Resources",JOptionPane.DEFAULT_OPTION,JOptionPane.WARNING_MESSAGE,null,options,options[1]);
            if(PromptResult==JOptionPane.YES_OPTION)
            {

                try {
                    System.out.println("Delete Selected Resources fired!");


                    if(!selectedResources.isEmpty())
                    {
                        for (Iterator<Integer> iterator = selectedResources.iterator(); iterator.hasNext(); ) {
                            Integer id = iterator.next();
                            iterator.remove();
                            st.execute("DELETE FROM Resources WHERE ID = " + id);
                        }
                    }

                    setResources();


                } catch (SQLException ex) {
                    Logger.getLogger(jpResources.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        else
        {
            JFrame PopUp = new JFrame();
            JOptionPane.showMessageDialog(PopUp,"Select Resources first!"); 
        }
                                            

        // TODO add your handling code here:
    }//GEN-LAST:event_jbDeleteDocumentActionPerformed

    private void jbRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbRefreshActionPerformed
        setResources();
        // TODO add your handling code here:
    }//GEN-LAST:event_jbRefreshActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton jbAddResources;
    private javax.swing.JButton jbDeleteDocument;
    private javax.swing.JButton jbRefresh;
    private javax.swing.JLabel jlDocuments;
    private javax.swing.JTable jtResources;
    // End of variables declaration//GEN-END:variables
  private void CreateFrame(JPanel inPanel) {
                //  intWindowCounter++;
      JDialog jd = new JDialog();
      jd.add(inPanel);
      jd.pack();
      jd.setModal(true);
      jd.setVisible(true);
  }//CreateFrame
}
