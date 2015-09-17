/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package boerse;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author hauke
 */
public class Dateilogger {
    //String dateiname="/home/pi/Ausgabe.txt";
    String dateiname="Ausgabe.txt";
    
    public void logge(String text) throws IOException
    {
        BufferedWriter bw = new BufferedWriter(new FileWriter(dateiname,true));
        bw.write(text);
        bw.newLine();
        bw.flush();
    }
    
    
    private Dateilogger() {
        
    }
    
    public static Dateilogger getInstance() {
        return DateiloggerHolder.INSTANCE;
    }
    
    private static class DateiloggerHolder{

        private static final Dateilogger INSTANCE = new Dateilogger();
    }
}
