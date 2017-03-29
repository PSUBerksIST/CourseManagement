/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.psu.berksist.CourseEZ;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Nathan
 */
public class jpAssignment extends javax.swing.JPanel {

    List<Integer> selectedAssignmentIDs = new ArrayList<>();
    
    /**
     * Creates new form jpAssignment
     */
    private Connection dbConnection;
    private Statement st;
    
    private TableChangeListener tcListener = new TableChangeListener();
    private SortRows tableSort;
    
    public jpAssignment()
    {
        initComponents();

    }
    
    public jpAssignment(Connection inConnection){
        initComponents();
        setdbConnection(inConnection);
        
        tableSort = new SortRows(jtAssignments);
        tableSort.setColDirection(jtAssignments.getColumnModel()
                .getColumnIndex("Name"), SortRows.ASC);
        
        setAssignments();
        
        jtAssignments.getModel().addTableModelListener(tcListener);
        
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
        
        tableSort.setCurrentSort((List<RowSorter.SortKey>) jtAssignments.getRowSorter().getSortKeys());
        
        try {
            
            DefaultTableModel model = (DefaultTableModel) jtAssignments.getModel();

            // Reset the JTable in case we are coming back a second time
            model.setRowCount(0);
            model.setColumnCount(0);
            
            // Create our columns
            model.addColumn("Select");
            model.addColumn("ID");
            model.addColumn("Name");
            model.addColumn("Description");
            model.addColumn("Points");
            model.addColumn("Group");
            
            // Set column widths so everything can fit properly
            jtAssignments.getColumnModel().getColumn(0).setPreferredWidth(60);
            jtAssignments.getColumnModel().getColumn(1).setPreferredWidth(35);
            jtAssignments.getColumnModel().getColumn(2).setPreferredWidth(140);
            jtAssignments.getColumnModel().getColumn(3).setPreferredWidth(180);
            jtAssignments.getColumnModel().getColumn(4).setPreferredWidth(60);
            jtAssignments.getColumnModel().getColumn(5).setPreferredWidth(60);
            
            
            // JTable will make our checkboxes for us
            //TableColumn tc = jtAssignments.getColumnModel().getColumn(0);
            //tc.setCellEditor(jtAssignments.getDefaultEditor(Boolean.class));  
            //tc.setCellRenderer(jtAssignments.getDefaultRenderer(Boolean.class));
            
            // Prepared Statement
            PreparedStatement allAssigns = dbConnection.prepareStatement(
                    "SELECT intID, vchrShortName AS Name, vchrDescription, realMaximumPoints AS Points, boolGroupAssignment AS 'Group' "
                            + "FROM Assignment;");
            
            ResultSet result = allAssigns.executeQuery();
            
            while (result.next()) 
            {
                boolean g;
                if(result.getString("Group") == null) // shouldn't be null, but just in case
                {
                    g = false;
                }
                else
                {
                    g = result.getBoolean("Group");
                }
                
                String strGroup = g ? "Yes" : "No";
                
                boolean b = selectedAssignmentIDs.contains(result.getInt("intID"));
                
                // Add our row to the JTable
                model.addRow(new Object[]{ b, result.getInt("intID"), result.getString("Name"), 
                    result.getString("vchrDescription"), result.getInt("Points"), strGroup});
                
            }
            
            tableSort.applyCurrentSort();
            
        }
        catch (SQLException ex) 
        {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private class TableChangeListener implements TableModelListener{

        @Override
        public void tableChanged(TableModelEvent e) {
            // On a table change update our local store of selectedAssignmentIDs
            if (e.getColumn() == 0){

                int assignmentID = Integer.parseInt(jtAssignments.getModel().getValueAt(e.getLastRow(),1).toString());

                if ((boolean) jtAssignments.getModel().getValueAt(e.getLastRow(), 0)){

                    if (!selectedAssignmentIDs.contains(assignmentID)){
                        selectedAssignmentIDs.add(assignmentID);
                    }
                } else {
                    if (selectedAssignmentIDs.contains(assignmentID)){
                        selectedAssignmentIDs.remove(selectedAssignmentIDs.indexOf(assignmentID));
                    }

                }

                // Show the programmer what IDs are selected
                System.out.println(selectedAssignmentIDs);

            }
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

        jbAddAssignment = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtAssignments = new javax.swing.JTable();
        jbDelete = new javax.swing.JButton();
        jbRefresh = new javax.swing.JButton();
        jlAssignments = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(540, 350));

        jbAddAssignment.setText("Create Assignment");
        jbAddAssignment.setPreferredSize(new java.awt.Dimension(140, 32));
        jbAddAssignment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbAddAssignmentActionPerformed(evt);
            }
        });

