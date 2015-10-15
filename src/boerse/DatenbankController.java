/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boerse;

import static boerse.RaspberryPiBoerse.logger;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author administrator
 */
public class DatenbankController {
    
    ArrayList<Integer> dbEurUsdColumInArrayList() throws IOException, ClassNotFoundException, SQLException{
        ArrayList<Integer> closewerte = new ArrayList();
        ResultSet rs = null;
        Connection conn = null;
        try
        {
            // create a mysql database connection
            String myUrl = "jdbc:mysql://localhost:3306/eurusd";
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(myUrl, "root", "43mitmilch");
            String query = "Select wert From closewerte";
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            rs = preparedStmt.executeQuery();
            
            } catch (SQLException ex) {
                logger.loggeWarning("SQL Exception: "+ex.toString());
                Logger.getLogger(WerteAuslesen.class.getName()).log(Level.SEVERE, null, ex);
            }
        //System.out.println("Diff berechnen:");
        rs.next();
        double wertAlt = rs.getDouble("wert");
        
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
        conn.close();
        return closewerte;
    }
    
    ArrayList<Integer> dbGbpJpyColumInArrayList() throws IOException, ClassNotFoundException, SQLException{
        ArrayList<Integer> closewerte = new ArrayList();
        ResultSet rs = null;
        Connection conn = null;
        try
        {
            // create a mysql database connection
            String myUrl = "jdbc:mysql://localhost:3306/eurusd";
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(myUrl, "root", "43mitmilch");
            String query = "Select wert From gbpjpy";
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            //System.out.println("Resultset:");
            rs = preparedStmt.executeQuery();
            
            } catch (SQLException ex) {
                logger.loggeWarning("SQL Exception: "+ex.toString());
                Logger.getLogger(WerteAuslesen.class.getName()).log(Level.SEVERE, null, ex);
            }
        //System.out.println("Diff berechnen:");
        rs.next();
        double wertAlt = rs.getDouble("wert");
        
        //System.out.println("Ersten Wert ausgelesen");
        double wertNeu;
        int diff;
        while(rs.next()){//RUNDEN!!!!
            wertNeu = rs.getDouble("wert");
            diff = (int) (100*wertNeu - 100*wertAlt);
            closewerte.add(diff);
            wertAlt = wertNeu;
        }
        System.out.print("Arraylänge GBPJPY: " + closewerte.size());
        //System.out.println("Verbindung wird geschlossen");
        conn.close();
        return closewerte;
    }
    
    ArrayList<Integer> dbAudUsdColumInArrayList() throws IOException, ClassNotFoundException, SQLException{
        ArrayList<Integer> closewerte = new ArrayList();
        ResultSet rs = null;
        Connection conn = null;
        try
        {
            // create a mysql database connection
            String myUrl = "jdbc:mysql://localhost:3306/eurusd";
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(myUrl, "root", "43mitmilch");
            String query = "Select wert From audusd";
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            //System.out.println("Resultset:");
            rs = preparedStmt.executeQuery();
            
            } catch (SQLException ex) {
                logger.loggeWarning("SQL Exception: "+ex.toString());
                Logger.getLogger(WerteAuslesen.class.getName()).log(Level.SEVERE, null, ex);
            }
        //System.out.println("Diff berechnen:");
        rs.next();
        double wertAlt = rs.getDouble("wert");
        
        //System.out.println("Ersten Wert ausgelesen");
        double wertNeu;
        int diff;
        while(rs.next()){//RUNDEN!!!!
            wertNeu = rs.getDouble("wert");
            diff = (int) (10000*wertNeu - 10000*wertAlt);
            closewerte.add(diff);
            wertAlt = wertNeu;
        }
        System.out.print("Arraylänge AUDUSD: " + closewerte.size());
        //System.out.println("Verbindung wird geschlossen");
        conn.close();
        return closewerte;
    }
    
