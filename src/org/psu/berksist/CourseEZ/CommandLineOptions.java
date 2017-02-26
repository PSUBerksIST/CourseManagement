/*
 * See https://commons.apache.org/proper/commons-cli/usage.html
 */


package org.psu.berksist.CourseEZ;

import org.apache.commons.cli.*;

/**
 *
 * @author admin_whb108
 */
public class CommandLineOptions 
{
    
  

    public CommandLineOptions() 
    {
     
     
    } // constructor
    
    
    public static Options makeOptions()
    {
        Options optTemp = new Options();
      
        optTemp.addOption("h", "Display usage information");
        optTemp.addOption("u", true, "User Properties File");
        return optTemp;
        
    } // makeOptions
    
    
    public static CommandLine processCommandLine(String[] args) throws ParseException
    {
       CommandLineParser parser = new DefaultParser();
       CommandLine cmd = parser.parse( makeOptions(), args);       
       return cmd;
    } // processCommandLine
} // CommandLineOptions
