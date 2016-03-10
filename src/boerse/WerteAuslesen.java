/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boerse;

import java.io.IOException;
import static java.lang.Thread.sleep;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
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
        double eurusdWert, gpbjpyWert, audusdWert,audusdLetzterWert;
        Calendar cl;
        Timestamp akt;
        Connection conn = null;
        
        String queryEurusd = null;
        //String queryGbpjpy = null;
        String queryAudusd = null;
        
        ArrayList<Integer> closewerte = dbCon.dbEurUsdColumInArrayList();
        //ArrayList<Integer> gbpjpyDiffwerte = dbCon.dbGbpJpyColumInArrayList();
        ArrayList<Integer> audusdDiffwerte = dbCon.dbAudUsdColumInArrayList();
        
        Kursdaten letzterEintragEurUsd = dbCon.lastEntry();
        //Kursdaten letzterEintragGbpJpy = dbCon.lastEntryGbpJpy();
        Kursdaten letzterEintragAudUsd = dbCon.lastEntryAudUsd();
        
        //gbpjpyLetzterWert = letzterEintragGbpJpy.Closewert;
        eurusdLetzterWert = letzterEintragEurUsd.Closewert;
        audusdLetzterWert = letzterEintragAudUsd.Closewert;
        try
        {
          //create a mysql database connection
          String myUrl = "jdbc:mysql://localhost:3306/eurusd";
          Class.forName("com.mysql.jdbc.Driver");
          System.out.println("Verbindungsversuch:");
          conn = DriverManager.getConnection(myUrl, "root", "43mitmilch");
          queryEurusd = " insert into closewerte (zeit,wert)"
            + " values (?, ?)";
          //queryGbpjpy = " insert into gbpjpy (zeit,wert)"
            //+ " values (?, ?)";
          queryAudusd = " insert into audusd (zeit,wert)"
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
            //gpbjpyWert = 0;
            audusdWert = 0;
            try{
                eurusdWert = getAllLive.getClosewert();
                //ToDo: In rechneOperationen
                int diff = (int) (10000*eurusdWert - 10000*eurusdLetzterWert);
                closewerte.add(diff);
                eurusdLetzterWert = eurusdWert;
                
                /*gpbjpyWert = getAllLive.getGBPJPYWert();
                int diffGbpjpy = (int) (100*gpbjpyWert - 100*gbpjpyLetzterWert);
                gbpjpyDiffwerte.add(diffGbpjpy);
                gbpjpyLetzterWert = gpbjpyWert;*/
                
                audusdWert = getAllLive.getAUDUSDWert();
                int diffAudusd = (int) (10000*audusdWert - 10000*audusdLetzterWert);
                audusdDiffwerte.add(diffAudusd);
                audusdLetzterWert = audusdWert;
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
                new InsertLiveValueInNewDb(eurusdWert,akt).start();
            } catch (SQLException ex) {
                logger.loggeWarning("SQL Exception: "+ex.toString());
                Logger.getLogger(WerteAuslesen.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            preparedStmt = null;
            try {
                preparedStmt = conn.prepareStatement(queryAudusd);
                preparedStmt.setTimestamp(1, akt);
                preparedStmt.setDouble(2, audusdWert);
                // execute the preparedstatement
                preparedStmt.execute();
            } catch (SQLException ex) {
                logger.loggeWarning("SQL Exception: "+ex.toString());
                Logger.getLogger(WerteAuslesen.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            //EUR/USD
           /* for(int i= 10; i < 41; i=i+5){
                new RechnerZusammenfasser(closewerte, closewerte.size()-1, 240, i, 30,spread.eurusd,"EUR/USD",false,false).start();
            }
            for(int i= 5; i < 41; i=i+5){
                new RechnerZusammenfasser(closewerte, closewerte.size()-1, 210, i, 30,spread.eurusd,"EUR/USD",false,false).start();
            }
            for(int i= 5; i < 31; i=i+5){
                new RechnerZusammenfasser(closewerte, closewerte.size()-1, 180, i, 20,spread.eurusd,"EUR/USD",false,false).start();
            }
            for(int i= 5; i < 21; i=i+5){
                new RechnerZusammenfasser(closewerte, closewerte.size()-1, 150, i, 10,spread.eurusd,"EUR/USD",false,false).start();
            }
            for(int i= 5; i < 21; i=i+5){
                new RechnerZusammenfasser(closewerte, closewerte.size()-1, 120, i, 10,spread.eurusd,"EUR/USD",false,false).start();
            }
            for(int i= 5; i < 16; i=i+5){
                new RechnerZusammenfasser(closewerte, closewerte.size()-1, 90, i, 10,spread.eurusd,"EUR/USD",false,false).start();
            }
            
            //AUD/USD
            for(int i= 5; i < 41; i=i+5){
                new RechnerZusammenfasser(audusdDiffwerte, audusdDiffwerte.size()-1, 240, i, 30,spread.audusd,"AUD/USD",false,false).start();
            }
            for(int i= 5; i < 41; i=i+5){
                new RechnerZusammenfasser(audusdDiffwerte, audusdDiffwerte.size()-1, 210, i, 30,spread.audusd,"AUD/USD",false,false).start();
            }
            for(int i= 5; i < 31; i=i+5){
                new RechnerZusammenfasser(audusdDiffwerte, audusdDiffwerte.size()-1, 180, i, 20,spread.audusd,"AUD/USD",false,false).start();
            }
            for(int i= 5; i < 21; i=i+5){
                new RechnerZusammenfasser(audusdDiffwerte, audusdDiffwerte.size()-1, 150, i, 10,spread.audusd,"AUD/USD",false,false).start();
            }
            for(int i= 5; i < 21; i=i+5){
                new RechnerZusammenfasser(audusdDiffwerte, audusdDiffwerte.size()-1, 120, i, 10,spread.audusd,"AUD/USD",false,false).start();
            }
            for(int i= 5; i < 16; i=i+5){
                new RechnerZusammenfasser(audusdDiffwerte, audusdDiffwerte.size()-1, 90, i, 10,spread.audusd,"AUD/USD",false,false).start();
            }    */      
            
//            sleep(2000);
            //Gesamtanalyseergebnis der nächsten 20 min
            new AnalyseMehererVergleichsstrecken(akt, closewerte, closewerte.size()-1, null , 20, spread.eurusd, "EUR/USD").start();
//            new AnalyseMehererVergleichsstrecken(audusdDiffwerte, audusdDiffwerte.size()-1, null , 20, spread.audusd, "AUD/USD").start();
            //new AnalyseMehererVergleichsstrecken(closewerte, closewerte.size()-1, List<Integer> Vergleichsstrecken, 20, List<Integer> Zusammenfassintervalle,spread.eurusd,"EUR/USD",true,false).start();        
        }
    }
}
