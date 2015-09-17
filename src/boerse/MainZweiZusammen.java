/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package boerse;

import java.io.IOException;

/**
 *
 * @author hauke
 */
public class MainZweiZusammen {
    
    public static void main(String[] args) throws IOException, InterruptedException {
        // TODO code application logic here
        String datei1 = "EURUSD_Candlestick_1_m_BID_31.12.2013-05.11.2014_test.csv";
        String datei2 = "EURUSD_Candlestick_1_m_BID_31.10.2014-05.11.2014.csv";
        ArrayOperationen ao = new ArrayOperationen();
        ao.csvzusammenfuehren(datei1, datei2);
                
    }     
    
}
