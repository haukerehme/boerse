/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boerse;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import static java.lang.Thread.sleep;
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
        Spread spread = new Spread();
//        Rechner rechner = new Rechner();
        Kursdaten tmp = new Kursdaten();
        Converter converter = new Converter();
        ArrayOperationen ao = new ArrayOperationen();
        DatenbankController dbCon = new DatenbankController();
        //ArrayList<Kursdaten> daten = new ArrayList<Kursdaten>();
        LiveClosewertEurUsd liveInstanz = new LiveClosewertEurUsd();
        GetAllLiveClosewerte getAllLive = new GetAllLiveClosewerte();
        double eurusdLetzterWert, gbpjpyLetzterWert;
        double eurusdWert, gpbjpyWert;
        Calendar cl;
        Timestamp akt;
        Connection conn = null;
        
        String queryEurusd = null;
        String queryGbpjpy = null;
        
        ArrayList<Integer> closewerte = dbCon.dbEurUsdColumInArrayList();
        ArrayList<Integer> gbpjpyDiffwerte = dbCon.dbGbpJpyColumInArrayList();
        
        Kursdaten letzterEintragEurUsd = dbCon.lastEntry();
        Kursdaten letzterEintragGbpJpy = dbCon.lastEntryGbpJpy();
        
        gbpjpyLetzterWert = letzterEintragGbpJpy.Closewert;
        eurusdLetzterWert = letzterEintragEurUsd.Closewert;
        try
        {
          //create a mysql database connection
          String myUrl = "jdbc:mysql://localhost:3306/eurusd";
          Class.forName("com.mysql.jdbc.Driver");
          System.out.println("Verbindungsversuch:");
          conn = DriverManager.getConnection(myUrl, "root", "43mitmilch");
          queryEurusd = " insert into closewerte (zeit,wert)"
            + " values (?, ?)";
          queryGbpjpy = " insert into gbpjpy (zeit,wert)"
            + " values (?, ?)";
        }
        catch (Exception e)
        {
          System.err.println("Got an exception!");
          System.err.println(e.getMessage());
        }
        
        
        while(true){
//            System.out.println("Arraylänge: " + closewerte.size());
            //System.out.println("Letzter Eintrag: lastDiff: "+ closewerte.get(closewerte.size()-1));
            //System.out.println("Erster Eintrag 20: FirstDiff: "+ closewerte.get(closewerte.size()-20));
            //System.out.println("Erster Eintrag 30: FirstDiff: "+ closewerte.get(closewerte.size()-30));
            //Aktueller Wert
            eurusdWert = 0;
            gpbjpyWert = 0;
            try{
                eurusdWert = liveInstanz.getClosewert();
                int diff = (int) (10000*eurusdWert - 10000*eurusdLetzterWert);
                closewerte.add(diff);
                eurusdLetzterWert = eurusdWert;
                
                gpbjpyWert = getAllLive.getGBPJPYWert();
                int diffGbpjpy = (int) (100*gpbjpyWert - 100*gbpjpyLetzterWert);
                gbpjpyDiffwerte.add(diffGbpjpy);
                gbpjpyLetzterWert = gpbjpyWert;
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
            
            /*BufferedWriter bw = new BufferedWriter(new FileWriter("/home/test.csv",true));
            bw.write(akt.toString()+";"+eurusdWert);
            bw.newLine();
            bw.flush();*/
            //-> Die aktuelle Zeit
            //Wert + Uhrzeit in DB schreiben
            // create the mysql insert preparedstatement
            // the mysql insert statement
          
            PreparedStatement preparedStmt = null;
            try {
                preparedStmt = conn.prepareStatement(queryEurusd);
                preparedStmt.setTimestamp(1, akt);
                preparedStmt.setDouble(2, eurusdWert);
                // execute the preparedstatement
                preparedStmt.execute();
            } catch (SQLException ex) {
                logger.loggeWarning("SQL Exception: "+ex.toString());
                Logger.getLogger(WerteAuslesen.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            preparedStmt = null;
            try {
                preparedStmt = conn.prepareStatement(queryGbpjpy);
                preparedStmt.setTimestamp(1, akt);
                preparedStmt.setDouble(2, gpbjpyWert);
                // execute the preparedstatement
                preparedStmt.execute();
            } catch (SQLException ex) {
                logger.loggeWarning("SQL Exception: "+ex.toString());
                Logger.getLogger(WerteAuslesen.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            for(int i= 5; i < 41; i=i+5){
                new RechnerZusammenfasser(closewerte, closewerte.size()-1, 240, i, 30,spread.eurusd,"EUR/USD").start();
            }
            
            sleep(2000);
//            System.out.println("-");
            for(int i= 5; i < 41; i=i+5){
                new RechnerZusammenfasser(closewerte, closewerte.size()-1, 180, i, 20,spread.eurusd,"EUR/USD").start();
            }
            
            sleep(2000);
//            System.out.println("-");
            for(int i= 5; i < 31; i=i+5){
                new RechnerZusammenfasser(closewerte, closewerte.size()-1, 150, i, 10,spread.eurusd,"EUR/USD").start();
            }
           
            sleep(2000);
//            System.out.println("-");
            for(int i= 5; i < 31; i=i+5){
                new RechnerZusammenfasser(closewerte, closewerte.size()-1, 120, i, 10,spread.eurusd,"EUR/USD").start();
            }
            
            sleep(2000);
//            System.out.println("-");
            for(int i= 5; i < 21; i=i+5){
                new RechnerZusammenfasser(closewerte, closewerte.size()-1, 90, i, 10,spread.eurusd,"EUR/USD").start();
            }
            
            //GBP/JPY
            sleep(2000);
            for(int i= 5; i < 41; i=i+5){
                new RechnerZusammenfasser(gbpjpyDiffwerte, gbpjpyDiffwerte.size()-1, 240, i, 30,spread.gbpjpy,"GBP/JPY").start();
            }
            
            sleep(2000);
//            System.out.println("-");
            for(int i= 5; i < 41; i=i+5){
                new RechnerZusammenfasser(gbpjpyDiffwerte, gbpjpyDiffwerte.size()-1, 180, i, 20,spread.gbpjpy,"GBP/JPY").start();
            }
            
            sleep(2000);
//            System.out.println("-");
            for(int i= 5; i < 31; i=i+5){
                new RechnerZusammenfasser(gbpjpyDiffwerte, gbpjpyDiffwerte.size()-1, 150, i, 10,spread.gbpjpy,"GBP/JPY").start();
            }
           
            sleep(2000);
//            System.out.println("-");
            for(int i= 5; i < 31; i=i+5){
                new RechnerZusammenfasser(gbpjpyDiffwerte, gbpjpyDiffwerte.size()-1, 120, i, 10,spread.gbpjpy,"GBP/JPY").start();
            }
            
            sleep(2000);
//            System.out.println("-");
            for(int i= 5; i < 21; i=i+5){
                new RechnerZusammenfasser(gbpjpyDiffwerte, gbpjpyDiffwerte.size()-1, 90, i, 10,spread.gbpjpy,"GBP/JPY").start();
            }
        }
    }
}
