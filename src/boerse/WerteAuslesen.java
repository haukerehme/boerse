/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boerse;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Calendar;

/**
 *
 * @author administrator
 */
public class WerteAuslesen {
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        // TODO code application logic here
        Dateilogger logger = Dateilogger.getInstance();
        Rechner rechner = new Rechner();
        Kursdaten tmp = new Kursdaten();
        Converter converter = new Converter();
        ArrayOperationen ao = new ArrayOperationen();
        //ArrayList<Kursdaten> daten = new ArrayList<Kursdaten>();
        LiveClosewertEurUsd liveInstanz = new LiveClosewertEurUsd();
        double letzterWert;
        
        while(true){
            
            //Aktueller Wert
            double wertNeu = 0;
            try{
                wertNeu = liveInstanz.getClosewert();
            }catch(Exception e){
                logger.logge("getCloseWert Fail\n");
                logger.logge(e.toString());
            }
            Calendar cl = Calendar.getInstance();
            Timestamp akt = new Timestamp(cl.getTimeInMillis());
            if(akt.getSeconds() < 59){
                akt.setMinutes(akt.getMinutes()-1);
            }
            akt.setSeconds(59);   
            BufferedWriter bw = new BufferedWriter(new FileWriter("/home/test.csv",true));
            bw.write(akt.toString()+";"+wertNeu);
            bw.newLine();
            bw.flush();
            //-> Die aktuelle Zeit
            //Wert + Uhrzeit in DB schreiben
        }      
    }
    
}
