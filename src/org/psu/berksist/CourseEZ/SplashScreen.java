/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.psu.berksist.CourseEZ;

import java.awt.Color;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import static java.lang.Thread.sleep;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rgs19
 * 
 *  ******************* MODIFICATION LOG *****************************************
 *  2017 April 12   -   Updated variable names to be more descriptive and be more compliant with naming conventions.
 *                      Added more comments.
 *                      Changed copyright text to reference Penn State Berks IST instead of the application name,
 *                          which should go elsewhere.
 *                      Cleaned up code (removed unused main(), merged unnecessary functions,
 *                          separated initialization/declaration/registration, made function names clearer, etc.).
 *                      Made the loading bar look slimmer. -JSS
 */
    

public class SplashScreen extends JWindow
{
    private static JProgressBar jpbLoadingBar;
    private static final int DEFAULT_DISPLAY_TIME = 1000;
    Thread t = null;
    //Container myCP;
    private int intDisplayDuration;
    
    
    
    /**
     * Creates an instance of SplashScreen with the default display duration time of DEFAULT_DISPLAY_TIME ticks.
     */
    public SplashScreen()
    {
        intDisplayDuration = DEFAULT_DISPLAY_TIME;
    }
    
    
    
    /**
     * Creates an instance of SplashScreen with the user-given input of intDisplayDuration ticks.
     * If input is invalid, default to DEFAULT_DISPLAY_TIME.
     * @param intDisplayDuration A non-negative integer for how long the splash screen should be displayed in ticks.
     */
    public SplashScreen(int intDisplayDuration)
    {
        if (intDisplayDuration >= 0)
        {
            this.intDisplayDuration = intDisplayDuration;
            //myCP = this.getContentPane();
            //myCP.setLayout(new GridLayout(2,1));
            //Java Grid layout
        }
        else
        {
            this.intDisplayDuration = DEFAULT_DISPLAY_TIME;
        }
    }

    
    
    /**
     * Displays the progress bar.
     */
    public void startProgressBar()
    {

      //jpbLoadingBar = new JProgressBar();
//        jpbLoadingBar.setMinimum(0);
//        jpbLoadingBar.setMaximum(100);
        //add(jpbLoadingBar);
        //jpbLoadingBar.setPreferredSize(new Dimension(310, 30));
        //jpbLoadingBar.setBounds(50, 50, 10, 10);

        Thread T1;
        T1 = new Thread()
        {
            @Override
            public void run()
            {
                int i = 0;
                while (i <= 100)
                {
                    jpbLoadingBar.setValue(i);
                    try
                    {
                        sleep(90);
                    }
                    catch (InterruptedException ex)
                    {
                        Logger.getLogger(SplashScreen.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    i++;
                }
            }
        };
        T1.start();
    }

    
    
    /**
     * Displays the splash screen and then hides the splash screen when finished.
     */
    public void showSplash()
    {
        //declaration
        JPanel jpContent;
        JPanel jpLoadingBar;
        int intSplashWindowWidth;
        int intSplashWindowHeight;
        Dimension dimScreen;
        int intSplashPosX;
        int intSplashPosY;
        JLabel lblCopyright;
        ImageIcon imicSplash;
        Image imgSplash;
        JLabel lblSplashImage;

        //initialization and customization
        //jpContent = (JPanel)getContentPane();
        jpContent = new JPanel(new BorderLayout());
        jpContent.setBackground(Color.BLACK);
        jpLoadingBar = new JPanel();
        //jpLoadingBar.setLayout(new FlowLayout());
        jpLoadingBar.setBackground(Color.BLACK);
        intSplashWindowWidth = 500;
        intSplashWindowHeight = 250;
        dimScreen = Toolkit.getDefaultToolkit().getScreenSize();
        intSplashPosX = (dimScreen.width - intSplashWindowWidth) / 2;
        intSplashPosY = (dimScreen.height - intSplashWindowHeight) / 2;
        //code to resize image from: http://nullpointer.at/2011/08/21/java-code-snippets-howto-resize-an-imageicon
        imicSplash = new ImageIcon(AppConstants.ROOT_FOLDER + AppConstants.IMAGE_DIR + "AppIcon-temp.png");   //load the image to a imageIcon
        imgSplash = imicSplash.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);              // transform it 
        lblSplashImage = new JLabel(new ImageIcon(imgSplash) );
        lblCopyright = new JLabel("Copyright 2017, Penn State Berks IST", JLabel.CENTER);
        lblCopyright.setForeground(Color.WHITE);
        jpbLoadingBar = new JProgressBar();
        jpbLoadingBar.setStringPainted(true);
        jpbLoadingBar.setForeground(Color.LIGHT_GRAY);
        jpbLoadingBar.setMinimum(0);
        jpbLoadingBar.setMaximum(100);
        jpbLoadingBar.setPreferredSize(new Dimension(intSplashWindowWidth - 20, 15));   //intSplashWindowWidth  - 20 pixels wide to account for two 10px borders, 15px-high to match textboxes (15px high by default, I believe)
        setBounds(intSplashPosX, intSplashPosY, intSplashWindowWidth, intSplashWindowHeight);

    //        JPanel jpContent = (JPanel)getContentPane();
    //        jpContent.setBackground(Color.BLACK);
    //
    //        int intSplashWindowWidth = 500;
    //        int intSplashWindowHeight = 250;
    //
    //        Dimension dimScreen = Toolkit.getDefaultToolkit().getScreenSize();
    //        int intSplashPosX = (dimScreen.width - intSplashWindowWidth) / 2;
    //        int intSplashPosY = (dimScreen.height - intSplashWindowHeight) / 2;

//        setBounds(intSplashPosX, intSplashPosY, intSplashWindowWidth, intSplashWindowHeight);

        /* code to re-size image found here http://www.nullpointer.at/2011/08/21/java-code-snippets-howto-resize-an-imageicon/ */

//        ImageIcon imicSplash = new ImageIcon(AppConstants.ROOT_FOLDER + AppConstants.IMAGE_DIR + "AppIcon-temp.png");   //load the image to a imageIcon
//        Image imgSplash = imicSplash.getImage().getScaledInstance(120, 120,  java.awt.Image.SCALE_SMOOTH);              // transform it 
        //imicSplash = new ImageIcon(imgSplash);  // transform it back

//        JLabel lblSplashImage = new JLabel(new ImageIcon(imgSplash));

        //registration
        jpLoadingBar.add(jpbLoadingBar);
        jpContent.add(jpLoadingBar, BorderLayout.CENTER);
        jpContent.add(lblSplashImage, BorderLayout.NORTH);
        jpContent.add(lblCopyright, BorderLayout.SOUTH);
        jpContent.setBorder(BorderFactory.createLineBorder(Color.ORANGE, 10) );  //draws border
        add(jpContent);
        
        startProgressBar(); //to display progress bar

        setVisible(true);   //displays splash

        try
        {
            Thread.sleep(intDisplayDuration);
        }
        catch (Exception e)
        {
            //TODO: Add exception-handling code.
        }

        setVisible(false);
    }
}