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
 
public class TileAction extends AbstractAction {
     
    private JDesktopPane desk; // the desktop to work with
     
    public TileAction(JDesktopPane desk) {
        super("Tile Frames");
        this.desk = desk;

        configureAction();
        
    }
    
    public TileAction() {
        super("Tile Frames");
        
        configureAction();
    }
    
    public void setDesktop(JDesktopPane desk) {
        this.desk = desk;
    }
    
    private void configureAction() {
        
        try {
            
            putValue(Action.SMALL_ICON, new ImageIcon
                    (new URL(AppConstants.TILE_ICON_16)));
            
            putValue(Action.LARGE_ICON_KEY, new ImageIcon
                    (new URL(AppConstants.TILE_ICON_32)));
            
        } catch (MalformedURLException ex) {
            Logger.getLogger(TileAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        putValue(Action.LONG_DESCRIPTION,"Tile the frames on the desktop");
          
        putValue(Action.NAME, "Tile");
          
        putValue(Action.SHORT_DESCRIPTION,"Tile Windows");
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