/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.psu.berksist.CourseEZ;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



/**
 *
 * @author William H. Bowers (admin_whb108)
 * 
 * @version 0.1
 * 
 * 
 ********************* MODIFICATION LOG ************************
 *
 * 2016 March 25 -  Initial Action creation

 */
import java.util.Properties;
import java.awt.event.ActionEvent;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
 
public class SavePropertiesAction extends AbstractAction {
     
    
    private jfMain jfApp; // the desktop to work with
    private Properties myProps;
    
    //private String strUserPrefsFile;
    
    
    public SavePropertiesAction(jfMain jfIn, Properties propsIn)
    {
        super("Save Properties");
        jfApp = jfIn;
        myProps = propsIn;    
        
        try {
            
            putValue(Action.SMALL_ICON, new ImageIcon
                    (new URL(AppConstants.SAVE_ICON_16)));
            
            putValue( Action.LARGE_ICON_KEY, new ImageIcon
                    (new URL(AppConstants.SAVE_ICON_32)));
            
        } catch (MalformedURLException ex) {
            Logger.getLogger(SavePropertiesAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        putValue(Action.LONG_DESCRIPTION,"Save the user's preferences to a file");
          
        putValue(Action.NAME, "Save Preferences");
          
        putValue(Action.SHORT_DESCRIPTION,"Save Preverences");
          
        /*
        Possible properties for putValue(property, value)
        see https://docs.oracle.com/javase/8/docs/api/javax/swing/AbstractAction.html#putValue(java.lang.String,%20java.lang.Object)
        
        ACCELERATOR_KEY, ACTION_COMMAND_KEY, DEFAULT, DISPLAYED_MNEMONIC_INDEX_KEY, LARGE_ICON_KEY, LONG_DESCRIPTION, MNEMONIC_KEY, NAME, SELECTED_KEY, SHORT_DESCRIPTION, SMALL_ICON
        
        Examples from Deitel Advanced Java HTP
        
          // set Action name
      putValue( Action.NAME, "Exit" );
      
      // set Action icon
      putValue( Action.SMALL_ICON, new ImageIcon(
         getClass().getResource( "images/EXIT.gif" ) ) );
      
      // set Action short description (tooltip text)
      putValue( Action.SHORT_DESCRIPTION, 
         "Exit Application" );
      
      // set Action mnemonic key
      putValue( Action.MNEMONIC_KEY, 
         new Integer( 'x' ) );
      
      // disable exitAction and associated GUI components
      setEnabled( false );
      
        
        */
    }
     
 
    public void actionPerformed(ActionEvent ev) 
    {
        
        //TODO: Save with .xml extension, Open JFileChooser when JMenuItem 'Save Options' is clicked - RQZ
        /*if (strFileName.length()== 0)
        {
           JFileChooser myJFC = new JFileChooser();
           myJFC.setDialogTitle("Save Preferences");
           if (myJFC.showSaveDialog(jfApp) == JFileChooser.APPROVE_OPTION)
           {
               try {
                   strFileName  = myJFC.getSelectedFile().getCanonicalPath();
               } catch (IOException ex) {
                   Logger.getLogger(SavePropertiesAction.class.getName()).log(Level.SEVERE, null, ex);
               }
           } // file selected
        } // if no file name was retrieved from preferences */
        
        try {
            String strWarning = "Edit this file at your own risk.  " 
                    + "It will be overwritten at program exit.";
            myProps.storeToXML(new FileOutputStream(AppConstants.PREFS_XML_FILE),strWarning);
        } catch (FileNotFoundException ex) {
            
            
            
            Logger.getLogger(SavePropertiesAction.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SavePropertiesAction.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        
        
                
    } // actionPerformed
}