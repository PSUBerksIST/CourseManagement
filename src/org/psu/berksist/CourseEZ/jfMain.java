/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.psu.berksist.CourseEZ;

import java.awt.Dimension;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.LookAndFeel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;


/**
 *
 * @author whb108
 * 
 * 
 * 
 ******************* MODIFICATION LOG *****************************************
 * 
 * 2017 January 31 - General housekeeping (formatted code for readability and 
 *                   consistency). - RQZ
 * 
 * 2016 April 07   - Added library and loaded plaf for Pago Soft plaf. - WHB
 * 
 * 2016 March 26   - Added exit menu item, finalize().  Created Help menu,
 *                   moved database info to help menu.- WHB 
 * 2016 March 25   - Added user preferences. - WHB
 * 
 * 2016 February 06 - Added code to dynamically create look and feel menu to
 *                    implement dynamic setting of PLAF. - WHB
 * 
 * 
 * 
 * 
 * 
 * 
 * ****************** ADDITIONAL LIBRARIES NEEDED ******************************
 * 
 *      NAME            SOURCE                      USE
 * 
 *      rs2xml.jar      https://drive.google.com/file/d/0BzIr4IDDKJEcdDE1YTlzbmtkMzg/view   create table model from ResultSet
 *      commons-cli-1.3.1.jar   https://commons.apache.org/proper/commons-cli/download_cli.cgi  Command line argument parser
 *      sqlite-jdbc-3.8.11.2.jar    https://bitbucket.org/xerial/sqlite-jdbc/downloads
 *      pgslookandfeel-1.1.2.jar http://www.pagosoft.com/projects/pgslookandfeel/
 *      JavaGPE_3DLF-2.5.jar  http://www.markus-hillenbrand.de/3dlf/
 */

public class jfMain extends JFrame {

    
    ButtonGroup bgLAF = new ButtonGroup();

    //private boolean bDebugging = true;

    private Properties myProps;
    
    public jpMain jpMainPanel;
    private AppControl appControl;
    
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
        
        // Run app centered on screen
        setLocationRelativeTo(null);
        
        MakeLookAndFeelMenu();
        
        
        this.setVisible(true);
    } // jfMain
    


    public void MakeLookAndFeelMenu() {

        LookAndFeelInfo[] lfAll = UIManager.getInstalledLookAndFeels();

        for (LookAndFeelInfo lfAll2 : lfAll) {
            
            System.out.println("Look and Feel - " + lfAll2.getName()
                    + " Class - " + lfAll2.getClassName());
            
            JRadioButtonMenuItem jmiTemp = new JRadioButtonMenuItem();
            jmiTemp.setText(lfAll2.getName());
            String strLAF = UIManager.getLookAndFeel().getClass().getName();
            
            
            if (strLAF.equalsIgnoreCase(lfAll2.getClassName())) {
                jmiTemp.setSelected(true);
            } // is this the current L&F?
            
            // TODO: Look into seperating this into its own class - RQZ
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
                    jmiSaveUserOptions.doClick();
                    
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
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jmiExit = new javax.swing.JMenuItem();
        jmEdit = new javax.swing.JMenu();
        jmWindows = new javax.swing.JMenu();
        jmiTile = new javax.swing.JMenuItem(new TileAction(jpMainPanel.jdpMain));
        jmiCascade = new javax.swing.JMenuItem(new CascadeAction(jpMainPanel.jdpMain));
        jmiMinimize = new javax.swing.JMenuItem(new MinimizeAllWindowsAction(jpMainPanel.jdpMain));
        jmLookAndFeel = new javax.swing.JMenu();
        jmOptions = new javax.swing.JMenu();
        jmiLoadUserOptions = new javax.swing.JMenuItem(gpa);
        jmiSaveUserOptions = new javax.swing.JMenuItem(spa);
        jmHelp = new javax.swing.JMenu();
        jmiDatabaseInformation = new javax.swing.JMenuItem();

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

        jmiLoadUserOptions.setText("Load Options");
        jmiLoadUserOptions.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiLoadUserOptionsActionPerformed(evt);
            }
        });
        jmOptions.add(jmiLoadUserOptions);

        jmiSaveUserOptions.setText("Save Options");
        jmiSaveUserOptions.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiSaveUserOptionsActionPerformed(evt);
            }
        });
        jmOptions.add(jmiSaveUserOptions);

        jmbMain.add(jmOptions);

        jmHelp.setText("Help");

        jmiDatabaseInformation.setText("DB Info");
        jmiDatabaseInformation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiDatabaseInformationActionPerformed(evt);
            }
        });
        jmHelp.add(jmiDatabaseInformation);

        jmbMain.add(jmHelp);

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
        
        System.exit(0);
    }//GEN-LAST:event_jmiExitActionPerformed

    private void jmOptionsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmOptionsActionPerformed
        
    }//GEN-LAST:event_jmOptionsActionPerformed



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JMenu jmEdit;
    private javax.swing.JMenu jmFile;
    private javax.swing.JMenu jmHelp;
    private javax.swing.JMenu jmLookAndFeel;
    private javax.swing.JMenu jmOptions;
    private javax.swing.JMenu jmWindows;
    private javax.swing.JMenuBar jmbMain;
    private javax.swing.JMenuItem jmiAddFrame;
    private javax.swing.JMenuItem jmiCascade;
    private javax.swing.JMenuItem jmiDatabaseInformation;
    private javax.swing.JMenuItem jmiExit;
    private javax.swing.JMenuItem jmiLoadUserOptions;
    private javax.swing.JMenuItem jmiMinimize;
    private javax.swing.JMenuItem jmiOpenDB;
    private javax.swing.JMenuItem jmiSaveUserOptions;
    private javax.swing.JMenuItem jmiTestTablePanel;
    private javax.swing.JMenuItem jmiTile;
    // End of variables declaration//GEN-END:variables
}
