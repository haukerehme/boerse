/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package boerse;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.*;
import java.util.Calendar;
import java.util.Scanner;
import java.net.InetAddress;

/**
 *
 * @author hauke
 */
public class LiveClosewertEurUsd{
    static long stunde = 60 * 60000;
    Dateilogger logger = Dateilogger.getInstance();
    public String getWert() throws MalformedURLException {
        StringBuilder sb = new StringBuilder();
        int i = 1;
        String text;
        try {
            /*InetAddress inet = InetAddress.getByName("http://bigmac.mi.ecs.hs-osnabrueck.de/~kakroene/");
            if (inet.isReachable(10000)){   
               logger.logge("Connected "+ inet.toString());
            }
            else{
               logger.loggeWarning("Failed "+inet.toString());
            }*/
            Scanner scanner = new Scanner(new URL("http://bigmac.mi.ecs.hs-osnabrueck.de/~kakroene/").openStream());
            while (scanner.hasNextLine()) {
                if(i == 13)
                {
                    sb.append(scanner.nextLine() + "\n");
                }
                scanner.nextLine();
                i++;
            }
            scanner.close();
        } catch (MalformedURLException e) {
        e.printStackTrace();
        } catch (IOException e) {
        e.printStackTrace();
        }
        return sb.toString();
    } 
    
    public double getClosewert() throws IOException, InterruptedException
    {   
        Dateilogger logger = Dateilogger.getInstance();
        double dWert = 0;
        System.out.println("Hole mir nun Livewert. Warte auf die 59. sek");
        logger.logge("Hole mir nun Livewert. Warte auf die 59. sek");
        while(true)
        {
            Calendar cl = Calendar.getInstance();
            Timestamp akt = new Timestamp(cl.getTimeInMillis());
            Timestamp tmp = akt;
            int sek = tmp.getSeconds();
                    
            if(tmp.getSeconds() == 59)
            {
                logger.logge("59. Sekunde!!!   Uhrzeit: "+tmp.toString());
                System.out.println("59. sek WUWU  Uhrzeit: "+tmp.toString());
                String wert = getWert();
                wert = wert.substring(8, 14);
                Thread.sleep(1000);
                return Double.parseDouble(wert);                                             
             }
        }
    } 
}
