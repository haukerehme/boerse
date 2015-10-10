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
public class Simulator {
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
        
        
        
//            System.out.println("Arraylänge: " + closewerte.size());
            //System.out.println("Letzter Eintrag: lastDiff: "+ closewerte.get(closewerte.size()-1));
            //System.out.println("Erster Eintrag 20: FirstDiff: "+ closewerte.get(closewerte.size()-20));
            //System.out.println("Erster Eintrag 30: FirstDiff: "+ closewerte.get(closewerte.size()-30));
            //Aktueller Wert
            
            
            //-> Die aktuelle Zeit
            //Wert + Uhrzeit in DB schreiben
            // create the mysql insert preparedstatement
            // the mysql insert statement
          
            
            
            int auswertungsstrecke = 40;
            int vergleichsstrecke = 180;
            int anzZusammenfassen = 10;
            int simuStartPkt = closewerte.size()-200000;
            int simuEndPkt = closewerte.size()-100000-180;
            int anzahlGefundenerForm = 10;
            float prozentsatzPositiv = 70;
            
            RechnerZusammenfasser rechner; 
            int entwicklung = 0;
            int tradeErfolg = 0;
            int tradeMisserfolg = 0;
            for(int i = simuStartPkt; i < simuEndPkt; i++){
                rechner = new RechnerZusammenfasser( closewerte.subList(0, i), closewerte.subList(0, i).size() -1,vergleichsstrecke,auswertungsstrecke, anzZusammenfassen);
                Tradevorhersage trade = rechner.analyse(closewerte.subList(0, i), closewerte.subList(0, i).size() -1, vergleichsstrecke, auswertungsstrecke);
                if(trade.anzForm > anzahlGefundenerForm){
                    if(trade.wahrscheinlichkeitLong > 70){
                        for(int z =i+vergleichsstrecke;z < i+vergleichsstrecke+auswertungsstrecke;z++){
                            entwicklung += closewerte.get(z);
                        }
                        if(entwicklung > -2){
                            tradeErfolg++;
                        }
                        if(entwicklung < 2){
                            tradeMisserfolg++;
                        }
                    }
                    if(trade.wahrscheinlichkeitShort > 70){
                        for(int z =i+vergleichsstrecke;z < i+vergleichsstrecke+auswertungsstrecke;z++){
                            entwicklung += closewerte.get(z);
                        }
                        if(entwicklung < -2){
                            tradeErfolg++;
                        }
                        if(entwicklung > -2){
                            tradeMisserfolg++;
                        }
                    }
                }
            }
            String ausgabe = "Simuliert wurde Vergleichsstrecke: "+ vergleichsstrecke +"\nAuswertungsstrecke: "+ auswertungsstrecke + "\nProzentsatz: "+ prozentsatzPositiv+" von "+ simuStartPkt+" bis "+simuEndPkt;
            ausgabe = ausgabe + "\nAnzahl gefundener Formationen um zu traden: " + anzahlGefundenerForm + "\nAnzahl Zusammenfasser" + anzZusammenfassen;
            logger.loggeInDatei( ausgabe + "\nErfolge: "+tradeErfolg+" Misserfolge: "+tradeMisserfolg+ " Gewonnene Punkte: " +entwicklung, "SimulatorErgebnis.txt");
            
            
            
            
        }
    
}
