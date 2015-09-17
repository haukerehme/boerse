/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package boerse;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;

/**
 *
 * @author hauke
 */
public class ArrayOperationen {
    
    static long stunde = 60 * 60000;
    static long minute = 60000;
    boolean isArrayLive(ArrayList<Kursdaten> list, long zeitsprunginmillis)
    {
        Calendar cl = Calendar.getInstance();
        Timestamp akt = new Timestamp(cl.getTimeInMillis());
        
        if(list.get(list.size()-1).zeitdatum.getTime() > akt.getTime() - zeitsprunginmillis )
        {
            return true;
        }
        
        return false;
    }
    
    boolean checkArray(ArrayList<Kursdaten> list, long zeitsprunginmillis, String dateiname)
    {
        for(int i = 0; i < list.size()-1; i++)
        {
            if(list.get(i).zeitdatum.getTime() + zeitsprunginmillis != list.get(i+1).zeitdatum.getTime())
            {
                System.out.println(list.get(i).zeitdatum.toString());
                return false;
            }
        }
        //doppelteLoeschen(list,dateiname);
        //einerLueckenAuffuellen(dateiname,zeitsprunginmillis);
        //System.out.println("Array korrekt");
        return true;
    }
    
    void doppelteLoeschen(ArrayList<Kursdaten> list, String dateiname)
    {
        for(int i = 0; i < list.size()-1; i++)
        {
            if(list.get(i).zeitdatum.getTime()  == list.get(i+1).zeitdatum.getTime())
            {
                list.remove(i);
                i--;
            }
        }
        Converter converter = new Converter();
        converter.arrayincsv(list, dateiname);
    }
    
    void einerLueckenAuffuellen(String dateiname, long zeitsprunginmillis) throws IOException
    {
        Converter converter = new Converter();
        ArrayList<Kursdaten> list = converter.csvinarray(dateiname);
        for(int i = 0; i < list.size()-1; i++)
        {
            if(list.get(i).zeitdatum.getTime() + (2*zeitsprunginmillis) == list.get(i+1).zeitdatum.getTime() )
            {
                Kursdaten tmp = new Kursdaten(list.get(i));
                long z = list.get(i).zeitdatum.getTime();
                Timestamp ts = new Timestamp(z+zeitsprunginmillis);
                tmp.setZeitdatum(ts);
                list.add(i+1, tmp);
                
                //list.get(i+1).zeitdatum.setTime(list.get(i+1).zeitdatum.getTime() + zeitsprunginmillis);
                i++;
            }
        }
        converter.arrayincsv(list, dateiname);
    }
    
    boolean csvzusammenfuehren(String dateiname, String dateiname2) throws IOException
    {
        Converter converter = new Converter();
        ArrayList<Kursdaten> list1 = converter.csvinarray(dateiname);
        ArrayList<Kursdaten> list2 = converter.csvinarray(dateiname2);
        
        if(list1.get(0).zeitdatum.getTime() <= list2.get(0).zeitdatum.getTime()) 
        {
            for(int i = 0; i < list2.size(); i++)
            {
                if(list1.get(list1.size()-1).zeitdatum.getTime() == list2.get(i).zeitdatum.getTime())
                {
                    for(int z = i+1; z < list2.size(); z++)
                    {
                        list1.add(list2.get(z));
                    }
                    converter.arrayincsv(list1, dateiname);
                    return true;
                }
            }
            
        }
        return false;
    }
    
}
