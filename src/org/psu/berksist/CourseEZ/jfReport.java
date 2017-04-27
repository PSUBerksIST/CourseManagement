/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.psu.berksist.CourseEZ;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.docx4j.openpackaging.exceptions.Docx4JException;

/**
 *
 * @author Deathx, jss5783
 * 
 *  ******************* MODIFICATION LOG *****************************************
 *  2017 April 27   -   Departments now loaded into cmbDepartment upon jfReport's creation.
 *                      Courses are loaded into cmbCourse when cmbDepartment has an actual department selected.
 *                      Beginnings of adding new profile functionality.
 *                      Content tab "hidden" (deleted, because disabling it doesn't work as desired) on jfReport's creation. -JSS5783
 *  2017 April 25   -   BUGFIX: Shows error message when no profile is loaded and the user tries to generate files.
 *                      Rearranged Template tab so table-related components can be easily hidden/shown as needed.
 *                      Components can now be added and removed from lstTemplateComponents
 *                          (technically lmodelLstTemplateComponents).
 *                      Added (currently blank) Preview tab.
 *                      Disabled cmbCourse and cmbSection in preparation for loading in actual departments/etc.
 *                          from database. -JSS5783
 *  2017 April 24   -   Radio buttons now update filetype.
 *                      Load button now updates profile type behind the scenes.
 *                      Worked on moving the report generation code here.
 *                      !BUG: Doesn't generate. Connection doesn't seem to be correctly passed?
 *                      !BUG: Commented out logger and just used serr for exception-reporting because of
 *                          "Failed to load class 'org.slf4j.impl.StaticLoggerBinder'." -JSS5783
 * 
 *  2017 April 20   -   WIP: GUI overhaul continued.
 *                          GUI-side dummy data complete.
 *                      Filename chooser can now choose nonexistent files in preparation for saving.
 *                      File choosers now start in the Resources folder instead of the user's root directory.
 *                      BUGFIX: Filepaths are now selected properly. -JSS5783
 * 
 *  2017 April 20   -   WIP: GUI overhaul. -JSS5783
 * 
 *  2017 April 19   -   Basic file-picking functionality added.
 *                      Continued roughing in code (not sure how data and reports are being generated yet). -JSS5783
 * 
 *  2017 April 17   -   Started laying the foundation for report generation, including a GUI makeover. -JSS5783
 */
public class jfReport extends javax.swing.JFrame
{
    Connection dbLocalConnection;
    private static String strFilepath = new String(AppConstants.ROOT_FOLDER);   //TODO: replace with dedicated reports folder; currently pointing to /resources
    private static String strFilename = new String("NewReport");           //TODO: Replace with better default report name; maybe split into filename and file extension variables?
    private static int intSelectedProfile = -1;
    private static final int DOCX_ONLY = 0;
    private static final int DOCX_AND_PDF = 1;
    private static final int PDF_ONLY = 2;
    private static int intFiletype = DOCX_AND_PDF;
    private static DefaultListModel lmodelLstTemplateComponents = new DefaultListModel();
    private static Statement st;
    private static ResultSet rs;
    
    
    
    /**
     * Creates new form ReportFrame
     * @param inConnection Database connection
     */
    public jfReport(Connection inConnection)
    {
        initComponents();
        
        dbLocalConnection = inConnection;
        
        txtfFilepath.setText(strFilepath);
        txtfFilename.setText(strFilename);
        
        jpTemplateTable.setVisible(false);
        
        jtpEditor.removeTabAt(1);   //removes the Content tab
        //jtpEditor.add(jpContent, 1);    //re-adds the Content tab
        
        //load departments into cmbDepartment
        try
        {
            cmbDepartment.addItem("---");
            st = dbLocalConnection.createStatement();
            rs = st.executeQuery("SELECT vchrName FROM DEPARTMENT");
            
            while (rs.next() )
            {
                cmbDepartment.addItem(rs.getString("vchrName") );
            }
        }
        catch (SQLException ex)
        {
            Logger.getLogger(jpEditCourse.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //load departments from dbLocalConnection into cmbDepartments
        //TODO: get number of departments from database, process it, etc.
        //database: get number of departments
//        for (int i = 0; i < NUMBEROFDEPARTMENTTYPESINDATABASE; i++)
//          {
//              cmbDepartment.addItem(CURRENTDEPARTMENTTYPE_INDEXINDATABASE(i) );
//          }
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jpProfile = new javax.swing.JPanel();
        btnNewProfile = new javax.swing.JButton();
        btnLoadProfile = new javax.swing.JButton();
        btnDeleteProfile = new javax.swing.JButton();
        btnSaveProfile = new javax.swing.JButton();
        scpListProfile = new javax.swing.JScrollPane();
        lstProfile = new javax.swing.JList<>();
        jpClass = new javax.swing.JPanel();
        cmbDepartment = new javax.swing.JComboBox<>();
        cmbCourse = new javax.swing.JComboBox<>();
        cmbSection = new javax.swing.JComboBox<>();
        lblDepartment = new javax.swing.JLabel();
        lblCourse = new javax.swing.JLabel();
        lblSection = new javax.swing.JLabel();
        jtpEditor = new javax.swing.JTabbedPane();
        jpTemplate = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        lstTemplateDefaultComponents = new javax.swing.JList<>();
        jScrollPane3 = new javax.swing.JScrollPane();
        lstTemplateComponents = new javax.swing.JList<>();
        btnTemplateAddComponentToTemplate = new javax.swing.JButton();
        btnTemplateRemoveComponentFromTemplate = new javax.swing.JButton();
        jpTemplateTable = new javax.swing.JPanel();
        btnAddComponentToTable = new javax.swing.JButton();
        btnRemoveComponentFromTable = new javax.swing.JButton();
        jpTemplateTableSize = new javax.swing.JPanel();
        spinnerColumns = new javax.swing.JSpinner();
        spinnerRows = new javax.swing.JSpinner();
        lblColumns = new javax.swing.JLabel();
        lblRows = new javax.swing.JLabel();
        btnTemplateUpdateTable = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        tableTemplateTableContents = new javax.swing.JTable();
        jpContents = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        lstContentsAvailableContentItems = new javax.swing.JList<>();
        jspListContentsAssignedComponents = new javax.swing.JScrollPane();
        listContentsAssignedContentItems = new javax.swing.JList<>();
        jScrollPane7 = new javax.swing.JScrollPane();
        tableContentsAssignedContentItems = new javax.swing.JTable();
        btnContentsRemoveSelectedContentItemFromList = new javax.swing.JButton();
        btnContentsAssignSelectedContentItemToList = new javax.swing.JButton();
        btnContentsAssignSelectedContentItemToTable = new javax.swing.JButton();
        btnContentsRemoveSelectedContentItemFromTable = new javax.swing.JButton();
        btnContentsAssignAllContentItemsToList = new javax.swing.JButton();
        btnContentsRemoveAllContentItemsFromList = new javax.swing.JButton();
        btnContentsAssignAllContentItemsToTable = new javax.swing.JButton();
        btnContentsRemoveAllContentItemsFromTable = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jpGeneration = new javax.swing.JPanel();
        txtfFilepath = new javax.swing.JTextField();
        btnFilepath = new javax.swing.JButton();
        txtfFilename = new javax.swing.JTextField();
        btnFilename = new javax.swing.JButton();
        btnGenerateReport = new javax.swing.JButton();
        jpGenerateReport = new javax.swing.JPanel();
        rbtnDocx = new javax.swing.JRadioButton();
        rbtnDocxAndPdf = new javax.swing.JRadioButton();
        rbtnPdf = new javax.swing.JRadioButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Generate Report");

        jpProfile.setBorder(javax.swing.BorderFactory.createTitledBorder("Profile"));

        btnNewProfile.setText("New");
        btnNewProfile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewProfileActionPerformed(evt);
            }
        });

