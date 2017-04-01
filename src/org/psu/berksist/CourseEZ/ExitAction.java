/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.psu.berksist.CourseEZ;

import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

/**
 *
 * @author Robert Zwolinski
 */
public class ExitAction extends AbstractAction {

    private jfMain jfMainFrame;
    
    public ExitAction(jfMain jfMainIn) {

        jfMainFrame = jfMainIn;
        
        try {

        putValue(Action.SMALL_ICON, new ImageIcon
                (new URL(AppConstants.EXIT_ICON_16)));

        putValue(Action.LARGE_ICON_KEY, new ImageIcon
                (new URL(AppConstants.EXIT_ICON_32)));

    } catch (MalformedURLException ex) {
        Logger.getLogger(ExitAction.class.getName()).log(Level.SEVERE, null, ex);
    }

    putValue(Action.LONG_DESCRIPTION,"Exit application.");

    putValue(Action.NAME, "Exit");

    putValue(Action.SHORT_DESCRIPTION,"Exit application.");

    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        jfMainFrame.closeAll();
        System.exit(0);
    }

}
