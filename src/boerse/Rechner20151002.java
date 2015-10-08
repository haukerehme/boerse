/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boerse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hrs
 */
public class Rechner20151002 extends Thread{
    Dateilogger logger = Dateilogger.getInstance();
    Rechner rechner = new Rechner();
    ArrayList<Integer> closewerte;
    int ausgangspkt,length,auswertungslaenge;
    boolean longPosition;
    
    Rechner20151002(ArrayList<Integer> intArray,int ausgangspkt,int length,int auswertungslaenge){
        closewerte = intArray;
        this.ausgangspkt =ausgangspkt;
        this.length = length;
        this.auswertungslaenge = auswertungslaenge;
    }
    
    List<Integer> getAnalyseArray(int length){
        List<Integer> tmp = closewerte.subList(closewerte.size()-(length+1), closewerte.size()-1);
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
        System.out.println((intArray.get(0) - intArray.get((intArray.size()-length))));
        for(int i = 0; i < intArray.size()-(length+1); i++){      
            /*if((intArray.get(i) - akt.get(0)) == 0 
                            || (intArray.get(i) - akt.get(0)) == 1 
                            || (intArray.get(i) - akt.get(0)) == -1)*/
            formFound = true;
            if(((intArray.get(i) - intArray.get(intArray.size()-length)) == 0 
                            || (intArray.get(i) - intArray.get(intArray.size()-length)) == 1 
                            || (intArray.get(i) - intArray.get(intArray.size()-length)) == -1
                            || (intArray.get(i) - intArray.get(intArray.size()-length)) == 2 
                            || (intArray.get(i) - intArray.get(intArray.size()-length)) == -2)){
//                System.out.println((intArray.get(i) - intArray.get(intArray.size()-length)));
                anzErsterRight++;
                for(int z = 1; z < length;z++){                    
//                    if((intArray.get(z+i) - akt.get(z)) == 0 
//                            || (intArray.get(i+z) - akt.get(z)) == 1 
//                            || (intArray.get(i+z) - akt.get(z)) == -1)
                        
                    if((intArray.get(z+i) - intArray.get((intArray.size()-length)+z)) != 0 
                            && (intArray.get(i+z) - intArray.get((intArray.size()-length)+z)) != 1 
                            && (intArray.get(i+z) - intArray.get((intArray.size()-length)+z)) != -1
                            && (intArray.get(i+z) - intArray.get(intArray.size()-length)+z) != 2 
                            && (intArray.get(i+z) - intArray.get(intArray.size()-length)+z) != -2){
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
                        if(entwicklung > 2){
                            //System.out.println("Gewinn " + alledaten.get(ausgangswert).zeitdatum + " " + alledaten.get(ausgangswert).Closewert);
                            GewinnzaehlerLong++;
                        }

                        if(entwicklung < 2){
                            //System.out.println("Verlust " + alledaten.get(ausgangswert).zeitdatum + " " + alledaten.get(ausgangswert).Closewert);
                            VerlustzaehlerLong++;
                        }
                    
                    
                        //bei Gewinn wird 1 zurückgegeben, bei Verlust 2 und wenn es gleich geblieben ist 0.
                        if(entwicklung < -2){
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
        
        //System.out.println("Ersten "+anzErsterRight+" mal gefunden!");
        System.out.println("Formation "+anzFormFound+" mal gefunden!");
        if(anzFormFound>5 /*&& (GewinnzaehlerLong > VerlustzaehlerLong*2 || GewinnzaehlerShort > VerlustzaehlerShort*2)*/){
//            System.out.println("Prozentgewinn "+ ((GewinnzaehlerLong/anzFormFound)*100));
//                System.out.println("Formation "+anzFormFound+" mal gefunden!");
//            System.out.println("Formation "+anzFormFound+" mal gefunden!");
            System.out.println("Long: Letzten "+this.length+" Minuten. Gewinn "+GewinnzaehlerLong+" mal und Verlust "+VerlustzaehlerLong+" mal gefunden nach "+this.auswertungslaenge+" Minuten!");
            //System.out.println("Long: Letzten "+this.length+" Minuten. Verlust "+VerlustzaehlerLong+" mal gefunden nach "+this.auswertungslaenge+" Minuten!");
            System.out.println("Short: Letzten "+this.length+" Minuten. Gewinn "+GewinnzaehlerShort+" mal und Verlust "+VerlustzaehlerShort+" mal gefunden nach "+this.auswertungslaenge+" Minuten!");
            //System.out.println("Short: Letzten "+this.length+" Minuten. Verlust "+VerlustzaehlerShort+" mal gefunden nach "+this.auswertungslaenge+" Minuten!");
        }
        
      
    }
    
    @Override
    public void run() {
        analyse(this.closewerte,this.ausgangspkt,this.length,this.auswertungslaenge);
    }
}
