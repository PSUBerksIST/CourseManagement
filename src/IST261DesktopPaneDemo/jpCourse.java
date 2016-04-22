/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IST261DesktopPaneDemo;

import java.awt.Dimension;
import java.util.*;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author IST 261
 * 
 * 
 * 
 ******************* MODIFICATION LOG *****************************************
 * 
 * 2016 March 30   - added createCourseList()        - dar5417
 *                   created selectedCourse variable - dar5417
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
public class jpCourse extends JPanel{
    
    // Contain active course selection
    private int selectedCourse;
    
    // Database and init params
    private Connection dbConnection;
    private Statement st;
    private HashMap<Integer, Integer> courseIds = new HashMap<>();
    private static List<String> courses = new ArrayList<String>();
    
    // Assignment Tab Declarations
    List<Integer> selectedAssignmentIDs = new ArrayList<Integer>();
    
    /**
     * Creates new form jpCourse
     */
    public jpCourse() {
        
        initComponents();
        
    }
    
    public jpCourse(Connection inConnection) {
        
        initComponents();
        setdbConnection(inConnection);
        setCourseList();
        
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
     * createCourseList.
     * Builds an array of all courses and displays them in a jComboBox
     */
    private void setCourseList()
    {

        courses.clear();
        // Grab the courses from the database and display them
        try {
            ResultSet result = st.executeQuery("SELECT course.id, department.departmentname, course.number FROM course, department WHERE course.fkdepartment = department.id ORDER BY course.id asc");

            int i = 0;
            while (result.next()) {
                courses.add(result.getString(2) + " " + result.getString(3));
                courseIds.put(i, result.getInt(1));
                i++;
            }

            jcbCourse.setModel(new DefaultComboBoxModel(courses.toArray()));
           
            // By default set the selected item to be nothing
            // to force user selection
            jcbCourse.setSelectedItem(null);
            
        }
        catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    private void setAssignments()
    {
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
            ResultSet result = st.executeQuery("SELECT -1 AS 'Select', "
                    + "Assignments.id AS ID, Assignments.ShortName AS Name, "
                    + "Assignments.Description, Assignments.MaximumPoints AS Points, "
                    + "Assignments.GroupAssignment AS 'Group' "
                    + "FROM Assignments, "
                    + "CourseAssignmentLink "
                    + "WHERE Assignments.id = CourseAssignmentLink.FKAssignmentID "
                    + "AND CourseAssignmentLink.FKCourseID = " + selectedCourse);

            int i = 0;
            while (result.next()) 
            {
                
                // SQLite won't do Booleans so lets convert it to one
                boolean b = (Integer.parseInt(result.getString("Select")) != -1);
                boolean g = (Integer.parseInt(result.getString("Group")) != 1);
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

        jcbCourse = new javax.swing.JComboBox();
        jbAddCourse = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtAssignments = new javax.swing.JTable();
        btnAddAssignments = new javax.swing.JButton();
        btnDeleteAssignments = new javax.swing.JButton();
        btnRefreshAssignments = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jbEditCourse = new javax.swing.JButton();

        jcbCourse.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select Course", "Item 1", "Item 2", "Item 3", "Item 4" }));
        jcbCourse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbCourseActionPerformed(evt);
            }
        });

        jbAddCourse.setText("Add Course");
        jbAddCourse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbAddCourseActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 375, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 245, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Overview", jPanel1);

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

        btnDeleteAssignments.setText("Remove Selected");
        btnDeleteAssignments.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteAssignmentsActionPerformed(evt);
            }
        });

        btnRefreshAssignments.setText("Refresh");
        btnRefreshAssignments.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshAssignmentsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btnAddAssignments)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDeleteAssignments)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                        .addComponent(btnRefreshAssignments)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAddAssignments)
                    .addComponent(btnDeleteAssignments)
                    .addComponent(btnRefreshAssignments))
                .addGap(0, 42, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Assignments", jPanel2);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 375, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 245, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("People", jPanel3);

        jbEditCourse.setText("Edit Course");
        jbEditCourse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbEditCourseActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jbAddCourse)
                        .addGap(18, 18, 18)
                        .addComponent(jcbCourse, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jbEditCourse)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jcbCourse, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbAddCourse)
                    .addComponent(jbEditCourse))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jbAddCourseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbAddCourseActionPerformed
        JPanel AddCourse = new jpAddCourse(dbConnection);
        AddCourse.setName("Add Course");
        CreateFrame(AddCourse);
        // TODO add your handling code here:
    }//GEN-LAST:event_jbAddCourseActionPerformed

    /**
     * Course DropDown Listener.
     * Changes selectedCourse variable based on user selection
     * @param evt 
     */
    private void jcbCourseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbCourseActionPerformed
        // TODO add your handling code here:
        
        if(jcbCourse.getSelectedItem() != null)
        {
            selectedCourse = Integer.parseInt(courseIds.get(jcbCourse.getSelectedIndex()).toString());
            System.out.println("Active Course Selected: (int) " + selectedCourse);
            
            // An action has been performed. Update tabs!
            setAssignments();

        }
        

    }//GEN-LAST:event_jcbCourseActionPerformed

    private void jbEditCourseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbEditCourseActionPerformed
        JPanel EditCourse = new jpEditCourse(dbConnection, selectedCourse);
        EditCourse.setName("Edit Course");
        CreateFrame(EditCourse);
    }//GEN-LAST:event_jbEditCourseActionPerformed

    private void btnAddAssignmentsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddAssignmentsActionPerformed
        
        JDialog jdAddAssignments = new JDialog();
        JPanel AddAssignments = new jpAddAssignmentsToCourse(selectedCourse, dbConnection);
        jdAddAssignments.add(AddAssignments);
        jdAddAssignments.setSize(500, 400);
        jdAddAssignments.setVisible(true);
        
    }//GEN-LAST:event_btnAddAssignmentsActionPerformed

    private void btnRefreshAssignmentsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshAssignmentsActionPerformed
        
        setAssignments();
        
    }//GEN-LAST:event_btnRefreshAssignmentsActionPerformed

    private void btnDeleteAssignmentsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteAssignmentsActionPerformed
        try {
            System.out.println("Remove Selected Assignments fired! Course ID: " + selectedCourse);
            

            if(!selectedAssignmentIDs.isEmpty())
            {
                String options[] = {"Yes","No"};

                int PromptResult = JOptionPane.showOptionDialog(null,"Are you sure you want to remove?","Assignments",JOptionPane.DEFAULT_OPTION,JOptionPane.WARNING_MESSAGE,null,options,options[1]);
                if(PromptResult==JOptionPane.YES_OPTION)
                {
                    for (Iterator<Integer> iterator = selectedAssignmentIDs.iterator(); iterator.hasNext(); ) {
                        Integer id = iterator.next();
                        iterator.remove();
                        st.execute("DELETE FROM CourseAssignmentLink WHERE FKCourseID = " + selectedCourse + " AND FKAssignmentID = " + id);
                    }
                }
            }
            else
            {
                JFrame PopUp = new JFrame();
                JOptionPane.showMessageDialog(PopUp,"Select an assignment first!"); 
            } 
            
        } catch (SQLException ex) {
            Logger.getLogger(jpAddAssignmentsToClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnDeleteAssignmentsActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddAssignments;
    private javax.swing.JButton btnDeleteAssignments;
    private javax.swing.JButton btnRefreshAssignments;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JButton jbAddCourse;
    private javax.swing.JButton jbEditCourse;
    private javax.swing.JComboBox jcbCourse;
    private javax.swing.JTable jtAssignments;
    // End of variables declaration//GEN-END:variables

    
    private void CreateFrame(JPanel inPanel) {
        
        
                //  intWindowCounter++;
      JDialog jd = new JDialog();
      jd.add(inPanel);
      jd.pack();
      jd.setModal(true);
      jd.setVisible(true);
//                //  intWindowCounter++;
//        
//        JFrame jifTemp = new jfTempFrames(inPanel.getName());// +"" + intWindowCounter,true,true,true,true);
//        
//        //JPanel jpTemp = new jpClass();
//        inPanel.setPreferredSize(new Dimension(400, 400));
//        jifTemp.add(inPanel);
//        jifTemp.pack();
//        //jdpMain.add(jifTemp);
//        jifTemp.setVisible(true);
        
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }//CreateFrame

}
