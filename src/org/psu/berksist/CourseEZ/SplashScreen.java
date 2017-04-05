/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.psu.berksist.CourseEZ;

//import com.thehowtotutorial.splashscreen.JSplash;
import java.awt.Color;
import java.awt.*;
import javax.swing.*;

/**
 *
 * @author rgs19
 */
    

public class SplashScreen extends JWindow {
    
  private int duration;
  public SplashScreen(int d) {
    duration = d;
  }

  public void showSplash() {
    JPanel content = (JPanel)getContentPane();
    content.setBackground(Color.black);

    int w = 500; //width of the splash screen
    int h = 250; //height of the splash screen
    
    Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
    int x = (screen.width-w)/2;
    int y = (screen.height-h)/2;
    setBounds(x,y,w,h);

    /* code to re-size image found here http://www.nullpointer.at/2011/08/21/java-code-snippets-howto-resize-an-imageicon/ */
   
    ImageIcon imageIcon = new ImageIcon(AppConstants.ROOT_FOLDER + AppConstants.IMAGE_DIR + "AppIcon-temp.png"); // load the image to a imageIcon
    Image image = imageIcon.getImage(); // transform it 
    Image newimg = image.getScaledInstance(120, 120,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
    imageIcon = new ImageIcon(newimg);  // transform it back
    
    JLabel label = new JLabel(new ImageIcon(newimg));
    System.out.println(AppConstants.ROOT_FOLDER + AppConstants.IMAGE_DIR + "RESIZED-NEWAppIcon-temp.png");
    
    JLabel text = new JLabel
      ("Copyright 2017, CourseEZ", JLabel.CENTER);
    text.setForeground(Color.white);
    content.add(label, BorderLayout.CENTER);
    content.add(text, BorderLayout.SOUTH);
    Color myColor = Color.ORANGE;
    content.setBorder(BorderFactory.createLineBorder(myColor, 10));
    
    setVisible(true); //true to display window

    try { Thread.sleep(duration); } catch (Exception e) {}

    setVisible(false);
  }

  public void showSplashAndExit() {
    showSplash();
    System.exit(0);
  }

  public static void main(String[] args) {
      
    SplashScreen splash = new SplashScreen(10000); //change time to display splash screen here
    splash.showSplashAndExit();
    
  }
}
    
    /* Code used with JSplashScreen libray - errors found while running
    
    public static void main(String[] args) {
    
    try{
    
    JSplash splash = new JSplash(SplashScreen.class.getResource(AppConstants.IMAGE_DIR + "splash.png"), true, true, false , "V1", null, Color.RED ,Color.BLACK);

 
    
    splash.splashOn();
    
    splash.setProgress(20, "Init");
    Thread.sleep(1000);
    splash.setProgress(40, "Loading");
    Thread.sleep(1000);
    splash.setProgress(60, "Applying Configs");
    Thread.sleep(1000);
    splash.setProgress(80, "Starting app..");
    Thread.sleep(1000);
    
    splash.splashOff();
    
    
} 
catch (Exception e) {
    e.printStackTrace();
}
    
}

*/
    
