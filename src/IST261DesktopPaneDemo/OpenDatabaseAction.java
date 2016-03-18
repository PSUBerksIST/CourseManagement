/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IST261DesktopPaneDemo;
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
 * Adapted from http://www.java-tips.org/how-to-tile-all-internal-frames-when-requested.html
 * 
 ********************* MODIFICATION LOG ************************
 *
 * 2016 February 02 -  Initial program creation

 */
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.beans.PropertyVetoException;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
 
public class OpenDatabaseAction extends AbstractAction {
     
    private jfMain jfApp; // the desktop to work with
     
    public OpenDatabaseAction(jfMain jfIn) {
        super("Open Database");
        jfApp = jfIn;
        
         putValue( Action.SMALL_ICON, new ImageIcon(
            getClass().getResource( "../images/DatabaseAdd16.png" ) ) );
         
          putValue( Action.LARGE_ICON_KEY, new ImageIcon(
            getClass().getResource( "../images/DatabaseAdd32.png" ) ) );
          
          putValue(Action.LONG_DESCRIPTION,"Tile the frames on the desktop");
          
          putValue(Action.NAME, "Open DB");
          
          putValue(Action.SHORT_DESCRIPTION,"Open Database");
          
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
     
    // Tile the open frames
    public void actionPerformed(ActionEvent ev) 
    {
        jfApp.dbConnection = jfApp.dbc.connectToDB();
    } // actionPerformed
}