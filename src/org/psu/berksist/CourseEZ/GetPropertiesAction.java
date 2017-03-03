
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
 * 2016 February 02 -  Initial program creation

 */
import java.awt.event.ActionEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
 
public class GetPropertiesAction extends AbstractAction {
     
    private jfMain jfApp; // the desktop to work with
    private Properties myProps; 
    
    //private String strUserPrefsFile;
    
    public GetPropertiesAction(Properties propsIn) 
    {
        super("Get Properties");
        
        //strUserPrefsFile = strUserFileIn;
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
            
            putValue( Action.LARGE_ICON_KEY, new ImageIcon
                    (new URL(AppConstants.LOAD_ICON_32)));
            
        } catch (MalformedURLException ex) {
            Logger.getLogger(SavePropertiesAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        putValue(Action.LONG_DESCRIPTION,"Load the user's properties from a file");
          
        putValue(Action.NAME, "Get Properties");
          
        putValue(Action.SHORT_DESCRIPTION,"Get Properties From File");
        
    }
    
    public void actionPerformed(ActionEvent ev) 
    {

        //TODO: Add a JFileChooser when selecting 'Load Options' from JMenuItem - RQZ
       
        loadPrefs();
        
    } // actionPerformed
    
    public void loadPrefs()
    {
        try {
            
            myProps.loadFromXML(new FileInputStream(AppConstants.PREFS_XML_FILE));
            applyProperties(myProps);
            
        } catch (FileNotFoundException ex) {
            
            System.out.println("Bad Prefs file selected. Loading Default..");
            
            // Revert back to default XML file
            AppConstants.resetXMLToDefault();
            
            try {
                myProps.loadFromXML(new FileInputStream(AppConstants.PREFS_XML_FILE));
                applyProperties(myProps);
                
                System.out.println("Successfully Loaded Default XML File");
                
            }  catch (IOException ex1) {
                Logger.getLogger(GetPropertiesAction.class.getName()).log(Level.SEVERE, null, ex1);
            }
            Logger.getLogger(GetPropertiesAction.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GetPropertiesAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    } // loadPrefs
    
    private void applyProperties(Properties inProps)
    {
        Enumeration e = inProps.propertyNames();

        while (e.hasMoreElements()) 
        {
            String key = (String) e.nextElement();
         
            switch(key)
            {
                case "LookAndFeel":
            
                    try 
                    {
                        UIManager.setLookAndFeel(inProps.getProperty(key));
                    } 
                    catch (ClassNotFoundException 
                          | InstantiationException 
                          | IllegalAccessException
                          | UnsupportedLookAndFeelException ex) 
                    {
                        Logger.getLogger(GetPropertiesAction.class.getName()).log(Level.SEVERE, null, ex);
                    } 
                    
                    if (jfApp != null){
                        SwingUtilities.updateComponentTreeUI(jfApp);
                    }
                    
                    break;        
            } // switch
         
        
        System.out.println(key + " -- " + inProps.getProperty(key));
        
        } // while
        
    } // applyProperties
    
} // class GetPropertiesAction