/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boerse;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 *
 * @author hrehmege
 */
public class testOldEurUsd {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder();
        int i = 1;
        String text;
        try {
            Scanner scanner = new Scanner(new URL("http://62.75.142.111/eurusd.php").openStream());
            while (scanner.hasNextLine()) {
                //System.out.println(scanner.nextLine());
                if(i == 2){
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
        System.out.println(Double.parseDouble(sb.substring(sb.indexOf("<body>")+6, sb.indexOf("\n"))));
        System.out.println(Double.parseDouble(sb.substring(6, sb.indexOf("</"))));
    }
    
}
