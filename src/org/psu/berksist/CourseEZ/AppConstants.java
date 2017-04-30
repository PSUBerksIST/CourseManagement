/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.psu.berksist.CourseEZ;

import javax.swing.UIManager;

/**
 *
 * @author admin_whb108
 * @version 0.1
 * 
 * Central location for all application constants.
 * 
 *  ******************* MODIFICATION LOG *****************************************
 *  2017 April 29   -   Added DEBUG_MODE.
 *                      APP_VERSION changed to... 0.2. Because this is the second class of students working on the project. -JSS5783
 *  2017 April 28   -   Updated APP_ICON. -JSS5783
 *  2017 April 27   -   Added SPLASH_IMAGE_NAME. -JSS5783
 *  2016 March 25   -   Initial creation - WHB

*/
public class AppConstants 
{
    public static final String APP_ID = "CourseEZ";
    public static final String APP_VERSION = "0.2";
    
    public static final boolean DEBUG_MODE = false; //change to true to enable debug-mode-only tweaks like skipped splash screens and test outputs to the console.
    
    public static String LAF = "LookAndFeel";
    public static String LAST_DB = "LastConnectedDatabase";
    
    public static String DEFAULT_LAF = getLAFClass("Nimbus");
    
    public static final String SPLASH_IMAGE_NAME = "LIONScroll_-_splash_v0_2017_04_23_alt-colors.png";
    
    
    // File Names ------------------------------------
    public static String DEFAULT_DB_FILE = "dbCourseManagement-V9.db3";
    public static String DEFAULT_XML_FILE = "UserPrefs.xml";
    
    public static String IMPORTED_XML_FILE;
    
    
    // Directories ------------------------------------
    public static String DB_DIR = "database/";
    public static String IMAGE_DIR = "images/";
    public static String PREFS_DIR = "userprefs/";
    public static String RELATIVE_PATH; // Path for resources included with jar file
    
    public static String ROOT_FOLDER = System.getProperty("user.dir") + "/resources/";
    
    
    // Icon Images --------(Alphabetical)------------------------
    public static String APP_ICON = "LIONScroll_-_icon_v0_2017_04_23_alt-colors.png";
    public static String CASCADE_ICON_16 = "CascadeIcon16.png";
    public static String CASCADE_ICON_32 = "CascadeIcon32.png";
    public static String DB_OPEN_ICON_16 = "DatabaseIcon16.png";
    public static String DB_OPEN_ICON_32 = "DatabaseIcon32.png";
    public static String EXIT_ICON_16 = "ExitIcon16.png";
    public static String EXIT_ICON_32 = "ExitIcon32.png";
    public static String LOAD_ICON_16 = "ImportIcon16.png";
    public static String LOAD_ICON_32 = "ImportIcon32.png";
    public static String MINIMIZE_ICON_16 = "MinimizeIcon16.png";
    public static String MINIMIZE_ICON_32 = "MinimizeIcon32.png";
    public static String SAVE_ICON_16 = "ExportIcon16.png";
    public static String SAVE_ICON_32 = "ExportIcon32.png";
    public static String TILE_ICON_16 = "TileIcon16.png";
    public static String TILE_ICON_32 = "TileIcon32.png";
    
    
    public static void setRelativePath(String strPath)
    {
         RELATIVE_PATH = strPath;
         
         setDirectories();
    }
    
    public static void setDirectories()
    {
        // Packages Directories (jar)
        IMAGE_DIR = RELATIVE_PATH + IMAGE_DIR;
        
        
        DB_DIR = ROOT_FOLDER + DB_DIR;
        PREFS_DIR = ROOT_FOLDER + PREFS_DIR;
        
        setFiles();
    }
    
    public static void setFiles()
    {
        APP_ICON = IMAGE_DIR + APP_ICON;
        CASCADE_ICON_16 = IMAGE_DIR + CASCADE_ICON_16;
        CASCADE_ICON_32 = IMAGE_DIR + CASCADE_ICON_32;
        DB_OPEN_ICON_16 = IMAGE_DIR + DB_OPEN_ICON_16;
        DB_OPEN_ICON_32 = IMAGE_DIR + DB_OPEN_ICON_32;
        EXIT_ICON_16 = IMAGE_DIR + EXIT_ICON_16;
        EXIT_ICON_32 = IMAGE_DIR + EXIT_ICON_32;
        LOAD_ICON_16 = IMAGE_DIR + LOAD_ICON_16;
        LOAD_ICON_32 = IMAGE_DIR + LOAD_ICON_32;
        MINIMIZE_ICON_16 = IMAGE_DIR + MINIMIZE_ICON_16;
        MINIMIZE_ICON_32 = IMAGE_DIR + MINIMIZE_ICON_32;
        SAVE_ICON_16 = IMAGE_DIR + SAVE_ICON_16;
        SAVE_ICON_32 = IMAGE_DIR + SAVE_ICON_32;
        TILE_ICON_16 = IMAGE_DIR + TILE_ICON_16;
        TILE_ICON_32 = IMAGE_DIR + TILE_ICON_32;
        
        IMPORTED_XML_FILE = PREFS_DIR + DEFAULT_XML_FILE;
        DEFAULT_XML_FILE = PREFS_DIR + DEFAULT_XML_FILE;
        
        DEFAULT_DB_FILE = DB_DIR + DEFAULT_DB_FILE;
        
    }
    
    /*public static void resetXMLToDefault()
    {
        PREFS_XML_FILE = PREFS_DIR + DEFAULT_XML_FILE;
    }*/
    
    public static void setXMLFile(String strPath)
    {
        IMPORTED_XML_FILE = strPath;
    }
    
    private static String getLAFClass(String strLAFIn)
    {
        // Gets the class name of the specified Look and Feel
        for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
            if (strLAFIn.equals(info.getName())) {
                return info.getClassName();
            }
        }

        return "";
    }
    
} // ApplicationConstants
