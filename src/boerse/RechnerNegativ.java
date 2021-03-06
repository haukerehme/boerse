
package boerse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Rechner schaut nach Formationen die komplett von der aktuellen abweichen.
 * @author hrs
 */

public class RechnerNegativ extends Thread{
    Dateilogger logger = Dateilogger.getInstance();
    Rechner rechner = new Rechner();
    ArrayList<Integer> closewerte;
    int ausgangspkt,length,auswertungslaenge;
    boolean longPosition;
    
    RechnerNegativ(ArrayList<Integer> intArray,int ausgangspkt,int length,int auswertungslaenge){
        closewerte = intArray;
        this.ausgangspkt =ausgangspkt;
        this.length = length;
        this.auswertungslaenge = auswertungslaenge;
    }
    
    List<Integer> getAnalyseArray(int length){
        List<Integer> tmp = closewerte.subList(closewerte.size()-(length), closewerte.size()-1);
        
        return tmp;
    }
    
    void analyse(ArrayList<Integer> intArray,int ausgangspkt,int length,int auswertungslaenge){
        
        int GewinnzaehlerLong = 0;
        int VerlustzaehlerLong = 0;
        int GewinnzaehlerShort = 0;
        int VerlustzaehlerShort = 0;
        int anzFormFound = 0;
        int anzErsterRight =0;
        boolean formFound;
        List<Integer> akt = getAnalyseArray(length);
        for(int i = 0; i < intArray.size()-(length);i++){      
            if(!((intArray.get(i) - akt.get(0)) == 0 
                            /*|| (intArray.get(i) - akt.get(0)) == 1 
                            || (intArray.get(i) - akt.get(0)) == -1*/)){
                anzErsterRight++;
                formFound = true;
                for(int z = 1; z < akt.size();z++){                    
                    if((intArray.get(z+i) - akt.get(z)) == 0 
                            /*|| (intArray.get(i+z) - akt.get(z)) == 1 
                            || (intArray.get(i+z) - akt.get(z)) == -1*/){
                        formFound = false;
                        break;
                    }
                }
                if(formFound){
                    anzFormFound++;
                    
                    int entwicklung = 0;
                    for(int z =i+this.length;z < i+this.length+this.auswertungslaenge;z++){
                        entwicklung += intArray.get(z);
                    }   
                    
                        //bei Gewinn wird 1 zurückgegeben, bei Verlust 2 und wenn es gleich geblieben ist 0.
                        if(entwicklung > 2)
                        {
                            //System.out.println("Gewinn " + alledaten.get(ausgangswert).zeitdatum + " " + alledaten.get(ausgangswert).Closewert);
                            GewinnzaehlerLong++;
                        }

                        if(entwicklung < 2){
                            //System.out.println("Verlust " + alledaten.get(ausgangswert).zeitdatum + " " + alledaten.get(ausgangswert).Closewert);
                            VerlustzaehlerLong++;
                        }
                    
                    
                        //bei Gewinn wird 1 zurückgegeben, bei Verlust 2 und wenn es gleich geblieben ist 0.
                        if(entwicklung < -2)
                        {
                            //System.out.println("Gewinn " + alledaten.get(ausgangswert).zeitdatum + " " + alledaten.get(ausgangswert).Closewert);
                            GewinnzaehlerShort++;
                        }

                        if(entwicklung > -2){
                            //System.out.println("Verlust " + alledaten.get(ausgangswert).zeitdatum + " " + alledaten.get(ausgangswert).Closewert);
                            VerlustzaehlerShort++;
                        }
                    
                }
            }
        }
        System.out.println("Formation "+anzFormFound+" mal gefunden!");
        //System.out.println("Ersten "+anzErsterRight+" mal gefunden!");
        if(anzFormFound>10 /*&& (GewinnzaehlerLong > VerlustzaehlerLong*2 || GewinnzaehlerShort > VerlustzaehlerShort*2)*/){
            System.out.println("Achtung bitte verkehrtherum traden!!!!");
            System.out.println("Long: Letzten "+this.length+" Minuten. Gewinn "+GewinnzaehlerLong+" mal und Verlust "+VerlustzaehlerLong+" mal gefunden nach "+this.auswertungslaenge+" Minuten!");
            System.out.println("Short: Letzten "+this.length+" Minuten. Gewinn "+GewinnzaehlerShort+" mal und Verlust "+VerlustzaehlerShort+" mal gefunden nach "+this.auswertungslaenge+" Minuten!");

            //System.out.println("Long: Geschaut auf die letzten "+this.length+" Minuten. Gewinn "+GewinnzaehlerLong+" mal gefunden nach "+this.auswertungslaenge+" Minuten!");
            //System.out.println("Long: Geschaut auf die letzten "+this.length+" Minuten. Verlust "+VerlustzaehlerLong+" mal gefunden nach "+this.auswertungslaenge+" Minuten!");

            //System.out.println("Short: Geschaut auf die letzten "+this.length+" Minuten. Gewinn "+GewinnzaehlerShort+" mal gefunden nach "+this.auswertungslaenge+" Minuten!");
            //System.out.println("Short: Geschaut auf die letzten "+this.length+" Minuten. Verlust "+VerlustzaehlerShort+" mal gefunden nach "+this.auswertungslaenge+" Minuten!");
        }
      
    }
    
    @Override
    public void run() {
        analyse(this.closewerte,this.ausgangspkt,this.length,this.auswertungslaenge);
    }
}