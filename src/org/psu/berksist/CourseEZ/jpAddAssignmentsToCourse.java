/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.psu.berksist.CourseEZ;

import java.awt.BorderLayout;
import java.awt.Component;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
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

public class jpAddAssignmentsToCourse extends javax.swing.JPanel {

    private Connection dbConnection;
    private Statement st;
    
    List<Integer> selectedAssignmentIDs = new ArrayList<Integer>();
    private int intSelectedCourseID;
    
    /**
     * Creates new form jpAddAssignmentsToCourse
     */
    public jpAddAssignmentsToCourse() {
        initComponents();
    }
    
    public jpAddAssignmentsToCourse(int classID, Connection inConnection) {
        
        initComponents();
        setdbConnection(inConnection);
        intSelectedCourseID = classID;
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
    
    public void setAssignments()
    {
        // Let the programmer know what's going on
        System.out.println("[jpAddAssignmentsToCourse] setAssignments fired for CourseID: " + intSelectedCourseID);
        
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
            model.addColumn("Select");
            model.addColumn("ID");
            model.addColumn("Name");
            model.addColumn("Description");
            model.addColumn("Points");
            model.addColumn("Group");
            
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
            ResultSet result = st.executeQuery("SELECT Assignments.id AS ID, Assignments.ShortName AS Name, Assignments.Description, Assignments.GroupAssignment AS 'Group', Assignments.MaximumPoints AS Points,\n" +
                "CASE WHEN Assignments.ID = CourseAssignmentLink.FKAssignmentID THEN 1 ELSE -1 END AS 'Select'\n" +
                "FROM Assignments \n" +
                "LEFT JOIN CourseAssignmentLink ON CourseAssignmentLink.FKCourseID = " + intSelectedCourseID);

            int i = 0;
            while (result.next()) 
            {
                
                // SQLite won't do Booleans so lets convert it to one
                boolean b = (Integer.parseInt(result.getString("Select")) != -1);
                System.out.println(result.getString("Group"));
                
                boolean g = false;
                if(result.getString("Group") == null || Integer.parseInt(result.getString("Group")) < 1)
                {
                   g = false;
                }
                else
                {
                    g = true;
                }
                
                //boolean g = (Integer.parseInt(result.getString("Group")) != 1);
                String group = (g) ? "no" : "yes";
                // Add our row to the JTable
                model.addRow(new Object[]{ b, result.getString("ID"), result.getString("Name"), result.getString("Description"), result.getString("Points"), group});
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
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Select", "ID", "Name", "Description", "Points", "Group"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                true, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jtAssignments);

        btnAddAssignments.setText("Add Assignments");
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
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 245, Short.MAX_VALUE)
                .addComponent(btnAddAssignments))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnAddAssignments)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddAssignmentsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddAssignmentsActionPerformed
        
        try {
            System.out.println("Add Selected Assignments fired! Course ID: " + intSelectedCourseID);
            
            st.execute("DELETE FROM CourseAssignmentLink WHERE FKCourseID = " + intSelectedCourseID);
            
            // Now lets add all assignments from our list to the link table
            for (Iterator<Integer> iterator = selectedAssignmentIDs.iterator(); iterator.hasNext(); ) {
                Integer id = iterator.next();
                st.execute("INSERT INTO CourseAssignmentLink (FKCourseID,FKAssignmentID) VALUES (" + intSelectedCourseID + "," + id + ")");
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
