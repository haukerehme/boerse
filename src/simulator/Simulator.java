/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulator;

import boerse.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;

/**
 *
 * @author hauke
 */
public class Simulator {

    ArrayList<Integer> vergangenheit;
    ArrayList<Integer> simulationszeitraum;
    public Dateilogger logger = Dateilogger.getInstance();
    Kursdaten kd;

    public Simulator(Timestamp startSimu, Timestamp endeSimu, String csvName) throws IOException {
        this.kd = new Kursdaten();
        this.vergangenheit = fuelleArray(csvName, null, startSimu);
        this.simulationszeitraum = fuelleArray(csvName, startSimu, endeSimu);
    }

    private ArrayList<Integer> fuelleArray(String csvName, Timestamp anfangArray, Timestamp endeArray) throws IOException {
        ArrayList<Integer> daten = new ArrayList<>();

        try {
            //FileReader fr = new FileReader("/home/pi/RaspberryPiBoerse_v1-1/"+dateiname);
            FileReader fr = new FileReader(csvName);
            logger.logge("FileReader erfolgreich initialisiert!");
            BufferedReader in = new BufferedReader(fr);
            String zeile;
            logger.logge("Bufferedreader erfolgreich initialisiert!");
            while ((zeile = in.readLine()) != null) {
                if (kd.convertZeitDatum(zeile).equals(anfangArray) || anfangArray == null) {
                    break;
                }
            }
            double wertNeu, wertAlt;
            String[] splitResult = zeile.split(",");
            wertNeu = Double.parseDouble(splitResult[4]);
            wertNeu = Math.round(wertNeu * 10000d) / 10000d;
            wertAlt = wertNeu;
            while ((zeile = in.readLine()) != null) {
                if (kd.convertZeitDatum(zeile).equals(endeArray)) {
                    break;
                }
                splitResult = zeile.split(",");
                wertNeu = Double.parseDouble(splitResult[4]);
                wertNeu = Math.round(wertNeu * 10000d) / 10000d;
                int differenz = (int) (10000 * wertNeu - 10000 * wertAlt);
                daten.add((int) (differenz));
                wertAlt = wertNeu;
            }
        } catch (IOException e) {
            logger.logge("catch IOException in Converter.csvinarray");
            e.printStackTrace();
        }
        return daten;
    }
}
