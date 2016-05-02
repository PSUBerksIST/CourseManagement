/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IST261DesktopPaneDemo;

import java.awt.BorderLayout;
import java.awt.Component;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import static java.util.Collections.list;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import net.proteanit.sql.DbUtils;


public class jpAddAssignmentsToClass extends javax.swing.JPanel {

    private Connection dbConnection;
    private Statement st;
    
    List<Integer> selectedAssignmentIDs = new ArrayList<Integer>();
    private int intSelectedClassID;
    
    /**
     * Creates new form jpAddAssignmentsToClass
     */
    
    public jpAddAssignmentsToClass() {
        initComponents();
    }
    
    public jpAddAssignmentsToClass(int classID, Connection inConnection) {
        initComponents();
        setdbConnection(inConnection);
        intSelectedClassID = classID;
        setAssignments();
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
    
    /**
     * setAssignments.
     * @param intSelectedClassID
     */
    public void setAssignments()
    {
        // Let the programmer know what's going on
        System.out.println("[jpAddAssignmentsToCourse] setAssignments fired for ClassID: " + intSelectedClassID);
        
        try {

            DefaultTableModel model = (DefaultTableModel) jtAssignments.getModel();

            // Reset the JTable in case we are coming back a second time
            int rowCount = model.getRowCount();
            for (int i = rowCount - 1; i >= 0; i--) {
                model.removeRow(i);
            }
            model.setColumnCount(0);
            model.setRowCount(0);
            
            // Create our columns
            model.addColumn("Selected");
            model.addColumn("ID");
            model.addColumn("Name");
            model.addColumn("Description");
            model.addColumn("Points");
            
            // Set our model and also create our listeners
            jtAssignments.getModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {

                // On a table change update our local store of selectedAssignmentIDs
                    for(int i = 0; i < jtAssignments.getModel().getRowCount(); i++)
                    {

                        int assignmentId = Integer.parseInt(jtAssignments.getModel().getValueAt(i,1).toString());

                        if ((Boolean) jtAssignments.getModel().getValueAt(i,0))
                        {  

                            System.out.println("Selected ID: " + assignmentId);

                            // Add the ID if we do not have it already
                            if(!selectedAssignmentIDs.contains(assignmentId))
                            {
                                selectedAssignmentIDs.add(assignmentId);
                            }

                        }
                        else
                        {

                            System.out.println("Selected ID: " + assignmentId);

                            for (Iterator<Integer> iterator = selectedAssignmentIDs.iterator(); iterator.hasNext(); ) {
                                Integer id = iterator.next();
                                if (id == assignmentId) 
                                {
                                    iterator.remove();
                                }
                            }

                      }

                    }     
                    
                    // Show the programmer what IDs are selected
                    System.out.println(selectedAssignmentIDs);
                }

            });
            
            // JTable will make our checkboxes for us
            TableColumn tc = jtAssignments.getColumnModel().getColumn(0);
            tc.setCellEditor(jtAssignments.getDefaultEditor(Boolean.class));  
            tc.setCellRenderer(jtAssignments.getDefaultRenderer(Boolean.class)); 
            
            // Result Set 
            ResultSet result = st.executeQuery("SELECT Assignments.id AS ID, Assignments.ShortName AS Name, Assignments.Description, Assignments.MaximumPoints AS Points,\n" +
                "CASE WHEN Assignments.ID = ClassAssignmentLink.FKAssignmentID THEN 1 ELSE -1 END AS 'Selected'\n" +
                "FROM Assignments \n" +
                "LEFT JOIN ClassAssignmentLink ON ClassAssignmentLink.FKClassID = " + intSelectedClassID);

            int i = 0;
            while (result.next()) 
            {
                
                // SQLite won't do Booleans so lets convert it to one
                boolean b = (Integer.parseInt(result.getString("Selected")) != -1);
                // Add our row to the JTable
                model.addRow(new Object[]{ b, result.getString("ID"), result.getString("Name"), result.getString("Description"), result.getString("Points")});
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

        jScrollPane1 = new javax.swing.JScrollPane();
        jtAssignments = new javax.swing.JTable();
        btnAddAssignments = new javax.swing.JButton();

        jtAssignments.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                true, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jtAssignments);

        btnAddAssignments.setText("Update Active Assignments");
        btnAddAssignments.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddAssignmentsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnAddAssignments)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                .addComponent(btnAddAssignments))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddAssignmentsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddAssignmentsActionPerformed
        try {
            System.out.println("Add Selected Assignments fired! Class ID: " + intSelectedClassID);
            
            // We are going to take selectedAssignmentIDs and add assign them to intSelectedClassID
            // But first we need to drop all previously assigned assignments
            st.execute("DELETE FROM ClassAssignmentLink WHERE FKClassID = " + intSelectedClassID);
            
            // Now lets add all assignments from our list to the link table
            for (Iterator<Integer> iterator = selectedAssignmentIDs.iterator(); iterator.hasNext(); ) {
                Integer id = iterator.next();
                st.execute("INSERT INTO ClassAssignmentLink (FKClassID,FKAssignmentID) VALUES (" + intSelectedClassID + "," + id + ")");
            }
            
            // Let the user know we have taken care of it
            JFrame PopUp = new JFrame();
            JOptionPane.showMessageDialog(PopUp,"Assignments Updated!");  
            
        } catch (SQLException ex) {
            Logger.getLogger(jpAddAssignmentsToClass.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//GEN-LAST:event_btnAddAssignmentsActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddAssignments;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jtAssignments;
    // End of variables declaration//GEN-END:variables
}
