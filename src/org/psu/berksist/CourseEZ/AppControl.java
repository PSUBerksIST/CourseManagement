/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.psu.berksist.CourseEZ;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.UIManager;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.ParseException;

/**
 *
 * @author rqz5104
 */
public class AppControl {
    
    private jfMain main;
    
    private Properties myProps;
    
    private GetPropertiesAction gpa;
    
    private CommandLine myCL;
    
    
    private String strRelPath = jfMain.class.getProtectionDomain().getCodeSource().getLocation().toString();
    
    public static void main(String[] args) {
        
        AppControl newRun = new AppControl();
        newRun.create(args);
        
    }
    
    public void create(String[] strArgs){
        
        checkPath();
        
        myProps = new Properties();
        
        
        
        
        addAdditionalPLAF();
        
        try {
            myCL = CommandLineOptions.processCommandLine(strArgs);
            
            // Set requested UserPrefs file
            String tempFile = myCL.getOptionValue("u");
            // Check to see if it is null
            if (tempFile != null){ AppConstants.setXMLFile(tempFile); }
            
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("Course Management", CommandLineOptions.makeOptions());
            
        } catch (ParseException ex) {
            Logger.getLogger(jfMain.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        gpa = new GetPropertiesAction(myProps);
        
        gpa.loadPrefs();
        
        setWindowDecoration();
        
        main = new jfMain(myProps, gpa, this);
        
        
    }
    
    public void setWindowDecoration()
    {
        
        JFrame.setDefaultLookAndFeelDecorated(UIManager.getLookAndFeel().getSupportsWindowDecorations());
        JDialog.setDefaultLookAndFeelDecorated(UIManager.getLookAndFeel().getSupportsWindowDecorations());
        
    }
    
    public void restartApp(jpMain hey){
        
        setWindowDecoration();
        main.dispose();
        main = new jfMain(myProps, gpa, this, hey);
        //ain.dispose();
        
    }
    
    public void addAdditionalPLAF() {
        UIManager.installLookAndFeel("Pago Soft", "com.pagosoft.plaf.PgsLookAndFeel");
        UIManager.installLookAndFeel("3D", "de.hillenbrand.swing.plaf.threeD.ThreeDLookAndFeel");
        UIManager.installLookAndFeel("Black Eye", "de.javasoft.plaf.synthetica.SyntheticaBlackEyeLookAndFeel");
    }
    
    private void checkPath()
    {
        // If it's a jar file, add the following pre and suffix to the path
        if (strRelPath.endsWith(".jar")){
            strRelPath = "jar:" + strRelPath + "!/";
        }
        
        // Set the relative path in the AppConstants
        AppConstants.setRelativePath(strRelPath);
    }
    
    
}
