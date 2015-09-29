/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package boerse;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hauke
 */
public class Converter {
    Run runInstanz = Run.getInstance();
    Dateilogger logger = Dateilogger.getInstance();
    private Object DatabaseManager;
    void arrayincsv(ArrayList<Kursdaten> liste, String dateiname)
    {    
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(dateiname));
            for(int i = 0; i< liste.size();i++)
            {
                String akt = liste.get(i).zeitdatum.toString();
                String ausgabe;
                        ausgabe = akt.toString().substring(8, 10) +"."+ akt.toString().substring(5, 7)+"."
                                + akt.toString().substring(0, 4);
                ausgabe = ausgabe + akt.toString().substring(10, 19) + "," + liste.get(i).Openwert+","
                        + liste.get(i).High + ","+liste.get(i).Low + ","+ liste.get(i).Closewert + "," 
                        + liste.get(i).Volumen;
                out.write(ausgabe);
                out.newLine();
            }
            out.flush();
            } catch (IOException e) {
		e.printStackTrace();
	}
    } 
    
    ArrayList<Kursdaten> csvinarray(String dateiname) throws IOException
    {
        ArrayList<Kursdaten> daten = new ArrayList<>();
        
        try {
            FileReader fr = new FileReader("/home/pi/RaspberryPiBoerse/"+dateiname);
            logger.logge("FileReader erfolgreich initialisiert!");
            BufferedReader in = new BufferedReader(fr);
            String zeile;
            logger.logge("Bufferedreader erfolgreich initialisiert!");
            //Time,Open,High,Low,Close,Volume
            while ((zeile = in.readLine()) != null) {
                Kursdaten tmp = new Kursdaten(zeile);
                daten.add(tmp);
            }
	} catch (IOException e) {
            logger.logge("catch IOException in Converter.csvinarray");
		e.printStackTrace();
	}
        return daten;
    }
    
    
    
    ArrayList<Integer> csvinIntArray(String dateiname) throws IOException
    {
        ArrayList<Integer> daten = new ArrayList<>();
        
        try {
            //FileReader fr = new FileReader("/home/pi/RaspberryPiBoerse_v1-1/"+dateiname);
            FileReader fr = new FileReader(dateiname);
            logger.logge("FileReader erfolgreich initialisiert!");
            BufferedReader in = new BufferedReader(fr);
            String zeile;
            logger.logge("Bufferedreader erfolgreich initialisiert!");
            double wertNeu,wertAlt;
            zeile = in.readLine();
            String[] splitResult1 = zeile.split(",");
            wertNeu = Double.parseDouble(splitResult1[4]);
            wertNeu = Math.round(wertNeu * 10000d) /10000d;
            wertAlt = wertNeu;
            //Time,Open,High,Low,Close,Volume
            while ((zeile = in.readLine()) != null) {
                //Kursdaten tmp = new Kursdaten(zeile);
                //daten.add(tmp);
                String[] splitResult = zeile.split(",");
                wertNeu = Double.parseDouble(splitResult[4]);
                wertNeu = Math.round(wertNeu * 10000d) /10000d;
                int differenz = (int)(10000*wertNeu-10000*wertAlt);
                daten.add((int)(differenz));
                wertAlt = wertNeu;
            }
            runInstanz.setLetzterWert(wertAlt);
	} catch (IOException e) {
            logger.logge("catch IOException in Converter.csvinarray");
		e.printStackTrace();
	}
        return daten;
    }
    
}
