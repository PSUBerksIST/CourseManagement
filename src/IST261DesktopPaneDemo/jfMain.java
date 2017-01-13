/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IST261DesktopPaneDemo;

import java.awt.Dimension;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;

import org.apache.commons.cli.*;


/**
 *
 * @author whb108
 * 
 * 
 * 
 ******************* MODIFICATION LOG *****************************************
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

    int intWindowCounter = 0;
    ButtonGroup bgLAF = new ButtonGroup();
    
    CommandLine myCL;
    String strUserPrefsFile;
    
    private boolean bDebugging = true;
    public Connection dbConnection;
    public DBConnection dbc;
 
    private Properties myProps;
    /**
     * Creates new form jfMain
     */
    public jfMain(String[] strArgs) 
    {
       myProps = new Properties();
       initComponents();
       addAdditionalPLAF();
     
       dbc = new DBConnection(this);
       setLocationByPlatform(true);
       addWindowListener(new java.awt.event.WindowAdapter() 
       {
          public void windowClosing(java.awt.event.WindowEvent e) 
          {
             try 
             {
                  jmiSaveUserOptions.doClick();
                if (dbConnection != null)
                   dbConnection.close();
             } // try to close the database connection
             catch (SQLException ex) 
             {
                Logger.getLogger(jfMain.class.getName()).log(Level.SEVERE, null, ex);
             } // catch
                  System.exit(0);
            }
        });
        try {
            myCL = CommandLineOptions.processCommandLine(strArgs);
            strUserPrefsFile = myCL.getOptionValue("u");
           HelpFormatter formatter = new HelpFormatter();
           formatter.printHelp( "Course Management", CommandLineOptions.makeOptions() );
        } 
        catch (ParseException ex) 
        {
            Logger.getLogger(jfMain.class.getName()).log(Level.SEVERE, null, ex);
        }
        jmiLoadUserOptions.doClick();
        jmiOpenDB.doClick();
          MakeLookAndFeelMenu();
    } // 

public void finalize()  
{
        try 
        {
            jmiSaveUserOptions.doClick();
            dbConnection.close();
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(jfMain.class.getName()).log(Level.SEVERE, null, ex);
        }
}