    public Kursdaten lastEntry() throws ClassNotFoundException, IOException, SQLException{
        Kursdaten kd = new Kursdaten();
        ResultSet rs = null;
        Connection conn = null;
        try
        {
            // create a mysql database connection
            String myUrl = "jdbc:mysql://localhost:3306/eurusd";
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(myUrl, "root", "43mitmilch");
            String query = "Select wert,zeit From closewerte order by zeit DESC Limit 1";
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            rs = preparedStmt.executeQuery();
            rs.next();
            kd.Closewert = rs.getDouble("wert");
            kd.zeitdatum = rs.getTimestamp("zeit"); 
        } catch (SQLException ex) {
            logger.loggeWarning("SQL Exception: "+ex.toString());
            Logger.getLogger(WerteAuslesen.class.getName()).log(Level.SEVERE, null, ex);
        }
        conn.close();
        return kd;
    }
    
    public Kursdaten lastEntryGbpJpy() throws ClassNotFoundException, IOException, SQLException{
        Kursdaten kd = new Kursdaten();
        ResultSet rs = null;
        Connection conn = null;
        try
        {
            // create a mysql database connection
            String myUrl = "jdbc:mysql://localhost:3306/eurusd";
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(myUrl, "root", "43mitmilch");
            String query = "Select wert,zeit From gbpjpy order by zeit DESC Limit 1";
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            rs = preparedStmt.executeQuery();
            rs.next();
            kd.Closewert = rs.getDouble("wert");
            kd.zeitdatum = rs.getTimestamp("zeit"); 
        } catch (SQLException ex) {
            logger.loggeWarning("SQL Exception: "+ex.toString());
            Logger.getLogger(WerteAuslesen.class.getName()).log(Level.SEVERE, null, ex);
        }
        conn.close();
        return kd;
    }
    
    public Kursdaten lastEntryAudUsd() throws ClassNotFoundException, IOException, SQLException{
        Kursdaten kd = new Kursdaten();
        ResultSet rs = null;
        Connection conn = null;
        try
        {
            // create a mysql database connection
            String myUrl = "jdbc:mysql://localhost:3306/eurusd";
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(myUrl, "root", "43mitmilch");
            String query = "Select wert,zeit From audusd order by zeit DESC Limit 1";
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            rs = preparedStmt.executeQuery();
            rs.next();
            kd.Closewert = rs.getDouble("wert");
            kd.zeitdatum = rs.getTimestamp("zeit"); 
        } catch (SQLException ex) {
            logger.loggeWarning("SQL Exception: "+ex.toString());
            Logger.getLogger(WerteAuslesen.class.getName()).log(Level.SEVERE, null, ex);
        }
        conn.close();
        return kd;
    }
    
    ArrayList<Timestamp> getEurUsdZeit() throws IOException, ClassNotFoundException, SQLException{
        ArrayList<Timestamp> zeit = new ArrayList();
        ResultSet rs = null;
        Connection conn = null;
        try
        {
            // create a mysql database connection
            String myUrl = "jdbc:mysql://localhost:3306/eurusd";
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(myUrl, "root", "43mitmilch");
            String query = "Select zeit From closewerte";
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            rs = preparedStmt.executeQuery();
            
            } catch (SQLException ex) {
                logger.loggeWarning("SQL Exception: "+ex.toString());
                Logger.getLogger(WerteAuslesen.class.getName()).log(Level.SEVERE, null, ex);
            }
        //System.out.println("Diff berechnen:");
        
        Timestamp tmp;
        
        //System.out.println("Ersten Wert ausgelesen");
        double wertNeu;
        int diff;
        while(rs.next()){
            tmp = rs.getTimestamp("zeit");
            zeit.add(tmp);
        }
        System.out.print("Arraylänge EURUSD: " + zeit.size());
        //System.out.println("Verbindung wird geschlossen");
        conn.close();
        return zeit;
    }
}
