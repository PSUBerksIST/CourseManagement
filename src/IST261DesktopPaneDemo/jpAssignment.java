/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IST261DesktopPaneDemo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
/**
 *
 * @author Nathan
 */
public class jpAssignment extends javax.swing.JPanel {

    List<Integer> selectedAssignmentIDs = new ArrayList<Integer>();
    
    /**
     * Creates new form jpAssignment
     */
    private Connection dbConnection;
    private Statement st;
    
    public jpAssignment()
    {
        initComponents();

    }
    
    public jpAssignment(Connection inConnection){
        initComponents();
        setdbConnection(inConnection);;
        setAssignments();
        
    }
    
    // This sets the local datbase connection
    private void setdbConnection(Connection inConnection){
        dbConnection = inConnection;
        try {             
            st = dbConnection.createStatement();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
    
    public void setAssignments()
    {
        try {

            DefaultTableModel model = (DefaultTableModel) jtAssignments.getModel();

            // Reset the JTable in case we are coming back a second time
            int rowCount = model.getRowCount();
            for (int i = rowCount - 1; i >= 0; i--) {
                model.removeRow(i);
            }
            model.setColumnCount(0);
            model.setRowCount(0);
            
            // Create our columns
            model.addColumn("Select");
            model.addColumn("ID");
            model.addColumn("Name");
            model.addColumn("Description");
            model.addColumn("Points");
            model.addColumn("Group");
            
            // Set our model and also create our listeners
            jtAssignments.getModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {

                // On a table change update our local store of selectedAssignmentIDs
                    for(int i = 0; i < jtAssignments.getModel().getRowCount(); i++)
                    {

                        int assignmentId = Integer.parseInt(jtAssignments.getModel().getValueAt(i,1).toString());

                        if ((Boolean) jtAssignments.getModel().getValueAt(i,0))
                        {  

                            System.out.println("Selected ID: " + assignmentId);

                            // Add the ID if we do not have it already
                            if(!selectedAssignmentIDs.contains(assignmentId))
                            {
                                selectedAssignmentIDs.add(assignmentId);
                            }

                        }
                        else
                        {

                            System.out.println("Selected ID: " + assignmentId);

                            for (Iterator<Integer> iterator = selectedAssignmentIDs.iterator(); iterator.hasNext(); ) {
                                Integer id = iterator.next();
                                if (id == assignmentId) 
                                {
                                    iterator.remove();
                                }
                            }

                      }

                    }     
                    
                    // Show the programmer what IDs are selected
                    System.out.println(selectedAssignmentIDs);
                }

            });
            
            // JTable will make our checkboxes for us
            TableColumn tc = jtAssignments.getColumnModel().getColumn(0);
            tc.setCellEditor(jtAssignments.getDefaultEditor(Boolean.class));  
            tc.setCellRenderer(jtAssignments.getDefaultRenderer(Boolean.class)); 
            
            // Result Set 
            ResultSet result = st.executeQuery("SELECT -1 AS 'Select', ID, ShortName AS Name, Description, MaximumPoints AS Points, GroupAssignment AS 'Group' FROM Assignments;");

            int i = 0;
            while (result.next()) 
            {
                
                // SQLite won't do Booleans so lets convert it to one
                boolean b = (Integer.parseInt(result.getString("Select")) != -1);
                boolean g = (Integer.parseInt(result.getString("Group")) != 1);
                String group = (g) ? "no" : "yes";
                // Add our row to the JTable
                model.addRow(new Object[]{ b, result.getString("ID"), result.getString("Name"), result.getString("Description"), result.getString("Points"), group});
                // Authorize the checkbox to be editable
                model.isCellEditable(i, 0);
                i++;   
                
            }

        }
        catch (SQLException ex) 
        {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jbAddAssignment = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtAssignments = new javax.swing.JTable();
        jbDelete = new javax.swing.JButton();
        jbRefresh = new javax.swing.JButton();
        jlAssignments = new javax.swing.JLabel();

        jbAddAssignment.setText("Create Assignment");
        jbAddAssignment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbAddAssignmentActionPerformed(evt);
            }
        });

        jtAssignments.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Select", "ID", "Name", "Description", "Points", "Group"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                true, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jtAssignments);

        jbDelete.setText("Delete");
        jbDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbDeleteActionPerformed(evt);
            }
        });

        jbRefresh.setText("Refresh");
        jbRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbRefreshActionPerformed(evt);
            }
        });

        jlAssignments.setText("Assignments:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jlAssignments))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jbAddAssignment)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbDelete, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jlAssignments)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbAddAssignment)
                    .addComponent(jbDelete)
                    .addComponent(jbRefresh))
                .addContainerGap(78, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jbAddAssignmentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbAddAssignmentActionPerformed
        JDialog jdAddAssignment = new JDialog();
        JPanel AddAssignment = new jpAddAssignment(dbConnection);
        jdAddAssignment.add(AddAssignment);
        jdAddAssignment.setSize(565, 580);
        jdAddAssignment.setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jbAddAssignmentActionPerformed

    private void jbDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbDeleteActionPerformed
     
        if(!selectedAssignmentIDs.isEmpty())
        {
            String options[] = {"Yes","No"};

            int PromptResult = JOptionPane.showOptionDialog(null,"Are you sure you want to delete?","Assignments",JOptionPane.DEFAULT_OPTION,JOptionPane.WARNING_MESSAGE,null,options,options[1]);
            if(PromptResult==JOptionPane.YES_OPTION)
            {

                try {
                    System.out.println("Delete Selected Assignments fired!");


                    if(!selectedAssignmentIDs.isEmpty())
                    {
                        for (Iterator<Integer> iterator = selectedAssignmentIDs.iterator(); iterator.hasNext(); ) {
                            Integer id = iterator.next();
                            iterator.remove();
                            st.execute("DELETE FROM Assignments WHERE ID = " + id);
                        }
                    }

                    setAssignments();


                } catch (SQLException ex) {
                    Logger.getLogger(jpAddAssignmentsToClass.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        else
        {
            JFrame PopUp = new JFrame();
            JOptionPane.showMessageDialog(PopUp,"Select assignments first!"); 
        }
        
    }//GEN-LAST:event_jbDeleteActionPerformed

    private void jbRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbRefreshActionPerformed
        setAssignments();
    }//GEN-LAST:event_jbRefreshActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton jbAddAssignment;
    private javax.swing.JButton jbDelete;
    private javax.swing.JButton jbRefresh;
    private javax.swing.JLabel jlAssignments;
    private javax.swing.JTable jtAssignments;
    // End of variables declaration//GEN-END:variables
//    private void CreateFrame(JPanel inPanel) {
//                //  intWindowCounter++;
//        JFrame jifTemp = new jfTempFrames(inPanel.getName());// +"" + intWindowCounter,true,true,true,true);
//        
//        //JPanel jpTemp = new jpClass();
//        inPanel.setPreferredSize(new Dimension(400, 400));
//        jifTemp.add(inPanel);
//        System.out.println(this.getClass());
//        jifTemp.pack();
//        //jdpMain.add(jifTemp);
//        jifTemp.setVisible(true);
//        
//        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }//CreateFrame
}