        btnLoadProfile.setText("Load");
        btnLoadProfile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoadProfileActionPerformed(evt);
            }
        });

        btnDeleteProfile.setText("Delete");

        btnSaveProfile.setText("Save");

        lstProfile.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Assignment", "Syllabus" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        lstProfile.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        scpListProfile.setViewportView(lstProfile);

        javax.swing.GroupLayout jpProfileLayout = new javax.swing.GroupLayout(jpProfile);
        jpProfile.setLayout(jpProfileLayout);
        jpProfileLayout.setHorizontalGroup(
            jpProfileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpProfileLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpProfileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpProfileLayout.createSequentialGroup()
                        .addGroup(jpProfileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnNewProfile, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnSaveProfile, javax.swing.GroupLayout.DEFAULT_SIZE, 77, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jpProfileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnDeleteProfile, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnLoadProfile, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(scpListProfile))
                .addContainerGap())
        );
        jpProfileLayout.setVerticalGroup(
            jpProfileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpProfileLayout.createSequentialGroup()
                .addComponent(scpListProfile)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpProfileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnLoadProfile)
                    .addComponent(btnNewProfile))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpProfileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSaveProfile)
                    .addComponent(btnDeleteProfile))
                .addContainerGap())
        );

        jpClass.setBorder(javax.swing.BorderFactory.createTitledBorder("Class"));

        cmbDepartment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbDepartmentActionPerformed(evt);
            }
        });

        cmbCourse.setEnabled(false);

        cmbSection.setEnabled(false);

        lblDepartment.setText("Department:");

        lblCourse.setText("Course: ");

        lblSection.setText("Section:");

        javax.swing.GroupLayout jpClassLayout = new javax.swing.GroupLayout(jpClass);
        jpClass.setLayout(jpClassLayout);
        jpClassLayout.setHorizontalGroup(
            jpClassLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpClassLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblDepartment)
                .addGap(4, 4, 4)
                .addComponent(cmbDepartment, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblCourse)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbCourse, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblSection)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbSection, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jpClassLayout.setVerticalGroup(
            jpClassLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpClassLayout.createSequentialGroup()
                .addGroup(jpClassLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDepartment)
                    .addComponent(cmbDepartment, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCourse)
                    .addComponent(cmbCourse, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblSection)
                    .addComponent(cmbSection, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 12, Short.MAX_VALUE))
        );

        jtpEditor.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jtpEditorStateChanged(evt);
            }
        });
        jtpEditor.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jtpEditorFocusGained(evt);
            }
        });

        lstTemplateDefaultComponents.setBorder(javax.swing.BorderFactory.createTitledBorder("Default Components"));
        lstTemplateDefaultComponents.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "TASK", "ASSIGNMENT", "CLASS_NAME", "COURSE_DESCRIPTION", "PROJECT_INDIVIDUAL", "PROJECT_GROUP", "RESOURCE_REQUIREMENTS{COLUMNS,ROWS}", "CLASS_SCHEDULE{COLUMNS,ROWS}", "DATE" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        lstTemplateDefaultComponents.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane2.setViewportView(lstTemplateDefaultComponents);

        lstTemplateComponents.setBorder(javax.swing.BorderFactory.createTitledBorder("Template Components"));
        lstTemplateComponents.setModel(lmodelLstTemplateComponents);
        lstTemplateComponents.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        lstTemplateComponents.setToolTipText("");
        lstTemplateComponents.setDropMode(javax.swing.DropMode.INSERT);
        lstTemplateComponents.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                lstTemplateComponentsValueChanged(evt);
            }
        });
        jScrollPane3.setViewportView(lstTemplateComponents);

        btnTemplateAddComponentToTemplate.setText(">");
        btnTemplateAddComponentToTemplate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTemplateAddComponentToTemplateActionPerformed(evt);
            }
        });

        btnTemplateRemoveComponentFromTemplate.setText("<");
        btnTemplateRemoveComponentFromTemplate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTemplateRemoveComponentFromTemplateActionPerformed(evt);
            }
        });

        btnAddComponentToTable.setText(">");
        btnAddComponentToTable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddComponentToTableActionPerformed(evt);
            }
        });

        btnRemoveComponentFromTable.setText("<");
        btnRemoveComponentFromTable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveComponentFromTableActionPerformed(evt);
            }
        });

        jpTemplateTableSize.setBorder(javax.swing.BorderFactory.createTitledBorder("Table Size"));

        lblColumns.setText("Columns:");

        lblRows.setText("Rows:");

        btnTemplateUpdateTable.setText("Update Table");
        btnTemplateUpdateTable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTemplateUpdateTableActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpTemplateTableSizeLayout = new javax.swing.GroupLayout(jpTemplateTableSize);
        jpTemplateTableSize.setLayout(jpTemplateTableSizeLayout);
        jpTemplateTableSizeLayout.setHorizontalGroup(
            jpTemplateTableSizeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpTemplateTableSizeLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblColumns)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(spinnerColumns, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(51, 51, 51)
                .addComponent(lblRows)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(spinnerRows, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnTemplateUpdateTable)
                .addGap(24, 24, 24))
        );
        jpTemplateTableSizeLayout.setVerticalGroup(
            jpTemplateTableSizeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpTemplateTableSizeLayout.createSequentialGroup()
                .addGroup(jpTemplateTableSizeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(spinnerColumns, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(spinnerRows, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblColumns)
                    .addComponent(lblRows)
                    .addComponent(btnTemplateUpdateTable))
                .addGap(0, 7, Short.MAX_VALUE))
        );

        tableTemplateTableContents.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        tableTemplateTableContents.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"DATE", "ASSIGNMENT"},
                {"DATE", "ASSIGNMENT"},
                {"DATE", "ASSIGNMENT"}
            },
            new String [] {
                "Column1", "Column2"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane4.setViewportView(tableTemplateTableContents);

        javax.swing.GroupLayout jpTemplateTableLayout = new javax.swing.GroupLayout(jpTemplateTable);
        jpTemplateTable.setLayout(jpTemplateTableLayout);
        jpTemplateTableLayout.setHorizontalGroup(
            jpTemplateTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpTemplateTableLayout.createSequentialGroup()
                .addGroup(jpTemplateTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnAddComponentToTable, javax.swing.GroupLayout.DEFAULT_SIZE, 51, Short.MAX_VALUE)
                    .addComponent(btnRemoveComponentFromTable, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jpTemplateTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jpTemplateTableSize, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 482, Short.MAX_VALUE)))
        );
        jpTemplateTableLayout.setVerticalGroup(
            jpTemplateTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpTemplateTableLayout.createSequentialGroup()
                .addGap(62, 62, 62)
                .addComponent(btnAddComponentToTable)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnRemoveComponentFromTable)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jpTemplateTableLayout.createSequentialGroup()
                .addComponent(jpTemplateTableSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jpTemplateLayout = new javax.swing.GroupLayout(jpTemplate);
        jpTemplate.setLayout(jpTemplateLayout);
        jpTemplateLayout.setHorizontalGroup(
            jpTemplateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpTemplateLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpTemplateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpTemplateLayout.createSequentialGroup()
                        .addGroup(jpTemplateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnTemplateAddComponentToTemplate, javax.swing.GroupLayout.DEFAULT_SIZE, 54, Short.MAX_VALUE)
                            .addComponent(btnTemplateRemoveComponentFromTemplate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane3))
                    .addComponent(jpTemplateTable, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jpTemplateLayout.setVerticalGroup(
            jpTemplateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpTemplateLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpTemplateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 357, Short.MAX_VALUE)
                    .addGroup(jpTemplateLayout.createSequentialGroup()
                        .addGroup(jpTemplateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jpTemplateLayout.createSequentialGroup()
                                .addGap(25, 25, 25)
                                .addComponent(btnTemplateAddComponentToTemplate)
                                .addGap(18, 18, 18)
                                .addComponent(btnTemplateRemoveComponentFromTemplate)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jpTemplateTable, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jtpEditor.addTab("Template", jpTemplate);

        lstContentsAvailableContentItems.setBorder(javax.swing.BorderFactory.createTitledBorder("Unassigned Content Items"));
        lstContentsAvailableContentItems.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "ASSIGNMENT1", "ASSIGNMENT4", "ASSIGNMENT5", "PROJECT_GROUP1", "PROJECT_INDIVIDUAL1" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        lstContentsAvailableContentItems.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane5.setViewportView(lstContentsAvailableContentItems);

        listContentsAssignedContentItems.setBorder(javax.swing.BorderFactory.createTitledBorder("Assigned Content Items"));
        listContentsAssignedContentItems.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "CLASS_NAME", "COURSE_DESCRIPTION", "CONTACT_INFORMATION", "RESOURCE_REQUIREMENTS{3,1}", "SCHEDULE{2,3}" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        listContentsAssignedContentItems.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jspListContentsAssignedComponents.setViewportView(listContentsAssignedContentItems);

        tableContentsAssignedContentItems.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        tableContentsAssignedContentItems.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"April 10", "ASSIGNMENT2"},
                {"April 12", "ASSIGNMENT3"},
                {"April 14", "ASSIGNMENT6"}
            },
            new String [] {
                "Date", "Task"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane7.setViewportView(tableContentsAssignedContentItems);

        btnContentsRemoveSelectedContentItemFromList.setText(">");

        btnContentsAssignSelectedContentItemToList.setText("<");

        btnContentsAssignSelectedContentItemToTable.setText("<");
        btnContentsAssignSelectedContentItemToTable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnContentsAssignSelectedContentItemToTableActionPerformed(evt);
            }
        });

        btnContentsRemoveSelectedContentItemFromTable.setText(">");

        btnContentsAssignAllContentItemsToList.setText("<<");

        btnContentsRemoveAllContentItemsFromList.setText(">>");

        btnContentsAssignAllContentItemsToTable.setText("<<");

        btnContentsRemoveAllContentItemsFromTable.setText(">>");

        javax.swing.GroupLayout jpContentsLayout = new javax.swing.GroupLayout(jpContents);
        jpContents.setLayout(jpContentsLayout);
        jpContentsLayout.setHorizontalGroup(
            jpContentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpContentsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpContentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jspListContentsAssignedComponents, javax.swing.GroupLayout.DEFAULT_SIZE, 277, Short.MAX_VALUE)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpContentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnContentsRemoveSelectedContentItemFromList)
                    .addComponent(btnContentsAssignSelectedContentItemToList)
                    .addComponent(btnContentsAssignSelectedContentItemToTable)
                    .addComponent(btnContentsRemoveSelectedContentItemFromTable)
                    .addComponent(btnContentsAssignAllContentItemsToList)
                    .addComponent(btnContentsRemoveAllContentItemsFromList)
                    .addComponent(btnContentsAssignAllContentItemsToTable)
                    .addComponent(btnContentsRemoveAllContentItemsFromTable))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 408, Short.MAX_VALUE)
                .addContainerGap())
        );
        jpContentsLayout.setVerticalGroup(
            jpContentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpContentsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpContentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpContentsLayout.createSequentialGroup()
                        .addComponent(jspListContentsAssignedComponents, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                    .addGroup(jpContentsLayout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addComponent(btnContentsAssignAllContentItemsToList)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnContentsAssignSelectedContentItemToList)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnContentsRemoveSelectedContentItemFromList)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnContentsRemoveAllContentItemsFromList)
                        .addGap(54, 54, 54)
                        .addComponent(btnContentsAssignAllContentItemsToTable)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnContentsAssignSelectedContentItemToTable)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnContentsRemoveSelectedContentItemFromTable)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnContentsRemoveAllContentItemsFromTable))
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );

        jtpEditor.addTab("Contents", jpContents);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 753, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 381, Short.MAX_VALUE)
        );

        jtpEditor.addTab("Preview", jPanel1);

        txtfFilepath.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtfFilepathFocusLost(evt);
            }
        });
        txtfFilepath.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtfFilepathActionPerformed(evt);
            }
        });

        btnFilepath.setText("Change Filepath");
        btnFilepath.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFilepathActionPerformed(evt);
            }
        });

        txtfFilename.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtfFilenameFocusLost(evt);
            }
        });
        txtfFilename.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtfFilenameActionPerformed(evt);
            }
        });

        btnFilename.setText("Change Filename");
        btnFilename.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFilenameActionPerformed(evt);
            }
        });

        btnGenerateReport.setText("Generate Report");
        btnGenerateReport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerateReportActionPerformed(evt);
            }
        });

        jpGenerateReport.setBorder(javax.swing.BorderFactory.createTitledBorder("Filetype"));

        buttonGroup1.add(rbtnDocx);
        rbtnDocx.setText("DOCX");
        rbtnDocx.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtnDocxActionPerformed(evt);
            }
        });

        buttonGroup1.add(rbtnDocxAndPdf);
        rbtnDocxAndPdf.setSelected(true);
        rbtnDocxAndPdf.setText("DOCX + PDF");
        rbtnDocxAndPdf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtnDocxAndPdfActionPerformed(evt);
            }
        });

        buttonGroup1.add(rbtnPdf);
        rbtnPdf.setText("PDF");
        rbtnPdf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtnPdfActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpGenerateReportLayout = new javax.swing.GroupLayout(jpGenerateReport);
        jpGenerateReport.setLayout(jpGenerateReportLayout);
        jpGenerateReportLayout.setHorizontalGroup(
            jpGenerateReportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpGenerateReportLayout.createSequentialGroup()
                .addContainerGap(189, Short.MAX_VALUE)
                .addComponent(rbtnDocx)
                .addGap(60, 60, 60)
                .addComponent(rbtnDocxAndPdf)
                .addGap(75, 75, 75)
                .addComponent(rbtnPdf)
                .addContainerGap(189, Short.MAX_VALUE))
        );
        jpGenerateReportLayout.setVerticalGroup(
            jpGenerateReportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpGenerateReportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(rbtnDocx)
                .addComponent(rbtnDocxAndPdf)
                .addComponent(rbtnPdf))
        );

        javax.swing.GroupLayout jpGenerationLayout = new javax.swing.GroupLayout(jpGeneration);
        jpGeneration.setLayout(jpGenerationLayout);
        jpGenerationLayout.setHorizontalGroup(
            jpGenerationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpGenerationLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jpGenerationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpGenerationLayout.createSequentialGroup()
                        .addGroup(jpGenerationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtfFilename)
                            .addComponent(txtfFilepath))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jpGenerationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnFilepath, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnFilename, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addComponent(jpGenerateReport, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpGenerationLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnGenerateReport)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jpGenerationLayout.setVerticalGroup(
            jpGenerationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpGenerationLayout.createSequentialGroup()
                .addContainerGap(32, Short.MAX_VALUE)
                .addGroup(jpGenerationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtfFilepath, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnFilepath))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                .addGroup(jpGenerationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtfFilename, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnFilename))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 67, Short.MAX_VALUE)
                .addComponent(jpGenerateReport, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 63, Short.MAX_VALUE)
                .addComponent(btnGenerateReport)
                .addContainerGap(60, Short.MAX_VALUE))
        );

        jtpEditor.addTab("Generation", jpGeneration);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jpProfile, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jtpEditor)
                    .addComponent(jpClass, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jpClass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtpEditor))
                    .addComponent(jpProfile, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Pick a new filepath to save reports in. (Doesn't pick files themselves, unless pointing at a folder to save in.) If invalid, changes aren't saved.
     * @param evt 
     */
    private void btnFilepathActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFilepathActionPerformed
        try
        {
            JFileChooser fc = new JFileChooser();
            fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            fc.setCurrentDirectory(new File(AppConstants.ROOT_FOLDER) );
            int intFileChooserReturnValue = fc.showOpenDialog(this);
            
            if (intFileChooserReturnValue == JFileChooser.APPROVE_OPTION)   //if filepath is valid
            {
                //File file = fc.getSelectedFile();
                //File fileFilepath = fc.getCurrentDirectory();
                //strFilepath = fileFilepath.getPath();     //getCurrentDirectory() and getPath() return a filepath that's one level too short (e.g., path is CourseManagement/resources, it will save it as CourseManagement instead)
                strFilepath = fc.getSelectedFile().toString() + "/";
                txtfFilepath.setText(strFilepath);
            }
            else if (intFileChooserReturnValue == JFileChooser.ERROR_OPTION)
            {
                throw new Exception();  //not sure what pops error, but if one can choose an invalid filepath, then maybe pop an error dialog about invalid target?
            }
        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(this, e.toString(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnFilepathActionPerformed

    /**
     * Generates a report.
     * TODO: report data gets passed in how and where?
     * @param evt 
     */
    private void btnGenerateReportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerateReportActionPerformed
        /*
        TODO
        try
        {
            //check if filepath is valid
            //check if filename is valid
            //check if data getting passed is valid here, I guess
            //if file already exists
            //  then ask if user wants to overwrite the preexisting file (default selection is "no")
            //      if true
            //          if file is (over)writeable ((and location can be written to))
            //              try to overwrite preexisting file with passed-in data
            //                  pop dialog stating "Report generated!"
            //              catch any errors for whatever reason with an error dialog
            //          otherwise
            //              pop error dialog stating "Report generation failed!", that the file cannot be overwritten or location cannot be written to or whatever, and with error log
            //otherwise
            //  if location can be written to
            //      try to write to file with passed-in data
            //          pop dialog stating "Report generated!"
            //      catch any errors for whatever reason with an error dialog with error log
            //  otherwise
            //      pop error dialog stating "Report generation failed!", that the file cannot be overwritten or location cannot be written to or whatever, and with error log
            
            //TODO: Possibly if a lot of reports get generated in short order, instead of dialog boxes, just pop colored labels to reduce clicking
            //Also a bunch of radio buttons for filetype(s). i.e., PDF, DOCX, or both.
        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(this, e.toString(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        */
        
        //TODO: Replace code here with smartcode, instead of checking for selected template and generating based on that.
        Reports report = new Reports();
        
        switch(intSelectedProfile)
        {
            case -1:    //nothing selected
                JOptionPane.showMessageDialog(this, "No profile loaded.","Error", JOptionPane.ERROR_MESSAGE);
                break;
            case 0:     //Assignment
                try
                {
                    report.Assignment(dbLocalConnection, strFilepath, strFilename, intFiletype);
                }
                catch (Docx4JException ex)
                {
                    //Logger.getLogger(jfReport.class.getName()).log(Level.SEVERE, null, ex);
                    System.err.println(ex.toString() );
                }
                catch (FileNotFoundException ex)
                {
                    //Logger.getLogger(jfReport.class.getName()).log(Level.SEVERE, null, ex);
                    System.err.println(ex.toString() );
                }
                //TODO: Reports.Assignment() needs to be fleshed out more rather than just basic dummy data
                catch (SQLException ex)
                {
                    //Logger.getLogger(jfReport.class.getName() ).log(Level.SEVERE, null, ex);
                    System.err.println(ex.toString() );
                }
                break;
            case 1: //Syllabus
                try
                {
                    report.Syllabus(dbLocalConnection, strFilepath, strFilename, intFiletype);
                }
                catch (Docx4JException ex)
                {
                    //Logger.getLogger(jfReport.class.getName()).log(Level.SEVERE, null, ex);
                    System.err.println(ex.toString() );
                }
                catch (FileNotFoundException ex)
                {
                    //Logger.getLogger(jfReport.class.getName()).log(Level.SEVERE, null, ex);
                    System.err.println(ex.toString() );
                }
                catch (SQLException ex)
                {
                    //Logger.getLogger(jfReport.class.getName() ).log(Level.SEVERE, null, ex);
                    System.err.println(ex.toString() );
                }
                break;
            default:
                break;
        }   //end of switch(intSelectedProfile)
        
    }//GEN-LAST:event_btnGenerateReportActionPerformed

    /**
     * Pick a new filename, either from preexisting ones on the computer or enter your own.
     * File extensions (i.e., anything after the first period (.) ) will be stripped in preparation for generation as a DOCX, PDF, or both.
     * @param evt 
     */
    private void btnFilenameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFilenameActionPerformed
        /*
        TODO
        rather than pulling from wherever JFileChooser decides to start from,
        check if current string in textbook is valid (since it can be typed in, not just FileChooser picked)
        if valid, start there
        otherwise, start from default location
        should also be able to enter a new one in JFileChooser for future generation,
        just as FileChoosers in other programs allow one to "reserve" a namespace without an actual preexisting file
        */
                try
        {
            JFileChooser fc = new JFileChooser();
            fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
//            fc.setFileFilter(new FileFilter()
//                {
//                    public String getDescription()
//                    {
//                        return "PDF (*.pdf)";
//                    }
//                    
//                    public boolean accept(File f)
//                    {
//                        if (f.isDirectory() )
//                        {
//                            return true;
//                        }
//                        else
//                        {
//                            String filename = f.getName().toLowerCase();
//                            return filename.endsWith(".pdf");
//                        }
//                    }
//                });
            fc.addChoosableFileFilter(new FileNameExtensionFilter("DOCX (*.docx)", "docx"));
            fc.addChoosableFileFilter(new FileNameExtensionFilter("PDF (*.pdf)", "pdf"));
            fc.setAcceptAllFileFilterUsed(false);
            fc.setCurrentDirectory(new File(AppConstants.ROOT_FOLDER) );
            int intFileChooserReturnValue = fc.showSaveDialog(this);    //SAVE dialog means names can be "reserved" without the file actually existing
            
            if (intFileChooserReturnValue == JFileChooser.APPROVE_OPTION)   //if filepath is valid
            {
                File fileFilename = fc.getSelectedFile();
                if (fileFilename.getName().contains(".") == true)
                {
                    strFilename = fileFilename.getName().substring(0, fileFilename.getName().indexOf(".") );    //gets the filename without the file extension
                }
                else
                {
                    strFilename = fileFilename.getName();
                }
                txtfFilename.setText(strFilename);
            }
            else if (intFileChooserReturnValue == JFileChooser.ERROR_OPTION)
            {
                throw new Exception();  //not sure what pops error, but if one can choose an invalid filepath, then maybe pop an error dialog about invalid target?
            }
        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(this, e.toString(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnFilenameActionPerformed

    private void txtfFilenameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtfFilenameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtfFilenameActionPerformed

    private void txtfFilepathActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtfFilepathActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtfFilepathActionPerformed

    /**
     * TODO: Check that having this focused and then say, clicking on Generate Report updates the variables correctly before btnGenerateReport fires.
     * @param evt 
     */
    private void txtfFilepathFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtfFilepathFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtfFilepathFocusLost

    /**
     * TODO: Check that having this focused and then say, clicking on Generate Report updates the variables correctly before btnGenerateReport fires.
     * @param evt 
     */
    private void txtfFilenameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtfFilenameFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtfFilenameFocusLost

    private void btnContentsAssignSelectedContentItemToTableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnContentsAssignSelectedContentItemToTableActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnContentsAssignSelectedContentItemToTableActionPerformed

    /**
     * Selects rbtnDocx and sets intFiletype to DOCX.
     * @param evt 
     */
    private void rbtnDocxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtnDocxActionPerformed
        rbtnDocx.setSelected(true);
        intFiletype = DOCX_ONLY;
    }//GEN-LAST:event_rbtnDocxActionPerformed

    private void btnRemoveComponentFromTableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveComponentFromTableActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnRemoveComponentFromTableActionPerformed

    private void jtpEditorFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtpEditorFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_jtpEditorFocusGained

    /**
     * When selected tab changes to Content (middle tab), information is loaded in from the database.
     * @param evt 
     */
    private void jtpEditorStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jtpEditorStateChanged
        if (jtpEditor.getSelectedIndex() == 1)
        {
            //TODO: Should also detect DB changes, or at least include a refresh button somewhere
        }
        //System.out.println("[DEBUG] Current tab name=" + jTabbedPanel.getTitleAt(jTabbedPanel.getSelectedIndex() ) + " | Current tab index=" + jTabbedPanel.getSelectedIndex() );
    }//GEN-LAST:event_jtpEditorStateChanged

    /**
     * Loads the selected profile.
     * TODO: Should probably also make sure it's okay to load (and lose any possible changes to the current profile/report) before actually loading.
     * @param evt 
     */
    private void btnLoadProfileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoadProfileActionPerformed
        //TODO: Add actual loading code
        intSelectedProfile = lstProfile.getSelectedIndex();
        System.out.println("[DEBUG] Filepath=" + strFilepath + " | Filename=" + strFilename + " | Filetype=" + intFiletype);
    }//GEN-LAST:event_btnLoadProfileActionPerformed

    /**
     * Selects rbtnDocxAndPdf and sets intFiletype to DOCX and PDF.
     * @param evt 
     */
    private void rbtnDocxAndPdfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtnDocxAndPdfActionPerformed
        rbtnDocxAndPdf.setSelected(true);
        intFiletype = DOCX_AND_PDF;
    }//GEN-LAST:event_rbtnDocxAndPdfActionPerformed

    /**
     * Selects rbtnPdf and sets intFiletype to PDF.
     * @param evt 
     */
    private void rbtnPdfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtnPdfActionPerformed
        rbtnPdf.setSelected(true);
        intFiletype = PDF_ONLY;
    }//GEN-LAST:event_rbtnPdfActionPerformed

    /**
     * Adds selected element from lstTemplateDefaultComponents to lstTemplateComponents (by way of lmodelLstTemplateComponents).
     * @param evt 
     */
    private void btnTemplateAddComponentToTemplateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTemplateAddComponentToTemplateActionPerformed
        if (lstTemplateDefaultComponents.getSelectedIndex() != -1)
        {
            lmodelLstTemplateComponents.addElement(lstTemplateDefaultComponents.getSelectedValue() );
            if (lstTemplateDefaultComponents.getSelectedValue().contains("{") == true)  //if the selected component is an array of some sort
            {
                //TODO: Create new array and associate it with the added component
            }
        }
    }//GEN-LAST:event_btnTemplateAddComponentToTemplateActionPerformed

    /**
     * Removes selected element from lstTemplateComponents (by way of lmodelLstTemplateComponents).
     * @param evt 
     */
    private void btnTemplateRemoveComponentFromTemplateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTemplateRemoveComponentFromTemplateActionPerformed
        if (lstTemplateComponents.getSelectedIndex() != -1)
        {
            //if deleting a table component
            if (lstTemplateComponents.getSelectedValue().contains("{") == true)
            {
                jpTemplateTable.setVisible(false);
            }
            lmodelLstTemplateComponents.removeElementAt(lstTemplateComponents.getSelectedIndex() );
        }
    }//GEN-LAST:event_btnTemplateRemoveComponentFromTemplateActionPerformed

    /**
     * Adds selected element from lstTemplateDefaultComponents to aTemplateComponents (by way of lmodelLstTemplateComponents).
     * @param evt 
     */
    private void btnAddComponentToTableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddComponentToTableActionPerformed
        //if a non-table component is selected in lstTemplateComponents and tableTemplateTableContents has at least 1 cell (at least 1 row and at least 1 column)
        if (lstTemplateComponents.getSelectedIndex() != -1 && lstTemplateComponents.getSelectedValue().contains("{") == false && tableTemplateTableContents.getRowCount() > 0 && tableTemplateTableContents.getColumnCount() > 0)
        {
            tableTemplateTableContents.setValueAt(evt, tableTemplateTableContents.getSelectedRow(), tableTemplateTableContents.getSelectedColumn() );
        }
    }//GEN-LAST:event_btnAddComponentToTableActionPerformed

    /**
     * Shows jpTemplateTable (array-related components) if lstTemplateComponents's selected value is an array.
     * Disables related components if lstTemplateComponents's selected value is not an array.
     * @param evt 
     */
    private void lstTemplateComponentsValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_lstTemplateComponentsValueChanged
        if (lstTemplateComponents.isSelectionEmpty() == false)
        {
            if (lstTemplateComponents.getSelectedValue().contains("{") == true)
            {
                //shows array-related components
                jpTemplateTable.setVisible(true);

                //TODO: Load array's components into tableTemplateTableContents

                //load in table's row and column values for the array
                if (lstTemplateComponents.getSelectedValue().contains("COLUMNS") == true)
                {
                    spinnerColumns.setValue(0);
                }
                else
                {
                    //returns the selected component's COLUMNS value by searching for the indexes of ["{", ",").
                    spinnerColumns.setValue(lstTemplateComponents.getSelectedValue().substring(lstTemplateComponents.getSelectedValue().indexOf("{"), lstTemplateComponents.getSelectedValue().indexOf(",") ) );
                }

                if (lstTemplateComponents.getSelectedValue().contains("ROWS") == true)
                {
                    spinnerRows.setValue(0);
                }
                else
                {
                    //returns the selected component's ROWS value by searching for the indexes of [",", "}").
                    spinnerRows.setValue(lstTemplateComponents.getSelectedValue().substring(lstTemplateComponents.getSelectedValue().indexOf(","), lstTemplateComponents.getSelectedValue().indexOf("}") ) );
                }

            }   //if (lstTemplateComponents.getSelectedValue().contains("{") == true)
            else
            {
                //hide array-related components
                jpTemplateTable.setVisible(false);
            }
        }
    }//GEN-LAST:event_lstTemplateComponentsValueChanged

    /**
     * Updates tableTemplateTableContents with the non-negative values in spinnerColumns and spinnerRows.
     * @param evt 
     */
    private void btnTemplateUpdateTableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTemplateUpdateTableActionPerformed
        //TODO: tableTemplateTableContents should probably not have the column sort/name buttons? showing
        //tableTemplateTableContents should also probably be 1 column x 2 rows at minimum,
        //with the first row being editable somehow for titles, perhaps, or maybe if using the
        //"1 row = template for the entire table" idea, then the first row, when pulling in data from
        //the database, should instead load in cleaner-looking defaults based on the components in the cells
        //(e.g., TASK = Task or Assignment or something not all in caps).
    }//GEN-LAST:event_btnTemplateUpdateTableActionPerformed

    /**
     * Creates a new template profile.
     * @param evt 
     */
    private void btnNewProfileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewProfileActionPerformed
        String strOutput = new String();
        strOutput = JOptionPane.showInputDialog(this, "Enter the new profile's name below.");
        if (strOutput.isEmpty() == false && strOutput != null)
        {
            System.out.println("[DEBUG] New profile can be created successfully.");
            //TODO
            //iterate through preexisting list to make sure new name is not duplicate
            //  if duplicate,
            //      break
            //add new profile to lstProfile
            //add new entry in profiles.txt or something
        }
        else
        {
            System.out.println("[DEBUG] New profile cannot be created successfully (null or empty string).");
        }
    }//GEN-LAST:event_btnNewProfileActionPerformed

    /**
     * Enables and loads into cmbCourse the available courses for the selected department.
     * @param evt 
     */
    private void cmbDepartmentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbDepartmentActionPerformed
        if (cmbDepartment.getSelectedItem().equals("---") == false)
        {
            try
            {
                cmbCourse.removeAllItems();
                cmbCourse.addItem("---");
                Statement st = dbLocalConnection.createStatement();
                ResultSet rs = st.executeQuery("SELECT COURSE.intNumber " +
                    "FROM COURSE " +
                    "WHERE COURSE.fkDEPARTMENT_intID = (SELECT intID " +
                    "    FROM DEPARTMENT " +
                    "    WHERE LIKE(DEPARTMENT.vchrName, \"" + cmbDepartment.getSelectedItem().toString() + "\") " +
                    "    )");

                while (rs.next())
                {
                    cmbCourse.addItem(rs.getString("intNumber") );
                }
                cmbCourse.setEnabled(true);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(jpEditCourse.class.getName()).log(Level.SEVERE, null, ex);
                //in case cmbCourse was still enabled from a previous event trigger and something went wrong,
                //to prevent breaking cmbCourse
                //resets cmbCourse to "unused" state for tidying up
                cmbCourse.setEnabled(false);
                cmbCourse.removeAllItems();
                cmbCourse.addItem("---");
            }
        }
        else
        {
            //resets cmbCourse to "unused" state for tidying up
            cmbCourse.setEnabled(false);
            cmbCourse.removeAllItems();
            cmbCourse.addItem("---");
        }
    }//GEN-LAST:event_cmbDepartmentActionPerformed

