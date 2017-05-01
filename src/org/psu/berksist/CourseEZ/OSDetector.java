/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.psu.berksist.CourseEZ;

import java.awt.Desktop;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rgs19
 */

/* 
 * April 21, 2017 - OSDetector implemented from - http://stackoverflow.com/questions/7024031/java-open-a-file-windows-mac - RGS
 * April 25, 2017 - Modified to use a different command for Linux/Unix
 */

public class OSDetector {
    
    private static boolean isWindows = false;
    private static boolean isMac = false;
    private static boolean isLinux = false;

    static
    {
        String os = System.getProperty("os.name").toLowerCase();
        isWindows = os.contains("win");
        isMac = os.contains("mac");
        isLinux = os.contains("nux") || os.contains("nix");
    }

    private static boolean isWindows() {return isWindows;}
    private static boolean isMac() {return isMac;}
    private static boolean isLinux() {return isLinux;}

    
    public static boolean open(File file) {
    try
    {
        if (OSDetector.isWindows()) {
            Runtime.getRuntime().exec(new String[] 
                {"rundll32", "url.dll,FileProtocolHandler",
                file.getAbsolutePath()});
            return true;
        } else if (OSDetector.isMac()) {
            Runtime.getRuntime().exec(new String[]{"/usr/bin/open",
                file.getAbsolutePath()});
            return true;
        } else if (OSDetector.isLinux()) {
            Runtime.getRuntime().exec(new String[]{"xgd-open",
                file.getAbsolutePath()});
            return true;
        } else {
            // Unknown OS, try with desktop
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(file);
                return true;
            } else {
                return false;
            }
        }
    } catch (Exception ex) {
        Logger.getLogger(jfMain.class.getName()).log(Level.SEVERE, null, ex);
        return false;
    }
    
}
    
}
