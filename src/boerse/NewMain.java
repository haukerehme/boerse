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
 * @author hauke
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter("/home/hauke/NetBeansProjects/RaspberryPiBoerse/test.csv",true));
        Kursdaten liveWert = new Kursdaten();
        liveWert.Openwert = 1.2289;
        liveWert.High = 1.2289;
        liveWert.Low = 1.2289;
        liveWert.Closewert= 1.2289;
        String datum = "07.12.2014 00:00:00,1.2289,1.2289,1.2289,1.2289";
        String datumEnde="08.12.2014 00:00:00,1.2289,1.2289,1.2289,1.2289";
        long minute = 60000;
        liveWert.zeitdatum = liveWert.convertZeitDatum(datum);
        int tag = 1440;
        boolean eintragen = true;
        for(int i = 0; i < tag; i++)
        {
            String ausgabe;
            ausgabe = liveWert.zeitdatum.toString().substring(8, 10) +"."+ liveWert.zeitdatum.toString().substring(5, 7)+"."
                                + liveWert.zeitdatum.toString().substring(0, 4);
            //System.out.println(ausgabe + akt.toString().substring(10, 19) + ",  ,  ,  ,"+wert);
            ausgabe = ausgabe + liveWert.zeitdatum.toString().substring(10, 17)+ "00" + ","+ liveWert.Openwert+","+liveWert.High+","+liveWert.Low+","+1.2289;
            bw.write(ausgabe);
            bw.newLine();
            bw.flush();
            liveWert.zeitdatum.setTime( liveWert.zeitdatum.getTime() + minute);

        }
    }
    
}
