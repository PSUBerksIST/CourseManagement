/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.psu.berksist.CourseEZ;

/**
 *
 * @author admin_whb108
 * @version 0.1
 * 
 * Central location for all application constants.
 */

/*
    MODIFICATION LOG

   2016 March 25 - Initial creation - WHB

*/
public class AppConstants 
{
    public static String LAF = "LookAndFeel";
    public static String LAST_DB = "LastConnectedDatabase";
    
    
    // File Names ------------------------------------
    public static String DEFAULT_DB_FILE = "dbCourseManagement.db3";
    private static final String DEFAULT_XML_FILE = "UserPrefs.xml";
    
    public static String PREFS_XML_FILE;
    
    
    // Directories ------------------------------------
    public static String DB_DIR = "database/";
    public static String IMAGE_DIR = "images/";
    public static String PREFS_DIR = "userprefs/";
    public static String RELATIVE_PATH; // Path for resources included with jar file
    
    public static String ROOT_FOLDER = System.getProperty("user.dir") + "/resources/";
    
    
    // Icon Images --------(Alphabetical)------------------------
    public static String DB_OPEN_ICON_16 = "DatabaseAdd16.png";
    public static String DB_OPEN_ICON_32 = "DatabaseAdd32.png";
    public static String LOAD_ICON_16 = "DatabaseAdd16.png";
    public static String LOAD_ICON_32 = "DatabaseAdd32.png";
    public static String MINIMIZE_ICON_16 = "TileIcon16.png";
    public static String MINIMIZE_ICON_32 = "TileIcon32.png";
    public static String SAVE_ICON_16 = "DatabaseAdd16.png";
    public static String SAVE_ICON_32 = "DatabaseAdd32.png";
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
        DB_OPEN_ICON_16 = IMAGE_DIR + DB_OPEN_ICON_16;
        DB_OPEN_ICON_32 = IMAGE_DIR + DB_OPEN_ICON_32;
        LOAD_ICON_16 = IMAGE_DIR + LOAD_ICON_16;
        LOAD_ICON_32 = IMAGE_DIR + LOAD_ICON_32;
        MINIMIZE_ICON_16 = IMAGE_DIR + MINIMIZE_ICON_16;
        MINIMIZE_ICON_32 = IMAGE_DIR + MINIMIZE_ICON_32;
        SAVE_ICON_16 = IMAGE_DIR + SAVE_ICON_16;
        SAVE_ICON_32 = IMAGE_DIR + SAVE_ICON_32;
        TILE_ICON_16 = IMAGE_DIR + TILE_ICON_16;
        TILE_ICON_32 = IMAGE_DIR + TILE_ICON_32;
        
        PREFS_XML_FILE = PREFS_DIR + DEFAULT_XML_FILE;
        
        
    }
    
    public static void resetXMLToDefault()
    {
        PREFS_XML_FILE = PREFS_DIR + DEFAULT_XML_FILE;
    }
    
    public static void setXMLFile(String strPath)
    {
        PREFS_XML_FILE = strPath;
    }
    
} // ApplicationConstants
