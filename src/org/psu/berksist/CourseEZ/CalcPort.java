/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.psu.berksist.CourseEZ;

import java.io.IOException;
import java.io.*;

/**
 *
 * @author rgs19
 */

/***************************** MODIFICATION LOG *****************************
 * 
 * 2017 April 26 - Initial program creation - RGS (rgs19@psu.edu)
 ****************************************************************************/


public class CalcPort {

    public static void DisplayPanel(String a)
    {
       jfHelp.jEditorPane1.setEditable(false);   
     
        try 
        {
            jfHelp.jEditorPane1.setPage(a); // to set the jEditorPane1 to the URL ( in String a)
        } // try
        
        catch (IOException e) 
        {
            jfHelp.jEditorPane1.setContentType("text/html");
            jfHelp.jEditorPane1.setText("<html>Could not load '" + a + "' <br> Kindly check your URL <i>(Please add 'http://' prior to URL)</i> and the web connection. </html>"); // error notification
        } // catch
        
    } // DisplayPanel
    
  public static void main(String[] args) {
      
  } // main
  
} // class CalcPort
  
