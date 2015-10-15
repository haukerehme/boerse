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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hrs
 */
public class FindIndex {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ClassNotFoundException, IOException, SQLException {
        //year - the year minus 1900
        //month - 0 to 11 Achtung Januar -> 0
        //date - 1 to 31
        //hour - 0 to 23
        //minute - 0 to 59
        //second - 0 to 59
        Timestamp gesuchteZeit = new Timestamp(113,0,1,0,0,0,0);
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
        int zaehler = 0;
        while(rs.next()){
            tmp = rs.getTimestamp("zeit");
            if(tmp.equals(gesuchteZeit)){
                System.out.println("Index: " + zaehler);
                break;
            }
            zaehler++;
        }
        
        System.out.print("Arraylänge EURUSD: " + zeit.size());
        //System.out.println("Verbindung wird geschlossen");
        conn.close();
    }
    
}
