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
    List<Integer> closewerte;
    List<List<Integer>> aktuellerAbschnittUnterteilt;
    int ausgangspkt,vergleichsLaenge,auswertungslaenge;
    boolean longPosition;
    int zusammenfasserInterval;
    int spread;
    String instrument;
    
    
    RechnerZusammenfasser(){}
    
    RechnerZusammenfasser(List<Integer> intArray,int ausgangspkt,int vergleichsLaenge,int auswertungslaenge, int zusammenfasserInterval, int spread, String instrument){
        closewerte = intArray;
        this.ausgangspkt =ausgangspkt;
        this.vergleichsLaenge = vergleichsLaenge;
        this.auswertungslaenge = auswertungslaenge;
        this.zusammenfasserInterval = zusammenfasserInterval;
        this.spread = spread;
        this.instrument = instrument;
    }
    
    List<Integer> getAnalyseArray(int vergleichsLaenge){
        List<Integer> tmp = closewerte.subList(closewerte.size()-(vergleichsLaenge), closewerte.size()-1);
        
        return tmp;
    }
    
    int addierer(List<Integer> liste){
        int result = 0;
        for (Integer differenz : liste) {
            result += differenz;
        }
        return result;
    }
    
    Tradevorhersage analyse(List<Integer> intArray, int ausgangspkt,int vergleichsLaenge,int auswertungslaenge){
        
        int GewinnzaehlerLong = 0;
        int VerlustzaehlerLong = 0;
        
        int GewinnzaehlerShort = 0;
        int VerlustzaehlerShort = 0;
        
        int GenerellPlus = 0;
        int GenerellMinus = 0;
        int hohesMinus = 0;
        int hohesPlus = 0;
        
        int hoherLongVerlust = 0;
        int geringerLongGewinn = 0;
        int mittlererLongGewinn = 0;
        int hoherLongGewinn = 0;
        int sehrHoherLongGewinn = 0;
        
        int geringerShortGewinn = 0;
        int mittlererShortGewinn = 0;
        int hoherShortGewinn = 0;
        int sehrHoherShortGewinn = 0;
        int hoherShortVerlust = 0;
        int anzFormFound = 0;
        int anzErsterRight =0;
        boolean formFound;
        List<Integer> akt = getAnalyseArray(vergleichsLaenge);
        aktuellerAbschnittUnterteilt = new ArrayList<List<Integer>>();
        
        //aktueller Array wird zuvor in sublists unterteilt
        //  -> Performance verbessern ;)
        for(int u = 0; u < akt.size(); u=u+zusammenfasserInterval){
            aktuellerAbschnittUnterteilt.add(akt.subList(u, u+zusammenfasserInterval-1));
        }
        
        for(int i = 0; i < closewerte.size()-(vergleichsLaenge)-this.auswertungslaenge;i++){
            formFound = true;
            if(
                        addierer(aktuellerAbschnittUnterteilt.get(0)) - addierer(closewerte.subList(i, i+zusammenfasserInterval-1)) < 4 &&
                        addierer(aktuellerAbschnittUnterteilt.get(0)) - addierer(closewerte.subList(i, i+zusammenfasserInterval-1)) > -4
                        ){
            for(int z = zusammenfasserInterval-1; z < akt.size();z=z+zusammenfasserInterval){   
                if(
                        addierer(aktuellerAbschnittUnterteilt.get((z+1)/zusammenfasserInterval)) - addierer(closewerte.subList(i+z, i+z+zusammenfasserInterval-1)) >= 4 ||
                        addierer(aktuellerAbschnittUnterteilt.get((z+1)/zusammenfasserInterval)) - addierer(closewerte.subList(i+z, i+z+zusammenfasserInterval-1)) <= -4
                        )
                {
                    formFound = false;
                    break;
                }
            }
            if(formFound){
                anzFormFound++;
                    int entwicklung = 0;
                    for(int z =i+this.vergleichsLaenge;z < i+this.vergleichsLaenge+this.auswertungslaenge;z++){
                        entwicklung += closewerte.get(z);
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
                        if(entwicklung > this.spread)
                        {
                            //System.out.println("Gewinn " + alledaten.get(ausgangswert).zeitdatum + " " + alledaten.get(ausgangswert).Closewert);
                            GewinnzaehlerLong++;
                            if(entwicklung > this.spread){
                                geringerLongGewinn++;
                            }
                            if(entwicklung > this.spread+3){
                                mittlererLongGewinn++;
                            }
                            if(entwicklung > this.spread+9){
                                hoherLongGewinn++;
                            }
                            if(entwicklung > this.spread+15){
                                sehrHoherLongGewinn++;
                            }
                            
                            if(entwicklung > this.spread+6){
                                hoherShortVerlust++;
                            }
                        }

                        if(entwicklung < this.spread){
                            //System.out.println("Verlust " + alledaten.get(ausgangswert).zeitdatum + " " + alledaten.get(ausgangswert).Closewert);
                            VerlustzaehlerLong++;
                            
                        }
                    
                    
                        //bei Gewinn wird 1 zurückgegeben, bei Verlust 2 und wenn es gleich geblieben ist 0.
                        if(entwicklung < -this.spread)
                        {
                            //System.out.println("Gewinn " + alledaten.get(ausgangswert).zeitdatum + " " + alledaten.get(ausgangswert).Closewert);
                            GewinnzaehlerShort++;
                            
                            if(entwicklung < -this.spread){
                                geringerShortGewinn++;
                            }
                            if(entwicklung < -this.spread-3){
                                mittlererShortGewinn++;
                            }
                            if(entwicklung < -this.spread-10){
                                hoherShortGewinn++;
                            }
                            if(entwicklung < -this.spread-15){
                                sehrHoherShortGewinn++;
                            }
                            if(entwicklung > -this.spread-6){
                                hoherLongVerlust++;
                            }
                        }

                        if(entwicklung > -this.spread){
                            //System.out.println("Verlust " + alledaten.get(ausgangswert).zeitdatum + " " + alledaten.get(ausgangswert).Closewert);
                            VerlustzaehlerShort++;
                        }
                    
                }
            }
        }
        if(anzFormFound>3){
            /*System.out.print("\033[32mGENERELL:  \033[37m");
            System.out.println(this.vergleichsLaenge+" Minuten:");
            System.out.print("STEIGT: "+GenerellPlus+"/"+anzFormFound+" , "+hohesPlus+"/"+GenerellPlus+"  ");
            System.out.println("FÄLLT: "+GenerellMinus+"/"+anzFormFound+" , "+hohesMinus+"/"+GenerellMinus+"     "+this.auswertungslaenge+" MINUTEN\n");*/
               
            //System.out.println("Für "+this.vergleichsLaenge+" Minuten Formation "+anzFormFound+" mal gefunden! Generell "+GenerellPlus+" mal Plus davon "+hohesPlus+" mal hohes Plus und "+GenerellMinus+" mal Minus davon "+hohesMinus+" mal hohes Minus nach "+this.auswertungslaenge+" Minuten!");
        }
        //System.out.println("Ersten "+anzErsterRight+" mal gefunden!");
        if(anzFormFound>9 && (GewinnzaehlerLong > VerlustzaehlerLong*2 || GewinnzaehlerShort > VerlustzaehlerShort*2)){
            //System.out.println("Long: Letzten "+this.vergleichsLaenge+" Minuten. Gewinn "+GewinnzaehlerLong+" mal davon hoher Gewinn "+hoherLongGewinn+" mal und Verlust "+VerlustzaehlerLong+" mal davon"+hoherLongVerlust+" mal hoher Verlust gefunden nach "+this.auswertungslaenge+" Minuten!");
            //System.out.println("Short: Letzten "+this.vergleichsLaenge+" Minuten. Gewinn "+GewinnzaehlerShort+" mal davon hoher Gewinn "+hoherShortGewinn+" mal und Verlust "+VerlustzaehlerShort+" mal davon"+hoherShortVerlust+" mal hoher Verlust gefunden nach "+this.auswertungslaenge+" Minuten!");
            
            System.out.println("\033[31mTRADEN: Instrument: "+this.instrument+" "+this.vergleichsLaenge +"min "+this.auswertungslaenge+"min\033[37m");
            System.out.println("Long:   GEWINN: "+GewinnzaehlerLong+"/"+anzFormFound+" , "+sehrHoherLongGewinn+"/"+GewinnzaehlerLong+" , "+hoherLongGewinn+"/"+GewinnzaehlerLong+" , "+mittlererLongGewinn+"/"+GewinnzaehlerLong+" , "+geringerLongGewinn+"/"+GewinnzaehlerLong);
            System.out.println("Long:   VERLUST: "+VerlustzaehlerLong+"/"+anzFormFound+" , "+hoherLongVerlust+"/"+VerlustzaehlerLong);

            System.out.println("Short:   GEWINN: "+GewinnzaehlerShort+"/"+anzFormFound+" , "+sehrHoherShortGewinn+"/"+GewinnzaehlerShort+" , "+hoherShortGewinn+"/"+GewinnzaehlerShort+" , "+mittlererShortGewinn+"/"+GewinnzaehlerShort+" , "+geringerShortGewinn+"/"+GewinnzaehlerShort);
            System.out.println("Short:   VERLUST: "+VerlustzaehlerShort+"/"+anzFormFound+" , "+hoherShortVerlust+"/"+VerlustzaehlerShort);

            //System.out.println("Long: Geschaut auf die letzten "+this.vergleichsLaenge+" Minuten. Gewinn "+GewinnzaehlerLong+" mal gefunden nach "+this.auswertungslaenge+" Minuten!");
            //System.out.println("Long: Geschaut auf die letzten "+this.vergleichsLaenge+" Minuten. Verlust "+VerlustzaehlerLong+" mal gefunden nach "+this.auswertungslaenge+" Minuten!");

            //System.out.println("Short: Geschaut auf die letzten "+this.vergleichsLaenge+" Minuten. Gewinn "+GewinnzaehlerShort+" mal gefunden nach "+this.auswertungslaenge+" Minuten!");
            //System.out.println("Short: Geschaut auf die letzten "+this.vergleichsLaenge+" Minuten. Verlust "+VerlustzaehlerShort+" mal gefunden nach "+this.auswertungslaenge+" Minuten!");
        }
        double wahrscheinlichkeitLong = 100* (double) GewinnzaehlerLong / ((double) GewinnzaehlerLong+(double) VerlustzaehlerLong);
        double wahrscheinlichkeitShort = 100* (double) GewinnzaehlerShort / ((double) GewinnzaehlerShort+(double) VerlustzaehlerShort);
        double wahrscheinlichkeitLongHoherGewinn = 100* (double) hoherLongGewinn / ((double) GewinnzaehlerLong);
        double wahrscheinlichkeitShortHoherGewinn = 100* (double) hoherShortGewinn / ((double) GewinnzaehlerShort);
        
        return new Tradevorhersage(anzFormFound, wahrscheinlichkeitLong, wahrscheinlichkeitShort,wahrscheinlichkeitLongHoherGewinn,wahrscheinlichkeitShortHoherGewinn);
    }
    
    @Override
    public void run() {
        analyse(this.closewerte,this.ausgangspkt,this.vergleichsLaenge,this.auswertungslaenge);
    }
}
