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
 * 
 ********************* MODIFICATION LOG ************************
 *
 * 2016 March 25    -  Added Preferences parameter to allow last used database
 *                     to be set and saved. - WHB
 * 
 * 2016 February 02 -  Initial program creation

 */

import java.util.prefs.*;
import java.awt.event.ActionEvent;
import java.util.Properties;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
 
public class OpenDatabaseAction extends AbstractAction {
     
    private jfMain jfApp; // the desktop to work with
    Properties propUserProperties;
     
    public OpenDatabaseAction(jfMain jfIn, Properties propsIn) 
    {
        super("Open Database");
        jfApp = jfIn;
        propUserProperties = propsIn;
        
         putValue( Action.SMALL_ICON, new ImageIcon(
            getClass().getResource( "../images/DatabaseAdd16.png" ) ) );
         
          putValue( Action.LARGE_ICON_KEY, new ImageIcon(
            getClass().getResource( "../images/DatabaseAdd32.png" ) ) );
          
          putValue(Action.LONG_DESCRIPTION,"Opens and connects to the database");
          
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
    } // constructor
     
    
    public void actionPerformed(ActionEvent ev) 
    {
        jfApp.dbConnection = jfApp.dbc.connectToDB(propUserProperties);
                  
    } // actionPerformed
}