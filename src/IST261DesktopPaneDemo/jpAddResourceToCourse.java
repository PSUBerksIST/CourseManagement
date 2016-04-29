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
/**
 *
 * @author Nathan
 */
public class jpAddResourceToCourse extends javax.swing.JPanel {
    private Connection dbConnection;
    private Statement st;
    
    List<Integer> selectedResources = new ArrayList<Integer>();
    private int intSelectedCourseID;

    /**
     * Creates new form AddResourceToCourse
     */
    public jpAddResourceToCourse() {
        initComponents();
    }
    public jpAddResourceToCourse(int classID, Connection inConnection) {
        
        initComponents();
        setdbConnection(inConnection);
        intSelectedCourseID = classID;
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
        // Let the programmer know what's going on
        System.out.println("[jpAddResourceToCourse] setResources fired for CourseID: " + intSelectedCourseID);
        
        try {

            DefaultTableModel model = (DefaultTableModel) jtResources.getModel();

            // Reset the JTable in case we are coming back a second time
            model.setColumnCount(0);
            model.setRowCount(0);
            
            // Create our columns
            model.addColumn("Select");
            model.addColumn("ID");
            //model.addColumn("Name");
            model.addColumn("Description");
           // model.addColumn("Points");
            //model.addColumn("Group");
            
            // Set our model and also create our listeners
            jtResources.getModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {

                // On a table change update our local store of selectedAssignmentIDs
                    for(int i = 0; i < jtResources.getModel().getRowCount(); i++)
                    {
                       // System.out.println("jtResources.getModel().getRowCount() = " + jtResources.getModel().getRowCount());

                        int ResourcesId = Integer.parseInt(jtResources.getModel().getValueAt(i,1).toString());
                        //System.out.println(selectedResources);
                        if ((Boolean) jtResources.getModel().getValueAt(i,0))
                        {  

                          //  System.out.println("Selected ID1: " + ResourcesId);

                            // Add the ID if we do not have it already
                            if(!selectedResources.contains(ResourcesId))
                            {
                                selectedResources.add(ResourcesId);
                            }

                        }
                        else
                        {

                        //    System.out.println("Selected ID2: " + ResourcesId);

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
                   // System.out.println(selectedResources);
                }

            });
            
            // JTable will make our checkboxes for us
            TableColumn tc = jtResources.getColumnModel().getColumn(0);
            tc.setCellEditor(jtResources.getDefaultEditor(Boolean.class));  
            tc.setCellRenderer(jtResources.getDefaultRenderer(Boolean.class)); 
            
            // Result Set 
            ResultSet result1  = st.executeQuery("Select FKResourcesID FROM CourseResourcesLink WHERE FKCourseID = "+intSelectedCourseID+" order by FKResourcesID asc;");
            String ResourceIDForWhere = "";
            int Counter =0;
            while(result1.next()){
                Counter = Counter+1;
                int ResourceID = result1.getInt("FKResourcesID");
                if(Counter <2){
                    ResourceIDForWhere = "ID != "+ResourceID;
                }
                else{
                    ResourceIDForWhere = ResourceIDForWhere + " AND ID != "+ResourceID;
                }
                Statement st1 = dbConnection.createStatement();
                ResultSet result = st1.executeQuery("SELECT ID, Description,\n" +
                    "CASE WHEN Resources.ID = "+ResourceID+" THEN 1 ELSE -1 END AS 'Select'\n" +
                    "FROM Resources Where ID = "+ResourceID+" order by ID asc;");

                int i = 0;
                while (result.next()) 
                {
                    // SQLite won't do Booleans so lets convert it to one
                    boolean b = (Integer.parseInt(result.getString("Select")) != -1);
                    model.addRow(new Object[]{ b, result.getString("ID"), result.getString("Description")});
                    // Authorize the checkbox to be editable
                    model.isCellEditable(i, 0);
                    i++;   

                }
            } 
            ResultSet result = st.executeQuery("SELECT -1 AS 'Select', ID, Description FROM Resources Where "+ResourceIDForWhere+" order by ID asc;");

                int i = 0;
                while (result.next()) 
                {
                    // SQLite won't do Booleans so lets convert it to one
                    boolean b = (Integer.parseInt(result.getString("Select")) != -1);
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

        jScrollPane2 = new javax.swing.JScrollPane();
        jtResources = new javax.swing.JTable();
        btnAddAssignments = new javax.swing.JButton();

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
        jScrollPane2.setViewportView(jtResources);

        btnAddAssignments.setText("Add Resources");
        btnAddAssignments.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddAssignmentsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnAddAssignments))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 354, Short.MAX_VALUE))
                .addGap(36, 36, 36))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnAddAssignments)
                .addContainerGap(87, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddAssignmentsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddAssignmentsActionPerformed

        try {
            System.out.println("Add Selected Resources fired! Course ID: " + intSelectedCourseID);

            st.execute("DELETE FROM CourseResourcesLink WHERE FKCourseID = " + intSelectedCourseID);

            // Now lets add all assignments from our list to the link table
            for (Iterator<Integer> iterator = selectedResources.iterator(); iterator.hasNext(); ) {
                Integer id = iterator.next();
                st.execute("INSERT INTO CourseResourcesLink (FKCourseID,FKResourcesID) VALUES (" + intSelectedCourseID + "," + id + ")");
            }

            // Let the user know we have taken care of it
            JFrame PopUp = new JFrame();
            JOptionPane.showMessageDialog(PopUp,"Resources Updated!");
            this.getTopLevelAncestor().setVisible(false);

        } catch (SQLException ex) {
            Logger.getLogger(jpAddResourceToCourse.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_btnAddAssignmentsActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddAssignments;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jtResources;
    // End of variables declaration//GEN-END:variables
}
