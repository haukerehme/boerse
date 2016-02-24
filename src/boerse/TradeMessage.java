/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boerse;

import java.io.IOException;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;
/**
 *
 * @author hrs <hauke.rehme-schlueter at hotmail.com>
 */
public class TradeMessage{

    
    private Timestamp timestamp;
    
    private String instrument;
    
    private int timeperiod;
    
    private int anzFound;
    
    private int longWin;
    
    private int longWinMiddle;
    
    private int longWinHigh;
    
    private int longWinVeryHigh;
    
    private int longLose;
    
    private int longLoseHigh;
    
    private int shortWin;
    
    private int shortWinMiddle;
    
    private int shortWinHigh;
    
    private int shortWinVeryHigh;
    
    private int shortLose;
    
    private int shortLoseHigh;

    public TradeMessage(Timestamp timestamp, String instrument, int timeperiod, int anzFound, int longWin, int longWinMiddle, int longWinHigh, int longWinVeryHigh, int longLose, int longLoseHigh, int shortWin, int shortWinMiddle, int shortWinHigh, int shortWinVeryHigh, int shortLose, int shortLoseHigh) {
        this.timestamp = timestamp;
        this.instrument = instrument;
        this.timeperiod = timeperiod;
        this.anzFound = anzFound;
        this.longWin = longWin;
        this.longWinMiddle = longWinMiddle;
        this.longWinHigh = longWinHigh;
        this.longWinVeryHigh = longWinVeryHigh;
        this.longLose = longLose;
        this.longLoseHigh = longLoseHigh;
        this.shortWin = shortWin;
        this.shortWinMiddle = shortWinMiddle;
        this.shortWinHigh = shortWinHigh;
        this.shortWinVeryHigh = shortWinVeryHigh;
        this.shortLose = shortLose;
        this.shortLoseHigh = shortLoseHigh;
    }
    
    

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    
    public int getLongLose() {
        return longLose;
    }

    public void setLongLose(int longLose) {
        this.longLose = longLose;
    }

    public int getLongLoseHigh() {
        return longLoseHigh;
    }

    public void setLongLoseHigh(int longLoseHigh) {
        this.longLoseHigh = longLoseHigh;
    }

    public int getShortLose() {
        return shortLose;
    }

    public void setShortLose(int shortLose) {
        this.shortLose = shortLose;
    }

    public int getShortLoseHigh() {
        return shortLoseHigh;
    }

    public void setShortLoseHigh(int shortLoseHigh) {
        this.shortLoseHigh = shortLoseHigh;
    }
    
    public String getInstrument() {
        return instrument;
    }

    public void setInstrument(String instrument) {
        this.instrument = instrument;
    }

    public int getTimeperiod() {
        return timeperiod;
    }

    public void setTimeperiod(int timeperiod) {
        this.timeperiod = timeperiod;
    }

    public int getAnzFound() {
        return anzFound;
    }

    public void setAnzFound(int anzFound) {
        this.anzFound = anzFound;
    }

    public int getLongWin() {
        return longWin;
    }

    public void setLongWin(int longWin) {
        this.longWin = longWin;
    }

    public int getLongWinMiddle() {
        return longWinMiddle;
    }

    public void setLongWinMiddle(int longWinMiddle) {
        this.longWinMiddle = longWinMiddle;
    }

    public int getLongWinHigh() {
        return longWinHigh;
    }

    public void setLongWinHigh(int longWinHigh) {
        this.longWinHigh = longWinHigh;
    }

    public int getLongWinVeryHigh() {
        return longWinVeryHigh;
    }

    public void setLongWinVeryHigh(int longWinVeryHigh) {
        this.longWinVeryHigh = longWinVeryHigh;
    }

    public int getShortWin() {
        return shortWin;
    }

    public void setShortWin(int shortWin) {
        this.shortWin = shortWin;
    }

    public int getShortWinMiddle() {
        return shortWinMiddle;
    }

    public void setShortWinMiddle(int shortWinMiddle) {
        this.shortWinMiddle = shortWinMiddle;
    }

    public int getShortWinHigh() {
        return shortWinHigh;
    }

    public void setShortWinHigh(int shortWinHigh) {
        this.shortWinHigh = shortWinHigh;
    }

    public int getShortWinVeryHigh() {
        return shortWinVeryHigh;
    }

    public void setShortWinVeryHigh(int shortWinVeryHigh) {
        this.shortWinVeryHigh = shortWinVeryHigh;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.timestamp);
        hash = 37 * hash + Objects.hashCode(this.instrument);
        hash = 37 * hash + this.timeperiod;
        hash = 37 * hash + this.anzFound;
        hash = 37 * hash + this.longWin;
        hash = 37 * hash + this.longWinMiddle;
        hash = 37 * hash + this.longWinHigh;
        hash = 37 * hash + this.longWinVeryHigh;
        hash = 37 * hash + this.longLose;
        hash = 37 * hash + this.longLoseHigh;
        hash = 37 * hash + this.shortWin;
        hash = 37 * hash + this.shortWinMiddle;
        hash = 37 * hash + this.shortWinHigh;
        hash = 37 * hash + this.shortWinVeryHigh;
        hash = 37 * hash + this.shortLose;
        hash = 37 * hash + this.shortLoseHigh;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TradeMessage other = (TradeMessage) obj;
        if (this.timeperiod != other.timeperiod) {
            return false;
        }
        if (this.anzFound != other.anzFound) {
            return false;
        }
        if (this.longWin != other.longWin) {
            return false;
        }
        if (this.longWinMiddle != other.longWinMiddle) {
            return false;
        }
        if (this.longWinHigh != other.longWinHigh) {
            return false;
        }
        if (this.longWinVeryHigh != other.longWinVeryHigh) {
            return false;
        }
        if (this.longLose != other.longLose) {
            return false;
        }
        if (this.longLoseHigh != other.longLoseHigh) {
            return false;
        }
        if (this.shortWin != other.shortWin) {
            return false;
        }
        if (this.shortWinMiddle != other.shortWinMiddle) {
            return false;
        }
        if (this.shortWinHigh != other.shortWinHigh) {
            return false;
        }
        if (this.shortWinVeryHigh != other.shortWinVeryHigh) {
            return false;
        }
        if (this.shortLose != other.shortLose) {
            return false;
        }
        if (this.shortLoseHigh != other.shortLoseHigh) {
            return false;
        }
        if (!Objects.equals(this.instrument, other.instrument)) {
            return false;
        }
        if (!Objects.equals(this.timestamp, other.timestamp)) {
            return false;
        }
        return true;
    }
    
    

    

    public void persistTradeMessage() throws ClassNotFoundException, IOException {
        DatenbankController dbCon = new DatenbankController();
        dbCon.persistTradeMessage(this);
    }
}
