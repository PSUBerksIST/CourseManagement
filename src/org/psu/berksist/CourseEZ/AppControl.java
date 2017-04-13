/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.psu.berksist.CourseEZ;

import java.io.IOException;
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
    private AppControl newRun;
    
    private String strRelPath = jfMain.class.getProtectionDomain().getCodeSource().getLocation().toString();
    
    public static void main(String[] args) throws IOException
    {
        
        SplashScreen splash = new SplashScreen(1000); //change duration of splash screen here
        splash.showSplash(); //to display splash screen
        
        new AppControl(args);
        
    }
    
    public AppControl(String[] strArgs){
        
        newRun = this;
        checkPath();
        
        myProps = new Properties();
        gpa = new GetPropertiesAction(myProps);
        
        addAdditionalPLAF();
        
        try {
            myCL = CommandLineOptions.processCommandLine(strArgs);
            
            // Set requested UserPrefs file
            String tempFile = myCL.getOptionValue("u");
            // Check to see if it is null
            if (tempFile != null){ 
                gpa.loadPrefs(tempFile);
            } else {
                gpa.loadPrefs(AppConstants.DEFAULT_XML_FILE); 
            }
            
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("Course Management", CommandLineOptions.makeOptions());
            
        } catch (ParseException ex) {
            Logger.getLogger(jfMain.class.getName() ).log(Level.SEVERE, null, ex);
        }
        
        
        
        
        
        setWindowDecoration();
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {

                
                main = new jfMain(myProps, gpa, newRun);

            }
        });
        
    }
    
    public void setWindowDecoration()
    {
        
        JFrame.setDefaultLookAndFeelDecorated(UIManager.getLookAndFeel().getSupportsWindowDecorations());
        JDialog.setDefaultLookAndFeelDecorated(UIManager.getLookAndFeel().getSupportsWindowDecorations());
        
    }
    
    public void restartApp(jpMain jpWorkingPanel){
        
        setWindowDecoration();
        main.dispose();
        main = new jfMain(myProps, gpa, this, jpWorkingPanel);
        
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
