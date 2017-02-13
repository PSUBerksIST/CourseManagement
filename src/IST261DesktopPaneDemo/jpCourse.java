/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IST261DesktopPaneDemo;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
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
    List<Integer> selectedResources = new ArrayList<Integer>();
    
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
    
    
    public void setResources(int inCourse)
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
            //model.addColumn("Points");
            //model.addColumn("Group");
            
            // Set our model and also create our listeners
            jtResources.getModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {

                // On a table change update our local store of selectedAssignmentIDs
                    for(int i = 0; i < jtResources.getModel().getRowCount(); i++)
                    {

                        int ResourceId = Integer.parseInt(jtResources.getModel().getValueAt(i,1).toString());

                        if ((Boolean) jtResources.getModel().getValueAt(i,0))
                        {  

                            System.out.println("Selected ID: " + ResourceId);

                            // Add the ID if we do not have it already
                            if(!selectedResources.contains(ResourceId))
                            {
                                selectedResources.add(ResourceId);
                            }

                        }
                        else
                        {

                            System.out.println("Selected ID: " + ResourceId);

                            for (Iterator<Integer> iterator = selectedResources.iterator(); iterator.hasNext(); ) {
                                Integer id = iterator.next();
                                if (id == ResourceId) 
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
            ResultSet result1 = st.executeQuery("Select FKResourcesID FROM CourseResourcesLink Where FKCourseID = "+inCourse+" order by FKResourcesID asc;");
            while (result1.next()){
                int ResourcesID  = result1.getInt("FKResourcesID");
                // Result Set 
                Statement st1 = dbConnection.createStatement();
                ResultSet result = st1.executeQuery("SELECT -1 AS 'Select', ID,  Description  FROM Resources Where ID = "+ResourcesID+" order by ID asc;");

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

        }
        catch (SQLException ex) 
        {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * setOverview() This function gives brief description about the course selected from the drop down list.
     * @param None
     * @return None
     * 
     */
    private void setOverview()
    {
       try
        {
            ResultSet rs = st.executeQuery("SELECT Number, Title, Description FROM Course WHERE ID = " + selectedCourse);
          
            while (rs.next())
            {
                jlNumber.setText("IST "+ rs.getString("Number"));
                jlTitle.setText(rs.getString("Title"));
                jtDescription.setText(rs.getString("Description"));
            }//while
          
        }//try
       
       catch (SQLException ex) 
        {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }//catch
    } //setOverview()

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
        jlNumber = new javax.swing.JLabel();
        jlTitle = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jtDescription = new javax.swing.JTextArea();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtAssignments = new javax.swing.JTable();
        btnAddAssignments = new javax.swing.JButton();
        btnDeleteAssignments = new javax.swing.JButton();
        btnRefreshAssignments = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jbAddResource = new javax.swing.JButton();
        jbDeleteDocument = new javax.swing.JButton();
        jbRefresh = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtResources = new javax.swing.JTable();
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

        jtDescription.setEditable(false);
        jtDescription.setColumns(20);
        jtDescription.setLineWrap(true);
        jtDescription.setRows(5);
        jtDescription.setWrapStyleWord(true);
        jScrollPane4.setViewportView(jtDescription);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addComponent(jlNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jlTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 374, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jlNumber, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jlTitle, javax.swing.GroupLayout.DEFAULT_SIZE, 24, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE))
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
        if (jtAssignments.getColumnModel().getColumnCount() > 0) {
            jtAssignments.getColumnModel().getColumn(2).setHeaderValue("Name");
            jtAssignments.getColumnModel().getColumn(4).setHeaderValue("Points");
            jtAssignments.getColumnModel().getColumn(5).setHeaderValue("Group");
        }

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
                .addGap(0, 84, Short.MAX_VALUE))
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
            .addGap(0, 281, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("People", jPanel3);

        jbAddResource.setText("Add Resource");
        jbAddResource.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbAddResourceActionPerformed(evt);
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
        jScrollPane2.setViewportView(jtResources);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jbAddResource)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbDeleteDocument)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbRefresh)
                .addContainerGap(119, Short.MAX_VALUE))
            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbAddResource)
                    .addComponent(jbDeleteDocument)
                    .addComponent(jbRefresh))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Resources", jPanel4);

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
                .addComponent(jTabbedPane1))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jbAddCourseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbAddCourseActionPerformed
        JPanel AddCourse = new jpAddCourse(dbConnection);
        AddCourse.setName("Add Course");
        CreateFrame(AddCourse);
        
    }//GEN-LAST:event_jbAddCourseActionPerformed

    /**
     * Course DropDown Listener.
     * Changes selectedCourse variable based on user selection
     * @param evt 
     */
    private void jcbCourseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbCourseActionPerformed
        
        
        if(jcbCourse.getSelectedItem() != null)
        {
            selectedCourse = Integer.parseInt(courseIds.get(jcbCourse.getSelectedIndex()).toString());
            System.out.println("Active Course Selected: (int) " + selectedCourse);
            
            // An action has been performed. Update tabs!
            setAssignments();
            setOverview();
            setResources(selectedCourse);

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
        
        // on close reset the table to refresh 
        jdAddAssignments.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        jdAddAssignments.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                System.out.println("Window closed!");
                // Update the table
                setAssignments();
            }
        });
        
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
                
                setAssignments();
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

    private void jbAddResourceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbAddResourceActionPerformed
        JDialog jdAddResources = new JDialog();
        JPanel AddAssignments = new jpAddResourceToCourse(selectedCourse, dbConnection);
        jdAddResources.add(AddAssignments);
        jdAddResources.setSize(500, 400);
        jdAddResources.setVisible(true);
        
    }//GEN-LAST:event_jbAddResourceActionPerformed

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
                            st.execute("DELETE FROM CourseResourcesLink WHERE FKResourcesID = " + id + " AND FKCourseID = "+selectedCourse);
                        }
                    }

                    setResources(selectedCourse);

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

        
    }//GEN-LAST:event_jbDeleteDocumentActionPerformed

    private void jbRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbRefreshActionPerformed
        setResources(selectedCourse);
        
    }//GEN-LAST:event_jbRefreshActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddAssignments;
    private javax.swing.JButton btnDeleteAssignments;
    private javax.swing.JButton btnRefreshAssignments;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JButton jbAddCourse;
    private javax.swing.JButton jbAddResource;
    private javax.swing.JButton jbDeleteDocument;
    private javax.swing.JButton jbEditCourse;
    private javax.swing.JButton jbRefresh;
    private javax.swing.JComboBox jcbCourse;
    private javax.swing.JLabel jlNumber;
    private javax.swing.JLabel jlTitle;
    private javax.swing.JTable jtAssignments;
    private javax.swing.JTextArea jtDescription;
    private javax.swing.JTable jtResources;
    // End of variables declaration//GEN-END:variables

    
    private void CreateFrame(JPanel inPanel) {
        
        
                //  intWindowCounter++;
      JDialog jd = new JDialog();
      jd.add(inPanel);
      jd.pack();
      jd.setModal(true);
      jd.setVisible(true);
      setCourseList();
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
