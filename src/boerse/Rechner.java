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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hauke
 */
public class Rechner extends Thread{
    Dateilogger logger = Dateilogger.getInstance();
    ArrayList<Integer> closewerte;
    int ausgangspkt,length,auswertungslaenge;
    
    Rechner(ArrayList<Integer> intArray,int ausgangspkt,int length,int auswertungslaenge){
        closewerte = intArray;
        this.ausgangspkt =ausgangspkt;
        this.length = length;
        this.auswertungslaenge = auswertungslaenge;
    }

    Rechner() {}
    
    ArrayList<Integer> unterschiedsArrayFuellen(ArrayList<Integer> alledaten,int ausgangswert, int laenge, int laengeArray1) throws IOException
    {
        ArrayList<Integer> tmp = new ArrayList();
        for(int i = alledaten.size()-(laenge+1); i < alledaten.size()-1;i++)
        {
            tmp.add(alledaten.get(i));
        }
        return tmp;
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
    
    public boolean unterschiedsVergleicher(ArrayList<Integer> alledaten,int ausgangswert, int laenge,int auswertungsstecke) throws IOException
    {       
        logger.logge("Bin jetzt im unterschiedsVergleicher!");
        logger.logge("Ausgangsarray füllen");
        ArrayList<Integer> Unterschiedarray = unterschiedsArrayFuellen(alledaten,ausgangswert, laenge,laenge);
        
        int Gleichzaehler = 0;
        int Gewinnzaehler = 0;
        int Verlustzaehler = 0;
        
        /*ArrayList<Integer> Test = unterschiedsArrayFuellen(alledaten,laenge,laenge,laenge);*/
        logger.logge("Berechnung startet jetzt!");
        boolean gleich = true;
        int obergrenze = ausgangswert-(2*laenge)-1;
        for(int i = 0; i<obergrenze;i++)
        {
            gleich = true;
            for(int z = 0;z<laenge;z++)
            {
                if( (alledaten.get(i+z) - Unterschiedarray.get(z)) < -1 || (alledaten.get(i+z) - Unterschiedarray.get(z)) > 1)
                {
                    gleich = false;
                    break;
                }
            }
            if(gleich)
            {
                Gleichzaehler++;
                //System.out.println("Sensation " + alledaten.get(i).zeitdatum + " " + alledaten.get(i).Closewert);
                int ergebnis = GuV(alledaten,i,auswertungsstecke,laenge);
                if(ergebnis == 1){Gewinnzaehler++;}
                if(ergebnis == 2){Verlustzaehler++;}
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
        if(Gewinnzaehler>Verlustzaehler){
            return true;
        }
        else{
            return false;
        }
    }
    
    
    int GuV(ArrayList<Integer> alledaten,int ausgangswert, int analysewert, int laenge)
    {
        int entwicklung = 0;
        for(int i = laenge;i < analysewert+laenge;i++)
        {
            entwicklung += alledaten.get(ausgangswert+i-1);
        }
        //bei Gewinn wird 1 zurückgegeben, bei Verlust 2 und wenn es gleich geblieben ist 0.
        if(entwicklung > 2)
        {
            //System.out.println("Gewinn " + alledaten.get(ausgangswert).zeitdatum + " " + alledaten.get(ausgangswert).Closewert);
            return 1;
        }
        
        if(entwicklung < 2){
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

    @Override
    public void run() {
        try {
            unterschiedsVergleicher(closewerte,ausgangspkt,length,auswertungslaenge);
        } catch (IOException ex) {
            Logger.getLogger(Rechner.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
