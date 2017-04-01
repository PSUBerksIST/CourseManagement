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
 * Adapted from http://www.java-tips.org/how-to-tile-all-internal-frames-when-requested.html
 * 
 ********************* MODIFICATION LOG ************************
 *
 * 2016 February 02 -  Initial program creation

 */
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.beans.PropertyVetoException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
 
public class MinimizeAllWindowsAction extends AbstractAction {
     
    private JDesktopPane desk; // the desktop to work with
     
    public MinimizeAllWindowsAction(JDesktopPane desk) {
        super("Minimize Windows");
        this.desk = desk;
        
        try {
            
            putValue(Action.SMALL_ICON, new ImageIcon
                    (new URL(AppConstants.MINIMIZE_ICON_16)));
            
            putValue( Action.LARGE_ICON_KEY, new ImageIcon
                    (new URL(AppConstants.MINIMIZE_ICON_32)));
            
        } catch (MalformedURLException ex) {
            Logger.getLogger(MinimizeAllWindowsAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        putValue(Action.LONG_DESCRIPTION,"Minimize all of the frames on the desktop");
          
        putValue(Action.NAME, "Minimize");
          
        putValue(Action.SHORT_DESCRIPTION,"Minimize All Windows");
          
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
    public void actionPerformed(ActionEvent ev) {
       
        // How many frames do we have?
        JInternalFrame[] allframes = desk.getAllFrames();
        
        int count = allframes.length;
        if (count == 0) return;
         
       
         
        // Iterate over the frames, deiconifying any iconified frames and then
        // relocating & resizing each.
        for (int i = 0; i < count; i++) 
        {
            try {
                allframes[i].setIcon(true);
            } catch (PropertyVetoException ex) {
                Logger.getLogger(MinimizeAllWindowsAction.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}