/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boerse;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Scanner;

/**
 *
 * @author hrs
 */
public class GetAllLiveClosewerte {
    static long stunde = 60 * 60000;
    Dateilogger logger = Dateilogger.getInstance();
    
    public double getEurUsdWert() throws MalformedURLException {
        StringBuilder sb = new StringBuilder();
        int i = 1;
        String text;
        try {
            Scanner scanner = new Scanner(new URL("http://62.75.142.111/getGBPJPY.php").openStream());
            while (scanner.hasNextLine()) {
                //System.out.println(scanner.nextLine());
                if(i == 2)
                {
                    //System.out.println(scanner.nextLine());
                    sb.append(scanner.nextLine() + "\n");
                    /*System.out.println(sb.indexOf(">")+1);
                    System.out.println(sb.indexOf("</"));
                    System.out.println(sb.substring(6, sb.indexOf("</")));*/
                    break;
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
        //return sb.toString();
        return Double.parseDouble(sb.substring(6, sb.indexOf("</")));
    } 
    
    public double getClosewert() throws IOException, InterruptedException
    {   
        Dateilogger logger = Dateilogger.getInstance();
        double dWert = 0;
        //System.out.println("Hole mir nun Livewert. Warte auf die 59. sek");
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
                Thread.sleep(1000);
                return getEurUsdWert();                                             
             }
        }
    } 
    
    public Double getGBPJPYWert() throws MalformedURLException {
        StringBuilder sb = new StringBuilder();
        int i = 1;
        String text;
        try {
            Scanner scanner = new Scanner(new URL("http://62.75.142.111/getGBPJPY.php").openStream());
            while (scanner.hasNextLine()) {
                //System.out.println(scanner.nextLine());
                if(i == 2)
                {
                    //System.out.println(scanner.nextLine());
                    sb.append(scanner.nextLine() + "\n");
                    /*System.out.println(sb.indexOf(">")+1);
                    System.out.println(sb.indexOf("</"));
                    System.out.println(sb.substring(6, sb.indexOf("</")));*/
                    break;
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
        //return sb.toString();
        return Double.parseDouble(sb.substring(6, sb.indexOf("</")));
    } 
    
    public Double getAUDUSDWert() throws MalformedURLException {
        StringBuilder sb = new StringBuilder();
        int i = 1;
        String text;
        try {
            Scanner scanner = new Scanner(new URL("http://62.75.142.111/getAUDUSD.php").openStream());
            while (scanner.hasNextLine()) {
                //System.out.println(scanner.nextLine());
                if(i == 2)
                {
                    //System.out.println(scanner.nextLine());
                    sb.append(scanner.nextLine() + "\n");
                    /*System.out.println(sb.indexOf(">")+1);
                    System.out.println(sb.indexOf("</"));
                    System.out.println(sb.substring(6, sb.indexOf("</")));*/
                    break;
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
        //return sb.toString();
        return Double.parseDouble(sb.substring(6, sb.indexOf("</")));
    } 
}
