/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.psu.berksist.CourseEZ;

import java.awt.Color;
import java.awt.*;
import static java.lang.Thread.sleep;
import java.sql.*;
import java.util.Random;
import javax.swing.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import sas.swing.MultiLineLabel;
import sas.swing.plaf.MultiLineLabelUI;

/**
 *
 * @author rgs19
 * 
 *  ******************* MODIFICATION LOG *****************************************
 *  2017 April 27   -   Tweaked color scheme. -JSS5783
 *  2017 April 27   -   Replaced splash image name with constant name.
 *                      Modified color scheme to match image (as constants for easy modification).
 *                      Moved custom splash images to images folder from resources folder. -JSS5783
 *  2017 April 21   -   Addition of the name of the person who says the quote.
 *                      Quotes are now within a ' ' for a better look. - RGS
 *  2017 April 18   -   Quotes are now displayed properly.
 *                      Made use of a Multiline library - https://github.com/sasjo/multiline - RGS
 *  2017 April 14   -   Added new tip of the day functionality to fetch quotes from a database. - RGS
 *  2017 April 13   -   Minor changes to the file. Added an import so that splash screen works properly. 
 *                      Increased splash screen display time. - RGS
 *  2017 April 12   -   Cleaned up merged file.
 *                      Moved declaration to main body of class.
 *                      Modified layout.    -JSS
 *  2017 April 12   -   Minor changes including changing the grid layout and fonts/sizes. 
 *                      Addition of documentation & modification log. -RGS
 *  2017 April 12   -   Updated variable names to be more descriptive and be more compliant with naming conventions.
 *                      Added more comments.
 *                      Changed copyright text to reference Penn State Berks IST instead of the application name,
 *                          which should go elsewhere.
 *                      Cleaned up code (removed unused main(), merged unnecessary functions,
 *                          separated initialization/declaration/registration, made function names clearer, etc.).
 *                      Made the loading bar look slimmer. -JSS
 *  2017 April 11   -   Added tip of the day functionality. -RGS
 *  2017 April 10   -   Fixed progress bar (working and displays correctly on splash screen).
 *                      Minor change: removed unused import. Implemented a GridLayout. -RGS
 *  2017 April  7   -   Added code for progress bar, which needs to be revised. -RGS
 *  2017 April  5   -   Shifted from making use of the JSplashScreen library to making use of a JWindow
 *                      Added the ability to resize splash screen image.
 *                      Changed background & font colors.   -RGS
 * 2017 April  4    -   Initial program creation.   -RGS
 */



public class SplashScreen extends JWindow
{
    //declaration
    private static final String strDBClass = "org.sqlite.JDBC";
    private static final String strJDBCString = "jdbc:sqlite:";
    private static String strDBName = "resources/database/Today.db3"; //this is what worked on my Macbook. Makes use of the Today.db3 present in the IST261CourseManagement folder.  
    private boolean bDebugging = true;
    private static JProgressBar jpbLoadingBar;
    private static final int DEFAULT_DISPLAY_TIME = 10000;
    private static final int BORDER_WIDTH = 8;      //remember that the number of pixels occupied by the border is multiplied by 2
    private static final int SPLASH_IMAGE_WIDTH = 192;
    private static final int SPLASH_IMAGE_HEIGHT = 192;
    //private static final Color BORDER_COLOR = Color.ORANGE;   //old color scheme before custom splash image was used
    private static final Color BORDER_COLOR = new Color(0, 0, 128);
    //private static final Color BACKGROUND_COLOR = Color.BLACK;  //old color scheme before custom splash image was used
    private static final Color BACKGROUND_COLOR = new Color(98, 128, 192);
//    private static final Color TEXT_COLOR = Color.WHITE;    //old color scheme before custom splash image was used
    private static final Color TEXT_COLOR = Color.WHITE;
//    private static final Color LOADING_COLOR = Color.LIGHT_GRAY;    //old color scheme before custom splash image was used
    private static final Color LOADING_COLOR = BORDER_COLOR;
    //private static String strTipOfTheDay;
    private MultiLineLabel lblTipOfTheDay;
    private JLabel lblLoading;
//    private JPanel jpContent;
//    private JPanel jpLoadingBar;
    private JPanel jpSplash;        //the panel the other two panels are registered to so the border can be shown properly
    private JPanel jpAppInfo;       //the panel that the splash image, application name, and copyright info are registered to
    private JPanel jpLoadingInfo;   //the panel that the loading bar and loading label are registered to
    private int intSplashWindowWidth;
    private int intSplashWindowHeight;
    private Dimension dimScreen;
    private int intSplashPosX;
    private int intSplashPosY;
    private JLabel lblApplicationName;
    private JLabel lblCopyright;
    private JLabel lblSplashImage;
    private ImageIcon imicSplash;
    private Image imgSplash;
    private Random rng;

    
    private Thread t = null;
    private int intDisplayDuration;
    //Container myCP;
    
    
    
