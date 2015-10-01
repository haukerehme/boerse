/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulator;

import java.util.ArrayList;
import boerse.Rechner;
import java.io.IOException;

/**
 *
 * @author hauke
 */
public class RunSimulator {
    double Startkapital;
    double Konto;
    int spreadKonto = 0;
    double spread;
    int anzDeals;
    ArrayList<Integer> vergangenheit;
    ArrayList<Integer> simulationszeitraum;
//    Rechner rechner = new Rechner();
    
    public void run() throws IOException{
        int vergleichslaenge = 120;
        int auswertungsstecke = 60;
        boolean handle = false;
        for(int i = 0; i < simulationszeitraum.size() - auswertungsstecke;i++){
                int differenz = simulationszeitraum.get(i);
                vergangenheit.add(differenz);
                simulationszeitraum.remove(0);
                System.out.println("Rechne");
//                if(rechner.unterschiedsVergleicher(vergangenheit, vergangenheit.size()-1, vergleichslaenge,auswertungsstecke)){
//                    //Rechnung für Deal
//                    for(int z = 0; z < auswertungsstecke;z++){
//                        spreadKonto += simulationszeitraum.get(i);
//                    }
//                    spreadKonto -= 2;
//                }
        }
    }

    public RunSimulator(double Startkapital, double spread, ArrayList<Integer> vergangenheit, ArrayList<Integer> simulationszeitraum) {
        this.Startkapital = Startkapital;
        this.spread = spread;
        this.vergangenheit = vergangenheit;
        this.simulationszeitraum = simulationszeitraum;
    }
}
