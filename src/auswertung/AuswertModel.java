/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auswertung;

import boerse.TradeMessage;
import java.sql.Timestamp;

/**
 *
 * @author hrs <hauke.rehme-schlueter at hotmail.com>
 */
public class AuswertModel {
    double closewert;
    TradeMessage tradeMessage;

    public AuswertModel(double closewert, TradeMessage tradeMessage) {
        this.closewert = closewert;
        this.tradeMessage = tradeMessage;
    }

    public double getClosewert() {
        return closewert;
    }

    public void setClosewert(double closewert) {
        this.closewert = closewert;
    }

    public TradeMessage getTradeMessage() {
        return tradeMessage;
    }

    public void setTradeMessage(TradeMessage tradeMessage) {
        this.tradeMessage = tradeMessage;
    }
    
    
}
