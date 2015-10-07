/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boerse;

import java.util.ArrayList;
import java.util.List;

/**
 * Dieser Rechner schaut, ob eine Formation immer entgegengesetzte Steigungen zu der aktuellen Formation hat ;)
 * @author hrs
 */
public class RechnerAntiVZ extends Thread{
    Dateilogger logger = Dateilogger.getInstance();
    Rechner rechner = new Rechner();
    ArrayList<Integer> closewerte;
    int ausgangspkt,length,auswertungslaenge;
    boolean longPosition;
    
    RechnerAntiVZ(ArrayList<Integer> intArray,int ausgangspkt,int length,int auswertungslaenge){
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
            if((intArray.get(i) <= 0 && akt.get(0) <= 0) || (intArray.get(i) >= 0 && akt.get(0) >= 0)){
                anzErsterRight++;
                formFound = true;
                for(int z = 1; z < akt.size();z++){   
                    if((intArray.get(z+i) <= 0 && akt.get(z) <= 0) || (intArray.get(z+i) >= 0 && akt.get(z) >= 0)){
                        formFound = false;
                        break;
                    }
                }
                if(formFound){
                    anzFormFound++;
                    
                    int entwicklung = 0;
                    for(int z =i;z < i+this.auswertungslaenge;z++){
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