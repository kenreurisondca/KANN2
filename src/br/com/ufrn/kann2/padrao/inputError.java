/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ufrn.kann2.padrao;

/**
 *
 * @author larihmoura
 */
public class inputError {

    private Double FP;
    private Double FN;

    public inputError(Double FP, Double FN) {
        this.FP = FP;
        this.FN = FN;
    }

    public Double getFP() {
        return FP;
    }

    public Double getFN() {
        return FN;
    }

    public Double getError() {
        return FP + FN;
    }

    public void incFP() {
        FP++;
    }

    public void incFN() {
        FN++;
    }

}
