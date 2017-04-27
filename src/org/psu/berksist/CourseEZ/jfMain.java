package org.psu.berksist.CourseEZ;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.LookAndFeel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.text.DefaultEditorKit;
import org.docx4j.openpackaging.exceptions.Docx4JException;


/**
 *
 * @author whb108
 * 
 * 
 * 
 ******************* MODIFICATION LOG *****************************************
 * 2017 April 24    -   jMenuItem1 is now jmiTestGenerateReport as "TEST: Generate Report" under Report.
 *                      Updated jmiTestGenerateReport and jmiGenerateReport's parameters to work
 *                          with the modified functions in Reports.java. -JSS5783
 * 
 * 2017 April 19    -   Added JMenuItem for jfReport under File.
 *                      jfAbout's title is now implemented in the form's design itself. - JSS5783
 * 
 * 2017 March 29    -   Added JMenuItem for jfAbout under Help. - JSS5783
 * 
 * 2017 January 31  -   General housekeeping (formatted code for readability and 
 *                      consistency). - RQZ
 * 
 * 2016 April 07    -   Added library and loaded plaf for Pago Soft plaf. - WHB
 * 
 * 2016 March 26    -   Added exit menu item, finalize().
 *                      Created Help menu, moved database info to help menu. - WHB
 * 
 * 2016 March 25    -   Added user preferences. - WHB
 * 
 * 2016 February 06 -   Added code to dynamically create look and feel menu
 *                          to implement dynamic setting of PLAF. - WHB
 * 
 * 
 * 
 * 
 * 
 * 
 * ****************** ADDITIONAL LIBRARIES NEEDED ******************************
 * 
 *      NAME                        SOURCE                      USE
 * 
 *      rs2xml.jar                  https://drive.google.com/file/d/0BzIr4IDDKJEcdDE1YTlzbmtkMzg/view   Create table model from ResultSet
 *      commons-cli-1.3.1.jar       https://commons.apache.org/proper/commons-cli/download_cli.cgi      Command line argument parser
 *      sqlite-jdbc-3.8.11.2.jar    https://bitbucket.org/xerial/sqlite-jdbc/downloads
 *      pgslookandfeel-1.1.2.jar    http://www.pagosoft.com/projects/pgslookandfeel/
 *      JavaGPE_3DLF-2.5.jar        http://www.markus-hillenbrand.de/3dlf/
 */

public class jfMain extends JFrame {

    
    ButtonGroup bgLAF = new ButtonGroup();

    //private boolean bDebugging = true;

    private Properties myProps;
    
    public jpMain jpMainPanel;
    public AppControl appControl;
    
    GetPropertiesAction gpa;
    SavePropertiesAction spa;
    
    

    /**
     * Creates new form jfMain
     */
    public jfMain(Properties propsIn, GetPropertiesAction gpaIn, AppControl appControlIn) 
    {
        
        this(propsIn, gpaIn, appControlIn, new jpMain());
        
        jmiOpenDB.doClick();
        
    } // jfMain
    
