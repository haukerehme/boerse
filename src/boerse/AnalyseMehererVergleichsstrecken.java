/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boerse;

import java.util.List;

/**
 *
 * @author hrs
 */
public class AnalyseMehererVergleichsstrecken extends Thread{
    Dateilogger logger = Dateilogger.getInstance();
    RechnerZusammenfasser rechner;
    List<Integer> closewerte;
    int ausgangspkt,vergleichsLaenge,auswertungslaenge;
    boolean longPosition, mehrereVergleichsstrecken, SimulatorModus;
    int zusammenfasserInterval;
    int spread;
    String instrument;
    List<Integer> listVergleichsLaenge;
    
    int GewinnzaehlerLong = 0;
    int VerlustzaehlerLong = 0;
        
    int GewinnzaehlerShort = 0;
    int VerlustzaehlerShort = 0;
        
    int GenerellPlus = 0;
    int GenerellMinus = 0;
    int hohesMinus = 0;
    int hohesPlus  = 0;
        
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
    
    AnalyseMehererVergleichsstrecken(List<Integer> intArray,int ausgangspkt,List<Integer> listVergleichsLaenge,int auswertungslaenge, int spread, String instrument){
        closewerte = intArray;
        this.ausgangspkt =ausgangspkt;
        this.listVergleichsLaenge = listVergleichsLaenge;
        this.auswertungslaenge = auswertungslaenge;
        this.spread = spread;
        this.instrument = instrument;
    }
    
    void addiere(Tradevorhersage tmp){
        GewinnzaehlerLong += tmp.GewinnzaehlerLong;
        VerlustzaehlerLong += tmp.VerlustzaehlerLong;

        GewinnzaehlerShort += tmp.GewinnzaehlerShort;
        VerlustzaehlerShort += tmp.VerlustzaehlerShort;

        GenerellPlus += tmp.GenerellPlus;
        GenerellMinus += tmp.GenerellMinus;
        hohesMinus += tmp.hohesMinus;
        hohesPlus  += tmp.hohesPlus;

        hoherLongVerlust += tmp.hoherLongVerlust;
        geringerLongGewinn += tmp.geringerLongGewinn;
        mittlererLongGewinn += tmp.mittlererLongGewinn;
        hoherLongGewinn += tmp.hoherLongGewinn;
        sehrHoherLongGewinn += tmp.sehrHoherLongGewinn;

        geringerShortGewinn += tmp.geringerShortGewinn;
        mittlererShortGewinn += tmp.mittlererShortGewinn;
        hoherShortGewinn += tmp.hoherShortGewinn;
        sehrHoherShortGewinn += tmp.sehrHoherShortGewinn;
        hoherShortVerlust += tmp.hoherShortVerlust;
        anzFormFound += tmp.anzFormFound;
    }
    
    @Override
    public void run() {
        
        Tradevorhersage tradeTmp;
        rechner = new RechnerZusammenfasser(this.closewerte, this.closewerte.size()-1, 240, auswertungslaenge, 30,spread,"EUR/USD",true,false);  
        tradeTmp = rechner.analyse(/*this.closewerte, this.closewerte.size()-1, 240, auswertungslaenge*/);
        addiere(tradeTmp);
        
        rechner = new RechnerZusammenfasser(this.closewerte, this.closewerte.size()-1, 210, auswertungslaenge, 30,spread,"EUR/USD",true,false);  
        tradeTmp = rechner.analyse(/*this.closewerte, this.closewerte.size()-1, 240, auswertungslaenge*/);
        addiere(tradeTmp);
        
        rechner = new RechnerZusammenfasser(this.closewerte, this.closewerte.size()-1, 180, auswertungslaenge, 20,spread,"EUR/USD",true,false);  
        tradeTmp = rechner.analyse(/*this.closewerte, this.closewerte.size()-1, 240, auswertungslaenge*/);
        addiere(tradeTmp);
        
        rechner = new RechnerZusammenfasser(this.closewerte, this.closewerte.size()-1, 150, auswertungslaenge, 10,spread,"EUR/USD",true,false);  
        tradeTmp = rechner.analyse(/*this.closewerte, this.closewerte.size()-1, 240, auswertungslaenge*/);
        addiere(tradeTmp);
        
        rechner = new RechnerZusammenfasser(this.closewerte, this.closewerte.size()-1, 120, auswertungslaenge, 10,spread,"EUR/USD",true,false);  
        tradeTmp = rechner.analyse(/*this.closewerte, this.closewerte.size()-1, 240, auswertungslaenge*/);
        addiere(tradeTmp);
        
        
        String ausgabe = "";
        if(this.spread == 1){
            ausgabe += "\033[31mTRADEN: Mehrere Vergleichslaengen ;) Instrument: "+this.instrument+" "+this.auswertungslaenge+"min\033[0m";
        }else{
            ausgabe += "\033[33mTRADEN: Mehrere Vergleichslaengen ;) Instrument: "+this.instrument+ " "+this.auswertungslaenge+"min\033[0m";
        }
        ausgabe += "\nLong:   GEWINN: "+GewinnzaehlerLong+"/"+anzFormFound+" , "+sehrHoherLongGewinn+"/"+GewinnzaehlerLong+" , "+hoherLongGewinn+"/"+GewinnzaehlerLong+" , "+mittlererLongGewinn+"/"+GewinnzaehlerLong+" , "+geringerLongGewinn+"/"+GewinnzaehlerLong;
        ausgabe += "\nLong:   VERLUST: "+VerlustzaehlerLong+"/"+anzFormFound+" , "+hoherLongVerlust+"/"+VerlustzaehlerLong;

        ausgabe += "\nShort:   GEWINN: "+GewinnzaehlerShort+"/"+anzFormFound+" , "+sehrHoherShortGewinn+"/"+GewinnzaehlerShort+" , "+hoherShortGewinn+"/"+GewinnzaehlerShort+" , "+mittlererShortGewinn+"/"+GewinnzaehlerShort+" , "+geringerShortGewinn+"/"+GewinnzaehlerShort;
        ausgabe += "\nShort:   VERLUST: "+VerlustzaehlerShort+"/"+anzFormFound+" , "+hoherShortVerlust+"/"+VerlustzaehlerShort+"\n";
        System.out.print(ausgabe);
    }
}
