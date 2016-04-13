
package IST261DesktopPaneDemo;




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
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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
    public GetPropertiesAction(jfMain jfIn, Properties propsIn) 
    {
        super("Get Properties");
        jfApp = jfIn;
        myProps = propsIn;
        
         putValue( Action.SMALL_ICON, new ImageIcon(
            getClass().getResource( "../images/DatabaseAdd16.png" ) ) );
         
          putValue( Action.LARGE_ICON_KEY, new ImageIcon(
            getClass().getResource( "../images/DatabaseAdd32.png" ) ) );
          
          putValue(Action.LONG_DESCRIPTION,"Load the user's properties from a file");
          
          putValue(Action.NAME, "Get Properties");
          
          putValue(Action.SHORT_DESCRIPTION,"Get Properties From File");
          
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

        try {
            // Gets the path of the running application
            // TODO Test running the application from the jar file
            
            String path = new File(".").getCanonicalPath();
            String strFileName;
            
            // If a preferences file was indicated from the command line
            // parameters, use it.  If not set it to the default name
            // in application path
            
            if(jfApp.strUserPrefsFile != null)
            {
                strFileName = jfApp.strUserPrefsFile;
            }
            else
            {
            strFileName = path + "\\" + ApplicationConstants.PREFS_XML_FILE;
            jfApp.strUserPrefsFile = strFileName;
            }
            
            
            myProps.loadFromXML(new FileInputStream(strFileName));
            applyProperties(myProps);
            //TODO Apply each property
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(GetPropertiesAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    } // actionPerformed
    
    private void applyProperties(Properties inProps)
    {
       Enumeration e = inProps.propertyNames();

       while (e.hasMoreElements()) 
       {
         String key = (String) e.nextElement();
         
         switch(key)
         {
             case "LookAndFeel":
         {
             try 
             {
                 UIManager.setLookAndFeel(inProps.getProperty(key));
             } 
             catch (ClassNotFoundException 
                  | InstantiationException 
                  | IllegalAccessException
                  | UnsupportedLookAndFeelException   
                     ex) 
             {
                 Logger.getLogger(GetPropertiesAction.class.getName()).log(Level.SEVERE, null, ex);
             } 
         }
                SwingUtilities.updateComponentTreeUI(jfApp);
                jfApp.jbTile.doClick();
                break;        
         } // switch
         System.out.println(key + " -- " + inProps.getProperty(key));
       } // while
    } // applyProperties
} // class GetPropertiesAction