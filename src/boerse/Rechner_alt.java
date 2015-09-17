/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package boerse;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.logging.Logger;

/**
 *
 * @author hauke
 */
public class Rechner_alt {
    Dateilogger logger = Dateilogger.getInstance();
    ArrayList<Integer> unterschiedsArrayFuellen(ArrayList<Kursdaten> alledaten,int ausgangswert, int laenge, int laengeArray1) throws IOException
    {
        //logger.logge("unterschiedsArrayFuellen! Länge des gesamten Arrays: "+ alledaten.size());
        ArrayList<Integer> Unterschiedarray = new ArrayList<>();
        Kursdaten ausgang = alledaten.get(ausgangswert); 
        Kursdaten tmp;
        //logger.logge("Länge: "+laenge+" Ausgangswert: "+ausgangswert);
        for(int i = 0; i <laenge ;i++)
        {
            tmp = alledaten.get((ausgangswert-laengeArray1)+i);
            Unterschiedarray.add(unterschied(tmp,ausgang));
        }
        return Unterschiedarray;
    }
    
    boolean gleichheitstest(ArrayList<Integer> Unterschiedarray, ArrayList<Integer> Unterschiedarray2)
    {
        for(int u = 0; u < Unterschiedarray2.size();u++)
        {
            if( (Unterschiedarray2.get(u)-Unterschiedarray.get(u) > 2 
              || Unterschiedarray2.get(u)-Unterschiedarray.get(u) < -2 ))
            {
                return false;
            }
        }
        return true;
    }
    
    void unterschiedsVergleicher(ArrayList<Kursdaten> alledaten,int ausgangswert, int laenge) throws IOException
    {
        
        logger.logge("Bin jetzt im unterschiedsVergleicher!");
        ArrayList<Integer> Unterschiedarray = unterschiedsArrayFuellen(alledaten,ausgangswert, laenge,laenge);
        logger.logge("Array gefüllt!");
        int Gleichzaehler = 0;
        int Gewinnzaehler = 0;
        int Verlustzaehler = 0;
        for(int i = laenge; i<ausgangswert-100;i++)
        {
            ArrayList<Integer> Test = unterschiedsArrayFuellen(alledaten,i,2,laenge);
            Boolean gleich = gleichheitstest(Unterschiedarray, Test);
            if(gleich==true)
            {
                Test = unterschiedsArrayFuellen(alledaten,i,4,laenge);
                gleich = gleichheitstest(Unterschiedarray, Test);
                if(gleich==true)
                {
                    Test = unterschiedsArrayFuellen(alledaten,i,10,laenge);
                    gleich = gleichheitstest(Unterschiedarray, Test);
                    if(gleich==true)
                    {
                        ArrayList<Integer> Unterschiedarray2 = unterschiedsArrayFuellen(alledaten,i,laenge,laenge);
                        gleich = gleichheitstest(Unterschiedarray, Unterschiedarray2);
                        if(gleich==true)
                        {
                            Gleichzaehler++;
                            //System.out.println("Sensation " + alledaten.get(i).zeitdatum + " " + alledaten.get(i).Closewert);
                            int ergebnis = GuV(alledaten,i,60);
                            if(ergebnis == 1)
                            {Gewinnzaehler++;}
                            if(ergebnis == 2)
                            {Verlustzaehler++;}
                        }
                    }
                }
            }
            
        }
        logger.logge("Auswertung:");
        
        String ausgabe = "";
        ausgabe = "Diese Formation habe ich "+ Gleichzaehler+" mal gefunden.\n";
        //log.info("Diese Formation habe ich "+ Gleichzaehler+" mal gefunden.");
        System.out.println("Diese Formation habe ich "+ Gleichzaehler+" mal gefunden.");
        if(Gleichzaehler!=0){
            ausgabe += "Gewinn kam "+Gewinnzaehler+" mal vor Digga. ;) Das sind " + Gewinnzaehler*100/Gleichzaehler + " %.\n";
            //log.info("Gewinn kam "+Gewinnzaehler+" mal vor Digga. ;) Das sind " + Gewinnzaehler*100/Gleichzaehler + " %.");
            System.out.println("Gewinn kam "+Gewinnzaehler+" mal vor Digga. ;) Das sind " + Gewinnzaehler*100/Gleichzaehler + " %.");
            ausgabe += "Verlust kam "+Verlustzaehler+" mal vor Digga. ;) Das sind " + Verlustzaehler*100/Gleichzaehler + " %.\n";
            //log.info("Verlust kam "+Verlustzaehler+" mal vor Digga. ;) Das sind " + Verlustzaehler*100/Gleichzaehler + " %.");
            System.out.println("Verlust kam "+Verlustzaehler+" mal vor Digga. ;) Das sind " + Verlustzaehler*100/Gleichzaehler + " %.");
        }
        logger.logge(ausgabe);
    }
    
    int GuV(ArrayList<Kursdaten> alledaten,int ausgangswert, int analysewert)
    {
        //bei Gewinn wird 1 zurückgegeben, bei Verlust 2 und wenn es gleich geblieben ist 0.
        if(alledaten.get(ausgangswert+analysewert).Closewert - 0.0002 > alledaten.get(ausgangswert).Closewert)
        {
            //System.out.println("Gewinn " + alledaten.get(ausgangswert).zeitdatum + " " + alledaten.get(ausgangswert).Closewert);
            return 1;
        }
        
        if(alledaten.get(ausgangswert+analysewert).Closewert + 0.0002 < alledaten.get(ausgangswert).Closewert){
            //System.out.println("Verlust " + alledaten.get(ausgangswert).zeitdatum + " " + alledaten.get(ausgangswert).Closewert);
            return 2;
        }
        return 0;
    }
    
    int unterschied(Kursdaten vorgaenger, Kursdaten Bezugspkt)
    {
        DecimalFormat f = new DecimalFormat("#0.0000"); 
        int vorgaenger_ = (int) (10000*vorgaenger.Closewert);
        int Bezugspkt_ = (int) (10000*Bezugspkt.Closewert);
        return (int)(Bezugspkt_ - vorgaenger_);
    }
}
