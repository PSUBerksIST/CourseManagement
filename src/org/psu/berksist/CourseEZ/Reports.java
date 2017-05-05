package org.psu.berksist.CourseEZ;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import org.docx4j.Docx4J;
import org.docx4j.model.datastorage.migration.VariablePrepare;
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
            throws InvalidFormatException, Docx4JException, FileNotFoundException, SQLException, Exception
    //TODO: there is still quite a bit to do here like replacing the rest of the variables in the template
    //      and gettinng the rest of the information from the DB, but i think you will find it fairly simple
    //      to do following what was already done.
 
    {
        //connects syllabus to Database
        dbConnection = inConn;
        //Used for replacing Text in docx templates
        HashMap<String,String> mappings = new HashMap<String,String>();
        
        //Uses Template.docx as input for Syllabus reports
        WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage
                .load(new java.io.File(System.getProperty("user.dir")
                                        +"/Template.docx"));
        //Prepares word doc for variable replacement
        VariablePrepare.prepare(wordMLPackage);
        
        //gets main part of document
        MainDocumentPart mdp = wordMLPackage.getMainDocumentPart();
        
        try
        {
            //used to execute queries
            st = dbConnection.createStatement();
            
            //stores result of executed query
            ResultSet rs = st.executeQuery( "SELECT * FROM vSYLLABUS_MASTER;" );
            
            //storing information from executed query into strings to be replaced
            String  syllaMain = rs.getString("vchrDescription");
            String num  = rs.getString("intNumber");
            String  title = rs.getString("vchrTitle");
            
            //Replaces the first with the second
            //Example courseDescript will be replaced by syllaMain
            mappings.put("courseDescript",syllaMain);
            mappings.put("classNum",num);
            mappings.put("courseTitle",title);
            
            //replaces variables
            mdp.variableReplace(mappings);

            rs.close();
            st.close();
            
        }
        catch (SQLException e)
        {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }

        ProtectDocument protection = new ProtectDocument(wordMLPackage);
        protection.restrictEditing(STDocProtect.READ_ONLY, "ohhhnoooo");

        //saves document as
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
    //TODO: replicate what was being done for syllabus, but use the database for assignments instead
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
//TODO: any additional reports you make can be put down here as assignment and syllabus was made above, you would
// just need to create templates for them. Also customized reports was something i originally was working on, so if
//that was something you wanted to work on and need help, or need help on anything report related, contact me with the
//info on the handover documentation.