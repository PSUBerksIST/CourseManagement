/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.psu.berksist.CourseEZ;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author Nathan
 */
public class jpClass extends javax.swing.JPanel {
    
    /**
     * Creates new form jpClass
     */
    
    private Statement st;
    
    private Connection dbConnection;
    
    private int intSelectedClassID;
    
    private int intSelectedCourseID;
    
    // Assignment Tab Declarations
    List<Integer> assignmentTab_SelectedAssignmentIDs = new ArrayList<Integer>();
    List<Integer> assignmentTab_SelectedGroupAssignmentIDs = new ArrayList<Integer>();
    
    // Student Tab Declarations
    List<Integer> studentTab_SelectedStudentIDs = new ArrayList<Integer>();
    
    public jpClass() {
        initComponents();
    }
    
    public jpClass(Connection inConnection){
        initComponents();
        setdbConnection(inConnection);;
        setjcbCourse();
        
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
    // fill the course drop down box
    private void setjcbCourse(){
        jcbCourse.removeAllItems();
        jcbCourse.addItem("Select Course");
 
        try {
             
            ResultSet rs = st.executeQuery("Select intID as ID, intNumber as Number, intWritingEmphasis as WritingEmphasis from Course order by Number asc");
            //ResultSet rs = st.executeQuery("select Number from Course order by ID asc");

            while (rs.next()) {
                jcbCourse.addItem(new CourseInfo(rs.getInt("ID"), rs.getInt("Number"), rs.getBoolean("WritingEmphasis")));
                //jcbCourse.addItem(//rs.getString("IST") + " " + 
                //        rs.getString("Number"));
            }
            
        } catch (SQLException sqle) {
            System.out.println(sqle);
        }
    }
    // fills the class drop down box
    private void setjcbClass(){
        jcbClass.removeAllItems();
        jcbClass.addItem("Select Class");
 
        try {

            ResultSet rs = st.executeQuery("select intID, intSection from Class where FKCourse_intID = "
                    + intSelectedCourseID + " order by intSection");
            
            while (rs.next()) 
            {
                jcbClass.addItem(new ClassInfo(rs.getInt("intID"), rs.getInt("intSection")));
            }

        } catch (SQLException sqle) {
            System.out.println(sqle);
        }
    }
    
    private void setStudents()
    {
        System.out.println("setStudents fired! Class ID: " + intSelectedCourseID);
       
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
            
            // Create our columns
            model.addColumn("Select");
            model.addColumn("First Name");
            model.addColumn("Last Name");
            model.addColumn("Student ID");
            
            // Set our model and also create our listeners
            jtStudents.getModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                    int lead = jtStudents.getSelectedRow();
                    if(lead > -1)
                    {
                        // On a table change update our local store of selectedAssignmentIDs
                        for(int i = 0; i < jtStudents.getModel().getRowCount(); i++)
                        {

                            int studentId = Integer.parseInt(jtStudents.getModel().getValueAt(i,3).toString());

                            if ((Boolean) jtStudents.getModel().getValueAt(i,0))
                            {  

                                System.out.println("Selected Student ID: " + studentId);

                                // Add the ID if we do not have it already
                                if(!studentTab_SelectedStudentIDs.contains(studentId))
                                {
                                    studentTab_SelectedStudentIDs.add(studentId);
                                }

                            }
                            else
                            {

                                System.out.println("Selected Student ID: " + studentId);

                                for (Iterator<Integer> iterator = studentTab_SelectedStudentIDs.iterator(); iterator.hasNext(); ) {
                                    Integer id = iterator.next();
                                    if (id == studentId) 
                                    {
                                        iterator.remove();
                                    }
                                }

                          }

                        }   
                    
                    }//if lead
                    
                    // Show the programmer what IDs are selected
                    System.out.println(studentTab_SelectedStudentIDs);
                }

            });
            
            // JTable will make our checkboxes for us
            TableColumn tc = jtStudents.getColumnModel().getColumn(0);
            tc.setCellEditor(jtStudents.getDefaultEditor(Boolean.class));  
            tc.setCellRenderer(jtStudents.getDefaultRenderer(Boolean.class)); 
            
            // Result Set 
            ResultSet result = st.executeQuery("SELECT vchrFirstName, vchrLastName, intID AS StudentID FROM vClass_All_Students WHERE intClassID = " + intSelectedClassID);

            while (result.next()) 
            {
                
                // Add our row to the JTable
                model.addRow(new Object[]{ false, result.getString("vchrFirstName"), result.getString("vchrLastName"), result.getString("StudentID")});
            }

        }
        catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void setGroupAssignments()
    {
        System.out.println("setGroupAssignments fired! Class ID: " + intSelectedCourseID);
        
        // Grab the courses from the database and display them
        try {
            
            DefaultTableModel model = (DefaultTableModel) jtGroupAssignments.getModel();

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
            
            // Set our model and also create our listeners
            jtGroupAssignments.getModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                    int lead = jtIndividualAssignments.getSelectedRow();
                    if(lead > -1)
                    {
                        // On a table change update our local store of selectedAssignmentIDs
                        for(int i = 0; i < jtGroupAssignments.getModel().getRowCount(); i++)
                        {

                            int assignmentId = Integer.parseInt(jtGroupAssignments.getModel().getValueAt(i,1).toString());

                            if ((Boolean) jtGroupAssignments.getModel().getValueAt(i,0))
                            {  

                                System.out.println("Selected ID: " + assignmentId);

                                // Add the ID if we do not have it already
                                if(!assignmentTab_SelectedGroupAssignmentIDs.contains(assignmentId))
                                {
                                    assignmentTab_SelectedGroupAssignmentIDs.add(assignmentId);
                                }

                            }
                            else
                            {

                                System.out.println("Selected ID: " + assignmentId);

                                for (Iterator<Integer> iterator = assignmentTab_SelectedGroupAssignmentIDs.iterator(); iterator.hasNext(); ) {
                                    Integer id = iterator.next();
                                    if (id == assignmentId) 
                                    {
                                        iterator.remove();
                                    }
                                }

                          }

                        }   
                    
                    }//if lead
                    
                    // Show the programmer what IDs are selected
                    System.out.println(assignmentTab_SelectedGroupAssignmentIDs);
                }

            });
            
            // JTable will make our checkboxes for us
            TableColumn tc = jtGroupAssignments.getColumnModel().getColumn(0);
            tc.setCellEditor(jtGroupAssignments.getDefaultEditor(Boolean.class));  
            tc.setCellRenderer(jtGroupAssignments.getDefaultRenderer(Boolean.class)); 
            
            // Result Set 
            ResultSet result = st.executeQuery("SELECT Assignment.intID AS ID, Assignment.vchrShortName AS Name, Assignment.vchrDescription AS Description, Assignment.realMaximumPoints AS Points FROM Assignment, Class_Assignment WHERE Assignment.intid = Class_Assignment.FKAssignment_intID AND Assignment.boolGroupAssignment = 1 AND Class_Assignment.FKClass_intID = " + intSelectedClassID);

            while (result.next()) 
            {
                
                // Add our row to the JTable
                model.addRow(new Object[]{ false, result.getString("ID"), result.getString("Name"), result.getString("Description"), result.getString("Points")}); 
                
            }

        }
        catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void setIndividualAssignments()
    {
        System.out.println("setIndividualAssignments fired! Class ID: " + intSelectedClassID);
        
        // Grab the courses from the database and display them
        try {

            DefaultTableModel model = (DefaultTableModel) jtIndividualAssignments.getModel();

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
            
            // Set our model and also create our listeners
            jtIndividualAssignments.getModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                
                // On a table change update our local store of selectedAssignmentIDs
                    int lead = jtIndividualAssignments.getSelectedRow();
                    
                    if(lead > -1)
                    {

                        for(int i = 0; i < jtIndividualAssignments.getModel().getRowCount(); i++)
                        {

                            int assignmentId = Integer.parseInt(jtIndividualAssignments.getModel().getValueAt(i,1).toString());

                            if ((Boolean) jtIndividualAssignments.getModel().getValueAt(i,0))
                            {  

                                System.out.println("Selected ID: " + assignmentId);

                                // Add the ID if we do not have it already
                                if(!assignmentTab_SelectedAssignmentIDs.contains(assignmentId))
                                {
                                    assignmentTab_SelectedAssignmentIDs.add(assignmentId);
                                }

                            }
                            else
                            {

                                System.out.println("Selected ID: " + assignmentId);

                                for (Iterator<Integer> iterator = assignmentTab_SelectedAssignmentIDs.iterator(); iterator.hasNext(); ) {
                                    Integer id = iterator.next();
                                    if (id == assignmentId) 
                                    {
                                        iterator.remove();
                                    }
                                }

                          }

                        }    
                    }//if lead
                    
                    // Show the programmer what IDs are selected
                    System.out.println(assignmentTab_SelectedAssignmentIDs);
                }

            });
            
            // JTable will make our checkboxes for us
            TableColumn tc = jtIndividualAssignments.getColumnModel().getColumn(0);
            tc.setCellEditor(jtIndividualAssignments.getDefaultEditor(Boolean.class));  
            tc.setCellRenderer(jtIndividualAssignments.getDefaultRenderer(Boolean.class)); 
            
            // Result Set 
            ResultSet result = st.executeQuery("SELECT Assignment.intid AS ID, Assignment.vchrShortName AS Name, Assignment.vchrDescription AS Description, Assignment.realMaximumPoints AS Points FROM Assignment, Class_Assignment WHERE Assignment.intid = Class_Assignment.FKAssignment_intID AND Assignment.boolGroupAssignment != 1 AND Class_Assignment.FKClass_intID = " + intSelectedClassID);

            while (result.next()) 
            {
                
                // Add our row to the JTable
                model.addRow(new Object[]{ false, result.getString("ID"), result.getString("Name"), result.getString("Description"), result.getString("Points")});
                
            }
            
        }
        catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
    * setOverview() This function sets the Overview Tab under Class with the Course, Title, Class, Meeting Location and Meeting Times. It executes the query which is hard-coded in this function and populates the corresponding fields.
    * @param None
    * @return None
    * 
    */
   private void setOverview()
   {
        try
        {
            ResultSet rs = st.executeQuery("SELECT Course.intNumber, Course.vchrTitle, Class.vchrMeetingLocation," +
                                           " Class.tMondayStart, Class.tMondayEnd," +
                                           " Class.tTuesdayStart, Class.tTuesdayEnd," +
                                           " Class.tWednesdayStart, Class.tWednesdayEnd," +
                                           " Class.tThursdayStart, Class.tThursdayEnd," +
                                           " Class.tFridayStart, Class.tFridayEnd," +
                                           " Class.tSaturdayStart, Class.tSaturdayEnd,"+
                                           " Class.tSundayStart, Class.tSundayEnd" +
                                           " FROM Course, Class WHERE Course.intID = " + intSelectedCourseID + 
                                           " AND Class.intSection = " + jcbClass.getSelectedItem()); 
         
            
            // TODO: Load Department Name and use that instead of hardcoding "IST" - RQZ
            jlCourseNumber.setText("Course: IST " + rs.getString("intNumber"));
            jlCourseTitle.setText(rs.getString("vchrTitle"));
            jlMeetingLocation.setText("Meeting Location: " + rs.getString("vchrMeetingLocation"));
            jlTime.setText("Meeting Times: ");
            jtStartEndTime.setText("");
            while (rs.next())
            {
                if(rs.getString("tMondayStart")!= null)
                {
                    jtStartEndTime.append("Monday: " + rs.getString("tMondayStart")+" - "+ rs.getString("tMondayEnd")+"\n");
                } //Get Monday Start and End Times

                if(rs.getString("tTuesdayStart")!= null)
                {
                    jtStartEndTime.append("Tuesday: " + rs.getString("tTuesdayStart")+" - "+ rs.getString("tTuesdayEnd")+"\n");
                } //Get Tuesday Start and End Times
                
                if(rs.getString("tWednesdayStart")!= null)
                {
                    jtStartEndTime.append("Wednesday : " + rs.getString("tWednesdayStart")+" - "+rs.getString("tWednesdayEnd")+"\n");
                } //Get Wednesday Start and End Times
                
                if(rs.getString("tThursdayStart")!= null)
                {
                    jtStartEndTime.append("Thursday: " + rs.getString("tThursdayStart")+" - "+ rs.getString("tThursdayEnd")+"\n");
                }  //Get Thursday Start and End Times

                if(rs.getString("tFridayStart")!= null)
                {
                    jtStartEndTime.append("Friday: " + rs.getString("tFridayStart")+" - "+ rs.getString("tFridayEnd")+"\n");
                }  //Get Friday Start and End Times
                
                if(rs.getString("tSaturdayStart")!= null)
                {
                    jtStartEndTime.append("Saturday: " + rs.getString("tSaturdayStart")+" - "+ rs.getString("tSaturdayEnd")+"\n");
                }  //Get Saturday Start and End Times
                
                   if(rs.getString("tSundayStart")!= null)
                {
                    jtStartEndTime.append("Sunday: " + rs.getString("tSundayStart")+" - "+ rs.getString("tSundayEnd")+"\n");
                }  //Get Sunday Start and End Times
         
          } // while(rs.next)
            
       } // try
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

        jcbClass = new javax.swing.JComboBox();
        jbAddClass = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jpClassOverviewTab = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jlCourseNumber = new javax.swing.JLabel();
        jlCourseTitle = new javax.swing.JLabel();
        jlMeetingLocation = new javax.swing.JLabel();
        jlTime = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        jtStartEndTime = new javax.swing.JTextArea();
        jpClassStudentsTab = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jtStudents = new javax.swing.JTable();
        jbSave = new javax.swing.JButton();
        jbDelete = new javax.swing.JButton();
        jbRefresh = new javax.swing.JButton();
        jpClassGradesTab = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jtAssignments = new javax.swing.JTable();
        jlAssignments = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jbSaveGrades = new javax.swing.JButton();
        jpClassAssignmentTab = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtIndividualAssignments = new javax.swing.JTable();
        jlGroupAssignments = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtGroupAssignments = new javax.swing.JTable();
        jbNewAssignment = new javax.swing.JButton();
        jbDeleteAssignment = new javax.swing.JButton();
        jbRefreshAssignment = new javax.swing.JButton();
        jcbCourse = new javax.swing.JComboBox();
        jbEditClass = new javax.swing.JButton();

        jcbClass.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Open Class", "Item 1", "Item 2", "Item 3", "Item 4" }));
        jcbClass.setEnabled(false);
        jcbClass.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jcbClassItemStateChanged(evt);
            }
        });
        jcbClass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbClassActionPerformed(evt);
            }
        });

        jbAddClass.setText("Add Class");
        jbAddClass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbAddClassActionPerformed(evt);
            }
        });

        jlCourseNumber.setText("Course:");

        jlMeetingLocation.setText("Meeting Location:     ");

        jlTime.setText("Meeting Times:");

        jtStartEndTime.setEditable(false);
        jtStartEndTime.setColumns(20);
        jtStartEndTime.setLineWrap(true);
        jtStartEndTime.setRows(5);
        jtStartEndTime.setWrapStyleWord(true);
        jScrollPane7.setViewportView(jtStartEndTime);

        javax.swing.GroupLayout jpClassOverviewTabLayout = new javax.swing.GroupLayout(jpClassOverviewTab);
        jpClassOverviewTab.setLayout(jpClassOverviewTabLayout);
        jpClassOverviewTabLayout.setHorizontalGroup(
            jpClassOverviewTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpClassOverviewTabLayout.createSequentialGroup()
                .addGroup(jpClassOverviewTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpClassOverviewTabLayout.createSequentialGroup()
                        .addGap(371, 371, 371)
                        .addComponent(jLabel6))
                    .addGroup(jpClassOverviewTabLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jpClassOverviewTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jpClassOverviewTabLayout.createSequentialGroup()
                                .addComponent(jlCourseNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jlCourseTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 321, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jlMeetingLocation, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jpClassOverviewTabLayout.createSequentialGroup()
                                .addComponent(jlTime, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(128, 128, 128)))))
                .addContainerGap(21, Short.MAX_VALUE))
        );
        jpClassOverviewTabLayout.setVerticalGroup(
            jpClassOverviewTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpClassOverviewTabLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jpClassOverviewTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jlCourseTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jlCourseNumber, javax.swing.GroupLayout.DEFAULT_SIZE, 19, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jlMeetingLocation, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jpClassOverviewTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jlTime, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(188, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Overview", jpClassOverviewTab);

        jtStudents.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Select", "First Name", "Last Name", "Student ID"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                true, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane5.setViewportView(jtStudents);

        jbSave.setText("Manage Students");
        jbSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbSaveActionPerformed(evt);
            }
        });

        jbDelete.setText("Drop Selected");
        jbDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbDeleteActionPerformed(evt);
            }
        });

        jbRefresh.setText("Refresh");
        jbRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbRefreshActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpClassStudentsTabLayout = new javax.swing.GroupLayout(jpClassStudentsTab);
        jpClassStudentsTab.setLayout(jpClassStudentsTabLayout);
        jpClassStudentsTabLayout.setHorizontalGroup(
            jpClassStudentsTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpClassStudentsTabLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpClassStudentsTabLayout.createSequentialGroup()
                .addComponent(jbSave, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jbDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jbRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11))
        );
        jpClassStudentsTabLayout.setVerticalGroup(
            jpClassStudentsTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpClassStudentsTabLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jpClassStudentsTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jbRefresh)
                    .addGroup(jpClassStudentsTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jbSave, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jbDelete, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(328, 328, 328))
        );

        jTabbedPane1.addTab("Students", jpClassStudentsTab);

        jtAssignments.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"Team Charter", "1/15/16", "5", null},
                {"Proposal", "1/25/16", "10", "yes"},
                {"UID 1", "1/15/16", "2", "no"},
                {"Networking", "1/25/16", "10", "no"},
                {"UID 2", "2/12/16", "2", "no"},
                {"System Users Tasks", "2/12/16", "10", "yes"}
            },
            new String [] {
                "Assignment", "Due Date", "Points", "Group"
            }
        ));
        jScrollPane3.setViewportView(jtAssignments);

        jlAssignments.setText("Assignments:");

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"Team 1", "5", "10", null},
                {"Team 2", "10", "10", null},
                {"", null, null, null}
            },
            new String [] {
                "Name", "Points Earned", "Points Possible", "Comment"
            }
        ));
        jScrollPane4.setViewportView(jTable2);

        jLabel1.setText("Assign To:");

        jbSaveGrades.setText("Save");

        javax.swing.GroupLayout jpClassGradesTabLayout = new javax.swing.GroupLayout(jpClassGradesTab);
        jpClassGradesTab.setLayout(jpClassGradesTabLayout);
        jpClassGradesTabLayout.setHorizontalGroup(
            jpClassGradesTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpClassGradesTabLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpClassGradesTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jpClassGradesTabLayout.createSequentialGroup()
                        .addGroup(jpClassGradesTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jlAssignments)
                            .addComponent(jLabel1))
                        .addGap(0, 360, Short.MAX_VALUE))
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(jpClassGradesTabLayout.createSequentialGroup()
                .addComponent(jbSaveGrades)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jpClassGradesTabLayout.setVerticalGroup(
            jpClassGradesTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpClassGradesTabLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlAssignments)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbSaveGrades)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Grades", jpClassGradesTab);

        jtIndividualAssignments.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Select", "ID", "Name", "Description", "Points"
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
        jScrollPane1.setViewportView(jtIndividualAssignments);

        jlGroupAssignments.setText("Group Assignments:");

        jLabel7.setText("Individual Assignments:");

        jtGroupAssignments.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Select", "ID", "Name", "Description", "Points"
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
        jScrollPane2.setViewportView(jtGroupAssignments);

        jbNewAssignment.setText("Manage");
        jbNewAssignment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbNewAssignmentActionPerformed(evt);
            }
        });

        jbDeleteAssignment.setText("Delete Selected");
        jbDeleteAssignment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbDeleteAssignmentActionPerformed(evt);
            }
        });

        jbRefreshAssignment.setText("Refresh");
        jbRefreshAssignment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbRefreshAssignmentActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpClassAssignmentTabLayout = new javax.swing.GroupLayout(jpClassAssignmentTab);
        jpClassAssignmentTab.setLayout(jpClassAssignmentTabLayout);
        jpClassAssignmentTabLayout.setHorizontalGroup(
            jpClassAssignmentTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpClassAssignmentTabLayout.createSequentialGroup()
                .addGroup(jpClassAssignmentTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jpClassAssignmentTabLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jpClassAssignmentTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jlGroupAssignments)
                            .addComponent(jLabel7))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jpClassAssignmentTabLayout.createSequentialGroup()
                        .addComponent(jbNewAssignment, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jbDeleteAssignment, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jbRefreshAssignment, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jpClassAssignmentTabLayout.setVerticalGroup(
            jpClassAssignmentTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpClassAssignmentTabLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jlGroupAssignments)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(13, 13, 13)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jpClassAssignmentTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbNewAssignment)
                    .addComponent(jbRefreshAssignment)
                    .addComponent(jbDeleteAssignment))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Assignments", jpClassAssignmentTab);

        jcbCourse.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Open Course", "Item 1", "Item 2", "Item 3", "Item 4" }));
        jcbCourse.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jcbCourseItemStateChanged(evt);
            }
        });
        jcbCourse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbCourseActionPerformed(evt);
            }
        });

        jbEditClass.setText("Edit Class");
        jbEditClass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbEditClassActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jbAddClass)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jcbCourse, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jcbClass, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbEditClass)
                .addGap(35, 35, 35))
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbAddClass)
                    .addComponent(jcbClass)
                    .addComponent(jcbCourse)
                    .addComponent(jbEditClass))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jbAddClass.getAccessibleContext().setAccessibleDescription("");
    }// </editor-fold>//GEN-END:initComponents

    private void jbAddClassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbAddClassActionPerformed
        JPanel AddClass = new jpAddClass(dbConnection);
        AddClass.setName("Add Class");
        CreateFrame(AddClass);
    }//GEN-LAST:event_jbAddClassActionPerformed

    private void jbNewAssignmentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbNewAssignmentActionPerformed

        JDialog jdAddAssignments = new JDialog();
        JPanel AddAssignments = new jpAddAssignmentsToClass(intSelectedClassID, dbConnection);
        jdAddAssignments.add(AddAssignments);
        jdAddAssignments.setSize(500, 400);
        jdAddAssignments.setVisible(true);
        
        System.out.println("Current Selected Class: " + intSelectedClassID);
        
        // on close reset the table to refresh 
        jdAddAssignments.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        jdAddAssignments.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                System.out.println("Window closed!");
                // Update the table
                setGroupAssignments();
                setIndividualAssignments();
            }
        });
        
    }//GEN-LAST:event_jbNewAssignmentActionPerformed

    private void jcbCourseItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jcbCourseItemStateChanged
       jbEditClass.setEnabled(false);
        if(jcbCourse.getSelectedIndex()>0){
            jcbClass.setEnabled(true);
            
            //intSelectedCourseID = jcbCourse.getSelectedIndex();
            intSelectedCourseID = ((CourseInfo) jcbCourse.getSelectedItem()).getIntID();
            
            System.out.println("Active Course ID Selected: (int) " + intSelectedCourseID);
            
            setjcbClass();
        }
        else{
            jcbClass.setEnabled(false);
        }
        
    }//GEN-LAST:event_jcbCourseItemStateChanged

    private void jcbCourseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbCourseActionPerformed
        
    }//GEN-LAST:event_jcbCourseActionPerformed

    private void jcbClassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbClassActionPerformed
        
    }//GEN-LAST:event_jcbClassActionPerformed

    private void jcbClassItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jcbClassItemStateChanged
        jbEditClass.setEnabled(false);
        if(jcbClass.getSelectedIndex()>0){
            jbEditClass.setEnabled(true);
            //intSelectedClassID = jcbClass.getSelectedIndex();
            
            intSelectedClassID = ((ClassInfo) jcbClass.getSelectedItem()).getIntID();
            
            System.out.println("Active Class ID Selected: (int) " + intSelectedClassID);
            
            // Set panels here
            setStudents();
            setGroupAssignments();
            setIndividualAssignments();
            setOverview();
        }
        else
        {
            jbEditClass.setEnabled(false);
        }
        
    }//GEN-LAST:event_jcbClassItemStateChanged

    private void jbRefreshAssignmentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbRefreshAssignmentActionPerformed
        
        
        // Any panel you want to refresh under assignments tab when asked, set here
        setStudents();
        setGroupAssignments();
        setIndividualAssignments();
        
    }//GEN-LAST:event_jbRefreshAssignmentActionPerformed

    private void jbSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbSaveActionPerformed
        
        
        JDialog jdAddStudents = new JDialog();
        JPanel AddStudents = new jpAddStudent(intSelectedClassID, dbConnection);
        jdAddStudents.add(AddStudents);
        jdAddStudents.setSize(500, 400);
        jdAddStudents.setVisible(true);
        
        // on close reset the table to refresh 
        jdAddStudents.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        jdAddStudents.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                System.out.println("Window closed!");
                // Update the table
                setStudents();
            }
        });
        
        /*
        String firstName = jtfFirstName.getText();
        String lastName = jtfLastName.getText();
        String email = jtfEmail.getText();
        String studentId = jtfStudentID.getText();
        String tel = jtfPhoneNumber.getText();
        
        try
        {
            st.execute("INSERT INTO Student (FirstName,LastName,EmailName,StudentID,PhoneNumber) VALUES (\""+firstName+"\", \""+lastName+"\", \""+email+"\", \""+studentId+"\", \""+tel+"\")");
            
            setStudents();
            
            JFrame PopUp = new JFrame();
            JOptionPane.showMessageDialog(PopUp,"Student added!"); 
            
        }
        catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        */
 
        
        
    }//GEN-LAST:event_jbSaveActionPerformed

    private void jbDeleteAssignmentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbDeleteAssignmentActionPerformed
        try {
            System.out.println("Remove Selected Assignments fired! Class ID: " + intSelectedClassID);
            

            if(!assignmentTab_SelectedAssignmentIDs.isEmpty())
            {
                for (Iterator<Integer> iterator = assignmentTab_SelectedAssignmentIDs.iterator(); iterator.hasNext(); ) {
                    Integer id = iterator.next();
                    iterator.remove();
                    st.execute("DELETE FROM Class_Assignment WHERE FKClass_intID = " + intSelectedClassID + " AND FKAssignment_intID = " + id);
                }
            }
            
            if(!assignmentTab_SelectedGroupAssignmentIDs.isEmpty())
            {
                for (Iterator<Integer> iterator = assignmentTab_SelectedGroupAssignmentIDs.iterator(); iterator.hasNext(); ) {
                    Integer id = iterator.next();
                    iterator.remove();
                    st.execute("DELETE FROM Class_Assignment WHERE FKClass_intID = " + intSelectedClassID + " AND FKAssignment_intID = " + id);
                }
            }
            
            setIndividualAssignments();
            setGroupAssignments();
            
            // Let the user know we have taken care of it
            JFrame PopUp = new JFrame();
            JOptionPane.showMessageDialog(PopUp,"Assignments Updated!");  
            
        } catch (SQLException ex) {
            Logger.getLogger(jpAddAssignmentsToClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jbDeleteAssignmentActionPerformed

    //this button edit class allows you to edit the information of the class you are currently viewing 
    private void jbEditClassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbEditClassActionPerformed
        JPanel AddClass = new jpAddClass(dbConnection,  intSelectedCourseID, intSelectedClassID);
        AddClass.setName("Add Class");
        CreateFrame(AddClass); 
        
    }//GEN-LAST:event_jbEditClassActionPerformed

    private void jbDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbDeleteActionPerformed
        
        
        try {
            System.out.println("Remove Selected Students fired! Class ID: " + intSelectedClassID);
            

            if(!studentTab_SelectedStudentIDs.isEmpty())
            {
                String options[] = {"Yes","No"};

                int PromptResult = JOptionPane.showOptionDialog(null,"Are you sure you want to drop these students?","Students",JOptionPane.DEFAULT_OPTION,JOptionPane.WARNING_MESSAGE,null,options,options[1]);
                if(PromptResult==JOptionPane.YES_OPTION)
                {
                    for (Iterator<Integer> iterator = studentTab_SelectedStudentIDs.iterator(); iterator.hasNext(); ) {
                        Integer id = iterator.next();
                        iterator.remove();
                        st.execute("DELETE FROM Class_Student WHERE FKClass_intID = " + intSelectedClassID + " AND FKStudent_intID = " + id);
                    }
                    
                    setStudents();
            
                    // Let the user know we have taken care of it
                    JFrame PopUp = new JFrame();
                    JOptionPane.showMessageDialog(PopUp,"Students Updated!"); 
                    
                }

            }
             
            
        } catch (SQLException ex) {
            Logger.getLogger(jpAddAssignmentsToClass.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }//GEN-LAST:event_jbDeleteActionPerformed

    private void jbRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbRefreshActionPerformed
        setStudents();
    }//GEN-LAST:event_jbRefreshActionPerformed
  private void CreateFrame(JPanel inPanel) {
                //  intWindowCounter++;
      JDialog jd = new JDialog();
      jd.add(inPanel);
      jd.pack();
      jd.setModal(true);
      jd.setVisible(true);
  }//CreateFrame

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable2;
    private javax.swing.JButton jbAddClass;
    private javax.swing.JButton jbDelete;
    private javax.swing.JButton jbDeleteAssignment;
    private javax.swing.JButton jbEditClass;
    private javax.swing.JButton jbNewAssignment;
    private javax.swing.JButton jbRefresh;
    private javax.swing.JButton jbRefreshAssignment;
    private javax.swing.JButton jbSave;
    private javax.swing.JButton jbSaveGrades;
    private javax.swing.JComboBox jcbClass;
    private javax.swing.JComboBox jcbCourse;
    private javax.swing.JLabel jlAssignments;
    private javax.swing.JLabel jlCourseNumber;
    private javax.swing.JLabel jlCourseTitle;
    private javax.swing.JLabel jlGroupAssignments;
    private javax.swing.JLabel jlMeetingLocation;
    private javax.swing.JLabel jlTime;
    private javax.swing.JPanel jpClassAssignmentTab;
    private javax.swing.JPanel jpClassGradesTab;
    private javax.swing.JPanel jpClassOverviewTab;
    private javax.swing.JPanel jpClassStudentsTab;
    private javax.swing.JTable jtAssignments;
    private javax.swing.JTable jtGroupAssignments;
    private javax.swing.JTable jtIndividualAssignments;
    private javax.swing.JTextArea jtStartEndTime;
    private javax.swing.JTable jtStudents;
    // End of variables declaration//GEN-END:variables

}
