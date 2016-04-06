/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
// @author dmg5572
package IST261DesktopPaneDemo;

import java.awt.Component;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JDialog;
import javax.swing.JOptionPane;




public class jpAddCourse extends javax.swing.JPanel 
{
    
    private Connection dbLocalConnection;
    private PreparedStatement pst;
    private Statement st;
    
    //list of variables for passing to database
    private String strDepartment = "default value";
    private int intDepartment = 123456789;
        
    String strCourseNumber = "default value";
    int intCourseNumber = 123456789;
        
    int intWritingEmphasis = 123456789;
        
    String strCredits = "default value";
    int intCredits = 123456789;

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
        setdbConnection(inConnection);
        initComponents();
        setJcbDepartment();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        jlCourseName = new javax.swing.JLabel();
        jtfCourseTitle = new javax.swing.JTextField();
        jbAddCourseButton = new javax.swing.JButton();
        jlCourseNumber = new javax.swing.JLabel();
        jtfCourseNumber = new javax.swing.JTextField();
        jcbDepartment = new javax.swing.JComboBox();
        jlWritingEmphasis = new javax.swing.JLabel();
        jcbWritingEmphasis = new javax.swing.JComboBox();
        jlbCredits = new javax.swing.JLabel();
        jcbCredits = new javax.swing.JComboBox();
        jlbDepartment = new javax.swing.JLabel();
        jlbDescription = new javax.swing.JLabel();
        jtfDescription = new javax.swing.JTextField();
        jlbObjectives = new javax.swing.JLabel();
        jtfObjectives = new javax.swing.JTextField();
        jlbPrerequisites = new javax.swing.JLabel();
        jtfPrerequisites = new javax.swing.JTextField();
        jlbNotes = new javax.swing.JLabel();
        jtfNotes = new javax.swing.JTextField();

        jlCourseName.setText("Course Title");

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

        jcbWritingEmphasis.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Yes", "No" }));

        jlbCredits.setText("Credits");

        jcbCredits.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "3", "4" }));

        jlbDepartment.setText("Department");

        jlbDescription.setText("Course Description");

        jtfDescription.setHorizontalAlignment(javax.swing.JTextField.LEFT);

        jlbObjectives.setText("Objectives");

        jlbPrerequisites.setText("Prerequisites");

        jlbNotes.setText("Notes");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jbAddCourseButton)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jlbDescription)
                            .addComponent(jlCourseName)
                            .addComponent(jlCourseNumber)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jlbPrerequisites)
                                .addComponent(jlbNotes, javax.swing.GroupLayout.Alignment.TRAILING))
                            .addComponent(jlbDepartment)
                            .addComponent(jlbObjectives))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jtfDescription, javax.swing.GroupLayout.DEFAULT_SIZE, 306, Short.MAX_VALUE)
                            .addComponent(jtfCourseTitle)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jtfCourseNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jlbCredits)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jcbCredits, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jlWritingEmphasis)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jcbWritingEmphasis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jcbDepartment, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtfObjectives)
                            .addComponent(jtfPrerequisites)
                            .addComponent(jtfNotes))))
                .addContainerGap(18, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlCourseName)
                    .addComponent(jtfCourseTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jtfDescription, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jlbCredits)
                            .addComponent(jcbCredits, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jcbWritingEmphasis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtfCourseNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jlCourseNumber)
                            .addComponent(jlWritingEmphasis)))
                    .addComponent(jlbDescription))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlbDepartment)
                    .addComponent(jcbDepartment, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jtfObjectives, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlbObjectives))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jlbPrerequisites)
                    .addComponent(jtfPrerequisites, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtfNotes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlbNotes))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jbAddCourseButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jbAddCourseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbAddCourseButtonActionPerformed
        String strSelectedDepartment = "default value"; //string for inserting to FKDemartment field of Course
        int intSelectedDepartment = 123456789; //int derived from strSelectedDepartment for inserting to FKDemartment field of Course


        getInput();
        strSelectedDepartment = String.valueOf(jcbDepartment.getSelectedItem());
        intSelectedDepartment = getFKDepartment(strSelectedDepartment);

        if ( (strDepartment == null) || (strCourseNumber == null)  )
        {      Component frame = null;
//this is broken on purpose to call attention to me
        JOptionPane.showMessageDialog(frame,
    "Eggs are not supposed to be green.",
    "Inane error",
    JOptionPane.ERROR_MESSAGE);

        }
        
        databaseInsert(intSelectedDepartment);
        this.getTopLevelAncestor().setVisible(false);
    }//GEN-LAST:event_jbAddCourseButtonActionPerformed

private void getInput()
{
        //-----pull fields from UI, place contents into class variables to pass to database----
        //strDepartment = String.valueOf(jcbDepartment.getSelectedItem());
        strCourseNumber = jtfCourseNumber.getText();//pulling string from java text field
        intCourseNumber = Integer.parseInt(strCourseNumber);//set integer to java text field input  
        
        //check jcbWritingEmphasis for selected value, put integer into intWritingEmphasis for database
        if (jcbWritingEmphasis.getSelectedItem() == "Yes")
        {
            intWritingEmphasis = 1;
        }//if
        if (jcbWritingEmphasis.getSelectedItem() == "No")
        {
            intWritingEmphasis = 0;
        }//if        
        
        strCredits = String.valueOf(jcbCredits.getSelectedItem());//pulling string from java text field
        intCredits = Integer.parseInt(strCredits);//set integet to java text field input
        
        strCourseTitle = jtfCourseTitle.getText();
        strDescription = jtfDescription.getText();
        strObjectives = jtfObjectives.getText();
        strPrerequisites = jtfPrerequisites.getText();
        strNotes = jtfNotes.getText();
}

private void databaseInsert(int inFKDepartmentInt)
{
    //String strCurrentSelection = "default value";
    
    try
    {

        String query = " INSERT INTO Course (FKDepartment, Number, WritingEmphasis, Credits, Title, Description, Objectives, Prerequisite, Notes)" + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
        pst = dbLocalConnection.prepareStatement(query);
        //stmAddClassStatement.executeUpdate("INSERT INTO Course VALUES ('" + strCourseTitle + "', " + );
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

private void setdbConnection(Connection inConnection)
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
}

private int getFKDepartment(String inSelectedDepartment)
{//return ID from Department table of SQL database for department highlighted in jcbDepartment 
    String strSelectedDepartment = "Default Value";
    int intSelectedDepartment = 123456789;
    
    
    
    strSelectedDepartment = inSelectedDepartment;
    
    try
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
}

private void setJcbDepartment()
{
    //clean out defaults in jcbDepartment, pull list from SQL database and populate jcbDepartment
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
}
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
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
    private javax.swing.JTextField jtfCourseNumber;
    private javax.swing.JTextField jtfCourseTitle;
    private javax.swing.JTextField jtfDescription;
    private javax.swing.JTextField jtfNotes;
    private javax.swing.JTextField jtfObjectives;
    private javax.swing.JTextField jtfPrerequisites;
    // End of variables declaration//GEN-END:variables

    
    
}
