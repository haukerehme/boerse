/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boerse;

/**
 *
 * @author hrs
 */
public class Tradevorhersage {
    
    double wahrscheinlichkeitShort;
    double wahrscheinlichkeitLong;
    double wahrscheinlichkeitLongHoherGewinn;
    double wahrscheinlichkeitShortHoherGewinn;
    int anzForm;
    
    
    int GewinnzaehlerLong;
    int VerlustzaehlerLong;
        
    int GewinnzaehlerShort;
    int VerlustzaehlerShort;
        
    int GenerellPlus;
    int GenerellMinus;
    int hohesMinus;
    int hohesPlus ;
        
    int hoherLongVerlust;
    int geringerLongGewinn;
    int mittlererLongGewinn;
    int hoherLongGewinn;
    int sehrHoherLongGewinn;
        
    int geringerShortGewinn;
    int mittlererShortGewinn;
    int hoherShortGewinn;
    int sehrHoherShortGewinn;
    int hoherShortVerlust;
    int anzFormFound;
    
    Tradevorhersage(){}
    
    Tradevorhersage(int anzForm, double wahrscheinlichkeitLong, double wahrscheinlichkeitShort, double wahrscheinlichkeitLongHoherGewinn, double wahrscheinlichkeitShortHoherGewinn){
        this.anzForm = anzForm;
        this.wahrscheinlichkeitLong = wahrscheinlichkeitLong;
        this.wahrscheinlichkeitShort = wahrscheinlichkeitShort;
        
        this.wahrscheinlichkeitLongHoherGewinn = wahrscheinlichkeitLongHoherGewinn;
        this.wahrscheinlichkeitShortHoherGewinn = wahrscheinlichkeitShortHoherGewinn;   
    }
    
}
