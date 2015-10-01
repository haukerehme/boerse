/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boerse;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author administrator
 */
public class WerteAuslesen {
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException, SQLException {
        // TODO code application logic here
        Dateilogger logger = Dateilogger.getInstance();
//        Rechner rechner = new Rechner();
        Kursdaten tmp = new Kursdaten();
        Converter converter = new Converter();
        ArrayOperationen ao = new ArrayOperationen();
        DatenbankController dbCon = new DatenbankController();
        //ArrayList<Kursdaten> daten = new ArrayList<Kursdaten>();
        LiveClosewertEurUsd liveInstanz = new LiveClosewertEurUsd();
        double letzterWert;
        double wert;
        Calendar cl;
        Timestamp akt;
        Connection conn = null;
        String query = null;
        ArrayList<Integer> closewerte = dbCon.dbColumInArrayList();
        Kursdaten letzterEintrag = dbCon.lastEntry();
        letzterWert = letzterEintrag.Closewert;
        try
        {
          //create a mysql database connection
          String myUrl = "jdbc:mysql://localhost:3306/eurusd";
          Class.forName("com.mysql.jdbc.Driver");
          System.out.println("Verbindungsversuch:");
          conn = DriverManager.getConnection(myUrl, "root", "43mitmilch");
          query = " insert into closewerte (zeit,wert)"
            + " values (?, ?)";
        }
        catch (Exception e)
        {
          System.err.println("Got an exception!");
          System.err.println(e.getMessage());
        }
        
        
        while(true){
            System.out.println("Arraylänge: " + closewerte.size());
            System.out.println("Letzter Eintrag: lastDiff: "+ closewerte.get(closewerte.size()-1));
            //Aktueller Wert
            wert = 0;
            try{
                wert = liveInstanz.getClosewert();
                int diff = (int) (10000*wert - 10000*letzterWert);
                closewerte.add(diff);
                letzterWert = wert;
            }catch(Exception e){
                logger.logge("getCloseWert Fail\n");
                logger.logge(e.toString());
            }
            cl = Calendar.getInstance();
            akt = new Timestamp(cl.getTimeInMillis());
            if(akt.getSeconds() < 59){
                akt.setMinutes(akt.getMinutes()-1);
            }
            akt.setSeconds(59);   
            BufferedWriter bw = new BufferedWriter(new FileWriter("/home/test.csv",true));
            bw.write(akt.toString()+";"+wert);
            bw.newLine();
            bw.flush();
            //-> Die aktuelle Zeit
            //Wert + Uhrzeit in DB schreiben
            // create the mysql insert preparedstatement
            // the mysql insert statement
          
            PreparedStatement preparedStmt = null;
            try {
                preparedStmt = conn.prepareStatement(query);
                preparedStmt.setTimestamp(1, akt);
                preparedStmt.setDouble(2, wert);
                // execute the preparedstatement
                preparedStmt.execute();
            } catch (SQLException ex) {
                logger.loggeWarning("SQL Exception: "+ex.toString());
                Logger.getLogger(WerteAuslesen.class.getName()).log(Level.SEVERE, null, ex);
            }
            new Rechner(closewerte, closewerte.size()-1, 5, 5).start();
            //rechner.unterschiedsVergleicher(closewerte, closewerte.size()-1, 5, 5);
        }
    }
}
