/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.psu.berksist.CourseEZ;

/**
 *
 * @author William H. Bowers
 * 
 * This is a collection of common, miscellaneous functions put into a
 * single class.  Each function is declared static and final to allow
 * it to be used without instantiating the class, but not allowing them
 * to be extended and modified elsewhere.
 * 
 */

import de.svenjacobs.loremipsum.LoremIpsum;

/**
 *
 * @author admin_whb108
 */
public class CommonFunctions
{
   // Adapted from http://loremipsum.sourceforge.net/     

   /**
    *
    * @param intNumWords Number of "words" in the returned String.
    * @return String containing intNumWords number of words
    */
   public static final String getRandomStringByWordCount(int intNumWords)
   {
      LoremIpsum myIL = new LoremIpsum();
      String strTemp = myIL.getWords(intNumWords);
   //   System.out.println("strTemp = " + strTemp);
      return strTemp;
   } // getRandomStringByWordCount
     
// Adapted from http://loremipsum.sourceforge.net/

   /**
    *
    * @param intNumParas Number of paragraphs in the returned String.
    * @return String containing intNumParas number of paragraphs.
    */
   public static final String getRandomStringByParagraphCount(int intNumParas)
   {
      LoremIpsum myIL = new LoremIpsum();
      String strTemp = myIL.getParagraphs(intNumParas);
      //  System.out.println("strTemp = " + strTemp);
      return strTemp;
   } // getRandomStringByParagraphCount

   /**
    *
    * @param args
    */
   public static void main(String[] args)
   {
      String strByWordCount = getRandomStringByWordCount(100);
      System.out.println("strByWordCount = " + strByWordCount);
      
      String strByParaCount = getRandomStringByParagraphCount(10);
      System.out.println("strByParaCount = " + strByParaCount);
   } // main

} // class CommonFunctions
