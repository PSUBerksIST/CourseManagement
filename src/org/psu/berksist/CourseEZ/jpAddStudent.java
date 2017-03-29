/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.psu.berksist.CourseEZ;

import java.awt.Window;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author IST 261
 * 
 * 
 * 
 ******************* MODIFICATION LOG ******************************************
 * 
 * Table filter (http://docs.oracle.com/javase/tutorial/uiswing/components/table.html#sorting)
 * Table Select Column (@dar5417)                  
 * 
 * 
 * 
 * 
 * 
 * 
 * ****************** ADDITIONAL LIBRARIES NEEDED ******************************
 * 
 *      NAME            SOURCE                      USE
 * 
 *      N/A
 */

public class jpAddStudent extends javax.swing.JPanel {

    private Connection dbConnection;
    private Statement st;
    
    List<Integer> selectedIDs = new ArrayList<Integer>();
    private int intClassID;
    
    private TableRowSorter sorter;
    
    /**
     * Creates new form jpAddStudent
     */
    public jpAddStudent() {
        initComponents();
    }
    
    public jpAddStudent(int id, Connection inConnection) {
        
        initComponents();
        setdbConnection(inConnection);
        intClassID = id;
        setStudents();
        
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
     * Update the row filter regular expression from the expression in
     * the text box.
     */
    private void newFilter() {
        RowFilter rf = null;
        //If current expression doesn't parse, don't update.
        try {
            // "(?i)" indicates case-insensitivity
            rf = RowFilter.regexFilter("(?i)" + txtFilter.getText(), 1, 2, 3, 4);
        } catch (java.util.regex.PatternSyntaxException e) {
            return;
        }
        sorter.setRowFilter(rf);
    }
    
    private void setStudents()
    {
        System.out.println("setStudents fired! Class ID: " + intClassID);
       
        // Grab the courses from the database and display them
        try {
            
            DefaultTableModel model = (DefaultTableModel) jtStudents.getModel();

            // Reset the JTable in case we are coming back a second time
            int rowCount = model.getRowCount();
            for (int i = rowCount - 1; i >= 0; i--) {
                model.removeRow(i);
            }
            model.setColumnCount(0);
            model.setRowCount(0);
            
            // Set the filter
            sorter = new TableRowSorter<>(model);
   
            jtStudents.setRowSorter(sorter);
            jtStudents.getSelectionModel().addListSelectionListener(
                new ListSelectionListener() {
                    public void valueChanged(ListSelectionEvent event) {
                        int viewRow = jtStudents.getSelectedRow();
                        if (viewRow < 0) {
                            //Selection got filtered away.
                        } else {
                            int modelRow = 
                                jtStudents.convertRowIndexToModel(viewRow);
                        }
                    }
                }
            );
            
            txtFilter.getDocument().addDocumentListener(
               new DocumentListener() {
                   public void changedUpdate(DocumentEvent e) {
                       newFilter();
                   }
                   public void insertUpdate(DocumentEvent e) {
                       newFilter();
                   }
                   public void removeUpdate(DocumentEvent e) {
                       newFilter();
                   }
               });
            
            // Create our columns
            model.addColumn("Select");
            model.addColumn("First Name");
            model.addColumn("Last Name");
            model.addColumn("Email");
            model.addColumn("Student ID");
            
            // Set our model and also create our listeners
            jtStudents.getModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {

                // On a table change update our local store of selectedAssignmentIDs
                    for(int i = 0; i < jtStudents.getModel().getRowCount(); i++)
                    {

                        int studentId = Integer.parseInt(jtStudents.getModel().getValueAt(i,4).toString());

                        if ((Boolean) jtStudents.getModel().getValueAt(i,0))
                        {  

                            //System.out.println("Selected ID: " + studentId);

                            // Add the ID if we do not have it already
                            if(!selectedIDs.contains(studentId))
                            {
                                selectedIDs.add(studentId);
                            }

                        }
                        else
                        {

                            //System.out.println("Selected ID: " + studentId);

                            for (Iterator<Integer> iterator = selectedIDs.iterator(); iterator.hasNext(); ) {
                                Integer id = iterator.next();
                                if (id == studentId) 
                                {
                                    iterator.remove();
                                }
                            }

                      }

                    }     
                    
                    // Show the programmer what IDs are selected
                    //System.out.println(selectedIDs);
                }

            });
            
            // JTable will make our checkboxes for us
            TableColumn tc = jtStudents.getColumnModel().getColumn(0);
            tc.setCellEditor(jtStudents.getDefaultEditor(Boolean.class));  
            tc.setCellRenderer(jtStudents.getDefaultRenderer(Boolean.class)); 
            
            // Result Set 
            /*ResultSet result = st.executeQuery("SELECT Student.FirstName, Student.LastName, Student.EmailName, Student.StudentID,\n" +
"                CASE WHEN Student.StudentID = ClassStudentLink.FKStudent THEN 1 ELSE -1 END AS 'Select'\n" +
"                FROM Student\n" +
"                LEFT JOIN ClassStudentLink ON ClassStudentLink.FKClass = " + intClassID);*/
            
            ResultSet result = st.executeQuery("SELECT Student.vchrFirstName, Student.vchrLastName, Student.vchrEmailName, Student.intID, " +
                    "CASE WHEN Student.intID = Class_Student.FKStudent_intID THEN 1 ELSE 0 END AS 'Select' " +
                    "FROM Student " +
                    "LEFT JOIN Class_Student ON Class_Student.FKStudent_intID = Student.intID " + 
                    "AND Class_Student.FKClass_intID = " + intClassID);
            

            while (result.next()) 
            {
                
                boolean b = result.getBoolean("Select");
                
                // Add our row to the JTable
                model.addRow(new Object[]{ b, result.getString("vchrFirstName"), result.getString("vchrLastName"), result.getString("vchrEmailName"), result.getString("intID")});
                
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
        jtStudents = new javax.swing.JTable();
        lblFilter = new javax.swing.JLabel();
        txtFilter = new javax.swing.JTextField();
        btnAddStudents = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        jtStudents.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Select", "First Name", "Last Name", "Email", "Student ID"
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
        jScrollPane1.setViewportView(jtStudents);

        lblFilter.setText("Filter:");

        btnAddStudents.setText("Add Students");
        btnAddStudents.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddStudentsActionPerformed(evt);
            }
        });

        jLabel1.setText("Select students you want in this class");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btnAddStudents))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblFilter)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtFilter))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 151, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblFilter)
                    .addComponent(txtFilter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnAddStudents))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddStudentsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddStudentsActionPerformed
        try {
            System.out.println("Add Selected Students fired! Course ID: " + intClassID);
            
            st.execute("DELETE FROM Class_Student WHERE FKClass_intID = " + intClassID);
            
            // Now lets add all assignments from our list to the link table
            for (Iterator<Integer> iterator = selectedIDs.iterator(); iterator.hasNext(); ) {
                Integer id = iterator.next();
                st.execute("INSERT INTO Class_Student (FKClass_intID, FKStudent_intID) VALUES (" + intClassID + ", " + id + ")");
            }
            
            // Let the user know we have taken care of it
            JFrame PopUp = new JFrame();
            JOptionPane.showMessageDialog(PopUp,"Students Updated!");  
            
            ((Window) getRootPane().getParent()).dispose();
            
        } catch (SQLException ex) {
            Logger.getLogger(jpAddAssignmentsToClass.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//GEN-LAST:event_btnAddStudentsActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddStudents;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jtStudents;
    private javax.swing.JLabel lblFilter;
    private javax.swing.JTextField txtFilter;
    // End of variables declaration//GEN-END:variables
}
