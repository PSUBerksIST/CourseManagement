/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.psu.berksist.CourseEZ;

import java.awt.event.ItemEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import static java.lang.Thread.sleep;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import org.docx4j.Docx4J;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.exceptions.InvalidFormatException;
import org.docx4j.openpackaging.packages.ProtectDocument;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.wml.STDocProtect;

/**
 *
 * @author Deathx, jss5783
 * 
 *  ******************* MODIFICATION LOG *****************************************
 *  2017 May  1     -   Unhid the Content tab.
 *                      Clicking on the Content tab checks for selected class (department-course-section) and non-empty template.
 *                      Added ListModel and DefaultTableModel array for Content tab.
 *                          (Not registered yet to preserve examples for future teams for now.) -JSS5783
 * 
 * 2017 April 30   -   !BUG: Tried to implement dynamic report generation.
 *                          Turns out ResultSets can't be reused, and the Preview tab is probably needed as a step in general,
 *                              not just for cleanup/quality-checking.
 *                      !BUG: Loading bar and label don't display as intended.
 *                      The only table component is now TABLE{COLUMNS,ROWS}.
 *                      Some more components added. -JSS5783
 * 
 *  2017 April 29   -   Tables can now be created, customized (number of columns and rows; adding/deleting contents), and deleted,
 *                          with necessary backend variables implemented.
 *                      !BUGFIX: Selecting department/class/section now works as intended, with output being sorted in ascending order.
 *                      Additional components added to lstTemplateDefaultcomponents.
 *                      Various variables renamed to try for more naming consistency.
 *                      !BUGFIX: Filepath now adds all of its delimiters the right way, depending on what OS the application is running on. -JSS5783
 * 
 *  2017 April 28   -   !BUGFIX: Opening jfReport doesn't create duplicate (currently hard-coded) profiles (lmodelLstTemplateProfiles is no longer static).
 *                      Still trying to fix the Class choosers. -JSS5783
 * 
 *  2017 April 27   -   !BUG: Selecting anything after selecting from one of the Department/Course/Section dialogs throws an error and doesn't work.
 *                          As far as I can tell, it's triggering the other ActionListeners.
 *                          !WORKAROUND: Updates on FocusLost. It kind of works now.
 *                      Can now add and delete profiles, list-wise. (No dynamic profiles yet.)
 *                      Started work on progress bar. Reports.java code should probably be moved here so milestones can update the progress bar. -JSS5783
 * 
 *  2017 April 27   -   Departments now loaded into cmbDepartment upon jfReport's creation.
 *                      Courses are loaded into cmbCourse when cmbDepartment has an actual department selected.
 *                      Beginnings of adding new profile functionality.
 *                      Content tab "hidden" (deleted, because disabling it doesn't work as desired) on jfReport's creation. -JSS5783
 * 
 *  2017 April 25   -   !BUGFIX: Shows error message when no profile is loaded and the user tries to generate files.
 *                      Rearranged Template tab so table-related components can be easily hidden/shown as needed.
 *                      Components can now be added and removed from lstTemplateComponents
 *                          (technically lmodelLstTemplateComponents).
 *                      Added (currently blank) Preview tab.
 *                      Disabled cmbCourse and cmbSection in preparation for loading in actual departments/etc.
 *                          from database. -JSS5783
 * 
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
    private static final int MAX_COMPONENTS = 300;  //the maximum number of components the main template can have (which affects the tables that may or may not be in a template); since Java doesn't have good dynamic arrays, preallocate a chunk of memory for the array instead
    private static final int MAX_COLUMNS = 100;     //the maximum number of columns a table can have
    private static final int MAX_ROWS = 100;        //the maximum number of rows a table can have
    private static int intFiletype = DOCX_AND_PDF;
    private static DefaultListModel lmodelLstTemplateComponents = new DefaultListModel();
//    private static DefaultListModel lmodelLstProfile = new DefaultListModel();
    private DefaultListModel lmodelLstProfile = new DefaultListModel();
    private DefaultListModel lmodelLstContentAssignedContentItems = new DefaultListModel();
    //private static String[] aLstTemplateComponents;
    private DefaultTableModel[] aLstTemplateComponents = new DefaultTableModel[MAX_COMPONENTS];
    private DefaultTableModel[] aLstLstContentAssignedContentItems = new DefaultTableModel[MAX_COMPONENTS];
    private static Statement st;
    private static ResultSet rs;
    private static boolean bDepartmentsReady = false;
    private static boolean bCoursesReady = false;
    private static boolean bSectionsReady = false;
    
    
    
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
        lblGenerationProgress.setVisible(false);
        jpbGenerationProgress.setVisible(false);
        
        //TODO: remove dummy profiles here in favor of dynamically loading real ones from a file
//        if (lmodelLstProfile.isEmpty() == true) //only load if list is empty, otherwise duplicates result when jfReport is opened multiple times
//        {
            lmodelLstProfile.addElement("Assignment");
            lmodelLstProfile.addElement("Syllabus");
//        }
        
        //jtpEditor.removeTabAt(1);   //removes the Content tab
        //jtpEditor.add(jpContent, 1);    //re-adds the Content tab
        
//        cmbDepartment.addItem("---");
//        cmbCourse.addItem("---");
//        cmbSection.addItem("---");
        
        //load departments into cmbDepartment
        try
        {
            st = dbLocalConnection.createStatement();
            rs = st.executeQuery("SELECT vchrName FROM DEPARTMENT " +
                                 "ORDER BY vchrName ASC");
            
            while (rs.next() )
            {
                System.out.println("[DEBUG] vchrName=" + rs.getString("vchrName") );
                cmbDepartment.addItem(rs.getString("vchrName") );
            }
        }
        catch (SQLException ex)
        {
            Logger.getLogger(jfReport.class.getName()).log(Level.SEVERE, null, ex);
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
        tblTemplateComponentsTable = new javax.swing.JTable();
        jpContent = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        lstContentAvailableContentItems = new javax.swing.JList<>();
        jspListContentsAssignedComponents = new javax.swing.JScrollPane();
        lstContentAssignedContentItems = new javax.swing.JList<>();
        jScrollPane7 = new javax.swing.JScrollPane();
        tblContentAssignedContentItems = new javax.swing.JTable();
        btnContentRemoveSelectedContentItemFromList = new javax.swing.JButton();
        btnContentAssignSelectedContentItemToList = new javax.swing.JButton();
        btnContentAssignSelectedContentItemToTable = new javax.swing.JButton();
        btnContentRemoveSelectedContentItemFromTable = new javax.swing.JButton();
        btnContentAssignAllContentItemsToList = new javax.swing.JButton();
        btnContentRemoveAllContentItemsFromList = new javax.swing.JButton();
        btnContentAssignAllContentItemsToTable = new javax.swing.JButton();
        btnContentRemoveAllContentItemsFromTable = new javax.swing.JButton();
        jpPreview = new javax.swing.JPanel();
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
        jpbGenerationProgress = new javax.swing.JProgressBar();
        lblGenerationProgress = new javax.swing.JLabel();

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
        btnDeleteProfile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteProfileActionPerformed(evt);
            }
        });

        btnSaveProfile.setText("Save");
        btnSaveProfile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveProfileActionPerformed(evt);
            }
        });

        lstProfile.setModel(lmodelLstProfile);
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

        cmbDepartment.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "---" }));
        cmbDepartment.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbDepartmentItemStateChanged(evt);
            }
        });

        cmbCourse.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "---" }));
        cmbCourse.setEnabled(false);
        cmbCourse.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbCourseItemStateChanged(evt);
            }
        });

        cmbSection.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "---" }));
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

        lstTemplateDefaultComponents.setBorder(javax.swing.BorderFactory.createTitledBorder("Default Components"));
        lstTemplateDefaultComponents.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "TABLE{COLUMNS,ROWS}", "ASSIGNMENT_DESCRIPTION", "ASSIGNMENT_NAME", "AUTHOR", "COURSE_DESCRIPTION", "COURSE_NAME", "COURSE_NUMBER", "CLASS_NUMBER", "DATE", "DIVIDER", "GROUP_PROJECT_DESCRIPTION", "GROUP_PROJECT_NAME", "INDIVIDUAL_PROJECT_DESCRIPTION", "INDIVIDUAL_PROJECT_NAME", "NEWLINE", "PAGEBREAK", "SECTION_NUMBER", "SYLLABUS_ACADEMIC_INTEGRITY", "SYLLABUS_ASSIGNMENT_STANDARDS", "SYLLABUS_COURSE_FORMAT", "SYLLABUS_DISABILITY", "SYLLABUS_GRADING", "SYLLABUS_MODIFICATION", "TASK_NAME", "TEXTBOOK_IMAGE", "TEXTBOOK_INFORMATION", "TEXTBOOK_IS_REQUIRED_OR_OPTIONAL" };
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

        tblTemplateComponentsTable.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        tblTemplateComponentsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane4.setViewportView(tblTemplateComponentsTable);

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

        lstContentAvailableContentItems.setBorder(javax.swing.BorderFactory.createTitledBorder("Unassigned Content Items"));
        lstContentAvailableContentItems.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Assignment 1", "Assignment 4", "Assignment 5", "Group Project 1", "Individual Project 1" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        lstContentAvailableContentItems.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane5.setViewportView(lstContentAvailableContentItems);

        lstContentAssignedContentItems.setBorder(javax.swing.BorderFactory.createTitledBorder("Assigned Content Items"));
        lstContentAssignedContentItems.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Networking", "[[PAGEBREAK]]", "[[COURSE_DESCRIPTION]]", "[[CONTACT_INFORMATION]]", "[[RESOURCE_REQUIREMENTS{3,1}]]", "[[SCHEDULE{2,3}]]" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        lstContentAssignedContentItems.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jspListContentsAssignedComponents.setViewportView(lstContentAssignedContentItems);

        tblContentAssignedContentItems.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        tblContentAssignedContentItems.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"April 10", "Assignment 2"},
                {"April 12", "Assignment 3"},
                {"April 14", "Assignment 6"}
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
        jScrollPane7.setViewportView(tblContentAssignedContentItems);

        btnContentRemoveSelectedContentItemFromList.setText(">");

        btnContentAssignSelectedContentItemToList.setText("<");

        btnContentAssignSelectedContentItemToTable.setText("<");

        btnContentRemoveSelectedContentItemFromTable.setText(">");

        btnContentAssignAllContentItemsToList.setText("<<");

        btnContentRemoveAllContentItemsFromList.setText(">>");

        btnContentAssignAllContentItemsToTable.setText("<<");

        btnContentRemoveAllContentItemsFromTable.setText(">>");

        javax.swing.GroupLayout jpContentLayout = new javax.swing.GroupLayout(jpContent);
        jpContent.setLayout(jpContentLayout);
        jpContentLayout.setHorizontalGroup(
            jpContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpContentLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jspListContentsAssignedComponents, javax.swing.GroupLayout.DEFAULT_SIZE, 277, Short.MAX_VALUE)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnContentRemoveSelectedContentItemFromList)
                    .addComponent(btnContentAssignSelectedContentItemToList)
                    .addComponent(btnContentAssignSelectedContentItemToTable)
                    .addComponent(btnContentRemoveSelectedContentItemFromTable)
                    .addComponent(btnContentAssignAllContentItemsToList)
                    .addComponent(btnContentRemoveAllContentItemsFromList)
                    .addComponent(btnContentAssignAllContentItemsToTable)
                    .addComponent(btnContentRemoveAllContentItemsFromTable))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 408, Short.MAX_VALUE)
                .addContainerGap())
        );
        jpContentLayout.setVerticalGroup(
            jpContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpContentLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpContentLayout.createSequentialGroup()
                        .addComponent(jspListContentsAssignedComponents, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                    .addGroup(jpContentLayout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addComponent(btnContentAssignAllContentItemsToList)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnContentAssignSelectedContentItemToList)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnContentRemoveSelectedContentItemFromList)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnContentRemoveAllContentItemsFromList)
                        .addGap(54, 54, 54)
                        .addComponent(btnContentAssignAllContentItemsToTable)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnContentAssignSelectedContentItemToTable)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnContentRemoveSelectedContentItemFromTable)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnContentRemoveAllContentItemsFromTable))
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );

        jtpEditor.addTab("Content", jpContent);

        javax.swing.GroupLayout jpPreviewLayout = new javax.swing.GroupLayout(jpPreview);
        jpPreview.setLayout(jpPreviewLayout);
        jpPreviewLayout.setHorizontalGroup(
            jpPreviewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 753, Short.MAX_VALUE)
        );
        jpPreviewLayout.setVerticalGroup(
            jpPreviewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 381, Short.MAX_VALUE)
        );

        jtpEditor.addTab("Preview", jpPreview);

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

        lblGenerationProgress.setText("[PROGRESS_BAR]");

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
                .addContainerGap(14, Short.MAX_VALUE))
            .addGroup(jpGenerationLayout.createSequentialGroup()
                .addGap(317, 317, 317)
                .addComponent(btnGenerateReport)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jpGenerationLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jpGenerationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblGenerationProgress)
                    .addComponent(jpbGenerationProgress, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                .addComponent(lblGenerationProgress)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jpbGenerationProgress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnGenerateReport)
                .addContainerGap(36, Short.MAX_VALUE))
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
                
                strFilepath = fc.getSelectedFile().toString();
                
                //append filepath delimiter onto the end so it's prepared for file generation (just stick on [filename] and .[filetype] and generate).
                if (System.getProperty("os.name").toLowerCase().contains("win") == true)    //if Windows OS
                {
                    strFilepath += "\\";
                }
                //if Apple or Linux/Unix OS
                else if (System.getProperty("os.name").toLowerCase().contains("mac") == true || System.getProperty("os.name").toLowerCase().contains("nux") == true || System.getProperty("os.name").toLowerCase().contains("nix") == true)
                {
                    strFilepath += "/";
                }
                else    //if non-standard OS, like FreeBSD
                {
                    //use standard forward slash (/), I guess
                    strFilepath += "/";
                }
                
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
        
        if (cmbDepartment.getSelectedItem().toString() != "---" && cmbCourse.getSelectedItem().toString() != "---" && cmbSection.getSelectedItem().toString() != "---")
        {
            if (intSelectedProfile != -1)
            {
                //TODO: use currently edited profile, regardless of whether or not changes have been saved
                //TODO: replace temporary code for testing generateReport()
                    try
                    {
                        generateReport(dbLocalConnection, strFilepath, strFilename, intFiletype);
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
            }
            else
            {
                JOptionPane.showMessageDialog(this, "No profile loaded.","Error", JOptionPane.ERROR_MESSAGE);
            }
//            switch(intSelectedProfile)
//            {
//                case -1:    //nothing selected
//                    JOptionPane.showMessageDialog(this, "No profile loaded.","Error", JOptionPane.ERROR_MESSAGE);
//                    break;
//                case 0:     //Assignment
//                    try
//                    {
//                        report.Assignment(dbLocalConnection, strFilepath, strFilename, intFiletype);
//                    }
//                    catch (Docx4JException ex)
//                    {
//                        //Logger.getLogger(jfReport.class.getName()).log(Level.SEVERE, null, ex);
//                        System.err.println(ex.toString() );
//                    }
//                    catch (FileNotFoundException ex)
//                    {
//                        //Logger.getLogger(jfReport.class.getName()).log(Level.SEVERE, null, ex);
//                        System.err.println(ex.toString() );
//                    }
//                    //TODO: Reports.Assignment() needs to be fleshed out more rather than just basic dummy data
//                    catch (SQLException ex)
//                    {
//                        //Logger.getLogger(jfReport.class.getName() ).log(Level.SEVERE, null, ex);
//                        System.err.println(ex.toString() );
//                    }
//                    break;
//                case 1: //Syllabus
//                    try
//                    {
//                        report.Syllabus(dbLocalConnection, strFilepath, strFilename, intFiletype);
//                    }
//                    catch (Docx4JException ex)
//                    {
//                        //Logger.getLogger(jfReport.class.getName()).log(Level.SEVERE, null, ex);
//                        System.err.println(ex.toString() );
//                    }
//                    catch (FileNotFoundException ex)
//                    {
//                        //Logger.getLogger(jfReport.class.getName()).log(Level.SEVERE, null, ex);
//                        System.err.println(ex.toString() );
//                    }
//                    catch (SQLException ex)
//                    {
//                        //Logger.getLogger(jfReport.class.getName() ).log(Level.SEVERE, null, ex);
//                        System.err.println(ex.toString() );
//                    }
//                    break;
//                default:    //other (temporary, for testing generateReport() )
//                    try
//                    {
//                        generateReport(dbLocalConnection, strFilepath, strFilename, intFiletype);
//                    }
//                    catch (Docx4JException ex)
//                    {
//                        //Logger.getLogger(jfReport.class.getName()).log(Level.SEVERE, null, ex);
//                        System.err.println(ex.toString() );
//                    }
//                    catch (FileNotFoundException ex)
//                    {
//                        //Logger.getLogger(jfReport.class.getName()).log(Level.SEVERE, null, ex);
//                        System.err.println(ex.toString() );
//                    }
//                    catch (SQLException ex)
//                    {
//                        //Logger.getLogger(jfReport.class.getName() ).log(Level.SEVERE, null, ex);
//                        System.err.println(ex.toString() );
//                    }
//                    break;
//            }   //end of switch(intSelectedProfile)
        }
        else
        {
            JOptionPane.showMessageDialog(this, "A department, class, and section must be selected before a report can be generated.", "Error", JOptionPane.ERROR_MESSAGE);
        }
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

    /**
     * Selects rbtnDocx and sets intFiletype to DOCX.
     * @param evt 
     */
    private void rbtnDocxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtnDocxActionPerformed
        rbtnDocx.setSelected(true);
        intFiletype = DOCX_ONLY;
    }//GEN-LAST:event_rbtnDocxActionPerformed

    /**
     * Removes the component from the selected cell in tblTemplateComponentsTable.
     * @param evt 
     */
    private void btnRemoveComponentFromTableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveComponentFromTableActionPerformed
        if (tblTemplateComponentsTable.getSelectedColumn() != -1 && tblTemplateComponentsTable.getSelectedRow() != -1)
        {
            aLstTemplateComponents[lstTemplateComponents.getSelectedIndex() ].setValueAt(null, tblTemplateComponentsTable.getSelectedRow(), tblTemplateComponentsTable.getSelectedColumn() );
            //doesn't deselect cleared cell afterward, but that's better for ergonomics anyway; fix mistake, easily add intended component
        }
    }//GEN-LAST:event_btnRemoveComponentFromTableActionPerformed

    /**
     * When selected tab changes to:
     * Content, components are copied over
     * Preview, information is loaded in from the database.
     * @param evt 
     */
    private void jtpEditorStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jtpEditorStateChanged
        if (jtpEditor.getTitleAt(jtpEditor.getSelectedIndex() ).equals("Preview") == true)
        {
            /*
                TODO: Should also detect DB changes, or at least include a refresh button somewhere
                check if content tab is correctly filled (all components replaced with actual content for a specific class)
            */
        }
        else if (jtpEditor.getTitleAt(jtpEditor.getSelectedIndex() ).equals("Content") == true)
        {
            if (cmbDepartment.getSelectedItem().equals("---") == false && cmbCourse.getSelectedItem().equals("---") == false && cmbSection.getSelectedItem().equals("---") == false && lmodelLstTemplateComponents.isEmpty() == false)
            {
                if (AppConstants.DEBUG_MODE == true)
                {
                    System.out.println("[DEBUG] Content tab is now selected. Valid class is selected AND template is not empty!");
                }
            }
            else
            {
                if (AppConstants.DEBUG_MODE == true)
                {
                    System.out.println("[DEBUG] Content tab is now selected. No valid class selected OR empty template!");
                }
                //Check if Content tab is filled out
                JOptionPane.showMessageDialog(this, "Class must be selected and component template must not be empty.", "Error", JOptionPane.ERROR_MESSAGE);
                jtpEditor.setSelectedIndex(0);  //switches back to Template tab
            }
        }
        //System.out.println("[DEBUG] Current tab name=" + jtpEditor.getTitleAt(jtpEditor.getSelectedIndex() ) + " | Current tab index=" + jtpEditor.getSelectedIndex() );
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
     * Adds selected element from lstTemplateDefaultComponents to lstTemplateComponents (by way of lmodelLstTemplateComponents), along with any corresponding table objects.
     * @param evt 
     */
    private void btnTemplateAddComponentToTemplateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTemplateAddComponentToTemplateActionPerformed
        if (lstTemplateDefaultComponents.getSelectedIndex() != -1)
        {
            if (lstTemplateDefaultComponents.getSelectedValue().contains("{") == true)  //if the selected component is a table of some sort
            {
                //adds new array at end of list, using lmodelLstTemplateComponents's 1-based getSize() for aLstTemplateComponents's 0-based position of the new array
                lmodelLstTemplateComponents.addElement(lstTemplateDefaultComponents.getSelectedValue().replace("COLUMNS", "1").replace("ROWS", "1") );
                //((trying to resize the array for the new component))
                //aLstTemplateComponents = new DefaultTableModel[aLstTemplateComponents.length + 1];
                //aLstTemplateComponents = Arrays.copyOf(aLstTemplateComponents, aLstTemplateComponents.length + 1);
                //aLstTemplateComponents[lmodelLstTemplateComponents.getSize() ] = "";
                aLstTemplateComponents[lmodelLstTemplateComponents.getSize() - 1] = new DefaultTableModel();
                aLstTemplateComponents[lmodelLstTemplateComponents.getSize() - 1].setColumnCount(1);
                aLstTemplateComponents[lmodelLstTemplateComponents.getSize() - 1].setRowCount(1);
                if (AppConstants.DEBUG_MODE == true)
                {
                    System.out.println("[DEBUG] aLstTemplateComponents[" + lmodelLstTemplateComponents.getSize() + " - 1]=" + "Columns(" + aLstTemplateComponents[lmodelLstTemplateComponents.getSize() - 1].getColumnCount() + "), Rows(" + aLstTemplateComponents[lmodelLstTemplateComponents.getSize() - 1].getRowCount() + ")");
                }
            }
            else
            {
                lmodelLstTemplateComponents.addElement(lstTemplateDefaultComponents.getSelectedValue() );
                //if the selected component is not a table, then the corresponding position in aLstTemplateComponents remains NULL
            }
        }
    }//GEN-LAST:event_btnTemplateAddComponentToTemplateActionPerformed

    /**
     * Removes selected element from lstTemplateComponents (by way of lmodelLstTemplateComponents), along with any corresponding tables.
     * @param evt 
     */
    private void btnTemplateRemoveComponentFromTemplateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTemplateRemoveComponentFromTemplateActionPerformed
        if (lstTemplateComponents.getSelectedIndex() != -1)
        {
            //if deleting a table component
            if (lstTemplateComponents.getSelectedValue().contains("{") == true)
            {
                //if aLstTemplateComponents[lstTemplateComponents.getSelectedIndex()] instanceof String[][] == false)
                int intResult = JOptionPane.showConfirmDialog(this, "Are you sure? This will delete any components in the table itself.", "Remove Table from Template", JOptionPane.YES_NO_OPTION);
                if (intResult == JOptionPane.YES_OPTION)
                {
                    aLstTemplateComponents[lstTemplateComponents.getSelectedIndex()] = null;    //hopefully clears out any preexisting table, allowing Java to free up the memory
                    lmodelLstTemplateComponents.removeElementAt(lstTemplateComponents.getSelectedIndex() );
                    jpTemplateTable.setVisible(false);
                }
                //if the user clicks NO, then do not delete the selected component in DefaultListModel
            }
            else    //if not table, just delete the component
            {
                lmodelLstTemplateComponents.removeElementAt(lstTemplateComponents.getSelectedIndex() );
            }
        }
    }//GEN-LAST:event_btnTemplateRemoveComponentFromTemplateActionPerformed

    /**
     * Adds selected element from lstTemplateDefaultComponents to aLstTemplateComponents (by way of lmodelLstTemplateComponents).
     * @param evt 
     */
    private void btnAddComponentToTableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddComponentToTableActionPerformed
        //if a non-table component is selected in lstTemplateComponents and tableTemplateTableContents has at least 1 cell (at least 1 row and at least 1 column)
        if (lstTemplateDefaultComponents.getSelectedIndex() != -1 && lstTemplateDefaultComponents.getSelectedValue().contains("{") == false && tblTemplateComponentsTable.getRowCount() >= 1 && tblTemplateComponentsTable.getColumnCount() >= 1 && tblTemplateComponentsTable.getSelectedColumn() != -1 && tblTemplateComponentsTable.getSelectedRow() != -1)
        {
            tblTemplateComponentsTable.setValueAt(lstTemplateDefaultComponents.getSelectedValue(), tblTemplateComponentsTable.getSelectedRow(), tblTemplateComponentsTable.getSelectedColumn() );
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
            //if (aLstTemplateComponents[lstTemplateComponents.getSelectedIndex() ] != null)  //if is table
            if (lstTemplateComponents.getSelectedValue().contains("{") == true) //if is table
            {
                if (AppConstants.DEBUG_MODE == true)
                {
                    System.out.println("[DEBUG] aLstTemplateComponents[" + lstTemplateComponents.getSelectedIndex() + "]=" + aLstTemplateComponents[lstTemplateComponents.getSelectedIndex() ] );
                }
                //shows table-related components
                jpTemplateTable.setVisible(true);
                
                //get column and row values for spinners
                spinnerColumns.setValue(aLstTemplateComponents[lstTemplateComponents.getSelectedIndex()].getColumnCount() );
                spinnerRows.setValue(aLstTemplateComponents[lstTemplateComponents.getSelectedIndex()].getRowCount() );
                
                //registers selected table to tblTemplateComponentsTable
                tblTemplateComponentsTable.setModel(aLstTemplateComponents[lstTemplateComponents.getSelectedIndex() ] );

//                //load in table's row and column values for the array
//                if (lstTemplateComponents.getSelectedValue().contains("COLUMNS") == true)
//                {
//                    spinnerColumns.setValue(1);
//                }
//                else
//                {
//                    //returns the selected component's COLUMNS value by searching for the indexes of ["{", ",").
//                    spinnerColumns.setValue(lstTemplateComponents.getSelectedValue().substring(lstTemplateComponents.getSelectedValue().indexOf("{"), lstTemplateComponents.getSelectedValue().indexOf(",") ) );
//                }
//
//                if (lstTemplateComponents.getSelectedValue().contains("ROWS") == true)
//                {
//                    spinnerRows.setValue(1);
//                }
//                else
//                {
//                    //returns the selected component's ROWS value by searching for the indexes of [",", "}").
//                    spinnerRows.setValue(lstTemplateComponents.getSelectedValue().substring(lstTemplateComponents.getSelectedValue().indexOf(","), lstTemplateComponents.getSelectedValue().indexOf("}") ) );
//                }

            }   //if (lstTemplateComponents.getSelectedValue().contains("{") == true)
            else
            {
                //hide array-related components
                jpTemplateTable.setVisible(false);
                if (AppConstants.DEBUG_MODE == true)
                {
                    System.out.println("[DEBUG] aLstTemplateComponents[" + lstTemplateComponents.getSelectedIndex() + "]=" + aLstTemplateComponents[lstTemplateComponents.getSelectedIndex() ] );
                }
            }
        }
    }//GEN-LAST:event_lstTemplateComponentsValueChanged

    /**
     * Updates tableTemplateTableContents with the non-negative, non-zero values in spinnerColumns and spinnerRows.
     * @param evt 
     */
    private void btnTemplateUpdateTableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTemplateUpdateTableActionPerformed
        //TODO: tableTemplateTableContents should probably not have the column sort/name buttons? showing
        //tableTemplateTableContents should also probably be 1 column x 2 rows at minimum,
        //with the first row being editable somehow for titles, perhaps, or maybe if using the
        //"1 row = template for the entire table" idea, then the first row, when pulling in data from
        //the database, should instead load in cleaner-looking defaults based on the components in the cells
        //(e.g., TASK = Task or Assignment or something not all in caps).
