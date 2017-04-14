
/*
* Additional Libraries
*************************************************************
* antlr-2.7.7
* antlr-runtime-3.5.2
* avalon-framework-api-4.3.1
* avalon-framework-impl-4.3.1
* commons-codec-1.10
* commons-io-2.4
* commons-lang3-3.4
* commons-logging-1.1.3
* guava-19.0
* httpclient-3.5
* httpcore-4.4.4
* jackson-annotations-2.7.0
* jackson-core-2.7.3
* jackson-databind-2.7.3
* jaxb-svg11-1.0.2
* lorem-2.0
* mbassador-1.2.4.2
* serializer-2.7.2
* slf4j-api-1.7.21
* stringtemplate-3.2.1
* wmf2svg-0.9.8
* xalan-2.7.2
* xmlgraphics-commmons-2.1
* docx4j-3.3.3
************************************************************
*/
package org.psu.berksist.CourseEZ;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
    
    public void Reports() {}

        public void Syllabus ()throws InvalidFormatException, Docx4JException, FileNotFoundException{
                // creates main document 
		WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.createPackage();
		MainDocumentPart mdp = wordMLPackage.getMainDocumentPart();
                // writes into document
		mdp.addParagraphOfText("Welcome to course EZ");
                //protects document
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
