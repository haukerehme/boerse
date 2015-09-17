/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package boerse;
import java.sql.Time;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
/**
 *
 * @author hauke
 */
public class Kursdaten {
    
    public int spread = 2;

    public void setZeitdatum(Timestamp zeitdatum) {
        this.zeitdatum = zeitdatum;
    }

    public void setOpenwert(double Openwert) {
        this.Openwert = Openwert;
    }

    public void setClosewert(double Closewert) {
        this.Closewert = Closewert;
    }

    public void setLow(double Low) {
        this.Low = Low;
    }

    public void setHigh(double High) {
        this.High = High;
    }

    public void setVolumen(double Volumen) {
        this.Volumen = Volumen;
    }

    public void setRownumber(double rownumber) {
        this.rownumber = rownumber;
    }

    public void setZeit(Time zeit) {
        this.zeit = zeit;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }
    
    public double Openwert,Closewert,Low,High,Volumen,rownumber;
    public java.sql.Time zeit;
    public java.sql.Date datum; 
    public java.sql.Timestamp zeitdatum;
    
    public Kursdaten()
    {}
    
    public Kursdaten(Kursdaten k)
    {
        this.Openwert = k.Openwert;
        this.High = k.High;
        this.Low = k.Low;
        this.Closewert = k.Closewert;
        this.Volumen = k.Volumen;
        this.zeitdatum = k.zeitdatum;
    }
    
    public Kursdaten(String zeile)
    {
        String[] splitResult = zeile.split(",");
        String Uhrzeitdatum = splitResult[0];
        this.zeitdatum = convertZeitDatum(Uhrzeitdatum);
        //this.datum = convertdatum(Uhrzeitdatum);
        //this.zeit = convertzeit(Uhrzeitdatum);
        
        this.Openwert = Double.parseDouble(splitResult[1]);
        this.High = Double.parseDouble(splitResult[2]);
        this.Low = Double.parseDouble(splitResult[3]);
        this.Closewert = Double.parseDouble(splitResult[4]);
        //this.Volumen = Double.parseDouble(splitResult[5]);
    }
 
    public Date convertdatum(String Uhrzeitdatum)
    {
        String tag = Uhrzeitdatum.substring(0,2);
        String monat = Uhrzeitdatum.substring(3,5);
        String jahr = Uhrzeitdatum.substring(6,10);
        java.sql.Date tmp = new java.sql.Date(Integer.parseInt(jahr)-1900,Integer.parseInt(monat)-01,Integer.parseInt(tag));
        return tmp;
    }
    
    Time convertzeit(String Uhrzeitdatum)
    {
        String minuten = Uhrzeitdatum.substring(14,16);
        String stunden = Uhrzeitdatum.substring(11,13);
        String sekunden = Uhrzeitdatum.substring(17,19);
        java.sql.Time tmp = new Time(Integer.parseInt(stunden),Integer.parseInt(minuten),Integer.parseInt(sekunden));
        return tmp;
    }
    public java.sql.Timestamp convertZeitDatum(String Uhrzeitdatum)
    {
        String minuten = Uhrzeitdatum.substring(14,16);
        String stunden = Uhrzeitdatum.substring(11,13);
        String sekunden = Uhrzeitdatum.substring(17,19);
        String tag = Uhrzeitdatum.substring(0,2);
        String monat = Uhrzeitdatum.substring(3,5);
        String jahr = Uhrzeitdatum.substring(6,10);
        java.sql.Timestamp tmp = new java.sql.Timestamp(Integer.parseInt(jahr)-1900, Integer.parseInt(monat)-01
                , Integer.parseInt(tag), Integer.parseInt(stunden), Integer.parseInt(minuten), Integer.parseInt(sekunden), 0);
        return tmp;
    }
}
