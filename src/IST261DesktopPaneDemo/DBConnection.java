/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package IST261DesktopPaneDemo;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.util.Properties;
import javax.swing.JOptionPane;


/**
 *
 * @author whb108
 * 
 * Direct connection to and manipulation of a SQLite database.
 * 
 */


/*
 TODO Add example for delete
TODO Add class definition for an object based on a single table
TODO Add JFileChooser

*/
public class DBConnection {
    
    private static final String strDBClass = "org.sqlite.JDBC";
    private static final String strJDBCString = "jdbc:sqlite:";
    private boolean bDebugging = true;
    private jfMain jfApp;
  //  private static String strDBName = "C:\\Users\\admin_whb108\\Dropbox\\CourseManagement\\CourseManagement2.db3";
  //   private static String strDBName = "F:\\NetBeansProjects\\JDBCDemo1\\src\\JDBCDemo1\\Address.db3";
  //  private static String strDBName = "E:\\NetBeansProjects\\JDBCDemoV3\\JDBCDemo\\CleanedCourseManagement2.db3";
    
    public DBConnection(jfMain jfmIn)
    {
        jfApp = jfmIn;
    }    
        
       
    public Connection connectToDB(String strDBName, Properties propsIn)
    {
    
        Connection c;
             
    try {
      Class.forName(strDBClass);
      c = DriverManager.getConnection(strJDBCString + strDBName);
      propsIn.setProperty(ApplicationConstants.LAST_DB, strDBName);
      return c;
    } catch ( Exception e ) {
      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
      return null;
      // System.exit(0);
    }
    } // connectToDB

    
    
        public Connection connectToDB(Properties propsIn)
    {
        boolean bNewConnection = false;
        
        if (jfApp.dbConnection != null)
        {
            bNewConnection = (JOptionPane.showConfirmDialog(jfApp, 
                   "You are currently connected to " 
                      + propsIn.getProperty(ApplicationConstants.LAST_DB,"")
                   + "\n\n Do you want to connect to a different DB?", 
                   "Connect to New Database?", JOptionPane.YES_NO_OPTION,
                   JOptionPane.WARNING_MESSAGE)
                == JOptionPane.YES_OPTION);
            if (bNewConnection == false)
            {
                return jfApp.dbConnection;
            } // don't connect to a new DB
        } // if there is an existing connection
   
        

        String strInFile = propsIn.getProperty(ApplicationConstants.LAST_DB,"");
        File fTemp = null;
        
        if(strInFile.length() > 0)
        {
            fTemp = new File(strInFile);
        } // if file name is longer than zero chars
        // if there was no value for the last database opened in the properties
        // or the named file does not exist
        
        if ((strInFile.length() == 0) 
         || (fTemp.exists() == false) 
         || (bNewConnection == true))
        {
        // Insure that the path is empty then set it to the application path
           String strPath = "";
           try 
           {
              strPath = new File(".").getCanonicalPath();
           } 
           
           catch (IOException ex) 
           {
              Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
           }

        // Set the file name to the application path and default file name   
           String strFileName = strPath + File.separatorChar + ApplicationConstants.DEFAULT_DB_FILE;
           File file = new File(strFileName);
                   JFileChooser myFC = new JFileChooser();
           myFC.setCurrentDirectory(file);
           
        // Set a filter for the JFileChooser to use standard SQLite file extensions   
           FileNameExtensionFilter filter = new FileNameExtensionFilter("SQLite DB", "sqlite", "db", "db2", "db3");
           myFC.addChoosableFileFilter(filter);
           myFC.setFileFilter(filter);
        

       if (myFC.showOpenDialog(jfApp) == JFileChooser.APPROVE_OPTION)
          {
             File fileSelected = myFC.getSelectedFile();
             strInFile = fileSelected.getPath();
             
          } // user chooses a file
        else
       {
           return null;
       }
        
        } // if file name is empty
       
       
        // connect to the database
        Connection cn =  connectToDB(strInFile, propsIn);
        System.out.println("Connected");
        System.out.println(getDBInfo(cn));
        return cn;
               
              
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
           System.out.println(rsMD.getColumnClassName(nLCV));
             System.out.println(rsMD.getColumnLabel(nLCV));
             System.out.println(rsMD.getColumnName(nLCV));
             System.out.println(rsMD.getColumnType(nLCV));
             System.out.println(rsMD.getColumnTypeName(nLCV));
             System.out.println("\n\n");
        } // for
        
        
        while(rsIn.next() == true)
        {
             for (int nLCV = 1; nLCV <= intNumCols; nLCV++)
        {
            switch(rsMD.getColumnType(nLCV))            {
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
    
    
    public String getDBInfo(Connection cnInput)
    {
        StringBuilder sbInfo = new StringBuilder();
        try 
        {
            DatabaseMetaData myMD = cnInput.getMetaData();
            
            sbInfo.append("DB Product Name = " + myMD.getDatabaseProductName()+"\n");
            sbInfo.append("DB Product Version = " + myMD.getDatabaseProductVersion()+ "\n");
          
            // printDBSchemas(myMD);
        } // try
        catch (SQLException ex) 
        {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        } //catch
        
          return sbInfo.toString();
    } // getDBInfo  
    
    
    public void printDBInfo(Connection cnInput)
    {
        try 
        {
            DatabaseMetaData myMD = cnInput.getMetaData();
            
            System.out.println("DB Product Name = " + myMD.getDatabaseProductName());
            System.out.println("DB Product Version = " + myMD.getDatabaseProductVersion());
           
            // printDBSchemas(myMD);
        } // try
        catch (SQLException ex) 
        {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        } //catch
        
        
    } // printDBInfo    
    
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
    
    
    
    public void doPreparedStatementQueryDemo(Connection cnIn)
    {
        try {
            PreparedStatement psAuthor = cnIn.prepareStatement("Select * from Author where LastName = ?");
            psAuthor.setString(1, "Deitel");
            ResultSet rsAuthors = psAuthor.executeQuery();
            
           // printRSMetaData(rsPublishers);
            printResultSet(rsAuthors);
            
        } 
        
        catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
            
    } // doPreparedStatementQueryDemo   
    
        public void doPreparedStatementUpdateDemo(Connection cnIn)
    {
        try {
            PreparedStatement psAuthor = cnIn.prepareStatement("Update Author set CreatedBy = ? where LastName = ?");
            psAuthor.setString(1, "Bill");
            psAuthor.setString(2, "Deitel");
            
            
           psAuthor.executeUpdate();
           System.out.println(psAuthor.getUpdateCount() + " records were updated");
          
        } 
        
        catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
            
    } // doPreparedStatementQueryDemo   
    
   
    
    
    public static void main( String args[] )
  {
/*
      String strDBName = "CourseManagement3.db3";
    DBConnection myDBC = new DBConnection();
    Connection cnMyC = myDBC.connectToDB(strDBName);
    if (cnMyC != null)
    {
    System.out.println("Opened database successfully");
    myDBC.printDBInfo(cnMyC);
        try {
            Statement stmtGradeStandards = cnMyC.createStatement();
           
            ResultSet rsGradeStandards = stmtGradeStandards.executeQuery("Select * from authorisbn ;");
          myDBC.printRSMetaData(rsGradeStandards);
            
    //        myDBC.doPreparedStatementQueryDemo(cnMyC);
   //   myDBC.doPreparedStatementUpdateDemo(cnMyC);
   //                myDBC.doPreparedStatementQueryDemo(cnMyC);
            
            
            
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    
            
        try {
            cnMyC.close();
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        */
        } // main
} // DBConnection class