        jtAssignments.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Select", "ID", "Name", "Description", "Points", "Group"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Boolean.class, java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class, java.lang.Integer.class, java.lang.Object.class
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
        jtAssignments.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        jScrollPane1.setViewportView(jtAssignments);

        jbDelete.setText("Delete");
        jbDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbDeleteActionPerformed(evt);
            }
        });

        jbRefresh.setText("Refresh");
        jbRefresh.setPreferredSize(new java.awt.Dimension(140, 32));
        jbRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbRefreshActionPerformed(evt);
            }
        });

        jlAssignments.setText("Assignments:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 375, Short.MAX_VALUE)
                            .addComponent(jlAssignments))
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jbAddAssignment, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbDelete, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jlAssignments)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbAddAssignment, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbDelete)
                    .addComponent(jbRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(78, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jbAddAssignmentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbAddAssignmentActionPerformed
        JDialog jdAddAssignment = new JDialog();
        JPanel AddAssignment = new jpAddAssignment(dbConnection);
        jdAddAssignment.add(AddAssignment);
        jdAddAssignment.setSize(565, 580);
        jdAddAssignment.setVisible(true);
        
        // on close reset the table to refresh 
        jdAddAssignment.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        jdAddAssignment.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                System.out.println("Window closed!");
                // Update the table
                setAssignments();
            }
        });
    }//GEN-LAST:event_jbAddAssignmentActionPerformed

    private void jbDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbDeleteActionPerformed
     
        if(!selectedAssignmentIDs.isEmpty())
        {
            String options[] = {"Yes","No"};

            int PromptResult = JOptionPane.showOptionDialog(null,"Are you sure you want to delete?","Assignments",JOptionPane.DEFAULT_OPTION,JOptionPane.WARNING_MESSAGE,null,options,options[1]);
            if(PromptResult==JOptionPane.YES_OPTION)
            {

                try {
                    System.out.println("Delete Selected Assignments fired!");


                    if(!selectedAssignmentIDs.isEmpty())
                    {
                        for (Iterator<Integer> iterator = selectedAssignmentIDs.iterator(); iterator.hasNext(); ) {
                            Integer id = iterator.next();
                            iterator.remove();
                            st.execute("DELETE FROM Assignment WHERE intID = " + id);
                            
                            // Need to also delete from link tables - RQZ
                            st.execute("DELETE FROM Class_Assignment WHERE FKAssignment_intID = " + id);
                            st.execute("DELETE FROM Course_Assignment WHERE FKAssignment_intID = " + id);
                            
                        }
                    }

                    setAssignments();


                } catch (SQLException ex) {
                    Logger.getLogger(jpAddAssignmentsToClass.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        else
        {
            JFrame PopUp = new JFrame();
            JOptionPane.showMessageDialog(PopUp,"Select assignments first!"); 
        }
        
    }//GEN-LAST:event_jbDeleteActionPerformed

    private void jbRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbRefreshActionPerformed
        setAssignments();
    }//GEN-LAST:event_jbRefreshActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton jbAddAssignment;
    private javax.swing.JButton jbDelete;
    private javax.swing.JButton jbRefresh;
    private javax.swing.JLabel jlAssignments;
    private javax.swing.JTable jtAssignments;
    // End of variables declaration//GEN-END:variables

}
