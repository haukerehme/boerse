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
        
        int Gewinnzaehler = 0;
        int Verlustzaehler = 0;
        int anzFormFound = 0;
        int anzErsterRight =0;
        boolean formFound;
        List<Integer> akt = getAnalyseArray(length);
        for(int i = 0; i < intArray.size()-(length+1);i++){      
            if((intArray.get(i) - akt.get(0)) == 0 
                            || (intArray.get(i) - akt.get(0)) == 1 
                            || (intArray.get(i) - akt.get(0)) == -1){
                anzErsterRight++;
                formFound = true;
                for(int z = 1; z < akt.size();z++){                    
                    if((intArray.get(z+i) - akt.get(z)) == 0 
                            || (intArray.get(i+z) - akt.get(z)) == 1 
                            || (intArray.get(i+z) - akt.get(z)) == -1){
                        formFound = false;
                        break;
                    }
                }
                if(formFound){
                    anzFormFound++;
                    
                    int entwicklung = 0;
                    for(int z =i;z < i+length;z++){
                        entwicklung += intArray.get(z);
                    }   
                        //bei Gewinn wird 1 zurückgegeben, bei Verlust 2 und wenn es gleich geblieben ist 0.
                        if(entwicklung > 2)
                        {
                            //System.out.println("Gewinn " + alledaten.get(ausgangswert).zeitdatum + " " + alledaten.get(ausgangswert).Closewert);
                            Gewinnzaehler++;
                        }

                        if(entwicklung < 2){
                            //System.out.println("Verlust " + alledaten.get(ausgangswert).zeitdatum + " " + alledaten.get(ausgangswert).Closewert);
                            Verlustzaehler++;
                        }
                }
            }
        }
        System.out.println("Formation "+anzFormFound+" mal gefunden!");
        System.out.println("Ersten "+anzErsterRight+" mal gefunden!");
        
        System.out.println("Gewinn "+Gewinnzaehler+" mal gefunden!");
        System.out.println("Verlust "+Verlustzaehler+" mal gefunden!");
      
    }
    
    @Override
    public void run() {
        analyse(this.closewerte,this.ausgangspkt,this.length,this.auswertungslaenge);
    }
}