    /**
     * Creates an instance of SplashScreen with the default display intDisplayDuration time of DEFAULT_DISPLAY_TIME ticks.
     */
    public SplashScreen()
    {
        intDisplayDuration = DEFAULT_DISPLAY_TIME;
//        //Code for Icon present in the splash screen
//        JLabel splshicon = new JLabel(imageIcon); //fetches icon from previous code where it is transformed back
//        splshicon.setHorizontalAlignment(JLabel.CENTER);
//        jpContent.add(splshicon, BorderLayout.SOUTH);
//
//        // Code for Course EZ header in the splash screen
//        JLabel header = new JLabel("CourseEZ", JLabel.CENTER);
//        header.setForeground(Color.WHITE); //color of text 
//        header.setFont(new Font("Sans-Serif",Font.BOLD, 24));
//        jpContent.add(header);
//
//
//        //Code for Copyright text in the splash screen
//        JLabel text = new JLabel("Copyright 2017, CourseEZ", JLabel.CENTER);
//        text.setForeground(Color.white);  
//        text.setFont(new Font("Sans-Serif", Font.PLAIN, 10) );
//        jpContent.add(text, BorderLayout.SOUTH);
//
//
//        //Code for Tip of the Day functionality
//        try
//        {
//            BufferedReader reader = new BufferedReader(new FileReader(AppConstants.ROOT_FOLDER + "strTipOfTheDay.txt") ); //fetches text from "strTipOfTheDay.txt"
//
//            String line = reader.readLine();
//            List<String> lines = new ArrayList<>();
//            while (line != null)
//            {
//                lines.add(line);
//                line = reader.readLine();
//            }
//            Random r = new Random();
//            strTipOfTheDay = lines.get(r.nextInt(lines.size()));
//
//            JLabel lblTipOfTheDay = new JLabel(strTipOfTheDay);
//            // System.out.println(strTipOfTheDay); //to test the output for strTipOfTheDay
//            lblTipOfTheDay.setForeground(Color.white);  
//            lblTipOfTheDay.setHorizontalAlignment(JLabel.CENTER);
//            jpContent.add(lblTipOfTheDay, BorderLayout.CENTER);
//        }
//        catch (FileNotFoundException fnfException)
//        {
//            //TODO: Add exception-handling code
//        }
//        catch (IOException ioException)
//        {
//            //TODO: Add exception-handling code
//        }
//
//        //code to display Progress Bar
//        showProgressBar(); 
//        jpContent.add(jpbLoadingBar, BorderLayout.CENTER); //progress bar layout
//
//        jpContent.setBorder(BorderFactory.createLineBorder(Color.ORANGE, 10)); //Sets border with border color and width
//        setVisible(true); //true to display window
//
//        try
//        {
//            Thread.sleep(intDisplayDuration);
//        }
//        catch (Exception e)
//        {
//            //TODO: Add exception-handling
//        }
//        setVisible(false);
    }   //SplashScreen()
  
    
    
    
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

        //TODO: Make progress bar's progress meaningful.
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
                    //i++;
                    i += rng.nextInt(6);   //so it loads a little less evenly for debugging purposes (looks more realistic, at least)
                }
            }
        };
        T1.start();
    }
    
//    //code for progress bar
//    public void showProgressBar()
//    {
//        intDisplayDuration = DEFAULT_DISPLAY_TIME;
//    }

  
//  public void showSplashAndExit() throws IOException {
//    showSplash();
//    System.exit(0);
//    
//  } //showSplashAndExit()
 