public void addAdditionalPLAF()
{
     UIManager.installLookAndFeel("Pago Soft", "com.pagosoft.plaf.PgsLookAndFeel");
        UIManager.installLookAndFeel("3D", "de.hillenbrand.swing.plaf.threeD.ThreeDLookAndFeel");
}
    
    public void MakeLookAndFeelMenu()
    {
        
       
         LookAndFeelInfo[] lfAll = UIManager.getInstalledLookAndFeels();
        
         
        for (LookAndFeelInfo lfAll1 : lfAll) 
        {
           System.out.println("Look and Feel - " + lfAll1.getName() 
              + " Class - " + lfAll1.getClassName());
          JRadioButtonMenuItem jmiTemp = new JRadioButtonMenuItem(); 
          jmiTemp.setText(lfAll1.getName());
          String strLAF = UIManager.getLookAndFeel().getClass().getName();
     //       System.out.println("strLAF = " + strLAF); 
     //       System.out.println("LAF Info name = " + lfAll1.getName());
     //       System.out.println("LAF Info class name = " + lfAll1.getClassName());
          if (strLAF.equalsIgnoreCase(lfAll1.getClassName()))
          {
             jmiTemp.setSelected(true);
          } // is this the current L&F?
          
           jmiTemp.addActionListener((java.awt.event.ActionEvent evt) -> {
              try {
                 UIManager.setLookAndFeel(lfAll1.getClassName());
                 myProps.setProperty(ApplicationConstants.LAF, lfAll1.getClassName());
                 SwingUtilities.updateComponentTreeUI(this);
                 jbTile.doClick();
         //        this.pack();
                
                 JRadioButtonMenuItem jrbTemp = (JRadioButtonMenuItem)evt.getSource();
                 jrbTemp.setSelected(true);
                  jmiSaveUserOptions.doClick();
              } 
              
              catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
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

        jdpMain = new javax.swing.JDesktopPane();
        jtbMain = new javax.swing.JToolBar();
        jbAddFrame = new javax.swing.JButton();
        jbTile = new javax.swing.JButton(new TileAction(jdpMain));
        jbCourse = new javax.swing.JButton();
        jbClass = new javax.swing.JButton();
        jbAssignments = new javax.swing.JButton();
        jbDocuments = new javax.swing.JButton();
        jmbMain = new javax.swing.JMenuBar();
        jmFile = new javax.swing.JMenu();
        jmiAddFrame = new javax.swing.JMenuItem();
        jmiOpenDB = new javax.swing.JMenuItem(new OpenDatabaseAction(this, myProps));
        jmiTestTablePanel = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jmiExit = new javax.swing.JMenuItem();
        jmEdit = new javax.swing.JMenu();
        jmWindows = new javax.swing.JMenu();
        jmiTile = new javax.swing.JMenuItem(new TileAction(jdpMain));
        jmiCascade = new javax.swing.JMenuItem(new CascadeAction(jdpMain));
        jmiMinimize = new javax.swing.JMenuItem(new MinimizeAllWindowsAction(jdpMain));
        jmLookAndFeel = new javax.swing.JMenu();
        jmOptions = new javax.swing.JMenu();
        jmiLoadUserOptions = new javax.swing.JMenuItem(new GetPropertiesAction(this,myProps));
        jmiSaveUserOptions = new javax.swing.JMenuItem(new IST261DesktopPaneDemo.SavePropertiesAction(this, myProps));
        jmHelp = new javax.swing.JMenu();
        jmiDatabaseInformation = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout jdpMainLayout = new javax.swing.GroupLayout(jdpMain);
        jdpMain.setLayout(jdpMainLayout);
        jdpMainLayout.setHorizontalGroup(
            jdpMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 947, Short.MAX_VALUE)
        );
        jdpMainLayout.setVerticalGroup(
            jdpMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 455, Short.MAX_VALUE)
        );

        jtbMain.setFloatable(false);
        jtbMain.setRollover(true);
        jtbMain.setToolTipText("");

        jbAddFrame.setText("Add");
        jbAddFrame.setToolTipText("Create and add new window");
        jbAddFrame.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbAddFrameActionPerformed(evt);
            }
        });
        jtbMain.add(jbAddFrame);

        jbTile.setText("");
        jbTile.setToolTipText("");
        jbTile.setFocusable(false);
        jbTile.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jbTile.setIconTextGap(-17);
        jbTile.setMinimumSize(new java.awt.Dimension(36, 36));
        jbTile.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jtbMain.add(jbTile);

        jbCourse.setText("Course");
        jbCourse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbCourseActionPerformed(evt);
            }
        });

        jbClass.setText("Class");
        jbClass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbClassActionPerformed(evt);
            }
        });

        jbAssignments.setText("Assignments");
        jbAssignments.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbAssignmentsActionPerformed(evt);
            }
        });

        jbDocuments.setText("Resources");
        jbDocuments.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbDocumentsActionPerformed(evt);
            }
        });

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
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jdpMain)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jbCourse)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jbClass)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbAssignments)
                        .addGap(18, 18, 18)
                        .addComponent(jbDocuments)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jtbMain, javax.swing.GroupLayout.PREFERRED_SIZE, 577, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jbCourse)
                        .addComponent(jbClass)
                        .addComponent(jbAssignments)
                        .addComponent(jbDocuments))
                    .addComponent(jtbMain, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jdpMain)
                .addGap(20, 20, 20))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbAddFrameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbAddFrameActionPerformed
        // TODO add your handling code here:
        CreateFrame();
      
        
    }//GEN-LAST:event_jbAddFrameActionPerformed

    private void jmiAddFrameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiAddFrameActionPerformed
        // TODO add your handling code here:
        CreateFrame();
    }//GEN-LAST:event_jmiAddFrameActionPerformed

    private void jmiTestTablePanelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiTestTablePanelActionPerformed
        try 
        {
           System.out.println("Made it to test2");
           
           while (dbConnection == null)
           {
               JOptionPane.showMessageDialog(this, 
                  "You must connect to a database!", 
                  "No Database Connection", JOptionPane.ERROR_MESSAGE);
               jmiOpenDB.doClick();
           } // check for database connection
           


           Statement stTest = dbConnection.createStatement();
           String strQuery = "Select * from AttendanceCode";
            
           strQuery = (String) JOptionPane.showInputDialog(this, "Enter a query", strQuery);
           ResultSet rsAttendance = stTest.executeQuery(strQuery);
           
           
           int[] arrColsToHide = {};
           jpTableDisplay jpDisplay = new jpTableDisplay(rsAttendance,0, arrColsToHide);    
            
            jpDisplay.setPreferredSize(new Dimension(900, 900));
           CreateFrame(jpDisplay, strQuery);
        } catch (SQLException ex) {
            Logger.getLogger(jfMain.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        
    }//GEN-LAST:event_jmiTestTablePanelActionPerformed

    private void jbCourseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbCourseActionPerformed
        JPanel Course = new jpCourse(dbConnection);
        Course.setName("Course");
        CreateFrame(Course,Course.getName());
        // TODO add your handling code here:
    }//GEN-LAST:event_jbCourseActionPerformed

    private void jbClassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbClassActionPerformed
        JPanel Class = new jpClass(dbConnection);
        Class.setName("Class");
        CreateFrame(Class,Class.getName());
        // TODO add your handling code here:
    }//GEN-LAST:event_jbClassActionPerformed

    private void jbAssignmentsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbAssignmentsActionPerformed
        JPanel Assignment = new jpAssignment(dbConnection);
        Assignment.setName("Assignment");
        CreateFrame(Assignment,Assignment.getName());
        // TODO add your handling code here:
    }//GEN-LAST:event_jbAssignmentsActionPerformed

    private void jbDocumentsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbDocumentsActionPerformed
        JPanel Resources = new jpResources(dbConnection);
        Resources.setName("Resources");
        CreateFrame(Resources,Resources.getName());        // TODO add your handling code here:
    }//GEN-LAST:event_jbDocumentsActionPerformed

    private void jmiSaveUserOptionsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiSaveUserOptionsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jmiSaveUserOptionsActionPerformed

    private void jmiOpenDBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiOpenDBActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jmiOpenDBActionPerformed

    private void jmiLoadUserOptionsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiLoadUserOptionsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jmiLoadUserOptionsActionPerformed

    private void jmiDatabaseInformationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiDatabaseInformationActionPerformed
        // TODO add your handling code here:
         JOptionPane.showMessageDialog(this, new jpSQLiteDBInfo(dbConnection, myProps),
                   "Connection Information for " + myProps.getProperty(ApplicationConstants.LAST_DB),
                   JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_jmiDatabaseInformationActionPerformed

    private void jmiExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiExitActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_jmiExitActionPerformed

    private void CreateFrame()
    {
        
        JPanel jpTemp = new JPanel();
        jpTemp.setPreferredSize(new Dimension(200, 200));
       
        CreateFrame(jpTemp,"");
      
      
        
    } // CreateFrame
    
    
    private void CreateFrame(JPanel jpIn, String strIn)
    {
            intWindowCounter++;
        
        JInternalFrame jifTemp = new JInternalFrame("New Frame " 
                + intWindowCounter + " - " + strIn,true,true,true,true);
        
        jifTemp.add(jpIn);
        jifTemp.pack();
        
        jdpMain.add(jifTemp);
        jifTemp.setVisible(true);
       
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        //</editor-fold>
        
        

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(jfMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(jfMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(jfMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(jfMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }java.awt.EventQueue.invokeLater(new Runnable() {
                public void run() {
                                           
                    new jfMain(args).setVisible(true);
                 
                }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JButton jbAddFrame;
    private javax.swing.JButton jbAssignments;
    private javax.swing.JButton jbClass;
    private javax.swing.JButton jbCourse;
    private javax.swing.JButton jbDocuments;
    public javax.swing.JButton jbTile;
    private javax.swing.JDesktopPane jdpMain;
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
    private javax.swing.JToolBar jtbMain;
    // End of variables declaration//GEN-END:variables
}
