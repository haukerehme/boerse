/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package boerse;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;

/**
 *
 * @author hauke
 */
public class Run {
    Dateilogger logger = Dateilogger.getInstance();
//    Rechner rechner = new Rechner();
    Kursdaten tmp = new Kursdaten();
    Converter converter = new Converter();
    ArrayOperationen ao = new ArrayOperationen();
    //ArrayList<Kursdaten> daten = new ArrayList<Kursdaten>();
    LiveClosewertEurUsd liveInstanz = new LiveClosewertEurUsd();
    double letzterWert;

    public double getLetzterWert() {
        return letzterWert;
    }

    public void setLetzterWert(double letzterWert) {
        this.letzterWert = letzterWert;
    }
    long minute = 60000;
    long stunde = 60 * 60000;
    
    boolean initEurUsd(ArrayList<Kursdaten> daten,long zeitsprunginmillis)
    {
        //daten = converter.csvinarray(dateiname);
       /* if(ao.checkArray(daten, zeitsprunginmillis,dateiname)==false)
        {return false;}*/
        return (ao.isArrayLive(daten, zeitsprunginmillis));
    }  
    
    void run(String dateiname, ArrayList<Integer> daten ,  int vergleichslaenge,int auswertungsstecke) throws IOException, InterruptedException
    {
        
        //if(initEurUsd(daten,zeitsprunginmillis)){
        if(true){
            logger.logge("Gehe in die Whileschleife");
            System.out.println("Daten ist live");
            
            while(true){
                double wertNeu = liveInstanz.getClosewert();
                int differenz = (int)(10000*wertNeu-10000*letzterWert);
                daten.add(differenz);
                logger.logge("Rechne");
                System.out.println("Rechne");
//                rechner.unterschiedsVergleicher(daten, daten.size()-1, vergleichslaenge,auswertungsstecke);
                //rechner.unterschiedsVergleicher(daten, daten.size()-1, vergleichslaenge*2);
            }
            
        }
        else{
            logger.logge("Daten nicht live");
            //log.info("Array ist nicht live");
            System.out.println("Daten nicht live");
            //daten.add(liveInstanz.getClosewert());
        }
        
    }
    
    private Run() {
        
    }
    
    public static Run getInstance() {
        return RunHolder.INSTANCE;
    }
    
    private static class RunHolder{

        private static final Run INSTANCE = new Run();
    }
}