//        if ( (int) spinnerColumns.getValue() <= 0)  //if invalid number of columns, reset to preexisting value in selected table
//        {
//            if (lmodelLstTemplateComponents.getElementAt(lstTemplateComponents.getSelectedIndex() ) instanceof String[][] == true)
//            {
//                spinnerColumns.setValue(lstTemplateComponents.getSelectedValue().substring(lstTemplateComponents.getSelectedValue().indexOf("{"), lstTemplateComponents.getSelectedValue().indexOf(",") ) );
//            }
//        //else if NEW VALUE < OLD VALUE
//        //  ask if user wants to resize the table since it's potentially a lossy operation (adding extra space is free, but accidentally deleting added components is not
//        }
//        else
//        {
//        }
        //if new column or row size(s) is/are illegal or smaller than the current table column or row size(s)
        if ( (int) spinnerColumns.getValue() <= 0 || (int) spinnerRows.getValue() <= 0)
        {
            JOptionPane.showMessageDialog(this, "Illegal column and/or row size. Must have 1+ column(s) and 1+ row(s).", "Update Table Size", JOptionPane.ERROR_MESSAGE);
            
            spinnerColumns.setValue(aLstTemplateComponents[lstTemplateComponents.getSelectedIndex()].getColumnCount() );
            spinnerRows.setValue(aLstTemplateComponents[lstTemplateComponents.getSelectedIndex()].getRowCount() );
        }
        //if new column and/or row sizes are smaller than table's current column and/or row sizes
        else if ( (int) spinnerColumns.getValue() < tblTemplateComponentsTable.getColumnCount() || (int) spinnerRows.getValue() < tblTemplateComponentsTable.getRowCount() )
        {
            int intResult = JOptionPane.showConfirmDialog(this, "Column and/or row size is smaller than current table size(s). Resizing may result in lost data. Continue anyway?", "Update Table Size", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (intResult == JOptionPane.YES_OPTION)
            {
                aLstTemplateComponents[lstTemplateComponents.getSelectedIndex()].setColumnCount( (int) spinnerColumns.getValue() );
                aLstTemplateComponents[lstTemplateComponents.getSelectedIndex()].setRowCount( (int) spinnerRows.getValue() );
            }
        }
        else
        {
            aLstTemplateComponents[lstTemplateComponents.getSelectedIndex()].setColumnCount( (int) spinnerColumns.getValue() );
            aLstTemplateComponents[lstTemplateComponents.getSelectedIndex()].setRowCount( (int) spinnerRows.getValue() );
        }
    }//GEN-LAST:event_btnTemplateUpdateTableActionPerformed

    /**
     * Creates a new template profile.
     * @param evt 
     */
    private void btnNewProfileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewProfileActionPerformed
        String strNewProfileName = new String();
        boolean isDuplicate = false;
        
        strNewProfileName = JOptionPane.showInputDialog(this, "Enter the new profile's name below.");
        try
        {
            if (strNewProfileName.isEmpty() == false && strNewProfileName != null)
            {
                for (int i = 0; i < lmodelLstProfile.getSize(); i++)
                {
                    if (lmodelLstProfile.getElementAt(i).equals(strNewProfileName) == true)
                    {
                        isDuplicate = true;
                        break;
                    }
                }
                if (isDuplicate == false)
                {
                    System.out.println("[DEBUG] New profile can be created successfully.");
                    lmodelLstProfile.addElement(strNewProfileName);
                }
                else
                {
                    System.out.println("[DEBUG] New profile cannot be created: is duplicate.");
                }
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
        }
        catch (NullPointerException npe)    //thrown when canceled? TODO (low-priority): handle user clicking Cancel in input dialog properly instead of just catching the resulting exception
        {
            System.out.println("[DEBUG] Cancel button was probably clicked.\n" + npe.toString() );
        }
        catch (Exception e)
        {
            System.err.println("[DEBUG] " + e.toString() );
        }
    }//GEN-LAST:event_btnNewProfileActionPerformed

    /**
     * Delete selected template profile.
     * @param evt 
     */
    private void btnDeleteProfileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteProfileActionPerformed
        //TODO: Add DefaultListModel to lstProfile so it actually deletes the selected object
        if (lstProfile.getSelectedIndex() != -1)
        {
            //TODO (low-priority): "No" should be the default option, to avoid any accidental deletions
            int intDialogResult = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete " + lmodelLstProfile.getElementAt(lstProfile.getSelectedIndex() ) + "?", "Delete Profile", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (intDialogResult == JOptionPane.YES_OPTION)
            {
                lmodelLstProfile.removeElementAt(lstProfile.getSelectedIndex() );
            }
        }
    }//GEN-LAST:event_btnDeleteProfileActionPerformed

    /**
     * Enables and loads into cmbCourse the available courses for the selected department.
     * @param evt 
     */
    private void cmbDepartmentItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbDepartmentItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED)
        {
            bDepartmentsReady = false;
            bCoursesReady = false;
            bSectionsReady = false;

            try
            {
                sleep(100);
            }
            catch (InterruptedException ie)
            {
                System.out.println("[DEBUG] Interrupted");
            }

            if (cmbDepartment.getSelectedItem().equals("---") == false)
            //if (cmbDepartment.getSelectedIndex() != -1)
            {
                try
                {
                    cmbSection.setEnabled(false);
                    cmbCourse.setEnabled(false);
                    cmbCourse.removeAllItems();
                    cmbCourse.addItem("---");
                    st = dbLocalConnection.createStatement();
                    rs = st.executeQuery("SELECT intNumber " +
                        "FROM COURSE " +
                        "WHERE fkDEPARTMENT_intID = (SELECT intID " +
                        "    FROM DEPARTMENT " +
                        "    WHERE vchrName = \"" + cmbDepartment.getSelectedItem().toString() + "\") " +
                        "ORDER BY intNumber ASC");

                    while (rs.next())
                    {
                        System.out.println("[DEBUG] intNumber=" + rs.getString("intNumber") );
                        cmbCourse.addItem(rs.getString("intNumber") );
                    }
    //                cmbCourse.setSelectedIndex(-1);
                    cmbCourse.setEnabled(true);
                    bDepartmentsReady = true;
                }
                catch (SQLException ex)
                {
                    Logger.getLogger(jfReport.class.getName()).log(Level.SEVERE, null, ex);
                    //in case cmbCourse was still enabled from a previous event trigger and something went wrong,
                    //to prevent breaking cmbCourse
                    //resets cmbCourse to "unused" state for tidying up
                    cmbCourse.removeAllItems();
                    cmbCourse.addItem("---");
    //                cmbCourse.setSelectedIndex(-1);
                    cmbCourse.setEnabled(false);
                }
            }
            else
            {
                //resets cmbCourse to "unused" state for tidying up
                cmbCourse.removeAllItems();
                cmbCourse.addItem("---");
    //            cmbCourse.setSelectedIndex(-1);
                cmbCourse.setEnabled(false);
            }
        }
    }//GEN-LAST:event_cmbDepartmentItemStateChanged

    /**
     * Enables and loads into cmbSection the available classes for the selected course.
     * @param evt 
     */
    private void cmbCourseItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbCourseItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED)
        {
            bCoursesReady = false;
            bSectionsReady = false;

            if (bDepartmentsReady = true)
            {
                if (cmbCourse.getSelectedItem().equals("---") == false)
                {
                    try
                    {
                        cmbSection.removeAllItems();
                        cmbSection.addItem("---");
                        st = dbLocalConnection.createStatement();
                        rs = st.executeQuery("SELECT intSection " +
                            "FROM CLASS " +
                            "WHERE fkCOURSE_intID = (SELECT intID " +
                            "    FROM COURSE " +
                            "    WHERE intNumber = \"" + cmbCourse.getSelectedItem().toString() + "\") " +
                            "ORDER BY intSection ASC");

                        while (rs.next())
                        {
                            System.out.println("[DEBUG] intSection=" + rs.getString("intSection") );
                            cmbSection.addItem(rs.getString("intSection") );
                        }
                        cmbSection.setEnabled(true);
                        bCoursesReady = true;
                    }
                    catch (SQLException ex)
                    {
                        Logger.getLogger(jfReport.class.getName()).log(Level.SEVERE, null, ex);
                        //in case cmbSection was still enabled from a previous event trigger and something went wrong,
                        //to prevent breaking cmbSection
                        //resets cmbSection to "unused" state for tidying up
                        cmbSection.removeAllItems();
                        cmbSection.addItem("---");
        //                cmbSection.setSelectedIndex(-1);
                        cmbSection.setEnabled(false);
                    }
                }
                else
                {
                    //resets cmbSection to "unused" state for tidying up
                    cmbSection.removeAllItems();
                    cmbSection.addItem("---");
        //            cmbSection.setSelectedIndex(-1);

                    cmbSection.setEnabled(false);
                }
            }
        }
    }//GEN-LAST:event_cmbCourseItemStateChanged

    /**
     * Saves the currently loaded profile.
     * @param evt 
     */
    private void btnSaveProfileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveProfileActionPerformed
        //ask if the user wants to save the currently loaded profile
        int intResult = JOptionPane.showConfirmDialog(this, "Are you sure you want to overwrite " + lmodelLstProfile.getElementAt(intSelectedProfile) + "?", "Save Profile", JOptionPane.YES_NO_OPTION);
        if (intResult == JOptionPane.YES_OPTION)
        {
            //TODO: Write file
        }
    }//GEN-LAST:event_btnSaveProfileActionPerformed

    
    /**
     * Generates a report based on the current loaded template and saves it as the specified filetype(s) in the specified filepath.
     * @param dbConnection Connection used to retrieve data from the database.
     * @param strFilepath String used to determine where to save the file(s).
     * @param strFilename String used to determine the name of the file(s).
     * @param intFiletype Integer used to determine whether the file is generated as a DOCX, PDF, or both.
     * @throws InvalidFormatException
     * @throws Docx4JException
     * @throws FileNotFoundException
     * @throws SQLException 
     */
    private void generateReport(Connection dbConnection, String strFilepath, String strFilename, int intFiletype)
            throws InvalidFormatException, Docx4JException, FileNotFoundException, SQLException
    {
        
        //declaration
        int intEmptyRows;

        //creates main document
        WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.createPackage();
        MainDocumentPart mdp = wordMLPackage.getMainDocumentPart();
        
        try
        {
            lblGenerationProgress.setVisible(true);
            jpbGenerationProgress.setValue(30);
            lblGenerationProgress.setText("Generating report...");
            
            //from 0 to the number of components in lmodelLstTemplateComponents, taking advantage of the 0-based index to scan through the list with getSize() as the exclusive upper bound
            for (int intComponents = 0; intComponents < lmodelLstTemplateComponents.getSize(); intComponents++)
            {
                if (lmodelLstTemplateComponents.getElementAt(intComponents).toString().contains("{") == false)  //if non-table-type component
                {
                    //TODO (low-priority): hard-coded for now; possibly better to put into an external file, if possible
                    //TODO: Get precise values instead of dumping the entire ResultSet to file.
                    ResultSet rs;
                    switch(lmodelLstTemplateComponents.getElementAt(intComponents).toString() )
                        {
                        case "ASSIGNMENT_NAME":                 //selects all assignment names for the selected course section (i.e., class)
                            rs = st.executeQuery("SELECT vchrShortName FROM ASSIGNMENT WHERE ASSIGNMENT.intID = (SELECT fkASSIGNMENT_intID FROM CLASS_ASSIGNMENT WHERE CLASS_ASSIGNMENT.fkCLASS_intID = " + cmbSection.getSelectedItem() + ")");
                            mdp.addParagraphOfText(rs.getString("vchrShortName") );
                            break;
                        case "ASSIGNMENT_DESCRIPTION":          //selects all assignment descriptions for the selected course section (i.e., class)
                            rs = st.executeQuery("SELECT vchrDescription FROM ASSIGNMENT WHERE ASSIGNMENT.intID = (SELECT fkASSIGNMENT_intID FROM CLASS_ASSIGNMENT WHERE CLASS_ASSIGNMENT.fkCLASS_intID = " + cmbSection.getSelectedItem().toString() + ")");
                            mdp.addParagraphOfText(rs.getString("vchrDescription") );
                            break;
                        case "AUTHOR":                          //selects all authors for resources for the selected course
                            rs = st.executeQuery("SELECT vchrFirstName, vchrLastName " +
                                "FROM AUTHOR, TEXTBOOK_AUTHOR, COURSE_TEXTBOOK, TEXTBOOK, COURSE " +
                                "WHERE AUTHOR.intID = TEXTBOOK_AUTHOR.fkAUTHOR_intID AND TEXTBOOK.chrISBN = COURSE_TEXTBOOK.fkTEXTBOOK_chrISBN AND COURSE.intID =  " + cmbCourse.getSelectedItem().toString() );
                            mdp.addParagraphOfText(rs.getString("vchrFirstName") + rs.getString("vchrLastName") );
                            break;
                        case "COURSE_DESCRIPTION":              //selects the course description for the selected course
                            rs = st.executeQuery("SELECT vchrDescription FROM COURSE WHERE intNumber = " + cmbCourse.getSelectedItem().toString() );
                            mdp.addParagraphOfText(rs.getString("vchrDescription") );
                            break;
                        case "COURSE_NAME":                     //selects the course name for the selected course
                            rs = st.executeQuery("SELECT vchrTitle FROM COURSE WHERE intNumber = " + cmbCourse.getSelectedItem().toString() );
                            mdp.addParagraphOfText(rs.getString("vchrTitle") );
                            break;
                        case "COURSE_NUMBER":                   //gets the course number from cmbCourse's selected item. Should be accurate unless the user changes it while the report is generated (TODO: lock out report generator controls while generating report with a "glass pane" or something), and saves on processing that way.
                            mdp.addParagraphOfText(cmbCourse.getSelectedItem().toString() );
                            break;
                        case "CLASS_NUMBER":                    //gets the section (i.e., class) number from cmbSection's Should be accurate unless the user changes it while the report is generated, and saves on processing that way.
                            mdp.addParagraphOfText(cmbSection.getSelectedItem().toString() );
                            break;
                        case "DATE":                            //date to do a task (not a due date)
                            //TODO
                            break;
                        case "DIVIDER":                         //adds a divider line (TODO: center this)
                            mdp.addParagraphOfText("\n--------------------------------------------------\n");
                            break;
                        case "GROUP_PROJECT_DESCRIPTION":       //adds group project description
                            break;
                        case "GROUP_PROJECT_NAME":              //adds group project name
                            break;
                        case "INDIVIDUAL_PROJECT_DESCRIPTION":  //adds individual project description
                            break;
                        case "INDIVIDUAL_PROJECT_NAME":         //adds individual project name
                            break;
                        case "NEWLINE":                         //adds an empty line between paragraphs
                            mdp.addParagraphOfText("\n\n\n");
                            break;
                        case "PAGEBREAK":                       //adds a pagebreak
                            //TODO
                            break;
                        case "SYLLABUS_ACADEMIC_INTEGRITY":     //gets the academic integrity description associated with the selected course
                            rs = st.executeQuery("SELECT vchrContent " +
                                "FROM SYLLABUSACADEMICINTEGRITY, COURSE, COURSE_SYLLABUSACADEMICINTEGRITY " +
                                "WHERE SYLLABUSACADEMICINTEGRITY.intID = COURSE_SYLLABUSACADEMICINTEGRITY.fkSYLLABUSACADEMICINTEGRITY_intID AND COURSE_SYLLABUSACADEMICINTEGRITY.fkCOURSE_intID = COURSE.intID AND COURSE.intID = " + cmbCourse.getSelectedItem().toString() );
                            mdp.addParagraphOfText(rs.getString("vchrContent") );
                            break;
                        case "SYLLABUS_ASSIGNMENT_STANDARDS":   //gets the assignment standards description associated with the selected course
                            rs = st.executeQuery("SELECT vchrContent " +
                                "FROM SYLLABUSASSIGNMENTSTANDARDS, COURSE, COURSE_SYLLABUSASSIGNMENTSTANDARDS " +
                                "WHERE SYLLABUSASSIGNMENTSTANDARDS.intID = COURSE_SYLLABUSASSIGNMENTSTANDARDS.fkSYLLABUSASSIGNMENTSTANDARDS_intID AND COURSE_SYLLABUSASSIGNMENTSTANDARDS.fkCOURSE_intID = COURSE.intID AND COURSE.intID = " + cmbCourse.getSelectedItem().toString() );
                            mdp.addParagraphOfText(rs.getString("vchrContent") );
                            break;
                        case "SYLLABUS_COURSE_FORMAT":          //gets the course format description associated with the selected course
                            //TODO
                            break;
                        case "SYLLABUS_DISABILITY":             //gets the disability description associated with the selected course
                            //TODO
                            break;
                        case "SYLLABUS_GRADING":                //gets the grading standards associated with the selected course
                            //TODO
                            break;
                        case "SYLLABUS_MODIFICATION":           //gets the syllabus modifications associated with the selected course
                            //TODO
                            break;
                        case "TASK_NAME":                       //gets the assignment or group project (individual or group) associated with the selected class (meant to be used in a schedule table for a syllabus).
                            //TODO
                            break;
                        case "TEXTBOOK_IMAGE":                  //gets the covers of textbooks associated with the selected course
                            //TODO
                            break;
                        case "TEXTBOOK_INFORMATION":            //gets the non-author, non-cover, non-title information (e.g., edition, ISBN, website, publisher, etc.) of textbooks associated with the selected course
                            //TODO
                            break;
                        case "TEXTBOOK_TITLE":                  //gets the titles of textbooks associated with the selected course
                           //TODO
                            break;
                        case "TEXTBOOK_IS_REQUIRED_OR_OPTIONAL":    //gets the required/not required status of textbooks associated with the selected course
                            //TODO
                            break;
                        default:
                            //TODO: throw exception; somehow a component has not had its case added here
                            break;
                        }   //switch(lmodelLstTemplateComponents.getElementAt(intComponents).toString() )
                }   //if (lmodelLstTemplateComponents.getElementAt(intComponents).toString().contains("{") == false)  //if non-table-type component
                else    //if table-type component
                {
                    for (int intRows = 0; intRows < aLstTemplateComponents[intComponents].getRowCount(); intRows++)
                    {
                        for (int intColumns = 0; intColumns < aLstTemplateComponents[intComponents].getColumnCount(); intColumns++)
                        {
                            //TODO: process selected cell's component (see if can reuse non-table component's select)
                            //not sure if possible to look for PART of a string in a switch
//                            //this is setup as a switch in case any hard-coded table templates like CLASS_SCHEDULE{2,1} gets added or anything
//                            switch(lmodelLstTemplateComponents.getElementAt(intComponents).toString() )
//                            {
//                            case "TABLE{COLUMNS,ROWS}":
//                                break;
//                            default:
//                                //TODO: throw exception; somehow a component has not had its case added here
//                                break;
//                            }   //switch(lmodelLstTemplateComponents.getElementAt(intComponents).toString() )
                        }   //for (int intColumns = 0; intColumns <= MAX_COLUMNS; intColumns++)
                    }   //for (int intRows = 0; intRows <= MAX_ROWS; intRows++)
                }   //for (int intRows = 0; intRows <= MAX_ROWS; intRows++)   //else    //if table-type component
            }   //for (int intComponents = 0; intComponents < lmodelLstTemplateComponents.getSize(); intComponents++)
        }
        catch (SQLException e)
        {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            //System.exit(0);
        }
        catch (Exception e)
        {
            //TODO
            System.err.println("[ERROR] " + e.toString() );
        }
//        finally
//        {
//            rs.close();
//            st.close();
//        }

        //protect document
        ProtectDocument protection = new ProtectDocument(wordMLPackage);
        protection.restrictEditing(STDocProtect.READ_ONLY);

        //save document
        if (intFiletype == jfReport.DOCX_ONLY || intFiletype == jfReport.DOCX_AND_PDF)
        {
            if (intFiletype == jfReport.DOCX_ONLY)
            {
                jpbGenerationProgress.setValue(75);
            }
            else
            {
                jpbGenerationProgress.setValue(60);
            }
            lblGenerationProgress.setText("Saving report as DOCX...");
            Docx4J.save(wordMLPackage, new java.io.File(strFilepath + strFilename + ".docx"), Docx4J.FLAG_SAVE_ZIP_FILE);
        }

        //converts to pdf
        if (intFiletype == jfReport.DOCX_AND_PDF || intFiletype == jfReport.PDF_ONLY)
        {
            if (intFiletype == jfReport.DOCX_AND_PDF)
            {
                jpbGenerationProgress.setValue(75);
            }
            else
            {
                jpbGenerationProgress.setValue(60);
            }
            lblGenerationProgress.setText("Saving report as PDF...");
            //Docx4J.toPDF(wordMLPackage, new FileOutputStream(System.getProperty("user.dir") + "/CourseEZ.pdf"));
            Docx4J.toPDF(wordMLPackage, new FileOutputStream(strFilepath + strFilename + ".pdf") );
        }
        
        jpbGenerationProgress.setValue(100);
        lblGenerationProgress.setText("Report generation complete!");
    }
    
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
    private javax.swing.JButton btnContentAssignAllContentItemsToList;
    private javax.swing.JButton btnContentAssignAllContentItemsToTable;
    private javax.swing.JButton btnContentAssignSelectedContentItemToList;
    private javax.swing.JButton btnContentAssignSelectedContentItemToTable;
    private javax.swing.JButton btnContentRemoveAllContentItemsFromList;
    private javax.swing.JButton btnContentRemoveAllContentItemsFromTable;
    private javax.swing.JButton btnContentRemoveSelectedContentItemFromList;
    private javax.swing.JButton btnContentRemoveSelectedContentItemFromTable;
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
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JPanel jpClass;
    private javax.swing.JPanel jpContent;
    private javax.swing.JPanel jpGenerateReport;
    private javax.swing.JPanel jpGeneration;
    private javax.swing.JPanel jpPreview;
    private javax.swing.JPanel jpProfile;
    private javax.swing.JPanel jpTemplate;
    private javax.swing.JPanel jpTemplateTable;
    private javax.swing.JPanel jpTemplateTableSize;
    private javax.swing.JProgressBar jpbGenerationProgress;
    private javax.swing.JScrollPane jspListContentsAssignedComponents;
    private javax.swing.JTabbedPane jtpEditor;
    private javax.swing.JLabel lblColumns;
    private javax.swing.JLabel lblCourse;
    private javax.swing.JLabel lblDepartment;
    private javax.swing.JLabel lblGenerationProgress;
    private javax.swing.JLabel lblRows;
    private javax.swing.JLabel lblSection;
    private javax.swing.JList<String> lstContentAssignedContentItems;
    private javax.swing.JList<String> lstContentAvailableContentItems;
    private javax.swing.JList<String> lstProfile;
    private javax.swing.JList<String> lstTemplateComponents;
    private javax.swing.JList<String> lstTemplateDefaultComponents;
    private javax.swing.JRadioButton rbtnDocx;
    private javax.swing.JRadioButton rbtnDocxAndPdf;
    private javax.swing.JRadioButton rbtnPdf;
    private javax.swing.JScrollPane scpListProfile;
    private javax.swing.JSpinner spinnerColumns;
    private javax.swing.JSpinner spinnerRows;
    private javax.swing.JTable tblContentAssignedContentItems;
    private javax.swing.JTable tblTemplateComponentsTable;
    private javax.swing.JTextField txtfFilename;
    private javax.swing.JTextField txtfFilepath;
    // End of variables declaration//GEN-END:variables
}
