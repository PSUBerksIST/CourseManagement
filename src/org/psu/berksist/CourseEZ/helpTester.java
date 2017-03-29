/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.psu.berksist.CourseEZ;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import oracle.help.Help;
import oracle.help.library.helpset.HelpSet;
import oracle.help.library.helpset.HelpSetParseException;


import org.psu.berksist.CourseEZ.ClassInfo;

/**
 *
 * @author admin_whb108
 */
public class helpTester extends JFrame
{
   
   public static void main(String[] args)
   {
      try
      {
         helpTester myHT = new helpTester();
          Class htmlBrowserClass = null;
       
            htmlBrowserClass = Class.forName("oracle.help.htmlBrowser.ICEBrowser");
      
         
//         ICEBrowser myIB = new ICEBrowser();
         Help myH = new Help(htmlBrowserClass);
        
         HelpSet myHS = new HelpSet(ClassInfo.class, "resources\\HelpFiles\\OHJ.hs");
      myH.addBook(myHS);
         myH.showNavigatorWindow();
      }
      
      catch (HelpSetParseException ex)
      {
         Logger.getLogger(helpTester.class.getName()).log(Level.SEVERE, null, ex);
      } catch (ClassNotFoundException ex) {
           ex.printStackTrace();
       }
    
     
      
      
   }
}