//    for testing/debugging in isolation
//    /**
//     * @param args the command line arguments
//     */
//        public static void main(String[] args) {
//            
//        jfReport test = new jfReport();
//        test.setVisible(true);
//        
//
//        } //main
        
   /* public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ReportFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ReportFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ReportFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ReportFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form 
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ReportFrame().setVisible(true);
            }
        });
    }*/
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddComponentToTable;
    private javax.swing.JButton btnContentsAssignAllContentItemsToList;
    private javax.swing.JButton btnContentsAssignAllContentItemsToTable;
    private javax.swing.JButton btnContentsAssignSelectedContentItemToList;
    private javax.swing.JButton btnContentsAssignSelectedContentItemToTable;
    private javax.swing.JButton btnContentsRemoveAllContentItemsFromList;
    private javax.swing.JButton btnContentsRemoveAllContentItemsFromTable;
    private javax.swing.JButton btnContentsRemoveSelectedContentItemFromList;
    private javax.swing.JButton btnContentsRemoveSelectedContentItemFromTable;
    private javax.swing.JButton btnDeleteProfile;
    private javax.swing.JButton btnFilename;
    private javax.swing.JButton btnFilepath;
    private javax.swing.JButton btnGenerateReport;
    private javax.swing.JButton btnLoadProfile;
    private javax.swing.JButton btnNewProfile;
    private javax.swing.JButton btnRemoveComponentFromTable;
    private javax.swing.JButton btnSaveProfile;
    private javax.swing.JButton btnTemplateAddComponentToTemplate;
    private javax.swing.JButton btnTemplateRemoveComponentFromTemplate;
    private javax.swing.JButton btnTemplateUpdateTable;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cmbCourse;
    private javax.swing.JComboBox<String> cmbDepartment;
    private javax.swing.JComboBox<String> cmbSection;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JPanel jpClass;
    private javax.swing.JPanel jpContents;
    private javax.swing.JPanel jpGenerateReport;
    private javax.swing.JPanel jpGeneration;
    private javax.swing.JPanel jpProfile;
    private javax.swing.JPanel jpTemplate;
    private javax.swing.JPanel jpTemplateTable;
    private javax.swing.JPanel jpTemplateTableSize;
    private javax.swing.JScrollPane jspListContentsAssignedComponents;
    private javax.swing.JTabbedPane jtpEditor;
    private javax.swing.JLabel lblColumns;
    private javax.swing.JLabel lblCourse;
    private javax.swing.JLabel lblDepartment;
    private javax.swing.JLabel lblRows;
    private javax.swing.JLabel lblSection;
    private javax.swing.JList<String> listContentsAssignedContentItems;
    private javax.swing.JList<String> lstContentsAvailableContentItems;
    private javax.swing.JList<String> lstProfile;
    private javax.swing.JList<String> lstTemplateComponents;
    private javax.swing.JList<String> lstTemplateDefaultComponents;
    private javax.swing.JRadioButton rbtnDocx;
    private javax.swing.JRadioButton rbtnDocxAndPdf;
    private javax.swing.JRadioButton rbtnPdf;
    private javax.swing.JScrollPane scpListProfile;
    private javax.swing.JSpinner spinnerColumns;
    private javax.swing.JSpinner spinnerRows;
    private javax.swing.JTable tableContentsAssignedContentItems;
    private javax.swing.JTable tableTemplateTableContents;
    private javax.swing.JTextField txtfFilename;
    private javax.swing.JTextField txtfFilepath;
    // End of variables declaration//GEN-END:variables
}
