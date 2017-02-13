/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package IST261DesktopPaneDemo;

/**
 *
 * @author William H. Bowers (admin_whb108)
 * 
 * @version 0.1
 * 
 * Adapted from http://www.java2s.com/Code/Java/Swing-JFC/Thisprogramdemonstratestheuseofinternalframes.htm
 * 
 ********************* MODIFICATION LOG ************************
 *
 * 2016 February 02 -  Initial program creation
 * 2017 February 13 -  Fixed cascade

 */
import java.awt.event.ActionEvent;
import java.beans.PropertyVetoException;
import javax.swing.AbstractAction;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
 
public class CascadeAction extends AbstractAction {
     
    private JDesktopPane desktop; // the desktop to work with
     
    public CascadeAction(JDesktopPane desk) {
        super("Cascade Frames");
        desktop = desk;
    }
     
    public void actionPerformed(ActionEvent ev) {
         
      int x = 0;
      int y = 0;
      int width = desktop.getWidth() / 2;
      int height = desktop.getHeight() / 2;
      JInternalFrame[] frame = desktop.getAllFrames();

      for (int i = frame.length - 1; i >= 0; i--)
      {
         if (!frame[i].isIcon())
         {
            try
            {
               // try to make maximized frames resizable; this might be vetoed
               frame[i].setMaximum(false);
               frame[i].reshape(x, y, width, height);
               int  frameDistance = frame[i].getHeight() - frame[i].getContentPane().getHeight();
               x += frameDistance;
               y += frameDistance;
               // wrap around at the desktop edge
               if (x + width > desktop.getWidth()) x = 0;
               if (y + height > desktop.getHeight()) y = 0;
            }
            catch (PropertyVetoException e)
            {
            }
         }
      }
}
}