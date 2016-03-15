/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Test;

import boerse.AnalyseMehererVergleichsstrecken;
import boerse.DatenbankController;
import boerse.Kursdaten;
import hilfsklassen.MailClass;
import java.io.IOException;
import java.sql.SQLException;
import javax.mail.MessagingException;

/**
 *
 * @author hrs <hauke.rehme-schlueter at hotmail.com>
 */
public class TestIfLastEurUsdIsNull {

    /**
     * @param args the command line arguments
     * @throws java.lang.ClassNotFoundException
     * @throws java.io.IOException
     * @throws java.sql.SQLException
     * @throws javax.mail.MessagingException
     */
    public static void main(String[] args) throws ClassNotFoundException, IOException, SQLException, MessagingException {
        // TODO code application logic here
        DatenbankController dbCon = new DatenbankController();
        Kursdaten kursDaten = dbCon.lastEntry();
        
        if(kursDaten.Closewert == 0){
            System.out.println("Last entry is null. Send Email!!!");
            MailClass.sendMail("haukekatha","43mitmilch","hrs@logentis.de","lemur.katha@googlemail.com","Last Entry Error","Last Entry EurUsd is 0");
        }else{
            System.out.println("Last entry is "+kursDaten.Closewert);
        }
    }
    
}
