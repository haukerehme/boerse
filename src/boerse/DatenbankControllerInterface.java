/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boerse;

import java.sql.Timestamp;
import java.util.ArrayList;

/**
 *
 * @author administrator
 */
public interface DatenbankControllerInterface {
    boolean isDatenbankLive(String tabellenname);
    Timestamp lastDbEntry(String tabellenname);
    ArrayList<Integer> getWerte(String tabellenname);
}
