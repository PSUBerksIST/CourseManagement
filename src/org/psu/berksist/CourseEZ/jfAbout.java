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
 * 2017 April  9 -  Changed references to libraries.txt/Libraries to resources.txt/Resources.
 *                  Changed anti-spam text to be a little more professional.
 *                  Changed resources.txt format and associated code.
 *                      Can now use one collective resource name that all associated files are collected under.
 *                  Hard-coded fix for Synthetica email in resources.txt and rbtnResourcesActionPerformed for now. -JSS
 * 2017 April  7 -  Turned URL and email formatting code into functions.
 *                  +BUG: leading and trailing spaces in email addresses in contributors from ConvertToEmail.
 *                  TODO: Change how rbtnLibraries reads and displays resources.txt.
 *                      (Note: First line currently holds not-yet-implemented new format.
 *                      Delete that line if testing convertToEmail() before rbtnLibraries
 *                          is updated.) -JSS
 * 2017 April  4 -  Removed HTTPS conversion code. If the user wants HTTPS,
 *                      they should run HTTPS Everywhere instead in Firefox or something.
 *                      Can't tell whether or not a website is equipped for HTTPS from here.
 *                  Modified code to not add "http://" when "https://" is in the TXT files.
 *                      If it's in the TXT, then it was probably added as an HTTPS-enabled website.
 *                  Added most of the JARS' information to resources.txt.
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
        bgrpAbout.add(rbtnResources);
        
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
        rbtnResources = new javax.swing.JRadioButton();
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
                        .addGap(192, 192, 192)
                        .addComponent(lblDescription))
                    .addGroup(jpAboutTitleLayout.createSequentialGroup()
                        .addGap(292, 292, 292)
                        .addGroup(jpAboutTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblProgramName)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpAboutTitleLayout.createSequentialGroup()
                                .addComponent(lblVersionNumber)
                                .addGap(65, 65, 65)))))
                .addContainerGap(131, Short.MAX_VALUE))
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

        rbtnResources.setText("Resources");
        rbtnResources.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtnResourcesActionPerformed(evt);
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
                .addComponent(rbtnResources)
                .addGap(101, 101, 101)
                .addComponent(rbtnTools)
                .addContainerGap(149, Short.MAX_VALUE))
        );
        jpRadioButtonsLayout.setVerticalGroup(
            jpRadioButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpRadioButtonsLayout.createSequentialGroup()
                .addContainerGap(16, Short.MAX_VALUE)
                .addGroup(jpRadioButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbtnTools)
                    .addComponent(rbtnResources)
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
                            astrInput[1] = convertToURL(astrInput[1]);  //convert any URLs
                            astrInput[3] = convertToURL(astrInput[3]);  //convert any URLs
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
     * Example (without double-quotes): "Doe;John;jdd123ANTISPAMTEXT@notarealwebsite.com;notarealwebsite.no/jdd123".
     * Any emails are suggested to add "ANTISPAMTEXT" before the @ symbol to try to prevent scraping by spambots,
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
                        while ((strInput = reader.readLine()) != null)
                        {
                            String[] astrInput = strInput.split(";");
                            if (astrInput.length > 2)   //if there's more than a first name and last name
                            {
                                strContactMethods = "";
                                for (int i = 2; i < astrInput.length; i++)
                                {
                                    astrInput[i] = convertToURL(astrInput[i]);          //convert any URLs
                                    astrInput[i] = convertToEmail(astrInput[i]);        //convert any emails
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
     * Loads resources.txt into jtpText.
     * Course Management's license at the top.
     * resources.txt is the list of resources used in the program
     * (e.g., icons, not help files, as the latter are /generated by/ a tool)
     * and what licenses they operate under, basically.
     * The licenses themselves are regular semi-colon (;)-delimited TXT files in the program's folder.
     * Format in resources.txt is one library/resource per line, as below:
     * "[resource name];[resource URL];[resource license];[resource URL];[description of purpose]";[filename1](;[filename2];[etc.])
     * For example,
     * foolib;foolib.notaurl;Foo License v1.1;foolicense.notaurl;Description of purpose.;foolib1.jar;foolib2.jar;foolib3.jar
     * outputs
     * foolib (footlib.notaurl)
     * -Foo License v1.1 (foolicense.notaurl)
     * -Description of purpose.
     * -Comprised of:
     *  +foolib1.jar
     *  +foolib2.jar
     *  +foolib3.jar
     * @param evt 
     */
    private void rbtnResourcesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtnResourcesActionPerformed
        try
        {
            File fResources = new File(AppConstants.ROOT_FOLDER + "resources.txt");     //this doesn't mean the file actually exists - it just points to where it should be
            if (fResources.exists() == true)    //if resources.txt does exist
            {
                try     //can file can be opened?
                {
                    BufferedReader reader = new BufferedReader(new FileReader(fResources));
                    String strInput = new String();
                    String strFile = new String("<center><b>===List of resources===</b></center><br>");
                    String strResourceFilenames = new String();
                    try     //can file's contents be read and processed?
                    {
                        while ((strInput = reader.readLine()) != null)
                        {
                            String[] astrInput = strInput.split(";");          
                            astrInput[1] = convertToURL(astrInput[1]);
                            
                            astrInput[3] = convertToURL(astrInput[3]);
                            //The resource section isn't intended to have emails here - it's all supposed to be FOSS or similar.
                            //So the email link is hard-coded in resources.txt, and any anti-spam text stripped from the license type field.
                            astrInput[2] = astrInput[2].replaceAll("ANTISPAMTEXT", "");
                            //System.out.println("[DEBUG] License type=" + astrInput[2]);
                            
                            if (astrInput.length > 5)   //if there's more than the basic first 5 fields (resource name, resource URL, license name, license URL, description)
                            {
                                strResourceFilenames = "";
                                for (int i = 5; i < astrInput.length; i++)
                                {
                                    strResourceFilenames += "-" + astrInput[i];
                                    if (i < astrInput.length - 1)   //if not the last filename, add a newline for spacing
                                    {
                                        strResourceFilenames += "<br>";
                                    }
                                }
                                strFile += astrInput[0] + " (" + astrInput[1] + ")<br>-" + astrInput[2] + " (" + astrInput[3] + ")<br>-" + astrInput[4] + "<br>" + strResourceFilenames + "<br><br>";
                            }
                            else
                            {
                                strFile += astrInput[0] + " (" + astrInput[1] + ")<br>-" + astrInput[2] + " (" + astrInput[3] + ")<br>-" + astrInput[4] + "<br><br>";
                            }
                            
                        }
                        jtpText.setText(strFile);
                        jspText.getVerticalScrollBar().setValue(0);
                        reader.close();
                    }
                    catch (IOException e)   //if there's a problem reading a line in the file
                    {
                        JOptionPane.showMessageDialog(null, "Error reading resources.txt.\n" + e.toString(),"Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
                catch (IOException e)   //should open if file can't be opened
                {
                    JOptionPane.showMessageDialog(null, "resources.txt cannot be opened.\n" + e.toString(),"Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            else    //if resources.txt does NOT exist
            {
                JOptionPane.showMessageDialog(null, "resources.txt cannot be found.","Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        catch (Exception e) //generic exception
        {
            JOptionPane.showMessageDialog(null, e.toString(),"Error", JOptionPane.ERROR_MESSAGE); //should print stack trace to message box
        }
    }//GEN-LAST:event_rbtnResourcesActionPerformed

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

    
    /**
     * Adds "http://" to a string lacking "http://" or "https://".
     * Assumes that URLs are isolated (only URL), as the function doesn't attempt to parse out the link from mixed (part of a sentence) input.
     * @param strInput String that has a period (.), not an at symbol (@), and lacks "http://"/"https://" signifying that it's a URL.
     * @return If link, formatted string with "http://" added to the beginning of it.
     */
    private String convertToURL(String strInput)
    {
        //if string contains period ('.') and not @, assume it's a URL
         if (strInput.contains(".") == true && strInput.contains("@") == false)
            {
                //if URL doesn't have http:// to signify it's a URL, add http://
                //assume that if it already has https:// in it for some reason,
                //then the website is HTTPS-enabled and shouldn't be modified
                if (strInput.contains("http://") == false && strInput.contains("https://") == false)
                {
                    strInput = "http://" + strInput;
                }
                //turns it into a link that opens in the user's default web browser
                strInput = "<a href=\"" + strInput + "\">" + strInput + "</a>";
                //System.out.println("[DEBUG] Formatted URL=" + strInput);
                return strInput;
            }
         return strInput;   //else return the unmodified string
    }
    
    /**
     * Converts probable email addresses into working linked emails.
     * Accepts emails in isolated (only email) and mixed (part of a sentence) formats, attempting to parse the email out of the latter.
     * BUG: Doesn't properly process mixed strings.
     *  For example, the Synthetica license (which has an email address in it) has the spaces before and after the email address associated with the email address instead of the non-email parts.
     *  The link then doesn't display.
     *  Also, there's a missing period at the end of the sentence.
     * @param strInput String that contains an at symbol (@).
     * @return If email address, formatted string with working link and anti-spam text removed.
     */
    private String convertToEmail(String strInput)
    {
        if (strInput.contains("@") == true) //if probable email
        {
            int intEmailStart;
            int intEmailEnd;
            int i = strInput.indexOf("@");
            
            //while character is not ' ', '(', or '[', and i > 0
            while (strInput.substring(i, i + 1).equals(" ") == false && strInput.substring(i, i + 1).equals("(") == false && strInput.substring(i, i + 1).equals("[") == false && i > 0)
            {
                i--;
            }
            if (i < 0)
            {
                intEmailStart = 0;
            }
            else
            {
                intEmailStart = i;  //set beginning of email address (either hit probable non-email character or end of string)
            }
            //System.out.println("[DEBUG] intEmailStart=" + intEmailStart);
            
            i = strInput.indexOf("@");
            //System.out.println("[DEBUG] Index of @=" + i);
            
//            while (!(strInput.indexOf(i) == ' ' || strInput.indexOf(i) == ')' || strInput.indexOf(i) == ']' || strInput.indexOf(i) == ',') && i < strInput.length() - 1)
            while (strInput.substring(i, i + 1).equals(" ") == false && strInput.substring(i, i + 1).equals(")") == false && strInput.substring(i, i + 1).equals("]") == false && i < strInput.length() - 1)
            //while (strInput.substring(i).contains(" ") == false && strInput.substring(i).contains(")") == false && strInput.substring(i).contains("]") == false && i < strInput.length() - 1)
            {
                i++;
            }
            if (i == strInput.length())
            {
                intEmailEnd = strInput.length() - 1;
            }
            else
            {
                intEmailEnd = i;    //set end of email address
            }
//            System.out.println("[DEBUG] intEmailEnd=" + intEmailEnd);
            
            /*  TODO: Check against a whitelist for probable email characters instead of a blacklist for non-email characters.
                For beginning of email, whitelist would be something like:
                    Alphanumerics (A-Z, 0-9)
                    Underscore (_)
                    Dash (-)
                    Period (.)
                For ending of email (email domain), whitelist would be something like:
                    Find the first period, and then the first non-alphabet, non-dash, non-underscore? character.
                Example of a nonstandard email address would be:
                    firstname.lastname_1234-5@example-name_here.com
            */

            
//            System.out.println("[DEBUG] Pre-email substring=" + strInput.substring(0, intEmailStart));
//            System.out.println("[DEBUG] Email=" + strInput.substring(intEmailStart,intEmailEnd + 1));
//            System.out.println("[DEBUG] Post-email substring=" + strInput.substring(intEmailEnd, strInput.length() - 1));
            //Removes anti-spambot text from the string and turns it into a link that opens in the user's default email client
            strInput = strInput.substring(0, intEmailStart) + "<a href=mailto:\"" + strInput.substring(intEmailStart,intEmailEnd + 1).replaceAll("ANTISPAMTEXT", "") + "\">" + strInput.substring(intEmailStart,intEmailEnd + 1).replaceAll("ANTISPAMTEXT", "") + "</a>" + strInput.substring(intEmailEnd, strInput.length() - 1);
            
//            System.out.println("[DEBUG] Pre-email substring=" + strInput.substring(0, intEmailStart));
//            System.out.println("[DEBUG] Email=" + strInput.substring(intEmailStart,intEmailEnd + 1));
//            System.out.println("[DEBUG] Post-email substring=" + strInput.substring(intEmailEnd, strInput.length() - 1));
//            strInput = strInput.substring(0, intEmailStart) + "<a href=mailto:\"" + strInput.substring(intEmailStart,intEmailEnd + 1).replaceAll("ANTISPAMTEXT", "") + "\">" + strInput.substring(intEmailStart,intEmailEnd + 1).replaceAll("ANTISPAMTEXT", "") + "</a>" + strInput.substring(intEmailEnd, strInput.length() - 1);
            
//            System.out.println("[DEBUG] Formatted string=" + strInput);
            return strInput;    //returns modified string
        }
        return strInput;    //if not email, return unmodified string
    }
    
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
    private javax.swing.JRadioButton rbtnResources;
    private javax.swing.JRadioButton rbtnTools;
    // End of variables declaration//GEN-END:variables
}



