/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auswertung;

import java.io.IOException;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import static jdk.nashorn.internal.objects.NativeMath.round;

/**
 *
 * @author hrs <hauke.rehme-schlueter at hotmail.com>
 */
public class AuswertungTradevorhersagenTabelle {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException {
        // TODO code application logic here
        DbControllerAuswertung db = new DbControllerAuswertung();
        ArrayList<AuswertModel> auswertModels = db.getTradeMessage();
        int differenz;
        double diff;
        PreparedStatement preparedStmt;
        String myUrl = "jdbc:mysql://62.75.142.111:3306/Boerse";
        Class.forName("com.mysql.jdbc.Driver");
        System.out.println("Verbindungsversuch:");
        Connection conn = DriverManager.getConnection(myUrl, "root", "43mitmilch");
        String query = "Update TradeMessage SET timestamp=? , 20minLong= ? Where timestamp = ?";
        for(int i = 0; i < auswertModels.size()-20;i++){
            diff = auswertModels.get(i+20).closewert*10000 - auswertModels.get(i).closewert*10000;
            differenz = (int) Math.round((float)diff);
            preparedStmt = conn.prepareStatement(query);
            try{   
                preparedStmt.setTimestamp(1, auswertModels.get(i).tradeMessage.getTimestamp());
                preparedStmt.setInt(2, differenz);
                preparedStmt.setTimestamp(3, auswertModels.get(i).tradeMessage.getTimestamp());
                preparedStmt.executeUpdate();
                preparedStmt.close();
                //create a mysql database connection
            }
            catch (Exception e){
                System.err.println("Got an exception!");
                System.err.println(e.getMessage());
            }   
        }
    }
}
