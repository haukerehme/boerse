/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boerse;

import java.net.MalformedURLException;

/**
 *
 * @author hrs
 */
public class TestGetGbpjpy {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws MalformedURLException {
        // TODO code application logic here
        GetAllLiveClosewerte getAll = new GetAllLiveClosewerte();
        System.out.println(getAll.getGBPJPYWert());
    }
    
}
