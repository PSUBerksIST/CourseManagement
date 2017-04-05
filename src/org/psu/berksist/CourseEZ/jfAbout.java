/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.psu.berksist.CourseEZ;

import java.awt.Desktop;
import java.io.*;
import java.net.URISyntaxException;
import javax.swing.JOptionPane;
import javax.swing.event.HyperlinkEvent;

/**
 *
 * @author jss5783
 * 
 * 
 * 
 *  ******************* MODIFICATION LOG *****************************************
 * 2017 April  4 -  Removed HTTPS conversion code. If the user wants HTTPS,
 *                      they should run HTTPS Everywhere instead in Firefox or something.
 *                      Can't tell whether or not a website is equipped for HTTPS from here.
 *                  Modified code to not add "http://" when "https://" is in the TXT files.
 *                      If it's in the TXT, then it was probably added as an HTTPS-enabled website.
 *                  Added most of the JARS' information to libraries.txt.
 *                  Added information to tools.txt. -JSS
 * 2017 April  3 -  Added email-handling code to rbtnLibraries.
 *                      (Should extract into a function later if used again.) -JSS
 * 2017 April  3 -  Added more comments.
 *                  rbtnLibraries is now also for "Other (in-program) Resources" like icons.
 *                  Made jtpText use HTML.
 *                  Added the ability to display and use URL and mail links
 *                      (clicking on them opens in the default web browser and email program,
 *                      respectively).
 *                  contributors.txt now lists LastName before FirstName for easy sortability.
 *                  Additional students' and teacher's names and contact information added to contributors.txt.
 *                  Anti-spam text is now stripped on an individual basis,
 *                      rather than at the very end,
 *                      so emails can be displayed in all lowercase. -JSS
 * 2017 March 31 -  Cleaned up code (added try... catch; comments; etc.).
 *                  Labels now update with program name and program version from AppConstants constants.
 *                  -BUG: Fixed bug with licenses.txt (there was an empty line).
 *                  +BUG: Loading a long file results in the textbox being scrolled to the bottom.
 *                  Can have 0+ methods of contact for contributors.
 *                  Most library licenses added to licenses.txt.
 *                  Some students' emails added to contributors.txt
 *                      (somewhat obfuscated to try to prevent spambots scraping the file). -JSS
 * 2017 March 30 -  Cleaned up rbtnContributorsActionPerformed code.
 *                  Removed rbtnChangelog, associated code, and TXT file.
 *                  Added rbtnTools and associated code.
 *                  Added lblDescription.
 *                  Changed rbtnLicenses to rbtnLibraries and associated code and TXT file.
 *                  +BUG: Doesn't read licenses.txt.    -JSS
 * 2017 March 29 -  Created jfAbout.java. Added basic rough functionality. - JSS
 */
public class jfAbout extends javax.swing.JFrame {

