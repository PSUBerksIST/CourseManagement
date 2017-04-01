/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.psu.berksist.CourseEZ;

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
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
 
public class SavePropertiesAction extends AbstractAction {
     
    private jfMain jfApp; // the desktop to work with
    private Properties myProps;
    
    public SavePropertiesAction(jfMain jfIn, Properties propsIn)
    {
        super("Save Properties");
        
        jfApp = jfIn;
        myProps = propsIn;
        
        configureAction();
        
    }
    
    private void configureAction(){
        
        try {
            
            putValue(Action.SMALL_ICON, new ImageIcon
                    (new URL(AppConstants.SAVE_ICON_16)));
            
            putValue( Action.LARGE_ICON_KEY, new ImageIcon
                    (new URL(AppConstants.SAVE_ICON_32)));
            
        } catch (MalformedURLException ex) {
            Logger.getLogger(SavePropertiesAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        putValue(Action.LONG_DESCRIPTION,"Save the user's settings to an xml file");
          
        putValue(Action.NAME, "Save Settings");
          
        putValue(Action.SHORT_DESCRIPTION,"Save Settings To File");
        
    }
 
    public void actionPerformed(ActionEvent ev) 
    {
        
        JFileChooser jfcExport = new JFileChooser();
        jfcExport.setDialogTitle("Export Settings");
        FileNameExtensionFilter xmlFilter = new FileNameExtensionFilter("XML Files", "xml");
        jfcExport.setFileFilter(xmlFilter);
        jfcExport.setAcceptAllFileFilterUsed(false);
        jfcExport.setApproveButtonText("Export Settings");
        
        boolean blnFileSelected = (jfcExport.showOpenDialog(jfApp) == JFileChooser.APPROVE_OPTION);
        
        if (blnFileSelected){
            String strSelectedFile = jfcExport.getSelectedFile().getPath();
            
            strSelectedFile = strSelectedFile.toLowerCase().endsWith(".xml") ? strSelectedFile : strSelectedFile.concat(".xml");
            
            savePrefs(strSelectedFile);
        }
        
    } // actionPerformed
    
    public void savePrefs(){
        savePrefs(AppConstants.DEFAULT_XML_FILE);
    }
    
    public void savePrefs(String strFileIn)
    {
            
            String strWarning = "Edit this file at your own risk.  " 
                    + "It will be overwritten at program exit.";
            
            // Set App and version in properties
            myProps.setProperty("AppID", AppConstants.APP_ID);
            myProps.setProperty("AppVersion", AppConstants.APP_VERSION);
            
        try {
            
            // Save to XML
            myProps.storeToXML(new FileOutputStream(strFileIn), strWarning);
            
        } catch (IOException ex) {
            Logger.getLogger(SavePropertiesAction.class.getName()).log(Level.SEVERE, null, ex);
        }
            
    } // savePrefs
    
}