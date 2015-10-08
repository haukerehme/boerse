/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boerse;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hrs
 */
public class RechnerZusammenfasser extends Thread{
    Dateilogger logger = Dateilogger.getInstance();
    Rechner rechner = new Rechner();
    ArrayList<Integer> closewerte;
    int ausgangspkt,length,auswertungslaenge;
    boolean longPosition;
    int zusammenfasserInterval;
    
    RechnerZusammenfasser(ArrayList<Integer> intArray,int ausgangspkt,int length,int auswertungslaenge, int zusammenfasserInterval){
        closewerte = intArray;
        this.ausgangspkt =ausgangspkt;
        this.length = length;
        this.auswertungslaenge = auswertungslaenge;
        this.zusammenfasserInterval = zusammenfasserInterval;
    }
    
    List<Integer> getAnalyseArray(int length){
        List<Integer> tmp = closewerte.subList(closewerte.size()-(length), closewerte.size()-1);
        
        return tmp;
    }
    
    int addierer(List<Integer> liste){
        int result = 0;
        for (Integer differenz : liste) {
            result += differenz;
        }
        return result;
    }
    
    void analyse(ArrayList<Integer> intArray,int ausgangspkt,int length,int auswertungslaenge){
        
        int GewinnzaehlerLong = 0;
        int VerlustzaehlerLong = 0;
        int GewinnzaehlerShort = 0;
        int VerlustzaehlerShort = 0;
        int GenerellPlus = 0;
        int GenerellMinus = 0;
        int hoherLongVerlust = 0;
        int hoherLongGewinn = 0;
        int hohesMinus = 0;
        int hohesPlus = 0;
        int hoherShortGewinn = 0;
        int hoherShortVerlust = 0;
        int anzFormFound = 0;
        int anzErsterRight =0;
        boolean formFound;
        List<Integer> akt = getAnalyseArray(length);
        for(int i = 0; i < intArray.size()-(length)-this.auswertungslaenge;i++){
            formFound = true;
            if(
                        addierer(akt.subList(0, zusammenfasserInterval-1)) - addierer(intArray.subList(i, i+zusammenfasserInterval-1)) < 4 &&
                        addierer(akt.subList(0, zusammenfasserInterval-1)) - addierer(intArray.subList(i, i+zusammenfasserInterval-1)) > -4
                        ){
            for(int z = zusammenfasserInterval-1; z < akt.size();z=z+zusammenfasserInterval){   
                if(
                        addierer(akt.subList(z, z+zusammenfasserInterval-1)) - addierer(intArray.subList(i+z, i+z+zusammenfasserInterval-1)) >= 4 ||
                        addierer(akt.subList(z, z+zusammenfasserInterval-1)) - addierer(intArray.subList(i+z, i+z+zusammenfasserInterval-1)) <= -4
                        )
                {
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
                        if(entwicklung > 0)
                        {
                            //System.out.println("Gewinn " + alledaten.get(ausgangswert).zeitdatum + " " + alledaten.get(ausgangswert).Closewert);
                            GenerellPlus++;
                            if(entwicklung > 10){
                                hohesPlus++;
                            }
                        }
                        if(entwicklung < 0){
                            GenerellMinus++;
                            if(entwicklung < -10){
                                hohesMinus++;
                            }
                        }
                    
                        //bei Gewinn wird 1 zurückgegeben, bei Verlust 2 und wenn es gleich geblieben ist 0.
                        if(entwicklung > 2)
                        {
                            //System.out.println("Gewinn " + alledaten.get(ausgangswert).zeitdatum + " " + alledaten.get(ausgangswert).Closewert);
                            GewinnzaehlerLong++;
                            if(entwicklung > 12){
                                hoherLongGewinn++;
                            }
                            if(entwicklung > 8){
                                hoherShortVerlust++;
                            }
                        }

                        if(entwicklung < 2){
                            //System.out.println("Verlust " + alledaten.get(ausgangswert).zeitdatum + " " + alledaten.get(ausgangswert).Closewert);
                            VerlustzaehlerLong++;
                            if(entwicklung < -8){
                                hoherLongVerlust++;
                            }
                            if(entwicklung < -12){
                                hoherShortGewinn++;
                            }
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
        if(anzFormFound>3){
            System.out.print("\033[32mGENERELL:  \033[37m");
            System.out.println(this.length+" Minuten:");
            System.out.print("STEIGT: "+GenerellPlus+"/"+anzFormFound+" , "+hohesPlus+"/"+GenerellPlus+"  ");
            System.out.println("FÄLLT: "+GenerellMinus+"/"+anzFormFound+" , "+hohesMinus+"/"+GenerellMinus+"     "+this.auswertungslaenge+" MINUTEN\n");
               
            //System.out.println("Für "+this.length+" Minuten Formation "+anzFormFound+" mal gefunden! Generell "+GenerellPlus+" mal Plus davon "+hohesPlus+" mal hohes Plus und "+GenerellMinus+" mal Minus davon "+hohesMinus+" mal hohes Minus nach "+this.auswertungslaenge+" Minuten!");
        }
        //System.out.println("Ersten "+anzErsterRight+" mal gefunden!");
        if(anzFormFound>5 && (GewinnzaehlerLong > VerlustzaehlerLong*2 || GewinnzaehlerShort > VerlustzaehlerShort*2)){
            //System.out.println("Long: Letzten "+this.length+" Minuten. Gewinn "+GewinnzaehlerLong+" mal davon hoher Gewinn "+hoherLongGewinn+" mal und Verlust "+VerlustzaehlerLong+" mal davon"+hoherLongVerlust+" mal hoher Verlust gefunden nach "+this.auswertungslaenge+" Minuten!");
            //System.out.println("Short: Letzten "+this.length+" Minuten. Gewinn "+GewinnzaehlerShort+" mal davon hoher Gewinn "+hoherShortGewinn+" mal und Verlust "+VerlustzaehlerShort+" mal davon"+hoherShortVerlust+" mal hoher Verlust gefunden nach "+this.auswertungslaenge+" Minuten!");
            
            System.out.println("\033[31mTRADEN:\033[37m");
            System.out.println("Long:   GEWINN: "+GewinnzaehlerLong+"/"+anzFormFound+" , "+hoherLongGewinn+"/"+GewinnzaehlerLong);
            System.out.println("Long:   VERLUST: "+VerlustzaehlerLong+"/"+anzFormFound+" , "+hoherLongVerlust+"/"+VerlustzaehlerLong);

            System.out.println("Short:   GEWINN: "+GewinnzaehlerShort+"/"+anzFormFound+" , "+hoherShortGewinn+"/"+GewinnzaehlerShort);
            System.out.println("Short:   VERLUST: "+VerlustzaehlerShort+"/"+anzFormFound+" , "+hoherShortVerlust+"/"+VerlustzaehlerShort);

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
