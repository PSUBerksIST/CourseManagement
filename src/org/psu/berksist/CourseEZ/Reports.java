package org.psu.berksist.CourseEZ;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.docx4j.Docx4J;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.exceptions.InvalidFormatException;
import org.docx4j.openpackaging.packages.ProtectDocument;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.wml.STDocProtect;

/**
 *
 * @author Darius Holt
 * 
 *  ******************* MODIFICATION LOG *****************************************
 *  April 24 2017       -   Added additional parameters to Syllabus() and Assignment().
 *                          Updated associated code so they'll create a DOCX and/or PDF as needed,
 *                              with parameter-given filepaths and names.
 *                          Cleaned up code a little.   -JSS5783
 * 
 */
public class Reports
{
    private Statement st;   
    private Connection dbConnection;
    
    public void Reports()
    {
    }

    public void Syllabus(Connection inConn, String strFilepath, String strFilename, int intFiletype)
            throws InvalidFormatException, Docx4JException, FileNotFoundException, SQLException
    {
        // creates main document 
        dbConnection = inConn;

        WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.createPackage();
        MainDocumentPart mdp = wordMLPackage.getMainDocumentPart();
        // writes into document


        //protects document

        //st = dbConnection.prepareStatement("SELECT vchrDescription "
        //            + "FROM SYLLABUS_MASTER;");
        try
        {
            st = dbConnection.createStatement();
            ResultSet rs = st.executeQuery( "SELECT * FROM vSYLLABUS_MASTER;" );

            String  syllaMain = rs.getString("vchrDescription");
            int num  = rs.getInt("intNumber");
            String  title = rs.getString("vchrTitle");

            mdp.addParagraphOfText(syllaMain);
            mdp.addParagraphOfText( Integer.toString(num));
            mdp.addParagraphOfText(title);

            rs.close();
            st.close();
           // c.close();
        }
        catch (SQLException e)
        {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            //System.exit(0);
        }

        ProtectDocument protection = new ProtectDocument(wordMLPackage);
        protection.restrictEditing(STDocProtect.READ_ONLY, "ohhhnoooo");

        //saves documents as
        //String filename = System.getProperty("user.dir") + "/CourseEZ.docx";
        if (intFiletype == 0 || intFiletype == 1)
        {
            Docx4J.save(wordMLPackage, new java.io.File(strFilepath + strFilename + ".docx"), Docx4J.FLAG_SAVE_ZIP_FILE);
        }

        //converts to pdf
        if (intFiletype == 1 || intFiletype == 2)
        {
            //Docx4J.toPDF(wordMLPackage, new FileOutputStream(System.getProperty("user.dir") + "/CourseEZ.pdf"));
            Docx4J.toPDF(wordMLPackage, new FileOutputStream(strFilepath + strFilename + ".pdf") );
        }
    }

    public void Assignment(Connection inConn, String strFilepath, String strFilename, int intFiletype)
            throws InvalidFormatException, Docx4JException, FileNotFoundException, SQLException
    {

        //creates main document 
        WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.createPackage();
        MainDocumentPart mdp = wordMLPackage.getMainDocumentPart();
        // writes into document
        mdp.addParagraphOfText("Welcome to course EZ");
        //protects document
        ProtectDocument protection = new ProtectDocument(wordMLPackage);
        protection.restrictEditing(STDocProtect.READ_ONLY, "whatever");

        //saves documents as
        //String filename = System.getProperty("user.dir") + "/CourseEZ.docx";
        if (intFiletype == 0 || intFiletype == 1)   //DOCX-only or DOCX-and-PDF
        {
            Docx4J.save(wordMLPackage, new java.io.File(strFilepath + strFilename + ".docx"), Docx4J.FLAG_SAVE_ZIP_FILE);
        }

        //converts to pdf
        if (intFiletype == 1 || intFiletype == 2)   //DOCX-and-PDF or PDF-only
        {
            //Docx4J.toPDF(wordMLPackage, new FileOutputStream(System.getProperty("user.dir") + "/CourseEZ.pdf"));
            Docx4J.toPDF(wordMLPackage, new FileOutputStream(strFilepath + strFilename + ".pdf") );
        }
    }
    
}