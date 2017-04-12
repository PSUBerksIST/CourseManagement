/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.psu.berksist.CourseEZ;

import java.awt.Color;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import static java.lang.Thread.sleep;
import java.util.ArrayList;
import java.util.Random;
import java.util.List;
import javax.swing.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rgs19
 */

/***************************** MODIFICATION LOG *****************************
 *                      Rishabh G. Shanbhag - rgs19@psu.edu
 * 
 * 2017 April 4  - Initial program creation
 * 2017 April 5  - Shifted from making use of the JSplashScreen library to making use of a JWindow
 *                 Added the ability to resize splash screen image. Changed background & font colors.
 * 
 * 2017 April 7  - Added code for progress bar, which needs to be revised.
 * 2017 April 10 - Fixed progress bar (working and displays correctly on splash screen)
 *                 Minor change - Removed unused import. Implemented a GridLayout.
 * 2017 April 11 - Added tip of the day functionality 
 * 2017 April 12 - Minor changes including changing the grid layout and fonts/sizes. 
 *                 Addition of documentation & modification log
 ****************************************************************************/


public class SplashScreen extends JWindow {
    
    private static JProgressBar pbar;
    private static String tipoftheday;
    
    Thread t = null;
    Container myCP;
    
  private int duration;
  public SplashScreen(int d) {
    duration = d;
    myCP = this.getContentPane();
    myCP.setLayout(new GridLayout(0,3)); //Java Grid layout (rows, columns) format
  }

  public void showSplash() throws IOException {
    
    JPanel content = (JPanel)getContentPane();
    content.setBackground(Color.BLACK);

    int w = 500; //width of the splash screen
    int h = 250; //height of the splash screen
    
    Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
    int x = (screen.width-w)/2;
    int y = (screen.height-h)/2;
    setBounds(x,y,w,h);

    /* code to re-size image found here http://www.nullpointer.at/2011/08/21/java-code-snippets-howto-resize-an-imageicon/ */
   
    ImageIcon imageIcon = new ImageIcon(AppConstants.ROOT_FOLDER + AppConstants.IMAGE_DIR + "AppIcon-temp.png"); // load the image to a imageIcon
    Image image = imageIcon.getImage(); // transform it 
    Image newimg = image.getScaledInstance(100, 100,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
    imageIcon = new ImageIcon(newimg);  // transform it back
    
    //Code for Icon present in the splash screen
    JLabel splshicon = new JLabel(imageIcon); //fetches icon from previous code where it is transformed bacl
    splshicon.setHorizontalAlignment(JLabel.CENTER);
    content.add(splshicon, BorderLayout.SOUTH);
    
    // Code for Course EZ header in the splash screen
    JLabel header = new JLabel("CourseEZ", JLabel.CENTER);
    header.setForeground(Color.white); //color of text 
    header.setFont(new Font("Sans-Serif",Font.BOLD, 24));
    content.add(header);
    
    
    //Code for Copyright text in the splash screen
    JLabel text = new JLabel("Copyright 2017, CourseEZ", JLabel.CENTER);
    text.setForeground(Color.white);  
    text.setFont(new Font("Sans-Serif",Font.PLAIN, 10));
    content.add(text, BorderLayout.SOUTH);
    
    
    //Code for Tip of the Day functionality
    BufferedReader reader = new BufferedReader(new FileReader(AppConstants.ROOT_FOLDER + "tipoftheday.txt")); //fetches text from "tipoftheday.txt"
    String line = reader.readLine();
    List<String> lines = new ArrayList<>();
    while (line != null) {
     lines.add(line);
     line = reader.readLine();
    }
    Random r = new Random();
    tipoftheday = lines.get(r.nextInt(lines.size()));
    
    JLabel tod = new JLabel(tipoftheday);
    // System.out.println(tipoftheday); //to test the output for tipoftheday
    tod.setForeground(Color.white);  
    tod.setHorizontalAlignment(JLabel.CENTER);
    content.add(tod, BorderLayout.CENTER);
    
    //code to display Progress Bar
    showProgressBar(); 
    content.add(pbar, BorderLayout.CENTER); //progress bar layout
    
    Color myColor = Color.ORANGE; //to set border color of splash screen
    content.setBorder(BorderFactory.createLineBorder(myColor, 10)); //Sets border with border color and width
    setVisible(true); //true to display window
    
    try { Thread.sleep(duration); } catch (Exception e) {}

    setVisible(false);
  }
  
  
  //code for progress bar
  public void showProgressBar()
    {
    
    pbar = new JProgressBar();
        pbar.setMinimum(0);
        pbar.setMaximum(100);
        pbar.setStringPainted(true);
        pbar.setForeground(Color.LIGHT_GRAY);
        add(pbar);
        //pbar.setPreferredSize(new Dimension(310, 30));
        //pbar.setBounds(50, 50, 10, 10);
        
 
        Thread T1;
        T1 = new Thread() {
            
            @Override
            public void run() {
                int i = 0;
                while (i <= 100) {
                    pbar.setValue(i);
                    try {
                        sleep(90);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(SplashScreen.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    i++;
                }
            }
        };
        T1.start();
    }
  
  public void showSplashAndExit() throws IOException {
    showSplash();
    System.exit(0);
    
  } //showSplashAndExit()
 
  public static void main(String[] args) throws IOException {
      
    SplashScreen splash = new SplashScreen(10000); //change time to display splash screen here
    splash.showSplashAndExit();
    
  } // main
  
} // class SplashScreen