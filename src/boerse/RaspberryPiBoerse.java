/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package boerse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;
/**
 *
 * @author hauke
 */
public class RaspberryPiBoerse {    
    static long stunde = 60 * 60000;
    static long minute = 60000;
    static Converter converter = new Converter();
    static Rechner rechner = new Rechner();
    static ArrayOperationen ao = new ArrayOperationen();
    static Dateilogger logger = Dateilogger.getInstance();
    static int analysestrecke = 120;
    static int auswertungstrecke = 30;
    /**
     * @param args the command line arguments
     */
    /*
        Unterschied zum Vorgänger:
        Es wird mit einem Array gearbeitet der nur die Differenz zum Vorgänger enthält und nicht mehr mit
        einem Array voller Kursdaten!
        ToDo:
        CsvinArray neu
        Rechner neu, da jetzt int-Array
    */
    
    public static void main(String[] args) throws IOException, InterruptedException{
        ArrayList<Integer> daten = new ArrayList<>();
        String dateiname = "EURUSD_Candlestick_1_m_BID_31.12.2013-07.12.2014.csv";
        //String dateiname = "EURUSD_Candlestick_1_m_BID_18.08.2014-18.08.2014.csv";
        daten = converter.csvinIntArray(dateiname);
        logger.logge("Arraylänge: " + daten.size());
        Run runInstanz = Run.getInstance();
        runInstanz.run(dateiname,daten,analysestrecke,auswertungstrecke);
    }
}
