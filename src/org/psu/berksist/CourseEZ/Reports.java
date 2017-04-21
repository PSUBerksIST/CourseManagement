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
 * @author Darius Holt,
 */
public class Reports {
    
    private Statement st;
    
    private Connection dbConnection;
    
    
    
    public void Reports() {}

        public void Syllabus (Connection inConn)throws InvalidFormatException, Docx4JException, FileNotFoundException, SQLException{
                // creates main document 
                dbConnection = inConn;
                
                
                
                WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.createPackage();
		MainDocumentPart mdp = wordMLPackage.getMainDocumentPart();
                // writes into document
		
                
                //protects document
		
                //st = dbConnection.prepareStatement("SELECT vchrDescription "
                //            + "FROM SYLLABUS_MASTER;");
                try {
      

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
    } catch ( SQLException e ) {
      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
      //System.exit(0);
    }
                
		ProtectDocument protection = new ProtectDocument(wordMLPackage);
		protection.restrictEditing(STDocProtect.READ_ONLY, "ohhhnoooo");
		
                //saves documents as
		String filename = System.getProperty("user.dir") + "/CourseEZ.docx";
		Docx4J.save(wordMLPackage, new java.io.File(filename), Docx4J.FLAG_SAVE_ZIP_FILE); 
		
		
		
		
                //converts to pdf
                Docx4J.toPDF(wordMLPackage, new FileOutputStream(System.getProperty("user.dir") + "/CourseEZ.pdf"));
	}
    
        public void Assignment ()throws InvalidFormatException, Docx4JException, FileNotFoundException{
                
                // creates main document 
		WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.createPackage();
		MainDocumentPart mdp = wordMLPackage.getMainDocumentPart();
                // writes into document
		mdp.addParagraphOfText("Welcome to course EZ");
                //protects document
		ProtectDocument protection = new ProtectDocument(wordMLPackage);
		protection.restrictEditing(STDocProtect.READ_ONLY, "whatever");
		
                //saves documents as
		String filename = System.getProperty("user.dir") + "/CourseEZ.docx";
		Docx4J.save(wordMLPackage, new java.io.File(filename), Docx4J.FLAG_SAVE_ZIP_FILE); 
		
                //converts to pdf
                Docx4J.toPDF(wordMLPackage, new FileOutputStream(System.getProperty("user.dir") + "/CourseEZ.pdf"));
	}
    
}