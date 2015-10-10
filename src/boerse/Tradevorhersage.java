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
    int anzForm;
    
    Tradevorhersage(int anzForm, double wahrscheinlichkeitLong, double wahrscheinlichkeitShort){
        this.anzForm = anzForm;
        this.wahrscheinlichkeitLong = wahrscheinlichkeitLong;
        this.wahrscheinlichkeitShort = wahrscheinlichkeitShort;
    }
    
}
