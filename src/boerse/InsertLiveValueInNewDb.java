/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boerse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hrs <hauke.rehme-schlueter at hotmail.com>
 */
public class InsertLiveValueInNewDb extends Thread{
   Dateilogger logger = Dateilogger.getInstance();
    double closewert;
    Connection conn;
    String queryEurusd;
    Timestamp timestamp;

    public InsertLiveValueInNewDb(double closewert, Timestamp timestamp) throws ClassNotFoundException, SQLException {
        this.closewert = closewert;
        try{
          //create a mysql database connection
          String myUrl = "jdbc:mysql://localhost:3306/Boerse";
          Class.forName("com.mysql.jdbc.Driver");
          System.out.println("Verbindungsversuch:");
          conn = DriverManager.getConnection(myUrl, "root", "43mitmilch");
          queryEurusd = " insert into eurusd (zeit,wert)"
            + " values (?, ?)";
        }catch (Exception e){
          System.err.println("Got an exception!");
          System.err.println(e.getMessage());
        }
    }
    
    
    
    public void insertIntoBoerseDb(double closewert, Timestamp akt) throws IOException{
        PreparedStatement preparedStmt = null;
            try {
                preparedStmt = conn.prepareStatement(queryEurusd);
                preparedStmt.setTimestamp(1, akt);
                preparedStmt.setDouble(2, closewert);
                // execute the preparedstatement
                preparedStmt.execute();
            } catch (SQLException ex) {
                logger.loggeWarning("SQL Exception: "+ex.toString());
                Logger.getLogger(WerteAuslesen.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
    @Override
    public void run() {
       try {
           insertIntoBoerseDb(this.closewert,this.timestamp);
       } catch (IOException ex) {
           Logger.getLogger(InsertLiveValueInNewDb.class.getName()).log(Level.SEVERE, null, ex);
       }
    }
    
}
