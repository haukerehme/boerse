/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Test;

import boerse.WerteAuslesen;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hrs <hauke.rehme-schlueter at hotmail.com>
 */
public class testNewEurUsdIg {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ClassNotFoundException, IOException, SQLException {
        StringBuilder sb = new StringBuilder();
        int i = 1;
        String text;
        try {
            Scanner scanner = new Scanner(new URL("http://62.75.142.111/igEurUsdLocalTest.php").openStream());
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
        //return sb.toString();
        //System.out.println(Double.parseDouble(sb.substring(sb.indexOf("<body>")+6, sb.indexOf("</"))));
        Double value = Double.parseDouble(sb.substring(sb.indexOf("<body>")+6, sb.indexOf("</")));
        
        
       ResultSet rs = null;
        Connection conn = null;
        try{
            String myUrl = "jdbc:mysql://62.75.142.111:3306/Boerse";
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(myUrl, "root", "43mitmilch");
            
            String insertTableSQL = "INSERT INTO igEurusd"
		+ "(time, value) VALUES"
		+ "(?,?)";
            Calendar cl = Calendar.getInstance();
            Timestamp akt = new Timestamp(cl.getTimeInMillis());
            PreparedStatement preparedStatement = conn.prepareStatement(insertTableSQL);
            preparedStatement.setTimestamp(1, akt);
            preparedStatement.setDouble(2, value);
            
            preparedStatement.executeUpdate();
        }catch (SQLException ex) {
            Logger.getLogger(WerteAuslesen.class.getName()).log(Level.SEVERE, null, ex);
        }
        conn.close();
    }
    
}
