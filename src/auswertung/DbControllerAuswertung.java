/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auswertung;

import boerse.TradeMessage;
import boerse.WerteAuslesen;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hrs <hauke.rehme-schlueter at hotmail.com>
 */
public class DbControllerAuswertung {
    
    ArrayList<AuswertModel> getTradeMessage() throws IOException, ClassNotFoundException, SQLException{
        ArrayList<AuswertModel> analyseArray = new ArrayList();
        ResultSet rs = null;
        Connection conn = null;
        try
        {
            // create a mysql database connection
            String myUrl = "jdbc:mysql://62.75.142.111:3306/Boerse";
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(myUrl, "root", "43mitmilch");
            String query = "SELECT `timestamp`, `wert`, `anzFound`, `longWin`, `longWinMiddle`, `longWinHigh`, `longWinVeryHigh`, `longLose`, `longLoseHigh`, `shortWin`, `shortWinMiddle`, `shortWinHigh`, `shortWinVeryHigh`, `shortLose`, `shortLoseHigh` FROM `TradeMessage`,`eurusd` WHERE timestamp = zeit";
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            rs = preparedStmt.executeQuery();
            
            } catch (SQLException ex) {
                Logger.getLogger(WerteAuslesen.class.getName()).log(Level.SEVERE, "DbController SQL-Exception", ex);
            }
        //System.out.println("Diff berechnen:");
        AuswertModel tmp;
        Timestamp timestamp;
        double wert;
        int anzFound;
        int longWin;
        int longMiddleWin;
        int longWinHigh;
        int longWinVeryHigh;
        int longLose;
        int longLoseHigh;
        int shortWin;
        int shortWinMiddle;
        int shortWinHigh;
        int shortWinVeryHigh;
        int shortLose;
        int shortLoseHigh;
        TradeMessage tradeMessage;
        while(rs.next()){
            timestamp = rs.getTimestamp("timestamp");
            wert = rs.getDouble("wert");
            anzFound = rs.getInt("anzFound");
            longWin = rs.getInt("longWin");
            longMiddleWin = rs.getInt("longWinMiddle");
            longWinHigh = rs.getInt("longWinHigh");
            longWinVeryHigh = rs.getInt("longWinVeryHigh");
            longLose = rs.getInt("longLose");
            longLoseHigh = rs.getInt("longLoseHigh");
            shortWin = rs.getInt("shortWin");
            shortWinMiddle = rs.getInt("shortWinMiddle");
            shortWinHigh = rs.getInt("shortWinHigh");
            shortWinVeryHigh = rs.getInt("shortWinVeryHigh");
            shortLose = rs.getInt("shortLose");
            shortLoseHigh = rs.getInt("shortLoseHigh");
            tradeMessage = new TradeMessage(timestamp,"EUR/USD",20,anzFound,longWin, longMiddleWin, longWinHigh, longWinVeryHigh, longLose, longLoseHigh, shortWin, shortWinMiddle, shortWinHigh, shortWinVeryHigh, shortLose, shortLoseHigh);
            analyseArray.add(new AuswertModel(wert,tradeMessage));
        }
        conn.close();
        return analyseArray;
        /*double wertAlt = rs.getDouble("wert");
        
        //System.out.println("Ersten Wert ausgelesen");
        double wertNeu;
        int diff;
        while(rs.next()){
            wertNeu = rs.getDouble("wert");
            diff = (int) (10000*wertNeu - 10000*wertAlt);
            closewerte.add(diff);
            wertAlt = wertNeu;
        }
        System.out.print("Arraylänge EURUSD: " + closewerte.size());
        //System.out.println("Verbindung wird geschlossen");
        
        return closewerte;*/
    }

    void insert20MinLong(Timestamp timestamp, int differenz) {
        try
        {
          //create a mysql database connection
          String myUrl = "jdbc:mysql://62.75.142.111:3306/Boerse";
          Class.forName("com.mysql.jdbc.Driver");
          System.out.println("Verbindungsversuch:");
          Connection conn = DriverManager.getConnection(myUrl, "root", "43mitmilch");
          String query = "Update TradeMessage SET timestamp=? , 20minLong= ? Where timestamp = ?";
          PreparedStatement preparedStmt;
          preparedStmt = conn.prepareStatement(query);
          preparedStmt.setTimestamp(1, timestamp);
          preparedStmt.setInt(2, differenz);
          preparedStmt.setTimestamp(3, timestamp);
          preparedStmt.executeUpdate();
          preparedStmt.close();
        }
        catch (Exception e)
        {
          System.err.println("Got an exception!");
          System.err.println(e.getMessage());
        }
        
    }


}
