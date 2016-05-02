/*
    On click of "Add Course" button under the course panel jpAddCourse pops up a window that takes information
    and saves it to the database to create a new course.
 */
package IST261DesktopPaneDemo;
import java.awt.Component;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author dmg5572
 */
public class jpAddCourse extends javax.swing.JPanel 
{
    private Connection dbLocalConnection; //database connection passed from jpCourse
    private PreparedStatement pst;
    private Statement st;
    
    //list of variables for passing to database
    private String strDepartment = "default value";//string to receive input from GUI boxes
    private int intDepartment = 123456789; //int to convert strDepartment for passing to database
        

    int intCourseNumber = 123456789;//int to convert strDepartment for passing to database
        
    int intWritingEmphasis = 123456789;
        
    String strCredits = "default value";//string to receive input from GUI boxes
    int intCredits = 123456789;//int to convert strDepartment for passing to database

    String strCourseTitle = "default value";
    String strDescription = "default value";
    String strObjectives = "default value";
    String strPrerequisites = "default value";        
    String strNotes = "default value";
    
    public jpAddCourse() 
    {
        initComponents();
    }
    
    jpAddCourse(Connection inConnection)
    {
        setDBConnection(inConnection);//receives database connection from jpCourse, sets it up locally
        initComponents();
        setJCBDepartment();//SQL Select to pull list of departments and configure the department drop down
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        jlCourseName = new javax.swing.JLabel();
        jtfCourseTitle = new javax.swing.JTextField();
        jbAddCourseButton = new javax.swing.JButton();
        jlCourseNumber = new javax.swing.JLabel();
        jcbDepartment = new javax.swing.JComboBox();
        jlWritingEmphasis = new javax.swing.JLabel();
        jcbWritingEmphasis = new javax.swing.JComboBox();
        jlbCredits = new javax.swing.JLabel();
        jcbCredits = new javax.swing.JComboBox();
        jlbDepartment = new javax.swing.JLabel();
        jlbDescription = new javax.swing.JLabel();
        jlbObjectives = new javax.swing.JLabel();
        jlbPrerequisites = new javax.swing.JLabel();
        jtfPrerequisites = new javax.swing.JTextField();
        jlbNotes = new javax.swing.JLabel();
        jtfNotes = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        jtaDescription = new javax.swing.JTextArea();
        jScrollPane4 = new javax.swing.JScrollPane();
        jtaObjectives = new javax.swing.JTextArea();
        jspCourseNumber = new javax.swing.JSpinner();

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jTextArea2.setColumns(20);
        jTextArea2.setRows(5);
        jScrollPane2.setViewportView(jTextArea2);

        jlCourseName.setText("Course Title");

        jtfCourseTitle.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jtfCourseTitleActionPerformed(evt);
            }
        });

        jbAddCourseButton.setText("Add");
        jbAddCourseButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jbAddCourseButtonActionPerformed(evt);
            }
        });

        jlCourseNumber.setText("Course  Number");

        jcbDepartment.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Default Item" }));

        jlWritingEmphasis.setText("Writing Emphasis");

        jcbWritingEmphasis.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "No", "Yes" }));
        jcbWritingEmphasis.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jcbWritingEmphasisActionPerformed(evt);
            }
        });

        jlbCredits.setText("Credits");

        jcbCredits.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "3", "4" }));
        jcbCredits.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jcbCreditsActionPerformed(evt);
            }
        });

        jlbDepartment.setText("Department");

        jlbDescription.setText("Course Description");

        jlbObjectives.setText("Objectives");

        jlbPrerequisites.setText("Prerequisites");

        jlbNotes.setText("Notes");

        jtfNotes.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jtfNotesActionPerformed(evt);
            }
        });

        jtaDescription.setColumns(20);
        jtaDescription.setRows(5);
        jScrollPane3.setViewportView(jtaDescription);

        jtaObjectives.setColumns(20);
        jtaObjectives.setRows(5);
        jScrollPane4.setViewportView(jtaObjectives);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(12, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jlbObjectives)
                    .addComponent(jlbPrerequisites)
                    .addComponent(jlbNotes)
                    .addComponent(jlbDepartment)
                    .addComponent(jlbDescription)
                    .addComponent(jlCourseName)
                    .addComponent(jlCourseNumber))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addComponent(jtfPrerequisites))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jcbDepartment, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jbAddCourseButton))
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 339, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jtfNotes, javax.swing.GroupLayout.PREFERRED_SIZE, 338, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 339, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jspCourseNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jlbCredits)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jcbCredits, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jlWritingEmphasis)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jcbWritingEmphasis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jtfCourseTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 338, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(12, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlCourseNumber)
                    .addComponent(jlbCredits)
                    .addComponent(jcbCredits, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jcbWritingEmphasis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlWritingEmphasis)
                    .addComponent(jspCourseNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtfCourseTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlCourseName))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlbDescription))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlbObjectives))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtfPrerequisites, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlbPrerequisites))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtfNotes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlbNotes))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jbAddCourseButton)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jlbDepartment)
                        .addComponent(jcbDepartment, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(20, 20, 20))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jbAddCourseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbAddCourseButtonActionPerformed
        String strSelectedDepartment = "default value"; //string for inserting to FKDepartment field of Course
        int intSelectedDepartment = 123456789; //int derived from strSelectedDepartment for inserting to FKDepartment field of Course

        getInput();
        strSelectedDepartment = String.valueOf(jcbDepartment.getSelectedItem());
        intSelectedDepartment = getFKDepartment(strSelectedDepartment);
        
        databaseInsert(intSelectedDepartment);
        
        this.getTopLevelAncestor().setVisible(false);
    }//GEN-LAST:event_jbAddCourseButtonActionPerformed

    private void jtfNotesActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jtfNotesActionPerformed
    {//GEN-HEADEREND:event_jtfNotesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtfNotesActionPerformed

    private void jcbWritingEmphasisActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jcbWritingEmphasisActionPerformed
    {//GEN-HEADEREND:event_jcbWritingEmphasisActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jcbWritingEmphasisActionPerformed

    private void jcbCreditsActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jcbCreditsActionPerformed
    {//GEN-HEADEREND:event_jcbCreditsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jcbCreditsActionPerformed

    private void jtfCourseTitleActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jtfCourseTitleActionPerformed
    {//GEN-HEADEREND:event_jtfCourseTitleActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtfCourseTitleActionPerformed

private void getInput()
{
        //-----pull fields from UI, place contents into class variables to pass to database----
        intCourseNumber = (Integer)jspCourseNumber.getValue();//pulling entered value from Course Number spinner
 
        
        //check jcbWritingEmphasis for selected value, put integer into intWritingEmphasis for database
        if (jcbWritingEmphasis.getSelectedItem() == "Yes")
        {
            intWritingEmphasis = 1;
        }//if
        if (jcbWritingEmphasis.getSelectedItem() == "No")
        {
            intWritingEmphasis = 0;
        }//if        
        
        strCredits = String.valueOf(jcbCredits.getSelectedItem());//pulling string from jcbCredits
        intCredits = Integer.parseInt(strCredits);//set intCredits to java text field input
        
        strCourseTitle = jtfCourseTitle.getText();
        strDescription = jtaDescription.getText();
        strObjectives = jtaObjectives.getText();
        strPrerequisites = jtfPrerequisites.getText();
        strNotes = jtfNotes.getText();
}

private void databaseInsert(int inFKDepartmentInt)
{
    try
    {
        String query = " INSERT INTO Course (FKDepartment, Number, WritingEmphasis, Credits, Title, Description, Objectives, Prerequisite, Notes)" + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
        pst = dbLocalConnection.prepareStatement(query);
        pst.setInt(1, inFKDepartmentInt);
        pst.setInt(2, intCourseNumber);
        pst.setInt(3, intWritingEmphasis);
        pst.setInt(4, intCredits);
        pst.setString(5, strCourseTitle);
        pst.setString(6, strDescription);
        pst.setString(7, strObjectives);
        pst.setString(8, strPrerequisites);
        pst.setString(9, strNotes);
        pst.execute();
    }//try loop
    catch (Exception e) 
    { 
        System.err.println("jpAddCourse SQL Exception detected in databaseInsert()."); 
        System.err.println(e.getMessage()); 
    }//catch statements
    
}

private void setDBConnection(Connection inConnection)
{
    dbLocalConnection = inConnection;
    try 
    {
        st = dbLocalConnection.createStatement();
    } //try
    catch (SQLException ex) 
    {
        System.out.println(ex);
    }//catch
}//setdbConnection

private int getFKDepartment(String inSelectedDepartment)
{//return ID from Department table of SQL database for department highlighted in jcbDepartment 
    String strSelectedDepartment = "Default Value";
    int intSelectedDepartment = 123456789;
    
    strSelectedDepartment = inSelectedDepartment;
    
    try//try for SQL statement finding department ID of selected department in drop down menu
    {
        ResultSet rsResult = st.executeQuery("select ID from Department where DepartmentName = '" + strSelectedDepartment + "'");
        intSelectedDepartment = rsResult.getInt(1);
    }//try
    catch (SQLException sqle)
    {
        System.err.println("jpAddCourse SQL Exception detected in getFKDepartment()");
        System.out.println(sqle);                
    }//catch

    return intSelectedDepartment;
}//getFKDepartment

private void setJCBDepartment() //SQL Select to pull list of departments and configure the department drop down
{
    jcbDepartment.removeAllItems();
    try
    {
        ResultSet rsResults = st.executeQuery("select DepartmentName from Department order by DepartmentName asc");

        while (rsResults.next())
        {
            jcbDepartment.addItem(rsResults.getString("DepartmentName"));
        }//while
    }//try
    catch (SQLException sqle) 
    {
        System.err.println("jpAddCourse SQL Exception detected in setJcbDepartment().");
        System.out.println(sqle);
    }//catch SQL exceptions
}//setJCBDepartment
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JButton jbAddCourseButton;
    private javax.swing.JComboBox jcbCredits;
    private javax.swing.JComboBox jcbDepartment;
    private javax.swing.JComboBox jcbWritingEmphasis;
    private javax.swing.JLabel jlCourseName;
    private javax.swing.JLabel jlCourseNumber;
    private javax.swing.JLabel jlWritingEmphasis;
    private javax.swing.JLabel jlbCredits;
    private javax.swing.JLabel jlbDepartment;
    private javax.swing.JLabel jlbDescription;
    private javax.swing.JLabel jlbNotes;
    private javax.swing.JLabel jlbObjectives;
    private javax.swing.JLabel jlbPrerequisites;
    private javax.swing.JSpinner jspCourseNumber;
    private javax.swing.JTextArea jtaDescription;
    private javax.swing.JTextArea jtaObjectives;
    private javax.swing.JTextField jtfCourseTitle;
    private javax.swing.JTextField jtfNotes;
    private javax.swing.JTextField jtfPrerequisites;
    // End of variables declaration//GEN-END:variables
}//jpAddCourse class
