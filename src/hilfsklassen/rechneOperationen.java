/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hilfsklassen;

/**
 *
 * @author hrs <hauke.rehme-schlueter at hotmail.com>
 */
public class rechneOperationen {
    
    private rechneOperationen() {
    }
    
    double plusDoubleRound(double a, double b, int nachkommastellen){
        return 0.0;
    }
    
    public static rechneOperationen getInstance() {
        return rechneOperationenHolder.INSTANCE;
    }
    
    private static class rechneOperationenHolder {

        private static final rechneOperationen INSTANCE = new rechneOperationen();
    }
}
