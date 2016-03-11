/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

/**
 *
 * @author hrehmege
 */
public class rechneoperation {
    
    private rechneoperation() {
    }
    
    double plusDoubleRound(double a, double b, int nachkommastellen){
        return 0.0;
    }
    
    public static rechneoperation getInstance() {
        return rechneoperationHolder.INSTANCE;
    }
    
    private static class rechneoperationHolder {

        private static final rechneoperation INSTANCE = new rechneoperation();
    }
}