    public jfMain(Properties propsIn, GetPropertiesAction gpaIn, AppControl appControlIn, jpMain jpIn)
    {
        myProps = propsIn;
        appControl = appControlIn;
        
        gpa = gpaIn;
        gpa.setFrame(this);
        
        spa = new SavePropertiesAction(this, myProps);
        
        jpMainPanel = jpIn;
        
        initComponents();

        setContentPane(jpMainPanel);
        
        try {
            ImageIcon img = new ImageIcon( new URL(AppConstants.APP_ICON));
            setIconImage(img.getImage());
        } catch (MalformedURLException ex) {
            Logger.getLogger(jfMain.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        // Run app centered on screen
        setLocationRelativeTo(null);
        
        MakeLookAndFeelMenu();
        
        
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e){
                
                closeAll();
            }
        });
        
        
        this.setVisible(true);
    } // jfMain
    
    public void closeAll() {
        
        spa.savePrefs();
                
        try {
            if (jpMainPanel.dbConnection != null) {
                jpMainPanel.dbConnection.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(jfMain.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    public void MakeLookAndFeelMenu() {

        LookAndFeelInfo[] lfAll = UIManager.getInstalledLookAndFeels();

        for (LookAndFeelInfo lfAll2 : lfAll) {
            
            //System.out.println("Look and Feel - " + lfAll2.getName()
            //        + " Class - " + lfAll2.getClassName());
            
            JRadioButtonMenuItem jmiTemp = new JRadioButtonMenuItem();
            jmiTemp.setText(lfAll2.getName());
            String strLAF = UIManager.getLookAndFeel().getClass().getName();
            
            
            if (strLAF.equalsIgnoreCase(lfAll2.getClassName())) {
                jmiTemp.setSelected(true);
            } // is this the current L&F?
            
            
            jmiTemp.addActionListener((java.awt.event.ActionEvent evt) -> {
                try {
                    
                    UIManager.setLookAndFeel(lfAll2.getClassName());
                    
                    LookAndFeel laf = UIManager.getLookAndFeel();
                    
                    
                    
                    myProps.setProperty(AppConstants.LAF, lfAll2.getClassName());
                    SwingUtilities.updateComponentTreeUI(this);
                    
                    // Recreate frame
                    appControl.restartApp(jpMainPanel);
                    
                    JRadioButtonMenuItem jrbTemp = (JRadioButtonMenuItem) evt.getSource();
                    jrbTemp.setSelected(true);
                    
                    //jmiSaveUserOptions.doClick();
                    spa.savePrefs();
                    
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                    Logger.getLogger(jfMain.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            
            bgLAF.add(jmiTemp);
            jmLookAndFeel.add(jmiTemp);
        } // for
    } // MakeLookAndFeelMenu
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jmbMain = new javax.swing.JMenuBar();
        jmFile = new javax.swing.JMenu();
        jmiAddFrame = new javax.swing.JMenuItem();
        jmiOpenDB = new javax.swing.JMenuItem(new OpenDatabaseAction(this, myProps));
        jmiTestTablePanel = new javax.swing.JMenuItem();
        jmiGenerateReport = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jmiExit = new javax.swing.JMenuItem(new ExitAction(this));
        jmEdit = new javax.swing.JMenu();
        jmiCut = new javax.swing.JMenuItem(new DefaultEditorKit.CutAction());
        jmiCopy = new javax.swing.JMenuItem(new DefaultEditorKit.CopyAction());
        jmiPaste = new javax.swing.JMenuItem(new DefaultEditorKit.PasteAction());
        jmWindows = new javax.swing.JMenu();
        jmiTile = new javax.swing.JMenuItem(new TileAction(jpMainPanel.jdpMain));
        jmiCascade = new javax.swing.JMenuItem(new CascadeAction(jpMainPanel.jdpMain));
        jmiMinimize = new javax.swing.JMenuItem(new MinimizeAllWindowsAction(jpMainPanel.jdpMain));
        jmLookAndFeel = new javax.swing.JMenu();
        jmOptions = new javax.swing.JMenu();
        jmiLoadUserOptions = new javax.swing.JMenuItem(gpa);
        jmiSaveUserOptions = new javax.swing.JMenuItem(spa);
        jmHelp = new javax.swing.JMenu();
        jmHelpContents = new javax.swing.JMenuItem();
        jmiDatabaseInformation = new javax.swing.JMenuItem();
        jmiAbout = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        jmiTestGenerateReport = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Course EZ");

        jmbMain.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jmbMain.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);

        jmFile.setMnemonic('F');
        jmFile.setText("File");

        jmiAddFrame.setText("Add Frame");
        jmiAddFrame.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiAddFrameActionPerformed(evt);
            }
        });
        jmFile.add(jmiAddFrame);

        jmiOpenDB.setText("Open Database");
        jmiOpenDB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiOpenDBActionPerformed(evt);
            }
        });
        jmFile.add(jmiOpenDB);

        jmiTestTablePanel.setText("JTable Test");
        jmiTestTablePanel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiTestTablePanelActionPerformed(evt);
            }
        });
        jmFile.add(jmiTestTablePanel);

        jmiGenerateReport.setText("Generate Report");
        jmiGenerateReport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiGenerateReportActionPerformed(evt);
            }
        });
        jmFile.add(jmiGenerateReport);
        jmFile.add(jSeparator1);

        jmiExit.setText("Exit");
        jmiExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiExitActionPerformed(evt);
            }
        });
        jmFile.add(jmiExit);

        jmbMain.add(jmFile);

        jmEdit.setText("Edit");

        jmiCut.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/CutIcon16.png"))); // NOI18N
        jmiCut.setText("Cut");
        jmEdit.add(jmiCut);
        jmiCut.setMnemonic(KeyEvent.VK_X);

        jmiCopy.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/CopyIcon16.png"))); // NOI18N
        jmiCopy.setText("Copy");
        jmEdit.add(jmiCopy);
        jmiCopy.setMnemonic(KeyEvent.VK_C);

        jmiPaste.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/PasteIcon16.png"))); // NOI18N
        jmiPaste.setText("Paste");
        jmEdit.add(jmiPaste);
        jmiPaste.setMnemonic(KeyEvent.VK_V);

        jmbMain.add(jmEdit);

        jmWindows.setMnemonic('W');
        jmWindows.setText("Window");

        jmiTile.setText("Tile");
        jmWindows.add(jmiTile);

        jmiCascade.setText("Cascade");
        jmWindows.add(jmiCascade);

        jmiMinimize.setText("Minimize All");
        jmWindows.add(jmiMinimize);

        jmbMain.add(jmWindows);

        jmLookAndFeel.setText("Look And Feel");
        jmbMain.add(jmLookAndFeel);

        jmOptions.setText("Options");
        jmOptions.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmOptionsActionPerformed(evt);
            }
        });

        jmiLoadUserOptions.setText("Import Settings");
        jmiLoadUserOptions.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiLoadUserOptionsActionPerformed(evt);
            }
        });
        jmOptions.add(jmiLoadUserOptions);

        jmiSaveUserOptions.setText("Export Settings");
        jmiSaveUserOptions.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiSaveUserOptionsActionPerformed(evt);
            }
        });
        jmOptions.add(jmiSaveUserOptions);

        jmbMain.add(jmOptions);

        jmHelp.setText("Help");

        jmHelpContents.setText("Help Contents");
        jmHelpContents.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmHelpContentsActionPerformed(evt);
            }
        });
        jmHelp.add(jmHelpContents);

        jmiDatabaseInformation.setText("DB Info");
        jmiDatabaseInformation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiDatabaseInformationActionPerformed(evt);
            }
        });
        jmHelp.add(jmiDatabaseInformation);

        jmiAbout.setText("About");
        jmiAbout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiAboutActionPerformed(evt);
            }
        });
        jmHelp.add(jmiAbout);

        jmbMain.add(jmHelp);

        jMenu1.setText("Report");

        jmiTestGenerateReport.setText("TEST: Generate Report");
        jmiTestGenerateReport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiTestGenerateReportActionPerformed(evt);
            }
        });
        jMenu1.add(jmiTestGenerateReport);

        jmbMain.add(jMenu1);

        setJMenuBar(jmbMain);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 967, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 513, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jmiAddFrameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiAddFrameActionPerformed
        
        jpMainPanel.CreateFrame();
    }//GEN-LAST:event_jmiAddFrameActionPerformed

    private void jmiTestTablePanelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiTestTablePanelActionPerformed
        try {
            System.out.println("Made it to test2");

            while (jpMainPanel.dbConnection == null) {
                JOptionPane.showMessageDialog(this,
                        "You must connect to a database!",
                        "No Database Connection", JOptionPane.ERROR_MESSAGE);
                jmiOpenDB.doClick();
            } // check for database connection

            Statement stTest = jpMainPanel.dbConnection.createStatement();
            String strQuery = "Select * from AttendanceCode";

            strQuery = (String) JOptionPane.showInputDialog(this, "Enter a query", strQuery);
            ResultSet rsAttendance = stTest.executeQuery(strQuery);

            int[] arrColsToHide = {};
            jpTableDisplay jpDisplay = new jpTableDisplay(rsAttendance, 0, arrColsToHide);

            jpDisplay.setPreferredSize(new Dimension(900, 900));
            jpMainPanel.CreateFrame(jpDisplay, strQuery);
        } catch (SQLException ex) {
            Logger.getLogger(jfMain.class.getName()).log(Level.SEVERE, null, ex);
        }


    }//GEN-LAST:event_jmiTestTablePanelActionPerformed

    private void jmiSaveUserOptionsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiSaveUserOptionsActionPerformed

    }//GEN-LAST:event_jmiSaveUserOptionsActionPerformed

    private void jmiOpenDBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiOpenDBActionPerformed
        
    }//GEN-LAST:event_jmiOpenDBActionPerformed

    private void jmiLoadUserOptionsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiLoadUserOptionsActionPerformed
        
    }//GEN-LAST:event_jmiLoadUserOptionsActionPerformed

    private void jmiDatabaseInformationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiDatabaseInformationActionPerformed
        
        JOptionPane.showMessageDialog(this, new jpSQLiteDBInfo(jpMainPanel.dbConnection, myProps),
                "Connection Information for " + myProps.getProperty(AppConstants.LAST_DB),
                JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_jmiDatabaseInformationActionPerformed

    private void jmiExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiExitActionPerformed
        
    }//GEN-LAST:event_jmiExitActionPerformed

    private void jmOptionsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmOptionsActionPerformed
        
    }//GEN-LAST:event_jmOptionsActionPerformed

    /**
     * Opens the About window.
     * @param evt 
     */
    private void jmiAboutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiAboutActionPerformed
        JFrame jfAbout = new jfAbout();
        jfAbout.setVisible(true);
    }//GEN-LAST:event_jmiAboutActionPerformed

    private void jmHelpContentsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmHelpContentsActionPerformed
      
        /** 
         * Action performed that opens HelpDoc
         * Old methods are commented out 
         * mrs6041 Michael Strizziere
         */ 
        
        // Opening HelpDoc via ICEBrowser
        /* try {
            Class htmlBrowserClass;
            htmlBrowserClass = Class.forName("oracle.help.htmlBrowser.ICEBrowser");
            Help myH = new Help(htmlBrowserClass);
            HelpSet myHS = new HelpSet(ClassInfo.class, HelpDoc + "/CourseManagement.html");
            myH.addBook(myHS);
            myH.showNavigatorWindow();
        } catch (HelpSetParseException | ClassNotFoundException ex) {
            Logger.getLogger(helpTester.class.getName()).log(Level.SEVERE, null, ex);
        } */
        
        // Opening HelpDoc via OSDetector class
        //File HelpDoc = new File(AppConstants.ROOT_FOLDER + "HelpDoc" + "/CourseManagement.html");
        //OSDetector.open(HelpDoc);
        
        // This method of opening HelpDoc is now done inside of the OSDetector class
        /* File HelpDoc = new File(AppConstants.ROOT_FOLDER + "HelpDoc");
        try {
            if (OSDetector.isWindows) {
                Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " 
                    + HelpDoc + "/CourseManagement.html");
            }
            else if (OSDetector.isMac || OSDetector.isLinux) {
                Runtime.getRuntime().exec("/usr/bin/open" + HelpDoc
                        + "/CourseManagement.html");
            }
            else {
                Runtime.getRuntime().exec(HelpDoc + "/CourseManagement.hmtl");
            }
        } catch(Exception ex) {
            Logger.getLogger(jfMain.class.getName()).log(Level.SEVERE, null, ex);
        } */
    }//GEN-LAST:event_jmHelpContentsActionPerformed

    /**
     * Opens jfReport in a new window.
     * @param evt 
     */
    private void jmiGenerateReportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiGenerateReportActionPerformed
        // TODO add your handling code here:
        JFrame jfReport = new jfReport(jpMainPanel.dbConnection);
        jfReport.setVisible(true);
    }//GEN-LAST:event_jmiGenerateReportActionPerformed

    private void jmiTestGenerateReportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiTestGenerateReportActionPerformed
        Reports report1 = new Reports();
        {
            try
            {
                report1.Syllabus(jpMainPanel.dbConnection, AppConstants.ROOT_FOLDER, "NewReport", 1);
            }
            catch (Docx4JException ex)
            {
                Logger.getLogger(jfReport.class.getName()).log(Level.SEVERE, null, ex);
            }
            catch (FileNotFoundException ex)
            {
                Logger.getLogger(jfReport.class.getName()).log(Level.SEVERE, null, ex);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(jfReport.class.getName() ).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jmiTestGenerateReportActionPerformed



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu jMenu1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JMenu jmEdit;
    private javax.swing.JMenu jmFile;
    private javax.swing.JMenu jmHelp;
    private javax.swing.JMenuItem jmHelpContents;
    private javax.swing.JMenu jmLookAndFeel;
    private javax.swing.JMenu jmOptions;
    private javax.swing.JMenu jmWindows;
    private javax.swing.JMenuBar jmbMain;
    private javax.swing.JMenuItem jmiAbout;
    private javax.swing.JMenuItem jmiAddFrame;
    private javax.swing.JMenuItem jmiCascade;
    private javax.swing.JMenuItem jmiCopy;
    private javax.swing.JMenuItem jmiCut;
    private javax.swing.JMenuItem jmiDatabaseInformation;
    private javax.swing.JMenuItem jmiExit;
    private javax.swing.JMenuItem jmiGenerateReport;
    private javax.swing.JMenuItem jmiLoadUserOptions;
    private javax.swing.JMenuItem jmiMinimize;
    private javax.swing.JMenuItem jmiOpenDB;
    private javax.swing.JMenuItem jmiPaste;
    private javax.swing.JMenuItem jmiSaveUserOptions;
    private javax.swing.JMenuItem jmiTestGenerateReport;
    private javax.swing.JMenuItem jmiTestTablePanel;
    private javax.swing.JMenuItem jmiTile;
    // End of variables declaration//GEN-END:variables
}
