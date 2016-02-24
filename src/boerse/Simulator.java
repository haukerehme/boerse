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
 * @author hrs
 */
public class Simulator {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException, SQLException {
        // TODO code application logic here
        Calendar cl = Calendar.getInstance();;
        long startZeit = cl.getTimeInMillis();
        
        ArrayOperationen operation = new ArrayOperationen();
        
        Dateilogger logger = Dateilogger.getInstance();
        Spread spread = new Spread();
//        Rechner rechner = new Rechner();
        Kursdaten tmp = new Kursdaten();
        Converter converter = new Converter();
        ArrayOperationen ao = new ArrayOperationen();
        DatenbankController dbCon = new DatenbankController();
        //ArrayList<Kursdaten> daten = new ArrayList<Kursdaten>();
        LiveClosewertEurUsd liveInstanz = new LiveClosewertEurUsd();
        double letzterWert;
        double wert;
        
        Timestamp akt = new Timestamp(startZeit);
        System.out.println(akt.toString());
        Connection conn = null;
        String query = null;
        //ArrayList<Integer> closewerte = dbCon.dbEurUsdColumInArrayList();
        ArrayList<Integer> closewerte = dbCon.remoteDbEurUsdColumInArrayList();
        Kursdaten letzterEintrag = dbCon.lastEntry();
        letzterWert = letzterEintrag.Closewert;

            //-> Die aktuelle Zeit
            //Wert + Uhrzeit in DB schreiben
            // create the mysql insert preparedstatement
            // the mysql insert statement

            int auswertungsstrecke = 20;
            int vergleichsstrecke = 120;
            int anzZusammenfassen = 10;
            
            Timestamp timestampStart = Timestamp.valueOf("2015-11-09 00:08:59");
            Timestamp timestampEnde = Timestamp.valueOf("2015-11-13 00:17:59");
            
            int simuStartPkt = closewerte.size()-200000;
            ArrayList<Timestamp> timestampArray = dbCon.getEurUsdZeit();
            simuStartPkt = timestampArray.indexOf(timestampStart);
            int simuEndPkt = closewerte.size()-100000-180;
            simuEndPkt = timestampArray.indexOf(timestampEnde);
            
            System.out.println("indexStart: " +simuStartPkt+"\nindexEnde: " + simuEndPkt);
            
            int anzahlGefundenerForm = 10;
            float prozentsatzPositiv = 70;
            
            RechnerZusammenfasser rechner; 
            int entwicklung = 0;
            int tradeErfolg = 0;
            int tradeMisserfolg = 0;
            
            for(int i = simuStartPkt; i < simuEndPkt; i++){
                rechner = new RechnerZusammenfasser( closewerte.subList(0, i), closewerte.subList(0, i).size() -1,vergleichsstrecke,auswertungsstrecke, anzZusammenfassen,spread.eurusd,"EUR/USD",false,true);
                Tradevorhersage trade = rechner.analyse(/*closewerte.subList(0, i), closewerte.subList(0, i).size() -1, vergleichsstrecke, auswertungsstrecke*/);
                if(trade.anzForm > anzahlGefundenerForm){
                    if(trade.wahrscheinlichkeitLong > 70 && trade.wahrscheinlichkeitLongHoherGewinn > 50){
                        
                        for(int z =i+vergleichsstrecke;z < i+vergleichsstrecke+auswertungsstrecke;z++){
                            entwicklung += closewerte.get(z);
                        }
                        if(entwicklung > 2){
                            tradeErfolg++;
                        }
                        if(entwicklung < 2){
                            tradeMisserfolg++;
                        }
                        
                    }
                    if(trade.wahrscheinlichkeitShort > 70 && trade.wahrscheinlichkeitShortHoherGewinn > 50){
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
            long endZeit = cl.getTimeInMillis();
            long gesamtzeit = endZeit-startZeit;
            String ausgabe = "Simuliert wurde Vergleichsstrecke: "+ vergleichsstrecke +"\nAuswertungsstrecke: "+ auswertungsstrecke + "\nProzentsatz: "+ prozentsatzPositiv+" von "+ simuStartPkt+" bis "+simuEndPkt;
            ausgabe = ausgabe + "\nAnzahl gefundener Formationen um zu traden: " + anzahlGefundenerForm + "\nAnzahl Zusammenfasser" + anzZusammenfassen;
            System.out.println(ausgabe + "\nErfolge: "+tradeErfolg+" Misserfolge: "+tradeMisserfolg+ " Gewonnene Punkte: " +entwicklung + "Dauer in millis: " +gesamtzeit);
            cl = Calendar.getInstance();
            akt = new Timestamp(cl.getTimeInMillis());
            System.out.println(akt.toString());
            logger.loggeInDatei( ausgabe + "\nErfolge: "+tradeErfolg+" Misserfolge: "+tradeMisserfolg+ " Gewonnene Punkte: " +entwicklung + "Dauer in millis: " +gesamtzeit, "SimulatorErgebnis.txt");
    }
}
