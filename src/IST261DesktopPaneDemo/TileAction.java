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
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
 
public class TileAction extends AbstractAction {
     
    private JDesktopPane desk; // the desktop to work with
     
    public TileAction(JDesktopPane desk) {
        super("Tile Frames");
        this.desk = desk;
//TODO: Fix image file locations
/*
         putValue( Action.SMALL_ICON, new ImageIcon(
            getClass().getResource("images/TileIcon16.png") ) );
         
          putValue( Action.LARGE_ICON_KEY, new ImageIcon(
            getClass().getResource( "../images/TileIcon32.png" ) ) );
*/          
          putValue(Action.LONG_DESCRIPTION,"Tile the frames on the desktop");
          
          putValue(Action.NAME, "Tile");
          
          putValue(Action.SHORT_DESCRIPTION,"Tile Windows");
          
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
         
        // Determine the necessary grid size
        int sqrt = (int)Math.sqrt(count);
        int rows = sqrt;
        int cols = sqrt;
        if (rows * cols < count) {
            cols++;
            if (rows * cols < count) {
                rows++;
            }
        }
         
        // Define some initial values for size & location.
        Dimension size = desk.getSize();
         
        int w = size.width / cols;
        int h = size.height / rows;
        int x = 0;
        int y = 0;
         
        // Iterate over the frames, deiconifying any iconified frames and then
        // relocating & resizing each.
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols && ((i * cols) + j < count); j++) {
                JInternalFrame f = allframes[(i * cols) + j];
                 
                if (!f.isClosed() && f.isIcon()) {
                    try {
                        f.setIcon(false);
                    } catch (PropertyVetoException ignored) {}
                }
                 
                desk.getDesktopManager().resizeFrame(f, x, y, w, h);
                x += w;
            }
            y += h; // start the next row
            x = 0;
        }
    }
}