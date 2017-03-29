
package org.psu.berksist.CourseEZ;

/**
 *
 * @author William H. Bowers (admin_whb108)
 * 
 * @version 0.1
 * 
 * Loads the user preferences file into the properties object
 * 
 ********************* MODIFICATION LOG ************************
 *
 * 2017 March 10 - Icons now work, added feature to import a file from JFileChooser,
 *                 added a check to make sure a compatible XML file was loaded,
 *                 general code improvements and cleaning. - RQZ
 * 
 * 2016 February 02 -  Initial program creation
 *
 */

import java.awt.event.ActionEvent;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
 
public class GetPropertiesAction extends AbstractAction {
     
    private jfMain jfApp; // the desktop to work with
    private Properties myProps; 
    
    public GetPropertiesAction(Properties propsIn) 
    {
        super("Get Properties");
        
        myProps = propsIn;
        
        configureAction();
        
    }
    
    public void setFrame(jfMain jfIn)
    {
        jfApp = jfIn;
    }
    
    private void configureAction(){
        
        try {
            
            putValue(Action.SMALL_ICON, new ImageIcon
                    (new URL(AppConstants.LOAD_ICON_16)));
            
            putValue(Action.LARGE_ICON_KEY, new ImageIcon
                    (new URL(AppConstants.LOAD_ICON_32)));
            
        } catch (MalformedURLException ex) {
            Logger.getLogger(SavePropertiesAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        putValue(Action.LONG_DESCRIPTION,"Import the user's properties from an xml file");
          
        putValue(Action.NAME, "Import Properties");
          
        putValue(Action.SHORT_DESCRIPTION,"Import Properties From File");
        
    }
    
    public void actionPerformed(ActionEvent ev) 
    {
        
        JFileChooser jfcImport = new JFileChooser();
        jfcImport.setDialogTitle("Import User Preferences");
        FileNameExtensionFilter xmlFilter = new FileNameExtensionFilter("XML Files", "xml");
        jfcImport.setFileFilter(xmlFilter);
        jfcImport.setAcceptAllFileFilterUsed(false);
        jfcImport.setApproveButtonText("Import Preferences");
        
        boolean blnFileSelected = (jfcImport.showOpenDialog(jfApp) == JFileChooser.APPROVE_OPTION);
        
        if (blnFileSelected){
            String strSelectedFile = jfcImport.getSelectedFile().getPath();
            loadPrefs(strSelectedFile);
        }
        
    } // actionPerformed
    
    public void loadPrefs(String strFileIn)
    {
        myProps.clear(); // Clear old properties
        
        try {
            
            myProps.loadFromXML(new FileInputStream(strFileIn));
            
        } catch (IOException ex) {
            
            System.out.println("Something went wrong with the file you selected...");
            
            Logger.getLogger(GetPropertiesAction.class.getName()).log(Level.SEVERE, null, ex);
            
        } 
        
        
        // Check to make sure the opened XML file is for this App
        // AppID = CourseEZ, AppVersion = 1
        if (myProps.getProperty("AppID", "").equals(AppConstants.APP_ID) && 
                myProps.getProperty("AppVersion", "").equals(AppConstants.APP_VERSION))
        {
            applyProperties(myProps); // Apply the properties
            
        } else {
            JOptionPane.showMessageDialog(jfApp, "The preference file chosen is not compatible with this application.", "Incompatible File", JOptionPane.ERROR_MESSAGE);
            
            // Load default xml file
            loadPrefs(AppConstants.DEFAULT_XML_FILE);
            System.out.println("Default preferences file loaded..");
            
        }
        
    } // loadPrefs
    
    private void applyProperties(Properties inProps)
    {
        
        // Apply the LAF setting
        // If there is no entry in the xml file, use the default LAF
        String strLAF = 
                inProps.getProperty(AppConstants.LAF, AppConstants.DEFAULT_LAF);
        
        try {
            // Check to make sure we are loading a different LAF
            if (!UIManager.getLookAndFeel().getClass().getCanonicalName().equals(strLAF)){
                UIManager.setLookAndFeel(strLAF); // Set the new LAF
                
                // If app is running, we will have to reload the frame
                if (jfApp != null){
                    SwingUtilities.updateComponentTreeUI(jfApp);
                    jfApp.appControl.restartApp(jfApp.jpMainPanel);
                }
            }
            
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(GetPropertiesAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    } // applyProperties
    
} // class GetPropertiesAction