//  public static void main(String[] args) throws IOException {
//      
//    SplashScreen splash = new SplashScreen(10000); //change time to display splash screen here
//    splash.showSplashAndExit();
    
    
    
    /**
     * Displays the splash screen and then hides the splash screen when finished.
     */
    public void showSplash()
    {
        
        //initialization and customization
        //jpContent = (JPanel)getContentPane();
//        jpContent = new JPanel(new BorderLayout());
//        jpContent.setBackground(Color.BLACK);
//        jpLoadingBar = new JPanel();
//        //jpLoadingBar.setLayout(new FlowLayout());
//        jpLoadingBar.setBackground(Color.BLACK);
        jpSplash = new JPanel(new BorderLayout() );
        jpSplash.setBackground(BACKGROUND_COLOR);
        jpAppInfo = new JPanel(new BorderLayout() );
        jpAppInfo.setBackground(BACKGROUND_COLOR);
        jpLoadingInfo = new JPanel(new BorderLayout() );
        jpLoadingInfo.setBackground(BACKGROUND_COLOR);
        dimScreen = Toolkit.getDefaultToolkit().getScreenSize();
        intSplashWindowWidth = 260;
        intSplashWindowHeight = 384;
//        intSplashWindowWidth = dimScreen.width / 4;         //one-fourth screen resolution
//        intSplashWindowHeight = dimScreen.height / 2;       //one-half screen resolution
        intSplashPosX = (dimScreen.width - intSplashWindowWidth) / 2;
        intSplashPosY = (dimScreen.height - intSplashWindowHeight) / 2;
        //code to resize image from: http://nullpointer.at/2011/08/21/java-code-snippets-howto-resize-an-imageicon
        imicSplash = new ImageIcon(AppConstants.ROOT_FOLDER + AppConstants.IMAGE_DIR + AppConstants.SPLASH_IMAGE_NAME);   //load the image to a imageIcon
        imgSplash = imicSplash.getImage().getScaledInstance(SPLASH_IMAGE_WIDTH, SPLASH_IMAGE_HEIGHT, Image.SCALE_SMOOTH);              // transform it 
        //imgSplash = imicSplash.getImage().getScaledInstance(intSplashWindowWidth - (BORDER_WIDTH * 2), intSplashWindowHeight / 2, Image.SCALE_SMOOTH);   //scales the image to splash width - 20px border and half splash height
        //TODO: Probably better to scale the splash and image not based on monitor, since it can vary so much (e.g., 4:3, 16:9, etc.)
        lblApplicationName = new JLabel(AppConstants.APP_ID);
        lblApplicationName.setForeground(TEXT_COLOR);
        lblApplicationName.setFont(new Font("Sans-Serif", Font.BOLD, 24) );
        lblApplicationName.setHorizontalAlignment(JLabel.CENTER);
        lblSplashImage = new JLabel(new ImageIcon(imgSplash) );
        lblCopyright = new JLabel("Copyright 2017, Penn State Berks IST");
        lblCopyright.setHorizontalAlignment(JLabel.CENTER);
        lblCopyright.setFont(new Font("Sans-Serif", Font.PLAIN, 12) );
        lblCopyright.setForeground(TEXT_COLOR);
        lblLoading = new JLabel("Now loading...");  //TODO: Add code elsewhere to make this change dynamically
        lblLoading.setForeground(TEXT_COLOR);
        lblLoading.setFont(new Font("Sans-Serif", Font.ITALIC, 12) );
        lblLoading.setHorizontalAlignment(JLabel.CENTER);
        lblTipOfTheDay = new MultiLineLabel("Tip of the day!");
        //lblTipOfTheDay = new JLabel("Tip of the day!"); //TODO: Improve tip of the day code
        lblTipOfTheDay.setFont(new Font("Sans-Serif", Font.ITALIC, 12) );
        lblTipOfTheDay.setMaximumSize(new Dimension(intSplashWindowWidth - (BORDER_WIDTH * 2), 15) );   //TODO: Implement better handling of strings that are longer than the window can display on one line
        lblTipOfTheDay.setForeground(TEXT_COLOR);
        lblTipOfTheDay.setHorizontalAlignment(JLabel.CENTER);
        jpbLoadingBar = new JProgressBar();
        jpbLoadingBar.setStringPainted(true);
        jpbLoadingBar.setForeground(LOADING_COLOR);
        jpbLoadingBar.setMinimum(0);
        jpbLoadingBar.setMaximum(100);
        jpbLoadingBar.setPreferredSize(new Dimension(intSplashWindowWidth - (BORDER_WIDTH * 2), 15));   //intSplashWindowWidth  - 20 pixels wide to account for two 10px borders, 15px-high to match textboxes (15px high by default, I believe)
        setBounds(intSplashPosX, intSplashPosY, intSplashWindowWidth, intSplashWindowHeight);
        rng = new Random();
        

        /* Code for Tip of the Day functionality
        try
        {
            BufferedReader reader = new BufferedReader(new FileReader(AppConstants.ROOT_FOLDER + "tipoftheday.txt") ); //fetches text from "tipoftheday.txt"

            String line = reader.readLine();
            List<String> lines = new ArrayList<>();
            while (line != null)
            {
                lines.add(line);
                line = reader.readLine();
            }
            lblTipOfTheDay.setText(lines.get(rng.nextInt(lines.size() ) ) );
//            strTipOfTheDay = lines.get(rng.nextInt(lines.size()));
//            lblTipOfTheDay = new JLabel(strTipOfTheDay);
            // System.out.println(strTipOfTheDay); //to test the output for strTipOfTheDay
//            lblTipOfTheDay.setForeground(Color.white);  
//            lblTipOfTheDay.setHorizontalAlignment(JLabel.CENTER);
            //jpContent.add(lblTipOfTheDay, BorderLayout.CENTER);
        }
        catch (FileNotFoundException fnfException)
        {
            //TODO: Add exception-handling code
        }
        catch (IOException ioException)
        {
            //TODO: Add exception-handling code
        }
        
        */
        

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
//        jpLoadingBar.add(jpbLoadingBar);
//        jpLoadingBar.add(lblTipOfTheDay);
//        jpContent.add(lblApplicationName, BorderLayout.EAST);
//        jpContent.add(jpLoadingBar, BorderLayout.CENTER);
//        jpContent.add(lblSplashImage, BorderLayout.NORTH);
//        jpContent.add(lblCopyright, BorderLayout.SOUTH);
//        jpContent.setBorder(BorderFactory.createLineBorder(Color.ORANGE, 10) );  //draws border
//        add(jpContent);
        
        
        
        Connection cnMyC = connectToDB();
        if (cnMyC != null)
        {
        System.out.println("Opened database successfully\n");
        printDBInfo(cnMyC);
        lblTipOfTheDay.setText(doTipOfTheDay(cnMyC) );
        //lblTipOfTheDay.setUI(MultiLineLabelUI.labelUI);
        lblTipOfTheDay.setHorizontalTextAlignment(JLabel.CENTER);
        //lblTipOfTheDay.setVerticalTextAlignment(JLabel.CENTER);
    
        try
        {
            cnMyC.close();
        }
        catch (SQLException ex)
        {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        


        //Layout 1: splash at top, then application name, then copyright, then space for tip of the day, then loading bar, then currently loading file
        jpAppInfo.add(lblSplashImage, BorderLayout.NORTH);
        jpAppInfo.add(lblApplicationName, BorderLayout.CENTER);
        jpAppInfo.add(lblCopyright, BorderLayout.SOUTH);
        //jpLoadingInfo.add(lblTipOfTheDay, BorderLayout.NORTH);
        jpLoadingInfo.add(jpbLoadingBar, BorderLayout.CENTER);
        jpLoadingInfo.add(lblLoading, BorderLayout.SOUTH);
        jpSplash.add(jpAppInfo, BorderLayout.NORTH);
        jpSplash.add(lblTipOfTheDay, BorderLayout.CENTER);
        jpSplash.add(jpLoadingInfo, BorderLayout.SOUTH);
        jpSplash.setBorder(BorderFactory.createLineBorder(BORDER_COLOR, BORDER_WIDTH) );
        add(jpSplash);
        
        //Layout 2: splash at top, then application name, then tip of the day, then currently loading file, then loading bar, then copyright
//        jpAppInfo.add(lblSplashImage, BorderLayout.NORTH);
//        jpAppInfo.add(lblApplicationName, BorderLayout.CENTER);
//        jpAppInfo.add(lblTipOfTheDay, BorderLayout.SOUTH);
//        //jpLoadingInfo.add(lblTipOfTheDay, BorderLayout.NORTH);
//        jpLoadingInfo.add(lblLoading, BorderLayout.CENTER);
//        jpLoadingInfo.add(jpbLoadingBar, BorderLayout.SOUTH);
//        jpSplash.add(jpAppInfo, BorderLayout.NORTH);
//        jpSplash.add(jpLoadingInfo, BorderLayout.CENTER);
//        jpSplash.add(lblCopyright, BorderLayout.SOUTH);
//        jpSplash.setBorder(BorderFactory.createLineBorder(Color.ORANGE, BORDER_WIDTH) );
//        add(jpSplash);

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
    }   //showSplash()
    
    
    public Connection connectToDB()
    {
    
        Connection c = null;
    try {
      Class.forName(strDBClass);
      c = DriverManager.getConnection(strJDBCString + strDBName);
      return c;
    } catch ( Exception e ) {
      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
      return null;
      // System.exit(0);
    }
    } // connectToDB

    
    public void printRSMetaData(ResultSet rsIn) throws SQLException
    {
        ResultSetMetaData rsMD = rsIn.getMetaData();
        
        System.out.println("Columns in the ResultSet = " + rsMD.getColumnCount());
        
        
        if (bDebugging == true)
        {
            System.out.println("Made it to printsRSMetaData");
        } // if bDebugging
        
        int intNumCols = rsMD.getColumnCount();
        
        for (int nLCV = 1; nLCV <= intNumCols; nLCV++)
        {
            // TODO: Add code to display column information
           System.out.println("Column " + nLCV);
           System.out.println("   Class:      " + rsMD.getColumnClassName(nLCV));
           System.out.println("   Label:      " + rsMD.getColumnLabel(nLCV));
           System.out.println("   Name:       " + rsMD.getColumnName(nLCV));
           System.out.println("   Type:       " + rsMD.getColumnType(nLCV));
           System.out.println("   Type Name:  " + rsMD.getColumnTypeName(nLCV));
           System.out.println("\n\n");
        } // for
        
        int intRowCounter = 0;
        while(rsIn.next() == true)
        {
           intRowCounter++;
             System.out.println("Row " + intRowCounter);
             for (int nLCV = 1; nLCV <= intNumCols; nLCV++)
        {
         
            switch(rsMD.getColumnType(nLCV))            
            {
                case 4: 
                    System.out.println(rsMD.getColumnLabel(nLCV) +
                            " = " + rsIn.getInt(nLCV));
                    break;
                
                 case 12: 
                    System.out.println(rsMD.getColumnLabel(nLCV) +
                            " = " + rsIn.getString(nLCV));
                    break;
                     
                 default:
                     System.out.println("Column " + nLCV + "type number is " + rsMD.getColumnType(nLCV));
                    
                    
                
            } // switch
            
        } // for
        
             System.out.println("\n\n");
        } // while
        
    } // printRSMetaData
    
    
    public void printDBSchemas(DatabaseMetaData mdIn) throws SQLException
    {
          ResultSet rsSchemas = mdIn.getSchemas();
          printRSMetaData(rsSchemas);
          
       
          while(rsSchemas.next())
          {
              System.out.print(rsSchemas.getString(1)+ " \t" + rsSchemas.getString(2)+ "\n");
              //ToDo: Add code to loop through columns, get and display data
          } // while not past last row
    } // print DBSchemas
    
    
    
    public void printDBInfo(Connection cnInput)
    {
        try {
            DatabaseMetaData myMD = cnInput.getMetaData();
            System.out.println("DB Product Name = " + myMD.getDatabaseProductName());
            System.out.println("DB Product Version = " + myMD.getDatabaseProductVersion());
           
            // printDBSchemas(myMD);
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }    
    
    public void printResultSet(ResultSet rsIn)
    {
        try {
            ResultSetMetaData rsMD = rsIn.getMetaData();
            int intColCount = rsMD.getColumnCount();
            
          //  System.out.println("Statement Executed:  " + rsIn.getStatement() + "\n");
            
            while (rsIn.next() == true)
            {
                for (int nLCV = 1; nLCV <= intColCount; nLCV++)
                {
                     System.out.println(rsMD.getColumnLabel(nLCV) +
                            " = " + rsIn.getString(nLCV));
                } // for
                System.out.println("\n\n");
            } // while not at end
        
           
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }   
        
    public String doTipOfTheDay(Connection cnIn)
    {
        String strQuote = "";
        try
        {
            //Prints one of the randomquotes with the name of the person who said the quote
            PreparedStatement psRandomQ = cnIn.prepareStatement("select '''' || QUOTE || '''' || ' - ' || NAME FROM vFiveRandomQuotes;");
            ResultSet rsRandomQ = psRandomQ.executeQuery();
            
           //printRSMetaData(rsRandomQ);
            //printResultSet(rsRandomQ); //to test if quote is being fetched correctly
            System.out.println("[DEBUG] TotD=" + rsRandomQ.getString(1) );
            strQuote = rsRandomQ.getString(1);
        }
        catch (SQLException ex)
        {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return strQuote;
            
    } // TipoftheDay 
    
//    public static void main(String[] args) {
//        
//    SplashScreen myDBC = new SplashScreen();
//    Connection cnMyC = myDBC.connectToDB();
//    if (cnMyC != null)
//    {
//    System.out.println("Opened database successfully\n");
//    myDBC.printDBInfo(cnMyC);
//            System.out.println("To test whether quote is being displayed:"); 
//            myDBC.doTipOfTheDay(cnMyC);
//    
//        try {
//            cnMyC.close();
//        } catch (SQLException ex) {
//            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    
//  
//    }
    
//    } //main
    
} // class SplashScreen