    /**
     * Creates new form jfAbout
     */
    public jfAbout() {
        initComponents();
        
        //jtpText can now use HTML code
        jtpText.setEditorKit(jtpText.createEditorKitForContentType("text/html"));
        
        //Updates labels with program name and version number
        lblProgramName.setText(AppConstants.APP_ID);
        lblVersionNumber.setText("Version: " + AppConstants.APP_VERSION);
        
        //register radio buttons to button group
        bgrpAbout.add(rbtnTools);
        bgrpAbout.add(rbtnContributors);
        bgrpAbout.add(rbtnLibraries);
        
        rbtnContributors.doClick();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bgrpAbout = new javax.swing.ButtonGroup();
        jpText = new javax.swing.JPanel();
        jpAboutTitle = new javax.swing.JPanel();
        lblVersionNumber = new javax.swing.JLabel();
        lblProgramName = new javax.swing.JLabel();
        lblDescription = new javax.swing.JLabel();
        jspText = new javax.swing.JScrollPane();
        jtpText = new javax.swing.JTextPane();
        jpRadioButtons = new javax.swing.JPanel();
        rbtnTools = new javax.swing.JRadioButton();
        rbtnLibraries = new javax.swing.JRadioButton();
        rbtnContributors = new javax.swing.JRadioButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        lblVersionNumber.setText("VERSION_NUMBER");

        lblProgramName.setFont(new java.awt.Font("DejaVu Sans", 0, 24)); // NOI18N
        lblProgramName.setText("PROGRAM_NAME");

        lblDescription.setText("An open-source program for managing courses for teachers.");

        javax.swing.GroupLayout jpAboutTitleLayout = new javax.swing.GroupLayout(jpAboutTitle);
        jpAboutTitle.setLayout(jpAboutTitleLayout);
        jpAboutTitleLayout.setHorizontalGroup(
            jpAboutTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpAboutTitleLayout.createSequentialGroup()
                .addGroup(jpAboutTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpAboutTitleLayout.createSequentialGroup()
                        .addGap(292, 292, 292)
                        .addComponent(lblVersionNumber))
                    .addGroup(jpAboutTitleLayout.createSequentialGroup()
                        .addGap(192, 192, 192)
                        .addComponent(lblDescription)))
                .addContainerGap(128, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpAboutTitleLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(lblProgramName)
                .addGap(248, 248, 248))
        );
        jpAboutTitleLayout.setVerticalGroup(
            jpAboutTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpAboutTitleLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblProgramName)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblVersionNumber)
                .addGap(18, 18, 18)
                .addComponent(lblDescription)
                .addContainerGap(25, Short.MAX_VALUE))
        );

        jspText.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        jtpText.setEditable(false);
        jtpText.addHyperlinkListener(new javax.swing.event.HyperlinkListener() {
            public void hyperlinkUpdate(javax.swing.event.HyperlinkEvent evt) {
                jtpTextHyperlinkUpdate(evt);
            }
        });
        jtpText.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtpTextMouseClicked(evt);
            }
        });
        jspText.setViewportView(jtpText);

        javax.swing.GroupLayout jpTextLayout = new javax.swing.GroupLayout(jpText);
        jpText.setLayout(jpTextLayout);
        jpTextLayout.setHorizontalGroup(
            jpTextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpTextLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpTextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jspText)
                    .addComponent(jpAboutTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jpTextLayout.setVerticalGroup(
            jpTextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpTextLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jpAboutTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jspText, javax.swing.GroupLayout.PREFERRED_SIZE, 314, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        getContentPane().add(jpText, java.awt.BorderLayout.PAGE_START);

        rbtnTools.setText("Tools");
        rbtnTools.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtnToolsActionPerformed(evt);
            }
        });

        rbtnLibraries.setText("Libraries/Other Resources");
        rbtnLibraries.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtnLibrariesActionPerformed(evt);
            }
        });

        rbtnContributors.setText("Contributors");
        rbtnContributors.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtnContributorsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpRadioButtonsLayout = new javax.swing.GroupLayout(jpRadioButtons);
        jpRadioButtons.setLayout(jpRadioButtonsLayout);
        jpRadioButtonsLayout.setHorizontalGroup(
            jpRadioButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpRadioButtonsLayout.createSequentialGroup()
                .addGap(123, 123, 123)
                .addComponent(rbtnContributors)
                .addGap(107, 107, 107)
                .addComponent(rbtnLibraries)
                .addGap(101, 101, 101)
                .addComponent(rbtnTools)
                .addContainerGap(53, Short.MAX_VALUE))
        );
        jpRadioButtonsLayout.setVerticalGroup(
            jpRadioButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpRadioButtonsLayout.createSequentialGroup()
                .addContainerGap(16, Short.MAX_VALUE)
                .addGroup(jpRadioButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbtnTools)
                    .addComponent(rbtnLibraries)
                    .addComponent(rbtnContributors))
                .addContainerGap())
        );

        getContentPane().add(jpRadioButtons, java.awt.BorderLayout.PAGE_END);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Loads tools.txt into jtpText.
     * Tools are external programs used to create this program.
     * Format in tools.txt is one tool per line, as below:
     * "[tool name];[tool URL];[library license];[license URL];[description of purpose]"
     * For example,
     * foolib;foolib.notaurl;Foo License v1.1;foolicense.notaurl;Description of purpose.
     * outputs
     * foolib (footlib.notaurl)
     * -Foo License v1.1 (foolicense.notaurl)
     * -Description of purpose.
     * @param evt 
     */
    private void rbtnToolsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtnToolsActionPerformed
        try
        {
            File fTools = new File(AppConstants.ROOT_FOLDER + "tools.txt");     //this doesn't mean the file actually exists - it just points to where it should be
            if (fTools.exists() == true)    //if tools.txt does exist
            {
                try     //can file can be opened?
                {
                    BufferedReader reader = new BufferedReader(new FileReader(fTools));
                    String strInput = new String();
                    String strFile = new String("<center><b>===List of tools===</b></center><br>");
                    try     //can file's contents be read and processed?
                    {
                        while ((strInput = reader.readLine()) != null)
                        {
                            String[] astrInput = strInput.split(";");
                            if (astrInput[1].contains(".") == true) //if the string contains at least one period ('.'), assume that it's a URL and add the tags
                            {
                                //if URL doesn't have http:// to signify it's a URL, add http://
                                //assume that if it already has https:// in it for some reason,
                                //then the website is HTTPS-enabled and shouldn't be modified
                                if (astrInput[1].contains("http://") == false && astrInput[1].contains("https://") == false)
                                {
                                    astrInput[1] = "http://" + astrInput[1];
                                }
                                astrInput[1] = "<a href=\"" + astrInput[1] + "\">" + astrInput[1] + "</a>";   //turns it into a link that opens in the user's default web browser
                            }
                            if (astrInput[3].contains(".") == true) //if the string contains at least one period ('.'), assume that it's a URL and add the tags
                            {
                                //if URL doesn't have http:// to signify it's a URL, add http://
                                //assume that if it already has https:// in it for some reason,
                                //then the website is HTTPS-enabled and shouldn't be modified
                                if (astrInput[3].contains("http://") == false && astrInput[3].contains("https://") == false)
                                {
                                    astrInput[3] = "http://" + astrInput[3];
                                }
                                
                                astrInput[3] = "<a href=\"" + astrInput[3] + "\">" + astrInput[3] + "</a>";   //turns it into a link that opens in the user's default web browser
                            }
                            strFile += astrInput[0] + " (" + astrInput[1] + ")<br>-" + astrInput[2] + " (" + astrInput[3] + ")<br>-" + astrInput[4] + "<br><br>";
                        }
                        jtpText.setText(strFile);
                        jspText.getVerticalScrollBar().setValue(0);
                        reader.close();
                    }
                    catch (IOException e)   //if there's a problem reading a line in the file
                    {
                        JOptionPane.showMessageDialog(null, "Error reading tools.txt.\n" + e.toString(),"Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
                catch (IOException e)   //should open if file can't be opened
                {
                    JOptionPane.showMessageDialog(null, "tools.txt cannot be opened.\n" + e.toString(),"Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            else    //if tools.txt does NOT exist
            {
                JOptionPane.showMessageDialog(null, "tools.txt cannot be found.","Error", JOptionPane.ERROR_MESSAGE);
            }
        jspText.getVerticalScrollBar().setValue(1);
        }
        catch (Exception e) //generic exception
        {
            JOptionPane.showMessageDialog(null, e.toString(),"Error", JOptionPane.ERROR_MESSAGE); //should print stack trace to message box
        }
    }//GEN-LAST:event_rbtnToolsActionPerformed

    /**
     * Loads contributors.txt into jtpText.
     * Contributors are listed as "[first name] [last name] ([contact information])".
     * The project head's name is listed first and indicated as such.
     * File format is one person per line, formatted as below:
     * "[LastName];[FirstName]", followed by one or more methods of contact (separated by semi-colons).
     * LastName is before FirstName to allow easy automatic sorting in the TXT,
     * while preserving any manual placement (e.g., course instructors).
     * Example (without double-quotes): "Doe;John;jdd123SPAMBOTFAKEOUTREMOVEME@notarealwebsite.com;notarealwebsite.no/jdd123".
     * Any emails are suggested to add "SPAMBOTFAKEOUTREMOVEME" before the @ symbol to try to prevent scraping by spambots,
     * but this is not required.
     * @param evt 
     */
    private void rbtnContributorsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtnContributorsActionPerformed
        //TODO: Is there a way to check for the filepath without needing a /case-sensitive/ file extension attached?
        try
        {
            File fContributors = new File(AppConstants.ROOT_FOLDER + "contributors.txt");     //this doesn't mean the file actually exists - it just points to where it should be
            if (fContributors.exists() == true)    //if tools.txt does exist
            {
                try     //can file can be opened?
                {
                    BufferedReader reader = new BufferedReader(new FileReader(fContributors));
                    String strInput = new String();
                    String strFile = new String("<center><b>===List of all contributors (and contact information)===</b></center><br>");
                    String strContactMethods = new String();
                    try     //can file's contents be read and processed?
                    {
//                String strFirstName = new String();
//                String strLastName = new String();
//                String strGitHubAccount = new String();
                        while ((strInput = reader.readLine()) != null)
                        {
                            String[] astrInput = strInput.split(";");
                            if (astrInput.length > 2)   //if there's more than a first name and last name
                            {
                                strContactMethods = "";
                                for (int i = 2; i < astrInput.length; i++)
                                {
                                    if (astrInput[i].contains(".") == true && astrInput[i].contains("@") == false)  //if string contains period ('.') and not @, assume it's a URL
                                    {
                                        //if URL doesn't have http:// to signify it's a URL, add http://
                                        //assume that if it already has https:// in it for some reason,
                                        //then the website is HTTPS-enabled and shouldn't be modified
                                        if (astrInput[i].contains("http://") == false && astrInput[i].contains("https://") == false)
                                        {
                                            astrInput[i] = "http://" + astrInput[i];
                                        }
                                        
                                        astrInput[i] = "<a href=\"" + astrInput[i] + "\">" + astrInput[i] + "</a>";   //turns it into a link that opens in the user's default web browser
                                    }
                                    else if (astrInput[i].contains("@") == true) //if string contains @, assume it's an email address
                                    {
                                        //Removes anti-spambot-scraping from the string.
                                        astrInput[i] = astrInput[i].toLowerCase().replaceAll("spambotfakeoutremoveme", "");   //makes emails all lowercase and removes the anti-spam text
                                        astrInput[i] = "<a href=mailto:\"" + astrInput[i] + "\">" + astrInput[i] + "</a>";   //turns it into a link that opens in the user's default email client
                                    }
                                    strContactMethods += astrInput[i];
                                    if (i < astrInput.length - 1)   //if not the last method of contact, add a comma for spacing
                                    {
                                        strContactMethods += ", ";
                                    }
                                }
                                strFile += astrInput[1] + " " + astrInput[0] + " (" + strContactMethods + ")<br>";
                            }
                            else
                            {
                                strFile += astrInput[1] + " " + astrInput[0] + "<br>";
                            }
    //                    strFile += strFirstName + " " + strLastName + " (" + strGitHubAccount + ")<br>";
    //                    strFirstName = strInput.substring(0, strInput.indexOf(',') );
    //                    strLastName = strInput.substring(strInput.indexOf(',') + 1, strInput.lastIndexOf(',') );
    //                    strGitHubAccount = strInput.substring(strInput.lastIndexOf(',') + 1, strInput.length() - 1);
    //                    strFile += strFirstName + " " + strLastName + " (" + strGitHubAccount + ")<br>";
                        }
                        jtpText.setText(strFile);
                        jspText.getVerticalScrollBar().setValue(0);
                        reader.close();
                    }
                    catch (IOException e)   //if there's a problem reading a line in the file
                    {
                        JOptionPane.showMessageDialog(null, "Error reading contributors.txt.\n" + e.toString(),"Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
                catch (IOException e)   //should open if file can't be opened
                {
                    JOptionPane.showMessageDialog(null, "contributors.txt cannot be opened.\n" + e.toString(),"Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            else    //if contributors.txt does NOT exist
            {
                JOptionPane.showMessageDialog(null, "contributors.txt cannot be found.","Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        catch (Exception e) //generic exception
        {
            JOptionPane.showMessageDialog(null, e.toString(),"Error", JOptionPane.ERROR_MESSAGE); //should print stack trace to message box
        }
    }//GEN-LAST:event_rbtnContributorsActionPerformed

    /**
     * Loads libraries.txt into jtpText.
     * Course Management's license at the top.
     * libraries.txt is the list of libraries/non-library resources used inside of the program
     * (e.g., icons, not help files, as the latter are /generated by/ a tool) and what licenses they operate under,
     * basically. The licenses themselves are regular TXT files in the program's folder.
     * Format in libraries.txt is one library/resource per line, as below:
     * "[library name];[library URL];[library license];[license URL];[description of purpose]"
     * For example,
     * foolib;foolib.notaurl;Foo License v1.1;foolicense.notaurl;Description of purpose.
     * outputs
     * foolib (footlib.notaurl)
     * -Foo License v1.1 (foolicense.notaurl)
     * -Description of purpose.
     * @param evt 
     */
    private void rbtnLibrariesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtnLibrariesActionPerformed
        try
        {
            File fLibraries = new File(AppConstants.ROOT_FOLDER + "libraries.txt");     //this doesn't mean the file actually exists - it just points to where it should be
            if (fLibraries.exists() == true)    //if libraries.txt does exist
            {
                try     //can file can be opened?
                {
                    BufferedReader reader = new BufferedReader(new FileReader(fLibraries));
                    String strInput = new String();
                    String strFile = new String("<center><b>===List of libraries/other resources used===</b></center><br>");
                    try     //can file's contents be read and processed?
                    {
                        while ((strInput = reader.readLine()) != null)
                        {
                            String[] astrInput = strInput.split(";");          
                            if (astrInput[1].contains(".") == true) //if the string contains at least one period ('.'), assume that it's a URL and add the tags
                            {
                                //if URL doesn't have http:// to signify it's a URL, add http://
                                //assume that if it already has https:// in it for some reason,
                                //then the website is HTTPS-enabled and shouldn't be modified
                                if (astrInput[1].contains("http://") == false && astrInput[1].contains("https://") == false)
                                {
                                    astrInput[1] = "http://" + astrInput[1];
                                }
                                astrInput[1] = "<a href=\"" + astrInput[1] + "\">" + astrInput[1] + "</a>";   //turns it into a link that opens in the user's default web browser
                            }
                            
//                            if (astrInput[3].contains(".") == true) //if the string contains at least one period ('.'), assume that it's a URL and add the tags
//                            {
//                                //if URL doesn't have http:// to signify it's a URL, add http://
//                                if (astrInput[3].contains("http://") == false)
//                                {
//                                    astrInput[3] = "http://" + astrInput[3];
//                                }
//                                
//                                astrInput[3] = "<a href=\"" + astrInput[3] + "\">" + astrInput[3] + "</a>";   //turns it into a link that opens in the user's default web browser
//                            }
                            
                            if (astrInput[3].contains(".") == true && astrInput[3].contains("@") == false)  //if string contains period ('.') and not @, assume it's a URL
                            {
                                //if URL doesn't have http:// to signify it's a URL, add http://
                                //assume that if it already has https:// in it for some reason,
                                //then the website is HTTPS-enabled and shouldn't be modified
                                if (astrInput[3].contains("http://") == false && astrInput[3].contains("https://") == false)
                                {
                                    astrInput[3] = "http://" + astrInput[3];
                                }

                                astrInput[3] = "<a href=\"" + astrInput[3] + "\">" + astrInput[3] + "</a>";   //turns it into a link that opens in the user's default web browser
                            }
                            if (astrInput[2].contains("@") == true) //if string contains @, assume it's an email address
                            {
                                //Removes anti-spambot-scraping from the string.
                                //Crudely hacked together from the other versions of this processing.
                                //The license section isn't intended to have emails here - it's all supposed to be FOSS or similar.
                                astrInput[2] = astrInput[2].replaceAll("SPAMBOTFAKEOUTREMOVEME", "");   //makes emails all lowercase and removes the anti-spam text
                            }
                            
                            strFile += astrInput[0] + " (" + astrInput[1] + ")<br>-" + astrInput[2] + " (" + astrInput[3] + ")<br>-" + astrInput[4] + "<br><br>";
                        }
                        jtpText.setText(strFile);
                        jspText.getVerticalScrollBar().setValue(0);
                        reader.close();
                    }
                    catch (IOException e)   //if there's a problem reading a line in the file
                    {
                        JOptionPane.showMessageDialog(null, "Error reading libraries.txt.\n" + e.toString(),"Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
                catch (IOException e)   //should open if file can't be opened
                {
                    JOptionPane.showMessageDialog(null, "libraries.txt cannot be opened.\n" + e.toString(),"Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            else    //if libraries.txt does NOT exist
            {
                JOptionPane.showMessageDialog(null, "libraries.txt cannot be found.","Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        catch (Exception e) //generic exception
        {
            JOptionPane.showMessageDialog(null, e.toString(),"Error", JOptionPane.ERROR_MESSAGE); //should print stack trace to message box
        }
    }//GEN-LAST:event_rbtnLibrariesActionPerformed

    /**
     * Opens the URI (e.g., URL link, mailto: link) when the URI is clicked on.
     * @param evt 
     */
    private void jtpTextHyperlinkUpdate(javax.swing.event.HyperlinkEvent evt) {//GEN-FIRST:event_jtpTextHyperlinkUpdate
            if (Desktop.isDesktopSupported() == true)
            {
                try
                {
                    if (evt.getEventType() == HyperlinkEvent.EventType.ACTIVATED)   //if link is activated (e.g., clicked)
                    {
                        Desktop.getDesktop().browse(evt.getURL().toURI());
                    }
                }
                catch (IOException | URISyntaxException ex)
                {
                    //ex.printStackTrace(System.err);
                    JOptionPane.showMessageDialog(null, ex.toString(), "Error", JOptionPane.ERROR_MESSAGE); //should print stack trace to message box
                }
            }
    }//GEN-LAST:event_jtpTextHyperlinkUpdate

    private void jtpTextMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtpTextMouseClicked
        //TODO; unneeded and need to remove this stub somehow
    }//GEN-LAST:event_jtpTextMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgrpAbout;
    private javax.swing.JPanel jpAboutTitle;
    private javax.swing.JPanel jpRadioButtons;
    private javax.swing.JPanel jpText;
    private javax.swing.JScrollPane jspText;
    private javax.swing.JTextPane jtpText;
    private javax.swing.JLabel lblDescription;
    private javax.swing.JLabel lblProgramName;
    private javax.swing.JLabel lblVersionNumber;
    private javax.swing.JRadioButton rbtnContributors;
    private javax.swing.JRadioButton rbtnLibraries;
    private javax.swing.JRadioButton rbtnTools;
    // End of variables declaration//GEN-END:variables
}



