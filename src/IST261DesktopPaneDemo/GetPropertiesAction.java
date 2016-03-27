
package IST261DesktopPaneDemo;




/**
 *
 * @author William H. Bowers (admin_whb108)
 * 
 * @version 0.1
 * 

 * 
 ********************* MODIFICATION LOG ************************
 *
 * 2016 February 02 -  Initial program creation

 */
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.beans.PropertyVetoException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
 
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
          
          putValue(Action.LONG_DESCRIPTION,"Tile the frames on the desktop");
          
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
            String path = new File(".").getCanonicalPath();
            String strFileName = path + "\\" + ApplicationConstants.PREFS_XML_FILE;

            myProps.loadFromXML(new FileInputStream(strFileName));
            
            //TODO Apply each property
        } catch (IOException ex) {
            Logger.getLogger(GetPropertiesAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    } // actionPerformed
}